package com.ogc.standard.enums;

import java.util.HashMap;
import java.util.Map;

import com.ogc.standard.exception.BizException;

/**
 * 
 * @author: lei 
 * @since: 2018年8月31日 上午1:15:28 
 * @history:
 */
public enum ESimuKLinePeriod {

    MIN1("1min", "-1"), MIN5("5min", "-5"), MIN15("15min", "-15"), MIN30(
            "30min", "-30"), MIN60("30min", "-60"), DAY("1day",
                    "-1440"), WEEK("1week", "-10080"), MONTH("1mon",
                            "-43200"), YEAR("1year", "-525600"),;

    public static Map<String, ESimuKLinePeriod> getJourKindResultMap() {
        Map<String, ESimuKLinePeriod> map = new HashMap<String, ESimuKLinePeriod>();
        for (ESimuKLinePeriod type : ESimuKLinePeriod.values()) {
            map.put(type.getCode(), type);
        }
        return map;
    }

    public static ESimuKLinePeriod getESimuKLinePeriod(String code) {
        Map<String, ESimuKLinePeriod> map = getJourKindResultMap();
        ESimuKLinePeriod result = map.get(code);
        if (result == null) {
            throw new BizException("XN0000", code + "对应的ESimuKLinePeriod不存在");
        }
        return result;
    }

    ESimuKLinePeriod(String code, String value) {
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
