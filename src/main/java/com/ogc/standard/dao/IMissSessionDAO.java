package com.ogc.standard.dao;

import com.ogc.standard.dao.base.IBaseDAO;
import com.ogc.standard.domain.MissSession;

public interface IMissSessionDAO extends IBaseDAO<MissSession> {
    String NAMESPACE = IMissSessionDAO.class.getName().concat(".");

    public int updateUnreadSum(MissSession data);
}
