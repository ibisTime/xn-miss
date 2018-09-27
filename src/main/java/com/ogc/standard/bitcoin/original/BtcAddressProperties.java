/**
 * @Title BtcAddressProperties.java 
 * @Package com.cdkj.coin.wallet.bitcoin 
 * @Description 
 * @author haiqingzheng  
 * @date 2018年8月11日 上午9:41:42 
 * @version V1.0   
 */
package com.ogc.standard.bitcoin.original;

import java.math.BigDecimal;

/** 
 * @author: haiqingzheng 
 * @since: 2018年8月11日 上午9:41:42 
 * @history:
 */
public class BtcAddressProperties {

    private BigDecimal balance = BigDecimal.ZERO;

    private BigDecimal totalReceived = BigDecimal.ZERO;

    private BigDecimal totalSent = BigDecimal.ZERO;

    private BigDecimal unconfirmedBalance = BigDecimal.ZERO;

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public BigDecimal getTotalReceived() {
        return totalReceived;
    }

    public void setTotalReceived(BigDecimal totalReceived) {
        this.totalReceived = totalReceived;
    }

    public BigDecimal getTotalSent() {
        return totalSent;
    }

    public void setTotalSent(BigDecimal totalSent) {
        this.totalSent = totalSent;
    }

    public BigDecimal getUnconfirmedBalance() {
        return unconfirmedBalance;
    }

    public void setUnconfirmedBalance(BigDecimal unconfirmedBalance) {
        this.unconfirmedBalance = unconfirmedBalance;
    }

}
