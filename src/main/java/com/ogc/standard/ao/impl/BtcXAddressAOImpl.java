/**
 * @Title BtcXAddressAOImpl.java 
 * @Package com.ogc.standard.ao.impl 
 * @Description 
 * @author taojian  
 * @date 2018年9月11日 下午2:10:44 
 * @version V1.0   
 */
package com.ogc.standard.ao.impl;

import org.springframework.beans.factory.annotation.Autowired;

import com.ogc.standard.ao.IBtcXAddressAO;
import com.ogc.standard.bo.IBtcXAddressBO;
import com.ogc.standard.bo.base.Paginable;
import com.ogc.standard.domain.BtcXAddress;

/** 
 * @author: taojian 
 * @since: 2018年9月11日 下午2:10:44 
 * @history:
 */
public class BtcXAddressAOImpl implements IBtcXAddressAO {

    @Autowired
    private IBtcXAddressBO btcXAddressBO;

    @Override
    public Paginable<BtcXAddress> queryBtcXAddressPage(int start, int limit,
            BtcXAddress condition) {
        Paginable<BtcXAddress> result = btcXAddressBO.getPaginable(start, limit,
            condition);
        return result;
    }

}
