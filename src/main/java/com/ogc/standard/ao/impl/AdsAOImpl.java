package com.ogc.standard.ao.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.web3j.utils.Convert;

import com.ogc.standard.ao.IAdsAO;
import com.ogc.standard.ao.IMarketAO;
import com.ogc.standard.bo.IAccountBO;
import com.ogc.standard.bo.IAdsBO;
import com.ogc.standard.bo.IAdsDisplayTimeBO;
import com.ogc.standard.bo.IBlacklistBO;
import com.ogc.standard.bo.ICoinBO;
import com.ogc.standard.bo.IMarketBO;
import com.ogc.standard.bo.ISYSConfigBO;
import com.ogc.standard.bo.ITradeOrderBO;
import com.ogc.standard.bo.IUserBO;
import com.ogc.standard.bo.IUserRelationBO;
import com.ogc.standard.bo.base.Paginable;
import com.ogc.standard.common.BTCUtil;
import com.ogc.standard.common.CoinUtil;
import com.ogc.standard.common.SysConstants;
import com.ogc.standard.core.OrderNoGenerater;
import com.ogc.standard.domain.Account;
import com.ogc.standard.domain.Ads;
import com.ogc.standard.domain.AdsDisplayTime;
import com.ogc.standard.domain.Coin;
import com.ogc.standard.domain.Market;
import com.ogc.standard.domain.User;
import com.ogc.standard.domain.UserStatistics;
import com.ogc.standard.dto.req.XN625220Req;
import com.ogc.standard.dto.req.XN625221Req;
import com.ogc.standard.enums.EAdsPublishType;
import com.ogc.standard.enums.EAdsStatus;
import com.ogc.standard.enums.EBoolean;
import com.ogc.standard.enums.ECoin;
import com.ogc.standard.enums.ECoinStatus;
import com.ogc.standard.enums.ECoinType;
import com.ogc.standard.enums.ECurrency;
import com.ogc.standard.enums.EErrorCode_main;
import com.ogc.standard.enums.EJourBizTypeUser;
import com.ogc.standard.enums.ETradeType;
import com.ogc.standard.enums.EUserReleationType;
import com.ogc.standard.exception.BizException;

/**
 * Created by tianlei on 2017/十一月/14.
 * 1.发布： a.卖币广告是发布广告的时候就把出售金额 和 广告费冻结了
 * b.买币广告没有冻结
 * 2.购买： a. 卖币广告，改变广告的可售余额即可
 * b.买币广告，冻结卖家金额，同时改变广告可购买金额
 * 3.取消:  a. 卖币广告，改变广告的可售金额即可
 * b.买币广告，需要把 卖家冻结的金额返还
 * 4.释放   a. 卖币广告，卖家冻结金额减少,买家账户余额增加
 * b. 买币广告，卖家冻结金额减少，买家账户余额增加(扣除广告费之后)
 */
@Service
public class AdsAOImpl implements IAdsAO {

    @Autowired
    IMarketBO marketBO;

    @Autowired
    IMarketAO marketAO;

    @Autowired
    ISYSConfigBO sysConfigBO;

    @Autowired
    IAccountBO accountBO;

    @Autowired
    IAdsBO adsBO;

    @Autowired
    ITradeOrderBO tradeOrderBO;

    @Autowired
    IAdsDisplayTimeBO displayTimeBO;

    @Autowired
    IUserBO userBO;

    @Autowired
    IUserRelationBO userRelationBO;

    @Autowired
    IBlacklistBO blacklistBO;

    @Autowired
    ICoinBO coinBO;

