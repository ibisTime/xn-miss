/**
 * @Title IJourAO.java 
 * @Package com.ogc.standard.ao 
 * @Description 
 * @author xieyj  
 * @date 2016年12月23日 下午9:05:07 
 * @version V1.0   
 */
package com.ogc.standard.ao;

import java.util.List;

import com.ogc.standard.annotation.ServiceModule;
import com.ogc.standard.bo.base.Paginable;
import com.ogc.standard.domain.Jour;

/** 
 * @author: xieyj 
 * @since: 2016年12月23日 下午9:05:07 
 * @history:
 */
@ServiceModule
public interface IJourAO {
    String DEFAULT_ORDER_COLUMN = "code";

    public Paginable<Jour> queryJourPage(int start, int limit, Jour condition);

    public List<Jour> queryJourList(Jour condition);

    public Jour getJour(String code, String systemCode);

}
