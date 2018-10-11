package com.ogc.standard.dto.req;

import org.hibernate.validator.constraints.NotBlank;

/**
 * 下架选手
 * @author: jiafr 
 * @since: 2018年10月11日 下午2:30:20 
 * @history:
 */
public class XN640004Req {

    // 编号
    @NotBlank
    private String code;

    // 更新人
    @NotBlank
    private String updater;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getUpdater() {
        return updater;
    }

    public void setUpdater(String updater) {
        this.updater = updater;
    }

}
