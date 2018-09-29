package com.ogc.standard.bo.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ogc.standard.bo.IAccountBO;
import com.ogc.standard.bo.IChargeBO;
import com.ogc.standard.bo.IUserBO;
import com.ogc.standard.bo.base.PaginableBOImpl;
import com.ogc.standard.core.OrderNoGenerater;
import com.ogc.standard.dao.IChargeDAO;
import com.ogc.standard.domain.Account;
import com.ogc.standard.domain.Charge;
import com.ogc.standard.domain.EthWAddress;
import com.ogc.standard.domain.User;
import com.ogc.standard.enums.EChannelType;
import com.ogc.standard.enums.EChargeStatus;
import com.ogc.standard.enums.EErrorCode_main;
import com.ogc.standard.exception.BizException;

@Component
public class ChargeBOImpl extends PaginableBOImpl<Charge> implements IChargeBO {

    @Autowired
    private IChargeDAO chargeDAO;

    @Autowired
    private IAccountBO accountBO;

    @Autowired
    private IUserBO userBO;

    @Override
    public String applyOrderOffline(Account account, String bizType,
            BigDecimal amount, String payCardInfo, String payCardNo,
            String applyUser, String applyNote) {
        if (amount.compareTo(BigDecimal.ZERO) == 0) {
            throw new BizException(EErrorCode_main.charge_AMOUNT.getCode());
        }
        String code = OrderNoGenerater.generate("CZ");

        Charge data = new Charge();

        data.setCode(code);
        data.setAccountNumber(account.getAccountNumber());
        data.setAccountType(account.getType());
        data.setAmount(amount);
        data.setCurrency(account.getCurrency());

        data.setBizType(bizType);
        data.setBizNote(applyNote);
        data.setPayCardInfo(payCardInfo);
        data.setPayCardNo(payCardNo);
        data.setStatus(EChargeStatus.toPay.getCode());

        data.setApplyUser(applyUser);
        data.setApplyNote(applyNote);
        data.setApplyDatetime(new Date());
        data.setChannelType(EChannelType.Offline.getCode());

        chargeDAO.insert(data);
        return code;
    }

    // @Override
    public String applyOrderOnline(Account account, String payGroup,
            String refNo, String bizType, String bizNote,
            BigDecimal transAmount, EChannelType channelType, String applyUser,
            String fromAddress) {
        if (transAmount.compareTo(BigDecimal.ZERO) == 0) {
            throw new BizException(EErrorCode_main.charge_AMOUNT.getCode());
        }
        String code = OrderNoGenerater.generate("CZ");
        Charge data = new Charge();

        data.setCode(code);
        data.setAccountNumber(account.getAccountNumber());
        data.setAccountType(account.getType());
        data.setAmount(transAmount);
        data.setCurrency(account.getCurrency());

        data.setBizType(bizType);
        data.setBizNote(bizNote);
        data.setPayCardInfo(account.getCurrency());
        data.setPayCardNo(fromAddress);

        data.setStatus(EChargeStatus.Pay_YES.getCode());
        data.setApplyUser(applyUser);
        data.setApplyNote("线上充值");
        data.setApplyDatetime(new Date());
        data.setChannelType(channelType.getCode());
        chargeDAO.insert(data);
        return code;
    }

    @Override
    public String applyOrderOnline(Account account, String payCardInfo,
            String fromAddress, BigDecimal transAmount,
            EChannelType channelType, String channalOrder, String payNote,
            String hash) {
        if (transAmount.compareTo(BigDecimal.ZERO) == 0) {
            throw new BizException(EErrorCode_main.charge_AMOUNT.getCode());
        }
        String code = OrderNoGenerater.generate("CZ");
        Charge data = new Charge();

        data.setCode(code);
        data.setAccountNumber(account.getAccountNumber());
        data.setAccountType(account.getType());
        data.setAmount(transAmount);
        data.setCurrency(account.getCurrency());

        data.setPayCardInfo(payCardInfo);
        data.setStatus(EChargeStatus.Pay_YES.getCode());
        data.setPayUser(fromAddress);
        data.setPayNote(payNote);
        data.setPayDatetime(new Date());

        data.setChannelType(channelType.getCode());
        data.setChannelOrder(hash);
        chargeDAO.insert(data);
        return code;
    }

    @Override
    public void payOrder(Charge data, boolean booleanFlag, String payUser,
            String payNote) {
        if (booleanFlag) {
            data.setStatus(EChargeStatus.Pay_YES.getCode());
        } else {
            data.setStatus(EChargeStatus.Pay_NO.getCode());
        }
        data.setPayUser(payUser);
        data.setPayNote(payNote);
        data.setPayDatetime(new Date());
        chargeDAO.payOrder(data);
    }

    @Override
    public void callBackChange(Charge dbCharge, boolean booleanFlag) {
        if (booleanFlag) {
            dbCharge.setStatus(EChargeStatus.Pay_YES.getCode());
        } else {
            dbCharge.setStatus(EChargeStatus.Pay_NO.getCode());
        }
        dbCharge.setPayUser(null);
        dbCharge.setPayNote("在线充值自动回调");
        dbCharge.setPayDatetime(new Date());
        chargeDAO.payOrder(dbCharge);

    }

    @Override
    public List<Charge> queryChargeList(Charge condition) {
        return chargeDAO.selectList(condition);
    }

    @Override
    public Charge getCharge(String code) {
        Charge charge = null;
        if (StringUtils.isNotBlank(code)) {
            Charge condition = new Charge();
            condition.setCode(code);
            charge = chargeDAO.select(condition);
            if (null == charge) {
                throw new BizException(
                    EErrorCode_main.charge_NOTEXIST.getCode());
            }

            Account account = accountBO.getAccount(charge.getAccountNumber());
            User user = userBO.getUser(account.getUserId());
            charge.setPayer(user);
        }
        return charge;
    }

    @Override
    public EthWAddress getAddressUseInfo(String fromAddress, String currency) {
        Charge condition = new Charge();
        condition.setPayCardNo(fromAddress);
        condition.setCurrency(currency);
        condition.setChannelType(EChannelType.Online.getCode());
        condition.setStatus(EChargeStatus.Pay_YES.getCode());
        return chargeDAO.selectAddressUseInfo(condition);
    }

    @Override
    public boolean isExistOfChannelOrder(String orderNo) {
        boolean result = false;
        Charge condition = new Charge();
        condition.setChannelOrder(orderNo);
        if (getTotalCount(condition) > 0) {
            result = true;
        }
        return result;
    }

    @Override
    public boolean isExistOfRefNo(String refNo) {
        boolean result = false;
        Charge condition = new Charge();
        condition.setChannelOrder(refNo);
        if (getTotalCount(condition) > 0) {
            result = true;
        }
        return result;
    }
}
