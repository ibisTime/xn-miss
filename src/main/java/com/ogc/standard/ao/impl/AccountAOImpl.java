package com.ogc.standard.ao.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ogc.standard.ao.IAccountAO;
import com.ogc.standard.bo.IAccountBO;
import com.ogc.standard.bo.IJourBO;
import com.ogc.standard.bo.IUserBO;
import com.ogc.standard.bo.base.Paginable;
import com.ogc.standard.domain.Account;
import com.ogc.standard.domain.User;
import com.ogc.standard.enums.EAccountType;
import com.ogc.standard.enums.EUser;
import com.ogc.standard.exception.BizException;

@Service
public class AccountAOImpl implements IAccountAO {

    @Autowired
    private IAccountBO accountBO;

    @Autowired
    private IJourBO jourBO;

    @Autowired
    private IUserBO userBO;

    @Override
    public Paginable<Account> queryAccountPage(int start, int limit,
            Account condition) {
        Paginable<Account> paginable = accountBO.getPaginable(start, limit,
            condition);
        List<Account> list = paginable.getList();
        for (Account data : list) {
            init(data);
        }
        return paginable;
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

    @Override
    public List<Account> getAccountAmountSumList(String currency, String status) {
        Account condition = new Account();
        condition.setCurrency(currency);
        condition.setStatus(status);
        return accountBO.queryAccountAmountSumList(condition);
    }

    public void init(Account data) {
        // 户名
        String realName = null;

        // 账户
        Account account = accountBO.getAccount(data.getAccountNumber());

        if (EAccountType.Customer.getCode().equals(data.getType())) {
            // C端用户
            User user = userBO.getUser(account.getUserId());
            if (StringUtils.isBlank(user.getRealName())) {
                if (StringUtils.isNotBlank(user.getNickname())
                        && StringUtils.isNotBlank(user.getMobile())) {
                    realName = user.getNickname().concat("-")
                        .concat(user.getMobile());
                } else if (StringUtils.isNotBlank(user.getMobile())) {
                    realName = user.getMobile();
                } else if (StringUtils.isNotBlank(user.getNickname())) {
                    realName = user.getNickname();
                } else {
                    throw new BizException("xn0000", "用户未注册");
                }
            } else {
                if (StringUtils.isNotBlank(user.getNickname())) {
                    realName = user.getNickname().concat(user.getRealName());
                }
            }

        } else if (EAccountType.Business.getCode().equals(data.getType())) {
            realName = "品牌方";
        } else {
            // 系统用户
            realName = EUser.ADMIN.getValue();
        }

        data.setRealName(realName);

    }
}
