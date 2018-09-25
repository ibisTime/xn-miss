package com.ogc.standard.ao.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ogc.standard.ao.IChargeAO;
import com.ogc.standard.bo.IAccountBO;
import com.ogc.standard.bo.IChargeBO;
import com.ogc.standard.bo.ICoinBO;
import com.ogc.standard.bo.ICollectBO;
import com.ogc.standard.bo.IEthTransactionBO;
import com.ogc.standard.bo.IJourBO;
import com.ogc.standard.bo.IUserBO;
import com.ogc.standard.bo.base.Paginable;
import com.ogc.standard.domain.Account;
import com.ogc.standard.domain.Charge;
import com.ogc.standard.domain.Coin;
import com.ogc.standard.domain.Collect;
import com.ogc.standard.domain.EthTransaction;
import com.ogc.standard.domain.Jour;
import com.ogc.standard.domain.User;
import com.ogc.standard.dto.res.XN802347Res;
import com.ogc.standard.enums.EBoolean;
import com.ogc.standard.enums.EChargeStatus;
import com.ogc.standard.enums.ECoinType;
import com.ogc.standard.enums.EErrorCode_main;
import com.ogc.standard.enums.EJourBizTypePlat;
import com.ogc.standard.enums.EJourBizTypeUser;
import com.ogc.standard.enums.EJourType;
import com.ogc.standard.enums.ESystemAccount;
import com.ogc.standard.exception.BizException;

@Service
public class ChargeAOImpl implements IChargeAO {
    @Autowired
    private IAccountBO accountBO;

    @Autowired
    private IUserBO userBO;

    @Autowired
    private IChargeBO chargeBO;

    @Autowired
    private IJourBO jourBO;

    @Autowired
    private IEthTransactionBO ethTransactionBO;

    @Autowired
    private ICollectBO collectBO;

    @Autowired
    private ICoinBO coinBO;

    @Override
    public String applyOrder(String accountNumber, BigDecimal amount,
            String payCardInfo, String payCardNo, String applyUser,
            String applyNote) {
        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new BizException(EErrorCode_main.charge_AMOUNT.getCode());
        }
        Account account = accountBO.getAccount(accountNumber);
        // 生成充值订单
        String code = chargeBO.applyOrderOffline(account,
            EJourBizTypeUser.AJ_CHARGE.getCode(), amount, payCardInfo,
            payCardNo, applyUser, applyNote);
        return code;
    }

    @Override
    @Transactional
    public void payOrder(String code, String payUser, String payResult,
            String payNote) {
        Charge data = chargeBO.getCharge(code);
        if (!EChargeStatus.toPay.getCode().equals(data.getStatus())) {
            throw new BizException(EErrorCode_main.charge_STATUS.getCode());
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

        Account userAccount = accountBO.getAccount(data.getAccountNumber());
        User user = userBO.getUser(userAccount.getUserId());

        Coin coin = coinBO.getCoin(data.getCurrency());
        String symbol = coin.getSymbol();

        Account platAccount = accountBO
            .getAccount(ESystemAccount.getPlatAccount(symbol));
        accountBO.transAmount(platAccount, userAccount, data.getAmount(),
            EJourBizTypePlat.AJ_SUBSIDY.getCode(),
            EJourBizTypeUser.AJ_CHARGE.getCode(),
            EJourBizTypePlat.AJ_SUBSIDY.getValue(),
            EJourBizTypeUser.AJ_CHARGE.getValue(), "用户充值");

    }

    @Override
    public Paginable<Charge> queryChargePage(int start, int limit,
            Charge condition) {
        Paginable<Charge> page = chargeBO.getPaginable(start, limit, condition);
        List<Charge> chargeList = page.getList();
        // for (Charge charge : chargeList) {
        // User user = userBO.getUser(charge.getApplyUser());
        // charge.setPayer(user);
        // }
        return page;
    }

    @Override
    public List<Charge> queryChargeList(Charge condition) {
        List<Charge> list = chargeBO.queryChargeList(condition);
        return list;
    }

    @Override
    public Charge getCharge(String code) {
        return chargeBO.getCharge(code);
    }

    @Override
    public XN802347Res getChargeCheckInfo(String code) {
        XN802347Res res = new XN802347Res();

        // 充值订单详情
        Charge charge = chargeBO.getCharge(code);

        // 充值对应流水记录
        Jour jour = new Jour();
        jour.setRefNo(charge.getCode());
        jour.setType(EJourType.BALANCE.getCode());
        List<Jour> jourList1 = jourBO.queryJourList(jour);

        if (ECoinType.ETH.getCode().equals(charge.getCurrency())) {

            List<EthTransaction> resultList1 = new ArrayList<>();
            // 充值对应广播记录
            EthTransaction chargeTx = ethTransactionBO
                .getEthTransaction(charge.getChannelOrder());

            resultList1.add(chargeTx);

            Collect collect = collectBO.getCollectByRefNo(charge.getCode());
            // 如果有归集
            if (collect != null) {
                // 归集对应流水
                jour.setRefNo(collect.getCode());
                List<Jour> jourList2 = jourBO.queryJourList(jour);
                jourList1.addAll(jourList2);
                // 归集对应广播记录
                EthTransaction collectTx = ethTransactionBO
                    .getEthTransaction(collect.getTxHash());
                resultList1.add(collectTx);
                res.setCollect(collect);
                res.setEthTransList(resultList1);
            }
            res.setEthTransList(resultList1);
        }

        res.setCharge(charge);
        res.setJourList(jourList1);

        return res;
    }
}
