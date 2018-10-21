package com.ogc.standard.dao;

import com.ogc.standard.dao.base.IBaseDAO;
import com.ogc.standard.domain.Player;

//dao层 
public interface IPlayerDAO extends IBaseDAO<Player> {
    String NAMESPACE = IPlayerDAO.class.getName().concat(".");

    Player selectForUpdate(Player condition);

    int update(Player data);

    void updateApprove(Player data);

    void updateUpPlayer(Player data);

    void updateDownPlayer(Player data);

    void updateAttention(Player data);

    void updateScan(Player data);

    void updateShare(Player data);

    void updatePlayerTicketSum(Player data);
}
