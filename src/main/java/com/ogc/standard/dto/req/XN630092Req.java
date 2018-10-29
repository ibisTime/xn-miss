package com.ogc.standard.dto.req;

public class XN630092Req {
    // 公司编号(必填)
    private String companyCode;

    // 系统编号(必填)
    private String systemCode;

    // url(必填)
    private String url;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getCompanyCode() {
        return companyCode;
    }

    public void setCompanyCode(String companyCode) {
        this.companyCode = companyCode;
    }

    public String getSystemCode() {
        return systemCode;
    }

    public void setSystemCode(String systemCode) {
        this.systemCode = systemCode;
    }
}
