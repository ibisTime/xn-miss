package com.ogc.standard.bitcoin.original;

import java.math.BigDecimal;
import java.util.Date;

import com.ogc.standard.dao.base.ABaseDO;

/**
* 比特币广播记录
* @author: haiqingzheng
* @since: 2018年02月23日 13:08:00
* @history:
*/
public class BtcTransaction extends ABaseDO {

    private static final long serialVersionUID = 7952234649747552766L;

    // 交易ID
    private String txid;

    // 版本
    private String version;

    // 区块高度
    private Long blockheight;

    // 是否是挖矿奖励
    private String coinbase;

    // 区块hash
    private String blockhash;

    // 区块生成时间
    private Date blocktime;

    // 输入总额
    private BigDecimal valuein;

    // 输出总额
    private BigDecimal valueout;

    // 矿工费
    private BigDecimal fees;

    // 输入列表
    private String vin;

    // 输出列表
    private String vout;

    // 关联订单号
    private String refNo;

    public String getTxid() {
        return txid;
    }

    public void setTxid(String txid) {
        this.txid = txid;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public Long getBlockheight() {
        return blockheight;
    }

    public void setBlockheight(Long blockheight) {
        this.blockheight = blockheight;
    }

    public String getCoinbase() {
        return coinbase;
    }

    public void setCoinbase(String coinbase) {
        this.coinbase = coinbase;
    }

    public String getBlockhash() {
        return blockhash;
    }

    public void setBlockhash(String blockhash) {
        this.blockhash = blockhash;
    }

    public Date getBlocktime() {
        return blocktime;
    }

    public void setBlocktime(Date blocktime) {
        this.blocktime = blocktime;
    }

    public BigDecimal getValuein() {
        return valuein;
    }

    public void setValuein(BigDecimal valuein) {
        this.valuein = valuein;
    }

    public BigDecimal getValueout() {
        return valueout;
    }

    public void setValueout(BigDecimal valueout) {
        this.valueout = valueout;
    }

    public BigDecimal getFees() {
        return fees;
    }

    public void setFees(BigDecimal fees) {
        this.fees = fees;
    }

    public String getVin() {
        return vin;
    }

    public void setVin(String vin) {
        this.vin = vin;
    }

    public String getVout() {
        return vout;
    }

    public void setVout(String vout) {
        this.vout = vout;
    }

    public String getRefNo() {
        return refNo;
    }

    public void setRefNo(String refNo) {
        this.refNo = refNo;
    }

}
