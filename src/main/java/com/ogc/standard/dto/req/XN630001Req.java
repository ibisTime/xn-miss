package com.ogc.standard.dto.req;

import org.hibernate.validator.constraints.NotBlank;

/**
 * 删除角色
 * @author: xieyj 
 * @since: 2016年5月16日 下午10:06:08 
 * @history:
 */
public class XN630001Req {

    // 角色编号(必填)
    @NotBlank
    private String code;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

}
