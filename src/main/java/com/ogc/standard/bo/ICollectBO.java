package com.ogc.standard.bo;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.ogc.standard.bo.base.IPaginableBO;
import com.ogc.standard.domain.Coin;
import com.ogc.standard.domain.Collect;
import com.ogc.standard.domain.EthXAddress;

public interface ICollectBO extends IPaginableBO<Collect> {

    public String saveCollect(String symbol, String from, String to,
            BigDecimal value, String txHash, String refNo, String coinType);

    public List<Collect> queryCollectList(Collect condition);

    public Collect getCollect(String code);

    public Collect getCollectByTxHash(String txHash);

    public Collect getCollectByRefNo(String refNo);

    //
    public void doETHCollect(EthXAddress ethAddress, String chargeCode,
            BigDecimal balance);

    public int colectNoticeETH(Collect data, BigDecimal txfee,
            Date ethDatetime);

    public int colectNoticeBTC(Collect data, BigDecimal txfee,
            Date btcDatetime);
    //
    // public int colectionNoticeSC(Collect data, String fromAddress,
    // BigDecimal txfee, Date ethDatetime);
    //
    // public int colectionNoticeWAN(Collect data, BigDecimal txfee,
    // Date wanDatetime);
    //
    // // 归集地址使用次数及归集总额查询
    // public EthWAddress getAddressUseInfo(String toAddress, String currency);
    //
    // public BigDecimal getTotalCollect(String currency);

    // 批量插入待归集的token订单
    public void saveToCollectList(Coin coin,
            List<EthXAddress> tokenAddresseList, String to, String refNo);

    // 归集之前，补给矿工费广播成功
    public void collectFeeBroadcastSuccess(Collect collect, String preFrom,
            BigDecimal preAmount, String preTxHash);

    // 归集广播成功
    public void collectBroadcastSuccess(Collect collect, BigDecimal amount,
            String txHash);

    // 归集失败
    public void collectFailed(Collect collect, String remark);

    // 归集之前，补给矿工费交易成功
    public void collectFeeTxSuccess(Collect collection, BigDecimal preTxFee,
            Date preConfirmDatetime);

    // 归集交易成功
    public void collectTxSuccess(Collect collect, BigDecimal txFee,
            Date confirmDatetime);
}
