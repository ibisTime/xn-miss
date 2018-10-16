package com.ogc.standard.enums;

import java.util.HashMap;
import java.util.Map;

/**
 * @author: xieyj 
 * @since: 2016年11月11日 上午10:54:16 
 * @history:
 */
public enum EActionType {
    SHARE("1", "分享"), ATTENTION("2", "关注"), FOOT("3", "足迹");

    public static Map<String, EActionType> getAccountTypeResultMap() {
        Map<String, EActionType> map = new HashMap<String, EActionType>();
        for (EActionType type : EActionType.values()) {
            map.put(type.getCode(), type);
        }
        return map;
    }

    EActionType(String code, String value) {
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