    @Override
    @Transactional
    public void publishAds(XN625220Req req) {
        // 校验用户是否存在
        User user = userBO.getUser(req.getUserId());

        // 是否实名认证
        if (ETradeType.BUY.getCode().equals(req.getTradeType())
                && StringUtils.isBlank(user.getRealName())) {
            throw new BizException(EErrorCode_main.user_DOIDFIRST.getCode());
        }

        // 是否绑定手机号
        if (StringUtils.isBlank(user.getMobile())) {
            throw new BizException(EErrorCode_main.mobile_UNBIND.getCode());
        }

        // 检查 平台 黑名单
        blacklistBO.isAddBlacklist(user.getUserId());

        Ads data = new Ads();
        data.setUserId(req.getUserId());
        data.setTradeType(req.getTradeType());
        data.setTradeCurrency(req.getTradeCurrency());
        data.setTradeCoin(req.getTradeCoin());
        data.setOnlyTrust(req.getOnlyTrust());
        data.setPremiumRate(req.getPremiumRate());
        data.setProtectPrice(req.getProtectPrice());
        data.setTruePrice(req.getTruePrice());
        data.setTotalCount(req.getTotalCount());
        data.setMinTrade(req.getMinTrade());
        data.setMaxTrade(req.getMaxTrade());
        data.setPayLimit(req.getPayLimit());
        data.setPayType(req.getPayType());
        data.setLeaveMessage(req.getLeaveMessage());
        data.setDisplayTime(req.getDisplayTime());

        // 0.存草稿
        if (EAdsPublishType.DRAFT.getCode().equals(req.getPublishType())) {
            this.saveDraft(data);
        } else if (EAdsPublishType.PUBLISH.getCode()
            .equals(req.getPublishType())) {
            // 直接发布校验是否有，正在上架的同类型的广告
            adsBO.checkHaveSameTypeShangJiaAds(req.getUserId(),
                req.getTradeType(), req.getTradeCoin());
            // 新广告上架
            this.directPublish(data);
        } else {
            throw new BizException(EErrorCode_main.ads_PUBLISHTYPE.getCode());
        }
    }

    @Override
    public Object frontPage(Integer start, Integer limit, Ads condition) {

        Paginable<Ads> paginable = this.adsBO.frontPage(start, limit,
            condition);
        List<Ads> adsList = paginable.getList();
        for (Ads ads : adsList) {
            this.getAdsMasterAndSetMaster(ads);
        }
        return paginable;

    }

    @Override
    public Object ossPage(Integer start, Integer limit, Ads condition) {

        Paginable<Ads> paginable = this.adsBO.ossPage(start, limit, condition);
        List<Ads> adsList = paginable.getList();

        for (Ads ads : adsList) {

            this.getAdsMasterAndSetMaster(ads);

        }
        return paginable;

    }

    @Override
    public List<Ads> frontSearchAdsByNickName(String nickName) {

        // 先搜索出 复合的nick
        User userCondition = new User();
        userCondition.setNickname(nickName);

        List<User> userList = this.userBO.queryUserList(userCondition);
        if (userList.isEmpty()) {
            return new ArrayList<>();
        }

        //
        List<Ads> adsList = new ArrayList<>();
        for (User user : userList) {

            Ads condition = new Ads();
            condition.setUserId(user.getUserId());
            Paginable<Ads> adsPaginable = (Paginable) this.frontPage(0, 10,
                condition);
            //
            adsList.addAll(adsPaginable.getList());

        }

        return adsList;

    }

    private void xiaJiaAdsAndRepublish(Ads data) {
        String userId = data.getUserId();
        Ads oldAds = adsBO.getAds(data.getCode());

        // 只有待交易的可以下架
        if (!oldAds.getStatus().equals(EAdsStatus.SHANGJIA.getCode())) {
            throw new BizException(EErrorCode_main.ads_STATUSSUPPORT.getCode());
        }

        // 校验操作者是否是本人
        if (!oldAds.getUserId().equals(userId)) {
            throw new BizException(EErrorCode_main.ads_YOUSELF.getCode());
        }

        // 进行下架操作
        this.adsBO.xiaJiaAds(oldAds);

        // 删除该广告所有的待下单 订单
        this.tradeOrderBO.removeDaiXiaDanOrders(oldAds.getCode());

        // 构造,并校验
        Ads newAds = this.buildAds(data, OrderNoGenerater.generate("ADS"));
        // 直接发布
        newAds.setStatus(EAdsStatus.SHANGJIA.getCode());
        this.handleTime(newAds);
        if (this.adsBO.insertAds(newAds) != 1) {
            throw new BizException(EErrorCode_main.ads_FAILED.getCode());
        }

        // 如果新广告是卖出广告，首先判断账户余额是否足够支付发布数量+手续费
        if (newAds.getTradeType().equals(ETradeType.SELL.getCode())) {
            checkAccountOfReShangjia(oldAds, newAds);
        }

        // 如果原广告卖币广告 需要对账户进行处理,需要返还剩余数量和剩余手续费
        if (oldAds.getTradeType().equals(ETradeType.SELL.getCode())) {
            doHandAccountOfXiajia(oldAds);
        }

        // // 如果新广告是卖出广告，开始冻结发布数量和手续费
        if (newAds.getTradeType().equals(ETradeType.SELL.getCode())) {
            // 判断账户并处理
            this.doHandAccountOfShangjia(newAds);
        }

    }

