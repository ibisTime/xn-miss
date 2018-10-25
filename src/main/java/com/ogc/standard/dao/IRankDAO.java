package com.ogc.standard.dao;

import com.ogc.standard.dao.base.IBaseDAO;
import com.ogc.standard.domain.Rank;

//dao层 
public interface IRankDAO extends IBaseDAO<Rank> {
    String NAMESPACE = IRankDAO.class.getName().concat(".");

    int update(Rank data);

    void updateManualAdjustment(Rank data);

    void updateRanking(Rank data);

    void updateRankTicketSum(Rank rankDay);
}
