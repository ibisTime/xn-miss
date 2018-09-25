package com.ogc.standard.ao.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.web3j.protocol.core.methods.response.EthBlock;
import org.web3j.protocol.core.methods.response.Transaction;
import org.web3j.protocol.core.methods.response.TransactionReceipt;

import com.ogc.standard.ao.ICollectAO;
import com.ogc.standard.bo.IAccountBO;
import com.ogc.standard.bo.ICoinBO;
import com.ogc.standard.bo.ICollectBO;
import com.ogc.standard.bo.IEthSAddressBO;
import com.ogc.standard.bo.IEthTransactionBO;
import com.ogc.standard.bo.IEthWAddressBO;
import com.ogc.standard.bo.IEthXAddressBO;
import com.ogc.standard.bo.ITokenEventBO;
import com.ogc.standard.bo.base.Paginable;
import com.ogc.standard.common.AmountUtil;
import com.ogc.standard.common.DateUtil;
import com.ogc.standard.core.EthClient;
import com.ogc.standard.domain.Account;
import com.ogc.standard.domain.Coin;
import com.ogc.standard.domain.Collect;
import com.ogc.standard.domain.CtqEthTransaction;
import com.ogc.standard.domain.EthSAddress;
import com.ogc.standard.domain.EthWAddress;
import com.ogc.standard.domain.EthXAddress;
import com.ogc.standard.domain.TokenEvent;
import com.ogc.standard.enums.EChannelType;
import com.ogc.standard.enums.ECoinType;
import com.ogc.standard.enums.ECollectStatus;
import com.ogc.standard.enums.EErrorCode_main;
import com.ogc.standard.enums.EJourBizTypeCold;
import com.ogc.standard.enums.EJourBizTypePlat;
import com.ogc.standard.enums.EOriginialCoin;
import com.ogc.standard.enums.ESAddressStatus;
import com.ogc.standard.enums.ESystemAccount;
import com.ogc.standard.enums.ETransactionReceiptStatus;
import com.ogc.standard.exception.BizException;
import com.ogc.standard.token.OrangeCoinToken.TransferEventResponse;
import com.ogc.standard.token.TokenClient;

@Service
public class CollectAOImpl implements ICollectAO {

    private static final Logger logger = LoggerFactory
        .getLogger(CollectAOImpl.class);

    @Autowired
    private ICollectBO collectBO;

    @Autowired
    private ICoinBO coinBO;

    @Autowired
    private IEthXAddressBO ethXAddressBO;

    @Autowired
    private IEthWAddressBO ethWAddressBO;

    @Autowired
    private IEthSAddressBO ethSAddressBO;

    @Autowired
    private IAccountBO accountBO;

    @Autowired
    private ITokenEventBO tokenEventBO;

    @Autowired
    private IEthTransactionBO ethTransactionBO;

    private BigDecimal minBalance = new BigDecimal("5000000000000000");

    @Override
    public Paginable<Collect> queryCollectPage(int start, int limit,
            Collect condition) {
        return collectBO.getPaginable(start, limit, condition);
    }

    //
    @Override
    public Collect getCollect(String code) {
        return collectBO.getCollect(code);
    }

    //
    // @Override
    // public BigDecimal getTotalCollect(String currency) {
    // return collectBO.getTotalCollect(currency);
    // }
    //
    /**
    * @see
    com.ICollectAO.coin.wallet.ao.ICollectAO#collect(java.math.BigDecimal,
    java.lang.String, java.lang.String)
    */
    @Override
    public void collect(BigDecimal balanceStart, String currency,
            String refNo) {
        if (EOriginialCoin.ETH.getCode().equals(currency)) {
            doCollectManualETH(balanceStart);
        }
    }

    //
    // public static void main(String[] args) {
    // List<String> test = new ArrayList<String>();
    // build(test);
    // System.out.println(test);
    // }
    //
    // private static void build(List<String> test) {
    // test.add("aa");
    // test.add("bb");
    // }

