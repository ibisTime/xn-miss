package com.ogc.standard.ao.impl;

import java.math.BigDecimal;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ogc.standard.ao.IChargeAO;
import com.ogc.standard.ao.IWeChatAO;
import com.ogc.standard.bo.IAccountBO;
import com.ogc.standard.bo.IChargeBO;
import com.ogc.standard.bo.ISYSUserBO;
import com.ogc.standard.bo.IUserBO;
import com.ogc.standard.bo.base.Paginable;
import com.ogc.standard.domain.Account;
import com.ogc.standard.domain.Charge;
import com.ogc.standard.domain.SYSUser;
import com.ogc.standard.domain.User;
import com.ogc.standard.enums.EAccountType;
import com.ogc.standard.enums.EBoolean;
import com.ogc.standard.enums.EChannelType;
import com.ogc.standard.enums.EChargeStatus;
import com.ogc.standard.enums.ECurrency;
import com.ogc.standard.enums.EJourBizTypePlat;
import com.ogc.standard.enums.EJourBizTypeUser;
import com.ogc.standard.enums.EPayType;
import com.ogc.standard.enums.ESystemAccount;
import com.ogc.standard.enums.EUser;
import com.ogc.standard.exception.BizException;
import com.ogc.standard.exception.EBizErrorCode;

@Service
public class ChargeAOImpl implements IChargeAO {
    @Autowired
    private IAccountBO accountBO;

    @Autowired
    private IChargeBO chargeBO;

    @Autowired
    private IUserBO userBO;

    @Autowired
    private ISYSUserBO sysUserBO;

    @Autowired
    private IWeChatAO weChatAO;

    @Override
    public String applyOrder(String accountNumber, BigDecimal amount,
            String payCardInfo, String payCardNo, String applyUser,
            String applyNote, String collectionAccountNumber) {
        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new BizException("xn000000", "充值金额需大于零");
        }
        Account account = accountBO.getAccount(accountNumber);
        // 生成充值订单
        String code = chargeBO.applyOrderOffline(account,
            EJourBizTypeUser.AJ_CZ.getCode(), amount, payCardInfo, payCardNo,
            applyUser, applyNote, collectionAccountNumber);
        return code;
    }

    @Override
    @Transactional
    public void payOrder(String code, String payUser, String payResult,
            String payNote) {
        Charge data = chargeBO.getCharge(code);
        if (!EChargeStatus.toPay.getCode().equals(data.getStatus())) {
            throw new BizException("xn000000", "申请记录状态不是待支付状态，无法支付");
        }
        if (EBoolean.YES.getCode().equals(payResult)) {
            payOrderYES(data, payUser, payNote);
        } else {
            payOrderNO(data, payUser, payNote);
        }
    }

    private void payOrderNO(Charge data, String payUser, String payNote) {
        chargeBO.payOrder(data, false, payUser, payNote);
    }

    private void payOrderYES(Charge data, String payUser, String payNote) {
        chargeBO.payOrder(data, true, payUser, payNote);
        // 账户加钱
        accountBO.changeAmount(data.getAccountNumber(), EChannelType.Offline,
            null, null, data.getCode(), EJourBizTypeUser.AJ_CZ.getCode(),
            "线下充值", data.getAmount());
        Account account = accountBO.getAccount(data.getAccountNumber());
        if (ECurrency.CNY.getCode().equals(account.getCurrency())) {
            // 线下托管账户加钱
            accountBO.changeAmount(ESystemAccount.SYS_ACOUNT_OFFLINE.getCode(),
                EChannelType.Offline, null, null, data.getCode(),
                EJourBizTypePlat.AJ_CZ.getCode(), "线下充值", data.getAmount());
        }
    }

    @Override
    public Object applyOrderOnline(String userId, String payType,
            BigDecimal transAmount) {
        Object result = null;

        User user = userBO.getNormalUser(userId);
        if (EPayType.WEIXIN_H5.getCode().equals(payType)) {
            result = weChatAO.getPrepayIdH5(user.getUserId(),
                user.getH5OpenId(), user.getUserId(),
                EJourBizTypeUser.AJ_CZ.getCode(),
                EJourBizTypeUser.AJ_CZ.getCode(),
                EJourBizTypeUser.AJ_CZ.getCode(),
                EJourBizTypeUser.AJ_CZ.getValue(), transAmount);
        } else {
            throw new BizException(EBizErrorCode.DEFAULT.getCode(), "暂不支持支付方式");
        }
        return result;
    }

    @Override
    public Paginable<Charge> queryChargePage(int start, int limit,
            Charge condition) {
        Paginable<Charge> page = chargeBO.getPaginable(start, limit, condition);
        if (CollectionUtils.isNotEmpty(page.getList())) {
            List<Charge> list = page.getList();
            for (Charge charge : list) {
                init(charge);
            }
        }
        return page;
    }

    @Override
    public List<Charge> queryChargeList(Charge condition) {
        List<Charge> list = chargeBO.queryChargeList(condition);
        if (CollectionUtils.isNotEmpty(list)) {
            for (Charge charge : list) {
                init(charge);
            }
        }
        return list;
    }

    @Override
    public Charge getCharge(String code) {
        Charge charge = chargeBO.getCharge(code);
        init(charge);
        return charge;
    }

    private void init(Charge charge) {
        // 户名
        String realName = null;

        // 审核人
        String payUserName = null;

        // 账户
        Account account = accountBO.getAccount(charge.getAccountNumber());

        if (EAccountType.Customer.getCode().equals(charge.getType())) {

            // C端用户
            User user = userBO.getUser(account.getUserId());

            realName = user.getMobile();
            if (StringUtils.isNotBlank(user.getNickname())) {
                realName = user.getNickname().concat("-").concat(realName);
            }

        } else {

            // 系统用户
            realName = EUser.ADMIN.getValue();
        }

        SYSUser payUser = sysUserBO.getSYSUserUnCheck(charge.getPayUser());
        if (null != payUser) {
            payUserName = payUser.getLoginName();
            if (StringUtils.isNotBlank(payUser.getMobile())) {
                payUserName = payUserName.concat("-").concat(
                    payUser.getMobile());
            }
        }

        charge.setRealName(realName);

        charge.setApplyUserName(realName);

        charge.setPayUserName(payUserName);
    }

}
