package com.ogc.standard.dto.req;

import org.hibernate.validator.constraints.NotBlank;

public class XN805044Req extends BaseReq {

    private static final long serialVersionUID = 5984638266355952512L;

    // 手机
    @NotBlank
    private String mobile;

    // 证件类型
    private String idKind;

    // 证件编号
    private String idNo;

    // 真实姓名
    @NotBlank
    private String realName;

    // 负责区域
    @NotBlank
    private String respArea;

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getIdKind() {
        return idKind;
    }

    public void setIdKind(String idKind) {
        this.idKind = idKind;
    }

    public String getIdNo() {
        return idNo;
    }

    public void setIdNo(String idNo) {
        this.idNo = idNo;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getRespArea() {
        return respArea;
    }

    public void setRespArea(String respArea) {
        this.respArea = respArea;
    }
}
