package com.ogc.standard.core;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.bitcoinj.core.Utils;
import org.bitcoinj.crypto.ChildNumber;
import org.bitcoinj.crypto.DeterministicKey;
import org.bitcoinj.crypto.HDUtils;
import org.bitcoinj.wallet.DeterministicKeyChain;
import org.bitcoinj.wallet.DeterministicSeed;
import org.ethereum.crypto.ECKey;
import org.spongycastle.util.encoders.Hex;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.ECKeyPair;
import org.web3j.crypto.RawTransaction;
import org.web3j.crypto.TransactionEncoder;
import org.web3j.crypto.Wallet;
import org.web3j.crypto.WalletFile;
import org.web3j.crypto.WalletUtils;
import org.web3j.protocol.admin.Admin;
import org.web3j.protocol.core.DefaultBlockParameter;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.methods.response.EthBlock;
import org.web3j.protocol.core.methods.response.EthGasPrice;
import org.web3j.protocol.core.methods.response.EthGetBalance;
import org.web3j.protocol.core.methods.response.EthGetTransactionCount;
import org.web3j.protocol.core.methods.response.EthSendTransaction;
import org.web3j.protocol.core.methods.response.Transaction;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.protocol.http.HttpService;
import org.web3j.utils.Numeric;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ogc.standard.common.PropertiesUtil;
import com.ogc.standard.common.RandomUtil;
import com.ogc.standard.domain.EthXAddress;
import com.ogc.standard.exception.BizException;
import com.ogc.standard.exception.EBizErrorCode;

/** 
 * @author: haiqingzheng 
 * @since: 2017年10月18日 下午7:42:57 
 * @history:
 */
public class EthClient {

    private static String ETH_URL = PropertiesUtil.Config.ETH_URL;

    public static final int MAX_SEED_ENTROPY_BITS = 512;

    private volatile static Admin admin;

    public static Admin getClient() {
        if (admin == null) {
            synchronized (EthClient.class) {
                admin = Admin.build(new HttpService(ETH_URL));
            }
        }
        return admin;
    }

    public static EthXAddress newAccount() {

        EthXAddress ethAddress = null;

        try {
            // 随机生成密码
            String password = RandomUtil.generate8();

            ECKey key = new ECKey();
            byte[] addr = key.getAddress();
            String addrBase16 = Hex.toHexString(addr);
            ECKeyPair ecKeyPair = ECKeyPair.create(key.getPrivKey());
            WalletFile walletFile = Wallet.createStandard(password, ecKeyPair);
            ObjectMapper objectMapper = new ObjectMapper();

            // keystore加密文件内容
            String keystoreContent = objectMapper
                .writeValueAsString(walletFile);

            // 临时解决web3jbug
            String findStr = "\"kdf\":\"scrypt\",";
            String keystoreContentCopy = keystoreContent;
            String newkeystoreContent = keystoreContentCopy
                .replaceFirst(findStr, "");
            int index = newkeystoreContent.indexOf(findStr);
            if (index != -1) {
                keystoreContent = newkeystoreContent;
            }

            // keystore文件名称
            String keystoreName = ETHAddressFactory.getFileName(addrBase16);

            // 新生成的地址
            String address = "0x" + addrBase16;

            // 返回新建对象
            if (StringUtils.isNotBlank(keystoreContent)
                    && StringUtils.isNotBlank(keystoreName)
                    && StringUtils.isNotBlank(address)) {
                ethAddress = new EthXAddress();
                ethAddress.setAddress(address);
                ethAddress.setKeystorePwd(password);
                ethAddress.setKeystoreContent(keystoreContent);
                ethAddress.setKeystoreName(keystoreName);
            }

        } catch (Exception e) {
            throw new BizException(EBizErrorCode.DEFAULT.getCode(),
                "以太坊地址创建发生异常，原因：" + e.getMessage());
        }
        if (ethAddress == null) {
            throw new BizException(EBizErrorCode.DEFAULT.getCode(),
                "以太坊地址创建失败");
        }

        return ethAddress;
    }

