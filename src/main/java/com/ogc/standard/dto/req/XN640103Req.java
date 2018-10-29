package com.ogc.standard.dto.req;

import org.hibernate.validator.constraints.NotBlank;

/**
 * 阅读消息
 * @author: xieyj 
 * @since: 2018年10月29日 下午4:42:56 
 * @history:
 */
public class XN640103Req {

    // 会话编号
    @NotBlank
    private String code;

    @NotBlank
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
