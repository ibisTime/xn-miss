package com.ogc.standard.ao.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.web3j.crypto.WalletUtils;

import com.ogc.standard.ao.IWithdrawAO;
import com.ogc.standard.bo.IAccountBO;
import com.ogc.standard.bo.ICoinBO;
import com.ogc.standard.bo.IEthMAddressBO;
import com.ogc.standard.bo.IEthTransactionBO;
import com.ogc.standard.bo.IJourBO;
import com.ogc.standard.bo.ISYSConfigBO;
import com.ogc.standard.bo.IUserBO;
import com.ogc.standard.bo.IWithdrawBO;
import com.ogc.standard.bo.base.Paginable;
import com.ogc.standard.domain.Account;
import com.ogc.standard.domain.Coin;
import com.ogc.standard.domain.EthTransaction;
import com.ogc.standard.domain.Jour;
import com.ogc.standard.domain.Withdraw;
import com.ogc.standard.dto.res.XN802356Res;
import com.ogc.standard.enums.EBoolean;
import com.ogc.standard.enums.ECoinType;
import com.ogc.standard.enums.EJourBizTypeUser;
import com.ogc.standard.enums.EJourType;
import com.ogc.standard.enums.EWithdrawStatus;
import com.ogc.standard.exception.BizException;
import com.ogc.standard.exception.EBizErrorCode;

@Service
public class WithdrawAOImpl implements IWithdrawAO {

    private static Logger logger = Logger.getLogger(WithdrawAOImpl.class);

    @Autowired
    private IAccountBO accountBO;

    @Autowired
    private IUserBO userBO;

    @Autowired
    private IWithdrawBO withdrawBO;

    @Autowired
    private IJourBO jourBO;

    @Autowired
    private IEthMAddressBO ethMAddressBO;

    @Autowired
    private IEthTransactionBO ethTransactionBO;

    @Autowired
    private ICoinBO coinBO;

    @Autowired
    private ISYSConfigBO sysConfigBO;

    @Override
    @Transactional
    public String applyOrder(String accountNumber, BigDecimal amount,
            String payCardInfo, String payCardNo, String tradePwd,
            String applyUser, String applyNote) {

        Account dbAccount = accountBO.getAccount(accountNumber);
        Coin coin = coinBO.getCoin(dbAccount.getCurrency());

        BigDecimal fee = coin.getWithdrawFee();
        if (amount.compareTo(fee) == 0 || amount.compareTo(fee) == -1) {
            throw new BizException("xn000000", "提现金额需大于手续费");
        }

        // 取现地址格式校验以及是否被平台使用
        verifyPayCardNo(coin, payCardNo);

        // 校验资金密码
        userBO.checkTradePwd(applyUser, tradePwd);

        // 账户可用余额是否充足
        if (dbAccount.getAmount().subtract(dbAccount.getFrozenAmount())
            .compareTo(amount) == -1) {
            throw new BizException("xn000000", "可用余额不足");
        }

        // 判断本月是否次数已满，且现在只能有一笔取现未支付记录
        withdrawBO.doCheckTimes(dbAccount);

        // 生成取现订单
        String withdrawCode = withdrawBO.applyOrder(dbAccount, amount, fee,
            payCardInfo, payCardNo, applyUser, applyNote);
        // 冻结取现金额
        dbAccount = accountBO.frozenAmount(dbAccount, amount,
            EJourBizTypeUser.AJ_WITHDRAW_FROZEN.getCode(),
            EJourBizTypeUser.AJ_WITHDRAW_FROZEN.getValue(), withdrawCode);

        return withdrawCode;

    }

    private void verifyPayCardNo(Coin coin, String payCardNo) {
        if (ECoinType.ETH.getCode().equals(coin.getSymbol())
                || ECoinType.X.getCode().equals(coin.getSymbol())) {
            if (!WalletUtils.isValidAddress(payCardNo)) {
                throw new BizException(EBizErrorCode.DEFAULT.getCode(),
                    "提现地址不符合" + ECoinType.ETH.getCode() + "规则，请仔细核对");
            }
        }
    }

