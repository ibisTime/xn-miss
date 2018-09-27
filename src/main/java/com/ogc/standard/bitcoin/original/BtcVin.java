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
public class BtcVin {

    private String txid;

    private BigInteger n;

    private BigInteger vout;

    private String addr;

    private BigDecimal valueSat;

    private BigDecimal value;

    public String getTxid() {
        return txid;
    }

    public void setTxid(String txid) {
        this.txid = txid;
    }

    public BigInteger getN() {
        return n;
    }

    public void setN(BigInteger n) {
        this.n = n;
    }

    public BigInteger getVout() {
        return vout;
    }

    public void setVout(BigInteger vout) {
        this.vout = vout;
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
