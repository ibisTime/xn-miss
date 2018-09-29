package com.ogc.standard.domain;

import java.math.BigDecimal;
import java.util.Date;

import com.ogc.standard.dao.base.ABaseDO;

/**
 * 交易对
 * @author: lei 
 * @since: 2018年8月22日 下午4:17:39 
 * @history:
 */
public class ExchangePair extends ABaseDO {

    private static final long serialVersionUID = 1L;

    // ID
    private String id;

    // 交易所英文名
    private String exchangeEname;

    // 基础币种
    private String symbol;

    // 计价币种
    private String toSymbol;

    // 计价币种
    private String pair;

    // 交易对
    private BigDecimal price;

    // 更新人
    private String updater;

    // 更新时间
    private Date updateDatetime;

    // **************************db properties **************************

    // 法币价格
    private BigDecimal currencyPrice;

    // 涨幅
    private BigDecimal exchangeRate;

    // 日线
    private SimuKLine dayLineInfo;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getExchangeEname() {
        return exchangeEname;
    }

    public void setExchangeEname(String exchangeEname) {
        this.exchangeEname = exchangeEname;
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

    public String getPair() {
        return pair;
    }

    public void setPair(String pair) {
        this.pair = pair;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getUpdater() {
        return updater;
    }

    public void setUpdater(String updater) {
        this.updater = updater;
    }

    public Date getUpdateDatetime() {
        return updateDatetime;
    }

    public void setUpdateDatetime(Date updateDatetime) {
        this.updateDatetime = updateDatetime;
    }

    public BigDecimal getCurrencyPrice() {
        return currencyPrice;
    }

    public void setCurrencyPrice(BigDecimal currencyPrice) {
        this.currencyPrice = currencyPrice;
    }

    public BigDecimal getExchangeRate() {
        return exchangeRate;
    }

    public void setExchangeRate(BigDecimal exchangeRate) {
        this.exchangeRate = exchangeRate;
    }

    public SimuKLine getDayLineInfo() {
        return dayLineInfo;
    }

    public void setDayLineInfo(SimuKLine dayLineInfo) {
        this.dayLineInfo = dayLineInfo;
    }

}
