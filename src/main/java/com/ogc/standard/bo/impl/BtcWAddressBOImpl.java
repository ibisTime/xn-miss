/**
 * @Title BtcWAddressBOImpl.java 
 * @Package com.ogc.standard.bo.impl 
 * @Description 
 * @author taojian  
 * @date 2018年9月11日 下午8:06:15 
 * @version V1.0   
 */
package com.ogc.standard.bo.impl;

import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ogc.standard.bo.IBtcWAddressBO;
import com.ogc.standard.bo.base.PaginableBOImpl;
import com.ogc.standard.dao.IBtcWAddressDAO;
import com.ogc.standard.domain.BtcWAddress;
import com.ogc.standard.enums.EErrorCode_main;
import com.ogc.standard.enums.EWAddressStatus;
import com.ogc.standard.exception.BizException;

/** 
 * @author: taojian 
 * @since: 2018年9月11日 下午8:06:15 
 * @history:
 */
@Component
public class BtcWAddressBOImpl extends PaginableBOImpl<BtcWAddress>
        implements IBtcWAddressBO {
    @Autowired
    private IBtcWAddressDAO btcWAddressDAO;

    @Override
    public void saveBtcWAddress(String address) {
        BtcWAddress data = new BtcWAddress();
        data.setAddress(address);
        data.setCreateDatetime(new Date());
        data.setStatus(EWAddressStatus.VALID.getCode());
        btcWAddressDAO.insert(data);
    }

    @Override
    public BtcWAddress getBtcWAddressByAddress(String address) {
        BtcWAddress data = null;
        BtcWAddress condition = new BtcWAddress();
        condition.setAddress(address);
        List<BtcWAddress> results = btcWAddressDAO.selectList(condition);
        if (CollectionUtils.isNotEmpty(results)) {
            data = results.get(0);
        }
        return data;
    }

    @Override
    public int abandon(BtcWAddress btcWAddress) {
        int count = 0;
        if (btcWAddress != null) {
            btcWAddress.setStatus(EWAddressStatus.INVALID.getCode());
            count = btcWAddressDAO.updateStatus(btcWAddress);
        }
        return count;
    }

    @Override
    public BtcWAddress getBtcWAddress(Long id) {
        BtcWAddress data = null;
        BtcWAddress condition = new BtcWAddress();
        if (id != null) {
            condition.setId(id);
            data = btcWAddressDAO.select(condition);
        }
        return data;
    }

    @Override
    public BtcWAddress getWBtcAddressToday() {
        BtcWAddress condition = new BtcWAddress();
        condition.setStatus(EWAddressStatus.VALID.getCode());
        condition.setOrder("create_datetime", "desc");
        List<BtcWAddress> wList = btcWAddressDAO.selectList(condition);
        if (CollectionUtils.isEmpty(wList)) {
            throw new BizException(EErrorCode_main.btc_BROADCAST.getCode());
        }
        return wList.get(0);
    }

}
