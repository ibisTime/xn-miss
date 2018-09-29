package com.ogc.standard.bo.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ogc.standard.bo.ISimuKLineBO;
import com.ogc.standard.bo.base.PaginableBOImpl;
import com.ogc.standard.dao.ISimuKLineDAO;
import com.ogc.standard.domain.ExchangePair;
import com.ogc.standard.domain.SimuKLine;
import com.ogc.standard.enums.ESimuKLinePeriod;

@Component
public class SimuKLineBOImpl extends PaginableBOImpl<SimuKLine>
        implements ISimuKLineBO {

    @Autowired
    private ISimuKLineDAO simuKLineDAO;

    /**
     * 落地K线
     * @param orderDetail 
     * @create: 2018年8月31日 下午5:07:46 lei
     * @history:
     */
    @Override
    public void saveSimuKLine(ExchangePair pair, String period,
            BigDecimal volume, BigDecimal quantity, BigDecimal amount,
            BigDecimal open, BigDecimal close, BigDecimal high,
            BigDecimal low) {

        SimuKLine simuKLine = new SimuKLine();
        simuKLine.setSymbol(pair.getSymbol());
        simuKLine.setToSymbol(pair.getToSymbol());
        simuKLine.setPeriod(period);
        simuKLine.setVolume(volume);
        simuKLine.setQuantity(quantity);

        simuKLine.setAmount(amount);
        simuKLine.setOpen(open);
        simuKLine.setClose(close);
        simuKLine.setHigh(high);
        simuKLine.setLow(low);

        simuKLine.setCreateDatetime(new Date());
        simuKLineDAO.insert(simuKLine);
    }

    @Override
    public List<SimuKLine> querySimuKLineList(SimuKLine condition) {
        return simuKLineDAO.selectList(condition);
    }

    @Override
    public SimuKLine getLatestSimuKLine(String symbol, String toSymbol,
            String period) {
        SimuKLine data = null;

        SimuKLine condition = new SimuKLine();
        condition.setSymbol(symbol);
        condition.setToSymbol(toSymbol);
        condition.setPeriod(period);
        condition.setOrder("create_datetime desc");

        // 获取最新的一条
        List<SimuKLine> simuKLines = querySimuKLineList(condition);
        if (CollectionUtils.isNotEmpty(simuKLines)) {
            data = simuKLines.get(0);
        }
        return data;
    }

    @Override
    public BigDecimal getKLineExchangeRate(String symbol, String toSymbol) {
        BigDecimal exchangeRate = BigDecimal.ZERO;

        SimuKLine condition = new SimuKLine();
        condition.setSymbol(symbol);
        condition.setToSymbol(toSymbol);
        condition.setPeriod(ESimuKLinePeriod.DAY.getCode());
        condition.setOrder("create_datetime desc");

        SimuKLine now = null;
        SimuKLine last = null;

        // 获取最新的两条
        List<SimuKLine> simuKLines = querySimuKLineList(condition);
        if (CollectionUtils.isNotEmpty(simuKLines)) {

            if (simuKLines.size() >= 2) {
                now = simuKLines.get(0);
                BigDecimal nowPrice = now.getHigh().add(now.getLow())
                    .divide(new BigDecimal("2"), 8, BigDecimal.ROUND_HALF_DOWN);

                last = simuKLines.get(1);
                BigDecimal lastPrice = last.getHigh().add(last.getLow())
                    .divide(new BigDecimal("2"), 8, BigDecimal.ROUND_HALF_DOWN);

                exchangeRate = nowPrice.subtract(lastPrice).divide(lastPrice, 4,
                    BigDecimal.ROUND_HALF_DOWN);
            } else {
                exchangeRate = BigDecimal.ZERO;
            }
        } else {
            exchangeRate = BigDecimal.ZERO;
        }

        return exchangeRate;
    }

}
