package com.ogc.standard.bo;

import java.util.List;

import com.ogc.standard.bo.base.IPaginableBO;
import com.ogc.standard.domain.Jour;

/**
 * @author: xieyj 
 * @since: 2016年12月23日 下午2:40:13 
 * @history:
 */
public interface IJourHistoryBO extends IPaginableBO<Jour> {

    public List<Jour> queryJourList(Jour condition);

    public Jour getJour(String code, String systemCode);

    public Long getTotalAmount(String bizType, String channelType,
            String accountNumber);
}
