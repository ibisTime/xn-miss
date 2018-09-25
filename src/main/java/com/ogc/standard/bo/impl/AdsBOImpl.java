package com.ogc.standard.bo.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ogc.standard.bo.IAdsBO;
import com.ogc.standard.bo.base.Page;
import com.ogc.standard.bo.base.Paginable;
import com.ogc.standard.bo.base.PaginableBOImpl;
import com.ogc.standard.dao.IAdsDAO;
import com.ogc.standard.domain.Ads;
import com.ogc.standard.enums.EAdsStatus;
import com.ogc.standard.enums.ECoin;
import com.ogc.standard.enums.EErrorCode_main;
import com.ogc.standard.enums.ETradeType;
import com.ogc.standard.exception.BizException;

/**
 * Created by tianlei on 2017/十一月/14.
 */
@Component
public class AdsBOImpl extends PaginableBOImpl<Ads> implements IAdsBO {

    @Autowired
    IAdsDAO adsDAO;

    @Override
    public int insertAds(Ads ads) {

        int count = this.adsDAO.insert(ads);
        return count;

    }

    @Override
    public Ads getAds(String adsCode) {

        Ads condition = new Ads();
        condition.setCode(adsCode);

        Ads resultAds = this.adsDAO.select(condition);

        if (resultAds == null) {
            throw new BizException(EErrorCode_main.ads_EXIST.getCode());
        }

        return resultAds;

    }

    @Override
    public void addLeftCount(String adsCode, BigDecimal value) {

        if (value.compareTo(BigDecimal.ZERO) <= 0) {
            throw new BizException(EErrorCode_main.ads_CHANGEAMOUNT.getCode());
        }

        Ads condition = new Ads();
        condition.setCode(adsCode);
        Ads ads = this.adsDAO.select(condition);
        if (ads == null) {
            throw new BizException(EErrorCode_main.ads_EXIST.getCode());
        }

        Ads updateCcondition = new Ads();
        updateCcondition.setCode(ads.getCode());
        updateCcondition.setLeftCount(ads.getLeftCount().add(value));
        int count = this.adsDAO.updateByPrimaryKeySelective(updateCcondition);
        if (count != 1) {
            throw new BizException(EErrorCode_main.ads_FAILED.getCode());
        }
    }

    @Override
    public void cutLeftCount(String adsCode, BigDecimal value) {

        if (value.compareTo(BigDecimal.ZERO) <= 0) {
            throw new BizException(EErrorCode_main.ads_CHANGEAMOUNT.getCode());
        }

        Ads ads = getAds(adsCode);

        Ads updateCondition = new Ads();
        updateCondition.setCode(ads.getCode());
        updateCondition.setLeftCount(ads.getLeftCount().subtract(value));
        // 校验余额
        int count = this.adsDAO.updateByPrimaryKeySelective(updateCondition);
        if (count != 1) {
            throw new BizException(EErrorCode_main.ads_FAILED.getCode());
        }

    }

    @Override
    public void xiaJiaAds(Ads adsSell) {

        adsSell.setStatus(EAdsStatus.XIAJIA.getCode());
        int count = this.adsDAO.updateByPrimaryKeySelective(adsSell);
        if (count != 1) {
            throw new BizException(EErrorCode_main.ads_FAILED.getCode());
        }

    }

    @Override
    public int draftPublish(Ads adsSell) {

        return this.adsDAO.updateByPrimaryKey(adsSell);

    }

    // 前端分页
    @Override
    public Paginable<Ads> frontPage(Integer start, Integer limit,
            Ads condition) {

        if (condition.getMaxPrice() != null
                && condition.getMinPrice() != null) {
            if (condition.getMaxPrice()
                .compareTo(condition.getMinPrice()) <= 0) {
                throw new BizException(EErrorCode_main.ads_MAXMIN.getCode());
            }
        }

        // 只查正在上架中的
        List<String> statusList = new ArrayList<String>();
        statusList.add(EAdsStatus.SHANGJIA.getCode());
        condition.setStatusList(statusList);
        // 传现在是 周几 java 周日 = 1
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
        if (w == 0) {
            w = 7;
        }
        condition.setCurrentWeek(w);

        // 传入现在是几点
        int hour = cal.get(Calendar.HOUR_OF_DAY);
        int minute = cal.get(Calendar.MINUTE);
        condition.setCurrentTime(1.0 * hour + minute * 1.0 / 60);
        condition.setOrder("true_price",
            ETradeType.SELL.getCode().equals(condition.getTradeType()));
        //
        long totalCount = adsDAO.selectFrontTotalCount(condition);
        Paginable<Ads> page = new Page<Ads>(start, limit, totalCount);
        List<Ads> dataList = adsDAO.selectFrontList(condition, page.getStart(),
            page.getPageSize());
        page.setList(dataList);
        return page;

    }

