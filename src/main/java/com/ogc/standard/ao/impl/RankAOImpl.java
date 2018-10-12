package com.ogc.standard.ao.impl;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ogc.standard.ao.IRankAO;
import com.ogc.standard.bo.IPlayerBO;
import com.ogc.standard.bo.IRankBO;
import com.ogc.standard.bo.base.Paginable;
import com.ogc.standard.domain.Player;
import com.ogc.standard.domain.Rank;
import com.ogc.standard.enums.ERankType;
import com.ogc.standard.exception.BizException;

@Service
public class RankAOImpl implements IRankAO {

    @Autowired
    private IRankBO rankBO;

    @Autowired
    private IPlayerBO playerBO;

    @Override
    @Transactional
    public void manualAdjustment(String code, String fakeTicket,
            String updater, String remark) {
        Rank data = rankBO.getRank(code);
        // 增加虚拟加油数
        rankBO.refreshManualAdjustment(data, fakeTicket, updater, remark);
        // 更新日榜排名
        updateRanking(ERankType.DAY.getCode(), data.getBatch(), data.getMatch());
        // 更新总榜排名
        updateRanking(ERankType.TOTAL.getCode(), data.getBatch(),
            data.getMatch());
    }

    @Override
    public String addRank(Rank data) {
        return rankBO.saveRank(data);
    }

    @Override
    public int editRank(Rank data) {
        if (!rankBO.isRankExist(data.getCode())) {
            throw new BizException("xn0000", "记录编号不存在");
        }
        return rankBO.refreshRank(data);
    }

    @Override
    public int dropRank(String code) {
        if (!rankBO.isRankExist(code)) {
            throw new BizException("xn0000", "记录编号不存在");
        }
        return rankBO.removeRank(code);
    }

    @Override
    public Paginable<Rank> queryRankPage(int start, int limit, Rank condition) {
        Paginable<Rank> paginable = rankBO
            .getPaginable(start, limit, condition);
        List<Rank> list = paginable.getList();
        for (Rank rank : list) {
            init(rank);
        }
        return paginable;
    }

    @Override
    public List<Rank> queryRankList(Rank condition) {
        List<Rank> list = rankBO.queryRankList(condition);
        for (Rank rank : list) {
            init(rank);
        }
        return list;
    }

    @Override
    public Rank getRank(String code) {
        Rank data = rankBO.getRank(code);
        init(data);
        return data;
    }

    public void updateRanking(String type, String batch, String match) {
        Rank condition = new Rank();
        condition.setType(type);
        condition.setBatch(batch);
        condition.setMatch(match);
        List<Rank> list = rankBO.queryRankList(condition);
        sort(list);
        for (Rank r1 : list) {
            r1.setRank(list.indexOf(r1) + 1);
            rankBO.refreshRanking(r1);
        }
    }

    public void sort(List<Rank> list) {
        Collections.sort(list, new Comparator<Rank>() {
            public int compare(Rank r1, Rank r2) {
                if (r1.getTicketSum() + r1.getFakeTicketSum() < r2
                    .getTicketSum() + r2.getFakeTicketSum()) {
                    return 1;
                }
                if (r1.getTicketSum() + r1.getFakeTicketSum() == r2
                    .getTicketSum() + r2.getFakeTicketSum()) {
                    return 0;
                }
                return -1;
            }
        });
    }

    public void init(Rank data) {
        if (StringUtils.isNotBlank(data.getPlayerCode())) {
            Player player = playerBO.getPlayer(data.getPlayerCode());
            data.setPlayerCname(player.getCname());
            data.setPlayerEname(player.getEname());
        }
    }

}
