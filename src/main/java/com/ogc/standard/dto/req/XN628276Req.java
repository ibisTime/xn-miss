/**
 * @Title XN628271Req.java 
 * @Package com.ogc.standard.dto.req 
 * @Description 
 * @author taojian  
 * @date 2018年10月12日 上午11:18:05 
 * @version V1.0   
 */
package com.ogc.standard.dto.req;

import org.hibernate.validator.constraints.NotBlank;

/** 
 * 详情查评论
 * @author: taojian 
 * @since: 2018年10月12日 上午11:18:05 
 * @history:
 */
public class XN628276Req {

    // 评论编号
    @NotBlank
    private String code;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

}
