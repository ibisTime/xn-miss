package com.ogc.standard.dao.impl;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.ogc.standard.dao.IBtcUtxoDAO;
import com.ogc.standard.dao.base.support.AMybatisTemplate;
import com.ogc.standard.domain.BtcUtxo;

@Repository("btcUtxoDAOImpl")
public class BtcUtxoDAOImpl extends AMybatisTemplate implements IBtcUtxoDAO {

    @Override
    public int insert(BtcUtxo data) {
        return super.insert(NAMESPACE.concat("insert_btcUtxo"), data);
    }

    @Override
    public int delete(BtcUtxo data) {
        return 0;
    }

    @Override
    public BtcUtxo select(BtcUtxo condition) {
        return super.select(NAMESPACE.concat("select_btcUtxo"), condition,
            BtcUtxo.class);
    }

    @Override
    public long selectTotalCount(BtcUtxo condition) {
        return super.selectTotalCount(NAMESPACE.concat("select_btcUtxo_count"),
            condition);
    }

    @Override
    public List<BtcUtxo> selectList(BtcUtxo condition) {
        return super.selectList(NAMESPACE.concat("select_btcUtxo"), condition,
            BtcUtxo.class);
    }

    @Override
    public List<BtcUtxo> selectList(BtcUtxo condition, int start, int count) {
        return super.selectList(NAMESPACE.concat("select_btcUtxo"), start,
            count, condition, BtcUtxo.class);
    }

    @Override
    public int updateStatus(BtcUtxo data) {
        return super.update(NAMESPACE.concat("update_status"), data);
    }

    @Override
    public BigDecimal selectTotalUTXOCount(BtcUtxo data) {
        return super.select(NAMESPACE.concat("select_totalUTXOCount"), data,
            BigDecimal.class);
    }

    @Override
    public int updateBroadcast(BtcUtxo data) {
        return super.update(NAMESPACE.concat("update_broadcast"), data);
    }

}
