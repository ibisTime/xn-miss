package com.ogc.standard.ao.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ogc.standard.ao.ITradeOrderAO;
import com.ogc.standard.bo.IAccountBO;
import com.ogc.standard.bo.IAdsBO;
import com.ogc.standard.bo.IArbitrateBO;
import com.ogc.standard.bo.IAwardBO;
import com.ogc.standard.bo.IBlacklistBO;
import com.ogc.standard.bo.IJourBO;
import com.ogc.standard.bo.ISYSDictBO;
import com.ogc.standard.bo.ISmsOutBO;
import com.ogc.standard.bo.ITencentBO;
import com.ogc.standard.bo.ITradeOrderBO;
import com.ogc.standard.bo.IUserBO;
import com.ogc.standard.bo.IUserRelationBO;
import com.ogc.standard.bo.IUserSettingsBO;
import com.ogc.standard.bo.base.Paginable;
import com.ogc.standard.common.SysConstants;
import com.ogc.standard.domain.Account;
import com.ogc.standard.domain.Ads;
import com.ogc.standard.domain.Arbitrate;
import com.ogc.standard.domain.Award;
import com.ogc.standard.domain.Jour;
import com.ogc.standard.domain.SYSDict;
import com.ogc.standard.domain.TradeOrder;
import com.ogc.standard.domain.User;
import com.ogc.standard.domain.UserStatistics;
import com.ogc.standard.dto.res.XN625252Res;
import com.ogc.standard.enums.EAdsStatus;
import com.ogc.standard.enums.EBoolean;
import com.ogc.standard.enums.ECoin;
import com.ogc.standard.enums.ECommentLevel;
import com.ogc.standard.enums.EErrorCode_main;
import com.ogc.standard.enums.EJourBizTypePlat;
import com.ogc.standard.enums.EJourBizTypeUser;
import com.ogc.standard.enums.ERefType;
import com.ogc.standard.enums.EReleaserKind;
import com.ogc.standard.enums.ESysUser;
import com.ogc.standard.enums.ESystemCode;
import com.ogc.standard.enums.ETradeOrderStatus;
import com.ogc.standard.enums.ETradeOrderType;
import com.ogc.standard.enums.ETradeType;
import com.ogc.standard.enums.EUserKind;
import com.ogc.standard.enums.EUserReleationType;
import com.ogc.standard.enums.EUserSettingsType;
import com.ogc.standard.exception.BizException;
import com.ogc.standard.exception.EBizErrorCode;

@Service
public class TradeOrderAOImpl implements ITradeOrderAO {

    private static Logger logger = Logger.getLogger(TradeOrderAOImpl.class);

    @Autowired
    private ITradeOrderBO tradeOrderBO;

    @Autowired
    private IAdsBO adsBO;

    @Autowired
    private IAwardBO awardBO;

    @Autowired
    private IArbitrateBO arbitrateBO;

    @Autowired
    private IUserBO userBO;

    @Autowired
    private IAccountBO accountBO;

    @Autowired
    private IJourBO jourBO;

    @Autowired
    private IUserRelationBO userRelationBO;

    @Autowired
    private IUserSettingsBO userSettingsBO;

    @Autowired
    private IBlacklistBO blacklistBO;

    @Autowired
    private ITencentBO tencentBO;

    @Autowired
    private ISmsOutBO smsOutBO;

    @Autowired
    private ISYSDictBO sysDictBO;

    @Override
    @Transactional
    public String contactBuy(String adsCode, String buyUser) {
        String code = null;

        // 检查黑名单
        blacklistBO.isAddBlacklist(buyUser);

        // 校验用户是否存在
        User user = this.userBO.getUser(buyUser);
        // 是否实名
        if (StringUtils.isBlank(user.getRealName())) {
            throw new BizException(EErrorCode_main.user_DOIDFIRST.getCode());
        }

        // 获取广告详情
        Ads ads = adsBO.getAds(adsCode);

        // 校验广告状态，和广告 与 下单者的关系
        this.checkAdsStatusAndMasterCannotBuy(ads, buyUser);

        // 检查是否有正在聊天订单
        TradeOrder tradeOrder = tradeOrderBO.getToSubmitTradeOrder(buyUser,
            ads.getUserId(), adsCode);
        if (tradeOrder != null) {
            code = tradeOrder.getCode();
        } else {
            // 提交待下单的订单
            code = tradeOrderBO.contactBuySubmit(ads, buyUser);
            // 创建聊天群组
            tencentBO.createGroup(code, buyUser, ads.getUserId());
            // 发送提醒短信
            User adsUser = userBO.getUser(ads.getUserId());
            smsOutBO.sendSmsOut(adsUser.getMobile(), SysConstants.ORDER_CONTACT,
                ESystemCode.BZ.getCode(), ESystemCode.BZ.getCode());
        }

        return code;
    }

