package com.ogc.standard.ao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ogc.standard.ao.IEthXAddressAO;
import com.ogc.standard.bo.IAccountBO;
import com.ogc.standard.bo.IEthXAddressBO;
import com.ogc.standard.bo.IUserBO;
import com.ogc.standard.bo.base.Paginable;
import com.ogc.standard.domain.Account;
import com.ogc.standard.domain.EthXAddress;
import com.ogc.standard.domain.User;
import com.ogc.standard.enums.ECoinType;

/** 
 * @author: haiqingzheng 
 * @since: 2017年10月27日 下午5:43:34 
 * @history:
 */
@Service
public class EthXAddressAOImpl implements IEthXAddressAO {

    @Autowired
    private IUserBO userBO;

    @Autowired
    private IAccountBO accountBO;

    @Autowired
    private IEthXAddressBO ethXAddressBO;

    @Override
    public Paginable<EthXAddress> queryEthXAddressPage(int start, int limit,
            EthXAddress condition) {
        Paginable<EthXAddress> page = ethXAddressBO.getPaginable(start, limit,
            condition);

        if (null != page) {
            List<EthXAddress> list = page.getList();
            for (EthXAddress address : list) {
                User user = userBO.getUser(address.getUserId());
                address.setUserInfo(user);

                Account account = accountBO.getAccountByUser(
                    address.getUserId(), ECoinType.ETH.getCode());
                address.setBalance(account.getAmount());
                address.setSymbol(account.getCurrency());
            }
        }

        return page;
    }

}
