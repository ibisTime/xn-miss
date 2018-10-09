package com.ogc.standard.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.ogc.standard.dao.IExchangeCurrencyDAO;
import com.ogc.standard.dao.base.support.AMybatisTemplate;
import com.ogc.standard.domain.ExchangeCurrency;

@Repository("exchangeCurrencyDAOImpl")
public class ExchangeCurrencyDAOImpl extends AMybatisTemplate implements
        IExchangeCurrencyDAO {

    @Override
    public int insert(ExchangeCurrency data) {
        return 0;
    }

    @Override
    public int delete(ExchangeCurrency data) {
        return 0;
    }

    @Override
    public ExchangeCurrency select(ExchangeCurrency condition) {
        return super.select(NAMESPACE.concat("select_exchangeCurrency"),
            condition, ExchangeCurrency.class);
    }

    @Override
    public long selectTotalCount(ExchangeCurrency condition) {
        return super.selectTotalCount(
            NAMESPACE.concat("select_exchangeCurrency_count"), condition);
    }

    @Override
    public List<ExchangeCurrency> selectList(ExchangeCurrency condition) {
        return super.selectList(NAMESPACE.concat("select_exchangeCurrency"),
            condition, ExchangeCurrency.class);
    }

    @Override
    public List<ExchangeCurrency> selectList(ExchangeCurrency condition,
            int start, int count) {
        return super.selectList(NAMESPACE.concat("select_exchangeCurrency"),
            start, count, condition, ExchangeCurrency.class);
    }

    @Override
    public int applyExchange(ExchangeCurrency data) {
        return super.insert(NAMESPACE.concat("insert_exchangeCurrency"), data);
    }

    @Override
    public int approveExchange(ExchangeCurrency data) {
        return super.update(NAMESPACE.concat("update_approveExchange"), data);
    }

    @Override
    public int doExchange(ExchangeCurrency data) {
        return super.insert(NAMESPACE.concat("insert_exchangeCurrency"), data);
    }

    @Override
    public int paySuccess(ExchangeCurrency data) {
        return super.update(NAMESPACE.concat("update_paySuccess"), data);
    }

    @Override
    public int updateGdStatus(ExchangeCurrency data) {
        return super.update(NAMESPACE.concat("update_gdStatus"), data);
    }

}