    @Transactional
    private void draftPublish(Ads data) {

        if (StringUtils.isBlank(data.getCode())) {
            throw new BizException(EErrorCode_main.ads_ID.getCode());
        }

        // 检查是否有同类型的 上架 广告
        adsBO.checkHaveSameTypeShangJiaAds(data.getUserId(),
            data.getTradeType(), data.getTradeCoin());

        // 构造并校验
        Ads ads = this.buildAds(data, data.getCode());
        ads.setStatus(EAdsStatus.SHANGJIA.getCode());

        this.handleTime(ads);
        int count = this.adsBO.draftPublish(ads);
        if (count != 1) {
            throw new BizException(EErrorCode_main.ads_FAILED.getCode());
        }

        // 如果为卖币,就有对账户进行处理
        if (data.getTradeType().equals(ETradeType.SELL.getCode())) {

            // 判断账户并处理
            this.checkAccountOfShangjia(ads);
            this.doHandAccountOfShangjia(ads);

        }

    }

    private void getAdsMasterAndSetMaster(Ads ads) {

        User user = this.userBO.getUser(ads.getUserId());
        ads.setUser(user);
        UserStatistics userStatistics = this.tradeOrderBO
            .obtainUserStatistics(ads.getUserId(), ads.getTradeCoin());

        // 获取信任数量
        userStatistics.setBeiXinRenCount(this.userRelationBO.getRelationCount(
            ads.getUserId(), EUserReleationType.TRUST.getCode()));

        // 获取用户广告对应币种交易量
        BigDecimal totalTradeCount = this.tradeOrderBO
            .getUserTotalTradeCount(user.getUserId(), ads.getTradeCoin());
        userStatistics.setTotalTradeCount(totalTradeCount.toString());

        // 为了版本兼容，留下的代码 start
        // 获取用户ETH交易量
        BigDecimal totalTradeCountEth = this.tradeOrderBO
            .getUserTotalTradeCount(user.getUserId(), ECoin.ETH.getCode());
        userStatistics.setTotalTradeCountEth(totalTradeCountEth.toString());

        // 获取用户BTC交易量
        BigDecimal totalTradeCountBtc = this.tradeOrderBO
            .getUserTotalTradeCount(user.getUserId(), ECoin.BTC.getCode());
        userStatistics.setTotalTradeCountBtc(totalTradeCountBtc.toString());

        // 获取用户X交易量
        BigDecimal totalTradeCountX = this.tradeOrderBO
            .getUserTotalTradeCount(user.getUserId(), ECoin.X.getCode());
        userStatistics.setTotalTradeCountX(totalTradeCountX.toString());
        // 为了版本兼容，留下的代码 end
        user.setUserStatistics(userStatistics);
        ads.setUserStatistics(userStatistics);

    }

