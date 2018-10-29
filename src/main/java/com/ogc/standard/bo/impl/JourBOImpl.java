/**
 * @Title AJourBOImpl.java 
 * @Package com.ibis.account.bo.impl 
 * @Description 
 * @author miyb  
 * @date 2015-3-15 下午3:22:07 
 * @version V1.0   
 */
package com.ogc.standard.bo.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ogc.standard.bo.IJourBO;
import com.ogc.standard.bo.base.PaginableBOImpl;
import com.ogc.standard.common.DateUtil;
import com.ogc.standard.core.OrderNoGenerater;
import com.ogc.standard.dao.IJourDAO;
import com.ogc.standard.domain.Account;
import com.ogc.standard.domain.Jour;
import com.ogc.standard.enums.EChannelType;
import com.ogc.standard.enums.EGeneratePrefix;
import com.ogc.standard.enums.EJourStatus;
import com.ogc.standard.enums.EJourType;
import com.ogc.standard.exception.BizException;

/** 
 * @author: miyb 
 * @since: 2015-3-15 下午3:22:07 
 * @history:
 */
@Component
public class JourBOImpl extends PaginableBOImpl<Jour> implements IJourBO {
    @Autowired
    private IJourDAO jourDAO;

    @Override
    public String addJour(Account dbAccount, EChannelType channelType,
            String channelOrder, String payGroup, String refNo, String bizType,
            String bizNote, BigDecimal transAmount) {
        if (StringUtils.isBlank(refNo)) {// 必须要有的判断。每一次流水新增，必有有对应流水分组
            throw new BizException("xn000000", "新增流水流水分组不能为空");
        }
        if (transAmount.compareTo(BigDecimal.ZERO) == 0) {
            throw new BizException("xn000000", "新增流水变动金额不能为0");
        }
        String code = OrderNoGenerater
            .generate(EGeneratePrefix.AJour.getCode());

        Jour data = new Jour();
        data.setCode(code);

        data.setPayGroup(payGroup);
        data.setRefNo(refNo);
        data.setChannelOrder(channelOrder);// 内部转账时为空，外部转账时必定有
        data.setAccountNumber(dbAccount.getAccountNumber());
        data.setTransAmount(transAmount);

        data.setUserId(dbAccount.getUserId());
        data.setRealName(dbAccount.getRealName());
        data.setType(EJourType.BALANCE.getCode());// TODO
        data.setAccountType(dbAccount.getType());
        data.setCurrency(dbAccount.getCurrency());
        data.setBizType(bizType);

        data.setBizNote(bizNote);
        data.setPreAmount(dbAccount.getAmount());
        data.setPostAmount(dbAccount.getAmount().add(transAmount));
        data.setStatus(EJourStatus.todoCheck.getCode());
        data.setRemark("记得对账哦");

        data.setCreateDatetime(new Date());
        data.setWorkDate(DateUtil.dateToStr(new Date(),
            DateUtil.DB_DATE_FORMAT_STRING));
        data.setChannelType(channelType.getCode());
        data.setSystemCode(dbAccount.getSystemCode());
        data.setCompanyCode(dbAccount.getCompanyCode());
        jourDAO.insert(data);
        return code;
    }

    @Override
    public Jour getJour(String code, String systemCode) {
        Jour data = null;
        if (StringUtils.isNotBlank(code)) {
            Jour condition = new Jour();
            condition.setCode(code);
            condition.setSystemCode(systemCode);
            data = jourDAO.select(condition);
            if (data == null) {
                throw new BizException("xn000000", "单号不存在");
            }
        }
        return data;
    }

    @Override
    public Jour getJourNotException(String code, String systemCode) {
        Jour data = null;
        if (StringUtils.isNotBlank(code)) {
            Jour condition = new Jour();
            condition.setCode(code);
            condition.setSystemCode(systemCode);
            data = jourDAO.select(condition);
        }
        return data;
    }

    @Override
    public List<Jour> queryJourList(Jour condition) {
        return jourDAO.selectList(condition);
    }

    @Override
    public BigDecimal getTotalAmount(String bizType, String channelType,
            String accountNumber) {
        Jour jour = new Jour();
        jour.setBizType(bizType);
        jour.setChannelType(channelType);
        jour.setAccountNumber(accountNumber);
        BigDecimal a = jourDAO.selectTotalAmount(jour);
        return a.abs();
    }
}