    private void doCollectManualETH(BigDecimal balanceStart) {
        int start = 0;
        int limit = 10;
        while (true) {
            // 取出符合条件的地址列表
            List<EthXAddress> ethXAddresseList = ethXAddressBO
                .queryNeedCollectAddressPage(balanceStart,
                    EOriginialCoin.ETH.getCode(), start, limit);
            if (CollectionUtils.isEmpty(ethXAddresseList)) {
                break;
            }
            // 开始归集逻辑
            for (EthXAddress ethXAddress : ethXAddresseList) {
                try {
                    BigDecimal balance = EthClient
                        .getBalance(ethXAddress.getAddress()); // 余额大于配置值时，进行归集
                    collectBO.doETHCollect(ethXAddress, null, balance);
                } catch (Exception e) {
                    logger.info("地址" + ethXAddress.getAddress() + "手动归集失败，原因："
                            + e.getMessage());
                }
            }
            start++;
        }

    }

    private void doCollectTokenOfEth(BigDecimal balanceStart, Coin coin,
            String refNo) {

        // 获取归集地址
        EthWAddress wEthAddress = ethWAddressBO.getWEthAddressToday();

        int start = 0;
        int limit = 50;
        while (true) {

            // 取出符合条件的地址列表
            List<EthXAddress> tokenAddresseList = ethXAddressBO
                .queryNeedCollectAddressPage(balanceStart, coin.getSymbol(),
                    start, limit);

            if (CollectionUtils.isEmpty(tokenAddresseList)) {
                break;
            }

            collectBO.saveToCollectList(coin, tokenAddresseList,
                wEthAddress.getAddress(), refNo);

            start++;
        }

    }

    @Override
    public void ethTokenCollect(String balanceStart, String currency,
            String refNo) {
        Coin coin = coinBO.getCoin(currency);
        if (null == coin) {
            throw new BizException(EErrorCode_main.coin_UNSUPPORT.getCode());
        }
        BigDecimal tokenBalanceStart = AmountUtil
            .toOriginal(new BigDecimal(balanceStart), coin.getUnit());
        doCollectTokenOfEth(tokenBalanceStart, coin, refNo);
    }

    @Override
    public void ethTokenCollectAuto(String address, String symbol,
            String refNo) {
        // 获取地址信息
        EthXAddress xEthAddress = ethXAddressBO
            .getEthXAddressByAddress(address);
        if (xEthAddress == null) {
            throw new BizException(EErrorCode_main.coll_UNABLE.getCode());
        }

        Coin coin = coinBO.getCoin(symbol);
        BigDecimal limit = coin.getCollectStart();

        BigDecimal balance = TokenClient.getBalance(address,
            coin.getContractAddress());
        // 余额大于配置值时，进行归集
        if (balance.compareTo(limit) < 0) {
            throw new BizException(EErrorCode_main.coll_CONDITION.getCode());
        }

        // 获取归集地址
        EthWAddress wEthAddress = ethWAddressBO.getWEthAddressToday();
        List<EthXAddress> tokenAddresseList = new ArrayList<>();
        tokenAddresseList.add(xEthAddress);

        // 开始归集
        collectBO.saveToCollectList(coin, tokenAddresseList,
            wEthAddress.getAddress(), refNo);

    }

