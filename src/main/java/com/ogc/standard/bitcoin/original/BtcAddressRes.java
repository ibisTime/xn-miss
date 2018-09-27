package com.ogc.standard.bitcoin.original;

import com.ogc.standard.dao.base.ABaseDO;

public class BtcAddressRes extends ABaseDO {

    /** 
     * @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么) 
     */
    private static final long serialVersionUID = 1L;

    // 地址
    private String address;

    // 私钥
    private String privatekey;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPrivatekey() {
        return privatekey;
    }

    public void setPrivatekey(String privatekey) {
        this.privatekey = privatekey;
    }

    /** 
     * @param address
     * @param privatekey 
     */
    public BtcAddressRes(String address, String privatekey) {
        super();
        this.address = address;
        this.privatekey = privatekey;
    }

    @Override
    public String toString() {
        return "BtcAddressRes [address=" + address + ", privatekey="
                + privatekey + "]";
    }
}