    @Override
    @Transactional
    public String contactSell(String adsCode, String sellUser) {
        String code = null;

        // 检查黑名单
        blacklistBO.isAddBlacklist(sellUser);
        // 获取广告详情
        Ads ads = adsBO.getAds(adsCode);

        // 校验广告状态，和广告 与 下单者的关系
        this.checkAdsStatusAndMasterCannotBuy(ads, sellUser);

        // 检查是否有正在聊天订单
        TradeOrder tradeOrder = tradeOrderBO
            .getToSubmitTradeOrder(ads.getUserId(), sellUser, adsCode);
        if (tradeOrder != null) {
            code = tradeOrder.getCode();
        } else {
            // 提交待下单的订单
            code = tradeOrderBO.contactSellSubmit(ads, sellUser);
            // 创建聊天群组
            tencentBO.createGroup(code, ads.getUserId(), sellUser);
            // 发送提醒短信
            User adsUser = userBO.getUser(ads.getUserId());
            smsOutBO.sendSmsOut(adsUser.getMobile(), SysConstants.ORDER_CONTACT,
                ESystemCode.BZ.getCode(), ESystemCode.BZ.getCode());
        }

        return code;
    }

    // 我要买币
    @Override
    @Transactional
    public TradeOrder buy(String adsCode, String buyUser, BigDecimal tradePrice,
            BigDecimal count, BigDecimal tradeAmount) {

        TradeOrder tradeOrder = null;

        // 获取广告详情
        Ads ads = adsBO.getAds(adsCode);

        // 校验用户是否存在
        User user = this.userBO.getUser(buyUser);

        // 是否实名
        if (StringUtils.isBlank(user.getRealName())) {
            throw new BizException(EErrorCode_main.user_DOIDFIRST.getCode());
        }

        // 检查黑名单
        blacklistBO.isAddBlacklist(buyUser);

        // 检查购买用户，是否有未完成的订单, 有未完成的交易则不能在进行交易
        if (!this.tradeOrderBO.checkUserHasUnFinishOrder(buyUser,
            ETradeOrderType.BUY, ads.getTradeCoin())) {

            throw new BizException(EErrorCode_main.tradeorder_UNDONE.getCode());
        }
        // 必须为卖币广告
        if (!ads.getTradeType().equals(ETradeType.SELL.getCode())) {

            throw new BizException(
                EErrorCode_main.tradeorder_NOTSELL.getCode());
        }

        // 校验广告状态，和广告 与 购买者的关系
        this.checkAdsStatusAndMasterCannotBuy(ads, buyUser);

        // 交易金额校验
        doAmountCheck(ads, tradePrice, count, tradeAmount, "交易额大于剩余可出售金额");

        // 计算交易广告费
        BigDecimal fee = count.multiply(ads.getFeeRate()).setScale(0,
            BigDecimal.ROUND_DOWN);

        // 变更广告剩余 可售数量
        adsBO.cutLeftCount(ads.getCode(), count);

        // 检查是否有正在聊天订单
        tradeOrder = tradeOrderBO.getToSubmitTradeOrder(buyUser,
            ads.getUserId(), adsCode);
        if (tradeOrder != null) {
            tradeOrder.getCode();
            // 更新交易订单
            tradeOrderBO.buyRefresh(tradeOrder, ads, tradePrice, count,
                tradeAmount, fee);
        } else {
            // 提交交易订单
            tradeOrder = tradeOrderBO.buySubmit(ads, buyUser, tradePrice, count,
                tradeAmount, fee);
            // 创建聊天群组
            tencentBO.createGroup(tradeOrder.getCode(), buyUser,
                ads.getUserId());
        }

        // 发送系统消息
        tencentBO.sendNormalMessage(tradeOrder.getCode(),
            "系统消息：交易已下单，等待买家标记打款");

        // 发送提醒短信
        User adsUser = userBO.getUser(ads.getUserId());
        smsOutBO.sendSmsOut(adsUser.getMobile(), SysConstants.ORDER_SUBMIT,
            ESystemCode.BZ.getCode(), ESystemCode.BZ.getCode());

        return tradeOrder;

    }

