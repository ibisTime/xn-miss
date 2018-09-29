package com.ogc.standard.enums;

/**
 * 
 * @author: lei 
 * @since: 2018年9月14日 下午5:14:32 
 * @history:
 */
public enum EMarketOrigin {

    BITFINEX("bitfinex", "B站"), BITTREX("Bittrex", "SC B站"), OKEX("okex",
            "okex"), COINMARKETCAP("coinmarketcap",
                    "coinmarketcap"), HAPPYMONEY("happymoney", "happymoney");

    EMarketOrigin(String code, String value) {
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
