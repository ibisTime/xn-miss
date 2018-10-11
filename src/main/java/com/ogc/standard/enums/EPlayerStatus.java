package com.ogc.standard.enums;

/**
 * 选手状态
 * @author: jiafr 
 * @since: 2018年10月11日 下午2:08:32 
 * @history:
 */
public enum EPlayerStatus {

    DRAFT("0", "草稿"), TO_APPROVE("1", "已提交待审批"), NO_PASS("2", "审批不通过"), TO_UP(
            "3", "已审批待上架"), UP("4", "已上架"), DOWN("5", "已下架");

    EPlayerStatus(String code, String value) {
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
