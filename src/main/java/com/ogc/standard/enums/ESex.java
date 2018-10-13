/**
 * @Title ESex.java 
 * @Package com.std.user.enums 
 * @Description 
 * @author haiqingzheng  
 * @date 2016年12月28日 上午10:08:40 
 * @version V1.0   
 */
package com.ogc.standard.enums;

import java.util.HashMap;
import java.util.Map;

/** 
 * @author: haiqingzheng 
 * @since: 2016年12月28日 上午10:08:40 
 * @history:
 */
public enum ESex {
    UNKNOWN("0", "未知"), MEN("1", "男"), WOMEN("2", "女");
    public static Map<String, ESex> getDirectionMap() {
        Map<String, ESex> map = new HashMap<String, ESex>();
        for (ESex direction : ESex.values()) {
            map.put(direction.getCode(), direction);
        }
        return map;
    }

    ESex(String code, String value) {
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