    @Override
    @Transactional
    public void approveOrder(String code, String approveUser,
            String approveResult, String approveNote) {
        Withdraw data = withdrawBO.getWithdraw(code);
        if (null == data) {
            throw new BizException(EBizErrorCode.DEFAULT.getCode(),
                "取现订单编号不存在");
        }
        if (!EWithdrawStatus.toApprove.getCode().equals(data.getStatus())) {
            throw new BizException(EBizErrorCode.DEFAULT.getCode(),
                "申请记录状态不是待审批状态，无法审批");
        }
        if (EBoolean.YES.getCode().equals(approveResult)) {
            approveOrderYES(data, approveUser, approveNote);
        } else {
            approveOrderNO(data, approveUser, approveNote);
        }
    }

    @Override
    @Transactional
    public void broadcast(String code, String mAddressCode,
            String approveUser) {
        // 取现记录验证
        Withdraw withdraw = withdrawBO.getWithdraw(code);
        if (withdraw == null) {
            throw new BizException(EBizErrorCode.DEFAULT.getCode(),
                "不存在编号为" + code + "的订单");
        }
        if (!EWithdrawStatus.Approved_YES.getCode()
            .equals(withdraw.getStatus())) {
            throw new BizException(EBizErrorCode.DEFAULT.getCode(),
                "当前取现记录不处于待广播状态");
        }

        Account account = accountBO.getAccount(withdraw.getAccountNumber());
        Coin coin = coinBO.getCoin(account.getCurrency());

        if (ECoinType.ETH.getCode().equals(account.getCurrency())) {
            if (StringUtils.isBlank(mAddressCode)) {
                throw new BizException(EBizErrorCode.DEFAULT.getCode(),
                    "散取地址不能为空");
            }
            doEthBroadcast(withdraw, mAddressCode, approveUser);
        } else if (ECoinType.X.getCode().equals(coin.getType())) {
            if (StringUtils.isBlank(mAddressCode)) {
                throw new BizException(EBizErrorCode.DEFAULT.getCode(),
                    "散取地址不能为空");
            }
            doTokenBroadcast(coin, withdraw, mAddressCode, approveUser);
        }

    }

    private void doTokenBroadcast(Coin coin, Withdraw withdraw,
            String mAddressCode, String approveUser) {
        // // 获取今日散取地址
        // EthMAddress mEthAddress = ethMAddressBO
        // .getEthMAddressByAddress(mAddressCode);
        // if (EMAddressStatus.IN_USE.getCode().equals(mEthAddress.getStatus()))
        // {
        // throw new BizException(EBizErrorCode.DEFAULT.getCode(),
        // "该散取地址正在广播使用，请稍后再试！");
        // }
        // if
        // (EMAddressStatus.INVALID.getCode().equals(mEthAddress.getStatus())) {
        // throw new BizException(EBizErrorCode.DEFAULT.getCode(),
        // "该散取地址已被弃用！");
        // }
        //
        // String address = mEthAddress.getAddress();
        // EthMAddress secret = ethMAddressBO.get(mEthAddress.getId());
        //
        // // 实际到账金额=取现金额-取现手续费
        // BigDecimal realAmount = withdraw.getAmount()
        // .subtract(withdraw.getFee());
        //
        // // 查询散取地址token余额
        // BigDecimal tokenBalance = to.getTokenBalance(address,
        // coin.getContractAddress());
        // logger.info("地址" + address + coin.getSymbol() + "余额："
        // + tokenBalance.toString());
        // if (tokenBalance.compareTo(realAmount) < 0) {
        // throw new BizException("xn625000",
        // "地址" + address + coin.getSymbol() + "余额不足以支付提现金额！");
        // }
        //
        // // 预估矿工费用
        // BigDecimal gasPrice = ethTransactionBO.getGasPrice();
        // BigDecimal gasUse = new BigDecimal(210000);
        // BigDecimal txFee = gasPrice.multiply(gasUse);
        //
        // // 查询散取地址余额
        // BigDecimal balance = ethAddressBO.getEthBalance(address);
        // logger.info("地址" + address + "余额：" + balance.toString());
        // if (balance.compareTo(txFee) < 0) {
        // throw new BizException("xn625000",
        // "散取地址" + address + "ETH余额不足以支付提现矿工费！");
        // }
        //
        // // 广播
        // if (!WalletUtils.isValidAddress(withdraw.getPayCardNo())) {
        // throw new BizException("xn625000",
        // "无效的取现地址：" + withdraw.getPayCardInfo());
        // }
        // String txHash = TokenClient.transfer(secret, withdraw.getPayCardNo(),
        // realAmount, coin.getContractAddress());
        // if (StringUtils.isBlank(txHash)) {
        // throw new BizException("xn625000", "提现广播失败");
        // }
        // logger.info("广播成功：交易hash=" + txHash);
        // withdrawBO.broadcastOrder(withdraw, txHash, approveUser);
        //
        // // 修改取现地址状态为广播中
        // tokenAddressBO.refreshStatus(mEthAddress,
        // EMAddressStatus.IN_USE.getCode());
    }

