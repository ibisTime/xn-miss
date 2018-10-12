package com.ogc.standard.ao.impl;

import java.math.BigDecimal;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ogc.standard.ao.IExchangeCurrencyAO;
import com.ogc.standard.ao.IWeChatAO;
import com.ogc.standard.bo.IAccountBO;
import com.ogc.standard.bo.IExchangeCurrencyBO;
import com.ogc.standard.bo.ISYSConfigBO;
import com.ogc.standard.bo.IUserBO;
import com.ogc.standard.bo.base.Paginable;
import com.ogc.standard.common.AmountUtil;
import com.ogc.standard.common.UserUtil;
import com.ogc.standard.domain.Account;
import com.ogc.standard.domain.ExchangeCurrency;
import com.ogc.standard.domain.User;
import com.ogc.standard.enums.EBoolean;
import com.ogc.standard.enums.EChannelType;
import com.ogc.standard.enums.ECurrency;
import com.ogc.standard.enums.EExchangeCurrencyStatus;
import com.ogc.standard.enums.EJourBizType;
import com.ogc.standard.exception.BizException;
import com.ogc.standard.util.CalculationUtil;

@Service
public class ExchangeCurrencyAOImpl implements IExchangeCurrencyAO {

    @Autowired
    private IUserBO userBO;

    @Autowired
    private IAccountBO accountBO;

    @Autowired
    private IExchangeCurrencyBO exchangeCurrencyBO;

    @Autowired
    private IWeChatAO weChatAO;

    @Autowired
    private ISYSConfigBO sysConfigBO;

    @Override
    public Paginable<ExchangeCurrency> queryExchangeCurrencyPage(int start,
            int limit, ExchangeCurrency condition) {
        Paginable<ExchangeCurrency> page = exchangeCurrencyBO.getPaginable(
            start, limit, condition);
        if (page != null && CollectionUtils.isNotEmpty(page.getList())) {
            for (ExchangeCurrency exchangeCurrency : page.getList()) {
                User fromUser = userBO
                    .getUser(exchangeCurrency.getFromUserId());
                exchangeCurrency.setFromUser(fromUser);
                User toUser = userBO.getUser(exchangeCurrency.getToUserId());
                exchangeCurrency.setToUser(toUser);
            }
        }
        return page;
    }

    @Override
    public ExchangeCurrency getExchangeCurrency(String code) {
        ExchangeCurrency exchangeCurrency = exchangeCurrencyBO
            .getExchangeCurrency(code);
        User fromUser = userBO.getUser(exchangeCurrency.getFromUserId());
        exchangeCurrency.setFromUser(fromUser);
        return exchangeCurrency;
    }

    @Override
    public Double getExchangeRate(String fromCurrency, String toCurrency) {
        return exchangeCurrencyBO.getExchangeRate(fromCurrency, toCurrency);
    }

    @Override
    public String applyExchange(String userId, BigDecimal fromAmount,
            String fromCurrency, String toCurrency) {
        User user = userBO.getUser(userId);
        Account account = accountBO.getAccountByUser(userId, fromCurrency);
        if (fromAmount.compareTo(account.getAmount()) > 0) {
            new BizException("xn000000", "余额不足");
        }
        // 判断是否生成条件是否满足
        // if (ESystemCode.MISS.getCode().equals(user.getSystemCode())) {
        // exchangeCurrencyBO.doCheckZH(userId, fromCurrency, toCurrency);
        // }
        return exchangeCurrencyBO.applyExchange(user, fromAmount, fromCurrency,
            toCurrency);
    }

