package com.ogc.standard.dto.req;

import org.hibernate.validator.constraints.NotBlank;

public class XN805084Req extends BaseReq {

    private static final long serialVersionUID = -1915628516664306144L;

    // 用户编号(必填)
    @NotBlank
    private String userId;

    // 更新人(必填)
    @NotBlank
    private String updater;

    // 备注(选填)
    @NotBlank
    private String remark;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUpdater() {
        return updater;
    }

    public void setUpdater(String updater) {
        this.updater = updater;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

}
