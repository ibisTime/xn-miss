package com.ogc.standard.domain;

import java.math.BigDecimal;
import java.util.Date;

import com.ogc.standard.dao.base.ABaseDO;

/**
* 定存订单
* @author: haiqingzheng
* @since: 2017年11月09日 14:26:36
* @history:
*/
public class Deposit extends ABaseDO {

    private static final long serialVersionUID = 1L;

    // 编号
    private String code;

    // 币种
    private String currency;

    // 付款地址
    private String fromAddress;

    // 收款地址
    private String toAddress;

    // 定存数量
    private BigDecimal amount;

    // 定存类型 0 散取地址定存 1矿工费补给地址定存
    private String type;

    // 交易hash
    private String txHash;

    // 矿工费
    private BigDecimal txFee;

    // 网络记账时间
    private Date confirmDatetime;

    // 创建时间
    private Date createDatetime;

    // 订单编号模糊查询
    private String codeForQuery;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getFromAddress() {
        return fromAddress;
    }

    public void setFromAddress(String fromAddress) {
        this.fromAddress = fromAddress;
    }

    public String getToAddress() {
        return toAddress;
    }

    public void setToAddress(String toAddress) {
        this.toAddress = toAddress;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTxHash() {
        return txHash;
    }

    public void setTxHash(String txHash) {
        this.txHash = txHash;
    }

    public BigDecimal getTxFee() {
        return txFee;
    }

    public void setTxFee(BigDecimal txFee) {
        this.txFee = txFee;
    }

    public Date getConfirmDatetime() {
        return confirmDatetime;
    }

    public void setConfirmDatetime(Date confirmDatetime) {
        this.confirmDatetime = confirmDatetime;
    }

    public Date getCreateDatetime() {
        return createDatetime;
    }

    public void setCreateDatetime(Date createDatetime) {
        this.createDatetime = createDatetime;
    }

    public String getCodeForQuery() {
        return codeForQuery;
    }

    public void setCodeForQuery(String codeForQuery) {
        this.codeForQuery = codeForQuery;
    }

}
