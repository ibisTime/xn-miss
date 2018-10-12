package com.ogc.standard.dto.req;

import org.hibernate.validator.constraints.NotBlank;

/**
 * 下单加油订单
 * @author: jiafr 
 * @since: 2018年10月12日 下午12:40:55 
 * @history:
 */
public class XN640030Req {

    // 选手编号
    @NotBlank
    private String playerCode;

    // 票数
    @NotBlank
    private String ticket;

    // 下单人
    @NotBlank
    private String applyUser;

    public String getPlayerCode() {
        return playerCode;
    }

    public void setPlayerCode(String playerCode) {
        this.playerCode = playerCode;
    }

    public String getTicket() {
        return ticket;
    }

    public void setTicket(String ticket) {
        this.ticket = ticket;
    }

    public String getApplyUser() {
        return applyUser;
    }

    public void setApplyUser(String applyUser) {
        this.applyUser = applyUser;
    }

}