    @Override
    @Transactional
    public void approveExchange(String code, String approveResult,
            String approver, String approveNote) {
        ExchangeCurrency dbOrder = exchangeCurrencyBO.getExchangeCurrency(code);
        if (EExchangeCurrencyStatus.TO_PAY.getCode()
            .equals(dbOrder.getStatus())) {
            if (EBoolean.YES.getCode().equals(approveResult)) {
                exchangeCurrencyBO.approveExchangeYes(dbOrder, approver,
                    approveNote);
                // 开始资金划转
                String bizNote = CalculationUtil.divi(dbOrder.getFromAmount())
                        + dbOrder.getFromCurrency() + "虚拟币转化为"
                        + CalculationUtil.divi(dbOrder.getToAmount())
                        + dbOrder.getToCurrency();
                Account fromAccount = accountBO.getAccountByUser(
                    dbOrder.getFromUserId(), dbOrder.getFromCurrency());
                Account toAccount = accountBO.getAccountByUser(
                    dbOrder.getToUserId(), dbOrder.getToCurrency());

                accountBO.changeAmount(fromAccount.getAccountNumber(),
                    EChannelType.NBZ, null, null, code,
                    EJourBizType.EXCHANGE_CURRENCY,
                    EJourBizType.EXCHANGE_CURRENCY.getCode(), dbOrder
                        .getFromAmount().negate());
                accountBO.changeAmount(toAccount.getAccountNumber(),
                    EChannelType.NBZ, null, null, code,
                    EJourBizType.EXCHANGE_CURRENCY, bizNote,
                    dbOrder.getToAmount());
            } else {
                exchangeCurrencyBO.approveExchangeNo(dbOrder, approver,
                    approveNote);
            }
        } else {
            throw new BizException("xn000000", code + "不处于待审批状态");
        }
    }

    @Override
    @Transactional
    public void doTransfer(String fromUserId, String fromCurrency,
            String toUserId, String toCurrency, BigDecimal transAmount) {
        // 转化前提是否满足
        if (ECurrency.CNY.getCode().equals(toCurrency)) {
            throw new BizException("xn000000", "转化币种不能是人民币");
        }
        Account fromAccount = accountBO.getAccountByUser(fromUserId,
            fromCurrency);
        Account toAccount = accountBO.getAccountByUser(toUserId, toCurrency);
        Double rate = this.getExchangeRate(fromCurrency, toCurrency);
        BigDecimal toAmount = AmountUtil.mul(transAmount, rate);

        // 开始资金划转
        String bizNote = CalculationUtil.divi(transAmount)
                + ECurrency.getCurrencyMap().get(fromCurrency).getValue()
                + "转化为" + CalculationUtil.divi(toAmount)
                + ECurrency.getCurrencyMap().get(toCurrency).getValue();
        String fromBizNote = bizNote;
        String toBizNote = bizNote;
        if (fromCurrency.equals(toCurrency)) {
            fromBizNote = "代发代销至账户[" + toAccount.getRealName() + "]";
            toBizNote = "账户[" + fromAccount.getRealName() + "]代发代销";
        }
        String code = exchangeCurrencyBO.saveExchange(fromUserId, transAmount,
            fromCurrency, toUserId, toAmount, toCurrency, null,
            fromAccount.getCompanyCode(), fromAccount.getSystemCode());
        accountBO.changeAmount(fromAccount.getAccountNumber(),
            EChannelType.NBZ, null, null, code, EJourBizType.Transfer_CURRENCY,
            fromBizNote, transAmount.negate());
        accountBO.changeAmount(toAccount.getAccountNumber(), EChannelType.NBZ,
            null, null, code, EJourBizType.Transfer_CURRENCY, toBizNote,
            toAmount);
    }

    @Override
    @Transactional
    public void paySuccess(String payGroup, String payCode,
            BigDecimal transAmount) {
        List<ExchangeCurrency> resultList = exchangeCurrencyBO
            .queryExchangeCurrencyList(payGroup);
        if (CollectionUtils.isEmpty(resultList)) {
            throw new BizException("XN000000", "找不到对应的兑换记录");
        }
        ExchangeCurrency exchangeCurrency = resultList.get(0);
        if (!transAmount.equals(exchangeCurrency.getFromAmount())) {
            throw new BizException("XN000000", "金额校验错误，非正常调用");
        }
        // 更新状态
        exchangeCurrencyBO.paySuccess(exchangeCurrency.getCode(),
            EExchangeCurrencyStatus.PAYED.getCode(), payCode, transAmount);

        EJourBizType bizType = null;
        // if
        // (ECurrency.CG_CGB.getCode().equals(exchangeCurrency.getToCurrency()))
        // {
        // bizType = EJourBizType.CG_CGBGM;
        // } else if (ECurrency.YC_CB.getCode().equals(
        // exchangeCurrency.getToCurrency())) {
        // bizType = EJourBizType.YC_CBGM;
        // } else {
        // throw new BizException("xn000000", "币种未识别或不支持购买");
        // }

        User fromUser = userBO.getUser(exchangeCurrency.getFromUserId());

        // 购买的币种划转
        accountBO.transAmountCZB(exchangeCurrency.getToUserId(),
            exchangeCurrency.getToCurrency(), exchangeCurrency.getFromUserId(),
            exchangeCurrency.getToCurrency(), exchangeCurrency.getToAmount(),
            bizType,
            UserUtil.getUserMobile(fromUser.getMobile()) + bizType.getValue(),
            bizType.getValue(), exchangeCurrency.getCode());
    }

