package com.ogc.standard.dto.req;

import org.hibernate.validator.constraints.NotBlank;

public class XN805050Req extends BaseReq {

    private static final long serialVersionUID = -4147542771911812857L;

    // 登陆名（必填）
    @NotBlank
    private String loginName;

    // 登陆密码（必填）
    @NotBlank
    private String loginPwd;

    // 客户端（选填）
    private String client;

    // 定位（选填）
    private String location;

    public String getClient() {
        return client;
    }

    public void setClient(String client) {
        this.client = client;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getLoginPwd() {
        return loginPwd;
    }

    public void setLoginPwd(String loginPwd) {
        this.loginPwd = loginPwd;
    }

}
