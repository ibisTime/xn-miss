package com.ogc.standard.ao.impl;

import java.math.BigDecimal;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ogc.standard.ao.IWithdrawAO;
import com.ogc.standard.bo.IAccountBO;
import com.ogc.standard.bo.ISYSConfigBO;
import com.ogc.standard.bo.ISYSUserBO;
import com.ogc.standard.bo.ISmsOutBO;
import com.ogc.standard.bo.IUserBO;
import com.ogc.standard.bo.IWithdrawBO;
import com.ogc.standard.bo.base.Paginable;
import com.ogc.standard.common.AmountUtil;
import com.ogc.standard.domain.Account;
import com.ogc.standard.domain.SYSUser;
import com.ogc.standard.domain.User;
import com.ogc.standard.domain.Withdraw;
import com.ogc.standard.enums.EBoolean;
import com.ogc.standard.enums.EChannelType;
import com.ogc.standard.enums.EJourBizTypeBusiness;
import com.ogc.standard.enums.EJourBizTypePlat;
import com.ogc.standard.enums.ESystemAccount;
import com.ogc.standard.enums.EWithdrawStatus;
import com.ogc.standard.exception.BizException;
import com.ogc.standard.exception.EBizErrorCode;
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
    private ISYSUserBO sysUserBO;

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
            String payNote, String channelOrder, BigDecimal payFee,
            String systemCode) {
        Withdraw data = withdrawBO.getWithdraw(code, systemCode);
        if (!EWithdrawStatus.Approved_YES.getCode().equals(data.getStatus())) {
            throw new BizException("xn000000", "申请记录状态不是待支付状态，无法支付");
        }
        if (EBoolean.YES.getCode().equals(payResult)) {
            if (StringUtils.isBlank(channelOrder) || null == payFee) {
                throw new BizException("xn000000", "请填写渠道单号和转账费");
            }
        }
        if (EBoolean.YES.getCode().equals(payResult)) {
            payOrderYES(data, payUser, payNote, channelOrder, payFee);
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
            payCode, null);
        Account dbAccount = accountBO.getAccount(data.getAccountNumber());
        BigDecimal totalAmount = data.getAmount().add(data.getFee());
        // 释放冻结流水
        accountBO.unfrozenAmount(dbAccount, totalAmount, data.getCode());
    }

    private void payOrderYES(Withdraw data, String payUser, String payNote,
            String payCode, BigDecimal payFee) {
        withdrawBO.payOrder(data, EWithdrawStatus.Pay_YES, payUser, payNote,
            payCode, payFee);
        Account dbAccount = accountBO.getAccount(data.getAccountNumber());
        // 解冻
        BigDecimal totalAmount = data.getAmount().add(data.getFee());
        accountBO.cutFrozenAmount(dbAccount, totalAmount);
        // 系统账户扣除转账费
        accountBO.changeAmount(ESystemAccount.SYS_ACOUNT_CNY.getCode(),
            EChannelType.Offline, payCode, null, data.getCode(),
            EJourBizTypePlat.AJ_ZFTDF.getCode(), "取现转账手续费", payFee.negate());
    }

    @Override
    public Paginable<Withdraw> queryWithdrawPage(int start, int limit,
            Withdraw condition) {
        Paginable<Withdraw> page = withdrawBO.getPaginable(start, limit,
            condition);
        if (CollectionUtils.isNotEmpty(page.getList())) {
            List<Withdraw> list = page.getList();
            for (Withdraw data : list) {
                initWithdraw(data);
            }
        }
        return page;
    }

    @Override
    public List<Withdraw> queryWithdrawList(Withdraw condition) {
        List<Withdraw> list = withdrawBO.queryWithdrawList(condition);
        if (CollectionUtils.isNotEmpty(list)) {
            for (Withdraw data : list) {
                initWithdraw(data);
            }
        }
        return list;
    }

    @Override
    public Withdraw getWithdraw(String code, String systemCode) {
        Withdraw data = withdrawBO.getWithdraw(code, systemCode);
        initWithdraw(data);
        return data;
    }

    private void initWithdraw(Withdraw data) {
        SYSUser sysUser = sysUserBO.getSYSUser(data.getApplyUser());
        User user = new User();
        user.setMobile(sysUser.getMobile());
        user.setLoginName(sysUser.getLoginName());
        data.setUser(user);
    }

    // 取现回录
    @Override
    public void withdrawEnter(String accountNumber, BigDecimal amount,
            String withDate, String channelOrder, String withNote,
            String updater) {
        if (!ESystemAccount.SYS_ACOUNT_OFFLINE.getCode().equals(accountNumber)
                && !ESystemAccount.SYS_ACOUNT_WEIXIN.getCode().equals(
                    accountNumber)
                && !ESystemAccount.SYS_ACOUNT_B.getCode().equals(accountNumber)) {
            throw new BizException(EBizErrorCode.DEFAULT.getCode(), "只支持系统托管账户");
        }

        Account account = accountBO.getAccount(accountNumber);
        if (account.getAmount().compareTo(BigDecimal.ZERO) <= 0) {
            throw new BizException(EBizErrorCode.DEFAULT.getCode(), "账户余额不足");
        }
        String bizNote = null;
        String jourBizType = null;
        if (ESystemAccount.SYS_ACOUNT_OFFLINE.getCode().equals(accountNumber)
                || ESystemAccount.SYS_ACOUNT_WEIXIN.getCode().equals(
                    accountNumber)) {
            bizNote = "平台于" + withDate + "进行取现" + AmountUtil.div(amount, 1000L)
                    + "元";
            jourBizType = EJourBizTypePlat.AJ_QX.getCode();
        } else {
            bizNote = "品牌方于" + withDate + "进行取现"
                    + AmountUtil.div(amount, 1000L) + "元";
            jourBizType = EJourBizTypeBusiness.AJ_QX.getCode();
        }

        if (StringUtils.isNotBlank(withNote)) {
            bizNote = bizNote + withNote;
        }
        accountBO.changeAmount(accountNumber, EChannelType.Offline,
            channelOrder, null, channelOrder, jourBizType, bizNote,
            amount.negate());
    }
}
