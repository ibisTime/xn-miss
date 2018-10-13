package com.ogc.standard.dto.req;

import org.hibernate.validator.constraints.NotBlank;

public class XN630500Req {

    // 名字（必填）
    @NotBlank
    private String name;

    // 类型（必填）
    @NotBlank
    private String type;

    // 访问Url（必填）
    private String url;

    // 图片（选填）
    private String pic;

    // 父编号（选填）
    private String parentCode;

    // 备注(选填)
    private String remark;

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

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public String getParentCode() {
        return parentCode;
    }

    public void setParentCode(String parentCode) {
        this.parentCode = parentCode;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
