package com.ogc.standard.bo.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ogc.standard.ao.IMarketAO;
import com.ogc.standard.bo.IAcceptOrderBO;
import com.ogc.standard.bo.IBankcardBO;
import com.ogc.standard.bo.IBlacklistBO;
import com.ogc.standard.bo.ISYSConfigBO;
import com.ogc.standard.bo.IUserBO;
import com.ogc.standard.bo.base.PaginableBOImpl;
import com.ogc.standard.common.AmountUtil;
import com.ogc.standard.common.DateUtil;
import com.ogc.standard.common.SysConstants;
import com.ogc.standard.core.OrderNoGenerater;
import com.ogc.standard.core.StringValidater;
import com.ogc.standard.dao.IAcceptOrderDAO;
import com.ogc.standard.domain.AcceptOrder;
import com.ogc.standard.domain.Bankcard;
import com.ogc.standard.dto.req.XN625270Req;
import com.ogc.standard.dto.req.XN625271Req;
import com.ogc.standard.enums.EAcceptOrderStatus;
import com.ogc.standard.enums.EAcceptType;
import com.ogc.standard.enums.EBoolean;
import com.ogc.standard.enums.ECoin;
import com.ogc.standard.enums.EErrorCode_main;
import com.ogc.standard.enums.EGeneratePrefix;
import com.ogc.standard.enums.ESysConfigType;
import com.ogc.standard.enums.ESysUser;
import com.ogc.standard.exception.BizException;

