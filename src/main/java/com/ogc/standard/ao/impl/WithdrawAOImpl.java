package com.ogc.standard.ao.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.web3j.crypto.WalletUtils;
import org.web3j.protocol.core.methods.response.EthBlock;
import org.web3j.protocol.core.methods.response.Transaction;
import org.web3j.protocol.core.methods.response.TransactionReceipt;

import com.ogc.standard.ao.IWithdrawAO;
import com.ogc.standard.bo.IAccountBO;
import com.ogc.standard.bo.ICoinBO;
import com.ogc.standard.bo.IEthMAddressBO;
import com.ogc.standard.bo.IEthTransactionBO;
import com.ogc.standard.bo.IJourBO;
import com.ogc.standard.bo.ISYSConfigBO;
import com.ogc.standard.bo.ITokenEventBO;
import com.ogc.standard.bo.IUserBO;
import com.ogc.standard.bo.IWithdrawBO;
import com.ogc.standard.bo.base.Paginable;
import com.ogc.standard.common.SysConstants;
import com.ogc.standard.core.EthClient;
import com.ogc.standard.domain.Account;
import com.ogc.standard.domain.Coin;
import com.ogc.standard.domain.CtqEthTransaction;
import com.ogc.standard.domain.EthMAddress;
import com.ogc.standard.domain.EthTransaction;
import com.ogc.standard.domain.Jour;
import com.ogc.standard.domain.TokenEvent;
import com.ogc.standard.domain.Withdraw;
import com.ogc.standard.dto.res.XN802356Res;
import com.ogc.standard.enums.EBoolean;
import com.ogc.standard.enums.EChannelType;
import com.ogc.standard.enums.ECoinType;
import com.ogc.standard.enums.EErrorCode_main;
import com.ogc.standard.enums.EJourBizTypePlat;
import com.ogc.standard.enums.EJourBizTypeUser;
import com.ogc.standard.enums.EJourType;
import com.ogc.standard.enums.EMAddressStatus;
import com.ogc.standard.enums.EOriginialCoin;
import com.ogc.standard.enums.ESystemAccount;
import com.ogc.standard.enums.ETransactionReceiptStatus;
import com.ogc.standard.enums.EWithdrawStatus;
import com.ogc.standard.exception.BizException;
import com.ogc.standard.token.OrangeCoinToken.TransferEventResponse;
import com.ogc.standard.token.TokenClient;

@Service
public class WithdrawAOImpl implements IWithdrawAO {

    private static Logger logger = Logger.getLogger(WithdrawAOImpl.class);

    @Autowired
    private IAccountBO accountBO;

    @Autowired
    private ISYSConfigBO sysConfigBO;

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
    private ITokenEventBO tokenEventBO;

