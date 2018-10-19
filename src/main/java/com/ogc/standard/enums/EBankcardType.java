package com.ogc.standard.enums;

import java.util.HashMap;
import java.util.Map;

import com.ogc.standard.exception.BizException;

/**
 * 银行卡类型
 * @author: jiafr 
 * @since: 2018年10月18日 下午2:00:29 
 * @history:
 */
public enum EBankcardType {
    BANKCARD("1", "银行卡"), WECHAT("2", "微信"), ALIPAY("3", "支付宝");

    public static Map<String, EBankcardType> getAccountTypeResultMap() {
        Map<String, EBankcardType> map = new HashMap<String, EBankcardType>();
        for (EBankcardType type : EBankcardType.values()) {
            map.put(type.getCode(), type);
        }
        return map;
    }

    public static EBankcardType getAccountType(String code) {
        Map<String, EBankcardType> map = getAccountTypeResultMap();
        EBankcardType result = map.get(code);
        if (result == null) {
            throw new BizException("XN0000", code + "对应的BankcardType不存在");
        }
        return result;
    }

    EBankcardType(String code, String value) {
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