    // 扫描ERC20币的待归集订单，进行归集逻辑，两种情况：
    // 1、归集地址以太坊余额大于等于0.005，直接发起归集广播，状态改为"归集广播中"
    // 2、归集地址以太坊余额小于0.005，从S地址发起转账广播，状态改成"获取矿工费广播中"，s地址改为"正在使用中"
    // 3、token余额小于0，或找不到可用的S地址，或S地址以太坊余额不足，直接"归集失败"
    public void doERC20Collect() {

        logger.info("****** 扫描ERC20的待归集订单开始 ******");

        Collect condition = new Collect();
        condition.setStatus(ECollectStatus.TO_COLLECT.getCode());
        condition.setCoinType(ECoinType.X.getCode());

        List<Collect> collectList = collectBO.queryCollectList(condition);
        if (CollectionUtils.isEmpty(collectList)) {
            logger.info("****** 扫描ERC20的待归集订单结束 ******");
            return;
        }
        for (Collect collect : collectList) {

            Coin coin = coinBO.getCoin(collect.getCurrency());

            BigDecimal tokenBalance = TokenClient.getBalance(
                collect.getFromAddress(), coin.getContractAddress());
            if (tokenBalance.compareTo(BigDecimal.ZERO) <= 0) {
                collectBO.collectFailed(collect, "归集地址token余额小于等于0");
                return;
            }

            BigDecimal ethBalance = EthClient
                .getBalance(collect.getFromAddress());
            if (ethBalance.compareTo(minBalance) < 0) {

                BigDecimal need = minBalance.subtract(ethBalance);

                EthSAddress sEthAddress = ethSAddressBO.getEnableSEthAddress();
                if (sEthAddress == null) {

                    // 检查有没有正在使用中的补给地址，有的话，就等
                    EthSAddress condition1 = new EthSAddress();
                    condition1.setStatus(ESAddressStatus.IN_USE.getCode());
                    if (ethSAddressBO.getTotalCount(condition1) <= 0) {
                        collectBO.collectFailed(collect, "未找到可用的归集矿工费补给地址");
                    }
                    return;

                }
                EthSAddress secretFrom = ethSAddressBO
                    .getEthSAddressSecret(sEthAddress.getId());
                BigDecimal sEthBalance = EthClient
                    .getBalance(sEthAddress.getAddress());
                BigDecimal gasUse = new BigDecimal(21000);
                BigDecimal gasPrice = EthClient.getGasPrice();
                BigDecimal txFee = gasPrice.multiply(gasUse);
                BigDecimal value = sEthBalance.subtract(txFee).subtract(need);
                if (value.compareTo(BigDecimal.ZERO) < 0) {
                    collectBO.collectFailed(collect, "归集矿工费补给地址余额不足");
                    return;
                }

                String txHash = EthClient.transfer(secretFrom.getAddress(),
                    secretFrom.getKeystoreName(), secretFrom.getKeystorePwd(),
                    secretFrom.getKeystoreContent(), collect.getFromAddress(),
                    need);

                ethSAddressBO.refreshStatus(sEthAddress,
                    ESAddressStatus.IN_USE.getCode());
                collectBO.collectFeeBroadcastSuccess(collect,
                    sEthAddress.getAddress(), need, txHash);

            } else {

                BigDecimal value = TokenClient.getBalance(
                    collect.getFromAddress(), coin.getContractAddress());
                EthXAddress fromAddress = ethXAddressBO
                    .getEthXAddressByAddress(collect.getFromAddress());

                EthXAddress secretFrom = ethXAddressBO
                    .getEthXAddressSecret(fromAddress.getId());

                String txHash = TokenClient.transfer(secretFrom.getAddress(),
                    secretFrom.getKeystoreName(), secretFrom.getKeystorePwd(),
                    secretFrom.getKeystoreContent(), collect.getToAddress(),
                    value, coin.getContractAddress());

                collectBO.collectBroadcastSuccess(collect, value, txHash);

            }

        }

        logger.info("****** 扫描ERC20的待归集订单结束 ******");

    }

