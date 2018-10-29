package com.ogc.standard.enums;

import java.util.HashMap;
import java.util.Map;

import com.ogc.standard.exception.BizException;

/**
 * 
 * @author: lei 
 * @since: 2018年8月23日 下午10:04:20 
 * @history:
 */
public enum EJourBizTypeBusiness {

    AJ_JYFC("dividend", "投票分成"), AJ_QX("withdraw", "取现");

    public static EJourBizTypeBusiness getBizType(String code) {
        Map<String, EJourBizTypeBusiness> map = getBizTypeMap();
        EJourBizTypeBusiness result = map.get(code);
        if (result == null) {
            throw new BizException("XN0000", code + "对应的jourBizType不存在");
        }
        return result;
    }

    public static Map<String, EJourBizTypeBusiness> getBizTypeMap() {
        Map<String, EJourBizTypeBusiness> map = new HashMap<String, EJourBizTypeBusiness>();
        for (EJourBizTypeBusiness bizType : EJourBizTypeBusiness.values()) {
            map.put(bizType.getCode(), bizType);
        }
        return map;
    }

    EJourBizTypeBusiness(String code, String value) {
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
