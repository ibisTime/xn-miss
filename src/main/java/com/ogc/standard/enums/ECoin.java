package com.ogc.standard.enums;

import java.util.HashMap;
import java.util.Map;

import com.ogc.standard.exception.BizException;

/**
 * 
 * @author: lei 
 * @since: 2018年8月21日 下午5:25:45 
 * @history:
 */
public enum ECoin {
    ETH("ETH", "以太币"), BTC("BTC", "比特币"), USDT("USDT", "美元代币"), X("X", "X币");

    public static Map<String, ECoin> getCurrencyMap() {
        Map<String, ECoin> map = new HashMap<String, ECoin>();
        for (ECoin currency : ECoin.values()) {
            map.put(currency.getCode(), currency);
        }
        return map;
    }

    public static ECoin getCoin(String code) {
        Map<String, ECoin> map = getCurrencyMap();
        ECoin result = map.get(code);
        if (result == null) {
            throw new BizException(EErrorCode_main.code_NOTEXIST.getCode());
        }
        return result;
    }

    ECoin(String code, String value) {
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