    @Override
    public void doTransfer(String fromUserId, String fromCurrency,
            String toUserId, String toCurrency, BigDecimal amount,
            BigDecimal tranAmount, String remark) {
        // 转化前提是否满足
        if (ECurrency.CNY.getCode().equals(toCurrency)) {
            throw new BizException("xn000000", "转化币种不能是人民币");
        }
        Account fromAccount = accountBO.getAccountByUser(fromUserId,
            fromCurrency);
        Account toAccount = accountBO.getAccountByUser(toUserId, toCurrency);
        // 开始资金划转
        String bizNote = CalculationUtil.divi(amount)
                + ECurrency.getCurrencyMap().get(fromCurrency).getValue()
                + "转化为" + CalculationUtil.divi(tranAmount)
                + ECurrency.getCurrencyMap().get(toCurrency).getValue();
        String fromBizNote = bizNote;
        String toBizNote = bizNote;
        if (fromCurrency.equals(toCurrency)) {
            fromBizNote = "代发代销至账户[" + toAccount.getRealName() + "]";
            toBizNote = "账户[" + fromAccount.getRealName() + "]代发代销";
        }
        String code = exchangeCurrencyBO.saveExchange(fromUserId, amount,
            fromCurrency, toUserId, tranAmount, toCurrency, remark,
            fromAccount.getCompanyCode(), fromAccount.getSystemCode());
        accountBO.changeAmount(fromAccount.getAccountNumber(),
            EChannelType.NBZ, null, null, code, EJourBizType.Transfer_CURRENCY,
            fromBizNote, amount.negate());
        accountBO
            .changeAmount(toAccount.getAccountNumber(), EChannelType.NBZ, null,
                null, code, EJourBizType.Transfer_CURRENCY, toBizNote, amount);
    }

    @Override
    public void doTransferToC(String fromUserId, String toUserId,
            String currency, BigDecimal amount) {
        Account fromAccount = accountBO.getAccountByUser(fromUserId, currency);
        Account toAccount = accountBO.getAccountByUser(toUserId, currency);
        ECurrency eCurrency = ECurrency.getECurrency(currency);
        String bizNote = "领队将" + eCurrency.getValue() + "划转至本人C端账户，划转数量["
                + CalculationUtil.divi(amount) + "]";
        String code = exchangeCurrencyBO.saveExchange(fromUserId, amount,
            currency, toUserId, amount, currency, bizNote,
            fromAccount.getCompanyCode(), fromAccount.getSystemCode());
        accountBO.changeAmount(fromAccount.getAccountNumber(),
            EChannelType.NBZ, null, null, code, EJourBizType.Transfer_CURRENCY,
            bizNote, amount.negate());
        accountBO.changeAmount(toAccount.getAccountNumber(), EChannelType.NBZ,
            null, null, code, EJourBizType.Transfer_CURRENCY, bizNote, amount);
    }

    @Override
    public void updateGdStatus(String code) {
        ExchangeCurrency exchangeCurrency = exchangeCurrencyBO
            .getExchangeCurrency(code);
        if (!EExchangeCurrencyStatus.PAYED.getCode().equalsIgnoreCase(
            exchangeCurrency.getStatus())) {
            throw new BizException("xn000000", "该转化不能归档");
        }
        exchangeCurrencyBO.updateGdStatus(exchangeCurrency);
    }
}
