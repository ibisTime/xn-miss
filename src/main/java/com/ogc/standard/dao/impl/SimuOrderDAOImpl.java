package com.ogc.standard.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.ogc.standard.dao.ISimuOrderDAO;
import com.ogc.standard.dao.base.support.AMybatisTemplate;
import com.ogc.standard.domain.SimuOrder;

@Repository("simuOrderDAOImpl")
public class SimuOrderDAOImpl extends AMybatisTemplate
        implements ISimuOrderDAO {

    @Override
    public int insert(SimuOrder data) {
        return super.insert(NAMESPACE.concat("insert_simuOrder"), data);
    }

    @Override
    public int delete(SimuOrder data) {
        return 0;
    }

    @Override
    public SimuOrder select(SimuOrder condition) {
        return super.select(NAMESPACE.concat("select_simuOrder"), condition,
            SimuOrder.class);
    }

    @Override
    public long selectTotalCount(SimuOrder condition) {
        return super.selectTotalCount(
            NAMESPACE.concat("select_simuOrder_count"), condition);
    }

    @Override
    public List<SimuOrder> selectList(SimuOrder condition) {
        return super.selectList(NAMESPACE.concat("select_simuOrder"), condition,
            SimuOrder.class);
    }

    @Override
    public List<SimuOrder> selectList(SimuOrder condition, int start,
            int count) {
        return super.selectList(NAMESPACE.concat("select_simuOrder"), start,
            count, condition, SimuOrder.class);
    }

    @Override
    public int updateMarketSimuOrder(SimuOrder data) {
        return super.update(NAMESPACE.concat("update_market_simuOrder"), data);
    }

    @Override
    public int updateLimitSimuOrder(SimuOrder data) {
        return super.update(NAMESPACE.concat("update_limit_simuOrder"), data);
    }

    @Override
    public int updateScan(SimuOrder data) {
        return super.update(NAMESPACE.concat("update_scan"), data);
    }

    @Override
    public int cancel(SimuOrder data) {
        return super.update(NAMESPACE.concat("cancel_simuOrder"), data);
    }

    @Override
    public int tradeSuccess(SimuOrder data) {
        return super.update(NAMESPACE.concat("trade_success"), data);
    }

}
