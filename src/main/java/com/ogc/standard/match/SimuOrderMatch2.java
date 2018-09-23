package com.ogc.standard.match;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ogc.standard.bo.IHandicapBO;
import com.ogc.standard.bo.ISimuMatchResultBO;
import com.ogc.standard.bo.ISimuOrderBO;
import com.ogc.standard.bo.ISimuOrderDetailBO;
import com.ogc.standard.bo.ISimuOrderHistoryBO;
import com.ogc.standard.domain.Handicap;
import com.ogc.standard.domain.HandicapGrade;
import com.ogc.standard.domain.SimuOrder;
import com.ogc.standard.domain.SimuOrderDetail;
import com.ogc.standard.enums.ESimuOrderDirection;
import com.ogc.standard.enums.ESimuOrderStatus;
import com.ogc.standard.enums.ESimuOrderType;

/**
 * 币币交易撮合类
 * @author: lei 
 * @since: 2018年8月31日 下午4:50:02 
 * @history:
 */
@Service
public class SimuOrderMatch2 {

    @Autowired
    private ISimuOrderBO simuOrderBO;

    @Autowired
    private ISimuOrderDetailBO simuOrderDetailBO;

    @Autowired
    private ISimuOrderHistoryBO simuOrderHistoryBO;

    @Autowired
    private IHandicapBO handicapBO;

    @Autowired
    private ISimuMatchResultBO simuMatchResultBO;

    public void match(SimuOrder simuOrder) {

        if (ESimuOrderType.MARKET.getCode().equals(simuOrder.getType())) {
            doMatchMarket(simuOrder);
        } else {
            doMatchLimit(simuOrder);
        }

    }

    private void doMatchMarket(SimuOrder simuOrder) {

        String direction;
        if (ESimuOrderDirection.BUY.getCode()
            .equals(simuOrder.getDirection())) {
            direction = ESimuOrderDirection.SELL.getCode();
        } else {
            direction = ESimuOrderDirection.BUY.getCode();
        }

        // 获取对方盘口档位
        List<HandicapGrade> handicapGrades = handicapBO.queryHandicapGradeList(
            simuOrder.getSymbol(), simuOrder.getToSymbol(), direction);

        // 直接与可盘口档位匹配，成交
        doMatchOrder(simuOrder, handicapGrades);

    }

    private void doMatchLimit(SimuOrder simuOrder) {

        if (ESimuOrderDirection.BUY.getCode()
            .equals(simuOrder.getDirection())) {
            doMatchLimitBids(simuOrder);
        } else {
            doMatchLimitAsds(simuOrder);
        }

    }

    // 撮合限价买单
    private void doMatchLimitBids(SimuOrder simuOrder) {

        // 获取卖盘盘口档位
        List<HandicapGrade> handicapGrades = handicapBO.queryHandicapGradeList(
            simuOrder.getSymbol(), simuOrder.getToSymbol(),
            ESimuOrderDirection.SELL.getCode());

        // 当卖方盘口不存在，当前限价买单无法撮合成交，则 进入买方盘口
        if (CollectionUtils.isEmpty(handicapGrades)) {
            handicapBO.saveHandicap(simuOrder);
        }

        // 获取卖方盘口最低档位价格
        BigDecimal asdsLowest = handicapGrades.get(0).getPrice();

        // 当前限价买单价格 低于 卖盘最低档位价格，不可成交则进入盘口，反之则可以直接成交
        if (simuOrder.getPrice().compareTo(asdsLowest) < 0) {
            handicapBO.saveHandicap(simuOrder);
        }

        // 根据当前委托单价格筛选 在可成交范围内的 档位价格
        List<HandicapGrade> usableGrades = new ArrayList<>();
        for (HandicapGrade handicapGrade : handicapGrades) {

            if (simuOrder.getPrice().compareTo(handicapGrade.getPrice()) < 0) {
                usableGrades.add(handicapGrade);
            }
        }

        // 直接与可成交卖盘口档位匹配，成交
        doMatchOrder(simuOrder, usableGrades);

    }