    // 草稿code传入已存在的
    // 第一次插入传生成的
    Ads buildAds(Ads data, String adsCode) {

        Coin coin = coinBO.getCoin(data.getTradeCoin());
        if (ECoinStatus.REVOKE.getCode().equals(coin.getStatus())) {
            throw new BizException(EErrorCode_main.ads_FAILED.getCode());
        }

        Ads ads = new Ads();
        ads.setTradeCoin(data.getTradeCoin());
        ads.setCode(adsCode);
        ads.setUserId(data.getUserId());
        ads.setTradeCurrency(data.getTradeCurrency());
        ads.setCreateDatetime(new Date());
        ads.setUpdateDatetime(new Date());
        ads.setPremiumRate(data.getPremiumRate());
        ads.setTotalCount(data.getTotalCount());
        ads.setLeftCount(data.getTotalCount());
        ads.setTradeType(data.getTradeType());
        ads.setTruePrice(data.getTruePrice());
        if (ECoinType.BTC.getCode().equals(coin.getType())
                || ECoinType.ETH.getCode().equals(coin.getType())
                || ECoinType.X.getCode().equals(coin.getType())) {// 获取市场价格
            // Market market = this.marketBO
            // .standardMarket(ECoin.getCoin(data.getTradeCoin()));
            // if (market == null) {
            // throw new BizException("xn000", "发布失败,行情价格获取异常");
            // }
            //
            // BigDecimal platPrice = this.getPlatformPrice(market);
            BigDecimal platPrice = marketAO
                .coinPriceByPlatform(ads.getTradeCoin(), ads.getTradeCurrency())
                .getMid();
            // if (ECoin.X.getCode().equals(ads.getTradeCoin())) {
            // BigDecimal xToBtc = marketAO
            // .coinPriceByPlatform(ECoin.X.getCode(), ECoin.BTC.getCode())
            // .getMid();
            // BigDecimal btcToTradeCurrency = marketAO.coinPriceByPlatform(
            // ECoin.BTC.getCode(), ads.getTradeCurrency()).getMid();
            // platPrice = xToBtc.multiply(btcToTradeCurrency);
            // }
            ads.setMarketPrice(platPrice);
            BigDecimal truePrice = platPrice
                .multiply(BigDecimal.ONE.add(data.getPremiumRate()));
            if (data.getTradeType().equals(ETradeType.SELL.getCode())) {

                truePrice = truePrice.compareTo(data.getProtectPrice()) > 0
                        ? truePrice : data.getProtectPrice();

            } else {

                truePrice = truePrice.compareTo(data.getProtectPrice()) < 0
                        ? truePrice : data.getProtectPrice();

            }

            ads.setTruePrice(truePrice);

        } else if (ECoinType.X.getCode().equals(coin.getType())) {
            ads.setMarketPrice(data.getTruePrice());
            ads.setTruePrice(data.getTruePrice());
        } else {
            throw new BizException(EErrorCode_main.coin_UNSUPPORT.getCode());
        }

        // 获取用户交易广告费率
        // User user = userBO.getUser(data.getUserId());
        // Double fee = user.getTradeRate();

        BigDecimal fee = sysConfigBO
            .getBigDecimalValue(SysConstants.TRADE_FEE_RATE);

        // 如果活动期间的广告费率大于等于0，表示活动开启，优先取优惠费率
        // Double activityTradeRate = sysConfigBO
        // .getDoubleValue(SysConstants.ACTIVITY_TRADE_FEE_RATE);
        // if (activityTradeRate >= 0) {
        // fee = activityTradeRate;
        // }
        ads.setFeeRate(fee);
        // 设置保护价
        ads.setProtectPrice(data.getProtectPrice());
        ads.setMaxTrade(data.getMaxTrade());
        ads.setMinTrade(data.getMinTrade());
        ads.setPayType(data.getPayType());
        ads.setPayLimit(data.getPayLimit());
        ads.setLeaveMessage(data.getLeaveMessage());
        ads.setOnlyTrust(data.getOnlyTrust());
        ads.setDisplayTime(data.getDisplayTime());

        if (ads.getTotalCount().compareTo(BigDecimal.ZERO) <= 0) {
            throw new BizException(EErrorCode_main.ads_SELLCOUNT.getCode());
        }

        if (ads.getMinTrade().compareTo(BigDecimal.ZERO) <= 0) {
            throw new BizException(EErrorCode_main.ads_MINTRADE.getCode());
        }

        if (ads.getMaxTrade().compareTo(BigDecimal.ZERO) <= 0) {
            throw new BizException(EErrorCode_main.ads_MAXTRADE.getCode());
        }
        if (ads.getMaxTrade().compareTo(ads.getMinTrade()) < 0) {
            throw new BizException(EErrorCode_main.ads_MAXMIN.getCode());
        }

        BigDecimal totalCount = BigDecimal.ZERO;
        totalCount = CoinUtil.fromMinUnit(ads.getTotalCount(), coin.getUnit());

        if (totalCount.multiply(ads.getTruePrice())
            .compareTo(ads.getMinTrade()) < 0) {
            throw new BizException(EErrorCode_main.ads_COUNTMIN.getCode());
        }

        return ads;
    }

    @Transactional
    private void directPublish(Ads data) {

        // 构造,并校验
        Ads ads = this.buildAds(data, OrderNoGenerater.generate("ADS"));
        // 直接发布
        ads.setStatus(EAdsStatus.SHANGJIA.getCode());

        this.handleTime(ads);
        if (this.adsBO.insertAds(ads) != 1) {
            throw new BizException(EErrorCode_main.ads_FAILED.getCode());
        }

        if (ETradeType.SELL.getCode().equals(data.getTradeType())) {
            // 判断账户并处理
            this.checkAccountOfShangjia(ads);
            this.doHandAccountOfShangjia(ads);
        }

    }

