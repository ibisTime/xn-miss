package com.ogc.standard.bitcoin.original;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.codec.binary.Hex;
import org.bitcoinj.core.Address;
import org.bitcoinj.core.AddressFormatException;
import org.bitcoinj.core.ECKey;
import org.bitcoinj.core.NetworkParameters;
import org.bitcoinj.core.Utils;
import org.bitcoinj.crypto.ChildNumber;
import org.bitcoinj.crypto.DeterministicKey;
import org.bitcoinj.crypto.HDUtils;
import org.bitcoinj.crypto.MnemonicCode;
import org.bitcoinj.params.MainNetParams;
import org.bitcoinj.params.RegTestParams;
import org.bitcoinj.wallet.DeterministicKeyChain;
import org.bitcoinj.wallet.DeterministicSeed;

import com.ogc.standard.common.PropertiesUtil;
import com.ogc.standard.enums.EBtcEnv;

/** 
 * @author: haiqingzheng 
 * @since: 2018年1月30日 下午8:54:31 
 * @history:
 */
public class BtcClient {

    public static NetworkParameters getNetworkParameters() {
        NetworkParameters params = RegTestParams.get();
        if (EBtcEnv.PROD.getCode().equals(PropertiesUtil.Config.BTC_ENV)) {
            params = MainNetParams.get();
        }
        return params;
    }

    // 获取单个地址
    public static BtcAddressRes getSingleAddress() {
        NetworkParameters params = getNetworkParameters();
        ECKey key = new ECKey();
        String address = key.toAddress(params).toString();
        String privatekey = key.getPrivateKeyAsWiF(params);
        return new BtcAddressRes(address, privatekey);
    }

    // 地址格式校验，注意：正式环境和测试环境地址格式不一样
    public static boolean verifyAddress(String address) {
        NetworkParameters params = getNetworkParameters();
        try {
            Address.fromBase58(params, address);
            return true;
        } catch (AddressFormatException e) {
            return false;
        }
    }

    public static void main(String[] args) {
        // System.out.println(getSingleAddress());
        // System.out.println(verifyAddress("mpCH9Z8xiuK6s23GquDXLjSZMk3wmnu3AL"));

        new BtcClient().makeMnemonic();

    }

    // 生成助记词
    private List<String> makeMnemonic() {

        List<String> mnemonicList = null;

        try {

            // 钱包种子
            DeterministicSeed seed1 = new DeterministicSeed(new SecureRandom(),
                128, "", Utils.currentTimeSeconds());

            // 助记词
            mnemonicList = seed1.getMnemonicCode();

            byte[] seed2 = MnemonicCode.toSeed(mnemonicList, "");

            // 钱包主秘钥
            // DeterministicKey key = HDKeyDerivation
            // .createMasterPrivateKey(seed1.getSeedBytes());

            DeterministicKeyChain keyChain1 = DeterministicKeyChain.builder()
                .seed(seed1).build();

            List<ChildNumber> keyPath = HDUtils.parsePath("M/44H/0H/0H/0/0");

            DeterministicKey key = keyChain1.getKeyByPath(keyPath, true);

            System.out.println("seed1=" + seed1.toHexString());
            System.out.println("seed2=" + Hex.encodeHexString(seed2));

            System.out.println(seed1.getMnemonicCode());

            System.out.println(key.getPrivateKeyAsHex());

            System.out.println(key.toAddress(getNetworkParameters()));

            List<String> defaultMnenonic = new ArrayList<>();

            defaultMnenonic.add("uniform");
            defaultMnenonic.add("claim");
            defaultMnenonic.add("drum");
            defaultMnenonic.add("stool");
            defaultMnenonic.add("evidence");
            defaultMnenonic.add("stage");
            defaultMnenonic.add("prevent");
            defaultMnenonic.add("quiz");
            defaultMnenonic.add("lunar");
            defaultMnenonic.add("dove");
            defaultMnenonic.add("record");
            defaultMnenonic.add("kit");

            new MnemonicCode().check(defaultMnenonic);

            DeterministicSeed seed3 = new DeterministicSeed(defaultMnenonic,
                null, "", Utils.currentTimeSeconds());

            // 钱包主秘钥
            // DeterministicKey key3 = HDKeyDerivation
            // .createMasterPrivateKey(seed3.getSeedBytes());

            DeterministicKeyChain keyChain3 = DeterministicKeyChain.builder()
                .seed(seed3).build();

            DeterministicKey key3 = keyChain3.getKeyByPath(keyPath, true);
            // BigInteger privKey1 = key3.getPrivKey();

            System.out.println("seed3=" + seed3.toHexString());

            System.out.println(key3.getPrivateKeyAsHex());

            System.out.println(key3.toAddress(getNetworkParameters()));

        } catch (Exception e) {
            e.printStackTrace();
        }

        return mnemonicList;
    }

}
