/**
 * @Title EBugFeedbackStatus.java 
 * @Package com.cdkj.core.enums 
 * @Description 
 * @author haiqingzheng  
 * @date 2018年8月4日 上午10:55:12 
 * @version V1.0   
 */
package com.ogc.standard.enums;

/** 
 * 
 * @author: lei 
 * @since: 2018年8月20日 下午2:24:11 
 * @history:
 */
public enum ESignLogClient {

    ANDROID("0", "Android"), IOS("1", "iOS"), H5("2", "H5");

    ESignLogClient(String code, String value) {
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
