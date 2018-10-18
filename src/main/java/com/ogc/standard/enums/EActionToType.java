package com.ogc.standard.enums;

import java.util.HashMap;
import java.util.Map;

import com.ogc.standard.exception.BizException;

/**
 * 用户行为对象类型
 * @author: jiafr 
 * @since: 2018年10月17日 下午4:59:13 
 * @history:
 */
public enum EActionToType {
    PLAYER("1", "选手");

    public static Map<String, EActionToType> getAccountTypeResultMap() {
        Map<String, EActionToType> map = new HashMap<String, EActionToType>();
        for (EActionToType type : EActionToType.values()) {
            map.put(type.getCode(), type);
        }
        return map;
    }

    public static EActionToType getAccountType(String code) {
        Map<String, EActionToType> map = getAccountTypeResultMap();
        EActionToType result = map.get(code);
        if (result == null) {
            throw new BizException("XN0000", code + "对应的accountType不存在");
        }
        return result;
    }

    EActionToType(String code, String value) {
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
