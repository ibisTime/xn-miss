package com.ogc.standard.dto.req;

import org.hibernate.validator.constraints.NotBlank;

public class XN626140Req {

    @NotBlank
    private String address;

    @NotBlank
    private String symbol;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

}
