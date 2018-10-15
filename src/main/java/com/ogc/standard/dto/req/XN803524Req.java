package com.ogc.standard.dto.req;

public class XN803524Req extends APageReq {

    private static final long serialVersionUID = -5630907652486881440L;

    // 流水类型 0余额流水 1冻结流水
    private String type;

    // 流水所属账号(必填)
    private String accountNumber;

    // 业务类型(选填)
    private String bizType;

    // 渠道类型（选填）
    private String channelType;

    // 状态(选填)
    private String status;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getBizType() {
        return bizType;
    }

    public void setBizType(String bizType) {
        this.bizType = bizType;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getChannelType() {
        return channelType;
    }

    public void setChannelType(String channelType) {
        this.channelType = channelType;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

}
