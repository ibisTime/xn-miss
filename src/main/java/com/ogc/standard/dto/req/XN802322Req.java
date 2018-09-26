package com.ogc.standard.dto.req;

import java.util.List;

public class XN802322Req extends APageReq {

    private static final long serialVersionUID = -8457904537485395601L;

    private String type;

    // 流水所属账号(必填)
    private String accountNumber;

    // 业务类型(选填)
    private String bizType;

    // 渠道类型（选填）
    private String channelType;

    // 状态(选填)
    private String status;

    // 业务类型列表（选填）
    private List<String> bizTypeList;

    public List<String> getBizTypeList() {
        return bizTypeList;
    }

    public void setBizTypeList(List<String> bizTypeList) {
        this.bizTypeList = bizTypeList;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

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

}
