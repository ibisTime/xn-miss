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

import com.ogc.standard.ao.IEthMAddressAO;
import com.ogc.standard.bo.IEthMAddressBO;
import com.ogc.standard.bo.base.Paginable;
import com.ogc.standard.domain.EthMAddress;
import com.ogc.standard.enums.EErrorCode_main;
import com.ogc.standard.enums.EWAddressStatus;
import com.ogc.standard.exception.BizException;

/** 
 * @author: haiqingzheng 
 * @since: 2017年10月27日 下午5:43:34 
 * @history:
 */
@Service
public class EthMAddressAOImpl implements IEthMAddressAO {

    @Autowired
    private IEthMAddressBO ethMAddressBO;

    @Override
    public String generate() {
        return ethMAddressBO.generate();
    }

    @Override
    public void abandon(Long id) {
        EthMAddress ethMAddress = ethMAddressBO.getEthMAddress(id);
        if (EWAddressStatus.INVALID.getCode().equals(ethMAddress.getStatus())) {
            throw new BizException(EErrorCode_main.coin_INVALIDATE.getCode());
        }
        ethMAddressBO.abandon(ethMAddress);
    }

    @Override
    public Paginable<EthMAddress> queryEthMAddressPage(int start, int limit,
            EthMAddress condition) {
        return ethMAddressBO.getPaginable(start, limit, condition);
    }

}
