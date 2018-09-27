package com.ogc.standard.ao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.web3j.crypto.WalletUtils;

import com.ogc.standard.ao.IPersonalAddressAO;
import com.ogc.standard.bo.ICoinBO;
import com.ogc.standard.bo.IEthXAddressBO;
import com.ogc.standard.bo.IPersonalAddressBO;
import com.ogc.standard.bo.base.Paginable;
import com.ogc.standard.domain.Coin;
import com.ogc.standard.domain.PersonalAddress;
import com.ogc.standard.enums.EBoolean;
import com.ogc.standard.enums.ECoinType;
import com.ogc.standard.enums.EErrorCode_main;
import com.ogc.standard.enums.EPersonalAddressStatus;
import com.ogc.standard.exception.BizException;

@Service
public class PersonalAddressAOImpl implements IPersonalAddressAO {

    @Autowired
    private IPersonalAddressBO personalAddressBO;

    @Autowired
    private IEthXAddressBO ethXAddressBO;

    @Autowired
    private ICoinBO coinBO;

    @Override
    public String addPersonalAddress(String symbol, String address,
            String label, String userId, String isCerti) {

        // 地址有效性校验
        Coin coin = coinBO.getCoin(symbol);
        verifyAddress(coin, address);

        // 地址是否已经存在
        PersonalAddress condition = new PersonalAddress();
        condition.setSymbol(symbol);
        condition.setUserId(userId);
        condition.setAddress(address);
        if (personalAddressBO.getTotalCount(condition) > 0) {
            throw new BizException(
                EErrorCode_main.personal_REPEATEDLY.getCode());
        }

        // 是否设置为认证地址
        String status = EPersonalAddressStatus.NORMAL.getCode();
        if (EBoolean.YES.getCode().equals(isCerti)) {
            status = EPersonalAddressStatus.CERTI.getCode();
        }

        return personalAddressBO.savePersonalAddress(symbol, address, label,
            userId, status);
    }

    private void verifyAddress(Coin coin, String address) {
        if (ECoinType.ETH.getCode().equals(coin.getType())
                || ECoinType.X.getCode().equals(coin.getType())) {
            // 地址有效性校验
            if (!WalletUtils.isValidAddress(address)) {
                throw new BizException(EErrorCode_main.address_RULE.getCode(),
                    address, ECoinType.ETH.getValue());
            }
        } else {
            // 地址有效性校验
            if (!WalletUtils.isValidAddress(address)) {
                throw new BizException(EErrorCode_main.address_RULE.getCode(),
                    address, ECoinType.BTC.getValue());
            }
        }
    }

    @Override
    public int dropPersonalAddress(long id) {
        return personalAddressBO.removePersonalAddress(id);
    }

    @Override
    public Paginable<PersonalAddress> queryPersonalAddressPage(int start,
            int limit, PersonalAddress condition) {
        return personalAddressBO.getPaginable(start, limit, condition);
    }
    //
    // @Override
    // public List<PersonalAddress> queryPersonalAddressList(
    // PersonalAddress condition) {
    // return withdrawAddressBO.queryPersonalAddressList(condition);
    // }
    //
    // @Override
    // public PersonalAddress getPersonalAddress(String code) {
    // return withdrawAddressBO.getPersonalAddress(code);
    // }
}
