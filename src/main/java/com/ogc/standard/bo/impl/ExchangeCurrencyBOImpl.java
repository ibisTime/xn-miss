package com.ogc.standard.bo.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ogc.standard.bo.IExchangeCurrencyBO;
import com.ogc.standard.bo.ISYSConfigBO;
import com.ogc.standard.bo.base.PaginableBOImpl;
import com.ogc.standard.common.DateUtil;
import com.ogc.standard.core.OrderNoGenerater;
import com.ogc.standard.dao.IExchangeCurrencyDAO;
import com.ogc.standard.domain.ExchangeCurrency;
import com.ogc.standard.domain.User;
import com.ogc.standard.enums.ECurrency;
import com.ogc.standard.enums.EExchangeCurrencyStatus;
import com.ogc.standard.enums.EExchangeTimes;
import com.ogc.standard.enums.EGeneratePrefix;
import com.ogc.standard.enums.EPayType;
import com.ogc.standard.enums.ESystemCode;
import com.ogc.standard.exception.BizException;
import com.ogc.standard.util.AmountUtil;

@Component
public class ExchangeCurrencyBOImpl extends PaginableBOImpl<ExchangeCurrency>
        implements IExchangeCurrencyBO {

    @Autowired
    ISYSConfigBO sysConfigBO;

    @Autowired
    private IExchangeCurrencyDAO exchangeCurrencyDAO;

    @Override
    public List<ExchangeCurrency> queryExchangeCurrencyList(
            ExchangeCurrency condition) {
        return exchangeCurrencyDAO.selectList(condition);
    }

    public List<ExchangeCurrency> queryExchangeCurrencyList(String payGroup) {
        ExchangeCurrency condition = new ExchangeCurrency();
        condition.setPayGroup(payGroup);
        return exchangeCurrencyDAO.selectList(condition);
    }

    @Override
    public ExchangeCurrency getExchangeCurrency(String code) {
        ExchangeCurrency data = null;
        if (StringUtils.isNotBlank(code)) {
            ExchangeCurrency condition = new ExchangeCurrency();
            condition.setCode(code);
            data = exchangeCurrencyDAO.select(condition);
            if (data == null) {
                throw new BizException("xn0000", "记录不存在");
            }
        }
        return data;
    }

    @Override
    public Double getExchangeRate(String fromCurrency, String toCurrency) {
        if (fromCurrency != null && fromCurrency.equalsIgnoreCase(toCurrency)) {
            return 1.0;
        }

        if (ECurrency.CNY.getCode().equalsIgnoreCase(fromCurrency)
                && ECurrency.ZH_GXZ.getCode().equalsIgnoreCase(toCurrency)) {
            return sysConfigBO.getCNY2GXZ();
        } else if (ECurrency.CNY.getCode().equalsIgnoreCase(fromCurrency)
                && ECurrency.ZH_FRB.getCode().equalsIgnoreCase(toCurrency)) {
            return sysConfigBO.getCNY2FRB();
        } else if (ECurrency.CNY.getCode().equalsIgnoreCase(fromCurrency)
                && ECurrency.CG_CGB.getCode().equalsIgnoreCase(toCurrency)) {
            return sysConfigBO.getCNY2CGB();
        } else if (ECurrency.CNY.getCode().equalsIgnoreCase(fromCurrency)
                && ECurrency.CG_JF.getCode().equalsIgnoreCase(toCurrency)) {
            Double a = sysConfigBO.getCNY2CGB();
            Double b = sysConfigBO.getCGB2CGJF();
            return a * b;
        } else if (ECurrency.ZH_HBB.getCode().equalsIgnoreCase(fromCurrency)
                && ECurrency.ZH_GXZ.getCode().equalsIgnoreCase(toCurrency)) {
            return sysConfigBO.getHBB2GXZ();
        } else if (ECurrency.ZH_HBYJ.getCode().equalsIgnoreCase(fromCurrency)
                && ECurrency.ZH_GXZ.getCode().equalsIgnoreCase(toCurrency)) {
            return sysConfigBO.getHBYJ2GXZ();
        } else if (ECurrency.CG_CGB.getCode().equalsIgnoreCase(fromCurrency)
                && ECurrency.CG_JF.getCode().equalsIgnoreCase(toCurrency)) {
            return sysConfigBO.getCGB2CGJF();
        } else if (ECurrency.CNY.getCode().equalsIgnoreCase(fromCurrency)
                && ECurrency.YC_CB.getCode().equalsIgnoreCase(toCurrency)) {
            return sysConfigBO.getCNY2CB();
        } else if (ECurrency.CNY.getCode().equalsIgnoreCase(fromCurrency)
                && ECurrency.HW_XJK.getCode().equalsIgnoreCase(toCurrency)) {
            return sysConfigBO.getCNY2XJK();
        } else if (ECurrency.JF.getCode().equalsIgnoreCase(fromCurrency)
                && ECurrency.HW_XJK.getCode().equalsIgnoreCase(toCurrency)) {
            return AmountUtil.div(1L, sysConfigBO.getXJK2JF());
        } else {
            throw new BizException("xn000000", "兑换比例不存在，请检查钱包汇率规则参数");
        }
    }

    @Override
    public String saveExchange(String fromUserId, Long fromAmount,
            String fromCurrency, String toUserId, Long toAmount,
            String toCurrency, String remark, String companyCode,
            String systemCode) {
        String code = OrderNoGenerater
            .generate(EGeneratePrefix.EXCHANGE_CURRENCY.getCode());
        ExchangeCurrency data = new ExchangeCurrency();
        data.setCode(code);
        data.setToUserId(toUserId);
        data.setToAmount(toAmount);
        data.setToCurrency(toCurrency);
        data.setRemark(remark);
        data.setFromUserId(fromUserId);
        data.setFromAmount(fromAmount);
        data.setFromCurrency(fromCurrency);

        data.setCreateDatetime(new Date());
        data.setStatus(EExchangeCurrencyStatus.PAYED.getCode());
        data.setPayType(EPayType.DBHZ.getCode());
        data.setPayGroup(code);
        data.setSystemCode(systemCode);

        data.setCompanyCode(companyCode);
        exchangeCurrencyDAO.doExchange(data);
        return code;
    }

    @Override
    public String applyExchange(User user, BigDecimal fromAmount,
            String fromCurrency, String toCurrency) {
        String code = OrderNoGenerater
            .generate(EGeneratePrefix.EXCHANGE_CURRENCY.getCode());
        Double rate = this.getExchangeRate(fromCurrency, toCurrency);
        Long toAmount = Double.valueOf(fromAmount * rate).longValue();
        ExchangeCurrency data = new ExchangeCurrency();
        data.setCode(code);

        data.setToUserId(user.getUserId());
        data.setToAmount(toAmount);
        data.setToCurrency(toCurrency);
        data.setFromUserId(user.getUserId());
        data.setFromAmount(fromAmount);
        data.setFromCurrency(fromCurrency);

        data.setCreateDatetime(new Date());
        data.setStatus(EExchangeCurrencyStatus.TO_PAY.getCode());

        data.setSystemCode(user.getSystemCode());
        data.setCompanyCode(user.getCompanyCode());
        exchangeCurrencyDAO.applyExchange(data);
        return code;
    }

    @Override
    public void approveExchangeYes(ExchangeCurrency dbOrder, String approver,
            String approveNote) {
        dbOrder.setStatus(EExchangeCurrencyStatus.PAYED.getCode());
        dbOrder.setUpdater(approver);
        dbOrder.setUpdateDatetime(new Date());
        dbOrder.setRemark(approveNote);
        exchangeCurrencyDAO.approveExchange(dbOrder);
    }

    @Override
    public void approveExchangeNo(ExchangeCurrency dbOrder, String approver,
            String approveNote) {
        dbOrder.setStatus(EExchangeCurrencyStatus.CANCEL.getCode());
        dbOrder.setUpdater(approver);
        dbOrder.setUpdateDatetime(new Date());
        dbOrder.setRemark(approveNote);
        exchangeCurrencyDAO.approveExchange(dbOrder);
    }

    @Override
    public void doCheckZH(String userId, String fromCurrency, String toCurrency) {
        ExchangeCurrency condition = new ExchangeCurrency();
        condition.setFromUserId(userId);
        condition.setFromCurrency(fromCurrency);
        condition.setToUserId(userId);
        condition.setToCurrency(toCurrency);
        // 已经有申请
        condition.setStatus(EExchangeCurrencyStatus.TO_PAY.getCode());
        long totalCount = exchangeCurrencyDAO.selectTotalCount(condition);
        if (totalCount > 0) {
            throw new BizException("xn0000", "已经有未审核转化订单，不能再次提交申请");
        }
        // 每月的转化次数是有限制的
        String exchangeTimes = sysConfigBO.getSYSConfig(
            EExchangeTimes.EXCTIMES.getCode(), ESystemCode.MISS.getCode());
        if (StringUtils.isBlank(exchangeTimes)) {
            throw new BizException("xn0000", "每月兑换最大次数未配置");
        }
        condition.setStatus(null);
        condition.setCreateDatetimeStart(DateUtil.getCurrentMonthFirstDay());
        condition.setCreateDatetimeEnd(DateUtil.getCurrentMonthLastDay());
        totalCount = exchangeCurrencyDAO.selectTotalCount(condition);
        long maxMonthTimes = Double.valueOf(exchangeTimes).longValue();
        if (totalCount >= maxMonthTimes) {
            throw new BizException("xn0000", "每月最多兑换" + maxMonthTimes
                    + "次,本月申请次数已用尽");
        }
    }

    @Override
    public String payExchange(String fromUserId, String toUserId,
            Long rmbAmount, Long toAmount, String currency, String payType,
            String systemCode) {
        String code = OrderNoGenerater
            .generate(EGeneratePrefix.EXCHANGE_CURRENCY.getCode());
        ExchangeCurrency data = new ExchangeCurrency();
        data.setCode(code);

        data.setToUserId(toUserId);
        data.setToAmount(toAmount);
        data.setToCurrency(currency);
        data.setFromUserId(fromUserId);
        data.setFromAmount(rmbAmount);
        data.setFromCurrency(ECurrency.CNY.getCode());

        data.setCreateDatetime(new Date());
        data.setStatus(EExchangeCurrencyStatus.TO_PAY.getCode());
        data.setPayType(payType);
        data.setPayGroup(code);

        data.setSystemCode(systemCode);
        data.setCompanyCode(systemCode);
        exchangeCurrencyDAO.applyExchange(data);
        return code;
    }

    @Override
    public int paySuccess(String code, String status, String payCode,
            Long payAmount) {
        int count = 0;
        if (StringUtils.isNotBlank(code)) {
            ExchangeCurrency data = new ExchangeCurrency();
            data.setCode(code);
            data.setStatus(status);
            data.setPayCode(payCode);
            data.setPayAmount(payAmount);
            data.setPayDatetime(new Date());
            count = exchangeCurrencyDAO.paySuccess(data);
        }
        return count;
    }

    @Override
    public void updateGdStatus(ExchangeCurrency exchangeCurrency) {
        exchangeCurrency.setStatus(EExchangeCurrencyStatus.GUIDAN.getCode());
        exchangeCurrencyDAO.updateGdStatus(exchangeCurrency);
    }
}
