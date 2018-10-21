package com.ogc.standard.dto.req;

import org.hibernate.validator.constraints.NotBlank;

/**
 * 管理端为用户添加备注
 * @author: jiafr 
 * @since: 2018年10月21日 下午10:04:46 
 * @history:
 */
public class XN805086Req {

    // 用户编号
    @NotBlank
    private String userId;

    // 备注
    @NotBlank
    private String remark;

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
