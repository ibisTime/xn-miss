package com.ogc.standard.dto.req;

import org.hibernate.validator.constraints.NotBlank;

public class XN805061Req extends BaseReq {
    private static final long serialVersionUID = -6660327555886723984L;

    // userId
    @NotBlank
    private String userId;

    // 新手机号
    @NotBlank
    private String newMobile;

    // 验证码
    @NotBlank
    private String smsCaptcha;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getNewMobile() {
        return newMobile;
    }

    public void setNewMobile(String newMobile) {
        this.newMobile = newMobile;
    }

    public String getSmsCaptcha() {
        return smsCaptcha;
    }

    public void setSmsCaptcha(String smsCaptcha) {
        this.smsCaptcha = smsCaptcha;
    }

}
