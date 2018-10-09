package com.ogc.standard.dao;

import com.ogc.standard.dao.base.IBaseDAO;
import com.ogc.standard.domain.ExchangeCurrency;

public interface IExchangeCurrencyDAO extends IBaseDAO<ExchangeCurrency> {
    String NAMESPACE = IExchangeCurrencyDAO.class.getName().concat(".");

    int applyExchange(ExchangeCurrency data);

    int approveExchange(ExchangeCurrency data);

    int doExchange(ExchangeCurrency data);

    int paySuccess(ExchangeCurrency data);

    int updateGdStatus(ExchangeCurrency data);

}
