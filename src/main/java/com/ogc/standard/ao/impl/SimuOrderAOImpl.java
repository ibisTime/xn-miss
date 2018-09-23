package com.ogc.standard.ao.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ogc.standard.ao.ISimuOrderAO;
import com.ogc.standard.bo.IAccountBO;
import com.ogc.standard.bo.ICoinBO;
import com.ogc.standard.bo.IHandicapBO;
import com.ogc.standard.bo.ISYSConfigBO;
import com.ogc.standard.bo.ISimuKLineBO;
import com.ogc.standard.bo.ISimuMatchResultBO;
import com.ogc.standard.bo.ISimuOrderBO;
import com.ogc.standard.bo.ISimuOrderDetailBO;
import com.ogc.standard.bo.ISimuOrderHistoryBO;
import com.ogc.standard.bo.IUserBO;
import com.ogc.standard.bo.base.Paginable;
import com.ogc.standard.common.CoinUtil;
import com.ogc.standard.common.SysConstants;
import com.ogc.standard.core.StringValidater;
import com.ogc.standard.domain.Account;
import com.ogc.standard.domain.Coin;
import com.ogc.standard.domain.Handicap;
import com.ogc.standard.domain.SimuKLine;
import com.ogc.standard.domain.SimuOrder;
import com.ogc.standard.domain.SimuOrderDetail;
import com.ogc.standard.domain.User;
import com.ogc.standard.dto.req.XN650050Req;
import com.ogc.standard.enums.EJourBizTypeUser;
import com.ogc.standard.enums.ESimuKLinePeriod;
import com.ogc.standard.enums.ESimuOrderDirection;
import com.ogc.standard.enums.ESimuOrderStatus;
import com.ogc.standard.enums.ESimuOrderType;
import com.ogc.standard.exception.BizException;
import com.ogc.standard.exception.EBizErrorCode;
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

    @Autowired
    private ISimuMatchResultBO simuMatchResultBO;

    @Autowired
    private ISimuOrderDetailBO simuOrderDetailBO;

    @Autowired
    private ISYSConfigBO sysConfigBO;

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

            throw new BizException(EBizErrorCode.DEFAULT.getCode(), "不支持的买卖方向");

        }

        return data.getCode();
    }

    @Override
    @Transactional
    public void cancel(String code) {
        SimuOrder data = simuOrderBO.getSimuOrderCheck(code);
        if (!ESimuOrderStatus.SUBMIT.getCode().equals(data.getStatus())) {
            throw new BizException(EBizErrorCode.DEFAULT.getCode(),
                "当前状态下不支持撤单");
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
            throw new BizException(EBizErrorCode.DEFAULT.getCode(),
                "当前账户" + account.getCurrency() + "资产可用余额不足");
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
            throw new BizException(EBizErrorCode.DEFAULT.getCode(),
                "当前账户" + req.getSymbol() + "资产可用余额不足");
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
                throw new BizException(EBizErrorCode.DEFAULT.getCode(),
                    "限价委托单委托价格需必填");
            }
        }

        // 获取币种单位
        Coin coin = coinBO.getCoin(req.getSymbol());

        // 委托数量是否超过限制
        BigDecimal count = CoinUtil.fromMinUnit(
            StringValidater.toBigDecimal(req.getTotalCount()), coin.getUnit());
        if (count.compareTo(SysConstants.minCountLimit) < 0
                || count.compareTo(SysConstants.maxCountLimit) > 0) {
            throw new BizException(EBizErrorCode.DEFAULT.getCode(),
                "委托数量应在" + SysConstants.minCountLimit + "至"
                        + SysConstants.maxCountLimit + "之间");
        }

        // 是否是最小委托数量的整数倍
        if (BigDecimal.ZERO.compareTo(
            count.divideAndRemainder(SysConstants.minCountLimit)[1]) != 0) {
            throw new BizException(EBizErrorCode.DEFAULT.getCode(),
                "委托数量应当" + SysConstants.minCountLimit + "的整数倍");
        }

        // 委托价格是否超过价格范围:不高于前收盘价的900%，不低于前收盘价测50%
        BigDecimal price = StringValidater.toBigDecimal(req.getTotalCount());
        // 获取上一时间单位的K线
        SimuKLine simuKLine = simuKLineBO.getLatestSimuKLine(req.getSymbol(),
            req.getToSymbol(), ESimuKLinePeriod.MIN1.getCode());

        if (null != simuKLine) {
            if (price.compareTo(
                simuKLine.getClose().multiply(new BigDecimal("9"))) > 0) {
                throw new BizException(EBizErrorCode.DEFAULT.getCode(),
                    "委托价格不得高于前收盘价的900%");
            }

            if (price.compareTo(
                simuKLine.getClose().multiply(new BigDecimal("0.5"))) < 0) {
                throw new BizException(EBizErrorCode.DEFAULT.getCode(),
                    "委托价格不得低于前收盘价测50%");
            }

        }
    }

    // 扫描委托单集合
    public void scanSimuOrder() {
        int start = 1;
        int limit = 50;
        while (true) {
            List<SimuOrder> simuOrderList = simuOrderBO
                .queryToScanSimuOrderList(start, limit);
            if (CollectionUtils.isEmpty(simuOrderList)) {
                break;
            }
            for (SimuOrder simuOrder : simuOrderList) {
                // if (ESimuOrderType.MARKET.getCode()
                // .equals(simuOrder.getType())) {
                //
                // } else {
                // doMatchLimit(simuOrder);
                // }
                doMatchMarket(simuOrder);
            }
        }
    }

    /**
     * 市价单撮合
     * 1.从存活委托单池内取出对应交易对下所有市价单
     * 2.获取对向盘口档位，循环盘口档位，根据价格匹配市价单
     * 3.撮合当前档位下匹配到的市价单和盘口
     * 4.修改市价单、盘口交易信息
     * 5.根据最后的撮合结果修改市价单信息
     */
    private void doMatchMarket(SimuOrder simuOrder) {
        // 初始化对方盘口数据和档位
        BigDecimal[] priceArray = new BigDecimal[10];
        List<Handicap> handicapList = new ArrayList<Handicap>();
        int start = 1;
        int limit = 100;
        while (true) {
            // 获取对向盘口，遍历10档内数据,先遍历100条数据
            List<Handicap> resultList = handicapBO.queryHandicapList(
                simuOrder.getSymbol(), simuOrder.getToSymbol(),
                simuOrder.getDirection(), start, limit);
            if (CollectionUtils.isEmpty(resultList)) {
                break;
            }

            for (Handicap handicap : resultList) {
                // 此处有坑，写main方法测试
                if (!ArrayUtils.contains(priceArray, handicap.getPrice())) {
                    ArrayUtils.add(priceArray, handicap.getPrice());
                    handicapList.add(handicap);
                    if (priceArray.length == 10) {
                        break;
                    }
                }
            }
            start++;
        }

        // 数据为空，进历史
        if (CollectionUtils.isEmpty(handicapList)) {
            doEmptyIntoHistory(simuOrder);
        }

        List<SimuOrderDetail> simuOrderDetailList = new ArrayList<>();

        // 不为空遍历
        for (Handicap handicap : handicapList) {
            // 根据盘口获取委托单
            SimuOrder limitOrder = simuOrderBO
                .getSimuOrder(handicap.getOrderCode());
            // 双委托单所有者为同一人
            if (simuOrder.getUserId().equals(limitOrder.getUserId())) {
                continue;
            }

            // 委托总量减去已成交量：委托剩余可交易量
            BigDecimal avilAmount = simuOrder.getTotalAmount()
                .subtract(simuOrder.getTradedAmount());
            if (avilAmount.compareTo(BigDecimal.ZERO) == 0) {
                break;
            }
            simuOrderDetailList
                .addAll(doMatchMarket(simuOrder, limitOrder, avilAmount));
        }

        doMatchMarketDone(simuOrder, simuOrderDetailList);

    }

    private List<SimuOrderDetail> doMatchMarket(SimuOrder marketOrder,
            SimuOrder limitOrder, BigDecimal avilAmount) {

        List<SimuOrderDetail> simuOrderDetailList = new ArrayList<>();

        // 获取盘口币种单位
        Coin asksCoin = coinBO.getCoin(limitOrder.getSymbol());

        // 盘口交易信息
        BigDecimal handicapPrice = limitOrder.getPrice();
        BigDecimal handicapCount = limitOrder.getTotalCount()
            .subtract(limitOrder.getTradedCount());
        BigDecimal handicapAmount = handicapPrice
            .multiply(CoinUtil.fromMinUnit(handicapCount, asksCoin.getUnit()));

        // 当前盘口的交易量是否能满足
        if (handicapAmount.compareTo(avilAmount) > 0) {
            // 计算交易数量
            BigDecimal tradeCount = avilAmount.divide(handicapPrice, 4,
                BigDecimal.ROUND_DOWN);

            // 当前委托单完全成交，新增当前委托单交易明细单
            SimuOrderDetail marketDetail = doOrderMatch(marketOrder,
                handicapPrice, tradeCount, avilAmount,
                ESimuOrderStatus.ENTIRE_DEAL.getCode());

            simuOrderDetailList.add(marketDetail);

            // 盘口部分成交，新增盘口单交易明细单
            SimuOrderDetail handicapDetail = doHandicapOrderMatch(limitOrder,
                handicapPrice, tradeCount, avilAmount,
                ESimuOrderStatus.PART_DEAL.getCode());

            // 落地撮合结果
            simuMatchResultBO.doSimuMatchResult(marketDetail, handicapDetail);

        } else if (handicapAmount.compareTo(avilAmount) == 0) {
            // 当前委托单完全成交，新增当前委托单交易明细单
            SimuOrderDetail marketDetail = doOrderMatch(marketOrder,
                handicapPrice, handicapCount, handicapAmount,
                ESimuOrderStatus.ENTIRE_DEAL.getCode());

            simuOrderDetailList.add(marketDetail);

            // 盘口完全成交，新增盘口单交易明细单
            SimuOrderDetail handicapDetail = doHandicapOrderMatch(limitOrder,
                handicapPrice, handicapCount, handicapAmount,
                ESimuOrderStatus.ENTIRE_DEAL.getCode());

            // 落地撮合结果
            simuMatchResultBO.doSimuMatchResult(marketDetail, handicapDetail);

        } else {
            // 当前委托单部分成交，新增当前委托单交易明细单
            SimuOrderDetail marketDetail = doOrderMatch(marketOrder,
                handicapPrice, handicapCount, handicapAmount,
                ESimuOrderStatus.PART_DEAL.getCode());

            simuOrderDetailList.add(marketDetail);

            // 盘口完全成交，新增盘口单交易明细单
            SimuOrderDetail handicapDetail = doHandicapOrderMatch(limitOrder,
                handicapPrice, handicapCount, handicapAmount,
                ESimuOrderStatus.ENTIRE_DEAL.getCode());

            // 落地撮合结果
            simuMatchResultBO.doSimuMatchResult(marketDetail, handicapDetail);

        }

        return simuOrderDetailList;

    }

    /**
     * 市价单撮合对向盘口没数据，直接进历史
     * @param marketOrder 
     * @create: 2018年9月8日 下午1:54:31 lei
     * @history:
     */
    private void doEmptyIntoHistory(SimuOrder marketOrder) {
        // 此委托单无法成交
        marketOrder.setAvgPrice(BigDecimal.ZERO);
        marketOrder.setTradedCount(BigDecimal.ZERO);
        marketOrder.setTradedAmount(BigDecimal.ZERO);
        marketOrder.setStatus(ESimuOrderStatus.CANCEL.getCode());
        marketOrder.setCancelDatetime(new Date());
        moveToHistory(marketOrder);
    }

    /**
     * 市价单撮合一次后进历史
     * @param simuOrder 
     * @create: 2018年9月8日 下午1:54:31 lei
     * @history:
     */
    private void doMatchMarketDone(SimuOrder simuOrder,
            List<SimuOrderDetail> SimuOrderDetailList) {

        BigDecimal totalTradeAmount = simuOrder.getTradedAmount();
        BigDecimal totalTradeCount = simuOrder.getTradedCount();

        for (SimuOrderDetail simuOrderDetail : SimuOrderDetailList) {
            totalTradeAmount = totalTradeAmount
                .add(simuOrderDetail.getTradedAmount());

            totalTradeCount = totalTradeCount
                .add(simuOrderDetail.getTradedCount());
        }

        simuOrder.setTradedCount(totalTradeCount);
        simuOrder.setTradedAmount(totalTradeAmount);
        // 交易手续费
        simuOrder.setTradedFee(simuOrder.getTradedFee()
            .add(getFee(simuOrder.getUserId(), simuOrder.getDirection(),
                totalTradeCount.subtract(simuOrder.getTradedCount()),
                totalTradeAmount.subtract(simuOrder.getTradedAmount()))));

        // 计算成交均价
        BigDecimal avgPrice = totalTradeAmount.divide(totalTradeCount, 4,
            BigDecimal.ROUND_DOWN);
        // 装填委托单信息并转入历史委托单
        simuOrder.setAvgPrice(avgPrice);
        simuOrder.setLastTradedDatetime(new Date());

        // 市价单根据Amount判断状态，限价单根据Count判断状态
        if (ESimuOrderType.MARKET.getCode().equals(simuOrder.getDirection())) {

            if (simuOrder.getTotalAmount().compareTo(totalTradeAmount) > 0) {
                // 部分成交
                simuOrder.setStatus(ESimuOrderStatus.PART_DEAL.getCode());
                simuOrderBO.refreshMarketSimuOrder(simuOrder);
            } else {
                // 完全成交, 放入历史委托单
                simuOrder.setStatus(ESimuOrderStatus.ENTIRE_DEAL.getCode());
                moveToHistory(simuOrder);
            }

        } else {

            if (simuOrder.getTotalCount().compareTo(totalTradeCount) > 0) {
                // 部分成交
                simuOrder.setStatus(ESimuOrderStatus.PART_DEAL.getCode());
                simuOrderBO.refreshLimitSimuOrder(simuOrder);
            } else {
                // 完全成交,放入历史委托单
                simuOrder.setStatus(ESimuOrderStatus.ENTIRE_DEAL.getCode());
                moveToHistory(simuOrder);
            }
        }
    }

    /**
     * 处理委托单撮合结果
     * @param simuOrder
     * @param handicap
     * @param tradedCount
     * @param tradedAmount 
     * @create: 2018年8月31日 下午5:12:47 lei
     * @history:
     */
    private SimuOrderDetail doOrderMatch(SimuOrder simuOrder,
            BigDecimal tradedPrice, BigDecimal tradedCount,
            BigDecimal tradedAmount, String status) {

        // 新增成交
        BigDecimal tradedFee = getFee(simuOrder.getUserId(),
            simuOrder.getDirection(), tradedCount, tradedAmount);
        SimuOrderDetail orderDetail = simuOrderDetailBO.saveSimuOrderDetail(
            simuOrder, tradedPrice, tradedCount, tradedAmount, tradedFee);

        return orderDetail;
    }

    /**
     * 处理盘口撮合结果
     * @param simuOrder
     * @param handicap
     * @param tradedCount
     * @param tradedAmount 
     * @create: 2018年8月31日 下午5:12:47 lei
     * @history:
     */
    private SimuOrderDetail doHandicapOrderMatch(SimuOrder simuOrder,
            BigDecimal tradedPrice, BigDecimal tradedCount,
            BigDecimal tradedAmount, String status) {

        // 新增成交
        BigDecimal tradedFee = getFee(simuOrder.getUserId(),
            simuOrder.getDirection(), tradedCount, tradedAmount);
        SimuOrderDetail orderDetail = simuOrderDetailBO.saveSimuOrderDetail(
            simuOrder, tradedPrice, tradedCount, tradedAmount, tradedFee);

        // 委托单是限价单时更新盘口信息
        if (ESimuOrderType.LIMIT.getCode().equals(simuOrder.getType())) {
            // 更新盘口交易信息
            updateHandicapTradeInfo(simuOrder.getCode(),
                simuOrder.getTotalCount().subtract(simuOrder.getTradedCount()),
                status);
        }

        return orderDetail;
    }

    /**
     * 更新委托单交易信息
     * @param simuOrder
     * @param tradedCount
     * @param tradedAmount
     * @param status 
     * @create: 2018年8月31日 下午5:08:32 lei
     * @history:
     */
    private void updateSimuOrderTradeInfo(SimuOrder simuOrder,
            BigDecimal tradedCount, BigDecimal tradedAmount, String status) {

    }

    /**
     * 根据委托单更新盘口交易信息
     * @param orderCode
     * @param tradedCount
     * @param status 
     * @create: 2018年8月31日 下午5:08:00 lei
     * @history:
     */
    private void updateHandicapTradeInfo(String orderCode,
            BigDecimal tradedCount, String status) {

        Handicap condition = new Handicap();
        condition.setOrderCode(orderCode);
        condition.setCount(tradedCount);

        if (ESimuOrderStatus.PART_DEAL.getCode().equals(status)) { // 部分成交

            // 更新盘口可交易量
            handicapBO.refreshHandicap(condition);

        } else if (ESimuOrderStatus.ENTIRE_DEAL.getCode().equals(status)) { // 完全成交

            // 删除盘口
            handicapBO.removeHandicap(condition.getOrderCode());
        }

    }

    /**
     * 将存活委托单移入历史委托单
     * @param data 
     * @create: 2018年8月31日 下午5:06:44 lei
     * @history:
     */
    private void moveToHistory(SimuOrder data) {
        // 增加历史委托
        simuOrderHistoryBO.saveSimuOrderHistory(data);

        // 撤销，并从 存活委托 中删除；
        simuOrderBO.cancel(data);
    }

    /**
     * 计算手续费
     * @param tradeAmount
     * @return 
     * @create: 2018年9月4日 下午8:16:58 lei
     * @history:
     */
    private BigDecimal getFee(String userId, String direction,
            BigDecimal symbolAmount, BigDecimal toSymbolAmount) {

        User user = userBO.getUser(userId);

        BigDecimal rate = sysConfigBO
            .getBigDecimalValue(SysConstants.SIMU_ORDER_FEE_RATE);

        BigDecimal fee = BigDecimal.ZERO;
        // 手续费收取：买单收取symbol，卖单收取toSymbol(得到什么币收取什么币)
        if (ESimuOrderDirection.BUY.getCode().equals(direction)) {
            fee = symbolAmount.multiply(rate);
        } else {
            fee = toSymbolAmount.multiply(rate);
        }

        return fee;
    }

}
