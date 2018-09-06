package com.ogc.standard.enums;

public enum EYAddressStatus {
    NORMAL("0", "正常"), CERTI("1", "已认证");

    EYAddressStatus(String code, String value) {
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
