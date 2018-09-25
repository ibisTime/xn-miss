package com.ogc.standard.bo.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ogc.standard.bo.ITradeOrderBO;
import com.ogc.standard.bo.base.PaginableBOImpl;
import com.ogc.standard.common.DateUtil;
import com.ogc.standard.core.OrderNoGenerater;
import com.ogc.standard.dao.ITradeOrderDAO;
import com.ogc.standard.domain.Ads;
import com.ogc.standard.domain.TradeOrder;
import com.ogc.standard.domain.UserStatistics;
import com.ogc.standard.enums.ECommentLevel;
import com.ogc.standard.enums.EErrorCode_main;
import com.ogc.standard.enums.EGeneratePrefix;
import com.ogc.standard.enums.ETradeOrderStatus;
import com.ogc.standard.enums.ETradeOrderType;
import com.ogc.standard.exception.BizException;

@Component
public class TradeOrderBOImpl extends PaginableBOImpl<TradeOrder>
        implements ITradeOrderBO {

    @Autowired
    private ITradeOrderDAO tradeOrderDAO;

    @Override
    public String contactBuySubmit(Ads ads, String buyUser) {
        TradeOrder data = new TradeOrder();

        String code = OrderNoGenerater
            .generate(EGeneratePrefix.TRADE_ORDER.getCode());
        Date now = new Date();

        data.setCode(code);
        data.setType(ETradeOrderType.BUY.getCode());
        data.setAdsCode(ads.getCode());
        data.setBuyUser(buyUser);
        data.setSellUser(ads.getUserId());

        // data.setLeaveMessage(ads.getLeaveMessage());
        data.setTradeCurrency(ads.getTradeCurrency());
        data.setTradeCoin(ads.getTradeCoin());
        // data.setTradePrice(tradePrice);
        // data.setTradeAmount(tradeAmount);
        //
        // data.setFee(fee);
        // data.setCount(count);
        // data.setPayType(ads.getPayType());
        // data.setInvalidDatetime(DateUtil.getRelativeDateOfMinute(now,
        // ads.getPayLimit()));

        data.setStatus(ETradeOrderStatus.TO_SUBMIT.getCode());
        data.setCreateDatetime(now);
        data.setUpdater(buyUser);
        data.setUpdateDatetime(now);
        data.setRemark("开始聊天，等待下单");

        tradeOrderDAO.insert(data);

        return code;
    }

    @Override
    public String contactSellSubmit(Ads ads, String sellUser) {
        TradeOrder data = new TradeOrder();
        String code = OrderNoGenerater
            .generate(EGeneratePrefix.TRADE_ORDER.getCode());
        Date now = new Date();
        data.setCode(code);
        data.setType(ETradeOrderType.SELL.getCode());
        data.setAdsCode(ads.getCode());
        data.setBuyUser(ads.getUserId());
        data.setSellUser(sellUser);

        // data.setLeaveMessage(ads.getLeaveMessage());
        data.setTradeCurrency(ads.getTradeCurrency());
        data.setTradeCoin(ads.getTradeCoin());
        // data.setTradePrice(tradePrice);
        // data.setTradeAmount(tradeAmount);
        // data.setFee(fee);
        // data.setCount(count);
        // data.setPayType(ads.getPayType());
        // data.setInvalidDatetime(DateUtil.getRelativeDateOfMinute(now,
        // ads.getPayLimit()));

        data.setStatus(ETradeOrderStatus.TO_SUBMIT.getCode());
        data.setCreateDatetime(now);
        data.setUpdater(sellUser);
        data.setUpdateDatetime(now);
        data.setRemark("开始聊天，等待下单");
        tradeOrderDAO.insert(data);

        return code;
    }

    @Override
    public TradeOrder buySubmit(Ads ads, String buyUser, BigDecimal tradePrice,
            BigDecimal count, BigDecimal tradeAmount, BigDecimal fee) {

        TradeOrder data = new TradeOrder();

        String code = OrderNoGenerater
            .generate(EGeneratePrefix.TRADE_ORDER.getCode());
        Date now = new Date();

        data.setCode(code);
        data.setType(ETradeOrderType.BUY.getCode());
        data.setAdsCode(ads.getCode());
        data.setBuyUser(buyUser);
        data.setSellUser(ads.getUserId());

        data.setLeaveMessage(ads.getLeaveMessage());
        data.setTradeCurrency(ads.getTradeCurrency());
        data.setTradeCoin(ads.getTradeCoin());
        data.setTradePrice(tradePrice);
        data.setTradeAmount(tradeAmount);

        data.setFee(fee);
        data.setCount(count);
        data.setPayType(ads.getPayType());
        data.setInvalidDatetime(
            DateUtil.getRelativeDateOfMinute(now, ads.getPayLimit()));
        data.setStatus(ETradeOrderStatus.TO_PAY.getCode());

        data.setCreateDatetime(now);
        data.setUpdater(buyUser);
        data.setUpdateDatetime(now);
        data.setRemark("直接订单，等待支付");

        tradeOrderDAO.insert(data);

        return data;
    }

    @Override
    public TradeOrder sellSubmit(Ads ads, String sellUser,
            BigDecimal tradePrice, BigDecimal count, BigDecimal tradeAmount,
            BigDecimal fee) {

        TradeOrder data = new TradeOrder();
        String code = OrderNoGenerater
            .generate(EGeneratePrefix.TRADE_ORDER.getCode());
        Date now = new Date();
        data.setCode(code);
        data.setType(ETradeOrderType.SELL.getCode());
        data.setAdsCode(ads.getCode());
        data.setBuyUser(ads.getUserId());
        data.setSellUser(sellUser);

        data.setLeaveMessage(ads.getLeaveMessage());
        data.setTradeCurrency(ads.getTradeCurrency());
        data.setTradeCoin(ads.getTradeCoin());
        data.setTradePrice(tradePrice);
        data.setTradeAmount(tradeAmount);

        data.setFee(fee);
        data.setCount(count);
        data.setPayType(ads.getPayType());
        data.setInvalidDatetime(
            DateUtil.getRelativeDateOfMinute(now, ads.getPayLimit()));
        data.setStatus(ETradeOrderStatus.TO_PAY.getCode());

        data.setCreateDatetime(now);
        data.setUpdater(sellUser);
        data.setUpdateDatetime(now);
        data.setRemark("直接订单，等待支付");
        tradeOrderDAO.insert(data);

        return data;
    }

    @Override
    public void buyRefresh(TradeOrder data, Ads ads, BigDecimal tradePrice,
            BigDecimal count, BigDecimal tradeAmount, BigDecimal fee) {

        Date now = new Date();

        data.setLeaveMessage(ads.getLeaveMessage());
        data.setTradeCurrency(ads.getTradeCurrency());
        data.setTradeCoin(ads.getTradeCoin());
        data.setTradePrice(tradePrice);
        data.setTradeAmount(tradeAmount);

        data.setFee(fee);
        data.setCount(count);
        data.setPayType(ads.getPayType());
        data.setInvalidDatetime(
            DateUtil.getRelativeDateOfMinute(now, ads.getPayLimit()));
        data.setStatus(ETradeOrderStatus.TO_PAY.getCode());

        data.setUpdater(data.getBuyUser());
        data.setUpdateDatetime(now);
        data.setRemark("聊天后下单，等待支付");

        tradeOrderDAO.updateSubmit(data);
    }

    @Override
    public void sellRefresh(TradeOrder data, Ads ads, BigDecimal tradePrice,
            BigDecimal count, BigDecimal tradeAmount, BigDecimal fee) {

        Date now = new Date();

        data.setLeaveMessage(ads.getLeaveMessage());
        data.setTradeCurrency(ads.getTradeCurrency());
        data.setTradeCoin(ads.getTradeCoin());
        data.setTradePrice(tradePrice);
        data.setTradeAmount(tradeAmount);

        data.setFee(fee);
        data.setCount(count);
        data.setPayType(ads.getPayType());
        data.setInvalidDatetime(
            DateUtil.getRelativeDateOfMinute(now, ads.getPayLimit()));
        data.setStatus(ETradeOrderStatus.TO_PAY.getCode());

        data.setUpdater(data.getSellUser());
        data.setUpdateDatetime(now);
        data.setRemark("聊天后下单，等待支付");

        tradeOrderDAO.updateSubmit(data);
    }

    @Override
    public int cancel(TradeOrder tradeOrder, String updater, String remark) {
        int count = 0;
        if (tradeOrder != null) {
            tradeOrder.setStatus(ETradeOrderStatus.CANCEL.getCode());
            tradeOrder.setUpdater(updater);
            tradeOrder.setUpdateDatetime(new Date());
            tradeOrder
                .setRemark(StringUtils.isNotBlank(remark) ? remark : "订单已被取消");
            count = tradeOrderDAO.updateCancel(tradeOrder);
        }
        return count;
    }

    @Override
    public int removeTradeOrder(TradeOrder data) {
        return tradeOrderDAO.delete(data);
    }

    @Override
    public int markPay(TradeOrder tradeOrder, String updater, String remark) {
        int count = 0;
        if (tradeOrder != null) {
            Date now = new Date();
            tradeOrder.setStatus(ETradeOrderStatus.PAYED.getCode());
            tradeOrder.setMarkDatetime(now);
            tradeOrder.setUpdater(updater);
            tradeOrder.setUpdateDatetime(now);
            tradeOrder.setRemark(
                StringUtils.isNotBlank(remark) ? remark : "已标记付款，待释放");
            count = tradeOrderDAO.updateMarkPay(tradeOrder);
        }
        return count;
    }

    @Override
    public int release(TradeOrder tradeOrder, String updater, String remark) {
        int count = 0;
        if (tradeOrder != null) {
            Date now = new Date();
            tradeOrder.setStatus(ETradeOrderStatus.RELEASED.getCode());
            tradeOrder.setReleaseDatetime(now);
            tradeOrder.setUpdater(updater);
            tradeOrder.setUpdateDatetime(now);
            tradeOrder
                .setRemark(StringUtils.isNotBlank(remark) ? remark : "已释放，待评价");
            count = tradeOrderDAO.updateRelease(tradeOrder);
        }
        return count;
    }

    @Override
    public int doBsComment(TradeOrder tradeOrder, String userId, String comment,
            String status, String remark) {
        int count = 0;
        if (tradeOrder != null) {
            Date now = new Date();
            tradeOrder.setStatus(status);
            tradeOrder.setBsComment(comment);
            tradeOrder.setUpdater(userId);
            tradeOrder.setUpdateDatetime(now);
            tradeOrder.setRemark(remark);
            count = tradeOrderDAO.updateBsComment(tradeOrder);
        }
        return count;
    }

    @Override
    public int doSbComment(TradeOrder tradeOrder, String userId, String comment,
            String status, String remark) {
        int count = 0;
        if (tradeOrder != null) {
            Date now = new Date();
            tradeOrder.setStatus(status);
            tradeOrder.setSbComment(comment);
            tradeOrder.setUpdater(userId);
            tradeOrder.setUpdateDatetime(now);
            tradeOrder.setRemark(remark);
            count = tradeOrderDAO.updateSbComment(tradeOrder);
        }
        return count;
    }

    @Override
    public int applyArbitrate(TradeOrder tradeOrder, String applyUser) {
        int count = 0;
        if (tradeOrder != null) {
            Date now = new Date();
            tradeOrder.setStatus(ETradeOrderStatus.ARBITRATE.getCode());
            tradeOrder.setUpdater(applyUser);
            tradeOrder.setUpdateDatetime(now);
            tradeOrder.setRemark("申请仲裁，待处理");
            count = tradeOrderDAO.updateArbitrate(tradeOrder);
        }
        return count;
    }

    @Override
    public int revokePay(TradeOrder tradeOrder, String updater, String remark) {
        int count = 0;
        if (tradeOrder != null) {
            Date now = new Date();
            tradeOrder.setStatus(ETradeOrderStatus.TO_PAY.getCode());
            tradeOrder.setUpdater(updater);
            tradeOrder.setUpdateDatetime(now);
            tradeOrder.setRemark(remark);
            count = tradeOrderDAO.updateRevokePay(tradeOrder);
        }
        return count;
    }

    @Override
    public int arbitrateUnPass(TradeOrder tradeOrder) {

        tradeOrder.setStatus(ETradeOrderStatus.PAYED.getCode());
        // tradeOrder.setRemark("仲裁不通过");
        return this.tradeOrderDAO.updateArbitrate(tradeOrder);

    }

    @Override
    public List<TradeOrder> queryTradeOrderList(TradeOrder condition) {
        return tradeOrderDAO.selectList(condition);
    }

    @Override
    public TradeOrder getTradeOrder(String code) {
        TradeOrder data = null;
        if (StringUtils.isNotBlank(code)) {
            TradeOrder condition = new TradeOrder();
            condition.setCode(code);
            data = tradeOrderDAO.select(condition);
            if (data == null) {
                throw new BizException(EErrorCode_main.code_NOTEXIST.getCode());
            }
        }
        return data;
    }

    @Override
    public TradeOrder getToSubmitTradeOrder(String buyUser, String sellUser,
            String adsCode) {
        TradeOrder tradeOrder = null;
        TradeOrder condition = new TradeOrder();
        condition.setBuyUser(buyUser);
        condition.setSellUser(sellUser);
        condition.setAdsCode(adsCode);
        condition.setStatus(ETradeOrderStatus.TO_SUBMIT.getCode());
        List<TradeOrder> results = tradeOrderDAO.selectList(condition);
        if (CollectionUtils.isNotEmpty(results)) {
            tradeOrder = results.get(0);
        }
        return tradeOrder;
    }

    @Override
    public boolean isExistOningOrder(String adsCode) {
        boolean flag = false;
        // 检查该广告是否有未完成的订单
        List<String> statusList = new ArrayList<String>();
        statusList.add(ETradeOrderStatus.TO_PAY.getCode());
        statusList.add(ETradeOrderStatus.PAYED.getCode());
        statusList.add(ETradeOrderStatus.ARBITRATE.getCode());
        TradeOrder condition = new TradeOrder();
        condition.setAdsCode(adsCode);
        condition.setStatusList(statusList);
        if (this.getTotalCount(condition) > 0) {
            flag = true;
        }
        return flag;
    }

    // 释放后都算已完成
    @Override
    public boolean checkUserHasUnFinishOrder(String userId,
            ETradeOrderType tradeOrderType, String tradeCoin) {

        TradeOrder condition = new TradeOrder();
        condition.setType(tradeOrderType.getCode());
        condition.setBuyUser(userId);
        condition.setTradeCoin(tradeCoin);

        List<String> statusList = new ArrayList<>();
        statusList.add(ETradeOrderStatus.TO_PAY.getCode());
        statusList.add(ETradeOrderStatus.PAYED.getCode());
        statusList.add(ETradeOrderStatus.ARBITRATE.getCode());
        condition.setStatusList(statusList);
        long count = this.tradeOrderDAO.selectTotalCount(condition);
        return count <= 0;
    }

    private List<String> finishReleaseStatusList() {

        List<String> statusList = new ArrayList<String>();
        statusList.add(ETradeOrderStatus.RELEASED.getCode());
        statusList.add(ETradeOrderStatus.COMPLETE.getCode());
        return statusList;
    }

    @Override
    public UserStatistics obtainUserStatistics(String userId, String currency) {

        //
        int finishReleaseTradeCount = 0;

        // 被评价次数
        int beiPingJiaCount = 0;

        // 好评次数
        int beiHaoPingCount = 0;

        TradeOrder condition = new TradeOrder();
        // userId 为卖家或者买家
        condition.setBelongUser(userId);
        condition.setTradeCoin(currency);
        // 已释放都算 "完成"
        condition.setStatusList(this.finishReleaseStatusList());

        List<TradeOrder> orders = this.tradeOrderDAO.selectList(condition);

        finishReleaseTradeCount = orders.size();
        for (TradeOrder order : orders) {

            if (order.getSellUser().equals(userId)
                    && order.getBsComment() != null) {
                beiPingJiaCount++;
                if (order.getBsComment()
                    .equals(ECommentLevel.HAO_PING.getCode())) {
                    beiHaoPingCount++;
                }
            }

            if (order.getBuyUser().equals(userId)
                    && order.getSbComment() != null) {
                beiPingJiaCount++;

                if (order.getSbComment()
                    .equals(ECommentLevel.HAO_PING.getCode())) {
                    beiHaoPingCount++;
                }

            }

        }

        UserStatistics userStatistics = new UserStatistics();
        userStatistics.setJiaoYiCount(finishReleaseTradeCount);
        userStatistics.setBeiPingJiaCount(beiPingJiaCount);
        userStatistics.setBeiHaoPingCount(beiHaoPingCount);

        return userStatistics;

    }

    public BigDecimal getUserTotalTradeCount(String userId, String tradeCoin) {

        TradeOrder tradeOrder = new TradeOrder();
        tradeOrder.setUserId(userId);
        // 只查询 释放的 和 已完成的
        tradeOrder
            .setStatusList(Arrays.asList(ETradeOrderStatus.RELEASED.getCode(),
                ETradeOrderStatus.COMPLETE.getCode()));
        tradeOrder.setTradeCoin(tradeCoin);
        return this.tradeOrderDAO.selectedTotalTradeCount(tradeOrder);

    }

    @Override
    public long getTradeTimesBetweenUser(String user1, String user2) {

        List<String> statusList = this.finishReleaseStatusList();

        //
        TradeOrder con1 = new TradeOrder();
        con1.setSellUser(user1);
        con1.setBuyUser(user2);
        con1.setStatusList(statusList);
        long count1 = this.tradeOrderDAO.selectTotalCount(con1);

        //
        TradeOrder con2 = new TradeOrder();
        con2.setSellUser(user2);
        con2.setBuyUser(user1);
        con2.setStatusList(statusList);
        long count2 = this.tradeOrderDAO.selectTotalCount(con2);

        return count1 + count2;

    }

    @Override
    public void removeDaiXiaDanOrders(String adsCode) {

        TradeOrder condition = new TradeOrder();
        condition.setStatus(ETradeOrderStatus.TO_SUBMIT.getCode());
        condition.setAdsCode(adsCode);
        this.tradeOrderDAO.deleteByAdsCodeAndStatus(condition);
    }

    @Override
    public BigDecimal count(TradeOrder condition) {
        List<TradeOrder> orderList = this.queryTradeOrderList(condition);
        BigDecimal count = BigDecimal.ZERO;
        for (TradeOrder tradeOrder : orderList) {
            count = count.add(tradeOrder.getCount());
        }
        return count;
    }
}
