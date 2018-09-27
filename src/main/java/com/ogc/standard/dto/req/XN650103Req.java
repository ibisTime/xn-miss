package com.ogc.standard.dto.req;

import org.hibernate.validator.constraints.NotBlank;

/**
 * 
 * @author: lei 
 * @since: 2018年9月15日 下午3:15:50 
 * @history:
 */
public class XN650103Req {

    // 必填，用户编号
    @NotBlank
    private String userId;

    // 必填，转换法币
    @NotBlank
    private String currency;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

}
