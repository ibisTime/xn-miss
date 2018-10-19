package com.ogc.standard.enums;

/**
 * 银行卡状态
 * @author: jiafr 
 * @since: 2018年10月18日 下午2:03:21 
 * @history:
 */
public enum EBankcardStatus {

    USING("1", "启用"), UNUSED("0", "未启用");

    EBankcardStatus(String code, String value) {
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
