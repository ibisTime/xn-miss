package com.ogc.standard.bo.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ogc.standard.bo.IBankcardBO;
import com.ogc.standard.bo.IChannelBankBO;
import com.ogc.standard.bo.ISYSConfigBO;
import com.ogc.standard.bo.IWithdrawBO;
import com.ogc.standard.bo.base.PaginableBOImpl;
import com.ogc.standard.common.AmountUtil;
import com.ogc.standard.common.DateUtil;
import com.ogc.standard.common.SysConstant;
import com.ogc.standard.core.OrderNoGenerater;
import com.ogc.standard.dao.IWithdrawDAO;
import com.ogc.standard.domain.Account;
import com.ogc.standard.domain.Bankcard;
import com.ogc.standard.domain.ChannelBank;
import com.ogc.standard.domain.Withdraw;
import com.ogc.standard.enums.EAccountType;
import com.ogc.standard.enums.EChannelType;
import com.ogc.standard.enums.ECurrency;
import com.ogc.standard.enums.EGeneratePrefix;
import com.ogc.standard.enums.EWithdrawStatus;
import com.ogc.standard.exception.BizException;

@Component
public class WithdrawBOImpl extends PaginableBOImpl<Withdraw> implements
        IWithdrawBO {
    @Autowired
    private IBankcardBO bankcardBO; // 取现银行卡户名

    @Autowired
    private IWithdrawDAO withdrawDAO;

    @Autowired
    ISYSConfigBO sysConfigBO;

    @Autowired
    IChannelBankBO channelBankBO;

    @Override
    public String applyOrder(Account account, BigDecimal amount,
            BigDecimal fee, String payCardInfo, String payCardNo,
            String applyUser, String applyNote) {
        if (amount.compareTo(BigDecimal.ZERO) == 0) {
            throw new BizException("xn000000", "取现金额不能为0");
        }
        String code = OrderNoGenerater.generate(EGeneratePrefix.WITHDRAW
            .getCode());
        Withdraw data = new Withdraw();
        data.setCode(code);
        data.setAccountNumber(account.getAccountNumber());
        data.setType(account.getType());
        data.setAmount(amount);
        data.setCurrency(account.getCurrency());
        data.setFee(fee);

        data.setChannelType(EChannelType.Offline.getCode());
        data.setPayCardInfo(payCardInfo);
        // 取现户名，应该和银行卡户名一致
        Bankcard bankcard = bankcardBO.getBankcardByBankcardNumber(payCardNo);
        if (null == bankcard) {
            data.setAccountName(account.getRealName());
        } else {
            // 设置银行名称和银行名称
            data.setAccountName(bankcard.getRealName());
            data.setPayCardInfo(bankcard.getBankName());
            data.setSubbranch(bankcard.getSubbranch());
            // 获取银行编号
            ChannelBank channelBank = channelBankBO.getChannelBank(bankcard
                .getBankCode());
            if (null != channelBank) {
                data.setChannelBank(channelBank.getChannelBank());
            }
        }
        data.setPayCardNo(payCardNo);
        data.setStatus(EWithdrawStatus.toApprove.getCode());
        data.setApplyUser(applyUser);

        data.setApplyNote(applyNote);
        data.setApplyDatetime(new Date());
        data.setSystemCode(account.getSystemCode());
        data.setCompanyCode(account.getCompanyCode());
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
            String payNote, String channelOrder) {
        data.setStatus(status.getCode());
        data.setPayUser(payUser);
        data.setPayNote(payNote);
        data.setPayGroup(null);
        data.setChannelOrder(channelOrder);
        data.setPayDatetime(new Date());
        withdrawDAO.payOrder(data);
    }

    @Override
    public List<Withdraw> queryWithdrawList(Withdraw condition) {
        return withdrawDAO.selectList(condition);
    }

    @Override
    public Withdraw getWithdraw(String code, String systemCode) {
        Withdraw order = null;
        if (StringUtils.isNotBlank(code)) {
            Withdraw condition = new Withdraw();
            condition.setCode(code);
            condition.setSystemCode(systemCode);
            order = withdrawDAO.select(condition);
        }
        return order;
    }

    @Override
    public BigDecimal doCheckAndGetFee(Account account, BigDecimal amount) {
        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new BizException("xn000000", "提现金额需大于零");
        }
        Withdraw condition = new Withdraw();
        condition.setAccountNumber(account.getAccountNumber());
        condition.setStatus("13");// 待申请，审核成功待支付
        if (withdrawDAO.selectTotalCount(condition) > 0) {
            throw new BizException("xn000000", "上笔取现申请还未处理成功，不能再次申请");
        }

        Map<String, String> argsMap = sysConfigBO.getConfigsMap(null);

        String monthTimesKey = null; // 本月申请次数是否达到上限
        String qxDbzdjeValue = null;// 取现单笔最大金额
        String qxbs = null; // 取现倍数
        String qxfl = null; // 取现手续费率
        if (ECurrency.CNY.getCode().equals(account.getCurrency())) {
            if (EAccountType.Customer.getCode().equals(account.getType())) {
                monthTimesKey = SysConstant.CUSERMONTIMES;
                qxbs = SysConstant.CUSERQXBS;
                qxfl = SysConstant.CUSERQXFL;
            } else if (EAccountType.Merchant.getCode()
                .equals(account.getType())) {
                monthTimesKey = SysConstant.BUSERMONTIMES;
                qxbs = SysConstant.BUSERQXBS;
                qxfl = SysConstant.BUSERQXFL;
            }
            qxDbzdjeValue = argsMap.get(SysConstant.QXDBZDJE);
        } else if (ECurrency.HW_XJK.getCode().equals(account.getCurrency())) {
            monthTimesKey = SysConstant.CUSERMONTIMES_XJK;
            qxDbzdjeValue = argsMap.get(SysConstant.QXDBZDJE_XJK);
            qxbs = SysConstant.CUSERQXBS_XJK;
            qxfl = SysConstant.CUSERQXFL_XJK;
        } else {
            throw new BizException("xn0000", "币种不支持");
        }

        String monthTimesValue = argsMap.get(monthTimesKey);
        if (StringUtils.isNotBlank(monthTimesValue)) {// 月取现次数判断
            Withdraw cond = new Withdraw();
            cond.setAccountNumber(account.getAccountNumber());
            cond.setApplyDatetimeStart(DateUtil.getCurrentMonthFirstDay());
            cond.setApplyDatetimeEnd(DateUtil.getCurrentMonthLastDay());
            long totalCount = withdrawDAO.selectTotalCount(cond);
            long maxMonthTimes = Long.valueOf(monthTimesValue);
            if (totalCount >= maxMonthTimes) {
                throw new BizException("xn0000", "每月取现最多" + maxMonthTimes
                        + "次,本月申请次数已用尽");
            }
        }

        if (StringUtils.isNotBlank(qxDbzdjeValue)) {
            BigDecimal qxDbzdje = AmountUtil.mul(new BigDecimal(1000),
                Double.valueOf(qxDbzdjeValue));
            if (amount.compareTo(qxDbzdje) > 0) {
                throw new BizException("xn000000", "取现单笔最大金额不能超过"
                        + qxDbzdjeValue + "元。");
            }
        }

        String qxBsValue = argsMap.get(qxbs);
        if (StringUtils.isNotBlank(qxBsValue)) {
            // 取现金额倍数
            BigDecimal qxBs = AmountUtil.mul(new BigDecimal(1000),
                Double.valueOf(qxBsValue));
            if (qxBs.compareTo(BigDecimal.ZERO) > 0
                    && amount.divideAndRemainder(qxBs)[1]
                        .compareTo(BigDecimal.ZERO) > 0) {
                throw new BizException("xn000000", "金额请取" + qxBsValue + "的倍数");
            }
        }

        double feeRate = 0;
        String feeRateValue = argsMap.get(qxfl);
        if (StringUtils.isNotBlank(feeRateValue)) {
            feeRate = Double.valueOf(feeRateValue);
        }
        return AmountUtil.mul(amount, feeRate);
    }
}
