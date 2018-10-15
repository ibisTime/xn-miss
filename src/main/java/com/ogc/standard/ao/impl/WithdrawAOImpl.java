package com.ogc.standard.ao.impl;

import java.math.BigDecimal;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ogc.standard.ao.IWithdrawAO;
import com.ogc.standard.bo.IAccountBO;
import com.ogc.standard.bo.ISYSConfigBO;
import com.ogc.standard.bo.ISmsOutBO;
import com.ogc.standard.bo.IUserBO;
import com.ogc.standard.bo.IWithdrawBO;
import com.ogc.standard.bo.base.Paginable;
import com.ogc.standard.domain.Account;
import com.ogc.standard.domain.User;
import com.ogc.standard.domain.Withdraw;
import com.ogc.standard.enums.EBoolean;
import com.ogc.standard.enums.EChannelType;
import com.ogc.standard.enums.ECurrency;
import com.ogc.standard.enums.EJourBizType;
import com.ogc.standard.enums.EWithdrawStatus;
import com.ogc.standard.exception.BizException;
import com.ogc.standard.util.CalculationUtil;

@Service
public class WithdrawAOImpl implements IWithdrawAO {
    @Autowired
    private IAccountBO accountBO;

    @Autowired
    private IWithdrawBO withdrawBO;

    @Autowired
    private IUserBO userBO;

    @Autowired
    private ISYSConfigBO sysConfigBO;

    @Autowired
    private ISmsOutBO smsOutBO;

    @Override
    @Transactional
    public String applyOrderTradePwd(String accountNumber, BigDecimal amount,
            String payCardInfo, String payCardNo, String applyUser,
            String applyNote, String tradePwd) {
        Account dbAccount = accountBO.getAccount(accountNumber);
        // 验证交易密码
        userBO.checkTradePwd(dbAccount.getUserId(), tradePwd);
        // 取现获取手续费
        BigDecimal fee = withdrawBO.doCheckAndGetFee(dbAccount, amount);
        // 产生取现订单
        String withdrawCode = withdrawBO.applyOrder(dbAccount, amount, fee,
            payCardInfo, payCardNo, applyUser, applyNote);
        // 冻结取现金额
        BigDecimal totalAmount = amount.add(fee);
        accountBO.frozenAmount(dbAccount, totalAmount, withdrawCode);
        return withdrawCode;
    }

    @Override
    @Transactional
    public String applyOrder(String accountNumber, BigDecimal amount,
            String payCardInfo, String payCardNo, String applyUser,
            String applyNote) {
        Account dbAccount = accountBO.getAccount(accountNumber);
        // 取现前提检查
        BigDecimal fee = withdrawBO.doCheckAndGetFee(dbAccount, amount);
        // 产生取现订单
        String withdrawCode = withdrawBO.applyOrder(dbAccount, amount, fee,
            payCardInfo, payCardNo, applyUser, applyNote);
        // 冻结取现金额
        BigDecimal totalAmount = amount.add(fee);
        accountBO.frozenAmount(dbAccount, totalAmount, withdrawCode);
        return withdrawCode;
    }

    @Override
    @Transactional
    public void approveOrder(String code, String approveUser,
            String approveResult, String approveNote, String systemCode) {
        Withdraw data = withdrawBO.getWithdraw(code, systemCode);
        if (!EWithdrawStatus.toApprove.getCode().equals(data.getStatus())) {
            throw new BizException("xn000000", "申请记录状态不是待审批状态，无法审批");
        }
        if (EBoolean.YES.getCode().equals(approveResult)) {
            approveOrderYES(data, approveUser, approveNote);
        } else {
            approveOrderNO(data, approveUser, approveNote);
        }
    }

    @Override
    @Transactional
    public void payOrder(String code, String payUser, String payResult,
            String payNote, String channelOrder, String systemCode) {
        Withdraw data = withdrawBO.getWithdraw(code, systemCode);
        if (!EWithdrawStatus.Approved_YES.getCode().equals(data.getStatus())) {
            throw new BizException("xn000000", "申请记录状态不是待支付状态，无法支付");
        }
        if (EBoolean.YES.getCode().equals(payResult)) {
            payOrderYES(data, payUser, payNote, channelOrder);
            // 发送短信
            smsOutBO.sentContent(data.getApplyUser(), "尊敬的用户，您的取现金额"
                    + CalculationUtil.divi(data.getAmount())
                    + "元已打款到银行卡，请及时查看。");
        } else {
            payOrderNO(data, payUser, payNote, channelOrder);
        }
    }

    private void approveOrderYES(Withdraw data, String approveUser,
            String approveNote) {
        withdrawBO.approveOrder(data, EWithdrawStatus.Approved_YES,
            approveUser, approveNote);
    }

    private void approveOrderNO(Withdraw data, String approveUser,
            String approveNote) {
        withdrawBO.approveOrder(data, EWithdrawStatus.Approved_NO, approveUser,
            approveNote);
        Account dbAccount = accountBO.getAccount(data.getAccountNumber());
        BigDecimal totalAmount = data.getAmount().add(data.getFee());
        // 释放冻结流水
        accountBO.unfrozenAmount(dbAccount, totalAmount, data.getCode());
    }

    private void payOrderNO(Withdraw data, String payUser, String payNote,
            String payCode) {
        withdrawBO.payOrder(data, EWithdrawStatus.Pay_NO, payUser, payNote,
            payCode);
        Account dbAccount = accountBO.getAccount(data.getAccountNumber());
        BigDecimal totalAmount = data.getAmount().add(data.getFee());
        // 释放冻结流水
        accountBO.unfrozenAmount(dbAccount, totalAmount, data.getCode());
    }

    private void payOrderYES(Withdraw data, String payUser, String payNote,
            String payCode) {
        withdrawBO.payOrder(data, EWithdrawStatus.Pay_YES, payUser, payNote,
            payCode);
        Account dbAccount = accountBO.getAccount(data.getAccountNumber());
        // 扣减冻结流水
        BigDecimal totalAmount = data.getAmount().add(data.getFee());
        accountBO.cutFrozenAmount(dbAccount, totalAmount);
        Account account = accountBO.getAccount(data.getAccountNumber());
        if (ECurrency.CNY.getCode().equals(account.getCurrency())) {
            // 托管账户减钱
            accountBO.changeAmount(data.getCompanyCode(), EChannelType.Offline,
                null, null, data.getCode(), EJourBizType.BALANCE.getCode(),
                "线下取现", totalAmount.negate());
        }
    }

    @Override
    public Paginable<Withdraw> queryWithdrawPage(int start, int limit,
            Withdraw condition) {
        Paginable<Withdraw> page = withdrawBO.getPaginable(start, limit,
            condition);
        if (CollectionUtils.isNotEmpty(page.getList())) {
            List<Withdraw> list = page.getList();
            for (Withdraw withdraw : list) {
                User user = userBO.getUser(withdraw.getApplyUser());
                withdraw.setUser(user);
            }
        }
        return page;
    }

    @Override
    public List<Withdraw> queryWithdrawList(Withdraw condition) {
        List<Withdraw> list = withdrawBO.queryWithdrawList(condition);
        if (CollectionUtils.isNotEmpty(list)) {
            for (Withdraw withdraw : list) {
                User user = userBO.getUser(withdraw.getApplyUser());
                withdraw.setUser(user);
            }
        }
        return list;
    }

    @Override
    public Withdraw getWithdraw(String code, String systemCode) {
        Withdraw withdraw = withdrawBO.getWithdraw(code, systemCode);
        User user = userBO.getUser(withdraw.getApplyUser());
        withdraw.setUser(user);
        return withdraw;
    }
}