    // 我要卖币
    @Override
    @Transactional
    public TradeOrder sell(String adsCode, String sellUser,
            BigDecimal tradePrice, BigDecimal tradeCount,
            BigDecimal tradeAmount) {

        TradeOrder tradeOrder = null;

        // 获取广告详情
        Ads ads = this.adsBO.getAds(adsCode);

        // 检查黑名单
        blacklistBO.isAddBlacklist(sellUser);

        // 检查是否有未完成订单
        if (!this.tradeOrderBO.checkUserHasUnFinishOrder(sellUser,
            ETradeOrderType.SELL, ads.getTradeCoin())) {

            throw new BizException(EErrorCode_main.tradeorder_UNSELL.getCode());

        }

        // 必须为买币广告
        if (!ads.getTradeType().equals(ETradeType.BUY.getCode())) {

            throw new BizException(EErrorCode_main.tradeorder_NOTBUY.getCode());
        }

        // 校验广告
        this.checkAdsStatusAndMasterCannotBuy(ads, sellUser);

        doAmountCheck(ads, tradePrice, tradeCount, tradeAmount, "交易数量大于剩余收购数量");

        // 校验出售者余额
        Account sellUserAccount = this.accountBO.getAccountByUser(sellUser,
            ads.getTradeCoin());
        if (sellUserAccount.getAmount()
            .subtract(sellUserAccount.getFrozenAmount())
            .compareTo(tradeCount) < 0) {

            throw new BizException(
                EErrorCode_main.account_PERSONALLEFT.getCode());
        }

        // 计算交易广告费
        BigDecimal fee = tradeCount.multiply(ads.getFeeRate());

        // 检查是否有正在聊天订单
        tradeOrder = tradeOrderBO.getToSubmitTradeOrder(ads.getUserId(),
            sellUser, adsCode);
        if (tradeOrder != null) {
            // 更新交易订单
            tradeOrderBO.sellRefresh(tradeOrder, ads, tradePrice, tradeCount,
                tradeAmount, fee);
        } else {
            // 提交交易订单
            tradeOrder = tradeOrderBO.sellSubmit(ads, sellUser, tradePrice,
                tradeCount, tradeAmount, fee);
            // 创建聊天群组
            tencentBO.createGroup(tradeOrder.getCode(), ads.getUserId(),
                sellUser);
        }

        // 改变广告可交易量
        this.adsBO.cutLeftCount(ads.getCode(), tradeCount);

        Account dbAccount = accountBO.getAccountByUser(sellUser,
            ads.getTradeCoin());
        // 冻结卖家 数字货币
        this.accountBO.frozenAmount(dbAccount, tradeCount,
            EJourBizTypeUser.AJ_CCORDER_FROZEN.getCode(),
            EJourBizTypeUser.AJ_CCORDER_FROZEN.getValue() + "-提交卖出订单",
            tradeOrder.getCode());

        // 发送系统消息
        tencentBO.sendNormalMessage(tradeOrder.getCode(),
            "系统消息：交易已下单，等待买家标记打款");

        // 发送提醒短信
        User adsUser = userBO.getUser(ads.getUserId());
        smsOutBO.sendSmsOut(adsUser.getMobile(), SysConstants.ORDER_SUBMIT,
            ESystemCode.BZ.getCode(), ESystemCode.BZ.getCode());

        return tradeOrder;

    }

    // 校验广告交易状态，主人不能 购买 或者 出售
    private void checkAdsStatusAndMasterCannotBuy(Ads ads, String applyUser) {

        if (ads.getOnlyTrust().equals(EBoolean.YES.getCode())) {

            if (!this.userRelationBO.checkReleation(ads.getUserId(), applyUser,
                EUserReleationType.TRUST.getCode())) {

                throw new BizException(
                    EErrorCode_main.tradeOrder_TRUST.getCode());

            }

        }

        //
        boolean isBlack = this.userRelationBO.isExistUserRelation(
            ads.getUserId(), applyUser, EUserReleationType.BLACKLIST.getCode());
        if (isBlack) {
            throw new BizException(
                EErrorCode_main.tradeorder_BLACKLIST.getCode());
        }

        if (!EAdsStatus.SHANGJIA.getCode().equals(ads.getStatus())) {
            throw new BizException(EErrorCode_main.tradeorder_ADS.getCode());
        }
        if (applyUser.equals(ads.getUserId())) {
            throw new BizException(
                EErrorCode_main.tradeorder_YOUCANNOT.getCode());
        }
    }

    @Override
    @Transactional
    public void userCancel(String code, String updater, String remark) {
        TradeOrder order = this.tradeOrderBO.getTradeOrder(code);
        if (order == null) {
            throw new BizException(EErrorCode_main.code_NOTEXIST.getCode());
        }
        TradeOrder tradeOrder = tradeOrderBO.getTradeOrder(code);
        if (!ETradeOrderStatus.TO_PAY.getCode().equals(tradeOrder.getStatus())
                && !ETradeOrderStatus.ARBITRATE.getCode()
                    .equals(tradeOrder.getStatus())) {
            throw new BizException(
                EErrorCode_main.tradeorder_STATUSCANCEL.getCode());
        }

        // 变更广告信息 剩余可交易金额
        adsBO.addLeftCount(tradeOrder.getAdsCode(), tradeOrder.getCount());

        Ads data = adsBO.getAds(tradeOrder.getAdsCode());
        Account dbAccount = accountBO.getAccountByUser(data.getUserId(),
            data.getTradeCoin());
        if (tradeOrder.getType().equals(ETradeOrderType.SELL.getCode())) {
            // 由于出售时冻结了，卖家的余额这里需要解冻
            accountBO.unfrozenAmount(dbAccount, tradeOrder.getCount(),
                EJourBizTypeUser.AJ_CCORDER_UNFROZEN_TRADE.getCode(),
                EJourBizTypeUser.AJ_CCORDER_UNFROZEN_TRADE.getValue()
                        + "-取消卖出订单",
                tradeOrder.getCode());

        } else if (tradeOrder.getType().equals(ETradeOrderType.BUY.getCode())) {
            if (EAdsStatus.XIAJIA.getCode().equals(data.getStatus())) {
                // 如果广告已下架，解冻卖家订单金额
                if (tradeOrder.getCount().compareTo(BigDecimal.ZERO) > 0) {
                    this.accountBO.unfrozenAmount(dbAccount,
                        tradeOrder.getCount().add(tradeOrder.getFee()),
                        EJourBizTypeUser.AJ_CCORDER_UNFROZEN_REVOKE.getCode(),
                        EJourBizTypeUser.AJ_CCORDER_UNFROZEN_REVOKE.getValue()
                                + "-交易订单取消，解冻订单金额",
                        tradeOrder.getCode());
                }

            }
            // 购买订单
            // 由于出售广告，出售时就冻结了 所有的 交易金额 + 广告费，
            // 所以 购买订单取消，也不需要解冻
        }

        // 变更交易订单信息
        tradeOrderBO.cancel(tradeOrder, updater, remark);

        // 发送系统消息
        tencentBO.sendNormalMessage(code, "系统消息：" + remark);

    }

