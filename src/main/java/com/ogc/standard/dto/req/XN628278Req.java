package com.ogc.standard.dto.req;

import org.hibernate.validator.constraints.NotBlank;

public class XN628278Req {

    @NotBlank
    private String code;

    // 用户编号
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
