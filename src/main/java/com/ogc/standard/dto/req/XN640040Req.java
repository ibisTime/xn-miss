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
 * 新增用户行为
 * @author: taojian 
 * @since: 2018年10月12日 下午3:28:19 
 * @history:
 */
public class XN640040Req {

    // 类型
    @NotBlank
    private String type;

    // 对象类型
    @NotBlank
    private String toType;

    // 对象编号
    @NotBlank
    private String toCode;

    // 操作人编号
    @NotBlank
    private String creater;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getToType() {
        return toType;
    }

    public void setToType(String toType) {
        this.toType = toType;
    }

    public String getToCode() {
        return toCode;
    }

    public void setToCode(String toCode) {
        this.toCode = toCode;
    }

    public String getCreater() {
        return creater;
    }

    public void setCreater(String creater) {
        this.creater = creater;
    }
}
