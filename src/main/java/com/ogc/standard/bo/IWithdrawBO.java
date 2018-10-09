package com.ogc.standard.bo;

import java.util.List;

import com.ogc.standard.bo.base.IPaginableBO;
import com.ogc.standard.domain.Account;
import com.ogc.standard.domain.Withdraw;
import com.ogc.standard.enums.EWithdrawStatus;

public interface IWithdrawBO extends IPaginableBO<Withdraw> {

    Long doCheckAndGetFee(Account account, Long amount);

    String applyOrder(Account account, Long amount, Long fee,
            String payCardInfo, String payCardNo, String applyUser,
            String applyNote);

    void approveOrder(Withdraw data, EWithdrawStatus status,
            String approveUser, String approveNote);

    void payOrder(Withdraw data, EWithdrawStatus status, String payUser,
            String payNote, String channelOrder);

    List<Withdraw> queryWithdrawList(Withdraw condition);

    Withdraw getWithdraw(String code, String systemCode);
}
