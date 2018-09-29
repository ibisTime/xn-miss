package com.ogc.standard.ao;

import org.springframework.stereotype.Component;

import com.ogc.standard.bo.base.Paginable;
import com.ogc.standard.domain.ExchangePair;

@Component
public interface IExchangePairAO {

    static final String DEFAULT_ORDER_COLUMN = "id";

    public Paginable<ExchangePair> queryExchangePairPage(int start, int limit,
            ExchangePair condition, String referCurrency);

}
