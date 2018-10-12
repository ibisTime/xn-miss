package com.ogc.standard.dto.req;

/**
 * 分页查询加油订单
 * @author: jiafr 
 * @since: 2018年10月12日 下午12:40:55 
 * @history:
 */
public class XN640035Req extends APageReq {

    private static final long serialVersionUID = -9131787545964103736L;

    // 选手表编号
    private String playerCode;

    // 下单人
    private String applyUser;

    // 状态（0 待支付，1 已支付，2 超时取消）
    private String status;

    // 下单时间起
    private String createDatetimeStart;

    // 下单时间止
    private String createDatetimeEnd;

    // 支付时间起
    private String payDatetimeStart;

    // 支付时间止
    private String payDatetimeEnd;

    public String getPlayerCode() {
        return playerCode;
    }

    public void setPlayerCode(String playerCode) {
        this.playerCode = playerCode;
    }

    public String getApplyUser() {
        return applyUser;
    }

    public void setApplyUser(String applyUser) {
        this.applyUser = applyUser;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCreateDatetimeStart() {
        return createDatetimeStart;
    }

    public void setCreateDatetimeStart(String createDatetimeStart) {
        this.createDatetimeStart = createDatetimeStart;
    }

    public String getCreateDatetimeEnd() {
        return createDatetimeEnd;
    }

    public void setCreateDatetimeEnd(String createDatetimeEnd) {
        this.createDatetimeEnd = createDatetimeEnd;
    }

    public String getPayDatetimeStart() {
        return payDatetimeStart;
    }

    public void setPayDatetimeStart(String payDatetimeStart) {
        this.payDatetimeStart = payDatetimeStart;
    }

    public String getPayDatetimeEnd() {
        return payDatetimeEnd;
    }

    public void setPayDatetimeEnd(String payDatetimeEnd) {
        this.payDatetimeEnd = payDatetimeEnd;
    }

}