    private void doEthBroadcast(Withdraw withdraw, String mAddressCode,
            String approveUser) {
        // // // 获取今日散取地址
        // EthMAddress mEthAddress = ethMAddressBO.getEthMAddress(mAddressCode);
        // if (!EAddressType.M.getCode().endsWith(mEthAddress.getType())) {
        // throw new BizException(EBizErrorCode.DEFAULT.getCode(),
        // "无效的ETH地址，只有散取地址才能进行取现广播！");
        // }
        // if (EMAddressStatus.IN_USE.getCode().equals(mEthAddress.getStatus()))
        // {
        // throw new BizException(EBizErrorCode.DEFAULT.getCode(),
        // "该散取地址正在广播使用，请稍后再试！");
        // }
        // if
        // (EMAddressStatus.INVALID.getCode().equals(mEthAddress.getStatus())) {
        // throw new BizException(EBizErrorCode.DEFAULT.getCode(),
        // "该散取地址已被弃用！");
        // }
        //
        // String address = mEthAddress.getAddress();
        // EthAddress secret = ethAddressBO
        // .getEthAddressSecret(mEthAddress.getCode());
        //
        // // 实际到账金额=取现金额-取现手续费
        // BigDecimal realAmount = withdraw.getAmount()
        // .subtract(withdraw.getFee());
        //
        // // 预估矿工费用
        // BigDecimal gasPrice = ethTransactionBO.getGasPrice();
        // BigDecimal gasUse = new BigDecimal(21000);
        // BigDecimal txFee = gasPrice.multiply(gasUse);
        //
        // // 查询散取地址余额
        // BigDecimal balance = ethAddressBO.getEthBalance(address);
        // logger.info("地址" + address + "余额：" + balance.toString());
        // if (balance.compareTo(realAmount.add(txFee)) < 0) {
        // throw new BizException("xn625000",
        // "散取地址" + address + "余额不足以支付提现金额和矿工费！");
        // }
        // // 广播
        // if (!WalletUtils.isValidAddress(withdraw.getPayCardNo())) {
        // throw new BizException("xn625000",
        // "无效的取现地址：" + withdraw.getPayCardInfo());
        // }
        // String txHash = ethTransactionBO.broadcast(address, secret,
        // withdraw.getPayCardNo(), realAmount);
        // if (StringUtils.isBlank(txHash)) {
        // throw new BizException("xn625000", "交易签名失败，请仔细检查散取地址是否符合提现要求");
        // }
        // logger.info("广播成功：交易hash=" + txHash);
        // withdrawBO.broadcastOrder(withdraw, txHash, approveUser);
        //
        // // 修改取现地址状态为广播中
        // ethAddressBO.refreshStatus(mEthAddress,
        // EMAddressStatus.IN_USE.getCode());

    }