    // 撮合限价卖单
    private void doMatchLimitAsds(SimuOrder simuOrder) {

        // 获取买盘盘口档位
        List<HandicapGrade> handicapGrades = handicapBO.queryHandicapGradeList(
            simuOrder.getSymbol(), simuOrder.getToSymbol(),
            ESimuOrderDirection.BUY.getCode());

        // 当买方盘口不存在，当前限价卖单无法撮合成交，则 进入卖方盘口
        if (CollectionUtils.isEmpty(handicapGrades)) {
            handicapBO.saveHandicap(simuOrder);
        }

        // 获取买方盘口最高档位价格
        BigDecimal bidsLowest = handicapGrades.get(0).getPrice();

        // 当前限价买单价格 高于 买盘最高档位价格，不可成交则进入盘口，反之则可以直接成交
        if (simuOrder.getPrice().compareTo(bidsLowest) > 0) {
            handicapBO.saveHandicap(simuOrder);
            return;
        }

        // 根据当前委托单价格筛选 在可成交范围内的 档位价格
        List<HandicapGrade> usableGrades = new ArrayList<>();
        for (HandicapGrade handicapGrade : handicapGrades) {

            if (simuOrder.getPrice().compareTo(handicapGrade.getPrice()) > 0) {
                usableGrades.add(handicapGrade);
            }
        }

        // 直接与可成交买盘口档位匹配，成交
        doMatchOrder(simuOrder, usableGrades);
    }

    private void doMatchOrder(SimuOrder simuOrder,
            List<HandicapGrade> handicapGrades) {

        for (HandicapGrade handicapGrade : handicapGrades) {

            for (Handicap handicap : handicapGrade.getHandicapList()) {

                // 根据对方盘口获取委托单
                SimuOrder oppositeOrder = simuOrderBO
                    .getSimuOrderCheck(handicap.getOrderCode());

                // 双委托单所有者为同一人
                if (simuOrder.getUserId().equals(oppositeOrder.getUserId())) {
                    continue;
                }

                doMatchResult(simuOrder, oppositeOrder);

            }

        }

        // 当前市价委托单已 撮合 完所有的对方盘口
        if (ESimuOrderType.MARKET.getCode().equals(simuOrder.getType())) {
            doMarchMarketDone(simuOrder);
        }
    }

    private void doMatchResult(SimuOrder simuOrder, SimuOrder oppositeOrder) {

        // 我方盘口剩余可交易金额
        BigDecimal avilAmount = countTradeAmount(simuOrder);
        // 我方盘口剩余可交易数量
        BigDecimal avilCount = countTradeCount(simuOrder);

        // 对方盘口剩余可交易金额
        BigDecimal aviloppositeAmount = countTradeAmount(oppositeOrder);
        // 对方盘口剩余可交易数量
        BigDecimal aviloppositeCount = countTradeCount(oppositeOrder);

        // 我方盘交易金额能否被满足，成交价均以对方盘口为准
        if (avilAmount.compareTo(aviloppositeAmount) > 0) {

            // 我方盘单完全成交
            SimuOrderDetail bidsDetail = doOrderMatch(simuOrder,
                oppositeOrder.getPrice(), avilCount, avilAmount,
                ESimuOrderStatus.ENTIRE_DEAL.getCode());

            // 卖盘单部分成交
            SimuOrderDetail asksDetail = doOrderMatch(oppositeOrder,
                oppositeOrder.getPrice(), avilCount, avilAmount,
                ESimuOrderStatus.PART_DEAL.getCode());

            // 落地撮合结果
            simuMatchResultBO.doSimuMatchResult(bidsDetail, asksDetail);

        } else if (avilAmount.compareTo(aviloppositeAmount) == 0) {

            // 买盘单完全成交
            SimuOrderDetail bidsDetail = doOrderMatch(simuOrder,
                oppositeOrder.getPrice(), avilCount, avilAmount,
                ESimuOrderStatus.ENTIRE_DEAL.getCode());

            // 卖盘单完全成交
            SimuOrderDetail asksDetail = doOrderMatch(oppositeOrder,
                oppositeOrder.getPrice(), aviloppositeCount, aviloppositeAmount,
                ESimuOrderStatus.ENTIRE_DEAL.getCode());

            // 落地撮合结果
            simuMatchResultBO.doSimuMatchResult(bidsDetail, asksDetail);

        } else {

            // 买盘单部分成交
            SimuOrderDetail bidsDetail = doOrderMatch(simuOrder,
                oppositeOrder.getPrice(), avilCount, avilAmount,
                ESimuOrderStatus.PART_DEAL.getCode());

            // 卖盘单完全成交
            SimuOrderDetail asksDetail = doOrderMatch(oppositeOrder,
                oppositeOrder.getPrice(), aviloppositeCount, aviloppositeAmount,
                ESimuOrderStatus.ENTIRE_DEAL.getCode());

            // 落地撮合结果
            simuMatchResultBO.doSimuMatchResult(bidsDetail, asksDetail);

        }

    }

