/**
 * @Title Test.java 
 * @Package com.cdkj.coin.bitcoin 
 * @Description 
 * @author leo(haiqing)  
 * @date 2018年1月3日 下午9:02:08 
 * @version V1.0   
 */
package com.ogc.standard.bitcoin.original;

import org.bitcoinj.core.Address;
import org.bitcoinj.core.ECKey;
import org.bitcoinj.core.NetworkParameters;
import org.bitcoinj.params.MainNetParams;

/** 
 * @author: haiqingzheng 
 * @since: 2018年1月3日 下午9:02:08 
 * @history:
 */
public class Test {

    /** 
     * @param args 
     * @create: 2018年1月3日 下午9:02:08 haiqingzheng
     * @history: 
     */
    public static void main(String[] args) {

        NetworkParameters params = MainNetParams.get();
        ECKey key = new ECKey();
        System.out
            .println("We created a new key:\n" + key.getPrivateKeyAsHex());
        Address addressFromKey = key.toAddress(params);
        System.out.println("Public Address generated: "
                + String.valueOf(addressFromKey));

        System.out.println("Private key is: "
                + key.getPrivateKeyEncoded(params).toString());
        //
        // Wallet wallet = new Wallet(params);
        // wallet.importKey(key);
        //
        // final NetworkParameters netParams = MainNetParams.get();
        //
        // Block genesisBlock = netParams.getGenesisBlock();
        //
        // System.out.println(genesisBlock);
        //
        // BlockStore blockStore = new MemoryBlockStore(netParams);
        //
        // BlockFileLoader loader = new BlockFileLoader(netParams,
        // BlockFileLoader.getReferenceClientBlockFileList());
        //
        // for (Block block : loader) {
        // System.out.println(block);
        // }
        // Transaction t = genesisBlock.getTransactions().get(0);
        // System.out.println(t);

        // try {
        // BlockChain chain = new BlockChain(netParams, blockStore);
        // } catch (BlockStoreException e) {
        // e.printStackTrace();
        // }

        // genesisBlock.getPrevBlockHash()

        // MainNetParams.get().

    }
}
