/**
 * @Title IBtcWAddressBO.java 
 * @Package com.ogc.standard.bo 
 * @Description 
 * @author taojian  
 * @date 2018年9月11日 下午8:03:31 
 * @version V1.0   
 */
package com.ogc.standard.bo;

import com.ogc.standard.bo.base.IPaginableBO;
import com.ogc.standard.domain.BtcWAddress;

/** 
 * @author: taojian 
 * @since: 2018年9月11日 下午8:03:31 
 * @history:
 */
public interface IBtcWAddressBO extends IPaginableBO<BtcWAddress> {
    // 导入（保存）地址
    public void saveBtcWAddress(String address);

    public BtcWAddress getBtcWAddressByAddress(String address);

    // 弃用地址
    public int abandon(BtcWAddress btcWAddress);

    public BtcWAddress getBtcWAddress(Long id);

    public BtcWAddress getWBtcAddressToday();

}
