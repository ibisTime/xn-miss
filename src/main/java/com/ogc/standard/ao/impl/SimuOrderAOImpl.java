package com.ogc.standard.ao.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ogc.standard.ao.ISimuOrderAO;
import com.ogc.standard.bo.IAccountBO;
import com.ogc.standard.bo.ICoinBO;
import com.ogc.standard.bo.IHandicapBO;
import com.ogc.standard.bo.ISimuKLineBO;
import com.ogc.standard.bo.ISimuOrderBO;
import com.ogc.standard.bo.ISimuOrderHistoryBO;
import com.ogc.standard.bo.IUserBO;
import com.ogc.standard.bo.base.Paginable;
import com.ogc.standard.common.CoinUtil;
import com.ogc.standard.common.SysConstants;
import com.ogc.standard.core.StringValidater;
import com.ogc.standard.domain.Account;
import com.ogc.standard.domain.Coin;
import com.ogc.standard.domain.SimuKLine;
import com.ogc.standard.domain.SimuOrder;
import com.ogc.standard.domain.User;
import com.ogc.standard.dto.req.XN650050Req;
import com.ogc.standard.enums.EErrorCode_main;
import com.ogc.standard.enums.EJourBizTypeUser;
import com.ogc.standard.enums.ESimuKLinePeriod;
import com.ogc.standard.enums.ESimuOrderDirection;
import com.ogc.standard.enums.ESimuOrderStatus;
import com.ogc.standard.enums.ESimuOrderType;
import com.ogc.standard.exception.BizException;
import com.ogc.standard.util.SymbolUtil;

@Service
public class SimuOrderAOImpl implements ISimuOrderAO {

    @Autowired
    private ISimuOrderBO simuOrderBO;

    @Autowired
    private ISimuOrderHistoryBO simuOrderHistoryBO;

    @Autowired
    private IUserBO userBO;

    @Autowired
    private IHandicapBO handicapBO;

    @Autowired
    private ISimuKLineBO simuKLineBO;

    @Autowired
    private IAccountBO accountBO;

    @Autowired
    private ICoinBO coinBO;

    @Override
    @Transactional
    public String submit(XN650050Req req) {

        SimuOrder data = new SimuOrder();

        // 参数验证
        parameterValidater(req);

        if (ESimuOrderDirection.BUY.getCode().equals(req.getDirection())) {

            // 买入单
            data = submitBuyOrder(req);

        } else if (ESimuOrderDirection.SELL.getCode()
            .equals(req.getDirection())) {

            // 卖出单
            data = submitSellOrder(req);

        } else {

            throw new BizException(
                EErrorCode_main.simuorder_UNSUPPORTDIR.getCode());

        }

        return data.getCode();
    }

    @Override
    @Transactional
    public void cancel(String code) {
        SimuOrder data = simuOrderBO.getSimuOrderCheck(code);
        if (!ESimuOrderStatus.SUBMIT.getCode().equals(data.getStatus())) {
            throw new BizException(
                EErrorCode_main.simuorder_UNSUPPORTREVORK.getCode());
        }

        // 更新委托单信息
        if (ESimuOrderStatus.SUBMIT.getCode().equals(data.getStatus())) {
            data.setAvgPrice(BigDecimal.ZERO);
            data.setStatus(ESimuOrderStatus.CANCEL.getCode());
        } else {
            data.setStatus(ESimuOrderStatus.PART_DEAL_CANCEL.getCode());
        }
        data.setCancelDatetime(new Date());

        // 增加历史委托
        simuOrderHistoryBO.saveSimuOrderHistory(data);

        // 限价单需要删除盘口
        if (ESimuOrderType.LIMIT.getCode().equals(data.getType())) {
            handicapBO.removeHandicap(data.getCode());
        }

        // 撤销，并从 存活委托 中删除；
        simuOrderBO.cancel(data);

        // 解冻交易币种数量
        String accountSymbol;
        BigDecimal unfrozenAmount;
        if (ESimuOrderDirection.BUY.getCode().equals(data.getDirection())) {
            accountSymbol = data.getToSymbol();
            unfrozenAmount = data.getTotalAmount();
        } else {
            accountSymbol = data.getSymbol();
            unfrozenAmount = data.getTotalCount();
        }

        Account account = accountBO.getAccountByUser(data.getUserId(),
            accountSymbol);
        accountBO.unfrozenAmount(account, unfrozenAmount,
            EJourBizTypeUser.AJ_BBORDER_UNFROZEN_REVOKE.getCode(),
            EJourBizTypeUser.AJ_BBORDER_UNFROZEN_REVOKE.getValue(), code);
    }

    @Override
    public Paginable<SimuOrder> querySimuOrderPage(int start, int limit,
            SimuOrder condition) {
        return simuOrderBO.getPaginable(start, limit, condition);
    }

    @Override
    public List<SimuOrder> querySimuOrderList(SimuOrder condition) {
        return simuOrderBO.querySimuOrderList(condition);
    }

    @Override
    public SimuOrder getSimuOrder(String code) {
        return simuOrderBO.getSimuOrderCheck(code);
    }

