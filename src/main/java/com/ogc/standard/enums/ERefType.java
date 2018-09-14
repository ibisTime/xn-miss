package com.ogc.standard.enums;

/**
 * @author: taojian 
 * @since: 2018年9月14日 下午5:13:18 
 * @history:
 */
public enum ERefType {

    REGIST("1", "注册分佣"), TRADE("2", "交易分成");

    ERefType(String code, String value) {
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