    @Override
    @Transactional
    public void payOrder(String code, String payUser, String payResult,
            String payNote, String channelOrder) {
        Withdraw data = withdrawBO.getWithdraw(code);
        if (data == null) {
            throw new BizException(EBizErrorCode.DEFAULT.getCode(),
                "不存在编号为" + code + "的订单");
        }
        if (!EWithdrawStatus.Approved_YES.getCode().equals(data.getStatus())) {
            throw new BizException("xn000000", "申请记录状态不是待支付状态，无法支付");
        }
        if (EBoolean.YES.getCode().equals(payResult)) {
            payOrderYES(data, payUser, payNote, channelOrder);
        } else {
            payOrderNO(data, payUser, payNote, channelOrder);
        }
    }

    private void approveOrderYES(Withdraw data, String approveUser,
            String approveNote) {
        withdrawBO.approveOrder(data, EWithdrawStatus.Approved_YES, approveUser,
            approveNote);
    }

    private void approveOrderNO(Withdraw data, String approveUser,
            String approveNote) {
        withdrawBO.approveOrder(data, EWithdrawStatus.Approved_NO, approveUser,
            approveNote);
        Account dbAccount = accountBO.getAccount(data.getAccountNumber());
        // 释放冻结流水
        accountBO.unfrozenAmount(dbAccount, data.getAmount(),
            EJourBizTypeUser.AJ_WITHDRAW_UNFROZEN.getCode(), "取现失败退回",
            data.getCode());

    }

    private void payOrderNO(Withdraw data, String payUser, String payNote,
            String payCode) {
        withdrawBO.payOrder(data, EWithdrawStatus.Pay_NO, payUser, payNote,
            payCode, BigDecimal.ZERO);
        Account dbAccount = accountBO.getAccount(data.getAccountNumber());
        // 释放冻结流水
        accountBO.unfrozenAmount(dbAccount, data.getAmount(),
            EJourBizTypeUser.AJ_WITHDRAW.getCode(), "取现失败退回", data.getCode());
    }

    private void payOrderYES(Withdraw data, String payUser, String payNote,
            String payCode) {
        // withdrawBO.payOrder(data, EWithdrawStatus.Pay_YES, payUser, payNote,
        // payCode, payCode, BigDecimal.ZERO);
        // Account dbAccount = accountBO.getAccount(data.getAccountNumber());
        // // 先解冻，然后扣减余额
        // accountBO.unfrozenAmount(dbAccount, data.getAmount(),
        // EJourBizTypeUser.AJ_WITHDRAW.getCode(),
        // EJourBizTypeUser.AJ_WITHDRAW.getValue(), data.getCode());
        // accountBO.changeAmount(dbAccount.getAccountNumber(),
        // EChannelType.Offline, null, null, data.getCode(),
        // EJourBizTypeUser.AJ_WITHDRAW.getCode(),
        // EJourBizTypeUser.AJ_WITHDRAW.getValue(), data.getAmount());
        // Account account = accountBO.getAccount(data.getAccountNumber());
        // if (ECurrency.CNY.getCode().equals(account.getCurrency())) {
        // // 冷钱包减钱
        // accountBO.changeAmount(
        // ESystemAccount.SYS_ACOUNT_ETH_COLD.getCode(),
        // EChannelType.Offline, null, null, data.getCode(),
        // EJourBizTypeCold.AJ_PAY.getCode(), "ETH取现", data.getAmount()
        // .negate());
        // }
    }

    @Override
    public Paginable<Withdraw> queryWithdrawPage(int start, int limit,
            Withdraw condition) {
        Paginable<Withdraw> page = withdrawBO.getPaginable(start, limit,
            condition);
        return page;
    }

