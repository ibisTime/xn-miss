package com.ogc.standard.bo.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ogc.standard.bo.IHLOrderBO;
import com.ogc.standard.bo.base.PaginableBOImpl;
import com.ogc.standard.core.OrderNoGenerater;
import com.ogc.standard.dao.IHLOrderDAO;
import com.ogc.standard.domain.Account;
import com.ogc.standard.domain.HLOrder;
import com.ogc.standard.domain.Jour;
import com.ogc.standard.enums.EDirection;
import com.ogc.standard.enums.EErrorCode_main;
import com.ogc.standard.enums.EHLOrderStatus;
import com.ogc.standard.exception.BizException;

@Component
public class HLOrderBOImpl extends PaginableBOImpl<HLOrder> implements
        IHLOrderBO {
    @Autowired
    private IHLOrderDAO hlOrderDAO;

    @Override
    public String applyOrder(Account account, Jour jour,
            BigDecimal applyAmount, String applyUser, String applyNote) {
        if (applyAmount.compareTo(BigDecimal.ZERO) == 0) {
            throw new BizException(EErrorCode_main.hl_ZERO.getCode());
        }
        String code = OrderNoGenerater.generate("HL");
        HLOrder data = new HLOrder();
        data.setCode(code);
        data.setAccountNumber(account.getAccountNumber());

        data.setCurrency(account.getCurrency());
        data.setJourCode(jour.getCode());
        data.setChannelType(jour.getChannelType());
        if (applyAmount.compareTo(BigDecimal.ZERO) == 1) {
            data.setDirection(EDirection.PLUS.getCode());
        } else {
            data.setDirection(EDirection.MINUS.getCode());
        }

        data.setAmount(applyAmount);
        data.setStatus(EHLOrderStatus.toApprove.getCode());
        data.setApplyUser(applyUser);
        data.setApplyNote(applyNote);
        data.setApplyDatetime(new Date());

        hlOrderDAO.insert(data);
        return code;
    }

    @Override
    public void approveOrder(HLOrder order, EHLOrderStatus status,
            String approveUser, String approveNote) {
        order.setStatus(status.getCode());
        order.setApproveUser(approveUser);
        order.setApproveNote(approveNote);
        order.setApproveDatetime(new Date());
        hlOrderDAO.approveOrder(order);
    }

    @Override
    public HLOrder getHLOrder(String code, String systemCode) {
        HLOrder order = null;
        if (StringUtils.isNotBlank(code)) {
            HLOrder condition = new HLOrder();
            condition.setCode(code);
            order = hlOrderDAO.select(condition);
            if (order == null) {
                throw new BizException(EErrorCode_main.code_NOTEXIST.getCode());
            }
        }
        return order;
    }

    @Override
    public List<HLOrder> queryHLOrderList(HLOrder condition) {
        return hlOrderDAO.selectList(condition);
    }
}
