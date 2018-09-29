package com.ogc.standard.bo;

import java.math.BigDecimal;
import java.util.List;

import com.ogc.standard.bo.base.IPaginableBO;
import com.ogc.standard.domain.ExchangePair;
import com.ogc.standard.domain.SimuKLine;

public interface ISimuKLineBO extends IPaginableBO<SimuKLine> {

    public void saveSimuKLine(ExchangePair pair, String period,
            BigDecimal volume, BigDecimal quantity, BigDecimal amount,
            BigDecimal open, BigDecimal close, BigDecimal high, BigDecimal low);

    public List<SimuKLine> querySimuKLineList(SimuKLine condition);

    public SimuKLine getLatestSimuKLine(String symbol, String toSymbol,
            String period);

    public BigDecimal getKLineExchangeRate(String symbol, String toSymbol);

}