    @Override
    public List<Withdraw> queryWithdrawList(Withdraw condition) {
        List<Withdraw> list = withdrawBO.queryWithdrawList(condition);
        return list;
    }

    @Override
    public Withdraw getWithdraw(String code) {
        Withdraw withdraw = withdrawBO.getWithdraw(code);
        return withdraw;
    }

    /**
    * 取现申请检查，验证参数，返回手续费
    * @param accountType
    * @param amount
    * @param systemCode
    * @param companyCode
    * @return
    * @create: 2017年5月17日 上午7:53:01 xieyj
    * @history:
    */
    // private BigDecimal doGetFee(String accountType, BigDecimal amount,
    // String systemCode, String companyCode) {
    // Map<String, String> argsMap = sysConfigBO.getConfigsMap("");
    // String qxfl = null;
    // if (EAccountType.Customer.getCode().equals(accountType)) {
    // qxfl = SysConstants.CUSERQXFL;
    // } else {// 暂定其他账户类型不收手续费
    // return BigDecimal.ZERO;
    // }
    // // 取现单笔最大金额
    // String qxDbzdjeValue = argsMap.get(SysConstants.QXDBZDJE);
    // if (StringUtils.isNotBlank(qxDbzdjeValue)) {
    // BigDecimal qxDbzdje = BigDecimal
    // .valueOf(Double.valueOf(qxDbzdjeValue));
    // if (amount.compareTo(qxDbzdje) == 1) {
    // throw new BizException("xn000000",
    // "取现单笔最大金额不能超过" + qxDbzdjeValue + "元。");
    // }
    // }
    // String feeRateValue = argsMap.get(qxfl);
    // Double feeRate = 0D;
    // if (StringUtils.isNotBlank(feeRateValue)) {
    // feeRate = Double.valueOf(feeRateValue);
    // }
    // return AmountUtil.mul(amount, feeRate);
    // }

    @Override
    public XN802356Res getWithdrawCheckInfo(String code) {

        XN802356Res res = new XN802356Res();

        // 取现订单详情
        Withdraw withdraw = withdrawBO.getWithdraw(code);
        Account account = accountBO.getAccount(withdraw.getAccountNumber());

        // 取现对应流水记录
        Jour jour = new Jour();
        jour.setRefNo(withdraw.getCode());
        jour.setType(EJourType.BALANCE.getCode());
        List<Jour> jourList = jourBO.queryJourList(jour);

        if (ECoinType.ETH.getCode().equals(account.getCurrency())) {
            EthTransaction ethTransaction = ethTransactionBO
                .getEthTransaction(withdraw.getChannelOrder());
            // ETH取现对应广播记录
            List<EthTransaction> resultList = new ArrayList<>();
            resultList.add(ethTransaction);
            res.setEthTransList(resultList);

        }
        res.setWithdraw(withdraw);
        res.setJourList(jourList);

        return res;
    }

    @Override
    public BigDecimal getTotalWithdraw(String currency) {
        return withdrawBO.getTotalWithdraw(currency);
    }

    @Override
    public void returnOrder(String code, String payUser, String payNote) {
        Withdraw data = withdrawBO.getWithdraw(code);
        if (data == null) {
            throw new BizException(EBizErrorCode.DEFAULT.getCode(),
                "不存在编号为" + code + "的订单");
        }
        if (!EWithdrawStatus.Approved_YES.getCode().equals(data.getStatus())) {
            throw new BizException(EBizErrorCode.DEFAULT.getCode(),
                "申请记录状态不是待审批通过状态，无法审批");
        }
        withdrawBO.returnOrder(code, payUser, payNote);
        Account dbAccount = accountBO.getAccount(data.getAccountNumber());
        // 释放冻结流水
        accountBO.unfrozenAmount(dbAccount, data.getAmount(),
            EJourBizTypeUser.AJ_WITHDRAW_UNFROZEN.getCode(), "取现失败退回",
            data.getCode());
    }

}
