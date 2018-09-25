/**
 * @Title XN805060Req.java 
 * @Package com.ogc.standard.dto.req 
 * @Description 
 * @author dl  
 * @date 2018年8月21日 下午2:59:22 
 * @version V1.0   
 */
package com.ogc.standard.dto.req;

import org.hibernate.validator.constraints.NotBlank;

/** 
 * 绑定手机号
 * @author: dl 
 * @since: 2018年8月21日 下午2:59:22 
 * @history:
 */
public class XN805060Req extends BaseReq {

    private static final long serialVersionUID = 2145444698552677333L;

    // 是否发送密码短息(1=是，0=否)
    @NotBlank
    private String isSendSms;

    // 手机号
    @NotBlank
    private String mobile;

    // 用户id
    @NotBlank
    private String userId;

    // 验证码
    @NotBlank
    private String smsCaptcha;

    public String getIsSendSms() {
        return isSendSms;
    }

    public void setIsSendSms(String isSendSms) {
        this.isSendSms = isSendSms;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getSmsCaptcha() {
        return smsCaptcha;
    }

    public void setSmsCaptcha(String smsCaptcha) {
        this.smsCaptcha = smsCaptcha;
    }
}
