package com.ogc.standard.ao;

import java.util.List;

import com.ogc.standard.annotation.ServiceModule;
import com.ogc.standard.bo.base.Paginable;
import com.ogc.standard.domain.Withdraw;

@ServiceModule
public interface IWithdrawAO {
    String DEFAULT_ORDER_COLUMN = "code";

    public String applyOrderTradePwd(String accountNumber, Long amount,
            String payCardInfo, String payCardNo, String applyUser,
            String applyNote, String tradePwd);

    public String applyOrder(String accountNumber, Long amount,
            String payCardInfo, String payCardNo, String applyUser,
            String applyNote);

    public void approveOrder(String code, String approveUser,
            String approveResult, String approveNote, String systemCode);

    public void payOrder(String code, String payUser, String payResult,
            String payNote, String channelOrder, String systemCode);

    public Paginable<Withdraw> queryWithdrawPage(int start, int limit,
            Withdraw condition);

    public List<Withdraw> queryWithdrawList(Withdraw condition);

    public Withdraw getWithdraw(String code, String systemCode);
}
