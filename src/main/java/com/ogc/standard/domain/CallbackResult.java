/**
 * @Title CallbackResult.java 
 * @Package com.std.account.domain 
 * @Description 
 * @author leo(haiqing)  
 * @date 2017年2月27日 上午11:45:36 
 * @version V1.0   
 */
package com.ogc.standard.domain;

import java.math.BigDecimal;

/** 
 * @author: haiqingzheng 
 * @since: 2017年2月27日 上午11:45:36 
 * @history:
 */
public class CallbackResult {
    // 是否成功
    private boolean isSuccess;

    // 业务类型
    private String bizType;

    // 橙账本流水编号
    private String jourCode;

    // 支付组号
    private String payGroup;

    // 发生金额
    private BigDecimal transAmount;

    // 系统编号
    private String systemCode;

    // 公司编号
    private String companyCode;

    // 业务biz回调URL
    private String url;

    public CallbackResult(boolean isSuccess, String bizType, String jourCode,
            String payGroup, BigDecimal transAmount) {
        super();
        this.isSuccess = isSuccess;
        this.bizType = bizType;
        this.jourCode = jourCode;
        this.payGroup = payGroup;
        this.transAmount = transAmount;
    }

    public boolean isSuccess() {
        return isSuccess;
    }

    public void setSuccess(boolean isSuccess) {
        this.isSuccess = isSuccess;
    }

    public String getBizType() {
        return bizType;
    }

    public void setBizType(String bizType) {
        this.bizType = bizType;
    }

    public String getJourCode() {
        return jourCode;
    }

    public void setJourCode(String jourCode) {
        this.jourCode = jourCode;
    }

    public String getPayGroup() {
        return payGroup;
    }

    public void setPayGroup(String payGroup) {
        this.payGroup = payGroup;
    }

    public BigDecimal getTransAmount() {
        return transAmount;
    }

    public void setTransAmount(BigDecimal transAmount) {
        this.transAmount = transAmount;
    }

    public String getSystemCode() {
        return systemCode;
    }

    public void setSystemCode(String systemCode) {
        this.systemCode = systemCode;
    }

    public String getCompanyCode() {
        return companyCode;
    }

    public void setCompanyCode(String companyCode) {
        this.companyCode = companyCode;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return "CallbackResult [isSuccess=" + isSuccess + ", bizType="
                + bizType + ", jourCode=" + jourCode + ", payGroup=" + payGroup
                + ", transAmount=" + transAmount + ", systemCode=" + systemCode
                + ", companyCode=" + companyCode + ", url=" + url + "]";
    }

}