    // 扫描状态为"获取矿工费广播中"的订单，检查交易状态
    // 1、交易成功，状态改为"等待归集"，S地址改为"可使用"
    // 2、交易未确认，状态不变
    // 3、找不到交易或者交易失败，状态改为"归集失败"，S地址改为"可使用"
    public void doERC20CheckGetFeeTx() {

        logger.info("****** 扫描ERC20获取矿工费广播中的归集订单开始 ******");

        Collect condition = new Collect();
        condition.setStatus(ECollectStatus.FEE_BROADCAST.getCode());
        condition.setCoinType(ECoinType.X.getCode());

        List<Collect> collectList = collectBO.queryCollectList(condition);
        if (CollectionUtils.isEmpty(collectList)) {
            logger.info("****** 扫描ERC20获取矿工费广播中的归集订单结束 ******");
            return;
        }
        for (Collect collect : collectList) {

            TransactionReceipt transactionReceipt = EthClient
                .getTransactionReceipt(collect.getPreTxHash()).get();
            if (!ETransactionReceiptStatus.SUCCESS.getCode()
                .equals(transactionReceipt.getStatus())) {
                Transaction transaction = EthClient
                    .getEthTransactionByHash(collect.getPreTxHash());
                BigDecimal gasUsed = new BigDecimal(
                    transactionReceipt.getGasUsed());
                BigDecimal gasPrice = new BigDecimal(transaction.getGasPrice());
                BigDecimal txfee = gasUsed.multiply(gasPrice);

                EthBlock.Block block = EthClient
                    .getEthBlockByHash(transaction.getBlockHash());

                Date confirmTime = DateUtil.TimeStamp2Date(
                    block.getTimestamp().toString(),
                    DateUtil.DATA_TIME_PATTERN_1);
                // 转成 ctqEthTransaction
                CtqEthTransaction ctqEthTransaction = ethTransactionBO
                    .convertTx(transaction, transactionReceipt.getGasUsed(),
                        block.getTimestamp());
                // 落地交易记录
                ethTransactionBO.saveEthTransaction(ctqEthTransaction);
                collectBO.collectFeeTxSuccess(collect, txfee, confirmTime);
                EthSAddress sEthAddress = ethSAddressBO
                    .getEthSAddressByAddress(collect.getPreFrom());
                ethSAddressBO.refreshStatus(sEthAddress,
                    ESAddressStatus.VALID.getCode());
            }

        }

        logger.info("****** 扫描ERC20获取矿工费广播中的归集订单结束 ******");

    }

    // 扫描ERC20状态为"归集广播中"的订单，检查交易状态
    // 1、交易成功，状态改为"归集完成"
    // 2、交易未确认，状态不变
    // 3、找不到交易或者交易失败，状态改为"归集失败"
    public void doERC20CheckCollectTx() {

        logger.info("****** 扫描ERC20归集广播中的订单开始 ******");

        Collect condition = new Collect();
        condition.setStatus(ECollectStatus.BROADCAST.getCode());
        condition.setCoinType(ECoinType.X.getCode());

        List<Collect> collectList = collectBO.queryCollectList(condition);
        if (CollectionUtils.isEmpty(collectList)) {
            logger.info("****** 扫描ERC20归集广播中的订单结束 ******");
            return;
        }
        for (Collect collect : collectList) {

            TransactionReceipt transactionReceipt = EthClient
                .getTransactionReceipt(collect.getTxHash()).get();
            if (!ETransactionReceiptStatus.SUCCESS.getCode()
                .equals(transactionReceipt.getStatus())) {
                List<TokenEvent> tokenEventList = new ArrayList<TokenEvent>();
                // 向下获取event
                List<TransferEventResponse> transferEventList = TokenClient
                    .loadTransferEvents(transactionReceipt);

                for (TransferEventResponse transferEventResponse : transferEventList) {
                    // token地址不是提现地址则跳过
                    if (!collect.getFromAddress()
                        .equals(transferEventResponse.from)) {
                        continue;
                    }
                    TokenEvent tokenEvent = tokenEventBO.convertTokenEvent(
                        transferEventResponse, collect.getTxHash(),
                        collect.getCurrency());
                    tokenEventList.add(tokenEvent);
                }
                // 交易信息
                Transaction transaction = EthClient
                    .getEthTransactionByHash(collect.getTxHash());
                EthBlock.Block block = EthClient
                    .getEthBlockByHash(transactionReceipt.getBlockHash());

                CtqEthTransaction ctqEthTransaction = ethTransactionBO
                    .convertTx(transaction, transactionReceipt.getGasUsed(),
                        block.getTimestamp());
                Date confirmTime = DateUtil.TimeStamp2Date(
                    block.getTimestamp().toString(),
                    DateUtil.DATA_TIME_PATTERN_1);
                collectBO.collectTxSuccess(collect,
                    ctqEthTransaction.getGasFee(), confirmTime);
                // 落地交易记录
                ethTransactionBO.saveEthTransaction(ctqEthTransaction);
                // 落地event
                tokenEventBO.insertEventsList(tokenEventList);
            }

        }

        logger.info("****** 扫描ERC20归集广播中的订单结束 ******");
    }

