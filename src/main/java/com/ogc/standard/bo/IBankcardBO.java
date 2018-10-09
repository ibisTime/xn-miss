package com.ogc.standard.bo;

import java.util.List;

import com.ogc.standard.bo.base.IPaginableBO;
import com.ogc.standard.domain.Bankcard;

public interface IBankcardBO extends IPaginableBO<Bankcard> {

    public boolean isBankcardExist(String code);

    public String saveBankcard(Bankcard data);

    public int removeBankcard(String code);

    public int refreshBankcard(Bankcard data);

    public List<Bankcard> queryBankcardList(Bankcard condition);

    public int getTotalBankcard(String userId, String bankCode);

    public Bankcard getBankcard(String code);

    public Bankcard getBankcardInfo(String code);

    public Bankcard getBankcardByBankcardNumber(String bankcardNumber);

    public List<Bankcard> queryBankcardList(String userId);

    public List<Bankcard> queryBankcardList(String userId, String bankCode);

}
