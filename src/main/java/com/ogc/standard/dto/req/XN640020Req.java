package com.ogc.standard.dto.req;

import org.hibernate.validator.constraints.NotBlank;

/**
 * 人工调节
 * @author:jiafr
 * @since: 2018年10月12日 上午9:02:12 
 * @history:
 */
public class XN640020Req {
    // 编号
    @NotBlank
    private String code;

    // 虚拟票数
    @NotBlank
    private String fakeTicket;

    // 更新人
    @NotBlank
    private String updater;

    // 备注
    private String remark;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getFakeTicket() {
        return fakeTicket;
    }

    public void setFakeTicket(String fakeTicket) {
        this.fakeTicket = fakeTicket;
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
