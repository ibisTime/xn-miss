/**
 * @Title XN630503Req.java 
 * @Package com.ogc.standard.dto.req 
 * @Description 
 * @author taojian  
 * @date 2018年10月13日 下午1:43:58 
 * @version V1.0   
 */
package com.ogc.standard.dto.req;

import org.hibernate.validator.constraints.NotBlank;

/** 
 * 下架
 * @author: taojian 
 * @since: 2018年10月13日 下午1:43:58 
 * @history:
 */
public class XN630504Req {
    @NotBlank
    private String code;

    private String remark;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
