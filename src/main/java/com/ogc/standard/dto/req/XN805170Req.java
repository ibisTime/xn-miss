package com.ogc.standard.dto.req;

/**
 *  微信登录
 * @author: jiafr 
 * @since: 2018年10月13日 下午2:35:37 
 * @history:
 */
public class XN805170Req {

    // 开放编号（必填）
    private String code;

    // 类型(必填 微信h5=3)
    private String type;

    // 是否强制绑定手机号
    private String isNeedMobile;

    // 手机号（选填）
    private String mobile;

    // 短信验证码（选填）
    private String smsCaptcha;

    // 是否(非微信登录)登录状态，是则无需验证手机号，否则传验证验证手机
    private String isLoginStatus;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getIsNeedMobile() {
        return isNeedMobile;
    }

    public void setIsNeedMobile(String isNeedMobile) {
        this.isNeedMobile = isNeedMobile;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getSmsCaptcha() {
        return smsCaptcha;
    }

    public void setSmsCaptcha(String smsCaptcha) {
        this.smsCaptcha = smsCaptcha;
    }

    public String getIsLoginStatus() {
        return isLoginStatus;
    }

    public void setIsLoginStatus(String isLoginStatus) {
        this.isLoginStatus = isLoginStatus;
    }
}