    public static BigDecimal getBalance(String address) {
        try {
            DefaultBlockParameter defaultBlockParameter = DefaultBlockParameterName.LATEST;
            EthGetBalance ethGetBalance = getClient()
                .ethGetBalance(address, defaultBlockParameter).send();
            if (ethGetBalance != null) {
                return new BigDecimal(ethGetBalance.getBalance().toString());
            } else {
                throw new BizException("xn625000", "以太坊余额查询失败");
            }
        } catch (Exception e) {
            throw new BizException("xn625000",
                "以太坊余额查询异常，原因：" + e.getMessage());
        }
    }

    public static BigDecimal getGasPrice() {
        try {
            EthGasPrice ethGasPrice = getClient().ethGasPrice().send();
            if (ethGasPrice != null) {
                return new BigDecimal(ethGasPrice.getGasPrice().toString());
            } else {
                throw new BizException(EBizErrorCode.DEFAULT.getCode(),
                    "以太坊gas价格查询失败");
            }
        } catch (Exception e) {
            throw new BizException(EBizErrorCode.DEFAULT.getCode(),
                "以太坊gas价格查询异常，原因：" + e.getMessage());
        }
    }

    public static void main(String[] args) {

        System.out.println(newAccount());

        // System.out.println(getGasPrice());
        // getBalance("0xd6095084132581043451cce50351d4beb2b4da15");

    }

    // 生成助记词
    private List<String> makeMnemonic() {

        List<String> mnemonicList = null;

        try {

            List<String> defaultMnenonic = new ArrayList<>();
            // defaultMnenonic.add("club");
            // defaultMnenonic.add("baby");
            // defaultMnenonic.add("index");
            // defaultMnenonic.add("hint");
            // defaultMnenonic.add("library");
            // defaultMnenonic.add("vendor");
            // defaultMnenonic.add("judge");
            // defaultMnenonic.add("napkin");
            // defaultMnenonic.add("media");
            // defaultMnenonic.add("bullet");
            // defaultMnenonic.add("action");
            // defaultMnenonic.add("wine");
            defaultMnenonic.add("cube");
            defaultMnenonic.add("oil");
            defaultMnenonic.add("need");
            defaultMnenonic.add("license");
            defaultMnenonic.add("stove");
            defaultMnenonic.add("style");
            defaultMnenonic.add("act");
            defaultMnenonic.add("drive");
            defaultMnenonic.add("kit");
            defaultMnenonic.add("ship");
            defaultMnenonic.add("claw");
            defaultMnenonic.add("rapid");

            // 钱包种子
            DeterministicSeed seed1 = new DeterministicSeed(new SecureRandom(),
                128, "", Utils.currentTimeSeconds());

            // 助记词
            mnemonicList = seed1.getMnemonicCode();

            DeterministicKeyChain keyChain1 = DeterministicKeyChain.builder()
                .seed(seed1).build();

            List<ChildNumber> keyPath = HDUtils.parsePath("M/44H/60H/0H/0/0");

            // DeterministicKey key2 =
            // keyChain2.getKey(KeyPurpose.RECEIVE_FUNDS);
            DeterministicKey key1 = keyChain1.getKeyByPath(keyPath, true);
            BigInteger privKey1 = key1.getPrivKey();

            Credentials credentials1 = Credentials
                .create(privKey1.toString(16));

            System.out.println(seed1.getMnemonicCode());

            System.out.println("seed1=" + seed1.toHexString());

            System.out.println("privateKey1:" + key1.getPrivateKeyAsHex());

            System.out.println("address1: " + credentials1.getAddress());

            // byte[] seed2 = MnemonicCode.toSeed(mnemonicList, "password");

            DeterministicSeed seed2 = new DeterministicSeed(defaultMnenonic,
                null, "", Utils.currentTimeSeconds());

            DeterministicKeyChain keyChain2 = DeterministicKeyChain.builder()
                .seed(seed2).build();

            // List<ChildNumber> keyPath =
            // HDUtils.parsePath("M/44H/60H/0H/0/0");

            // DeterministicKey key2 =
            // keyChain2.getKey(KeyPurpose.RECEIVE_FUNDS);
            DeterministicKey key2 = keyChain2.getKeyByPath(keyPath, true);
            BigInteger privKey2 = key2.getPrivKey();

            Credentials credentials2 = Credentials
                .create(privKey2.toString(16));

            System.out.println("seed2=" + seed2.toHexString());

            System.out.println("privateKey2:" + key2.getPrivateKeyAsHex());

            System.out.println("address2: " + credentials2.getAddress());

        } catch (Exception e) {
            e.printStackTrace();
        }

        return mnemonicList;
    }

