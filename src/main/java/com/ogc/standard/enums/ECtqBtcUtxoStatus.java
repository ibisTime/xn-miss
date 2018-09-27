package com.ogc.standard.enums;

public enum ECtqBtcUtxoStatus {

    OUT_UN_PUSH("0", "输出未推送"), OUT_PUSHED("1", "输出已推送"), IN_UN_PUSH("2",
            "输入未推送"), IN_PUSHED("3", "输入已推送");

    ECtqBtcUtxoStatus(String code, String value) {
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
