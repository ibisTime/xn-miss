package com.ogc.standard.bo.impl;

import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ogc.standard.bo.IBtcXAddressBO;
import com.ogc.standard.bo.base.PaginableBOImpl;
import com.ogc.standard.core.BtcAddressRes;
import com.ogc.standard.core.BtcClient;
import com.ogc.standard.dao.IBtcXAddressDAO;
import com.ogc.standard.domain.BtcXAddress;
import com.ogc.standard.enums.EErrorCode_main;
import com.ogc.standard.exception.BizException;

@Component
public class BtcXAddressBOImpl extends PaginableBOImpl<BtcXAddress>
        implements IBtcXAddressBO {

    private static Logger logger = Logger.getLogger(BtcXAddressBOImpl.class);

    @Autowired
    private IBtcXAddressDAO BtcXAddressDAO;

    @Override
    public String generateAddress(String userId) {
        String address = null;

        if (getBtcXAddressByUserId(userId) != null) {
            address = getBtcXAddressByUserId(userId).getAddress();
        } else {
            // 生成btc地址
            BtcAddressRes BtcXAddress = BtcClient.getSingleAddress();

            // 落地地址信息
            saveBtcAddress(BtcXAddress.getAddress(),
                BtcXAddress.getPrivatekey(), userId);
            address = BtcXAddress.getAddress();
        }

        return address;
    }

    @Override
    public int saveBtcAddress(String address, String privatekey,
            String userId) {
        BtcXAddress data = new BtcXAddress();
        Date now = new Date();
        data.setAddress(address);
        data.setPrivatekey(privatekey);

        data.setUserId(userId);
        data.setCreateDatetime(now);
        int count = BtcXAddressDAO.insert(data);
        return count;
    }

    @Override
    public List<BtcXAddress> queryBtcAddressList(BtcXAddress condition) {
        return BtcXAddressDAO.selectList(condition);
    }

    @Override
    public BtcXAddress getBtcXAddressByUserId(String userId) {
        BtcXAddress dbAddress = null;
        BtcXAddress condition = new BtcXAddress();
        condition.setUserId(userId);
        List<BtcXAddress> btcList = queryBtcAddressList(condition);
        if (CollectionUtils.isNotEmpty(btcList)) {
            dbAddress = btcList.get(0);
        }
        return dbAddress;
    }

    @Override
    public BtcXAddress getBtcAddress(long id) {
        BtcXAddress data = null;
        BtcXAddress condition = new BtcXAddress();
        condition.setId(id);
        data = BtcXAddressDAO.select(condition);
        if (data == null) {
            throw new BizException(
                EErrorCode_main.coin_ADDRESSNOTEXIST.getCode());
        }
        return data;
    }

    @Override
    public BtcXAddress getBtcAddress(String address) {
        BtcXAddress data = null;
        BtcXAddress condition = new BtcXAddress();
        condition.setAddress(address);
        List<BtcXAddress> results = BtcXAddressDAO.selectList(condition);
        if (CollectionUtils.isNotEmpty(results)) {
            data = results.get(0);
        }
        return data;
    }

    // @Override
    // public BtcXAddress getBtcXAddressByUserId(String userId) {
    // BtcXAddress data = null;
    // BtcXAddress condition = new BtcXAddress();
    // condition.setUserId(userId);
    // List<BtcXAddress> results = BtcXAddressDAO.selectList(condition);
    // if (CollectionUtils.isNotEmpty(results)) {
    // data = results.get(0);
    // }
    // return data;
    // }
    //
    // @Override
    // public BtcXAddress getBtcXAddressByAccountNumber(String accountNumber) {
    // BtcXAddress data = null;
    // BtcXAddress condition = new BtcXAddress();
    // condition.setAccountNumber(accountNumber);
    // List<BtcXAddress> results = BtcXAddressDAO.selectList(condition);
    // if (CollectionUtils.isNotEmpty(results)) {
    // data = results.get(0);
    // }
    // return data;
    // }
    //
    // @Override
    // public int abandonAddress(BtcXAddress address, String updater,
    // String remark) {
    // int count = 0;
    // if (address != null) {
    // Date now = new Date();
    // address.setStatus(EXAddressStatus.INVALID.getCode());
    // address.setUpdater(updater);
    // address.setUpdateDatetime(now);
    // address.setRemark(remark);
    // BtcXAddressDAO.updateAbandon(address);
    // }
    // return count;
    // }
    //
    // @Override
    // public BtcXAddress getWBtcXAddressToday() {
    // BtcXAddress condition = new BtcXAddress();
    // condition.setType(EAddressType.W.getCode());
    // condition.setStatus(EYAddressStatus.NORMAL.getCode());
    // condition.setOrder("create_datetime", "desc");
    // List<BtcXAddress> wList = BtcXAddressDAO.selectList(condition);
    // if (CollectionUtils.isEmpty(wList)) {
    // throw new BizException("xn625000", "未找到今日可用的BTC归集地址");
    // }
    // return wList.get(0);
    // }
    //
    // @Override
    // public int refreshStatus(BtcXAddress address, String status) {
    // int count = 0;
    // if (address != null) {
    // address.setStatus(status);
    // address.setUpdateDatetime(new Date());
    // BtcXAddressDAO.updateStatus(address);
    // }
    // return count;
    // }
    //
    // @Override
    // public boolean isBtcXAddressExist(String address) {
    // boolean flag = false;
    // if (StringUtils.isNotBlank(address)) {
    // BtcXAddress condition = new BtcXAddress();
    // condition.setAddress(address);
    // if (BtcXAddressDAO.selectTotalCount(condition) > 0) {
    // flag = true;
    // }
    // }
    // return flag;
    // }
}
