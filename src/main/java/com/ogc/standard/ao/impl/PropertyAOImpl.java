package com.ogc.standard.ao.impl;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ogc.standard.ao.IMarketAO;
import com.ogc.standard.ao.IPropertyAO;
import com.ogc.standard.bo.IAccountBO;
import com.ogc.standard.bo.ICoinBO;
import com.ogc.standard.bo.IMarketBO;
import com.ogc.standard.bo.IUserBO;
import com.ogc.standard.common.CoinUtil;
import com.ogc.standard.domain.Account;
import com.ogc.standard.domain.Coin;
import com.ogc.standard.domain.Property;
import com.ogc.standard.enums.ECoin;

@Service
public class PropertyAOImpl implements IPropertyAO {

    @Autowired
    private IUserBO userBO;

    @Autowired
    private IAccountBO accountBO;

    @Autowired
    private IMarketAO marketAO;

    @Autowired
    private IMarketBO marketBO;

    @Autowired
    private ICoinBO coinBO;

    @Override
    public Property getProperty(String userId, String converCurrency) {

        // 转换币种
        BigDecimal symbol = BigDecimal.ZERO;

        userBO.getUser(userId);

        List<Account> accountList = accountBO.queryAccountList(userId);

        for (Account account : accountList) {

            Coin coin = coinBO.getCoin(account.getCurrency());

            if (ECoin.X.getCode().equals(account.getCurrency())) {

                symbol = symbol.add(
                    CoinUtil.fromMinUnit(account.getAmount(), coin.getUnit()));

            } else {

                BigDecimal priceRate = marketBO.getMarketPrice(
                    ECoin.X.getCode(), account.getCurrency(), "");

                symbol = symbol.add(
                    CoinUtil.fromMinUnit(account.getAmount(), coin.getUnit())
                        .divide(priceRate, 8, BigDecimal.ROUND_DOWN));

            }

        }

        // 获取X与法币的汇率
        BigDecimal rate = marketAO.getMarketRate(ECoin.X.getCode(), converCurrency);

        Property property = new Property();
        property.setSymbol(symbol);
        property.setCurrency(symbol.multiply(rate));

        return property;
    }

}
