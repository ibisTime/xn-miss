package com.ogc.standard.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.ogc.standard.dao.IPlayerDAO;
import com.ogc.standard.dao.base.support.AMybatisTemplate;
import com.ogc.standard.domain.Player;

@Repository("playerDAOImpl")
public class PlayerDAOImpl extends AMybatisTemplate implements IPlayerDAO {

    @Override
    public int insert(Player data) {
        return super.insert(NAMESPACE.concat("insert_player"), data);
    }

    @Override
    public void updateApprove(Player data) {
        super.update(NAMESPACE.concat("update_approve"), data);
    }

    @Override
    public int delete(Player data) {
        return 0;
    }

    @Override
    public Player select(Player condition) {
        return super.select(NAMESPACE.concat("select_player"), condition,
            Player.class);
    }

    @Override
    public long selectTotalCount(Player condition) {
        return super.selectTotalCount(NAMESPACE.concat("select_player_count"),
            condition);
    }

    @Override
    public List<Player> selectList(Player condition) {
        return super.selectList(NAMESPACE.concat("select_player"), condition,
            Player.class);
    }

    @Override
    public List<Player> selectList(Player condition, int start, int count) {
        return super.selectList(NAMESPACE.concat("select_player"), start,
            count, condition, Player.class);
    }

    @Override
    public int update(Player data) {
        return super.update(NAMESPACE.concat("update_player"), data);
    }

    @Override
    public void updateUpPlayer(Player data) {
        super.update(NAMESPACE.concat("update_up_player"), data);
    }

    @Override
    public void updateDownPlayer(Player data) {
        super.update(NAMESPACE.concat("update_down_player"), data);
    }

    @Override
    public void updateAttention(Player data) {
        super.update(NAMESPACE.concat("update_attention"), data);
    }

    @Override
    public void updateScan(Player data) {
        super.update(NAMESPACE.concat("update_scan"), data);
    }

    @Override
    public void updateShare(Player data) {
        super.update(NAMESPACE.concat("update_share"), data);
    }

    @Override
    public void updatePlayerTicketSum(Player data) {
        super.update(NAMESPACE.concat("update_player_ticket_sum"), data);
    }

}
