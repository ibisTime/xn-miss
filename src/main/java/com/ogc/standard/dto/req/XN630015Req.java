package com.ogc.standard.dto.req;

import org.hibernate.validator.constraints.NotBlank;

public class XN630015Req extends APageReq {

    private static final long serialVersionUID = 5138736221155343722L;

    // 菜单名称(选填)
    private String name;

    // 类型(选填)
    private String type;

    // 父菜单编号(选填)
    private String parentCode;

    // 更新人(选填)
    private String updater;

    // 系统编号（必填）
    @NotBlank
    private String systemCode;

    public String getSystemCode() {
        return systemCode;
    }

    public void setSystemCode(String systemCode) {
        this.systemCode = systemCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getParentCode() {
        return parentCode;
    }

    public void setParentCode(String parentCode) {
        this.parentCode = parentCode;
    }

    public String getUpdater() {
        return updater;
    }

    public void setUpdater(String updater) {
        this.updater = updater;
    }
}
