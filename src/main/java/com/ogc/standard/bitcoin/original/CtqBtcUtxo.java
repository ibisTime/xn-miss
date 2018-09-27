package com.ogc.standard.bitcoin.original;

import java.math.BigDecimal;
import java.util.Date;

import com.ogc.standard.dao.base.ABaseDO;

/**
 * @author: haiqingzheng 
 * @since: 2018年2月6日 下午8:53:29 
 * @history:
 */
public class CtqBtcUtxo extends ABaseDO {

    private static final long serialVersionUID = 3556545568582185031L;

    private Long id;

    private String txid;

    private Integer vout;

    private BigDecimal count;

    private String scriptPubKey;

    private String address;

    private Date syncTime;

    private Long blockHeight;

    private String status;

    public String getRefNo() {
        return txid + "|" + vout;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getCount() {
        return count;
    }

    public void setCount(BigDecimal count) {
        this.count = count;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Date getSyncTime() {
        return syncTime;
    }

    public void setSyncTime(Date syncTime) {
        this.syncTime = syncTime;
    }

    public Long getBlockHeight() {
        return blockHeight;
    }

    public String getTxid() {
        return txid;
    }

    public void setTxid(String txid) {
        this.txid = txid;
    }

    public Integer getVout() {
        return vout;
    }

    public void setVout(Integer vout) {
        this.vout = vout;
    }

    public void setBlockHeight(Long blockHeight) {
        this.blockHeight = blockHeight;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getScriptPubKey() {
        return scriptPubKey;
    }

    public void setScriptPubKey(String scriptPubKey) {
        this.scriptPubKey = scriptPubKey;
    }

}
