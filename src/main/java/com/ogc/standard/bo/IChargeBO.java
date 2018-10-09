package com.ogc.standard.bo;

import java.util.List;

import com.ogc.standard.bo.base.IPaginableBO;
import com.ogc.standard.domain.Account;
import com.ogc.standard.domain.Charge;
import com.ogc.standard.enums.EChannelType;
import com.ogc.standard.enums.EJourBizType;

public interface IChargeBO extends IPaginableBO<Charge> {
    String applyOrderOnline(Account account, String payGroup, String refNo,
            EJourBizType bizType, String bizNote, Long transAmount,
            EChannelType channelType, String applyUser);

    void callBackChange(Charge dbCharge, boolean booleanFlag);

    String applyOrderOffline(Account account, EJourBizType bizType,
            Long amount, String payCardInfo, String payCardNo,
            String applyUser, String applyNote);

    void payOrder(Charge data, boolean booleanFlag, String payUser,
            String payNote);

    List<Charge> queryChargeList(Charge condition);

    Charge getCharge(String code, String systemCode);

    // 验证渠道今日支付金额是否超限
    void doCheckTodayPayAmount(String applyUser, Long payAmount,
            EChannelType channelType, String companyCode, String systemCode);
}
