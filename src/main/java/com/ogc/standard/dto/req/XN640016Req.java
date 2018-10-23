package com.ogc.standard.dto.req;

import org.hibernate.validator.constraints.NotBlank;

/**
 * 详情查询选手
 * @author: jiafr 
 * @since: 2018年10月11日 下午5:03:13 
 * @history:
 */
public class XN640016Req {
    // 编号
    @NotBlank
    private String code;

    // C端用户编号
    private String userId;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

}