    @Override
    @Transactional
    public String applyOrder(String accountNumber, BigDecimal amount,
            String payCardInfo, String payCardNo, String tradePwd,
            String applyUser, String applyNote) {

        Account dbAccount = accountBO.getAccount(accountNumber);
        Coin coin = coinBO.getCoin(dbAccount.getCurrency());

        BigDecimal fee = coin.getWithdrawFee();
        if (amount.compareTo(fee) == 0 || amount.compareTo(fee) == -1) {
            throw new BizException(EErrorCode_main.with_COUNTFEE.getCode());
        }

        // 取现地址格式校验以及是否被平台使用
        verifyPayCardNo(coin, payCardNo);

        // 校验资金密码
        // userBO.checkTradePwd(applyUser, tradePwd);

        // 账户可用余额是否充足
        if (dbAccount.getAmount().subtract(dbAccount.getFrozenAmount())
            .compareTo(amount) == -1) {
            throw new BizException(
                EErrorCode_main.account_PERSONALLEFT.getCode());
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
                throw new BizException(
                    EErrorCode_main.coin_ADDRESSRULE.getCode());
            }
        }
    }

    @Override
    @Transactional
    public void approveOrder(String code, String approveUser,
            String approveResult, String approveNote) {
        Withdraw data = withdrawBO.getWithdraw(code);
        if (null == data) {
            throw new BizException(EErrorCode_main.code_NOTEXIST.getCode());
        }
        if (!EWithdrawStatus.toApprove.getCode().equals(data.getStatus())) {
            throw new BizException(EErrorCode_main.with_APPROVE.getCode());
        }
        if (EBoolean.YES.getCode().equals(approveResult)) {
            approveOrderYES(data, approveUser, approveNote);
        } else {
            approveOrderNO(data, approveUser, approveNote);
        }
    }

    @Override
    @Transactional
    public void broadcast(String code, Long mAddressId, String approveUser) {
        // 取现记录验证
        Withdraw withdraw = withdrawBO.getWithdraw(code);
        if (withdraw == null) {
            throw new BizException(EErrorCode_main.code_NOTEXIST.getCode());
        }
        if (!EWithdrawStatus.Approved_YES.getCode()
            .equals(withdraw.getStatus())) {
            throw new BizException(EErrorCode_main.with_BROADCAST.getCode());
        }

        Account account = accountBO.getAccount(withdraw.getAccountNumber());
        Coin coin = coinBO.getCoin(account.getCurrency());

        if (ECoinType.ETH.getCode().equals(coin.getType())) {
            if (mAddressId == null) {
                throw new BizException(EErrorCode_main.with_NOTEMPTY.getCode());
            }

            doEthBroadcast(withdraw, mAddressId, approveUser);
        } else if (ECoinType.X.getCode().equals(coin.getType())) {
            if (mAddressId == null) {
                throw new BizException(EErrorCode_main.with_NOTEMPTY.getCode());
            }
            doTokenBroadcast(coin, withdraw, mAddressId, approveUser);
        }

    }

    private void doTokenBroadcast(Coin coin, Withdraw withdraw,
            Long mAddressId, String approveUser) {
        // 获取今日散取地址
        EthMAddress mEthAddress = ethMAddressBO.getEthMAddress(mAddressId);

        if (null == mEthAddress) {
            throw new BizException(
                EErrorCode_main.eth_INVALIBLEADDRESS.getCode());
        }
        if (EMAddressStatus.IN_USE.getCode().equals(mEthAddress.getStatus())) {
            throw new BizException(EErrorCode_main.eth_INWITHDRAW.getCode());
        }
        if (EMAddressStatus.INVALID.getCode().equals(mEthAddress.getStatus())) {
            throw new BizException(EErrorCode_main.eth_ADDRESSABUNDON.getCode());
        }

        String address = mEthAddress.getAddress();
        EthMAddress secret = ethMAddressBO.getEthMAddressSecret(mEthAddress
            .getId());

        // 实际到账金额=取现金额-取现手续费
        BigDecimal realAmount = withdraw.getAmount().subtract(
            sysConfigBO.getBigDecimalValue(SysConstants.WITHDRAW_FEE));

        // 查询散取地址token余额
        BigDecimal tokenBalance = TokenClient.getBalance(address,
            coin.getContractAddress());
        logger.info("地址" + address + coin.getSymbol() + "余额："
                + tokenBalance.toString());
        if (tokenBalance.compareTo(realAmount) < 0) {
            throw new BizException(
                EErrorCode_main.account_PERSONALLEFT.getCode());
        }

        // 预估矿工费用
        BigDecimal gasPrice = ethTransactionBO.getGasPrice();
        BigDecimal gasUse = new BigDecimal(210000);
        BigDecimal txFee = gasPrice.multiply(gasUse);

        // 查询散取地址余额
        BigDecimal balance = EthClient.getBalance(address);
        logger.info("地址" + address + "余额：" + balance.toString());
        if (balance.compareTo(txFee) < 0) {
            throw new BizException(
                EErrorCode_main.account_PERSONALLEFT.getCode());
        }

        // 广播
        if (!WalletUtils.isValidAddress(withdraw.getPayCardNo())) {
            throw new BizException(
                EErrorCode_main.with_INVILEDADDRESS.getCode(),
                withdraw.getPayCardInfo());
        }

        String txHash = TokenClient.transfer(secret.getAddress(),
            secret.getKeystoreName(), secret.getKeystorePwd(),
            secret.getKeystoreContent(), withdraw.getPayCardNo(), realAmount,
            coin.getContractAddress());
        if (StringUtils.isBlank(txHash)) {
            throw new BizException(EErrorCode_main.with_FAILED.getCode());
        }
        logger.info("广播成功：交易hash=" + txHash);
        withdrawBO.broadcastOrder(withdraw, txHash, approveUser);

        // 修改取现地址状态为广播中
        ethMAddressBO.refreshStatus(mEthAddress,
            EMAddressStatus.IN_USE.getCode());
    }

    private void doEthBroadcast(Withdraw withdraw, Long mAddressId,
            String approveUser) {
        // 获取今日散取地址
        EthMAddress mEthAddress = ethMAddressBO.getEthMAddress(mAddressId);
        if (null == mEthAddress) {
            throw new BizException(
                EErrorCode_main.eth_INVALIBLEADDRESS.getCode());
        }
        if (EMAddressStatus.IN_USE.getCode().equals(mEthAddress.getStatus())) {
            throw new BizException(EErrorCode_main.eth_INWITHDRAW.getCode());
        }
        if (EMAddressStatus.INVALID.getCode().equals(mEthAddress.getStatus())) {
            throw new BizException(EErrorCode_main.eth_ADDRESSABUNDON.getCode());
        }

        String address = mEthAddress.getAddress();
        EthMAddress secret = ethMAddressBO.getEthMAddressSecret(mEthAddress
            .getId());

        // 实际到账金额=取现金额-取现手续费
        BigDecimal realAmount = withdraw.getAmount().subtract(
            sysConfigBO.getBigDecimalValue(SysConstants.WITHDRAW_FEE));

        // 预估矿工费用
        BigDecimal gasPrice = ethTransactionBO.getGasPrice();
        BigDecimal gasUse = new BigDecimal(21000);
        BigDecimal txFee = gasPrice.multiply(gasUse);

        // 查询散取地址余额
        BigDecimal balance = EthClient.getBalance(address);
        logger.info("地址" + address + "余额：" + balance.toString());
        if (balance.compareTo(realAmount.add(txFee)) < 0) {
            throw new BizException(
                EErrorCode_main.account_PERSONALLEFT.getCode());
        }
        // 广播
        if (!WalletUtils.isValidAddress(withdraw.getPayCardNo())) {
            throw new BizException(
                EErrorCode_main.with_INVILEDADDRESS.getCode(),
                withdraw.getPayCardInfo());
        }

        String txHash = ethTransactionBO.broadcast(address,
            secret.getKeystoreName(), secret.getKeystoreContent(),
            secret.getKeystorePwd(), withdraw.getPayCardNo(), realAmount);
        if (StringUtils.isBlank(txHash)) {
            throw new BizException(EErrorCode_main.with_SIGNED.getCode());
        }
        logger.info("广播成功：交易hash=" + txHash);
        withdrawBO.broadcastOrder(withdraw, txHash, approveUser);

        // 修改取现地址状态为广播中
        ethMAddressBO.refreshStatus(mEthAddress,
            EMAddressStatus.IN_USE.getCode());

    }

    @Override
    @Transactional
    public void payOrder(String code, String payUser, String payResult,
            String payNote, String channelOrder) {
        Withdraw data = withdrawBO.getWithdraw(code);
        if (data == null) {
            throw new BizException(EErrorCode_main.code_NOTEXIST.getCode());
        }
        if (!EWithdrawStatus.Approved_YES.getCode().equals(data.getStatus())) {
            throw new BizException(EErrorCode_main.with_STATUS.getCode());
        }
        if (EBoolean.YES.getCode().equals(payResult)) {
            payOrderYES(data, payUser, payNote, channelOrder);
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
            throw new BizException(EErrorCode_main.code_NOTEXIST.getCode());
        }
        if (!EWithdrawStatus.Approved_YES.getCode().equals(data.getStatus())) {
            throw new BizException(EErrorCode_main.with_STATUS.getCode());
        }
        withdrawBO.returnOrder(code, payUser, payNote);
        Account dbAccount = accountBO.getAccount(data.getAccountNumber());
        // 释放冻结流水
        accountBO.unfrozenAmount(dbAccount, data.getAmount(),
            EJourBizTypeUser.AJ_WITHDRAW_UNFROZEN.getCode(), "取现失败退回",
            data.getCode());
    }

    /**
     * 定时扫描eth和token取现订单记录
     *  
     * @create: 2018年9月17日 上午10:03:15 13912
     * @history:
     */
    public void doETHAndTokenWithDrawGetTx() {
        logger.info("****** 扫描eth和token的提现订单开始 ******");

        Withdraw condition = new Withdraw();
        condition.setStatus(EWithdrawStatus.Broadcast.getCode());
        // 哪一条链
        condition.setPayCardInfo(EOriginialCoin.ETH.getCode());

        List<Withdraw> withdrawList = withdrawBO.queryWithdrawList(condition);
        if (CollectionUtils.isEmpty(withdrawList)) {
            logger.info("****** 扫描ERC20的正在矿工费补给的归集订单结束 ******");
            return;
        }
        for (Withdraw withdraw : withdrawList) {
            String transactionHash = withdraw.getChannelOrder();
            TransactionReceipt tx = EthClient.getTransactionReceipt(
                transactionHash).get();
            if (!ETransactionReceiptStatus.SUCCESS.equals(tx.getStatus())) {
                // 获取区块信息
                EthBlock.Block block = EthClient.getEthBlockByHash(tx
                    .getBlockHash());
                // 交易信息
                Transaction transaction = EthClient
                    .getEthTransactionByHash(withdraw.getChannelOrder());
                // 转成 ctqEthTransaction
                CtqEthTransaction ctqEthTransaction = ethTransactionBO
                    .convertTx(transaction, tx.getGasUsed(),
                        block.getTimestamp());
                if (EOriginialCoin.ETH.getCode().equals(withdraw.getCurrency())) {// eth交易
                    ethWithdrawNotice(ctqEthTransaction, withdraw);
                } else {// ecr20交易
                    List<TokenEvent> tokenEventList = new ArrayList<TokenEvent>();
                    // 向下获取event
                    List<TransferEventResponse> transferEventList = TokenClient
                        .loadTransferEvents(tx);
                    for (TransferEventResponse transferEventResponse : transferEventList) {
                        // token地址不是提现地址则跳过
                        if (!withdraw.getPayCardNo().equals(
                            transferEventResponse.to)) {
                            continue;
                        }
                        TokenEvent tokenEvent = tokenEventBO.convertTokenEvent(
                            transferEventResponse, ctqEthTransaction.getHash(),
                            withdraw.getCurrency());
                        tokenEventList.add(tokenEvent);
                    }
                    tokenEventListokenwithdrawNotice(ctqEthTransaction,
                        tokenEventList, withdraw);
                }
            }
        }
        logger.info("****** 扫描eth和token的提现订单开始******");
    }

    private void ethWithdrawNotice(CtqEthTransaction ctqEthTransaction,
            Withdraw withdraw) {
        // 根据交易hash查询取现订单
        if (withdraw == null || ctqEthTransaction == null) {
            return;
        }
        // 矿工费
        BigDecimal txFee = ctqEthTransaction.getGasFee();

        // 取现订单更新
        withdrawBO.payOrder(withdraw, EWithdrawStatus.Pay_YES,
            ctqEthTransaction.getFrom(), "广播成功", ctqEthTransaction.getHash(),
            txFee);

        // 落地交易记录
        ethTransactionBO.saveEthTransaction(ctqEthTransaction);

        // 修改散取地址状态为可使用
        EthMAddress from = ethMAddressBO
            .getEthMAddressByAddress(ctqEthTransaction.getFrom());
        ethMAddressBO.refreshStatus(from, EMAddressStatus.VALID.getCode());

        Account userAccount = accountBO.getAccount(withdraw.getAccountNumber());
        // 取现金额解冻
        userAccount = accountBO.unfrozenAmount(userAccount,
            withdraw.getAmount(),
            EJourBizTypeUser.AJ_WITHDRAW_UNFROZEN.getCode(),
            EJourBizTypeUser.AJ_WITHDRAW_UNFROZEN.getValue(),
            withdraw.getCode());
        // 取现金额扣减
        userAccount = accountBO.changeAmount(
            userAccount,
            withdraw
                .getAmount()
                .subtract(
                    sysConfigBO.getBigDecimalValue(SysConstants.WITHDRAW_FEE))
                .negate(),
            EChannelType.Online,
            ctqEthTransaction.getHash(),
            withdraw.getCode(),
            EJourBizTypeUser.AJ_WITHDRAW.getCode(),
            EJourBizTypeUser.AJ_WITHDRAW.getValue() + "-外部地址："
                    + withdraw.getPayCardNo());
        if (withdraw.getFee().compareTo(BigDecimal.ZERO) > 0) {
            // 取现手续费扣减
            userAccount = accountBO.changeAmount(userAccount, withdraw.getFee()
                .negate(), EChannelType.Online, ctqEthTransaction.getHash(),
                withdraw.getCode(), EJourBizTypeUser.AJ_WITHDRAW_FEE.getCode(),
                EJourBizTypeUser.AJ_WITHDRAW_FEE.getValue());
        }

        // 平台盈亏账户记入取现手续费
        Account sysAccount = accountBO.getAccount(ESystemAccount.SYS_ACOUNT_ETH
            .getCode());
        if (withdraw.getFee().compareTo(BigDecimal.ZERO) > 0) {
            sysAccount = accountBO.changeAmount(sysAccount, withdraw.getFee(),
                EChannelType.Online, ctqEthTransaction.getHash(),
                withdraw.getCode(), EJourBizTypePlat.AJ_WITHDRAW_FEE.getCode(),
                EJourBizTypePlat.AJ_WITHDRAW_FEE.getValue() + "-外部地址："
                        + withdraw.getPayCardNo());
        }
        // 平台盈亏账户记入取现矿工费
        sysAccount = accountBO.changeAmount(sysAccount, txFee.negate(),
            EChannelType.Online, ctqEthTransaction.getHash(),
            withdraw.getCode(),
            EJourBizTypePlat.AJ_WITHDRAW_MINING_FEE.getCode(),
            EJourBizTypePlat.AJ_WITHDRAW_MINING_FEE.getValue() + "-外部地址："
                    + withdraw.getPayCardNo());

    }

    @Override
    public void tokenEventListokenwithdrawNotice(
            CtqEthTransaction ctqEthTransaction,
            List<TokenEvent> tokenEventList, Withdraw withdraw) {
        // 根据交易hash查询取现订单
        if (ctqEthTransaction == null
                || CollectionUtils.isEmpty(tokenEventList) || withdraw == null) {
            return;
        }
        if (!EWithdrawStatus.Broadcast.getCode().equals(withdraw.getStatus())) {
            return;
        }

        // 计算矿工费
        BigDecimal txFee = ctqEthTransaction.getGasFee();

        // 如果有多比，都是同一个地址的，取一个就好
        TokenEvent tokenEvent = tokenEventList.get(0);

        // 取现订单更新
        withdrawBO.payOrder(withdraw, EWithdrawStatus.Pay_YES,
            tokenEvent.getTokenFrom(), "广播成功", ctqEthTransaction.getHash(),
            txFee);

        // 落地交易记录
        ethTransactionBO.saveEthTransaction(ctqEthTransaction);
        // 落地event
        tokenEventBO.insertEventsList(tokenEventList);

        // 更新地址余额
        String symbol = withdraw.getCurrency();
        Coin coin = coinBO.getCoin(symbol);
        EthMAddress from = ethMAddressBO.getEthMAddressByAddress(tokenEvent
            .getTokenFrom());

        // 修改散取地址状态为可使用
        ethMAddressBO.refreshStatus(from, EMAddressStatus.VALID.getCode());

        Account userAccount = accountBO.getAccount(withdraw.getAccountNumber());
        // 取现金额解冻
        userAccount = accountBO.unfrozenAmount(userAccount,
            withdraw.getAmount(),
            EJourBizTypeUser.AJ_WITHDRAW_UNFROZEN.getCode(),
            EJourBizTypeUser.AJ_WITHDRAW_UNFROZEN.getValue(),
            withdraw.getCode());
        // 取现金额扣减
        userAccount = accountBO.changeAmount(
            userAccount,
            withdraw
                .getAmount()
                .subtract(
                    sysConfigBO.getBigDecimalValue(SysConstants.WITHDRAW_FEE))
                .negate(),
            EChannelType.Online,
            ctqEthTransaction.getHash(),
            withdraw.getCode(),
            EJourBizTypeUser.AJ_WITHDRAW.getCode(),
            EJourBizTypeUser.AJ_WITHDRAW.getValue() + "-外部地址："
                    + withdraw.getPayCardNo());
        if (withdraw.getFee().compareTo(BigDecimal.ZERO) > 0) {
            // 取现手续费扣减
            userAccount = accountBO.changeAmount(userAccount, withdraw.getFee()
                .negate(), EChannelType.Online, ctqEthTransaction.getHash(),
                withdraw.getCode(), EJourBizTypeUser.AJ_WITHDRAW_FEE.getCode(),
                EJourBizTypeUser.AJ_WITHDRAW_FEE.getValue());
        }

        // 平台盈亏账户记入取现手续费
        Account sysAccount = accountBO.getAccount(ESystemAccount
            .getPlatAccount(symbol));
        if (withdraw.getFee().compareTo(BigDecimal.ZERO) > 0) {
            sysAccount = accountBO.changeAmount(sysAccount, withdraw.getFee(),
                EChannelType.Online, ctqEthTransaction.getHash(),
                withdraw.getCode(), EJourBizTypePlat.AJ_WITHDRAW_FEE.getCode(),
                EJourBizTypePlat.AJ_WITHDRAW_FEE.getValue() + "-外部地址："
                        + withdraw.getPayCardNo());
        }
    }
}
