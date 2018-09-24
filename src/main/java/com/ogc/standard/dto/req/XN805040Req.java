/**
 * @Title XN805040Req.java 
 * @Package com.ogc.standard.dto.req 
 * @Description 
 * @author dl  
 * @date 2018年8月20日 下午1:57:20 
 * @version V1.0   
 */
package com.ogc.standard.dto.req;

import org.hibernate.validator.constraints.NotBlank;

/** 
 * @author: dl 
 * @since: 2018年8月20日 下午1:57:20 
 * @history:
 */
public class XN805040Req extends BaseReq {
    private static final long serialVersionUID = 2021805815367718571L;

    // 手机号
    @NotBlank
    private String mobile;

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

}
