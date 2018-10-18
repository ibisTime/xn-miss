package com.ogc.standard.dto.req;

import org.hibernate.validator.constraints.NotBlank;

/**
 * 取消关注选手
 * @author: jiafr 
 * @since: 2018年10月17日 下午3:49:02 
 * @history:
 */
public class XN640041Req {

    // 对象编号
    @NotBlank
    private String toCode;

    // 操作人编号
    @NotBlank
    private String creater;

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
