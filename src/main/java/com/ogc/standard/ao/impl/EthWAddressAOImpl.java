/**
 * @Title EthWAddressAOImpl.java 
 * @Package com.cdkj.coin.ao.impl 
 * @Description 
 * @author leo(haiqing)  
 * @date 2017年10月27日 下午5:43:34 
 * @version V1.0   
 */
package com.ogc.standard.ao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.web3j.crypto.WalletUtils;

import com.ogc.standard.ao.IEthWAddressAO;
import com.ogc.standard.bo.IEthWAddressBO;
import com.ogc.standard.bo.base.Paginable;
import com.ogc.standard.domain.EthWAddress;
import com.ogc.standard.enums.EErrorCode_main;
import com.ogc.standard.enums.EWAddressStatus;
import com.ogc.standard.exception.BizException;

/** 
 * @author: haiqingzheng 
 * @since: 2017年10月27日 下午5:43:34 
 * @history:
 */
@Service
public class EthWAddressAOImpl implements IEthWAddressAO {

    @Autowired
    private IEthWAddressBO ethWAddressBO;

    @Override
    public void importWAddress(String address) {
        EthWAddress ethWAddress = ethWAddressBO
            .getEthWAddressByAddress(address);
        if (ethWAddress != null) {
            throw new BizException(
                EErrorCode_main.coin_WADDRESSEXIST.getCode(), (Object) address);
        }
        // 地址有效性校验
        if (!WalletUtils.isValidAddress(address)) {
            throw new BizException(
                EErrorCode_main.coin_WADDRESSEXIST.getCode(), (Object) address);
        }
        ethWAddressBO.saveEthWAddress(address);
    }

    @Override
    public void abandon(Long id) {
        EthWAddress ethWAddress = ethWAddressBO.getEthWAddress(id);
        if (EWAddressStatus.INVALID.getCode().equals(ethWAddress.getStatus())) {
            throw new BizException(EErrorCode_main.coin_INVALIDATE.getCode());
        }
        ethWAddressBO.abandon(ethWAddress);
    }

    @Override
    public Paginable<EthWAddress> queryEthWAddressPage(int start, int limit,
            EthWAddress condition) {
        Paginable<EthWAddress> results = ethWAddressBO.getPaginable(start,
            limit, condition);
        return results;
    }

}
