package com.ogc.standard.bo;

import java.math.BigDecimal;
import java.util.List;

import com.cdkj.coin.wallet.bo.base.IPaginableBO;
import com.cdkj.coin.wallet.ethereum.CtqEthTransaction;
import com.cdkj.coin.wallet.ethereum.EthAddress;
import com.cdkj.coin.wallet.ethereum.EthTransaction;

public interface IEthTransactionBO extends IPaginableBO<EthTransaction> {

    public int saveEthTransaction(CtqEthTransaction ctqEthTransaction,
            String refNo);

    public List<EthTransaction> queryEthTransactionList(EthTransaction condition);

    public EthTransaction getEthTransaction(String hash);

    // 估算交易价格

    // 广播
    public String broadcast(String from, EthAddress fromSecret, String to,
            BigDecimal value);

    // 获取以太坊gas价格
    public BigDecimal getGasPrice();
}
