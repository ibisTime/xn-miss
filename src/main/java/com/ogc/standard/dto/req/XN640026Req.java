package com.ogc.standard.dto.req;

import org.hibernate.validator.constraints.NotBlank;

/**
 * 详情查询榜单
 * @author: jiafr 
 * @since: 2018年10月12日 上午11:27:14 
 * @history:
 */
public class XN640026Req {

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
