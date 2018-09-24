/**
 * @Title XN805086Req.java 
 * @Package com.ogc.standard.dto.req 
 * @Description 
 * @author taojian  
 * @date 2018年9月12日 上午10:29:09 
 * @version V1.0   
 */
package com.ogc.standard.dto.req;

import org.hibernate.validator.constraints.NotBlank;

/** 
 * 绑定邮箱
 * @author: taojian 
 * @since: 2018年9月12日 上午10:29:09 
 * @history:
 */
public class XN805086Req extends BaseReq {

    private static final long serialVersionUID = -303603668632593271L;

    // 邮箱验证码
    @NotBlank
    private String captcha;

    // 邮箱地址
    @NotBlank
    private String email;

    // 用户id
    @NotBlank
    private String userId;

    public String getCaptcha() {
        return captcha;
    }

    public void setCaptcha(String captcha) {
        this.captcha = captcha;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
