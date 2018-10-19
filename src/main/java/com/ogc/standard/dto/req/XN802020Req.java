package com.ogc.standard.dto.req;

/**
 * 绑定银行卡
 * @author: lei 
 * @since: 2018年9月11日 下午5:30:58 
 * @history:
 */
public class XN802020Req {

    // 银行编号
    private String bankCode;

    // 卡号（银行卡必填）
    private String bankcardNumber;

    // 银行名称（必填）
    private String bankName;

    // 支行名称（必填）
    private String subbranch;

    // 初始余额
    private String amount;

    // 绑定手机号（必填）
    private String bindMobile;

    // 用户编号（必填）
    private String userId;

    // 账户名称（必填）
    private String realName;

    // 类型（必填）
    private String type;

    // 备注（选填）
    private String remark;

    public String getBankCode() {
        return bankCode;
    }

    public void setBankCode(String bankCode) {
        this.bankCode = bankCode;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getSubbranch() {
        return subbranch;
    }

    public void setSubbranch(String subbranch) {
        this.subbranch = subbranch;
    }

    public String getBindMobile() {
        return bindMobile;
    }

    public void setBindMobile(String bindMobile) {
        this.bindMobile = bindMobile;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getBankcardNumber() {
        return bankcardNumber;
    }

    public void setBankcardNumber(String bankcardNumber) {
        this.bankcardNumber = bankcardNumber;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

}
