package com.ogc.standard.enums;

import java.util.HashMap;
import java.util.Map;

/**
 * @author: xieyj 
 * @since: 2016年5月24日 上午8:29:02 
 * @history:
 */
public enum EGeneratePrefix {

    DH("DH", "导航"), JS("JS", "角色"), CD("CD", "菜单"), BM("BM", "部门"), GS("GS",
            "公司"), XX("XX", "信息"), RZ("RZ", "日志"), ARBITRATE("ZC", "仲裁工单"), TRADE_ORDER(
            "JY", "交易订单"), PAY_GROUP("PG", "组号"),

    SIMU_ORDER("SO", "币币交易委托单"), SIMU_ORDER_DETAIL("SOMX", "币币交易委托单明细"), SIMU_ORDER_HISTORY(
            "SOT", "币币交易历史委托单"), Account("A", "账户"), AJour("AJ", "账户流水"), DEPOSIT(
            "DT", "定存记录"), Collect("GJ", "归集订单"),

    Match("M", "赛事"), MatchApply("MA", "参赛申请"), Team("T", "战队"), TeamMemberApply(
            "TMA", "战队成员申请"), GROUP("GP", "组合"), ATTENTION("AT", "关注/提醒"), Comment(
            "C", "评论"), Interact("I", "点赞"), ACCEPT_ORDER("AO", "承兑商订单"), BANK_CARD(
            "BC", "银行卡"),

    Charge("CH", "充值"), WITHDRAW("WI", "取现"), HLORDER("HL", "红蓝订单"), EXCHANGE_CURRENCY(
            "EC", "币种兑换"), CMB_BIZ_NO("CMB", "招商银行"), EVENT("EV", "赛事信息"), session(
            "SE", "会话"),

    PLAYER("P", "选手");

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
