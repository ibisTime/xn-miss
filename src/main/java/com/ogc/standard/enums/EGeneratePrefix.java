package com.ogc.standard.enums;

import java.util.HashMap;
import java.util.Map;

/**
 * @author: xieyj 
 * @since: 2016年5月24日 上午8:29:02 
 * @history:
 */
public enum EGeneratePrefix {

    ADVERTISE("AD", "广告"), DH("DH", "导航"), TRADE_ORDER("JY", "交易订单"), ARBITRATE("ZC","仲裁工单"), 
    Account("A", "账户"), AJour("AJ", "账户流水"), EXCHANGE_CURRENCY("EC", "币种兑换"), 
    HLORDER("HL", "红蓝订单"), Charge("CZ","充值订单"), Withdraw("QX", "取现订单"), 
    Collection("CO","归集订单"), BugFeedback("BF","bug反馈"), Product("PD", "量化产品"),
    Invest("IV", "认购汇总"),InvestFlow("IF", "认购/赎回记录"),RepayPlan("RP", "产品还款计划"), Dapp("DAP", "应用列表");
    
    public static Map<String, EGeneratePrefix> getMap() {
        Map<String, EGeneratePrefix> map = new HashMap<String, EGeneratePrefix>();
        for (EGeneratePrefix orderType : EGeneratePrefix.values()) {
            map.put(orderType.getCode(), orderType);
        }
        return map;
    }

    EGeneratePrefix(String code, String value) {
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
