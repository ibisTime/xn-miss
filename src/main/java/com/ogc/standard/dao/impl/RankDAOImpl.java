package com.ogc.standard.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.ogc.standard.dao.IRankDAO;
import com.ogc.standard.dao.base.support.AMybatisTemplate;
import com.ogc.standard.domain.Rank;

@Repository("rankDAOImpl")
public class RankDAOImpl extends AMybatisTemplate implements IRankDAO {

    @Override
    public int insert(Rank data) {
        return super.insert(NAMESPACE.concat("insert_rank"), data);
    }

    @Override
    public int delete(Rank data) {
        return super.delete(NAMESPACE.concat("delete_rank"), data);
    }

    @Override
    public Rank select(Rank condition) {
        return super.select(NAMESPACE.concat("select_rank"), condition,
            Rank.class);
    }

    @Override
    public long selectTotalCount(Rank condition) {
        return super.selectTotalCount(NAMESPACE.concat("select_rank_count"),
            condition);
    }

    @Override
    public List<Rank> selectList(Rank condition) {
        return super.selectList(NAMESPACE.concat("select_rank"), condition,
            Rank.class);
    }

    @Override
    public List<Rank> selectList(Rank condition, int start, int count) {
        return super.selectList(NAMESPACE.concat("select_rank"), start, count,
            condition, Rank.class);
    }

    @Override
    public int update(Rank data) {
        return super.update(NAMESPACE.concat("update_rank"), data);
    }

    @Override
    public void updateManualAdjustment(Rank data) {
        super.update(NAMESPACE.concat("update_manual_adjustment"), data);
    }

    @Override
    public void updateRanking(Rank data) {
        super.update(NAMESPACE.concat("update_ranking"), data);
    }

    @Override
    public void updateRankTicketSum(Rank data) {
        super.update(NAMESPACE.concat("update_rank_ticketSum"), data);
    }

}
