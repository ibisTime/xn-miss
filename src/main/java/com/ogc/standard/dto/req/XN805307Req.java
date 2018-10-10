/**
 * @Title XN805307Req.java 
 * @Package com.ogc.standard.dto.req 
 * @Description 
 * @author dl  
 * @date 2018年8月22日 下午3:05:56 
 * @version V1.0   
 */
package com.ogc.standard.dto.req;

import org.hibernate.validator.constraints.NotBlank;

/** 
 * 详情查赛事信息
 * @author: dl 
 * @since: 2018年8月22日 下午3:05:56 
 * @history:
 */
public class XN805307Req {

    // 消息编号
    @NotBlank
    private String code;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
