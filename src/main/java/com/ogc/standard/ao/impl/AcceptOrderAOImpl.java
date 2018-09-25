package com.ogc.standard.ao.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ogc.standard.ao.IAcceptOrderAO;
import com.ogc.standard.bo.IAcceptOrderBO;
import com.ogc.standard.bo.IAccountBO;
import com.ogc.standard.bo.IBankcardBO;
import com.ogc.standard.bo.ISYSConfigBO;
import com.ogc.standard.bo.IUserBO;
import com.ogc.standard.bo.base.Paginable;
import com.ogc.standard.common.AmountUtil;
import com.ogc.standard.common.SysConstants;
import com.ogc.standard.core.StringValidater;
import com.ogc.standard.domain.AcceptOrder;
import com.ogc.standard.domain.Account;
import com.ogc.standard.domain.Bankcard;
import com.ogc.standard.dto.req.XN625270Req;
import com.ogc.standard.dto.req.XN625271Req;
import com.ogc.standard.enums.EAcceptOrderStatus;
import com.ogc.standard.enums.EBoolean;
import com.ogc.standard.enums.ECoin;
import com.ogc.standard.enums.EErrorCode_main;
import com.ogc.standard.enums.EJourBizTypePlat;
import com.ogc.standard.enums.EJourBizTypeUser;
import com.ogc.standard.enums.ESysUser;
import com.ogc.standard.enums.ESystemAccount;
import com.ogc.standard.exception.BizException;

@Service
public class AcceptOrderAOImpl implements IAcceptOrderAO {

    @Autowired
    private IAcceptOrderBO acceptOrderBO;

    @Autowired
    private ISYSConfigBO sysConfigBO;

    @Autowired
    private IAccountBO accountBO;

    @Autowired
    private IBankcardBO bankcardBO;

    @Autowired
    private IUserBO userBO;

    @Override
    public String buyOrder(XN625270Req req) {
        // 检验入参
        acceptOrderBO.check(req.getUserId(), req.getTradePrice(),
            req.getTradeCurrency(), req.getCount(), req.getTradeAmount());
        // 获取所有承兑商收款账号
        List<Bankcard> bankcardList = bankcardBO.queryBankcardList(
            ESysUser.SYS_USER.getCode(), req.getReceiveType());
        if (CollectionUtils.isEmpty(bankcardList)) {
            throw new BizException(EErrorCode_main.accept_UNSUPPORT.getCode());
        }
        return acceptOrderBO.saveBuyAcceptOrder(req, bankcardList);
    }

    @Override
    public String sellOrder(XN625271Req req) {
        // 检验入参
        acceptOrderBO.check(req.getUserId(), req.getTradePrice(),
            req.getTradeCurrency(), req.getCount(), req.getTradeAmount());

        // 校验出售者余额
        Account sellUserAccount = accountBO.getAccountByUser(req.getUserId(),
            ECoin.X.getCode());
        BigDecimal feeRate = sysConfigBO
            .getBigDecimalValue(SysConstants.ACCEPT_ORDER_SELL_FEE_RATE);
        BigDecimal tradeFee = AmountUtil.mul(feeRate,
            StringValidater.toBigDecimal(req.getCount()));
        BigDecimal balanceCount = sellUserAccount.getAmount()
            .subtract(sellUserAccount.getFrozenAmount()).subtract(tradeFee);// 剩余余额=
        if (balanceCount
            .compareTo(StringValidater.toBigDecimal(req.getCount())) < 0) {
            throw new BizException(EErrorCode_main.accept_SALEABLE.getCode());
        }

        // 保存订单
        AcceptOrder order = acceptOrderBO.saveSellAcceptOrder(req);

        Account dbAccount = accountBO.getAccountByUser(order.getUserId(),
            order.getTradeCoin());
        // 冻结卖家交易金额
        accountBO.frozenAmount(dbAccount, order.getCount(),
            EJourBizTypeUser.AJ_ACCEPT_FROZEN.getCode(),
            EJourBizTypeUser.AJ_ACCEPT_FROZEN.getValue(), order.getCode());
        // 冻结手续费金额
        accountBO.frozenAmount(dbAccount, order.getFee(),
            EJourBizTypeUser.AJ_ACCEPT_FROZEN.getCode(),
            EJourBizTypeUser.AJ_ACCEPT_FROZEN.getValue(), order.getCode());
        return order.getCode();
    }

    @Override
    public void cancelBuyOrder(String code, String userId, String remark) {
        AcceptOrder order = acceptOrderBO.getAcceptOrder(code);

        if (!EAcceptOrderStatus.TO_PAY.getCode().equals(order.getStatus())) {
            throw new BizException(EErrorCode_main.accept_UNCANCEL.getCode());
        }

        acceptOrderBO.cancel(order, userId, remark);

    }

