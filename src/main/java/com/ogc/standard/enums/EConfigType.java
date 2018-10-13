package com.ogc.standard.enums;

/** 
 * @author: xieyj 
 * @since: 2015-3-7 上午8:41:50 
 * @history:
 */
public enum EConfigType {

    QINIU("qiniu", "七牛"), WEIXIN_H5("wx_h5", "微信h5"), WEIXIN_APP("wx_app",
            "微信APP"), TICKET("ticket", "加油订单");

    EConfigType(String code, String value) {
        this.code = code;
        this.value = value;
    }

    private String code;

    private String value;

    public String getCode() {
        return code;
    }

    public String getValue() {
        return value;
    }

}
