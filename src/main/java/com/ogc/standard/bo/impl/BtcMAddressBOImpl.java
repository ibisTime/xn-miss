/**
 * @Title BtcMAddressBOImpl.java 
 * @Package com.ogc.standard.bo.impl 
 * @Description 
 * @author taojian  
 * @date 2018年9月11日 下午2:57:02 
 * @version V1.0   
 */
package com.ogc.standard.bo.impl;

import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ogc.standard.bo.IBtcMAddressBO;
import com.ogc.standard.bo.base.PaginableBOImpl;
import com.ogc.standard.core.BtcClient;
import com.ogc.standard.dao.IBtcMAddressDAO;
import com.ogc.standard.domain.BtcMAddress;
import com.ogc.standard.enums.EMAddressStatus;

/** 
 * @author: taojian 
 * @since: 2018年9月11日 下午2:57:02 
 * @history:
 */
@Component
public class BtcMAddressBOImpl extends PaginableBOImpl<BtcMAddress>
        implements IBtcMAddressBO {

    @Autowired
    private IBtcMAddressDAO btcMAddressDAO;

    @Override
    public long getTotalCount(BtcMAddress condition) {
        return btcMAddressDAO.selectTotalCount(condition);
    }

    @Override
    public String saveAddress() {
        BtcMAddress data = new BtcMAddress();
        Date now = new Date();
        String newAddress = BtcClient.getSingleAddress().getAddress();
        String privatekey = BtcClient.getSingleAddress().getPrivatekey();
        data.setAddress(newAddress);
        data.setPrivatekey(privatekey);
        data.setCreateDatetime(now);
        data.setStatus(EMAddressStatus.VALID.getCode());
        btcMAddressDAO.insert(data);

        return newAddress;
    }

    @Override
    public BtcMAddress getBtcAddress(String address) {
        BtcMAddress data = null;
        BtcMAddress condition = new BtcMAddress();
        condition.setAddress(address);
        List<BtcMAddress> results = btcMAddressDAO.selectList(condition);
        if (CollectionUtils.isNotEmpty(results)) {
            data = results.get(0);
        }
        return data;
    }

    @Override
    public int refreshStatus(Long id) {
        int count = 0;
        BtcMAddress data = getAddressById(id);
        if (data != null) {
            data.setStatus(EMAddressStatus.INVALID.getCode());
            count = btcMAddressDAO.updateStatus(data);
        }
        return count;
    }

    @Override
    public BtcMAddress getAddressById(Long id) {
        BtcMAddress condition = new BtcMAddress();
        condition.setId(id);
        return btcMAddressDAO.select(condition);
    }

}
