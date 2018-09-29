package com.ogc.standard.enums;

import java.util.HashMap;
import java.util.Map;

/**
 * @author: xieyj 
 * @since: 2017年2月9日 下午8:10:43 
 * @history:
 */
public enum ESystemAccount {

    SYS_ACOUNT_ETH("SYS_ACOUNT_ETH", "平台ETH盈亏账户"), SYS_ACOUNT_ETH_COLD(
            "SYS_ACOUNT_ETH_COLD", "平台ETH冷钱包"), SYS_ACOUNT_ETH_M(
            "SYS_ACOUNT_ETH_M", "平台ETH散取账户")

    , SYS_ACOUNT_BTC("SYS_ACOUNT_BTC", "平台BTC盈亏账户"), SYS_ACOUNT_BTC_COLD(
            "SYS_ACOUNT_BTC_COLD", "平台BTC冷钱包"), SYS_ACOUNT_BTC_M(
            "SYS_ACOUNT_BTC_M", "平台BTC散取账户")

    , SYS_ACOUNT_X("SYS_ACOUNT_X", "平台X盈亏账户"), SYS_ACOUNT_X_COLD(
            "SYS_ACOUNT_X_COLD", "平台X冷钱包"), SYS_ACOUNT_X_M("SYS_ACOUNT_X_M",
            "平台M散取账户");

    public static Map<String, ESystemAccount> getMap() {
        Map<String, ESystemAccount> map = new HashMap<String, ESystemAccount>();
        for (ESystemAccount direction : ESystemAccount.values()) {
            map.put(direction.getCode(), direction);
        }
        return map;
    }

    public static String getPlatAccount(String symbol) {
        return "SYS_ACOUNT_" + symbol;
    }

    // 获取冷钱包账户
    public static String getPlatColdAccount(String symbol) {
        return "SYS_ACOUNT_" + symbol + "_COLD";
    }

    // 获取盈亏账户
    public static String getProfitAndLossAccount(String symbol) {
        return "SYS_ACOUNT_" + symbol;
    }

    // 获取散取账户
    public static String getMAccount(String symbol) {
        return "SYS_ACOUNT_" + symbol + "_M";
    }

    ESystemAccount(String code, String value) {
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
