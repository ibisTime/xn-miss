package com.ogc.standard.ao.impl;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ogc.standard.ao.IExchangePairAO;
import com.ogc.standard.bo.IExchangePairBO;
import com.ogc.standard.bo.IMarketBO;
import com.ogc.standard.bo.ISimuKLineBO;
import com.ogc.standard.bo.base.Paginable;
import com.ogc.standard.domain.ExchangePair;
import com.ogc.standard.enums.ECurrency;
import com.ogc.standard.enums.ESimuKLinePeriod;

@Service
public class ExchangePairAOImpl implements IExchangePairAO {

    @Autowired
    private IExchangePairBO exchangePairBO;

    @Autowired
    private IMarketBO marketBO;

    @Autowired
    private ISimuKLineBO simuKLineBO;

    @Override
    public Paginable<ExchangePair> queryExchangePairPage(int start, int limit,
            ExchangePair condition, String referCurrency) {

        if (StringUtils.isBlank(referCurrency)) {
            referCurrency = ECurrency.CNY.getCode();
        }

        Paginable<ExchangePair> page = exchangePairBO
            .queryExchangePairShowPage(start, limit, condition);

        for (ExchangePair exchangePair : page.getList()) {

            exchangePair.setPrice(
                marketBO.getMarketPrice(exchangePair.getSymbol().toUpperCase(),
                    exchangePair.getToSymbol().toUpperCase(), ""));

            exchangePair.setCurrencyPrice(marketBO.getMarketRate(
                exchangePair.getSymbol().toUpperCase(), referCurrency));

            exchangePair.setExchangeRate(simuKLineBO.getKLineExchangeRate(
                exchangePair.getSymbol(), exchangePair.getToSymbol()));

            exchangePair.setDayLineInfo(simuKLineBO.getLatestSimuKLine(
                exchangePair.getSymbol(), exchangePair.getToSymbol(),
                ESimuKLinePeriod.DAY.getCode()));
        }

        return page;
    }

}
