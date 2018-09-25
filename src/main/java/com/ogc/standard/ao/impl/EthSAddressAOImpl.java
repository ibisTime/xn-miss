/**
 * @Title EthMAddressAOImpl.java 
 * @Package com.cdkj.coin.ao.impl 
 * @Description 
 * @author leo(haiqing)  
 * @date 2017年10月27日 下午5:43:34 
 * @version V1.0   
 */
package com.ogc.standard.ao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ogc.standard.ao.IEthSAddressAO;
import com.ogc.standard.bo.IEthSAddressBO;
import com.ogc.standard.bo.base.Paginable;
import com.ogc.standard.domain.EthSAddress;
import com.ogc.standard.enums.EErrorCode_main;
import com.ogc.standard.enums.ESAddressStatus;
import com.ogc.standard.exception.BizException;

/** 
 * @author: haiqingzheng 
 * @since: 2017年10月27日 下午5:43:34 
 * @history:
 */
@Service
public class EthSAddressAOImpl implements IEthSAddressAO {

    @Autowired
    private IEthSAddressBO ethSAddressBO;

    @Override
    public String generate() {
        return ethSAddressBO.generate();
    }

    @Override
    public void abandon(Long id) {
        EthSAddress ethSAddress = ethSAddressBO.getEthSAddress(id);
        if (ESAddressStatus.INVALID.getCode().equals(ethSAddress.getStatus())) {
            throw new BizException(EErrorCode_main.coin_INVALIDATE.getCode());
        }
        ethSAddressBO.abandon(ethSAddress);
    }

    @Override
    public Paginable<EthSAddress> queryEthSAddressPage(int start, int limit,
            EthSAddress condition) {
        return ethSAddressBO.getPaginable(start, limit, condition);
    }

}
