package com.ogc.standard.bo.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ogc.standard.bo.ISYSConfigBO;
import com.ogc.standard.bo.IWithdrawBO;
import com.ogc.standard.bo.base.PaginableBOImpl;
import com.ogc.standard.core.OrderNoGenerater;
import com.ogc.standard.dao.IWithdrawDAO;
import com.ogc.standard.domain.Account;
import com.ogc.standard.domain.EthMAddress;
import com.ogc.standard.domain.Withdraw;
import com.ogc.standard.enums.EChannelType;
import com.ogc.standard.enums.EErrorCode_main;
import com.ogc.standard.enums.EWithdrawStatus;
import com.ogc.standard.exception.BizException;

@Component
public class WithdrawBOImpl extends PaginableBOImpl<Withdraw> implements
        IWithdrawBO {

    @Autowired
    private IWithdrawDAO withdrawDAO;

    @Autowired
    ISYSConfigBO sysConfigBO;

    @Override
    public String applyOrder(Account account, BigDecimal amount,
            BigDecimal fee, String payCardInfo, String payCardNo,
            String applyUser, String applyNote) {
        if (amount.compareTo(BigDecimal.ZERO) == 0) {
            throw new BizException(EErrorCode_main.with_ZERO.getCode());
        }
        String code = OrderNoGenerater.generate("QX");
        Withdraw data = new Withdraw();
        data.setCode(code);
        data.setAccountNumber(account.getAccountNumber());
        data.setAccountType(account.getType());
        data.setCurrency(account.getCurrency());
        data.setAmount(amount);

        data.setFee(fee);
        data.setActualAmount(amount.subtract(fee));
        data.setChannelType(EChannelType.Online.getCode());
        data.setChannelBank(account.getCurrency());
        data.setPayCardInfo(payCardInfo);

        data.setPayCardNo(payCardNo);
        data.setStatus(EWithdrawStatus.toApprove.getCode());
        data.setApplyUser(applyUser);
        data.setApplyNote(applyNote);
        data.setApplyDatetime(new Date());
        withdrawDAO.insert(data);
        return code;
    }

    @Override
    public void approveOrder(Withdraw data, EWithdrawStatus status,
            String approveUser, String approveNote) {
        data.setStatus(status.getCode());
        data.setApproveUser(approveUser);
        data.setApproveNote(approveNote);
        data.setApproveDatetime(new Date());
        withdrawDAO.approveOrder(data);

    }

    @Override
    public void payOrder(Withdraw data, EWithdrawStatus status, String payUser,
            String payNote, String channelOrder, BigDecimal payFee) {
        data.setStatus(status.getCode());
        data.setPayUser(payUser);
        data.setPayNote(payNote);
        data.setPayFee(payFee);
        data.setChannelOrder(channelOrder);
        data.setPayDatetime(new Date());
        withdrawDAO.payOrder(data);
    }

    @Override
    public void broadcastOrder(Withdraw data, String txHash, String updater) {
        data.setStatus(EWithdrawStatus.Broadcast.getCode());
        data.setChannelOrder(txHash);
        withdrawDAO.broadcastOrder(data);
    }

    @Override
    public List<Withdraw> queryWithdrawList(Withdraw condition) {
        return withdrawDAO.selectList(condition);
    }

    @Override
    public Withdraw getWithdraw(String code) {
        Withdraw order = null;
        if (StringUtils.isNotBlank(code)) {
            Withdraw condition = new Withdraw();
            condition.setCode(code);
            order = withdrawDAO.select(condition);
        }
        return order;
    }

    /**
    * @see com.std.account.bo.IWithdrawBO#doCheckTimes(java.lang.String)
    */
    @Override
    public void doCheckTimes(Account account) {
        // 判断本月申请次数是否达到上限
        // String monthTimesValue = sysConfigBO
        // .getStringValue(SysConstants.CUSERMONTIMES);
        // if (StringUtils.isNotBlank(monthTimesValue)) {// 月取现次数判断
        // Withdraw condition = new Withdraw();
        // condition.setAccountNumber(account.getAccountNumber());
        // condition.setApplyDatetimeStart(DateUtil.getCurrentMonthFirstDay());
        // condition.setApplyDatetimeEnd(DateUtil.getCurrentMonthLastDay());
        // long totalCount = withdrawDAO.selectTotalCount(condition);
        // long maxMonthTimes = Long.valueOf(monthTimesValue);
        // if (totalCount >= maxMonthTimes) {
        // throw new BizException("xn0000", "每月取现最多" + maxMonthTimes
        // + "次,本月申请次数已用尽");
        // }
        // }

        // 判断是否还有未处理的取现记录
        Withdraw condition = new Withdraw();
        condition.setAccountNumber(account.getAccountNumber());
        condition.setStatus("134");// 待申请，审核成功待支付
        if (withdrawDAO.selectTotalCount(condition) > 0) {
            throw new BizException(EErrorCode_main.with_AGAIN.getCode());
        }
    }

    @Override
    public Withdraw getWithdrawByChannelOrder(String hash) {
        Withdraw withdraw = null;
        Withdraw condition = new Withdraw();
        condition.setChannelOrder(hash);
        List<Withdraw> results = withdrawDAO.selectList(condition);
        if (CollectionUtils.isNotEmpty(results)) {
            withdraw = results.get(0);
        }
        return withdraw;
    }

    @Override
    public EthMAddress getAddressUseInfo(String fromAddress, String currency) {
        Withdraw data = new Withdraw();
        data.setPayUser(fromAddress);
        data.setCurrency(currency);
        data.setStatus(EWithdrawStatus.Pay_YES.getCode());
        return withdrawDAO.selectAddressUseInfo(data);
    }

    @Override
    public BigDecimal getTotalWithdraw(String currency) {
        Withdraw condition = new Withdraw();
        condition.setCurrency(currency);
        condition.setStatus(EWithdrawStatus.Pay_YES.getCode());
        return withdrawDAO.selectTotalAmount(condition);
    }

    @Override
    public void returnOrder(String code, String payUser, String payNote) {
        Withdraw data = new Withdraw();
        data.setCode(code);
        data.setPayUser(payUser);
        data.setPayNote(payNote);
        data.setStatus(EWithdrawStatus.Pay_NO.getCode());
        withdrawDAO.payOrder(data);
    }
}
