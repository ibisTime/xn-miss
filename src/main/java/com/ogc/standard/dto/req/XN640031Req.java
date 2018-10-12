package com.ogc.standard.dto.req;

import org.hibernate.validator.constraints.NotBlank;

/**
 * 取消加油订单
 * @author: jiafr 
 * @since: 2018年10月12日 下午12:40:55 
 * @history:
 */
public class XN640031Req {

    // 订单编号
    @NotBlank
    private String code;

    // 取消人编号
    @NotBlank
    private String userId;

    // 备注
    private String remark;

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

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

}
