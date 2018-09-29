package com.ogc.standard.bo.impl;

import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ogc.standard.bo.ICtqBO;
import com.ogc.standard.bo.IEthSAddressBO;
import com.ogc.standard.bo.base.PaginableBOImpl;
import com.ogc.standard.core.EthClient;
import com.ogc.standard.dao.IEthSAddressDAO;
import com.ogc.standard.domain.EthSAddress;
import com.ogc.standard.domain.EthXAddress;
import com.ogc.standard.enums.EAddressType;
import com.ogc.standard.enums.ESAddressStatus;

@Component
public class EthSAddressBOImpl extends PaginableBOImpl<EthSAddress> implements
        IEthSAddressBO {

    @Autowired
    private IEthSAddressDAO ethSAddressDAO;

    @Autowired
    private ICtqBO ctqBO;

    @Override
    public String generate() {

        EthXAddress newAddress = EthClient.newAccount();

        EthSAddress data = new EthSAddress();
        data.setAddress(newAddress.getAddress());
        data.setKeystorePwd(newAddress.getKeystorePwd());
        data.setKeystoreName(newAddress.getKeystoreName());
        data.setKeystoreContent(newAddress.getKeystoreContent());
        data.setStatus(ESAddressStatus.VALID.getCode());
        data.setCreateDatetime(new Date());
        ethSAddressDAO.insert(data);

        // 补给地址，通知ctq监听
        ctqBO.uploadEthAddress(data.getAddress(), EAddressType.S.getCode());
        return newAddress.getAddress();

    }

    @Override
    public int abandon(EthSAddress EthSAddress) {
        int count = 0;
        if (EthSAddress != null) {
            Date now = new Date();
            EthSAddress.setStatus(ESAddressStatus.INVALID.getCode());
            EthSAddress.setAbandonDatetime(now);
            ethSAddressDAO.updateAbandon(EthSAddress);
        }
        return count;
    }

    @Override
    public EthSAddress getEthSAddress(Long id) {
        EthSAddress data = null;

        if (id != null) {
            EthSAddress condition = new EthSAddress();
            condition.setId(id);
            data = ethSAddressDAO.select(condition);
        }

        return data;
    }

    @Override
    public EthSAddress getEthSAddressSecret(Long id) {
        EthSAddress data = null;

        if (id != null) {
            EthSAddress condition = new EthSAddress();
            condition.setId(id);
            data = ethSAddressDAO.selectSecret(condition);
        }

        return data;
    }

    @Override
    public EthSAddress getEthSAddressByAddress(String address) {
        EthSAddress data = null;
        EthSAddress condition = new EthSAddress();
        condition.setAddress(address);
        List<EthSAddress> results = ethSAddressDAO.selectList(condition);
        if (CollectionUtils.isNotEmpty(results)) {
            data = results.get(0);
        }
        return data;
    }

    @Override
    public EthSAddress getNormalEthSAddressByAddress(String address) {
        EthSAddress data = null;
        if (null != address) {
            EthSAddress condition = new EthSAddress();
            condition.setAddress(address);
            // condition.setStatus();
            List<EthSAddress> results = ethSAddressDAO.selectList(condition);
            if (CollectionUtils.isNotEmpty(results)) {
                data = results.get(0);
            }
        }

        return data;
    }

    @Override
    public EthSAddress getEnableSEthAddress() {
        EthSAddress condition = new EthSAddress();
        condition.setStatus(ESAddressStatus.VALID.getCode());
        return ethSAddressDAO.select(condition);
    }

    @Override
    public int refreshStatus(EthSAddress ethSAddress, String status) {
        int count = 0;
        if (ethSAddress != null) {
            ethSAddress.setStatus(status);
            ethSAddressDAO.updateStatus(ethSAddress);
        }
        return count;
    }
}
