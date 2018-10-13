package com.ogc.standard.dto.res;

public class XN805170Res {
    // 用户编号
    private String userId;

    // 账户编号
    private String accountNumber;

    // 是否绑定手机号
    private String isNeedMobile;

    public XN805170Res() {
    }

    public XN805170Res(String userId) {
        this.userId = userId;
    }

    public XN805170Res(String userId, String accountNumber) {
        this.userId = userId;
        this.accountNumber = accountNumber;
    }

    public XN805170Res(String userId, String accountNumber, String isNeedMobile) {
        this.userId = userId;
        this.accountNumber = accountNumber;
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

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

}
