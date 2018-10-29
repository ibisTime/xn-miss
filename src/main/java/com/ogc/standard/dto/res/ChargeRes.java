package com.ogc.standard.dto.res;

public class ChargeRes {
    // 是否成功
    private boolean isSuccess;

    private String orderCode;

    private String wechatOrderNo;

    private String bizType;

    public ChargeRes() {
    }

    public String getWechatOrderNo() {
        return wechatOrderNo;
    }

    public void setWechatOrderNo(String wechatOrderNo) {
        this.wechatOrderNo = wechatOrderNo;
    }

    public ChargeRes(boolean isSuccess, String orderCode, String wechatOrderNo,
            String bizType) {
        this.isSuccess = isSuccess;
        this.orderCode = orderCode;
        this.wechatOrderNo = wechatOrderNo;
        this.bizType = bizType;
    }

    public boolean getIsSuccess() {
        return isSuccess;
    }

    public void setIsSuccess(boolean isSuccess) {
        this.isSuccess = isSuccess;
    }

    public String getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
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

}
