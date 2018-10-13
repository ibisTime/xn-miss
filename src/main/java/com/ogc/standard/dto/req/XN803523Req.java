package com.ogc.standard.dto.req;

import org.hibernate.validator.constraints.NotBlank;

public class XN803523Req {
    // 参考订单号
    @NotBlank
    private String refNo;

    // 系统编号
    private String systemCode;

    public String getSystemCode() {
        return systemCode;
    }

    public void setSystemCode(String systemCode) {
        this.systemCode = systemCode;
    }

    public String getRefNo() {
        return refNo;
    }

    public void setRefNo(String refNo) {
        this.refNo = refNo;
    }
}
