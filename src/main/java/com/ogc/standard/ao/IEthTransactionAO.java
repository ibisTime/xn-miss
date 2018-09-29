/**
 * @Title IEthTransactionAO.java 
 * @Package com.cdkj.coin.ao 
 * @Description 
 * @author leo(haiqing)  
 * @date 2017年11月7日 下午8:32:00 
 * @version V1.0   
 */
package com.ogc.standard.ao;

import com.ogc.standard.bo.base.Paginable;
import com.ogc.standard.domain.CtqEthTransaction;
import com.ogc.standard.domain.EthTransaction;
import com.ogc.standard.domain.TokenEvent;

/** 
 * @author: haiqingzheng 
 * @since: 2017年11月7日 下午8:32:00 
 * @history:
 */
public interface IEthTransactionAO {

    // ETH充值
    public String ethChargeNotice(CtqEthTransaction ctqEthTransaction);

    public String tokenChargeNotice(CtqEthTransaction ctqEthTransaction,
            TokenEvent tokenEvent);

    //
    // // 提现
    // public void withdrawNotice(CtqEthTransaction ctqEthTransaction);
    //
    // 归集
    public void collection(String address, String chargeCode, String symbol);

    //
    // // 归集交易通知处理
    // public void collectionNotice(CtqEthTransaction ctqEthTransaction);
    //
    // 分页查询广播记录
    public Paginable<EthTransaction> queryEthTransactionPage(int start,
            int limit, EthTransaction condition);

    // ETH每日定存
    public void ethDepositNotice(CtqEthTransaction ctqEthTransaction);

    // toke 每日定存
    public void tokenDepositNotice(CtqEthTransaction ctqEthTransaction,
            TokenEvent tokenEvent);

    // 归集地址每日定存
    public void ethSupplyDepositNotice(CtqEthTransaction ctqEthTransaction);

    // 直接落地，不进行充值逻辑
    public void addTransaction(CtqEthTransaction ctqEthTransaction);
}