    public void platCancel(String code, String userId, String remark) {
        AcceptOrder order = acceptOrderBO.getAcceptOrder(code);

        if (!EAcceptOrderStatus.PAYED.getCode().equals(order.getStatus())) {
            throw new BizException(EErrorCode_main.accept_UNCANCEL.getCode());
        }

        acceptOrderBO.cancel(order, userId, remark);

    }

    @Override
    public void markPay(String code, String updater) {
        AcceptOrder order = acceptOrderBO.getAcceptOrder(code);

        // 检查该订单是否超时
        if (order.getInvalidDatetime().compareTo(new Date()) < 0) {
            // 订单已超时,取消订单
            cancelBuyOrder(order.getCode(), ESysUser.SYS_USER.getCode(),
                "订单支付超时，系统自动取消");
            throw new BizException(EErrorCode_main.accept_OUTOFTIME.getCode());
        }

        if (!order.getUserId().equals(updater)) {
            throw new BizException(EErrorCode_main.accept_ONLYBUYER.getCode());
        }

        if (!EAcceptOrderStatus.TO_PAY.getCode().equals(order.getStatus())) {
            throw new BizException(EErrorCode_main.accept_STATUSPAY.getCode());
        }

        if (order.getType().equals(EBoolean.YES.getCode())) { // 0买入/1卖出
            throw new BizException(EErrorCode_main.accept_ONLYBUYER.getCode());

        }

        // 变更交易订单信息
        acceptOrderBO.markPay(order, updater);
    }

    @Override
    @Transactional
    public void release(String code, String result, String updater) {
        AcceptOrder order = acceptOrderBO.getAcceptOrder(code);
        if (EBoolean.YES.getCode().equals(result)) {
//            // 平台法币账户加钱
//            if (ECurrency.CNY.getCode().equals(order.getTradeCurrency())) {
//                Account cnyAccount = accountBO
//                    .getAccount(ESystemAccount.SYS_ACOUNT_CNY_ACCEPT.getCode());
//                accountBO.changeAmount(cnyAccount, order.getTradeAmount(),
//                    EChannelType.NBZ, null, order.getCode(),
//                    EJourBizTypePlat.AJ_ACCEPT_SELL.getCode(), "平台卖币法币账户加钱");
//            } else if (ECurrency.USD.getCode()
//                .equals(order.getTradeCurrency())) {
//                Account usdAccount = accountBO
//                    .getAccount(ESystemAccount.SYS_ACOUNT_USD_ACCEPT.getCode());
//                accountBO.changeAmount(usdAccount, order.getTradeAmount(),
//                    EChannelType.NBZ, null, order.getCode(),
//                    EJourBizTypePlat.AJ_ACCEPT_SELL.getCode(), "平台卖币法币账户加钱");
//            } else {
//                throw new BizException(EBizErrorCode.DEFAULT.getCode(),
//                    "不支持的法币交易");
//            }
            if (!(EAcceptOrderStatus.PAYED.getCode()
                .equals(order.getStatus()))) {
                throw new BizException(
                    EErrorCode_main.accept_UNCANCEL.getCode());
            }

            Account dbAccount = accountBO.getAccountByUser(order.getUserId(),
                order.getTradeCoin());

            Account platAccount = accountBO
                .getAccount(ESystemAccount.SYS_ACOUNT_X_ACCEPT.getCode());
            // 交易金额
            accountBO.transAmount(platAccount, dbAccount, order.getCount(),
                EJourBizTypePlat.AJ_ACCEPT_SELL.getCode(),
                EJourBizTypeUser.AJ_ACCEPT_BUY.getCode(),
                EJourBizTypePlat.AJ_ACCEPT_SELL.getValue(),
                EJourBizTypeUser.AJ_ACCEPT_BUY.getValue(), order.getCode());

            Account feeAccount = accountBO
                .getAccount(ESystemAccount.SYS_ACOUNT_X_FEE.getCode());
            // 手续费
            accountBO.transAmount(dbAccount, feeAccount, order.getFee(),
                EJourBizTypeUser.AJ_ACCEPT_FEE.getCode(),
                EJourBizTypePlat.AJ_ACCEPT_FEE.getCode(),
                EJourBizTypeUser.AJ_ACCEPT_FEE.getValue(),
                EJourBizTypePlat.AJ_ACCEPT_FEE.getValue(), order.getCode());

            acceptOrderBO.platMarkPay(order, updater, "收款成功并释放币");
        } else {
            platCancel(order.getCode(), ESysUser.SYS_USER.getCode(),
                "未收到钱款，取消订单");
        }
    }