    @Override
    @Transactional
    public void platCancel(String code, String updater, String remark) {
        TradeOrder tradeOrder = tradeOrderBO.getTradeOrder(code);
        if (!ETradeOrderStatus.ARBITRATE.getCode()
            .equals(tradeOrder.getStatus())) {
            throw new BizException(
                EErrorCode_main.tradeorder_STATUSCANCEL.getCode());
        }

        TradeOrder order = this.tradeOrderBO.getTradeOrder(code);
        if (order == null) {
            throw new BizException(EErrorCode_main.code_NOTEXIST.getCode());
        }

        // 变更广告信息 剩余可交易金额
        adsBO.addLeftCount(tradeOrder.getAdsCode(), tradeOrder.getCount());
        Ads data = adsBO.getAds(tradeOrder.getAdsCode());
        Account dbAccount = accountBO.getAccountByUser(data.getUserId(),
            data.getTradeCoin());
        if (tradeOrder.getType().equals(ETradeOrderType.SELL.getCode())) {
            // 由于出售时冻结了，卖家的余额这里需要解冻
            this.accountBO.unfrozenAmount(dbAccount, tradeOrder.getCount(),
                EJourBizTypeUser.AJ_CCORDER_UNFROZEN_TRADE.getCode(),
                EJourBizTypeUser.AJ_CCORDER_UNFROZEN_TRADE.getValue()
                        + "-取消卖出订单",
                tradeOrder.getCode());

        } else if (tradeOrder.getType().equals(ETradeOrderType.BUY.getCode())) {
            if (EAdsStatus.XIAJIA.getCode().equals(data.getStatus())) {
                // 如果广告已下架，解冻卖家订单金额
                if (tradeOrder.getCount().compareTo(BigDecimal.ZERO) > 0) {
                    this.accountBO.unfrozenAmount(dbAccount,
                        tradeOrder.getCount().add(tradeOrder.getFee()),
                        EJourBizTypeUser.AJ_CCORDER_UNFROZEN_REVOKE.getCode(),
                        EJourBizTypeUser.AJ_CCORDER_UNFROZEN_REVOKE.getValue()
                                + "-交易订单取消，解冻订单金额",
                        tradeOrder.getCode());
                }

            }
            // 购买订单
            // 由于出售广告，出售时就冻结了 所有的 交易金额 + 广告费，
            // 所以 购买订单取消，也不需要解冻
        }

        // 变更交易订单信息
        tradeOrderBO.cancel(tradeOrder, updater, remark);

        // 发送系统消息
        tencentBO.sendNormalMessage(code, "系统消息：" + remark);

    }

    // 标价打款
    @Override
    @Transactional
    public void markPay(String code, String updater, String remark) {
        TradeOrder tradeOrder = tradeOrderBO.getTradeOrder(code);

        // 检查该订单是否超时
        if (tradeOrder.getInvalidDatetime().compareTo(new Date()) < 0) {
            // 订单已超时
            // 取消订单
            this.userCancel(tradeOrder.getCode(), "系统", "订单支付超时，系统自动取消");
            return;
        }

        //
        if (!ETradeOrderStatus.TO_PAY.getCode()
            .equals(tradeOrder.getStatus())) {
            throw new BizException(
                EErrorCode_main.tradeorder_CANNOTMARK.getCode());
        }

        // 变更交易订单信息
        tradeOrderBO.markPay(tradeOrder, updater, remark);

        // 发送系统消息
        tencentBO.sendNormalMessage(code, "系统消息：买家已标记打款");
    }

