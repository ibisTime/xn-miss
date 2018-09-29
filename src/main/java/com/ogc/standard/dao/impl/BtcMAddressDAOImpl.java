package com.ogc.standard.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.ogc.standard.dao.IBtcMAddressDAO;
import com.ogc.standard.dao.base.support.AMybatisTemplate;
import com.ogc.standard.domain.BtcMAddress;

@Repository("btcMAddressDAOImpl")
public class BtcMAddressDAOImpl extends AMybatisTemplate implements
        IBtcMAddressDAO {

    @Override
    public int insert(BtcMAddress data) {
        return super.insert(NAMESPACE.concat("insert_btcMAddress"), data);
    }

    @Override
    public int delete(BtcMAddress data) {
        return 0;
    }

    @Override
    public BtcMAddress select(BtcMAddress condition) {
        return super.select(NAMESPACE.concat("select_btcMAddress"), condition,
            BtcMAddress.class);
    }

    @Override
    public long selectTotalCount(BtcMAddress condition) {
        return super.selectTotalCount(
            NAMESPACE.concat("select_btcMAddress_count"), condition);
    }

    @Override
    public List<BtcMAddress> selectList(BtcMAddress condition) {
        return super.selectList(NAMESPACE.concat("select_btcMAddress"),
            condition, BtcMAddress.class);
    }

    @Override
    public List<BtcMAddress> selectList(BtcMAddress condition, int start,
            int count) {
        return super.selectList(NAMESPACE.concat("select_btcMAddress"), start,
            count, condition, BtcMAddress.class);
    }

    @Override
    public int updateStatus(BtcMAddress data) {
        return super.update(NAMESPACE.concat("update_status"), data);
    }

}
