package com.ogc.standard.enums;

/** 
 * @author: miyb 
 * @since: 2015-3-7 上午8:51:05 
 * @history:
 */
public enum EUserKind {
    Customer("C", "C端用户"), Plat("P", "平台用户");

    EUserKind(String code, String value) {
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