    // oss分页
    @Override
    public Paginable<Ads> ossPage(Integer start, Integer limit, Ads condition) {

        //
        condition.setOrder("update_datetime", "DESC");
        long totalCount = adsDAO.selectTotalCount(condition);
        Paginable<Ads> page = new Page<Ads>(start, limit, totalCount);
        List<Ads> dataList = adsDAO.selectList(condition, page.getStart(),
            page.getPageSize());
        page.setList(dataList);
        return page;

    }

    @Override
    public List<Ads> queryShangJiaAdsList() {

        Ads condition = new Ads();
        condition.setStatusList(this.shangJiaStatusList());
        condition.setTradeCoinList(this.originalCurrencyList());
        return this.adsDAO.selectList(condition);

    }

    private List<String> originalCurrencyList() {

        List<String> currencyList = new ArrayList<>();
        currencyList.add(ECoin.BTC.getCode());
        currencyList.add(ECoin.ETH.getCode());
        currencyList.add(ECoin.X.getCode());
        return currencyList;

    }

    private List<String> shangJiaStatusList() {

        List<String> statusList = new ArrayList<>();
        statusList.add(EAdsStatus.SHANGJIA.getCode());
        return statusList;
    }

    @Override
    public void checkHaveSameTypeShangJiaAds(String userId, String tradeType,
            String tradeCoin) {

        Ads condition = new Ads();
        condition.setTradeType(tradeType);
        condition.setUserId(userId);
        condition.setTradeCoin(tradeCoin);
        condition.setStatusList(this.shangJiaStatusList());
        long count = adsDAO.selectTotalCount(condition);

        if (count > 0) {

            if (tradeType.equals(ETradeType.BUY.getCode())) {

                throw new BizException(EErrorCode_main.ads_ISEXIST.getCode());

            } else if (tradeType.equals(ETradeType.SELL.getCode())) {

                throw new BizException(EErrorCode_main.ads_ISEXIST.getCode());

            }

        }

    }

    // @Override
    // public void refreshStatus(String adsCode, boolean existOningOrder) {
    //
    // if (StringUtils.isBlank(adsCode)) {
    //
    // throw new BizException("xn000", "传入正确的广告编号");
    //
    // }
    //
    // Ads updateCondotion = new Ads();
    // updateCondotion.setCode(adsCode);
    //
    // Ads ads = this.adsDAO.select(updateCondotion);
    // if (ads == null) {
    //
    // throw new BizException("xn000", "广告不存在");
    //
    // }
    // if (!ads.getStatus().equals(EAdsStatus.DAIJIAOYI.getCode()) &&
    // !ads.getStatus().equals(EAdsStatus.JIAOYIZHONG.getCode())) {
    // throw new BizException("xn000", "当前状态不支持状态刷新");
    // }
    //
    // //设置状态
    // updateCondotion.setStatus(existOningOrder ?
    // EAdsStatus.JIAOYIZHONG.getCode() : EAdsStatus.DAIJIAOYI.getCode());
    //
    // //更新状态
    // int count = this.adsDAO.updateByPrimaryKeySelective(updateCondotion);
    // if (count != 1) {
    // throw new BizException("xn000", "刷新状态失败");
    // }
    //
    // }

    @Override
    public long updateAdsPriceByPrimaryKey(String adsCode,
            BigDecimal marketPrice, BigDecimal truePrice) {
        Ads updateCondition = new Ads();
        updateCondition.setCode(adsCode);
        updateCondition.setMarketPrice(marketPrice);
        updateCondition.setTruePrice(truePrice);
        return this.adsDAO.updateByPrimaryKeySelective(updateCondition);
    }

}
