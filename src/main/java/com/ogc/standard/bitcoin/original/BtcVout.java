/**
 * @Title BtcVin.java 
 * @Package com.cdkj.coin.wallet.bitcoin 
 * @Description 
 * @author haiqingzheng  
 * @date 2018年8月11日 下午5:10:39 
 * @version V1.0   
 */
package com.ogc.standard.bitcoin.original;

import java.math.BigDecimal;
import java.math.BigInteger;

/** 
 * @author: haiqingzheng 
 * @since: 2018年8月11日 下午5:10:39 
 * @history:
 */
public class BtcVout {

    private BigInteger n;

    private String scriptPubKey;

    private String addr;

    private BigDecimal valueSat;

    private BigDecimal value;

    public BigInteger getN() {
        return n;
    }

    public void setN(BigInteger n) {
        this.n = n;
    }

    public String getScriptPubKey() {
        return scriptPubKey;
    }

    public void setScriptPubKey(String scriptPubKey) {
        this.scriptPubKey = scriptPubKey;
    }

    public String getAddr() {
        return addr;
    }

    public void setAddr(String addr) {
        this.addr = addr;
    }

    public BigDecimal getValueSat() {
        return valueSat;
    }

    public void setValueSat(BigDecimal valueSat) {
        this.valueSat = valueSat;
    }

    public BigDecimal getValue() {
        return value;
    }

    public void setValue(BigDecimal value) {
        this.value = value;
    }

}