    @Override
    @Transactional
    public TradeOrder release(String code, String updater, String remark,
            String kind) {

        TradeOrder tradeOrder = tradeOrderBO.getTradeOrder(code);
        // 操作权限判断，卖家和平台仲裁能释放订单
        // 卖家：已支付和仲裁中的状态下可释放订单
        // 平台：只有仲裁中的状态下可释放订单
        // 已支付 和 仲裁中的订单才能释放
        if (!(ETradeOrderStatus.PAYED.getCode().equals(tradeOrder.getStatus())
                || ETradeOrderStatus.ARBITRATE.getCode()
                    .equals(tradeOrder.getStatus()))) {
            throw new BizException(
                EErrorCode_main.tradeorder_CANNOTRELEASE.getCode());
        }
        if (EReleaserKind.plat.getCode().equals(kind)) {
            if (!ETradeOrderStatus.ARBITRATE.getCode()
                .equals(tradeOrder.getStatus())) {
                throw new BizException(
                    EErrorCode_main.tradeorder_CANNOTRELEASE.getCode());
            }
        }
        // if (user != null
        // && EUserKind.Customer.getCode().equals(user.getKind())) {
        // if (!updater.equals(tradeOrder.getSellUser())) {
        //
        // throw new BizException("xn000", "您不能释放该订单");
        //
        // }
        // }

        if (ETradeOrderType.BUY.getCode().equals(tradeOrder.getType())) {

            // 卖币广告
            // 购买订单划转业务处理
            doTransferBuy(tradeOrder);

        } else if (ETradeOrderType.SELL.getCode()
            .equals(tradeOrder.getType())) {

            // 收币广告
            // 出售订单划转业务处理
            doTransferSell(tradeOrder);

        } else {

            throw new BizException(EBizErrorCode.DEFAULT.getCode(), "未识别的订单类型");

        }
        // 变更交易订单信息
        tradeOrderBO.release(tradeOrder, updater, remark);

        // 维护广告 待交易和交易中状态
        // adsBO.refreshStatus(tradeOrder.getAdsCode(),
        // tradeOrderBO.isExistOningOrder(tradeOrder.getAdsCode()));

        // 发送系统消息
        tencentBO.sendNormalMessage(code, "系统消息：" + remark);

        // end__ 校验卖家的设置
        this.handleUserAutoSetting(tradeOrder.getCode(),
            tradeOrder.getSellUser(), tradeOrder.getBuyUser());

        // end__ 校验买家设置
        this.handleUserAutoSetting(tradeOrder.getCode(),
            tradeOrder.getBuyUser(), tradeOrder.getSellUser());

        return tradeOrder;
    }

    @Override
    @Transactional
    public void comment(String code, String userId, String comment) {
        TradeOrder tradeOrder = tradeOrderBO.getTradeOrder(code);
        if (!ETradeOrderStatus.RELEASED.getCode()
            .equals(tradeOrder.getStatus())) {
            throw new BizException(EBizErrorCode.DEFAULT.getCode(),
                "当前状态下不能评价");
        }
        if (userId.equals(tradeOrder.getBuyUser())) {
            doBsComment(tradeOrder, userId, comment); // 买家对卖家进行评论
        } else if (userId.equals(tradeOrder.getSellUser())) {
            doSbComment(tradeOrder, userId, comment); // 卖家对买家进行评论
        } else {
            throw new BizException(
                EErrorCode_main.tradeorder_NOCOMMENT.getCode());
        }
    }

    @Override
    @Transactional
    public void applyArbitrate(String code, String applyUser, String reason,
            String attach) {
        TradeOrder tradeOrder = tradeOrderBO.getTradeOrder(code);
        if (!ETradeOrderStatus.PAYED.getCode().equals(tradeOrder.getStatus())) {
            throw new BizException(
                EErrorCode_main.tradeorder_NOARBITRATE.getCode());
        }
        String yuangao = applyUser; // 原告
        String beigao = null; // 被告
        if (applyUser.equals(tradeOrder.getBuyUser())) {
            beigao = tradeOrder.getSellUser();
        } else if (applyUser.equals(tradeOrder.getSellUser())) {
            beigao = tradeOrder.getBuyUser();
        } else {
            throw new BizException(
                EErrorCode_main.tradeorder_USERNOTARB.getCode());
        }
        // 更新交易订单信息
        tradeOrderBO.applyArbitrate(tradeOrder, applyUser);
        // 提交仲裁工单
        String arbitrateCode = arbitrateBO.submit(tradeOrder.getCode(), yuangao,
            beigao, reason, attach);
        // 发送系统消息
        tencentBO.sendNormalMessage(code, "系统消息：订单已申请仲裁");

        // 通知相关人员
        String content = String.format(SysConstants.ARBITRATE, arbitrateCode);
        SYSDict condition = new SYSDict();
        condition.setParentKey(SysConstants.ZC_SMS_NOTICE);
        List<SYSDict> mobiledDicts = sysDictBO.querySYSDictList(condition);
        for (SYSDict sysDict : mobiledDicts) {
            smsOutBO.sendSmsOut(sysDict.getDkey(), content,
                ESystemCode.BZ.getCode(), ESystemCode.BZ.getCode());
        }
    }

    @Override
    public Paginable<TradeOrder> queryTradeOrderPage(int start, int limit,
            TradeOrder condition) {
        // 按时间倒叙

        condition.setOrder("update_datetime", "DESC");

        //
        Paginable<TradeOrder> results = tradeOrderBO.getPaginable(start, limit,
            condition);
        for (TradeOrder tradeOrder : results.getList()) {
            tradeOrder.setBuyUserInfo(userBO.getUser(tradeOrder.getBuyUser()));
            tradeOrder
                .setSellUserInfo(userBO.getUser(tradeOrder.getSellUser()));
        }
        return results;
    }

