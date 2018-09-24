package com.ogc.standard.bo.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ogc.standard.bo.ICurrencyRateBO;
import com.ogc.standard.bo.base.PaginableBOImpl;
import com.ogc.standard.dao.ICurrencyRateDAO;
import com.ogc.standard.domain.CurrencyRate;
import com.ogc.standard.enums.ECurrency;
import com.ogc.standard.enums.EErrorCode_main;
import com.ogc.standard.exception.BizException;

/**
 * Created by tianlei on 2017/十一月/13.
 */
@Component
public class CurrencyRateBOImpl extends PaginableBOImpl<CurrencyRate>
        implements ICurrencyRateBO {

    @Autowired
    ICurrencyRateDAO currencyRateDAO;

    public CurrencyRate currencyRateByCurrency(String currency) {

        if (StringUtils.isBlank(currency)) {
            throw new BizException(EErrorCode_main.curr_NOTEXIST.getCode());
        }

        CurrencyRate condation = new CurrencyRate();
        condation.setCurrency(currency);
        List<CurrencyRate> currencyRateList = this.currencyRateDAO
            .selectList(condation, 0, 1);

        if (currencyRateList.isEmpty()) {

            throw new BizException(EErrorCode_main.curr_GETERROR.getCode());

        }

        return currencyRateList.get(0);

    }

    @Override
    public List<CurrencyRate> allCurrencyRate() {

        CurrencyRate condition = new CurrencyRate();
        condition.setCurrency(ECurrency.USD.getCode());

        //
        List<CurrencyRate> usdRateList = this.currencyRateDAO
            .selectList(condition, 0, 1);

        return this.currencyRateDAO.selectList(null);

    }

    // @Override
    // public BigDecimal getAVGRate(ECurrency currency) {
    //
    // return this.currencyRateDAO.
    //
    // }

    // @Override
    // public int changeRate(String currency, BigDecimal rate, String origin) {
    //
    // if (StringUtils.isBlank(currency) || rate == null ||
    // StringUtils.isBlank(origin)) {
    // throw new BizException("xn0000","参数不符合要求");
    // }
    // CurrencyRate condation = new CurrencyRate();
    // condation.setCurrency(currency);
    // condation.setRate(rate);
    // condation.setUpdateDatetime(new Date());
    // condation.setReferCurrency(ECurrency.CNY.getCode());
    // condation.setOrigin(origin);
    // return this.currencyRateDAO.update(condation);
    //
    // }

    @Override
    public int insert(CurrencyRate currencyRate) {

        return this.currencyRateDAO.insert(currencyRate);

    }

}
