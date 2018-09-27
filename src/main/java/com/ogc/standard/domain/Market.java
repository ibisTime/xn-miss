package com.ogc.standard.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.ogc.standard.dao.base.ABaseDO;

public class Market extends ABaseDO implements Serializable {

    private static final long serialVersionUID = -1085440748192863387L;

    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    private String symbol;

    private String referCurrency;

    private String origin;

    private BigDecimal lastPrice;// Last

    private BigDecimal percentChange24h;// 24h

    private BigDecimal bid;// Bid

    private BigDecimal ask;// Ask

    private BigDecimal mid;// Mid

    private BigDecimal low;// Low

    private BigDecimal high;// High

    private String volume;// Volume

    private Date updateDatetime;

    private String coinmarketcapId;

    private List<String> coinList;

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getReferCurrency() {
        return referCurrency;
    }

    public void setReferCurrency(String referCurrency) {
        this.referCurrency = referCurrency;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public BigDecimal getLastPrice() {
        return lastPrice;
    }

    public void setLastPrice(BigDecimal lastPrice) {
        this.lastPrice = lastPrice;
    }

    public BigDecimal getPercentChange24h() {
        return percentChange24h;
    }

    public void setPercentChange24h(BigDecimal percentChange24h) {
        this.percentChange24h = percentChange24h;
    }

    public BigDecimal getBid() {
        return bid;
    }

    public void setBid(BigDecimal bid) {
        this.bid = bid;
    }

    public BigDecimal getAsk() {
        return ask;
    }

    public void setAsk(BigDecimal ask) {
        this.ask = ask;
    }

    public BigDecimal getMid() {
        return mid;
    }

    public void setMid(BigDecimal mid) {
        this.mid = mid;
    }

    public BigDecimal getLow() {
        return low;
    }

    public void setLow(BigDecimal low) {
        this.low = low;
    }

    public BigDecimal getHigh() {
        return high;
    }

    public void setHigh(BigDecimal high) {
        this.high = high;
    }

    public String getVolume() {
        return volume;
    }

    public void setVolume(String volume) {
        this.volume = volume;
    }

    public Date getUpdateDatetime() {
        return updateDatetime;
    }

    public void setUpdateDatetime(Date updateDatetime) {
        this.updateDatetime = updateDatetime;
    }

    public String getCoinmarketcapId() {
        return coinmarketcapId;
    }

    public void setCoinmarketcapId(String coinmarketcapId) {
        this.coinmarketcapId = coinmarketcapId;
    }

    public List<String> getCoinList() {
        return coinList;
    }

    public void setCoinList(List<String> coinList) {
        this.coinList = coinList;
    }

}
