package com.ogc.standard.dto.req;

import org.hibernate.validator.constraints.NotBlank;

public class XN805066Req {
    // userId（必填）
    @NotBlank
    private String userId;

    // 支付密码（必填）
    @NotBlank
    private String tradePwd;

    // 手机验证码（必填）
    // @NotBlank
    private String smsCaptcha;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getTradePwd() {
        return tradePwd;
    }

    public void setTradePwd(String tradePwd) {
        this.tradePwd = tradePwd;
    }

    public String getSmsCaptcha() {
        return smsCaptcha;
    }

    public void setSmsCaptcha(String smsCaptcha) {
        this.smsCaptcha = smsCaptcha;
    }

}
