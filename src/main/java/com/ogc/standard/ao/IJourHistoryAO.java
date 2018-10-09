package com.ogc.standard.ao;

import com.ogc.standard.annotation.ServiceModule;
import com.ogc.standard.bo.base.Paginable;
import com.ogc.standard.domain.Jour;

/** 
 * @author: xieyj 
 * @since: 2016年12月23日 下午9:05:07 
 * @history:
 */
@ServiceModule
public interface IJourHistoryAO {
    String DEFAULT_ORDER_COLUMN = "code";

    public Paginable<Jour> queryJourPage(int start, int limit, Jour condition);

    public Paginable<Jour> queryFrontJourPage(int start, int limit,
            Jour condition);

    public Jour getJour(String code, String systemCode);
}
