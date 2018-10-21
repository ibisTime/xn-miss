package com.ogc.standard.dto.req;

import org.hibernate.validator.constraints.NotBlank;

/**
 * 删除回复模版
 * @author: jiafr 
 * @since: 2018年10月21日 下午8:53:50 
 * @history:
 */
public class XN640111Req {

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