    private void saveDraft(Ads data) {

        Ads ads = this.buildAds(data, OrderNoGenerater.generate("ADS"));
        ads.setStatus(EAdsStatus.DRAFT.getCode());
        this.handleTime(ads);
        if (this.adsBO.insertAds(ads) != 1) {
            throw new BizException(EErrorCode_main.ads_FAILED.getCode());
        }

    }

    private void handleTime(Ads ads) {

        // 删除原来的展示时间
        this.displayTimeBO.deleteAdsDisplayTimeByAdsCode(ads.getCode());

        // 插入新的展示时间
        if (ads.getDisplayTime() != null && !ads.getDisplayTime().isEmpty()) {
            // 有展示时间限制、先插入展示时间
            for (AdsDisplayTime displayTime : ads.getDisplayTime()) {
                displayTime.setAdsCode(ads.getCode());
                // 插入
                this.displayTimeBO.insertDisplayTime(displayTime);
            }

        }

    }

    // 由市场加权价格 和 配置参数，获取 最终的价格
    private BigDecimal getPlatformPrice(Market market) {
        Double x = 0d;
        if (ECoin.ETH.getCode().equals(market.getSymbol())) {
            x = this.sysConfigBO.getDoubleValue(SysConstants.ETH_COIN_PRICE_X);
        } else if (ECoin.BTC.getCode().equals(market.getSymbol())) {
            x = this.sysConfigBO.getDoubleValue(SysConstants.BTC_COIN_PRICE_X);
        }
        return market.getMid().add(BigDecimal.valueOf(x));

    }

    public void checkAccountOfShangjia(Ads ads) {

        Account account = this.accountBO.getAccountByUser(ads.getUserId(),
            ads.getTradeCoin());
        if (account.getAmount().subtract(account.getFrozenAmount())
            .compareTo(BigDecimal.ZERO) <= 0) {
            throw new BizException(
                EErrorCode_main.account_PERSONALLEFT.getCode());
        }

        // 广告费+发布总额
        BigDecimal feeRate = ads.getFeeRate();
        BigDecimal fee = ads.getTotalCount().multiply(feeRate);
        BigDecimal willFrezonAmount = ads.getTotalCount().add(fee);

        // 校验账户余额
        if (account.getAmount().subtract(account.getFrozenAmount())
            .compareTo(willFrezonAmount) < 0) {

            BigDecimal maxSell = account.getAmount()
                .subtract(account.getFrozenAmount()).subtract(fee);
            BigDecimal maxSellEther = BigDecimal.ZERO;

            Coin coin = coinBO.getCoin(ads.getTradeCoin());

            maxSellEther = CoinUtil.fromMinUnit(maxSell, coin.getUnit());

            throw new BizException(EErrorCode_main.ads_FEEFROZEN.getCode(),
                (Object) maxSellEther.toString());
        }

    }

    public void checkAccountOfReShangjia(Ads oldAds, Ads newAds) {

        Account account = this.accountBO.getAccountByUser(newAds.getUserId(),
            newAds.getTradeCoin());

        // 原广告冻结金额
        BigDecimal oldFeeRate = oldAds.getFeeRate();
        BigDecimal oldFee = oldAds.getTotalCount().multiply(oldFeeRate);
        BigDecimal oldFrezonAmount = oldAds.getTotalCount().add(oldFee);

        if (account.getAmount().subtract(account.getFrozenAmount())
            .add(oldFrezonAmount).compareTo(BigDecimal.ZERO) <= 0) {
            throw new BizException(
                EErrorCode_main.account_PERSONALLEFT.getCode());
        }

        // 广告费+发布总额
        BigDecimal feeRate = newAds.getFeeRate();
        BigDecimal fee = newAds.getTotalCount().multiply(feeRate);
        BigDecimal willFrezonAmount = newAds.getTotalCount().add(fee);

        // 校验账户余额
        if (account.getAmount().subtract(account.getFrozenAmount())
            .add(oldFrezonAmount).compareTo(willFrezonAmount) < 0) {

            BigDecimal maxSell = account.getAmount().add(oldFrezonAmount)
                .subtract(account.getFrozenAmount()).subtract(fee);
            BigDecimal maxSellEther = BigDecimal.ZERO;
            if (ECoin.ETH.getCode().equals(newAds.getTradeCoin())) {
                maxSellEther = Convert.fromWei(maxSell, Convert.Unit.ETHER);
            } else if (ECoin.BTC.getCode().equals(newAds.getTradeCoin())) {
                maxSellEther = BTCUtil.fromBtc(maxSell);
            }
            throw new BizException(EErrorCode_main.ads_FEEFROZEN.getCode(),
                (Object) maxSellEther.toString());
        }

    }