    // 转账
    public static String transfer(String fromAddress, String keyStoreName,
            String keyStorePwd, String keystoreContent, String toAddress,
            BigDecimal value) {
        String txHash = null;
        try {

            Credentials credentials = getCredentials(fromAddress,
                keystoreContent, keyStorePwd);

            //
            EthGetTransactionCount ethGetTransactionCount = getClient()
                .ethGetTransactionCount(fromAddress,
                    DefaultBlockParameterName.LATEST)
                .sendAsync().get();
            //
            BigInteger nonce = ethGetTransactionCount.getTransactionCount();

            // TODO 动态获取
            BigInteger gasLimit = BigInteger.valueOf(21000);
            BigInteger gasPrice = getClient().ethGasPrice().send()
                .getGasPrice();

            // 本地签名的
            RawTransaction rawTransaction = RawTransaction.createTransaction(
                nonce, gasPrice, gasLimit, toAddress,
                new BigInteger(value.toString()), "");

            // 签名
            byte[] signedMessage = TransactionEncoder
                .signMessage(rawTransaction, credentials);
            txHash = Numeric.toHexString(signedMessage);
            EthSendTransaction ethSendTransaction = getClient()
                .ethSendRawTransaction(txHash).sendAsync().get();

            if (ethSendTransaction.getError() != null) {
                // failure
            }
            txHash = ethSendTransaction.getTransactionHash();

        } catch (Exception e) {
            throw new BizException(EBizErrorCode.DEFAULT.getCode(),
                "以太坊转账交易广播失败，原因：" + e.getMessage());
        }
        return txHash;
    }

    private static Credentials getCredentials(String keystoreName,
            String keystoreContent, String keyStorePwd) {
        String fileDirPath = PropertiesUtil.Config.KEY_STORE_PATH;
        File keystoreFile = new File(fileDirPath + "/" + keystoreName);
        if (!keystoreFile.exists()) {
            try {
                keystoreFile.createNewFile();
                FileWriter fw = null;
                BufferedWriter bw = null;
                fw = new FileWriter(keystoreFile.getAbsoluteFile(), true); // true表示可以追加新内容
                // fw=new FileWriter(f.getAbsoluteFile()); //表示不追加
                bw = new BufferedWriter(fw);
                bw.write(keystoreContent);
                bw.close();
            } catch (Exception e) {
                throw new BizException(EBizErrorCode.DEFAULT.getCode(),
                    "keystore文件写入异常，原因" + e.getMessage());
            }
        }
        Credentials credentials;
        try {
            credentials = WalletUtils.loadCredentials(keyStorePwd,
                keystoreFile);
            return credentials;
        } catch (Exception e) {
            throw new BizException(EBizErrorCode.DEFAULT.getCode(),
                "创建以太坊credentials失败，原因" + e.getMessage());
        }
    }

    public static Optional<TransactionReceipt> getTransactionReceipt(
            String txHash) {
        try {
            Optional<TransactionReceipt> transactionReceipt = getClient()
                .ethGetTransactionReceipt(txHash).send()
                .getTransactionReceipt();
            return transactionReceipt;
        } catch (Exception e) {
            throw new BizException(EBizErrorCode.DEFAULT.getCode(),
                "以太坊交易查询异常，原因：" + e.getMessage());
        }
    }

    public static Transaction getEthTransactionByHash(String txHash) {
        try {
            return getClient().ethGetTransactionByHash(txHash).send()
                .getResult();
        } catch (Exception e) {
            throw new BizException(EBizErrorCode.DEFAULT.getCode(),
                "以太坊交易查询异常，原因：" + e.getMessage());
        }
    }

    public static EthBlock.Block getEthBlockByHash(String hash) {
        try {
            return getClient().ethGetBlockByHash(hash, false).send()
                .getResult();
        } catch (Exception e) {
            throw new BizException(EBizErrorCode.DEFAULT.getCode(),
                "以太坊区块查询异常，原因：" + e.getMessage());
        }
    }
}