    private SimuOrder submitBuyOrder(XN650050Req req) {

        // 获取币种单位
        Coin coin = coinBO.getCoin(req.getSymbol());

        // 买入币总数量
        BigDecimal totalCount = StringValidater
            .toBigDecimal(req.getTotalCount());

        // 单个币买入价格（所需计价币种的数量）
        BigDecimal price;

        // 所有币买入，总共需要的计价币种的数量
        BigDecimal totalAmount;

        if (ESimuOrderType.LIMIT.getCode().equals(req.getType())) {
            price = StringValidater.toBigDecimal(req.getPrice());
            totalAmount = price
                .multiply(CoinUtil.fromMinUnit(totalCount, coin.getUnit()));
        } else {
            // 市价买单没有价格
            price = new BigDecimal(-1);
            // 在市价买单中数量的单位是 计价币种 ,意思是希望按照现在的市场价格持续买入，直到完全成交（计价币种完全消耗完）后停止
            totalAmount = totalCount;
        }

        User user = userBO.getUser(req.getUserId());

        // 检查计价币种是否存在 和 账户余额
        Account account = accountBO.getAccountByUser(user.getUserId(),
            req.getToSymbol());
        if (account.getAmount().subtract(account.getFrozenAmount())
            .compareTo(totalAmount) < 0) {
            throw new BizException(
                EErrorCode_main.account_PERSONALLEFT.getCode());
        }

        // 落地委托单
        SimuOrder simuOrder = simuOrderBO.saveSimuOrder(req, totalCount, price,
            totalAmount);

        // 冻结计价币种
        accountBO.frozenAmount(account, totalAmount,
            EJourBizTypeUser.AJ_BBORDER_FROZEN.getCode(), "提交购买[" + SymbolUtil
                .getSymbolPair(req.getSymbol(), req.getToSymbol()) + "]委托单",
            simuOrder.getCode());

        return simuOrder;
    }

    private SimuOrder submitSellOrder(XN650050Req req) {

        // 获取币种单位
        Coin coin = coinBO.getCoin(req.getSymbol());

        // 卖出币总数量
        BigDecimal totalCount = StringValidater
            .toBigDecimal(req.getTotalCount());

        // 单个币卖出价格（所需计价币种的数量）
        BigDecimal price;

        // 所有币卖出，总共需要的计价币种的数量
        BigDecimal totalAmount;
        if (ESimuOrderType.LIMIT.getCode().equals(req.getType())) {
            price = StringValidater.toBigDecimal(req.getPrice());
            totalAmount = price
                .multiply(CoinUtil.fromMinUnit(totalCount, coin.getUnit()));
        } else {
            // 市价单卖单没有价格
            price = new BigDecimal(-1);
            // 在市价卖单中数量的单位是 交易币种 ,意思是希望按照现在的市场价格持续卖出，直到完全成交（交易币种完全消耗完）后停止
            totalAmount = totalCount;
        }

        User user = userBO.getUser(req.getUserId());

        // 检查卖出币种账户 和 账户余额
        Account account = accountBO.getAccountByUser(user.getUserId(),
            req.getSymbol());
        if (account.getAmount().subtract(account.getFrozenAmount())
            .compareTo(totalCount) < 0) {
            throw new BizException(
                EErrorCode_main.account_PERSONALLEFT.getCode());
        }

        // 落地委托单
        SimuOrder simuOrder = simuOrderBO.saveSimuOrder(req, totalCount, price,
            totalAmount);

        // 冻结出售币种资产
        accountBO.frozenAmount(account, totalCount,
            EJourBizTypeUser.AJ_BBORDER_SELL.getCode(), "提交卖出[" + SymbolUtil
                .getSymbolPair(req.getSymbol(), req.getToSymbol()) + "]委托单",
            simuOrder.getCode());

        return simuOrder;

    }

    private void parameterValidater(XN650050Req req) {
        ESimuOrderType eSimuOrderType = ESimuOrderType
            .getOrderType(req.getType());
        if (ESimuOrderType.LIMIT.getCode().equals(eSimuOrderType.getCode())) {
            if (StringUtils.isBlank(req.getPrice())) {
                throw new BizException(
                    EErrorCode_main.simuorder_PRICE.getCode());
            }
        }

        // 获取币种单位
        Coin coin = coinBO.getCoin(req.getSymbol());

        // 委托数量是否超过限制
        BigDecimal count = CoinUtil.fromMinUnit(
            StringValidater.toBigDecimal(req.getTotalCount()), coin.getUnit());
        if (count.compareTo(SysConstants.minCountLimit) < 0
                || count.compareTo(SysConstants.maxCountLimit) > 0) {
            throw new BizException(EErrorCode_main.simucount_COUNT.getCode(),
                SysConstants.minCountLimit, SysConstants.maxCountLimit);
        }

        // 是否是最小委托数量的整数倍
        if (BigDecimal.ZERO.compareTo(
            count.divideAndRemainder(SysConstants.minCountLimit)[1]) != 0) {
            throw new BizException(
                EErrorCode_main.simuorder_MINCOUNT.getCode());
        }

        // 委托价格是否超过价格范围:不高于前收盘价的900%，不低于前收盘价测50%
        BigDecimal price = StringValidater.toBigDecimal(req.getTotalCount());
        // 获取上一时间单位的K线
        SimuKLine simuKLine = simuKLineBO.getLatestSimuKLine(req.getSymbol(),
            req.getToSymbol(), ESimuKLinePeriod.MIN1.getCode());

        if (null != simuKLine) {
            if (price.compareTo(
                simuKLine.getClose().multiply(new BigDecimal("9"))) > 0) {
                throw new BizException(
                    EErrorCode_main.simuorder_PRICELESSTHAN.getCode());
            }

            if (price.compareTo(
                simuKLine.getClose().multiply(new BigDecimal("0.5"))) < 0) {
                throw new BizException(
                    EErrorCode_main.simuorder_PRICEMORETHAN.getCode());
            }

        }
    }
}
