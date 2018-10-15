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
            "公司"), XX("XX", "信息"), RZ("RZ", "日志"),

    Account("A", "账户"), AJour("AJ", "账户流水"),

    ATTENTION("AT", "关注/提醒"), Comment("C", "评论"), BANK_CARD("BC", "银行卡"),

    Charge("CH", "充值"), WITHDRAW("WI", "取现"), HLORDER("HL", "红蓝订单"), EVENT(
            "EV", "赛事信息"), session("SE", "会话"), answer("AN", "回复模版"),

    PLAYER("P", "选手"), RANK("R", "榜单"), TICKET("T", "加油订单"), ACTION("AC",
            "用户行为");

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
