package com.ogc.standard.domain;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.ogc.standard.dao.base.ABaseDO;

/**
 * 账户流水
 * @author: haiqingzheng 
 * @since: 2018年8月26日 下午5:07:53 
 * @history:
 */
public class Jour extends ABaseDO {

    private static final long serialVersionUID = 1255747682967604091L;

    // 流水编号
    private String code;

    // 流水类型（余额流水、冻结流水）
    private String type;

    // 流水所属用户编号
    private String userId;

    // 流水所属账号---核心字段4
    private String accountNumber;

    // 类型(B B端账号，C C端账号，P 平台账号)
    private String accountType;

    // 币种
    private String currency;

    // 业务类型
    private String bizType;

    // 业务说明
    private String bizNote;

    // 变动金额（有正负之分）
    private transient BigDecimal transAmount;

    private String transAmountString;

    // 变动前金额
    private transient BigDecimal preAmount;

    private String preAmountString;

    // 变动后金额
    private transient BigDecimal postAmount;

    private String postAmountString;

    // **************************db properties **************************

    // 真实姓名
    private String realName;

    // 手机号
    private String mobile;

    public String getPreAmountString() {
        return preAmountString;
    }

    public void setPreAmountString(String preAmountString) {
        this.preAmountString = preAmountString;
    }

    public String getPostAmountString() {
        return postAmountString;
    }

    public void setPostAmountString(String postAmountString) {
        this.postAmountString = postAmountString;
    }

    // 状态
    private String status;

    // 支付渠道（线下/招商代付/支付宝/内部转账）
    private String channelType;

    // 支付渠道单号
    private String channelOrder;

    // 系统内部参考订单号
    private String refNo;

    // 备注
    private String remark;

    // 创建时间
    private Date createDatetime;

    // 拟对账时间
    private String workDate;

    // 对账人
    private String checkUser;

    // 对账说明
    private String checkNote;

    // 对账时间
    private Date checkDatetime;

    // 调账人
    private String adjustUser;

    // 调账说明
    private String adjustNote;

    // 调账时间
    private Date adjustDatetime;

    // ***********************db properties *************************
    // 业务类型列表
    private List<String> bizTypeList;

    // 查询条件1：创建起始时间
    private Date createDatetimeStart;

    // 查询条件2：创建终止时间
    private Date createDatetimeEnd;

    // 币种列表
    private List<String> currencyList;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getBizType() {
        return bizType;
    }

    public void setBizType(String bizType) {
        this.bizType = bizType;
    }

    public String getBizNote() {
        return bizNote;
    }

    public void setBizNote(String bizNote) {
        this.bizNote = bizNote;
    }

    public BigDecimal getTransAmount() {
        return transAmount;
    }

    public void setTransAmount(BigDecimal transAmount) {
        setTransAmountString(transAmount.toString());
        this.transAmount = transAmount;
    }

    public BigDecimal getPreAmount() {
        return preAmount;
    }

    public void setPreAmount(BigDecimal preAmount) {
        setPreAmountString(preAmount.toString());
        this.preAmount = preAmount;
    }

    public BigDecimal getPostAmount() {
        return postAmount;
    }

    public void setPostAmount(BigDecimal postAmount) {
        setPostAmountString(postAmount.toString());
        this.postAmount = postAmount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getChannelType() {
        return channelType;
    }

    public void setChannelType(String channelType) {
        this.channelType = channelType;
    }

    public String getChannelOrder() {
        return channelOrder;
    }

    public void setChannelOrder(String channelOrder) {
        this.channelOrder = channelOrder;
    }

    public String getRefNo() {
        return refNo;
    }

    public void setRefNo(String refNo) {
        this.refNo = refNo;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Date getCreateDatetime() {
        return createDatetime;
    }

    public void setCreateDatetime(Date createDatetime) {
        this.createDatetime = createDatetime;
    }

    public String getWorkDate() {
        return workDate;
    }

    public void setWorkDate(String workDate) {
        this.workDate = workDate;
    }

    public String getCheckUser() {
        return checkUser;
    }

    public void setCheckUser(String checkUser) {
        this.checkUser = checkUser;
    }

    public String getCheckNote() {
        return checkNote;
    }

    public void setCheckNote(String checkNote) {
        this.checkNote = checkNote;
    }

    public Date getCheckDatetime() {
        return checkDatetime;
    }

    public void setCheckDatetime(Date checkDatetime) {
        this.checkDatetime = checkDatetime;
    }

    public String getAdjustUser() {
        return adjustUser;
    }

    public void setAdjustUser(String adjustUser) {
        this.adjustUser = adjustUser;
    }

    public String getAdjustNote() {
        return adjustNote;
    }

    public void setAdjustNote(String adjustNote) {
        this.adjustNote = adjustNote;
    }

    public Date getAdjustDatetime() {
        return adjustDatetime;
    }

    public void setAdjustDatetime(Date adjustDatetime) {
        this.adjustDatetime = adjustDatetime;
    }

    public List<String> getBizTypeList() {
        return bizTypeList;
    }

    public void setBizTypeList(List<String> bizTypeList) {
        this.bizTypeList = bizTypeList;
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

    public List<String> getCurrencyList() {
        return currencyList;
    }

    public void setCurrencyList(List<String> currencyList) {
        this.currencyList = currencyList;
    }

    public String getTransAmountString() {
        return transAmountString;
    }

    public void setTransAmountString(String transAmountString) {
        this.transAmountString = transAmountString;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

}
