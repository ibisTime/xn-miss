package com.ogc.standard.bo.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ogc.standard.bitcoin.original.BTCOriginalTx;
import com.ogc.standard.bo.IBtcTransactionBO;
import com.ogc.standard.bo.base.PaginableBOImpl;
import com.ogc.standard.common.DateUtil;
import com.ogc.standard.common.JsonUtil;
import com.ogc.standard.dao.IBtcTransactionDAO;
import com.ogc.standard.domain.BtcTransaction;
import com.ogc.standard.enums.EBoolean;
import com.ogc.standard.enums.EErrorCode_main;
import com.ogc.standard.exception.BizException;

@Component
public class BtcTransactionBOImpl extends PaginableBOImpl<BtcTransaction>
        implements IBtcTransactionBO {

    @Autowired
    private IBtcTransactionDAO btcTransactionDAO;

    @Override
    public boolean isBtcTransactionExist(String txid) {
        BtcTransaction condition = new BtcTransaction();
        condition.setTxid(txid);
        if (btcTransactionDAO.selectTotalCount(condition) > 0) {
            return true;
        }
        return false;
    }

    @Override
    public int saveBtcTransaction(BTCOriginalTx btcOriginalTx, String refNo) {
        int count = 0;
        if (btcOriginalTx != null) {

            BtcTransaction data = new BtcTransaction();

            data.setTxid(btcOriginalTx.getTxid());
            data.setVersion(btcOriginalTx.getVersion());
            data.setBlockheight(btcOriginalTx.getBlockheight());
            if (null == btcOriginalTx.getIsCoinBase()) {
                data.setCoinbase(EBoolean.NO.getCode());
            } else {
                data.setCoinbase(btcOriginalTx.getIsCoinBase().toString());
            }
            data.setBlockhash(btcOriginalTx.getBlockhash());

            data.setBlocktime(
                DateUtil.TimeStamp2Date(btcOriginalTx.getBlocktime().toString(),
                    DateUtil.DATA_TIME_PATTERN_1));
            data.setValuein(btcOriginalTx.getValueIn());
            data.setValueout(btcOriginalTx.getValueOut());
            data.setFees(btcOriginalTx.getFees());
            data.setVin(JsonUtil.Object2Json(btcOriginalTx.getVin()));

            data.setVout(JsonUtil.Object2Json(btcOriginalTx.getVout()));
            data.setRefNo(refNo);

            count = btcTransactionDAO.insert(data);
        }
        return count;
    }

    @Override
    public List<BtcTransaction> queryBtcTransactionList(
            BtcTransaction condition) {
        return btcTransactionDAO.selectList(condition);
    }

    @Override
    public BtcTransaction getBtcTransaction(String txid) {
        BtcTransaction data = null;
        if (StringUtils.isNotBlank(txid)) {
            BtcTransaction condition = new BtcTransaction();
            condition.setTxid(txid);
            data = btcTransactionDAO.select(condition);
            if (data == null) {
                throw new BizException(EErrorCode_main.btc_BROADCAST.getCode());
            }
        }
        return data;
    }
}
