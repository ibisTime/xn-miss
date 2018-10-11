package com.ogc.standard.dto.req;

import org.hibernate.validator.constraints.NotBlank;

/**
 * 上架选手
 * @author: jiafr 
 * @since: 2018年10月11日 下午2:30:20 
 * @history:
 */
public class XN640003Req {

    // 编号
    @NotBlank
    private String code;

    // UI位置
    @NotBlank
    private String location;

    // UI次序
    @NotBlank
    private String orderNo;

    // 更新人
    @NotBlank
    private String updater;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getUpdater() {
        return updater;
    }

    public void setUpdater(String updater) {
        this.updater = updater;
    }

}
