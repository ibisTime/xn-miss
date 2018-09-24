/**
 * @Title BtcMAddress.java 
 * @Package com.ogc.standard.ao.impl 
 * @Description 
 * @author taojian  
 * @date 2018年9月11日 下午3:23:56 
 * @version V1.0   
 */
package com.ogc.standard.ao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ogc.standard.ao.IBtcMAddressAO;
import com.ogc.standard.bo.IBtcMAddressBO;
import com.ogc.standard.bo.base.Paginable;
import com.ogc.standard.domain.BtcMAddress;
import com.ogc.standard.enums.EErrorCode_main;
import com.ogc.standard.enums.EMAddressStatus;
import com.ogc.standard.exception.BizException;

/** 
 * @author: taojian 
 * @since: 2018年9月11日 下午3:23:56 
 * @history:
 */
@Service
public class BtcMAddressAOImpl implements IBtcMAddressAO {

    @Autowired
    private IBtcMAddressBO btcMAddressBO;

    @Override
    public String addAddress() {
        return btcMAddressBO.saveAddress();
    }

    @Override
    public void abandon(Long id) {
        BtcMAddress btcMAddress = btcMAddressBO.getAddressById(id);
        if (btcMAddress == null) {
            throw new BizException(
                EErrorCode_main.coin_ADDRESSNOTEXIST.getCode());
        }
        if (EMAddressStatus.INVALID.getCode().equals(btcMAddress.getStatus())) {
            throw new BizException(EErrorCode_main.coin_INVALIDATE.getCode());
        }
        btcMAddressBO.refreshStatus(id);

    }

    @Override
    public Paginable<BtcMAddress> queryBtcMAddressPage(int start, int limit,
            BtcMAddress condition) {
        return btcMAddressBO.getPaginable(start, limit, condition);
    }

}
