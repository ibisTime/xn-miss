package com.ogc.standard.bo.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ogc.standard.bo.IBtcUtxoBO;
import com.ogc.standard.bo.base.Paginable;
import com.ogc.standard.bo.base.PaginableBOImpl;
import com.ogc.standard.dao.IBtcUtxoDAO;
import com.ogc.standard.domain.BtcUtxo;
import com.ogc.standard.domain.CtqBtcUtxo;
import com.ogc.standard.enums.EAddressType;
import com.ogc.standard.enums.EBtcUtxoRefType;
import com.ogc.standard.enums.EBtcUtxoStatus;
import com.ogc.standard.enums.EErrorCode_main;
import com.ogc.standard.exception.BizException;

@Component
public class BtcUtxoBOImpl extends PaginableBOImpl<BtcUtxo> implements
        IBtcUtxoBO {

    @Autowired
    private IBtcUtxoDAO btcUtxoDAO;

    @Override
    public boolean isBtcUtxoExist(String txid, Integer vout) {
        BtcUtxo condition = new BtcUtxo();
        condition.setTxid(txid);
        condition.setVout(vout);
        if (btcUtxoDAO.selectTotalCount(condition) > 0) {
            return true;
        }
        return false;
    }

    @Override
    public int saveBtcUtxo(CtqBtcUtxo ctqBtcUtxo, EAddressType addressType) {
        int count = 0;
        if (ctqBtcUtxo != null) {

            BtcUtxo data = new BtcUtxo();

            data.setTxid(ctqBtcUtxo.getTxid());
            data.setVout(ctqBtcUtxo.getVout());
            data.setCount(ctqBtcUtxo.getCount());
            data.setScriptPubKey(ctqBtcUtxo.getScriptPubKey());
            data.setAddress(ctqBtcUtxo.getAddress());

            data.setSyncTime(ctqBtcUtxo.getSyncTime());
            data.setBlockHeight(ctqBtcUtxo.getBlockHeight());
            data.setStatus(EBtcUtxoStatus.ENABLE.getCode());
            data.setAddressType(addressType.getCode());

            count = btcUtxoDAO.insert(data);

        }
        return count;
    }

    @Override
    public int refreshBroadcast(BtcUtxo data, EBtcUtxoStatus status,
            EBtcUtxoRefType refType, String refNo) {
        int count = 0;
        if (data != null) {
            data.setStatus(status.getCode());
            data.setRefType(refType.getCode());
            data.setRefNo(refNo);
            count = btcUtxoDAO.updateBroadcast(data);
        }
        return count;
    }

    @Override
    public int refreshStatus(BtcUtxo data, EBtcUtxoStatus status) {
        int count = 0;
        if (data != null) {
            data.setStatus(status.getCode());
            count = btcUtxoDAO.updateStatus(data);
        }
        return count;
    }

    @Override
    public List<BtcUtxo> queryBtcUtxoList(BtcUtxo condition) {
        return btcUtxoDAO.selectList(condition);
    }

    @Override
    public List<BtcUtxo> queryBtcUtxoList(String refNo) {
        BtcUtxo condition = new BtcUtxo();
        condition.setRefNo(refNo);
        return btcUtxoDAO.selectList(condition);
    }

    @Override
    public BtcUtxo getBtcUtxo(String txid, Integer vout) {
        BtcUtxo data = null;
        if (StringUtils.isNotBlank(txid)) {
            BtcUtxo condition = new BtcUtxo();
            condition.setTxid(txid);
            condition.setVout(vout);
            data = btcUtxoDAO.select(condition);
            if (data == null) {
                throw new BizException(EErrorCode_main.id_NOTEXIST.getCode());
            }
        }
        return data;
    }

    @Override
    public List<BtcUtxo> selectUnPush() {
        List<String> statusList = new ArrayList<String>();
        BtcUtxo condition = new BtcUtxo();
        condition.setStatusList(statusList);
        return btcUtxoDAO.selectList(condition);
    }

    @Override
    public BigDecimal getTotalEnableUTXOCount(EAddressType addressType) {
        BtcUtxo condition = new BtcUtxo();
        condition.setAddressType(addressType.getCode());
        condition.setStatus(EBtcUtxoStatus.ENABLE.getCode());
        return btcUtxoDAO.selectTotalUTXOCount(condition);
    }

    @Override
    public BigDecimal getTotalEnableUTXOCount(String address) {
        BtcUtxo condition = new BtcUtxo();
        condition.setAddress(address);
        condition.setStatus(EBtcUtxoStatus.ENABLE.getCode());
        return btcUtxoDAO.selectTotalUTXOCount(condition);
    }

    @Override
    public List<BtcUtxo> queryEnableUtxoList(int start, int limit,
            EAddressType addressType) {
        BtcUtxo condition = new BtcUtxo();
        condition.setAddressType(addressType.getCode());
        condition.setStatus(EBtcUtxoStatus.ENABLE.getCode());
        condition.setOrder("count", "desc");
        Paginable<BtcUtxo> pageBtcUtxo = getPaginable(start, limit, condition);// 默认每次100条
        List<BtcUtxo> list = pageBtcUtxo.getList();
        return list;
    }

}
