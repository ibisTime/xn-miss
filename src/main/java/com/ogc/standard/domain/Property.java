package com.ogc.standard.domain;

import java.math.BigDecimal;

public class Property {

    private BigDecimal symbol;

    private BigDecimal currency;

    public BigDecimal getSymbol() {
        return symbol;
    }

    public void setSymbol(BigDecimal symbol) {
        this.symbol = symbol;
    }

    public BigDecimal getCurrency() {
        return currency;
    }

    public void setCurrency(BigDecimal currency) {
        this.currency = currency;
    }

}
