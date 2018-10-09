package com.ogc.standard.dao;

import com.ogc.standard.dao.base.IBaseDAO;
import com.ogc.standard.domain.Jour;

/**
 * @author: xieyj 
 * @since: 2016年12月23日 上午11:25:21 
 * @history:
 */
public interface IJourHistoryDAO extends IBaseDAO<Jour> {
    String NAMESPACE = IJourHistoryDAO.class.getName().concat(".");

    // 对账结果录入
    public int checkJour(Jour data);

    // 调账后状态更新
    public int adjustJour(Jour data);

    public long selectTotalAmount(Jour data);
}
