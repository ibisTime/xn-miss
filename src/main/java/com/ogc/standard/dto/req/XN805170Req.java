package com.ogc.standard.dto.req;

/**
 *  微信登录
 * @author: jiafr 
 * @since: 2018年10月13日 下午2:35:37 
 * @history:
 */
public class XN805170Req {

    // 开放编号（必填）
    private String code;

    // 类型(必填 微信h5=wx_h5)
    private String type;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

}
