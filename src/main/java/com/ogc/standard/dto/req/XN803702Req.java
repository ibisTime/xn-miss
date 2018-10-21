package com.ogc.standard.dto.req;

import org.hibernate.validator.constraints.NotBlank;

public class XN803702Req {

    @NotBlank
    private String userId;

    @NotBlank
    private String payType;

    @NotBlank
    private String amount;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getPayType() {
        return payType;
    }

    public void setPayType(String payType) {
        this.payType = payType;
    }
}