    private BigDecimal countTradeCount(SimuOrder simuOrder) {
        return simuOrder.getTotalCount().subtract(simuOrder.getTradedCount());
    }

    private BigDecimal countTradeAmount(SimuOrder simuOrder) {
        return simuOrder.getTotalAmount().subtract(simuOrder.getTradedAmount());
    }

    /**
     * 市价单撮合结束后处理
     * @param marketOrder 
     * @create: 2018年9月8日 下午1:54:31 lei
     * @history:
     */
    private void doMarchMarketDone(SimuOrder marketOrder) {
        // 获取当前市价委托单的所有成交明细
        SimuOrderDetail condition = new SimuOrderDetail();
        condition.setOrderCode(marketOrder.getCode());
        List<SimuOrderDetail> simuOrderDetails = simuOrderDetailBO
            .querySimuOrderDetailList(condition);

        if (CollectionUtils.isEmpty(simuOrderDetails)) {

            // 此委托单无法成交
            marketOrder.setAvgPrice(BigDecimal.ZERO);
            marketOrder.setTradedCount(BigDecimal.ZERO);
            marketOrder.setTradedAmount(BigDecimal.ZERO);
            marketOrder.setStatus(ESimuOrderStatus.CANCEL.getCode());
            marketOrder.setCancelDatetime(new Date());

        } else {

            // 比较所有明细交易金额相加 和 当前委托单的委托金额，小于则为部分成交，等于则为全部成交
            BigDecimal totalTradeAmount = BigDecimal.ZERO;

            // 成交总数量
            BigDecimal totalTradeCount = BigDecimal.ZERO;

            for (SimuOrderDetail simuOrderDetail : simuOrderDetails) {

                totalTradeAmount = totalTradeAmount
                    .add(simuOrderDetail.getTradedAmount());

                totalTradeCount = totalTradeCount
                    .add(simuOrderDetail.getTradedCount());

            }

            // 计算成交均价
            BigDecimal avgPrice = totalTradeAmount.divide(totalTradeCount, 4,
                BigDecimal.ROUND_DOWN);

            // 装填委托单信息并转入历史委托单
            marketOrder.setAvgPrice(avgPrice);
            marketOrder.setTradedCount(totalTradeCount);
            marketOrder.setTradedAmount(totalTradeAmount);

        }

        moveToHistory(marketOrder);
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

        // 新增成交单并更新委托单
        BigDecimal tradedFee = getFee(tradedAmount);
        SimuOrderDetail orderDetail = simuOrderDetailBO.saveSimuOrderDetail(
            simuOrder, tradedPrice, tradedCount, tradedAmount, tradedFee);

        // 更新委托单交易信息
        updateSimuOrderTradeInfo(simuOrder, tradedCount, tradedAmount, status);

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

        simuOrder.setAvgPrice(simuOrder.getPrice());
        simuOrder.setLastTradedDatetime(new Date());
        simuOrder.setStatus(status);

        if (ESimuOrderStatus.PART_DEAL.getCode().equals(status)) { // 部分成交

            simuOrder
                .setTradedCount(simuOrder.getTradedCount().add(tradedCount));
            simuOrder
                .setTradedAmount(simuOrder.getTradedAmount().add(tradedAmount));
            // 交易手续费
            simuOrder.setTradedFee(
                simuOrder.getTradedFee().add(getFee(tradedAmount)));

            simuOrderBO.refreshLimitSimuOrder(simuOrder);

        } else if (ESimuOrderStatus.ENTIRE_DEAL.getCode().equals(status)) { // 完全成交

            simuOrder.setTradedCount(tradedCount);
            simuOrder.setTradedAmount(tradedAmount);
            // 交易手续费
            simuOrder.setTradedFee(getFee(tradedAmount));

            // 放入历史委托单
            moveToHistory(simuOrder);
        }

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
    private BigDecimal getFee(BigDecimal tradeAmount) {

        BigDecimal fee = BigDecimal.ZERO;
        fee = tradeAmount.multiply(BigDecimal.ONE);

        return fee;
    }

}
