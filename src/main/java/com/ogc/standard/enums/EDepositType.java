package com.ogc.standard.enums;

public enum EDepositType {
    DEPOSIT_M("0", "散取地址定存"), DEPOSIT_S("1", "补给地址定存");

    private String code;

    private String value;

    EDepositType(String code, String value) {
        this.code = code;
        this.value = value;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

}
