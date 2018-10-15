package com.ogc.standard.enums;

import java.util.HashMap;
import java.util.Map;

/**
 * 评论类型
 * @author: silver 
 * @since: 2018年8月23日 上午11:13:45 
 * @history:
 */
public enum ECommentType {

    COMMENT("2", "评论");

    ECommentType(String code, String value) {
        this.code = code;
        this.value = value;
    }

    public static Map<String, ECommentType> getMap() {
        Map<String, ECommentType> map = new HashMap<String, ECommentType>();
        for (ECommentType userStatus : ECommentType.values()) {
            map.put(userStatus.getCode(), userStatus);
        }
        return map;
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