    // 扫描ETH状态为"归集广播中"的订单，检查交易状态
    // 1、交易成功，状态改为"归集完成"
    // 2、交易未确认，状态不变
    // 3、找不到交易或者交易失败，状态改为"归集失败"
    public void doEthCollectTx() {
        logger.info("****** 扫描eth正在归集的订单开始 ******");

        Collect condition = new Collect();
        condition.setStatus(ECollectStatus.BROADCAST.getCode());
        condition.setCoinType(ECoinType.ETH.getCode());
        List<Collect> collectList = collectBO.queryCollectList(condition);
        if (CollectionUtils.isEmpty(collectList)) {
            logger.info("****** 扫描eth正在归集的订单结束 ******");
            return;
        }
        for (Collect collect : collectList) {

            TransactionReceipt transactionReceipt = EthClient
                .getTransactionReceipt(collect.getTxHash()).get();
            if (!ETransactionReceiptStatus.SUCCESS.getCode()
                .equals(transactionReceipt.getStatus())) {
                Transaction transaction = EthClient
                    .getEthTransactionByHash(collect.getTxHash());
                EthBlock.Block block = EthClient
                    .getEthBlockByHash(transaction.getBlockHash());
                // 转成 ctqEthTransaction
                CtqEthTransaction ctqEthTransaction = ethTransactionBO
                    .convertTx(transaction, transactionReceipt.getGasUsed(),
                        block.getTimestamp());
                collectionNotice(ctqEthTransaction);
            }

        }
        logger.info("****** 扫描eth正在归集的订单结束 ******");
    }

    private void collectionNotice(CtqEthTransaction ctqEthTransaction) {
        // 根据交易hash查询归集记录
        Collect collect = collectBO
            .getCollectByTxHash(ctqEthTransaction.getHash());
        if (collect == null) {
            return;
        }
        if (!ECollectStatus.BROADCAST.getCode().equals(collect.getStatus())) {
            return;
        }
        // 归集订单状态更新
        BigDecimal txFee = ctqEthTransaction.getGasFee();
        collectBO.colectNoticeETH(collect, txFee,
            ctqEthTransaction.getBlockCreateDatetime());
        // 平台冷钱包加钱
        Account coldAccount = accountBO
            .getAccount(ESystemAccount.SYS_ACOUNT_ETH_COLD.getCode());
        BigDecimal amount = ctqEthTransaction.getValue();
        accountBO.changeAmount(coldAccount, amount, EChannelType.Online,
            ctqEthTransaction.getHash(), collect.getCode(),
            EJourBizTypeCold.AJ_COLLECT.getCode(),
            "归集-来自地址：" + collect.getFromAddress());
        // 平台盈亏账户记入矿工费
        Account sysAccount = accountBO
            .getAccount(ESystemAccount.SYS_ACOUNT_ETH.getCode());
        accountBO.changeAmount(sysAccount, txFee.negate(), EChannelType.Online,
            ctqEthTransaction.getHash(), collect.getCode(),
            EJourBizTypePlat.AJ_DEPOSIT_MINING_FEE.getCode(),
            "归集地址：" + collect.getFromAddress());
        // 落地交易记录
        ethTransactionBO.saveEthTransaction(ctqEthTransaction);
    }
}
