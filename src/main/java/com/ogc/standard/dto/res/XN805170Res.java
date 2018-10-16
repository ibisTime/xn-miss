package com.ogc.standard.dto.res;

public class XN805170Res {
    // 用户编号
    private String userId;

    // 是否绑定手机号
    private String isNeedMobile;

    public XN805170Res() {
    }

    public XN805170Res(String userId) {
        this.userId = userId;
    }

    public XN805170Res(String userId, String isNeedMobile) {
        this.userId = userId;
        this.isNeedMobile = isNeedMobile;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getIsNeedMobile() {
        return isNeedMobile;
    }

    public void setIsNeedMobile(String isNeedMobile) {
        this.isNeedMobile = isNeedMobile;
    }

}