    @Override
    public List<TradeOrder> queryTradeOrderList(TradeOrder condition) {
        return tradeOrderBO.queryTradeOrderList(condition);
    }

    @Override
    public TradeOrder getTradeOrder(String code) {
        TradeOrder tradeOrder = tradeOrderBO.getTradeOrder(code);
        tradeOrder.setBuyUserInfo(userBO.getUser(tradeOrder.getBuyUser()));
        tradeOrder.setSellUserInfo(userBO.getUser(tradeOrder.getSellUser()));
        return tradeOrder;
    }

    // 定时检验 待支付订单的超时时间
    @Override
    public void doCheckUnpayOrder() {
        TradeOrder condition = new TradeOrder();
        condition.setStatus(ETradeOrderStatus.TO_PAY.getCode());
        condition.setInvalidDatetime(new Date());
        List<TradeOrder> resultList = tradeOrderBO
            .queryTradeOrderList(condition);
        for (TradeOrder tradeOrder : resultList) {

            this.userCancel(tradeOrder.getCode(), "系统", "订单支付超时，系统自动取消");

        }

    }

    @Override
    public UserStatistics userStatisticsInfoNotContainTradeCount(String userId,
            String currency) {

        UserStatistics userStatistics = this.tradeOrderBO
            .obtainUserStatistics(userId, currency);
        // 获取被信任次数
        userStatistics.setBeiXinRenCount(this.userRelationBO
            .getRelationCount(userId, EUserReleationType.TRUST.getCode()));
        return userStatistics;

    }

    @Override
    public UserStatistics userStatisticsInfoContainTradeCount(String userId,
            String currency) {

        UserStatistics userStatistics = this
            .userStatisticsInfoNotContainTradeCount(userId, currency);

        // 获取指定交易量
        String totalTradeCountString = this.tradeOrderBO
            .getUserTotalTradeCount(userId, currency).toString();
        userStatistics.setTotalTradeCount(totalTradeCountString);

        return userStatistics;
    }

    @Override
    public BigDecimal getUserTotalTradeCount(String userId, String currency) {

        return this.tradeOrderBO.getUserTotalTradeCount(userId, currency);

    }

    @Override
    public long getTradeTimesBetweenUser(String user1, String user2) {

        return this.tradeOrderBO.getTradeTimesBetweenUser(user1, user2);

    }

    @Override
    public XN625252Res getTradeOrderCheckInfo(String code) {
        XN625252Res res = new XN625252Res();

        // 交易订单详情
        TradeOrder tradeOrder = tradeOrderBO.getTradeOrder(code);
        tradeOrder.setBuyUserInfo(userBO.getUser(tradeOrder.getBuyUser()));
        tradeOrder.setSellUserInfo(userBO.getUser(tradeOrder.getSellUser()));

        // 充值对应流水记录

        Jour condition = new Jour();
        condition.setRefNo(tradeOrder.getCode());

        List<Jour> jourList = jourBO.queryJourList(condition);

        res.setTradeOrder(tradeOrder);
        res.setJourList(jourList);

        return res;
    }

    @Override
    public void dropTradeOrder(String code) {
        // 交易订单详情
        TradeOrder tradeOrder = tradeOrderBO.getTradeOrder(code);
        if (!ETradeOrderStatus.TO_SUBMIT.getCode()
            .equals(tradeOrder.getStatus())
                && !ETradeOrderStatus.CANCEL.getCode()
                    .equals(tradeOrder.getStatus())) {
            throw new BizException(
                EErrorCode_main.tradeorder_CANNOTDELETE.getCode());
        }

        // 判断订单是不是被仲裁过
        Arbitrate condition = new Arbitrate();
        condition.setTradeOrderCode(code);
        if (arbitrateBO.getTotalCount(condition) > 0) {
            throw new BizException(
                EErrorCode_main.tradeorder_CANNOTDELETE.getCode());
        }

        tradeOrderBO.removeTradeOrder(tradeOrder);

    }

    private void doBsComment(TradeOrder tradeOrder, String userId,
            String comment) {
        String status = tradeOrder.getStatus();
        String remark = "买家已评价，等待卖家评价";
        if (StringUtils.isNotBlank(tradeOrder.getBsComment())) {
            throw new BizException(
                EErrorCode_main.tradeorder_COMMENTREPEAT.getCode());
        }
        // 如果卖家已经评价过，订单完成
        if (StringUtils.isNotBlank(tradeOrder.getSbComment())) {
            status = ETradeOrderStatus.COMPLETE.getCode();
            remark = "订单已完成";
        }
        tradeOrderBO.doBsComment(tradeOrder, userId, comment, status, remark);
        // 发送系统消息
        tencentBO.sendNormalMessage(tradeOrder.getCode(), "系统消息：买家已评价");
    }

    private void doSbComment(TradeOrder tradeOrder, String userId,
            String comment) {
        String status = tradeOrder.getStatus();
        String remark = "卖家已评价，等待买家评价";
        if (StringUtils.isNotBlank(tradeOrder.getSbComment())) {
            throw new BizException(
                EErrorCode_main.tradeorder_COMMENTREPEAT.getCode());
        }
        // 如果买家已经评价过，订单完成
        if (StringUtils.isNotBlank(tradeOrder.getBsComment())) {
            status = ETradeOrderStatus.COMPLETE.getCode();
            remark = "订单已完成";
        }
        tradeOrderBO.doSbComment(tradeOrder, userId, comment, status, remark);
        // 发送系统消息
        tencentBO.sendNormalMessage(tradeOrder.getCode(), "系统消息：卖家已评价");
    }

