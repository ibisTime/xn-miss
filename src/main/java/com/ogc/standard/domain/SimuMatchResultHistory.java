package com.ogc.standard.domain;

import java.math.BigDecimal;
import java.util.Date;

import com.ogc.standard.dao.base.ABaseDO;

/**
* 撮合结果历史
* @author: lei
* @since: 2018年09月02日 下午03:13:42
* @history:
*/
public class SimuMatchResultHistory extends ABaseDO {

    private static final long serialVersionUID = 1L;

    // 撮合编号
    private Long id;

    // 委托单买单编号
    private String buyOrderCode;

    // 委托单卖单编号
    private String sellOrderCode;

    // 成交单买单编号
    private String buyOrderDetailCode;

    // 成交单卖单编号
    private String sellOrderDetailCode;

    // 交易币种
    private String symbol;

    // 计价币种
    private String toSymbol;

    // 买方用户编号
    private String buyUserId;

    // 卖方用户编号
    private String sellUserId;

    // 兑换比例(toSymbol)
    private BigDecimal exchangeRate;

    // 交易币种数量(symbol)
    private BigDecimal symbolCount;

    // 计价币种数量(toSymbol)
    private BigDecimal toSymbolCount;

    // 买方手续费
    private BigDecimal buyFee;

    // 卖方手续费
    private BigDecimal sellFee;

    // 撮合时间
    private Date createDatetime;

    // ******************db properties**************

    // 成交时间起
    private Date createDatetimeStart;

    // 成交时间止
    private Date createDatetimeEnd;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBuyOrderCode() {
        return buyOrderCode;
    }

    public void setBuyOrderCode(String buyOrderCode) {
        this.buyOrderCode = buyOrderCode;
    }

    public String getSellOrderCode() {
        return sellOrderCode;
    }

    public void setSellOrderCode(String sellOrderCode) {
        this.sellOrderCode = sellOrderCode;
    }

    public String getBuyOrderDetailCode() {
        return buyOrderDetailCode;
    }

    public void setBuyOrderDetailCode(String buyOrderDetailCode) {
        this.buyOrderDetailCode = buyOrderDetailCode;
    }

    public String getSellOrderDetailCode() {
        return sellOrderDetailCode;
    }

    public void setSellOrderDetailCode(String sellOrderDetailCode) {
        this.sellOrderDetailCode = sellOrderDetailCode;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getToSymbol() {
        return toSymbol;
    }

    public void setToSymbol(String toSymbol) {
        this.toSymbol = toSymbol;
    }

    public String getBuyUserId() {
        return buyUserId;
    }

    public void setBuyUserId(String buyUserId) {
        this.buyUserId = buyUserId;
    }

    public String getSellUserId() {
        return sellUserId;
    }

    public void setSellUserId(String sellUserId) {
        this.sellUserId = sellUserId;
    }

    public BigDecimal getExchangeRate() {
        return exchangeRate;
    }

    public void setExchangeRate(BigDecimal exchangeRate) {
        this.exchangeRate = exchangeRate;
    }

    public BigDecimal getSymbolCount() {
        return symbolCount;
    }

    public void setSymbolCount(BigDecimal symbolCount) {
        this.symbolCount = symbolCount;
    }

    public BigDecimal getToSymbolCount() {
        return toSymbolCount;
    }

    public void setToSymbolCount(BigDecimal toSymbolCount) {
        this.toSymbolCount = toSymbolCount;
    }

    public BigDecimal getBuyFee() {
        return buyFee;
    }

    public void setBuyFee(BigDecimal buyFee) {
        this.buyFee = buyFee;
    }

    public BigDecimal getSellFee() {
        return sellFee;
    }

    public void setSellFee(BigDecimal sellFee) {
        this.sellFee = sellFee;
    }

    public Date getCreateDatetime() {
        return createDatetime;
    }

    public void setCreateDatetime(Date createDatetime) {
        this.createDatetime = createDatetime;
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

}
