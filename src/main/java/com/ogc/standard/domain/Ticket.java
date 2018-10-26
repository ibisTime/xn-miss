package com.ogc.standard.domain;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.ogc.standard.dao.base.ABaseDO;

/**
* 加油订单
* @author: jiafr 
* @since: 2018-10-12 11:43:44
* @history:
*/
public class Ticket extends ABaseDO {

    private static final long serialVersionUID = 7319255110950881689L;

    // 编号
    private String code;

    // 选手表编号
    private String playerCode;

    // 票数
    private Long ticket;

    // 下单金额
    private BigDecimal amount;

    // 下单人
    private String applyUser;

    // 状态（0 待支付，1 已支付，2 超时取消）
    private String status;

    // 下单时间
    private Date createDatetime;

    // 失效时间
    private Date invalidDatetime;

    // 支付方式
    private String payType;

    // 支付组号
    private String payGroup;

    // 支付渠道单号
    private String payCode;

    // 支付金额
    private BigDecimal payAmount;

    // 支付时间
    private Date payDatetime;

    // 取消人
    private String canceler;

    // 取消时间
    private Date cancelDatetime;

    // 备注
    private String remark;

    /************db properties************/

    /***********辅助字段************/

    // 选手信息
    private Player playerInfo;

    // 申请人信息
    private User applyUserInfo;

    // 下单时间起
    private Date createDatetimeStart;

    // 下单时间止
    private Date createDatetimeEnd;

    // 支付时间起
    private Date payDatetimeStart;

    // 支付时间止
    private Date payDatetimeEnd;

    // 状态list
    private List<String> statusList;

    // 选手编号
    private String matchPlayCode;

    public Player getPlayerInfo() {
        return playerInfo;
    }

    public void setPlayerInfo(Player playerInfo) {
        this.playerInfo = playerInfo;
    }

    public User getApplyUserInfo() {
        return applyUserInfo;
    }

    public void setApplyUserInfo(User applyUserInfo) {
        this.applyUserInfo = applyUserInfo;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getPlayerCode() {
        return playerCode;
    }

    public void setPlayerCode(String playerCode) {
        this.playerCode = playerCode;
    }

    public Long getTicket() {
        return ticket;
    }

    public void setTicket(Long ticket) {
        this.ticket = ticket;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
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

    public Date getCreateDatetime() {
        return createDatetime;
    }

    public void setCreateDatetime(Date createDatetime) {
        this.createDatetime = createDatetime;
    }

    public Date getInvalidDatetime() {
        return invalidDatetime;
    }

    public void setInvalidDatetime(Date invalidDatetime) {
        this.invalidDatetime = invalidDatetime;
    }

    public String getPayType() {
        return payType;
    }

    public void setPayType(String payType) {
        this.payType = payType;
    }

    public String getPayGroup() {
        return payGroup;
    }

    public void setPayGroup(String payGroup) {
        this.payGroup = payGroup;
    }

    public String getPayCode() {
        return payCode;
    }

    public void setPayCode(String payCode) {
        this.payCode = payCode;
    }

    public BigDecimal getPayAmount() {
        return payAmount;
    }

    public void setPayAmount(BigDecimal payAmount) {
        this.payAmount = payAmount;
    }

    public Date getPayDatetime() {
        return payDatetime;
    }

    public void setPayDatetime(Date payDatetime) {
        this.payDatetime = payDatetime;
    }

    public String getCanceler() {
        return canceler;
    }

    public void setCanceler(String canceler) {
        this.canceler = canceler;
    }

    public Date getCancelDatetime() {
        return cancelDatetime;
    }

    public void setCancelDatetime(Date cancelDatetime) {
        this.cancelDatetime = cancelDatetime;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Date getCreateDatetimeStart() {
        return createDatetimeStart;
    }

    public void setCreateDatetimeStart(Date createDatetimeStart) {
        this.createDatetimeStart = createDatetimeStart;
    }

    public Date getCreateDatetimeEnd() {
        return createDatetimeEnd;
    }

    public void setCreateDatetimeEnd(Date createDatetimeEnd) {
        this.createDatetimeEnd = createDatetimeEnd;
    }

    public Date getPayDatetimeStart() {
        return payDatetimeStart;
    }

    public void setPayDatetimeStart(Date payDatetimeStart) {
        this.payDatetimeStart = payDatetimeStart;
    }

    public Date getPayDatetimeEnd() {
        return payDatetimeEnd;
    }

    public void setPayDatetimeEnd(Date payDatetimeEnd) {
        this.payDatetimeEnd = payDatetimeEnd;
    }

    public List<String> getStatusList() {
        return statusList;
    }

    public void setStatusList(List<String> statusList) {
        this.statusList = statusList;
    }

    public String getMatchPlayCode() {
        return matchPlayCode;
    }

    public void setMatchPlayCode(String matchPlayCode) {
        this.matchPlayCode = matchPlayCode;
    }

}