    // 购买广告， 我要出售
    private void doTransferSell(TradeOrder tradeOrder) {

        // 1.卖家 冻结金额减少

        Account dbAccount = accountBO.getAccountByUser(tradeOrder.getSellUser(),
            tradeOrder.getTradeCoin());
        // 1.1 解冻卖家 冻结金额
        this.accountBO.unfrozenAmount(dbAccount, tradeOrder.getCount(),
            EJourBizTypeUser.AJ_CCORDER_FROZEN.getCode(),
            EJourBizTypeUser.AJ_CCORDER_FROZEN.getValue() + "-订单释放解冻",
            tradeOrder.getCode());

        // 1.2扣除卖家账户金额,买家 账户余额增加
        accountBO.transAmount(tradeOrder.getSellUser(),
            tradeOrder.getTradeCoin(), tradeOrder.getBuyUser(),
            tradeOrder.getTradeCoin(), tradeOrder.getCount(),
            EJourBizTypeUser.AJ_CCORDER_SELL.getCode(),
            EJourBizTypeUser.AJ_CCORDER_BUY.getCode(),
            EJourBizTypeUser.AJ_CCORDER_SELL.getValue(),
            EJourBizTypeUser.AJ_CCORDER_BUY.getValue(), tradeOrder.getCode());

        if (tradeOrder.getFee().compareTo(BigDecimal.ZERO) > 0) {

            // 2.2买家 扣除交易广告费,平台 盈亏账户加钱
            accountBO.transAmount(tradeOrder.getBuyUser(),
                tradeOrder.getTradeCoin(), ESysUser.SYS_USER.getCode(),
                tradeOrder.getTradeCoin(), tradeOrder.getFee(),
                EJourBizTypeUser.AJ_CCORDER_FEE.getCode(),
                EJourBizTypePlat.AJ_CCORDER_FEE.getCode(),
                EJourBizTypeUser.AJ_CCORDER_FEE.getValue(),
                EJourBizTypePlat.AJ_CCORDER_FEE.getValue(),
                tradeOrder.getCode());

            // todo 4.推荐人有分成

            handleReferenceFenCheng(tradeOrder);
        }
    }

    // ！！！！！！！！注意！！！！！！
    // 卖出广告 和 买入广告，共用此方法
    private void handleReferenceFenCheng(TradeOrder tradeOrder) {

        User user = this.userBO.getUser(tradeOrder.getBuyUser());

        // 无推荐人 直接 return 掉
        String rederUserId = user.getUserReferee();
        if (!StringUtils.isNotBlank(rederUserId)) {
            return;
        }

        // 推荐人为空 return
        User refereeUser = this.userBO.getUserByMobile(rederUserId);
        if (refereeUser == null) {
            return;
        }
        // 只有X币有分成
        if (!ECoin.X.getCode().equals(tradeOrder.getTradeCoin())) {
            return;
        }
        // 普通用户推荐的只有第一次有分成
        if (refereeUser.getKind().equals(EUserKind.Customer.getCode())) {
            Award condition = new Award();
            condition.setUserId(user.getUserId());
            condition.setRefType(ERefType.BBTRADE.getCode());
            if (awardBO.getTotalCount(condition) > 0) {
                return;
            }
            condition.setRefType(ERefType.CCTRADE.getCode());
            if (awardBO.getTotalCount(condition) > 0) {
                return;
            }
        }
        // 分成
        awardBO.saveOTCAward(refereeUser.getUserId(), refereeUser.getKind(),
            tradeOrder.getCode(), "OTC交易推荐人分成", tradeOrder.getCount());
    }

    // 出售广告处理，购买 释放处理
    private void doTransferBuy(TradeOrder tradeOrder) {

        Account dbAccount = accountBO.getAccountByUser(tradeOrder.getSellUser(),
            tradeOrder.getTradeCoin());
        // 1.1卖家 交易冻结 金额解冻
        accountBO.unfrozenAmount(dbAccount,
            tradeOrder.getCount().add(tradeOrder.getFee()),
            EJourBizTypeUser.AJ_CCORDER_UNFROZEN_TRADE.getCode(),
            EJourBizTypeUser.AJ_CCORDER_UNFROZEN_TRADE.getValue(),
            tradeOrder.getCode());

        // 1.3卖家 扣除交易金额,买家账户余额增加
        accountBO.transAmount(tradeOrder.getSellUser(),
            tradeOrder.getTradeCoin(), tradeOrder.getBuyUser(),
            tradeOrder.getTradeCoin(), tradeOrder.getCount(),
            EJourBizTypeUser.AJ_CCORDER_SELL.getCode(),
            EJourBizTypeUser.AJ_CCORDER_BUY.getCode(),
            EJourBizTypeUser.AJ_CCORDER_SELL.getValue(),
            EJourBizTypeUser.AJ_CCORDER_BUY.getValue(), tradeOrder.getCode());

        // 1.4卖家 扣除交易广告费,平台盈亏账户余额增加(有手续费，才有后续分成)
        if (tradeOrder.getFee().compareTo(BigDecimal.ZERO) > 0) {
            accountBO.transAmount(tradeOrder.getSellUser(),
                tradeOrder.getTradeCoin(), ESysUser.SYS_USER.getCode(),
                tradeOrder.getTradeCoin(), tradeOrder.getFee(),
                EJourBizTypeUser.AJ_CCORDER_FEE.getCode(),
                EJourBizTypePlat.AJ_CCORDER_FEE.getCode(),
                EJourBizTypeUser.AJ_CCORDER_FEE.getValue(),
                EJourBizTypePlat.AJ_CCORDER_FEE.getValue(),
                tradeOrder.getCode());

            // 4.推荐人有分成
            handleReferenceFenCheng(tradeOrder);
        }
    }

