package com.ogc.standard.dto.req;

import org.hibernate.validator.constraints.NotBlank;

public class XN805092Req {

    // 必填（必填）
    @NotBlank
    private String userId;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

}
