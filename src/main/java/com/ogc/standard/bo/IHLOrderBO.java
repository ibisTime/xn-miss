package com.ogc.standard.bo;

import java.math.BigDecimal;
import java.util.List;

import com.ogc.standard.bo.base.IPaginableBO;
import com.ogc.standard.domain.Account;
import com.ogc.standard.domain.HLOrder;
import com.ogc.standard.domain.Jour;
import com.ogc.standard.enums.EHLOrderStatus;

public interface IHLOrderBO extends IPaginableBO<HLOrder> {

    String applyOrder(Account account, Jour jour, BigDecimal applyAmount,
            String applyUser, String applyNote);

    void approveOrder(HLOrder order, EHLOrderStatus status, String approveUser,
            String approveNote);

    HLOrder getHLOrder(String code, String systemCode);

    List<HLOrder> queryHLOrderList(HLOrder condition);

}
