package com.ogc.standard.bo;

import com.ogc.standard.bo.base.IPaginableBO;
import com.ogc.standard.domain.BtcMAddress;

public interface IBtcMAddressBO extends IPaginableBO<BtcMAddress> {

    public BtcMAddress getAddressById(Long id);

    public String saveAddress();

    public BtcMAddress getBtcAddress(String address);

    public int refreshStatus(Long id);;

}
