package com.ogc.standard.bo.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ogc.standard.bo.IGroupCoinJourBO;
import com.ogc.standard.bo.base.PaginableBOImpl;
import com.ogc.standard.core.OrderNoGenerater;
import com.ogc.standard.dao.IGroupCoinJourDAO;
import com.ogc.standard.domain.GroupCoin;
import com.ogc.standard.domain.GroupCoinJour;
import com.ogc.standard.enums.EErrorCode_main;
import com.ogc.standard.enums.EGeneratePrefix;
import com.ogc.standard.exception.BizException;

@Component
public class GroupCoinJourBOImpl extends PaginableBOImpl<GroupCoinJour>
        implements IGroupCoinJourBO {

    @Autowired
    private IGroupCoinJourDAO groupCoinJourDAO;

    @Override
    public String addJour(GroupCoin dbAccount, String refNo, String bizType,
            String bizNote, BigDecimal transCount) {
        if (StringUtils.isBlank(refNo)) {// 必须要有的判断。每一次流水新增，必有有对应流水分组
            throw new BizException(
                EErrorCode_main.groupjourcoin_NOTEMPTY.getCode());
        }
        if (transCount.compareTo(BigDecimal.ZERO) == 0) {
            throw new BizException(
                EErrorCode_main.groupjourcoin_NOTZERO.getCode());
        }
        String code = OrderNoGenerater
            .generate(EGeneratePrefix.AJour.getCode());

        GroupCoinJour data = new GroupCoinJour();
        data.setCode(code);
        data.setRefNo(refNo);
        data.setAccountNumber(dbAccount.getAccountNumber());
        data.setTransAmount(transCount);
        data.setUserId(dbAccount.getUserId());

        data.setGroupCode(dbAccount.getGroupCode());
        data.setSymbol(dbAccount.getSymbol());
        data.setBizType(bizType);
        data.setBizNote(bizNote);
        data.setPreAmount(dbAccount.getCount());

        data.setPostAmount(dbAccount.getCount().add(transCount));
        data.setCreateDatetime(new Date());
        groupCoinJourDAO.insert(data);
        return code;
    }

    @Override
    public String addFrozenJour(GroupCoin dbAccount, String refNo,
            String bizType, String bizNote, BigDecimal transCount) {
        if (StringUtils.isBlank(refNo)) {// 必须要有的判断。每一次流水新增，必有有对应流水分组
            throw new BizException(
                EErrorCode_main.groupjourcoin_NOTEMPTY.getCode());
        }
        if (transCount.compareTo(BigDecimal.ZERO) == 0) {
            throw new BizException(
                EErrorCode_main.groupjourcoin_NOTZERO.getCode());
        }
        String code = OrderNoGenerater
            .generate(EGeneratePrefix.AJour.getCode());

        GroupCoinJour data = new GroupCoinJour();
        data.setCode(code);
        data.setRefNo(refNo);
        data.setAccountNumber(dbAccount.getAccountNumber());
        data.setTransAmount(transCount);
        data.setGroupCode(dbAccount.getGroupCode());

        data.setUserId(dbAccount.getUserId());
        data.setSymbol(dbAccount.getSymbol());
        data.setBizType(bizType);
        data.setBizNote(bizNote);
        data.setPreAmount(dbAccount.getFrozenCount());

        data.setPostAmount(dbAccount.getFrozenCount().add(transCount));
        data.setCreateDatetime(new Date());
        groupCoinJourDAO.insert(data);
        return code;
    }

    @Override
    public List<GroupCoinJour> queryGroupCoinJourList(GroupCoinJour condition) {
        return groupCoinJourDAO.selectList(condition);
    }

}
