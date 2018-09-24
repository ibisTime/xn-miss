package com.ogc.standard.dto.req;

import org.hibernate.validator.constraints.NotBlank;

public class XN805068Req extends BaseReq {
    private static final long serialVersionUID = -849157579805852956L;

    // userId（必填）
    @NotBlank
    private String userId;

    // 新支付密码（必填）
    @NotBlank
    private String newTradePwd;

    // 短信验证码（必填）
    @NotBlank
    private String smsCaptcha;

    // 证件类型（必填）
    @NotBlank
    private String idKind;

    // 证件号（必填）
    @NotBlank
    private String idNo;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getNewTradePwd() {
        return newTradePwd;
    }

    public void setNewTradePwd(String newTradePwd) {
        this.newTradePwd = newTradePwd;
    }

    public String getSmsCaptcha() {
        return smsCaptcha;
    }

    public void setSmsCaptcha(String smsCaptcha) {
        this.smsCaptcha = smsCaptcha;
    }

    public String getIdKind() {
        return idKind;
    }

    public void setIdKind(String idKind) {
        this.idKind = idKind;
    }

    public String getIdNo() {
        return idNo;
    }

    public void setIdNo(String idNo) {
        this.idNo = idNo;
    }

}
