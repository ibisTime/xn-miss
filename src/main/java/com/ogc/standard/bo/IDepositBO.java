/**
 * @Title IDepositBO.java 
 * @Package com.ogc.standard.bo 
 * @Description 
 * @author dl  
 * @date 2018年8月31日 下午3:01:19 
 * @version V1.0   
 */
package com.ogc.standard.bo;

import java.math.BigDecimal;
import java.util.Date;

import com.ogc.standard.bo.base.IPaginableBO;
import com.ogc.standard.domain.Deposit;
import com.ogc.standard.enums.EDepositType;

/** 
 * @author: dl 
 * @since: 2018年8月31日 下午3:01:19 
 * @history:
 */
public interface IDepositBO extends IPaginableBO<Deposit> {

    public Deposit getDeposit(String code);

    public String saveDeposit(String symbol, String fromAddress,
            String toAddress, BigDecimal amount, EDepositType depositType,
            String hash, BigDecimal gasFee, Date confirmDatetime);
}