    private void doAmountCheck(Ads adsSell, BigDecimal tradePrice,
            BigDecimal count, BigDecimal tradeAmount,
            String outOfLeftCountString) {
        // if (ECoin.ETH.getCode().equals(adsSell.getTradeCoin())) {
        // if (tradePrice.multiply(Convert.fromWei(count, Unit.ETHER))
        // .subtract(tradeAmount).abs().compareTo(BigDecimal.ONE) > 0) {
        // throw new BizException(EBizErrorCode.DEFAULT.getCode(),
        // "交易总额计算有误");
        // }
        // } else if (ECoin.BTC.getCode().equals(adsSell.getTradeCoin())) {
        // if (tradePrice.multiply(BTCUtil.fromBtc(count))
        // .subtract(tradeAmount).abs().compareTo(BigDecimal.ONE) > 0) {
        // throw new BizException(EBizErrorCode.DEFAULT.getCode(),
        // "交易总额计算有误");
        // }
        // }

        if (adsSell.getMinTrade().compareTo(tradeAmount) > 0) {
            throw new BizException(
                EErrorCode_main.tradeorder_MINPRICE.getCode());
        }
        if (adsSell.getMaxTrade().compareTo(tradeAmount) < 0) {
            throw new BizException(
                EErrorCode_main.tradeorder_MAXPRICE.getCode());
        }
        if (adsSell.getLeftCount().compareTo(count) < 0) {
            throw new BizException(EBizErrorCode.DEFAULT.getCode(),
                (Object) outOfLeftCountString);
        }
    }

    private void handleUserAutoSetting(String tradeOrderCode, String userId,
            String toUserId) {

        // 自动好评
        long count = this.userSettingsBO.checkUserSetting(userId,
            EUserSettingsType.AUTO_HAOPING.getCode());
        if (count > 0) {
            // 自动设置了好评
            this.comment(tradeOrderCode, userId,
                ECommentLevel.HAO_PING.getCode());
        }

        // 自动信任
        long trustCount = this.userSettingsBO.checkUserSetting(userId,
            EUserSettingsType.AUTO_TRUST.getCode());
        if (trustCount <= 0) {
            return;
        }

        if (this.userRelationBO.isExistUserRelation(userId, toUserId,
            EUserReleationType.TRUST.getCode())) {
            return;
        }

        this.userRelationBO.saveUserRelation(userId, toUserId,
            EUserReleationType.TRUST.getCode(), ESystemCode.COIN.getCode());

    }

    @Override
    public void createGroupIfNotExist() {
        int start = 0;
        int limit = 10;
        TradeOrder condition = new TradeOrder();
        while (true) {
            Paginable<TradeOrder> results = tradeOrderBO.getPaginable(start,
                limit, condition);
            if (CollectionUtils.isEmpty(results.getList())) {
                break;
            } else {
                for (TradeOrder tradeOrder : results.getList()) {
                    try {
                        tencentBO.createGroup(tradeOrder.getCode(),
                            tradeOrder.getBuyUser(), tradeOrder.getSellUser());
                        logger.info("群组创建成功:ID=" + tradeOrder.getCode());
                    } catch (Exception e) {
                        // logger.error("群组已存在或者创建异常");
                    }
                }
            }
            start++;
        }
    }

    @Override
    public BigDecimal getLastestPrice(String coin) {

        BigDecimal lastestPrice = BigDecimal.ZERO;

        List<String> statusList = new ArrayList<>();
        statusList.add(ETradeOrderStatus.RELEASED.getCode());
        statusList.add(ETradeOrderStatus.COMPLETE.getCode());

        TradeOrder condition = new TradeOrder();
        condition.setTradeCoin(coin);
        condition.setStatusList(statusList);
        condition.setOrder("release_datetime", false);

        Paginable<TradeOrder> pages = tradeOrderBO.getPaginable(1, 1,
            condition);
        if (CollectionUtils.isNotEmpty(pages.getList())) {
            lastestPrice = pages.getList().get(0).getTradePrice();
        }
        return lastestPrice;

    }

}
