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
import com.ogc.standard.dto.res.XN802901Res;

/** 
 * @author: xieyj 
 * @since: 2016年12月23日 下午9:05:07 
 * @history:
 */
@ServiceModule
public interface IJourAO {
    String DEFAULT_ORDER_COLUMN = "code";

    // 流水对账(包括现在和历史流水)
    public void checkJour(String code, Long checkAmount, String checkUser,
            String checkNote, String systemCode);

    public Paginable<Jour> queryJourPage(int start, int limit, Jour condition);

    public List<Jour> queryJourList(Jour condition);

    public Jour getJour(String code, String systemCode);

    // 获取某业务下的统计金额
    public Long getTotalAmount(String bizType, String channelType,
            String accountNumber);

    // 获取一段时间统计金额
    public XN802901Res getTotalAmountByDate(String accountNumber,
            String dateStart, String dateEnd);
}
