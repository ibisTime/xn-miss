/**
 * @Title XN625221Req.java 
 * @Package com.ogc.standard.dto.req 
 * @Description 
 * @author taojian  
 * @date 2018年9月4日 上午11:38:56 
 * @version V1.0   
 */
package com.ogc.standard.dto.req;

import java.math.BigDecimal;
import java.util.List;

import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

import com.ogc.standard.domain.AdsDisplayTime;

/** 
 * @author: taojian 
 * @since: 2018年9月4日 上午11:38:56 
 * @history:
 */
public class XN625221Req {

    // 草稿发布时使用（必填）
    @NotBlank
    private String adsCode;

    @NotBlank
    private String userId;

    // 发布类型 "2", "草稿发布" "3", "编辑发布，原广告下
    @NotBlank
    private String publishType;

    // 交易类型 0=买币，1=卖币
    @NotBlank
    private String tradeType;

    // 交易
    @NotBlank
    private String tradeCurrency;

    // 数字货币
    @NotBlank
    private String tradeCoin;

    // 0=任何人都可以交易、1=只有受信任的人可以交易
    @NotBlank
    private String onlyTrust;

    // 是否需要实名认证1是0否
    @NotBlank
    private String onlyCert;

    // 溢价率（原生币必填）
    @NotNull
    @DecimalMin("-0.9999")
    @DecimalMax("0.9999")
    private BigDecimal premiumRate;

    // 保护价格（原生币必填）
    @NotNull
    @DecimalMin("0")
    private BigDecimal protectPrice;

    // 真实价格（token币必填）
    @NotNull
    @DecimalMin("0")
    private BigDecimal truePrice;

    // 交易总量
    @NotNull
    @DecimalMin("0")
    private BigDecimal totalCount;

    // 单笔最小
    @NotNull
    @DecimalMin("0")
    private BigDecimal minTrade;

    // 单笔最大
    @NotNull
    @DecimalMin("0")
    private BigDecimal maxTrade;

    // 支付类型
    @NotBlank
    private String payType;

    // 付款超时时间
    @NotNull
    private Integer payLimit;

    // 留言
    @NotBlank
    private String leaveMessage;

    // 相关时间
    private List<AdsDisplayTime> displayTime;

    public String getTradeType() {
        return tradeType;
    }

    public void setTradeType(String tradeType) {
        this.tradeType = tradeType;
    }

    public String getAdsCode() {
        return adsCode;
    }

    public void setAdsCode(String adsCode) {
        this.adsCode = adsCode;
    }

    public String getPublishType() {
        return publishType;
    }

    public void setPublishType(String publishType) {
        this.publishType = publishType;
    }

    public String getTradeCurrency() {
        return tradeCurrency;
    }

    public void setTradeCurrency(String tradeCurrency) {
        this.tradeCurrency = tradeCurrency;
    }

    public String getTradeCoin() {
        return tradeCoin;
    }

    public void setTradeCoin(String tradeCoin) {
        this.tradeCoin = tradeCoin;
    }

    public String getOnlyTrust() {
        return onlyTrust;
    }

    public void setOnlyTrust(String onlyTrust) {
        this.onlyTrust = onlyTrust;
    }

    public BigDecimal getPremiumRate() {
        return premiumRate;
    }

    public void setPremiumRate(BigDecimal premiumRate) {
        this.premiumRate = premiumRate;
    }

    public BigDecimal getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(BigDecimal totalCount) {
        this.totalCount = totalCount;
    }

    public BigDecimal getProtectPrice() {
        return protectPrice;
    }

    public void setProtectPrice(BigDecimal protectPrice) {
        this.protectPrice = protectPrice;
    }

    public BigDecimal getMinTrade() {
        return minTrade;
    }

    public void setMinTrade(BigDecimal minTrade) {
        this.minTrade = minTrade;
    }

    public BigDecimal getMaxTrade() {
        return maxTrade;
    }

    public void setMaxTrade(BigDecimal maxTrade) {
        this.maxTrade = maxTrade;
    }

    public String getPayType() {
        return payType;
    }

    public void setPayType(String payType) {
        this.payType = payType;
    }

    public Integer getPayLimit() {
        return payLimit;
    }

    public void setPayLimit(Integer payLimit) {
        this.payLimit = payLimit;
    }

    public String getLeaveMessage() {
        return leaveMessage;
    }

    public void setLeaveMessage(String leaveMessage) {
        this.leaveMessage = leaveMessage;
    }

    public List<AdsDisplayTime> getDisplayTime() {
        return displayTime;
    }

    public void setDisplayTime(List<AdsDisplayTime> displayTime) {
        this.displayTime = displayTime;
    }

    public BigDecimal getTruePrice() {
        return truePrice;
    }

    public void setTruePrice(BigDecimal truePrice) {
        this.truePrice = truePrice;
    }

    public String getOnlyCert() {
        return onlyCert;
    }

    public void setOnlyCert(String onlyCert) {
        this.onlyCert = onlyCert;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}