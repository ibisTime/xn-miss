package com.ogc.standard.bo.impl;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.RawTransaction;
import org.web3j.crypto.TransactionEncoder;
import org.web3j.crypto.WalletUtils;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.methods.response.EthGetTransactionCount;
import org.web3j.protocol.core.methods.response.EthSendTransaction;
import org.web3j.protocol.core.methods.response.Transaction;
import org.web3j.protocol.http.HttpService;
import org.web3j.utils.Numeric;

import com.ogc.standard.bo.IEthTransactionBO;
import com.ogc.standard.bo.base.PaginableBOImpl;
import com.ogc.standard.common.DateUtil;
import com.ogc.standard.common.PropertiesUtil;
import com.ogc.standard.dao.IEthTransactionDAO;
import com.ogc.standard.domain.CtqEthTransaction;
import com.ogc.standard.domain.EthTransaction;
import com.ogc.standard.enums.EErrorCode_main;
import com.ogc.standard.exception.BizException;

@Component
public class EthTransactionBOImpl extends PaginableBOImpl<EthTransaction>
        implements IEthTransactionBO {

    private static Web3j web3j = Web3j
        .build(new HttpService(PropertiesUtil.Config.ETH_URL));

    @Autowired
    private IEthTransactionDAO ethTransactionDAO;

    @Override
    public int saveEthTransaction(CtqEthTransaction ctqEthTransaction) {
        int count = 0;
        if (ctqEthTransaction != null) {
            EthTransaction condition = new EthTransaction();
            condition.setHash(ctqEthTransaction.getHash());
            // 一条交易记录可能对应多条tokenEvent，如果数据库已经有了，则不插入
            if (ethTransactionDAO.selectTotalCount(condition) <= 0) {
                EthTransaction transaction = new EthTransaction();

                transaction.setHash(ctqEthTransaction.getHash());
                transaction.setNonce(ctqEthTransaction.getNonce());
                transaction.setBlockHash(ctqEthTransaction.getBlockHash());
                transaction.setBlockNumber(ctqEthTransaction.getBlockNumber());
                transaction.setBlockCreateDatetime(
                    ctqEthTransaction.getBlockCreateDatetime());

                transaction.setTransactionIndex(
                    ctqEthTransaction.getTransactionIndex());
                transaction.setFrom(ctqEthTransaction.getFrom());
                transaction.setTo(ctqEthTransaction.getTo());
                transaction.setValue(ctqEthTransaction.getValue());
                transaction
                    .setSyncDatetime(ctqEthTransaction.getSyncDatetime());

                transaction.setGasPrice(ctqEthTransaction.getGasPrice());
                transaction.setGasLimit(ctqEthTransaction.getGasLimit());
                transaction.setGasUsed(ctqEthTransaction.getGasUsed());
                transaction.setGasFee(ctqEthTransaction.getGasFee());
                transaction.setInput(ctqEthTransaction.getInput());

                transaction.setPublicKey(ctqEthTransaction.getPublicKey());
                transaction.setRaw(ctqEthTransaction.getRaw());
                transaction.setR(ctqEthTransaction.getR());
                transaction.setS(ctqEthTransaction.getS());

                count = ethTransactionDAO.insert(transaction);
            }
        }
        return count;
    }

    @Override
    public CtqEthTransaction convertTx(Transaction tx, BigInteger gasUsed,
            BigInteger timestamp) {

        if (tx == null)
            return null;

        CtqEthTransaction transaction = new CtqEthTransaction();
        transaction.setHash(tx.getHash());
        transaction.setNonce(tx.getNonce());
        transaction.setBlockHash(tx.getBlockHash());
        transaction.setBlockNumber(tx.getBlockNumber());

        Date blockCreateTime = DateUtil.TimeStamp2Date(timestamp.toString(),
            DateUtil.DATA_TIME_PATTERN_1);
        transaction.setBlockCreateDatetime(blockCreateTime);

        transaction.setTransactionIndex(tx.getTransactionIndex());
        transaction.setFrom(tx.getFrom());
        transaction.setTo(tx.getTo());
        BigDecimal ethValue = new BigDecimal(tx.getValue());
        transaction.setValue(ethValue);
        transaction.setSyncDatetime(new Date());

        BigDecimal gasPrice = new BigDecimal(tx.getGasPrice());
        transaction.setGasPrice(gasPrice);
        transaction.setGasLimit(tx.getGas());
        transaction.setGasUsed(gasUsed);

        BigDecimal gasFee = new BigDecimal(gasUsed).multiply(gasPrice);
        transaction.setGasFee(gasFee);
        transaction.setInput(tx.getInput());
        transaction.setPublicKey(tx.getPublicKey());
        transaction.setRaw(tx.getRaw());
        transaction.setR(tx.getR());

        transaction.setS(tx.getS());
        return transaction;
    }

    // @Override
    // public List<EthTransaction> queryEthTransactionList(
    // EthTransaction condition) {
    // return ethTransactionDAO.selectList(condition);
    // }
    //
    @Override
    public EthTransaction getEthTransaction(String hash) {
        EthTransaction data = null;
        if (StringUtils.isNotBlank(hash)) {
            EthTransaction condition = new EthTransaction();
            condition.setHash(hash);
            data = ethTransactionDAO.select(condition);
            if (data == null) {
                throw new BizException(
                    EErrorCode_main.eth_RECORDNOTEXIST.getCode());
            }
        }
        return data;
    }

    @Override
    public BigDecimal getGasPrice() {
        BigDecimal price = null;
        try {
            price = new BigDecimal(
                web3j.ethGasPrice().send().getGasPrice().toString());
        } catch (IOException e) {
            throw new BizException(EErrorCode_main.eth_GSEPRICEERROR.getCode());
        }
        return price;
    }

    @Override
    public String broadcast(String from, String keystoreName,
            String keystoreContent, String keystorePwd, String to,
            BigDecimal value) {
        String txHash = null;
        try {

            String fileDirPath = PropertiesUtil.Config.KEY_STORE_PATH;
            File keystoreFile = new File(fileDirPath + "/" + keystoreName);
            if (!keystoreFile.exists()) {
                keystoreFile.createNewFile();
                FileWriter fw = null;
                BufferedWriter bw = null;
                try {
                    fw = new FileWriter(keystoreFile.getAbsoluteFile(), true); // true表示可以追加新内容
                    // fw=new FileWriter(f.getAbsoluteFile()); //表示不追加
                    bw = new BufferedWriter(fw);
                    bw.write(keystoreContent);
                    bw.close();
                } catch (Exception e) {
                    throw new BizException(
                        EErrorCode_main.eth_KEYSTOREERROE.getCode(),
                        e.getMessage());
                }
            }

            Credentials credentials = WalletUtils.loadCredentials(keystorePwd,
                keystoreFile);

            EthGetTransactionCount ethGetTransactionCount = web3j
                .ethGetTransactionCount(from, DefaultBlockParameterName.LATEST)
                .sendAsync().get();
            //
            BigInteger nonce = ethGetTransactionCount.getTransactionCount();

            // TODO 动态获取
            BigInteger gasLimit = BigInteger.valueOf(21000);
            BigInteger gasPrice = web3j.ethGasPrice().send().getGasPrice();

            // 本地签名的
            RawTransaction rawTransaction = RawTransaction.createTransaction(
                nonce, gasPrice, gasLimit, to, new BigInteger(value.toString()),
                "");

            // 签名
            byte[] signedMessage = TransactionEncoder
                .signMessage(rawTransaction, credentials);
            txHash = Numeric.toHexString(signedMessage);
            EthSendTransaction ethSendTransaction = web3j
                .ethSendRawTransaction(txHash).sendAsync().get();

            if (ethSendTransaction.getError() != null) {
                // failure
            }
            txHash = ethSendTransaction.getTransactionHash();

        } catch (Exception e) {
            throw new BizException(EErrorCode_main.eth_BROADCAST.getCode(),
                e.getMessage());
        }
        return txHash;
        // success

    }
}