    @Override
    public Object adsDetail(String adsCode, String searchUserUserId) {

        Ads ads = this.adsBO.getAds(adsCode);

        // 获取展示时间
        ads.setDisplayTime(this.displayTimeBO.queryList(adsCode));

        // 处理用户相关信息
        this.getAdsMasterAndSetMaster(ads);

        return ads;

    }

    @Transactional
    @Override
    public void xiaJiaAds(String adsCode, String userId) {

        Ads ads = adsBO.getAds(adsCode);

        // 只有待交易的可以下架
        if (!ads.getStatus().equals(EAdsStatus.SHANGJIA.getCode())) {
            throw new BizException(EErrorCode_main.ads_STATUSSUPPORT.getCode());
        }

        // 校验操作者是否是本人
        if (!ads.getUserId().equals(userId)) {
            throw new BizException(EErrorCode_main.ads_YOUSELF.getCode());
        }

        // 进行下架操作
        this.adsBO.xiaJiaAds(ads);

        // 删除该广告所有的待下单 订单
        this.tradeOrderBO.removeDaiXiaDanOrders(ads.getCode());

        // 卖币广告 需要对账户进行处理,需要返还剩余数量和剩余手续费
        if (ads.getTradeType().equals(ETradeType.SELL.getCode())) {

            doHandAccountOfXiajia(ads);

        }

    }

    private void doHandAccountOfXiajia(Ads ads) {
        if (ads.getLeftCount().compareTo(BigDecimal.ZERO) > 0) {
            // 计算需要返还的数量
            BigDecimal leftCount = ads.getLeftCount();

            // 算出应该退还的广告费
            BigDecimal backFee = leftCount.multiply(ads.getFeeRate())
                .setScale(0, BigDecimal.ROUND_DOWN);

            Account dbAccount = accountBO.getAccountByUser(ads.getUserId(),
                ads.getTradeCoin());
            // 解冻 未卖出金额
            accountBO.unfrozenAmount(dbAccount, ads.getLeftCount().add(backFee),
                EJourBizTypeUser.AJ_CCORDER_UNFROZEN_REVOKE.getCode(),
                EJourBizTypeUser.AJ_CCORDER_UNFROZEN_REVOKE.getValue()
                        + "-广告未卖出部分解冻",
                ads.getCode());

        }
    }

    private void doHandAccountOfShangjia(Ads ads) {
        BigDecimal feeRate = ads.getFeeRate();
        BigDecimal fee = ads.getTotalCount().multiply(feeRate);

        Account dbAccount = accountBO.getAccountByUser(ads.getUserId(),
            ads.getTradeCoin());
        // 冻结交易金额
        accountBO.frozenAmount(dbAccount, ads.getTotalCount().add(fee),
            EJourBizTypeUser.AJ_CCORDER_FROZEN.getCode(),
            EJourBizTypeUser.AJ_CCORDER_FROZEN.getValue(), ads.getCode());

    }

    @Override
    public void checkXiajia(String adsCode) {

        Ads ads = adsBO.getAds(adsCode);
        // 只有上架的 广告才可以下架。
        if (EAdsStatus.SHANGJIA.getCode().equals(ads.getStatus())) {
            // 剩余金额小于 单笔最小交易金额就下架
            boolean condition1 = ads.getLeftCount()
                .compareTo(BigDecimal.ZERO) <= 0;

            Coin coin = coinBO.getCoin(ads.getTradeCoin());

            BigDecimal leftCount = CoinUtil.fromMinUnit(ads.getLeftCount(),
                coin.getUnit());

            boolean condition2 = leftCount.multiply(ads.getTruePrice())
                .compareTo(ads.getMinTrade()) < 0;
            if (condition1 || condition2) {
                this.xiaJiaAds(adsCode, ads.getUserId());
            }
        }

    }

