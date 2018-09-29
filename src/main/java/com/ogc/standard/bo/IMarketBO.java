package com.ogc.standard.bo;

import java.math.BigDecimal;
import java.util.List;

import com.ogc.standard.domain.Market;
import com.ogc.standard.enums.ECoin;

/**
 * Created by tianlei on 2017/十一月/13.
 */
public interface IMarketBO {

    /*
     * 获取价格计算的标准, 取加权值 + 系统可配参数
     */
    public Market standardMarket(ECoin coin, String refCurrency);

    public Market marketByCoinTypeAndOrigin(String coinType, String origin);

    public Market getMarketBysymbolAndCurrency(String symbol,
            String referCurrency);

    int updateMarket(String origin, String cointType, Market market);

    List<Market> marketListByCondition(Market condition);

    public BigDecimal getMarketPrice(String symbol, String referCurrency,
            String origin);

    public BigDecimal getMarketRate(String symbol, String referCurrency);

}
