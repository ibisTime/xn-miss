/**
 * @Title IEthTransactionAO.java 
 * @Package com.cdkj.coin.ao 
 * @Description 
 * @author leo(haiqing)  
 * @date 2017年11月7日 下午8:32:00 
 * @version V1.0   
 */
package com.ogc.standard.ao;

import java.math.BigDecimal;

import com.ogc.standard.bo.base.Paginable;
import com.ogc.standard.domain.BtcUtxo;
import com.ogc.standard.domain.CtqBtcUtxo;
import com.ogc.standard.enums.EAddressType;

/** 
 * @author: haiqingzheng 
 * @since: 2017年11月7日 下午8:32:00 
 * @history:
 */
public interface IBtcUtxoAO {

    // 充值
    public String chargeNotice(CtqBtcUtxo ctqBtcUtxo);

    // 提现
    public void withdrawNotice(CtqBtcUtxo ctqBtcUtxo);

    // 充值触发的自动归集
    public void collection(String chargeCode);

    // 归集交易通知处理
    public void collectionNotice(CtqBtcUtxo ctqBtcUtxo);

    // 分页查询UTXO
    public Paginable<BtcUtxo> queryBtcUtxoPage(int start, int limit,
            BtcUtxo condition);

    // 每日定存
    public void depositNotice(CtqBtcUtxo ctqBtcUtxo);

    // 可花费的UTXO总和
    public BigDecimal getTotalEnableUTXOCount(EAddressType addressType);
}
