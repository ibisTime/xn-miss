package com.ogc.standard.bo.impl;

import java.math.BigDecimal;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ogc.standard.bo.IMarketBO;
import com.ogc.standard.dao.IMarketDAO;
import com.ogc.standard.domain.Market;
import com.ogc.standard.enums.ECoin;
import com.ogc.standard.enums.EErrorCode_main;
import com.ogc.standard.exception.BizException;

/**
 * 
 * @author: lei 
 * @since: 2018年9月14日 下午1:59:53 
 * @history:
 */
@Component
public class MarketBOImpl implements IMarketBO {

    @Autowired
    IMarketDAO marketDAO;

    @Override
    public Market standardMarket(ECoin coin, String refCurrency) {

        Market avgCondition = new Market();
        avgCondition.setSymbol(coin.getCode());
        avgCondition.setReferCurrency(refCurrency);
        BigDecimal avg = this.marketDAO.selectMarketAvg(avgCondition);
        avg = avg.setScale(4, BigDecimal.ROUND_HALF_EVEN);
        if (avg == null) {
            throw new BizException(EErrorCode_main.market_RATE.getCode());
        }

        Market market = new Market();
        market.setMid(avg);
        market.setSymbol(coin.getCode());
        // market.setAsk(avg);
        // market.setBid(avg);
        // market.setLastPrice(avg);
        // market.setLow(avg);
        // market.setHigh(avg);

        return market;
    }

    @Override
    public Market marketByCoinTypeAndOrigin(String coinType, String origin) {

        if (StringUtils.isBlank(coinType) || StringUtils.isBlank(origin)) {
            return null;
        }

        Market condition = new Market();
        condition.setOrigin(origin);
        condition.setSymbol(coinType);
        return this.marketDAO.select(condition);

    }

    @Override
    public int updateMarket(String origin, String coinType, Market market) {

        market.setOrigin(origin);
        market.setSymbol(coinType);
        return this.marketDAO.update(market);

    }

    @Override
    public List<Market> marketListByCondation(Market condation) {

        return this.marketDAO.selectList(condation);

    }

}
