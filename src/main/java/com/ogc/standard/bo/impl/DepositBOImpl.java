/**
 * @Title DepositBOImpl.java 
 * @Package com.ogc.standard.bo.impl 
 * @Description 
 * @author dl  
 * @date 2018年8月31日 下午3:03:52 
 * @version V1.0   
 */
package com.ogc.standard.bo.impl;

import java.math.BigDecimal;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ogc.standard.bo.IDepositBO;
import com.ogc.standard.bo.base.PaginableBOImpl;
import com.ogc.standard.core.OrderNoGenerater;
import com.ogc.standard.dao.IDepositDAO;
import com.ogc.standard.domain.Deposit;
import com.ogc.standard.enums.EDepositType;
import com.ogc.standard.enums.EErrorCode_main;
import com.ogc.standard.enums.EGeneratePrefix;
import com.ogc.standard.exception.BizException;

/** 
 * @author: dl 
 * @since: 2018年8月31日 下午3:03:52 
 * @history:
 */
@Component
public class DepositBOImpl extends PaginableBOImpl<Deposit> implements
        IDepositBO {

    @Autowired
    private IDepositDAO depositDAO;

    @Override
    public Deposit getDeposit(String code) {
        Deposit data = null;
        if (StringUtils.isNotBlank(code)) {
            Deposit condition = new Deposit();
            condition.setCode(code);
            data = depositDAO.select(condition);
            if (data == null) {
                throw new BizException(EErrorCode_main.code_NOTEXIST.getCode());

            }
        }
        return data;
    }

    @Override
    public String saveDeposit(String symbol, String fromAddress,
            String toAddress, BigDecimal amount, EDepositType depositType,
            String hash, BigDecimal gasFee, Date confirmDatetime) {
        String code = OrderNoGenerater.generate(EGeneratePrefix.DEPOSIT
            .getCode());
        Deposit deposit = new Deposit();

        deposit.setCode(code);
        deposit.setCurrency(symbol);
        deposit.setFromAddress(fromAddress);
        deposit.setToAddress(toAddress);
        deposit.setAmount(amount);

        deposit.setType(depositType.getCode());
        deposit.setTxHash(hash);
        deposit.setTxFee(gasFee);
        deposit.setConfirmDatetime(confirmDatetime);
        deposit.setCreateDatetime(new Date());
        depositDAO.insert(deposit);
        return code;
    }

}
