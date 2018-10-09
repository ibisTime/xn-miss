package com.ogc.standard.dao;

import com.ogc.standard.dao.base.IBaseDAO;
import com.ogc.standard.domain.HLOrder;

public interface IHLOrderDAO extends IBaseDAO<HLOrder> {
    String NAMESPACE = IHLOrderDAO.class.getName().concat(".");

    void approveOrder(HLOrder order);

}
