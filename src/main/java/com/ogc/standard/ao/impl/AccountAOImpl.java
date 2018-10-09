package com.ogc.standard.ao.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ogc.standard.ao.IAccountAO;
import com.ogc.standard.bo.IAccountBO;
import com.ogc.standard.bo.IJourBO;
import com.ogc.standard.bo.base.Paginable;
import com.ogc.standard.domain.Account;
import com.ogc.standard.enums.EAccountType;
import com.ogc.standard.enums.EJourBizType;
import com.ogc.standard.exception.BizException;

@Service
public class AccountAOImpl implements IAccountAO {

    @Autowired
    private IAccountBO accountBO;

    @Autowired
    private IJourBO jourBO;

    @Override
    @Transactional
    public void distributeAccount(String userId, String realName,
            String accountType, List<String> currencyList, String systemCode,
            String companyCode) {
        if (CollectionUtils.isNotEmpty(currencyList)) {
            Map<String, EAccountType> map = EAccountType
                .getAccountTypeResultMap();
            EAccountType eAccountType = map.get(accountType);
            if (null == eAccountType) {
                new BizException("XN0000", "账户类型不存在");
            }
            for (String currency : currencyList) {
                accountBO.distributeAccount(userId, realName, eAccountType,
                    currency, systemCode, companyCode);
            }
        }
    }

    @Override
    public void editAccountName(String userId, String realName) {
        // 验证用户名和系统编号
        Account condition = new Account();
        condition.setUserId(userId);
        List<Account> accountList = accountBO.queryAccountList(condition);
        if (CollectionUtils.isEmpty(accountList)) {
            new BizException("XN0000", "该用户无对应账号");
        }
        accountBO.refreshAccountName(userId, realName);
    }

    @Override
    @Transactional
    public void transAmountCZB(String fromUserId, String fromCurrency,
            String toUserId, String toCurrency, Long transAmount,
            String bizType, String fromBizNote, String toBizNote, String refNo) {
        EJourBizType a = EJourBizType.getBizType(bizType);
        accountBO.transAmountCZB(fromUserId, fromCurrency, toUserId,
            toCurrency, transAmount, a, fromBizNote, toBizNote, refNo);
    }

    @Override
    public Paginable<Account> queryAccountPage(int start, int limit,
            Account condition) {
        return accountBO.getPaginable(start, limit, condition);
    }

    @Override
    public Account getAccount(String accountNumber) {
        return accountBO.getAccount(accountNumber);
    }

    @Override
    public List<Account> getAccountByUserId(String userId, String currency) {
        Account condition = new Account();
        condition.setUserId(userId);
        if (StringUtils.isNotBlank(currency)) {
            String[] currencyArrs = currency.split(",");
            List<String> currencyList = new ArrayList<String>();
            for (int i = 0; i < currencyArrs.length; i++) {
                currencyList.add(currencyArrs[i]);
            }
            condition.setCurrencyList(currencyList);
        }
        return accountBO.queryAccountList(condition);
    }

    @Override
    public List<Account> queryAccountList(Account condition) {
        return accountBO.queryAccountList(condition);
    }
}