    // 定时刷新行情价格
    public void refreshMarketPrice() {

        Market marketEth = this.marketBO.standardMarket(ECoin.ETH,
            ECurrency.CNY.getCode());
        Market marketX = this.marketBO.standardMarket(ECoin.X,
            ECurrency.CNY.getCode());
        Market marketBtc = this.marketBO.standardMarket(ECoin.BTC,
            ECurrency.CNY.getCode());
        // 1.只刷新上架状态的
        List<Ads> shangJiaAdsList = this.adsBO.queryShangJiaAdsList();

        BigDecimal marketPriceEth = this.getPlatformPrice(marketEth);
        BigDecimal marketPriceX = this.getPlatformPrice(marketX);
        BigDecimal marketPriceBtc = this.getPlatformPrice(marketBtc);

        for (Ads ads : shangJiaAdsList) {
            if (ECoin.ETH.getCode().equals(ads.getTradeCoin())) {
                ads.setMarketPrice(marketPriceEth);
            } else if (ECoin.X.getCode().equals(ads.getTradeCoin())) {
                ads.setMarketPrice(marketPriceX);
            } else if (ECoin.BTC.getCode().equals(ads.getTradeCoin())) {
                ads.setMarketPrice(marketPriceBtc);
            }

            // 行情价格
            // 真实价格
            // 取出溢价率
            BigDecimal premiumRate = ads.getPremiumRate();
            // 算出 溢价之后的价格
            BigDecimal truePrice = ads.getMarketPrice()
                .multiply(BigDecimal.ONE.add(premiumRate));
            BigDecimal protectPrice = ads.getProtectPrice();

            if (ads.getTradeType().equals(ETradeType.SELL.getCode())) {

                truePrice = truePrice.compareTo(protectPrice) > 0 ? truePrice
                        : protectPrice;

            } else if (ads.getTradeType().equals(ETradeType.BUY.getCode())) {

                truePrice = truePrice.compareTo(protectPrice) < 0 ? truePrice
                        : protectPrice;

            } else {

                continue;

            }
            ads.setTruePrice(truePrice);

            // 只更新行情 和 真实价格
            this.adsBO.updateAdsPriceByPrimaryKey(ads.getCode(),
                ads.getMarketPrice(), ads.getTruePrice());

        }

    }

    @Override
    @Transactional
    public void editAds(XN625221Req req) {
        // 校验用户是否存在
        User user = userBO.getUser(req.getUserId());
        // 是否实名认证
        if (EBoolean.YES.getCode().equals(req.getOnlyCert())) {
            if (StringUtils.isBlank(user.getRealName())) {
                throw new BizException(
                    EErrorCode_main.user_DOIDFIRST.getCode());
            }
        }

        // 检查 平台 黑名单
        blacklistBO.isAddBlacklist(user.getUserId());

        Ads data = new Ads();
        data.setCode(req.getAdsCode());
        data.setUserId(req.getUserId());
        data.setTradeType(req.getTradeType());
        data.setTradeCurrency(req.getTradeCurrency());
        data.setTradeCoin(req.getTradeCoin());
        data.setOnlyTrust(req.getOnlyTrust());
        data.setPremiumRate(req.getPremiumRate());
        data.setProtectPrice(req.getProtectPrice());
        data.setTruePrice(req.getTruePrice());
        data.setTotalCount(req.getTotalCount());
        data.setMinTrade(req.getMinTrade());
        data.setMaxTrade(req.getMaxTrade());
        data.setPayLimit(req.getPayLimit());
        data.setPayType(req.getPayType());
        data.setLeaveMessage(req.getLeaveMessage());
        data.setDisplayTime(req.getDisplayTime());
        // 2. 草稿发布
        if (req.getPublishType()
            .equals(EAdsPublishType.PUBLISH_DRAFT.getCode())) {
            this.draftPublish(data);
            return;
        }
        // 3. 重新编辑发布
        if (req.getPublishType()
            .equals(EAdsPublishType.PUBLISH_REEDIT.getCode())) {
            String oldAdsCode = req.getAdsCode();

            Ads lastAds = this.adsBO.getAds(oldAdsCode);
            if (lastAds == null) {
                throw new BizException(EErrorCode_main.ads_EXIST.getCode());
            }
            xiaJiaAdsAndRepublish(data);
            return;
        }

    }

}
