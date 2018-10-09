package com.ogc.standard.ao.impl;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
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
import com.ogc.standard.common.PropertiesUtil;
import com.ogc.standard.common.SysConstant;
import com.ogc.standard.common.UserUtil;
import com.ogc.standard.domain.Account;
import com.ogc.standard.domain.ExchangeCurrency;
import com.ogc.standard.domain.User;
import com.ogc.standard.dto.res.BooleanRes;
import com.ogc.standard.enums.EBoolean;
import com.ogc.standard.enums.EChannelType;
import com.ogc.standard.enums.ECurrency;
import com.ogc.standard.enums.EExchangeCurrencyStatus;
import com.ogc.standard.enums.EJourBizType;
import com.ogc.standard.enums.EPayType;
import com.ogc.standard.enums.ESystemCode;
import com.ogc.standard.enums.EUserKind;
import com.ogc.standard.exception.BizException;
import com.ogc.standard.util.AmountUtil;
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
                User fromUser = userBO.getRemoteUser(exchangeCurrency
                    .getFromUserId());
                exchangeCurrency.setFromUser(fromUser);
                User toUser = userBO.getRemoteUser(exchangeCurrency
                    .getToUserId());
                exchangeCurrency.setToUser(toUser);
            }
        }
        return page;
    }

    @Override
    public ExchangeCurrency getExchangeCurrency(String code) {
        ExchangeCurrency exchangeCurrency = exchangeCurrencyBO
            .getExchangeCurrency(code);
        User fromUser = userBO.getRemoteUser(exchangeCurrency.getFromUserId());
        exchangeCurrency.setFromUser(fromUser);
        return exchangeCurrency;
    }

    @Override
    public Double getExchangeRate(String fromCurrency, String toCurrency) {
        return exchangeCurrencyBO.getExchangeRate(fromCurrency, toCurrency);
    }

    @Override
    public String applyExchange(String userId, Long fromAmount,
            String fromCurrency, String toCurrency) {
        User user = userBO.getRemoteUser(userId);
        Account account = accountBO.getAccountByUser(userId, fromCurrency);
        if (fromAmount > account.getAmount()) {
            new BizException("xn000000", "余额不足");
        }
        // 判断是否生成条件是否满足
        if (ESystemCode.ZHPAY.getCode().equals(user.getSystemCode())) {
            exchangeCurrencyBO.doCheckZH(userId, fromCurrency, toCurrency);
        }
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
                    EJourBizType.EXCHANGE_CURRENCY.getCode(),
                    -dbOrder.getFromAmount());
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
            String toUserId, String toCurrency, Long transAmount) {
        // 转化前提是否满足
        if (ECurrency.CNY.getCode().equals(toCurrency)) {
            throw new BizException("xn000000", "转化币种不能是人民币");
        }
        Account fromAccount = accountBO.getAccountByUser(fromUserId,
            fromCurrency);
        Account toAccount = accountBO.getAccountByUser(toUserId, toCurrency);
        Double rate = this.getExchangeRate(fromCurrency, toCurrency);
        Long toAmount = AmountUtil.mul(transAmount, rate);

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
            fromBizNote, -transAmount);
        accountBO.changeAmount(toAccount.getAccountNumber(), EChannelType.NBZ,
            null, null, code, EJourBizType.Transfer_CURRENCY, toBizNote,
            toAmount);
    }

    @Override
    @Transactional
    public Object payExchange(String fromUserId, String toUserId, Long amount,
            String currency, String payType) {
        Object result = null;
        User fromUser = userBO.getRemoteUser(fromUserId);
        // 获取微信公众号支付prepayid
        if (EPayType.RMB_YE.getCode().equals(payType)) {
            result = rmbYePay(fromUser, toUserId, amount, currency, payType);
        } else if (EPayType.WEIXIN_H5.getCode().equals(payType)) {
            result = weixinH5Pay(fromUser, toUserId, amount, currency, payType);
        } else if (EPayType.WEIXIN_NATIVE.getCode().equals(payType)) {
            result = weixinNativePay(fromUser, toUserId, amount, currency,
                payType);
        } else {
            throw new BizException("XN000000", "现只支持微信H5和微信二维码，其他方式不支持");
        }
        return result;
    }

    /**
    * 人民币余额购买虚拟币
    * @param user
    * @param amount
    * @param currency
    * @param payType
    * @return
    * @create: 2017年4月20日 下午6:02:46 xieyj
    * @history:
    */
    private Object rmbYePay(User fromUser, String toUser, Long amount,
            String currency, String payType) {
        EJourBizType bizType = null;
        if (ECurrency.CG_CGB.getCode().equals(currency)) {
            bizType = EJourBizType.CG_CGBGM;
        } else if (ECurrency.YC_CB.getCode().equals(currency)) {
            bizType = EJourBizType.YC_CBGM;
        } else {
            throw new BizException("xn000000", "币种未识别或不支持购买");
        }

        Long rmbAmount = AmountUtil.mulJinFen(amount, 1 / exchangeCurrencyBO
            .getExchangeRate(ECurrency.CNY.getCode(), currency));
        // 产生记录
        String code = exchangeCurrencyBO.saveExchange(fromUser.getUserId(),
            rmbAmount, ECurrency.CNY.getCode(), toUser, amount, currency, null,
            fromUser.getCompanyCode(), fromUser.getSystemCode());
        // 人民币划转
        accountBO.transAmountCZB(fromUser.getUserId(), ECurrency.CNY.getCode(),
            toUser, ECurrency.CNY.getCode(), rmbAmount, bizType,
            bizType.getValue(), UserUtil.getUserMobile(fromUser.getMobile())
                    + bizType.getValue(), code);
        // 购买的币种划转
        accountBO.transAmountCZB(toUser, currency, fromUser.getUserId(),
            currency, amount, bizType,
            UserUtil.getUserMobile(fromUser.getMobile()) + bizType.getValue(),
            bizType.getValue(), code);
        return new BooleanRes(true);
    }

    /**
    * 二维码扫描购买虚拟币
    * @param fromUserId
    * @param toUserId
    * @param amount
    * @param currency
    * @param payType
    * @param systemCode
    * @return
    * @create: 2017年4月20日 下午7:01:28 xieyj
    * @history:
    */
    private Object weixinNativePay(User fromUser, String toUserId, Long amount,
            String currency, String payType) {
        EJourBizType bizType = null;
        if (ECurrency.CG_CGB.getCode().equals(currency)) {
            bizType = EJourBizType.CG_CGBGM;
        } else if (ECurrency.YC_CB.getCode().equals(currency)) {
            bizType = EJourBizType.YC_CBGM;
        } else {
            throw new BizException("xn000000", "币种未识别或不支持购买");
        }

        Long rmbAmount = AmountUtil.mulJinFen(amount, 1 / exchangeCurrencyBO
            .getExchangeRate(ECurrency.CNY.getCode(), currency));
        String code = exchangeCurrencyBO.payExchange(fromUser.getUserId(),
            toUserId, rmbAmount, amount, currency, payType,
            fromUser.getSystemCode());

        return weChatAO.getPrepayIdNative(fromUser.getUserId(), toUserId, code,
            code, bizType.getCode(), bizType.getValue(), rmbAmount,
            PropertiesUtil.Config.SELF_PAY_BACKURL);
    }

    /**
    * 微信H5支付购买虚拟币
    * @param user
    * @param amount
    * @param currency
    * @param payType
    * @return
    * @create: 2017年4月20日 下午6:02:46 xieyj
    * @history:
    */
    private Object weixinH5Pay(User fromUser, String toUser, Long amount,
            String currency, String payType) {
        EJourBizType bizType = null;
        if (ECurrency.CG_CGB.getCode().equals(currency)) {
            bizType = EJourBizType.CG_CGBGM;
        } else if (ECurrency.YC_CB.getCode().equals(currency)) {
            bizType = EJourBizType.YC_CBGM;
        } else {
            throw new BizException("xn000000", "币种未识别或不支持购买");
        }

        Long rmbAmount = AmountUtil.mulJinFen(amount, 1 / exchangeCurrencyBO
            .getExchangeRate(ECurrency.CNY.getCode(), currency));
        String code = exchangeCurrencyBO.payExchange(fromUser.getUserId(),
            toUser, rmbAmount, amount, currency, payType,
            fromUser.getSystemCode());

        return weChatAO.getPrepayIdH5(fromUser.getUserId(),
            fromUser.getOpenId(), toUser, code, code, bizType.getCode(),
            bizType.getValue(), rmbAmount,
            PropertiesUtil.Config.SELF_PAY_BACKURL);
    }

    @Override
    @Transactional
    public void paySuccess(String payGroup, String payCode, Long transAmount) {
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
        if (ECurrency.CG_CGB.getCode().equals(exchangeCurrency.getToCurrency())) {
            bizType = EJourBizType.CG_CGBGM;
        } else if (ECurrency.YC_CB.getCode().equals(
            exchangeCurrency.getToCurrency())) {
            bizType = EJourBizType.YC_CBGM;
        } else {
            throw new BizException("xn000000", "币种未识别或不支持购买");
        }

        User fromUser = userBO.getRemoteUser(exchangeCurrency.getFromUserId());

        // 购买的币种划转
        accountBO.transAmountCZB(exchangeCurrency.getToUserId(),
            exchangeCurrency.getToCurrency(), exchangeCurrency.getFromUserId(),
            exchangeCurrency.getToCurrency(), exchangeCurrency.getToAmount(),
            bizType,
            UserUtil.getUserMobile(fromUser.getMobile()) + bizType.getValue(),
            bizType.getValue(), exchangeCurrency.getCode());
    }

    @Override
    @Transactional
    public void doTransferC2CByZhFR(String fromUserId, String toMobile,
            Long transAmount, String tradePwd) {
        if (transAmount <= 0) {
            throw new BizException("xn000000", "划转金额需大于零");
        }
        String transAmountBsValue = sysConfigBO.getSYSConfig(
            SysConstant.TRANSAMOUNTBS, ESystemCode.ZHPAY.getCode());
        if (StringUtils.isNotBlank(transAmountBsValue)) {
            // 转账金额倍数
            Long transAmountBs = AmountUtil.mul(1000L,
                Double.valueOf(transAmountBsValue));
            if (transAmountBs > 0 && transAmount % transAmountBs > 0) {
                throw new BizException("xn000000", "请取" + transAmountBsValue
                        + "的倍数");
            }
        }
        // 验证交易密码
        userBO.checkTradePwd(fromUserId, tradePwd);
        // 验证双方是否C端用户
        User fromUser = userBO.getRemoteUser(fromUserId);
        if (!EUserKind.Customer.getCode().equals(fromUser.getKind())) {
            throw new BizException("xn000000", "当前划转用户不是C端用户，不能进行转账业务");
        }
        String toUserId = userBO.isUserExist(toMobile, EUserKind.Customer,
            fromUser.getSystemCode());
        // 同一个用户不可以相互转账
        if (toUserId.equals(fromUser.getUserId())) {
            throw new BizException("xn000000", "不能给自己转账");
        }

        // 开始资金划转
        String currency = ECurrency.ZH_FRB.getCode();
        Account fromAccount = accountBO.getAccountByUser(fromUserId, currency);
        Account toAccount = accountBO.getAccountByUser(toUserId, currency);
        String bizNote = fromUser.getMobile() + "用户转账" + toMobile + "用户分润"
                + CalculationUtil.divi(transAmount);

        String code = exchangeCurrencyBO.saveExchange(fromUserId, transAmount,
            currency, toUserId, transAmount, currency, null,
            fromAccount.getCompanyCode(), fromAccount.getSystemCode());

        accountBO.changeAmount(fromAccount.getAccountNumber(),
            EChannelType.NBZ, null, null, code,
            EJourBizType.Transfer_CURRENCY_C2C, bizNote, -transAmount);
        accountBO.changeAmount(toAccount.getAccountNumber(), EChannelType.NBZ,
            null, null, code, EJourBizType.Transfer_CURRENCY_C2C, bizNote,
            transAmount);
    }

    @Override
    public void doTransfer(String fromUserId, String fromCurrency,
            String toUserId, String toCurrency, Long amount, Long tranAmount,
            String remark) {
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
            fromBizNote, -amount);
        accountBO
            .changeAmount(toAccount.getAccountNumber(), EChannelType.NBZ, null,
                null, code, EJourBizType.Transfer_CURRENCY, toBizNote, amount);
    }

    @Override
    public void doTransferToC(String fromUserId, String toUserId,
            String currency, Long amount) {
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
            bizNote, -amount);
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
