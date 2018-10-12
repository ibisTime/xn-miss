package com.ogc.standard.enums;

/**
 * 榜单类型
 * @author: jiafr 
 * @since: 2018年10月12日 上午10:13:35 
 * @history:
 */
public enum ERankType {
    DAY("1", "日榜"), TOTAL("2", "总榜");

    ERankType(String code, String value) {
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
