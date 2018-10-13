package com.ogc.standard.dto.req;

import org.hibernate.validator.constraints.NotBlank;

public class XN803522Req {
    // 编号
    @NotBlank
    private String code;

    // 系统编号
    private String systemCode;

    public String getSystemCode() {
        return systemCode;
    }

    public void setSystemCode(String systemCode) {
        this.systemCode = systemCode;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

}