@Component
public class AcceptOrderBOImpl extends PaginableBOImpl<AcceptOrder>
        implements IAcceptOrderBO {

    @Autowired
    private IAcceptOrderDAO acceptOrderDAO;

    @Autowired
    private ISYSConfigBO sysConfigBO;

    @Autowired
    private IBankcardBO bankcardBO;

    @Autowired
    private IUserBO userBO;

    @Autowired
    private IMarketAO marketAO;

    @Autowired
    private IBlacklistBO blacklistBO;

    @Override
    public String saveBuyAcceptOrder(XN625270Req req,
            List<Bankcard> bankcardList) {
        AcceptOrder data = new AcceptOrder();
        String code = OrderNoGenerater
            .generate(EGeneratePrefix.ACCEPT_ORDER.getCode());
        data.setCode(code);
        data.setType(EAcceptType.IN.getCode());
        data.setUserId(req.getUserId());
        data.setAcceptUserId(ESysUser.SYS_USER.getCode());
        data.setTradeCurrency(req.getTradeCurrency());

        data.setTradeCoin(ECoin.X.getCode());
        data.setTradePrice(StringValidater.toBigDecimal(req.getTradePrice()));
        data.setCount(StringValidater.toBigDecimal(req.getCount()));
        data.setTradeAmount(StringValidater.toBigDecimal(req.getTradeAmount()));

        Map<String, String> ruleMap = sysConfigBO
            .getConfigsMap(ESysConfigType.ACCEPT_RULE.getCode());
        BigDecimal feeRate = new BigDecimal(
            ruleMap.get(SysConstants.ACCEPT_ORDER_BUY_FEE_RATE));
        data.setFee(AmountUtil.mul(data.getCount(), feeRate));
        Date date = new Date();
        data.setInvalidDatetime(DateUtil.getRelativeDateOfMinute(date, Integer
            .valueOf(ruleMap.get(SysConstants.ACCEPT_ORDER_CANCEL_MAX_TIME))));
        // 随机获取一个承兑商账户
        Random rand = new Random();
        Bankcard bankcard = bankcardList.get(rand.nextInt(bankcardList.size()));
        data.setReceiveBank(bankcard.getBankName());
        data.setReceiveCardNo(bankcard.getBankcardNumber());
        data.setReceiveInfo(bankcard.getSubbranch());
        data.setReceiveType(req.getReceiveType());
        data.setStatus(EAcceptOrderStatus.TO_PAY.getCode());
        data.setCreateDatetime(date);

        acceptOrderDAO.insert(data);
        return code;
    }

    @Override
    public AcceptOrder saveSellAcceptOrder(XN625271Req req) {
        AcceptOrder data = new AcceptOrder();
        String code = OrderNoGenerater
            .generate(EGeneratePrefix.ACCEPT_ORDER.getCode());
        BigDecimal feeRate = sysConfigBO
            .getBigDecimalValue(SysConstants.ACCEPT_ORDER_SELL_FEE_RATE);
        String bankInfo = bankcardBO
            .getBankcardByBankcardNumber(req.getReceiveCardNo()).getSubbranch();
        String receiveBank = bankcardBO
            .getBankcardByBankcardNumber(req.getReceiveCardNo()).getBankName();
        Map<String, String> ruleMap = sysConfigBO
            .getConfigsMap(ESysConfigType.ACCEPT_RULE.getCode());
        data.setCode(code);
        data.setType(EBoolean.YES.getCode()); // 0买入/1卖出
        data.setUserId(req.getUserId());
        data.setAcceptUserId(ESysUser.SYS_USER.getCode());
        data.setTradeCurrency(req.getTradeCurrency());

        data.setTradeCoin(ECoin.X.getCode());
        data.setTradePrice(StringValidater.toBigDecimal(req.getTradePrice()));
        data.setCount(StringValidater.toBigDecimal(req.getCount()));
        data.setTradeAmount(StringValidater.toBigDecimal(req.getTradeAmount()));
        BigDecimal fee = feeRate
            .multiply(StringValidater.toBigDecimal(req.getCount()));
        data.setFee(fee);
        Date date = new Date();
        data.setInvalidDatetime(DateUtil.getRelativeDateOfMinute(date, Integer
            .valueOf(ruleMap.get(SysConstants.ACCEPT_ORDER_CANCEL_MAX_TIME))));

        data.setReceiveType(req.getReceiveType());
        data.setReceiveInfo(bankInfo);
        data.setReceiveBank(receiveBank);
        data.setReceiveCardNo(req.getReceiveCardNo());
        data.setStatus(EAcceptOrderStatus.TO_PAY.getCode());
        data.setCreateDatetime(new Date());

        acceptOrderDAO.insert(data);
        return data;
    }

    @Override
    public List<AcceptOrder> queryCoinAcceptOrderList(AcceptOrder condition) {
        return acceptOrderDAO.selectList(condition);
    }

    @Override
    public AcceptOrder getAcceptOrder(String code) {
        AcceptOrder data = null;
        if (StringUtils.isNotBlank(code)) {
            AcceptOrder condition = new AcceptOrder();
            condition.setCode(code);
            data = acceptOrderDAO.select(condition);
            if (data == null) {
                throw new BizException(
                    EErrorCode_main.accept_ORDEREXIST.getCode());
            }
        }
        return data;
    }

    @Override
    public int cancel(AcceptOrder acceptOrder, String updater, String remark) {
        int count = 0;
        if (acceptOrder != null) {
            acceptOrder.setStatus(EAcceptOrderStatus.CCANCELED.getCode());
            if (ESysUser.SYS_USER.getCode().equals(updater)) {
                acceptOrder.setStatus(EAcceptOrderStatus.PCANCELED.getCode());
            }
            acceptOrder.setUpdateDatetime(new Date());
            acceptOrder.setUpdater(updater);
            acceptOrder.setRemark(remark);
            acceptOrder.setUpdateDatetime(new Date());
            count = acceptOrderDAO.updateCancel(acceptOrder);
        }
        return count;
    }

    @Override
    public int markPay(AcceptOrder tradeOrder, String updater) {
        int count = 0;
        if (tradeOrder != null) {
            tradeOrder.setStatus(EAcceptOrderStatus.PAYED.getCode());
            tradeOrder.setMarkDatetime(new Date());
            tradeOrder.setMarkNote("已标记打款");
            tradeOrder.setUpdater(updater);
            tradeOrder.setUpdateDatetime(new Date());
            tradeOrder.setRemark("已标记付款，待释放");
            count = acceptOrderDAO.updateMarkPay(tradeOrder);
        }
        return count;
    }

    @Override
    public int platMarkPay(AcceptOrder tradeOrder, String updater,
            String note) {
        int count = 0;
        if (tradeOrder != null) {
            tradeOrder.setStatus(EAcceptOrderStatus.RELEASED.getCode());
            tradeOrder.setMarkDatetime(new Date());
            tradeOrder.setMarkNote(note);
            tradeOrder.setUpdater(updater);
            tradeOrder.setUpdateDatetime(new Date());
            tradeOrder.setRemark(note);
            count = acceptOrderDAO.updateMarkPay(tradeOrder);
        }
        return count;
    }

    @Override
    public void check(String userId, String tradePrice, String tradeCurrecny,
            String tradeCount, String amount) {
        // 校验用户是否存在
        userBO.getUser(userId);

        // 检查黑名单
        blacklistBO.isAddBlacklist(userId);

        // 校验当时行情，需在容错误差内
        BigDecimal price = StringValidater.toBigDecimal(tradePrice);
        if (price.compareTo(BigDecimal.ZERO) <= 0) {
            throw new BizException(EErrorCode_main.accept_PRICEZERO.getCode());
        }
        BigDecimal btcToCurrency = marketAO
            .coinPriceByPlatform(ECoin.BTC.getCode(), tradeCurrecny).getMid();
        BigDecimal xToBtc = marketAO
            .coinPriceByPlatform(ECoin.X.getCode(), ECoin.BTC.getCode())
            .getMid();
        BigDecimal newPrice = btcToCurrency.multiply(xToBtc);
        if (newPrice.subtract(price).abs().compareTo(BigDecimal.ONE) >= 0) {
            throw new BizException(EErrorCode_main.accept_PRICEERROR.getCode());
        }
        BigDecimal count = StringValidater.toBigDecimal(tradeCount);
        if (count.compareTo(BigDecimal.ZERO) <= 0) {
            throw new BizException(EErrorCode_main.accept_COUNTZERO.getCode());
        }

        BigDecimal tradeAmount = StringValidater.toBigDecimal(amount);
        if (tradeAmount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new BizException(EErrorCode_main.accept_AMOUNTZERO.getCode());
        }
    }

}
