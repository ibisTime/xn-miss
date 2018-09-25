package com.ogc.standard.dto.req;

import org.hibernate.validator.constraints.NotBlank;

public class XN805069Req extends BaseReq {

    private static final long serialVersionUID = -3101553812759745501L;

    // userId（必填）
    @NotBlank
    private String userId;

    // 原支付密码（必填）
    @NotBlank
    private String oldTradePwd;

    // 新支付密码（必填）
    @NotBlank
    private String newTradePwd;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getOldTradePwd() {
        return oldTradePwd;
    }

    public void setOldTradePwd(String oldTradePwd) {
        this.oldTradePwd = oldTradePwd;
    }

    public String getNewTradePwd() {
        return newTradePwd;
    }

    public void setNewTradePwd(String newTradePwd) {
        this.newTradePwd = newTradePwd;
    }

}
