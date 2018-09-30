/**
 * @Title AwardMonth.java 
 * @Package com.ogc.standard.domain 
 * @Description 
 * @author taojian  
 * @date 2018年9月17日 上午11:25:14 
 * @version V1.0   
 */
package com.ogc.standard.domain;

import java.math.BigDecimal;
import java.util.Date;

import com.ogc.standard.dao.base.ABaseDO;

/** 
 * @author: taojian 
 * @since: 2018年9月17日 上午11:25:14 
 * @history:
 */
public class AwardMonth extends ABaseDO {

    private static final long serialVersionUID = 5072028877108946684L;

    // id
    private Long id;

    // 用户编号
    private String userId;

    // 未结算数量
    private BigDecimal unsettleCount;

    // 结算数量
    private BigDecimal settleCount;

    // 不结算数量
    private BigDecimal nosettleCount;

    // 下个月未结算数量
    private BigDecimal nextUnsettleCount;

    // 当月最后一次结算后余额
    private BigDecimal currentCount;

    // 开始结算日期
    private Date startDate;

    // 结束结算日期
    private Date endDate;

    // 创建时间
    private Date createDatetime;

    // 结算时间
    private Date settleDatetime;

    // 备注
    private String remark;

    // ***********db properties***********

    private Date createDatetimeStart;

    private Date createDatetimeEnd;

    private Date now;

    private User user;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
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

    public Date getNow() {
        return now;
    }

    public void setNow(Date now) {
        this.now = now;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public BigDecimal getUnsettleCount() {
        return unsettleCount;
    }

    public void setUnsettleCount(BigDecimal unsettleCount) {
        this.unsettleCount = unsettleCount;
    }

    public BigDecimal getSettleCount() {
        return settleCount;
    }

    public void setSettleCount(BigDecimal settleCount) {
        this.settleCount = settleCount;
    }

    public BigDecimal getNosettleCount() {
        return nosettleCount;
    }

    public void setNosettleCount(BigDecimal nosettleCount) {
        this.nosettleCount = nosettleCount;
    }

    public BigDecimal getNextUnsettleCount() {
        return nextUnsettleCount;
    }

    public void setNextUnsettleCount(BigDecimal nextUnsettleCount) {
        this.nextUnsettleCount = nextUnsettleCount;
    }

    public BigDecimal getCurrentCount() {
        return currentCount;
    }

    public void setCurrentCount(BigDecimal currentCount) {
        this.currentCount = currentCount;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Date getCreateDatetime() {
        return createDatetime;
    }

    public void setCreateDatetime(Date createDatetime) {
        this.createDatetime = createDatetime;
    }

    public Date getSettleDatetime() {
        return settleDatetime;
    }

    public void setSettleDatetime(Date settleDatetime) {
        this.settleDatetime = settleDatetime;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
