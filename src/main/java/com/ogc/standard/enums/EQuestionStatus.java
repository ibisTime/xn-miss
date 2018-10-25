/**
 * @Title ECoinType.java 
 * @Package com.ogc.standard.enums 
 * @Description 
 * @author haiqingzheng  
 * @date 2018年3月13日 上午11:30:16 
 * @version V1.0   
 */
package com.ogc.standard.enums;

/** 
 * @author: haiqingzheng 
 * @since: 2018年3月13日 上午11:30:16 
 * @history:
 */
public enum EQuestionStatus {
    TO_READ("0", "未读"), READ("1", "已读");

    EQuestionStatus(String code, String value) {
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
