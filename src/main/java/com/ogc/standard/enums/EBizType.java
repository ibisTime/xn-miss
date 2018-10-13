package com.ogc.standard.enums;

/**
 * 操作类型
 * @author: jiafr 
 * @since: 2018年10月11日 下午2:05:27 
 * @history:
 */
public enum EBizType {

    SAVE("0", "保存"), PUBLISH("1", "发布");

    EBizType(String code, String value) {
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
