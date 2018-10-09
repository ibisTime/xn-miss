package com.ogc.standard.bo;

import java.math.BigDecimal;
import java.util.List;

import com.ogc.standard.bo.base.IPaginableBO;
import com.ogc.standard.domain.ExchangeCurrency;
import com.ogc.standard.domain.User;

/**
 * @author: xieyj 
 * @since: 2017年3月30日 下午1:01:17 
 * @history:
 */
public interface IExchangeCurrencyBO extends IPaginableBO<ExchangeCurrency> {

    public List<ExchangeCurrency> queryExchangeCurrencyList(
            ExchangeCurrency condition);

    public List<ExchangeCurrency> queryExchangeCurrencyList(String payGroup);

    public ExchangeCurrency getExchangeCurrency(String code);

    // 1fromCurrency=多少toCurrency
    public Double getExchangeRate(String fromCurrency, String toCurrency);

    public String applyExchange(User user, BigDecimal fromAmount,
            String fromCurrency, String toCurrency);

    public void approveExchangeYes(ExchangeCurrency dbOrder, String approver,
            String approveNote);

    public void approveExchangeNo(ExchangeCurrency dbOrder, String approver,
            String approveNote);

    /*
     * 虚拟币划转兑换记录
     */
    public String saveExchange(String fromUserId, Long fromAmount,
            String fromCurrency, String toUserId, Long toAmount,
            String toCurrency, String remark, String companyCode,
            String systemCode);

    public void doCheckZH(String userId, String fromCurrency, String toCurrency);

    public String payExchange(String fromUserId, String toUserId,
            Long rmbAmount, Long toAmount, String currency, String payType,
            String systemCode);

    public int paySuccess(String code, String status, String payCode,
            Long payAmount);

    public void updateGdStatus(ExchangeCurrency exchangeCurrency);

}
