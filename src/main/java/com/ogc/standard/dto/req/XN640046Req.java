/**
 * @Title XN640040Req.java 
 * @Package com.ogc.standard.dto.req 
 * @Description 
 * @author taojian  
 * @date 2018年10月12日 下午3:28:19 
 * @version V1.0   
 */
package com.ogc.standard.dto.req;

import org.hibernate.validator.constraints.NotBlank;

/** 
 * 详情查用户行为
 * @author: taojian 
 * @since: 2018年10月12日 下午3:28:19 
 * @history:
 */
public class XN640046Req {

    // 编号
    @NotBlank
    private String code;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

}
