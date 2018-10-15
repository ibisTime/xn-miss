package com.ogc.standard.bo;

import java.math.BigDecimal;
import java.util.List;

import com.ogc.standard.bo.base.IPaginableBO;
import com.ogc.standard.domain.Account;
import com.ogc.standard.domain.Jour;
import com.ogc.standard.enums.EChannelType;

/**
 * @author: xieyj 
 * @since: 2016年12月23日 下午2:40:13 
 * @history:
 */
public interface IJourBO extends IPaginableBO<Jour> {

    // 正常新增
    public String addJour(Account dbAccount, EChannelType channelType,
            String channelOrder, String payGroup, String refNo, String bizType,
            String bizNote, BigDecimal transAmount);

    public List<Jour> queryJourList(Jour condition);

    public Jour getJour(String code, String systemCode);

    public Jour getJourNotException(String code, String systemCode);

    public BigDecimal getTotalAmount(String bizType, String channelType,
            String accountNumber);

}