    @Override
    @Transactional
    public void platPay(String code, String result, String updater) {
        AcceptOrder order = acceptOrderBO.getAcceptOrder(code);
        if (EBoolean.YES.getCode().equals(result)) {
            // 平台法币账户减钱
//            if (ECurrency.CNY.getCode().equals(order.getTradeCurrency())) {
//                Account cnyAccount = accountBO
//                    .getAccount(ESystemAccount.SYS_ACOUNT_CNY_ACCEPT.getCode());
//                accountBO.changeAmount(cnyAccount,
//                    order.getTradeAmount().negate(), EChannelType.NBZ, null,
//                    order.getCode(), EJourBizTypePlat.AJ_ACCEPT_BUY.getCode(),
//                    "平台卖币法币账户减钱");
//            } else if (ECurrency.USD.getCode()
//                .equals(order.getTradeCurrency())) {
//                Account usdAccount = accountBO
//                    .getAccount(ESystemAccount.SYS_ACOUNT_USD_ACCEPT.getCode());
//                accountBO.changeAmount(usdAccount,
//                    order.getTradeAmount().negate(), EChannelType.NBZ, null,
//                    order.getCode(), EJourBizTypePlat.AJ_ACCEPT_BUY.getCode(),
//                    "平台卖币法币账户减钱");
//            } else {
//                throw new BizException(EBizErrorCode.DEFAULT.getCode(),
//                    "不支持的法币交易");
//            }
            if (!EAcceptOrderStatus.TO_PAY.getCode()
                .equals(order.getStatus())) {
                throw new BizException(
                    EErrorCode_main.accept_UNCANCEL.getCode());
            }
            Account dbAccount = accountBO.getAccountByUser(order.getUserId(),
                order.getTradeCoin());

            Account platAccount = accountBO
                .getAccount(ESystemAccount.SYS_ACOUNT_X_ACCEPT.getCode());
            // 解冻交易金额
            accountBO.unfrozenAmount(dbAccount, order.getCount(),
                EJourBizTypeUser.AJ_ACCEPT_UNFROZEN.getCode(), "交易解冻",
                order.getCode());
            // 解冻交易手续费
            accountBO.unfrozenAmount(dbAccount, order.getFee(),
                EJourBizTypeUser.AJ_ACCEPT_FEE.getCode(), "交易手续费解冻",
                order.getCode());
            // 交易金额
            accountBO.transAmount(dbAccount, platAccount, order.getCount(),
                EJourBizTypeUser.AJ_ACCEPT_SELL.getValue(),
                EJourBizTypePlat.AJ_ACCEPT_BUY.getValue(),
                EJourBizTypeUser.AJ_ACCEPT_SELL.getCode(),
                EJourBizTypePlat.AJ_ACCEPT_BUY.getCode(), order.getCode());

            Account feeAccount = accountBO
                .getAccount(ESystemAccount.SYS_ACOUNT_X_FEE.getCode());
            // 手续费
            accountBO.transAmount(dbAccount, feeAccount, order.getFee(),
                EJourBizTypeUser.AJ_ACCEPT_FEE.getCode(),
                EJourBizTypePlat.AJ_ACCEPT_FEE.getCode(),
                EJourBizTypeUser.AJ_ACCEPT_FEE.getValue(),
                EJourBizTypePlat.AJ_ACCEPT_FEE.getValue(), order.getCode());
            // 改订单状态
            acceptOrderBO.platMarkPay(order, updater, "已打款并完成交易");
        } else {
            // 取消订单
            cancelBuyOrder(order.getCode(), ESysUser.SYS_USER.getCode(),
                "平台取消订单");
            // 解冻账户
            Account dbAccount = accountBO.getAccountByUser(order.getUserId(),
                ECoin.X.getCode());
            // 解冻交易金额
            accountBO.unfrozenAmount(dbAccount, order.getCount(),
                EJourBizTypeUser.AJ_ACCEPT_UNFROZEN.getCode(), "平台取消订单",
                order.getCode());
            // 解冻交易费率
            accountBO.unfrozenAmount(dbAccount, order.getFee(),
                EJourBizTypeUser.AJ_ACCEPT_UNFROZEN.getCode(), "平台取消订单",
                order.getCode());
        }

    }

    @Override
    public Paginable<AcceptOrder> queryAcceptOrderPage(int start, int limit,
            AcceptOrder condition) {
        Paginable<AcceptOrder> orderPage = acceptOrderBO.getPaginable(start,
            limit, condition);
        for (AcceptOrder order : orderPage.getList()) {
            order.setUser(userBO.getUser(order.getUserId()));
            order.setBankcard(bankcardBO
                .getBankcardByBankcardNumber(order.getReceiveCardNo()));
        }
        return orderPage;
    }

    @Override
    public List<AcceptOrder> queryAcceptOrderList(AcceptOrder condition) {
        return acceptOrderBO.queryCoinAcceptOrderList(condition);
    }

    @Override
    public AcceptOrder getAcceptOrder(String code) {
        AcceptOrder data = acceptOrderBO.getAcceptOrder(code);
        data.setUser(userBO.getUser(data.getUserId()));
        data.setBankcard(
            bankcardBO.getBankcardByBankcardNumber(data.getReceiveCardNo()));
        return data;
    }
}
