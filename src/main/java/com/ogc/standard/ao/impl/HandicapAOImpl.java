package com.ogc.standard.ao.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ogc.standard.ao.IHandicapAO;
import com.ogc.standard.bo.IHandicapBO;
import com.ogc.standard.domain.Handicap;
import com.ogc.standard.domain.HandicapGrade;
import com.ogc.standard.domain.HandicapItem;
import com.ogc.standard.dto.res.XN650065Res;
import com.ogc.standard.enums.ESimuOrderDirection;
import com.ogc.standard.market.MarketDepth;
import com.ogc.standard.market.MarketDepthItem;

@Service
public class HandicapAOImpl implements IHandicapAO {

    @Autowired
    private IHandicapBO handicapBO;

    @Override
    public XN650065Res getHandicap(String symbol, String toSymbol) {
        XN650065Res data = new XN650065Res();

        data.setBids(formatHandicap(symbol, toSymbol,
            ESimuOrderDirection.BUY.getCode()));

        data.setAsks(formatHandicap(symbol, toSymbol,
            ESimuOrderDirection.SELL.getCode()));

        return data;
    }

    private List<HandicapItem> formatHandicap(String symbol, String toSymbol,
            String direction) {

        List<HandicapItem> handicapItems = new ArrayList<>();

        List<HandicapGrade> asksGrades = handicapBO.queryHandicapGradeList(symbol,
            toSymbol, direction);

        for (HandicapGrade handicapGrade : asksGrades) {
            HandicapItem item = new HandicapItem();
            item.setPrice(handicapGrade.getPrice());

            BigDecimal count = BigDecimal.ZERO;
            for (Handicap handicaps : handicapGrade.getHandicapList()) {
                count = count.add(handicaps.getCount());
            }
            item.setCount(count);

            handicapItems.add(item);
        }

        return handicapItems;

    }

    @Override
    public MarketDepth getMarketDepth(String symbol, String toSymbol) {

        MarketDepth marketDepth = new MarketDepth();

        marketDepth.setAsks(formatMarketDepth(symbol, toSymbol,
            ESimuOrderDirection.SELL.getCode()));

        marketDepth.setBids(formatMarketDepth(symbol, toSymbol,
            ESimuOrderDirection.BUY.getCode()));

        return marketDepth;
    }

    private List<MarketDepthItem> formatMarketDepth(String symbol,
            String toSymbol, String direction) {

        List<MarketDepthItem> marketDepthItems = new ArrayList<>();

        // 获取档位
        List<HandicapItem> handicapItems = formatHandicap(symbol, toSymbol,
            direction);

        for (int i = 0; i < handicapItems.size(); i++) {

            MarketDepthItem item = new MarketDepthItem();
            item.setPrice(handicapItems.get(i).getPrice());

            BigDecimal count = BigDecimal.ZERO;
            for (int j = 0; j <= i; j++) {
                count = count.add(handicapItems.get(j).getCount());
            }
            item.setCount(count);

            marketDepthItems.add(item);
        }

        // 将深度倒序，方便前端展示
        if (ESimuOrderDirection.BUY.getCode().equals(direction)) {
            Collections.reverse(marketDepthItems);
        }

        return marketDepthItems;
    }
}
