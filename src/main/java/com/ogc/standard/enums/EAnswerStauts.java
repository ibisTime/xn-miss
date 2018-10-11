/**
 * @Title ESmsType.java 
 * @Package com.ogc.standard.enums 
 * @Description 
 * @author dl  
 * @date 2018年8月22日 下午2:38:20 
 * @version V1.0   
 */
package com.ogc.standard.enums;

/** 
 * @author: dl 
 * @since: 2018年8月22日 下午2:38:20 
 * @history:
 */
public enum EAnswerStauts {

    DRAFT("0", "草稿"), TOAPPROVE("1", "待审核"), FAILED("2", "审核不通过"), PASS("3",
            "审核通过"), ON("4", "已上架"), OFF("5", "已下架");

    EAnswerStauts(String code, String value) {
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
