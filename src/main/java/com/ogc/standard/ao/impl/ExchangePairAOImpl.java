package com.ogc.standard.ao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ogc.standard.ao.IExchangePairAO;
import com.ogc.standard.bo.IExchangePairBO;
import com.ogc.standard.bo.base.Paginable;
import com.ogc.standard.domain.ExchangePair;

@Service
public class ExchangePairAOImpl implements IExchangePairAO {

    @Autowired
    private IExchangePairBO exchangePairBO;

    @Override
    public Paginable<ExchangePair> queryExchangePairPage(int start, int limit,
            ExchangePair condition) {
        return exchangePairBO.queryExchangePairShowPage(start, limit,
            condition);
    }

}
