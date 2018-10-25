package com.ogc.standard.ao.impl;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ogc.standard.ao.IRankAO;
import com.ogc.standard.bo.IPlayerBO;
import com.ogc.standard.bo.IRankBO;
import com.ogc.standard.bo.base.Paginable;
import com.ogc.standard.common.DateUtil;
import com.ogc.standard.domain.Player;
import com.ogc.standard.domain.Rank;
import com.ogc.standard.enums.ERankType;

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
        rankBO.refreshRanking(ERankType.DAY.getCode(), data);
        // 更新总榜排名
        rankBO.refreshRanking(ERankType.TOTAL.getCode(), data);
    }

    @Override
    public Paginable<Rank> queryRankPage(int start, int limit, Rank condition) {
        if (StringUtils.isNotBlank(condition.getType())
                && ERankType.DAY.getCode().equals(condition.getType())) {
            condition.setBatch(DateUtil
                .getToday(DateUtil.DB_DATE_FORMAT_STRING));
        }
        Paginable<Rank> paginable = rankBO
            .getPaginable(start, limit, condition);
        List<Rank> list = paginable.getList();
        if (CollectionUtils.isNotEmpty(list)) {
            for (Rank rank : list) {
                init(rank);
            }
        }
        return paginable;
    }

    @Override
    public List<Rank> queryRankList(Rank condition) {
        if (StringUtils.isNotBlank(condition.getType())
                && ERankType.DAY.getCode().equals(condition.getType())) {
            condition.setBatch(DateUtil
                .getToday(DateUtil.DB_DATE_FORMAT_STRING));
        }
        List<Rank> list = rankBO.queryRankList(condition);
        if (CollectionUtils.isNotEmpty(list)) {
            for (Rank rank : list) {
                init(rank);
            }
        }
        return list;
    }

    @Override
    public Rank getRank(String code) {
        Rank data = rankBO.getRank(code);
        init(data);
        return data;
    }

    public void init(Rank data) {
        if (StringUtils.isNotBlank(data.getPlayerCode())) {
            Player player = playerBO.getPlayer(data.getPlayerCode());
            data.setPlayer(player);
            data.setAttentionSum(player.getAttentionSum());
            data.setShareSum(player.getShareSum());
            data.setScanSum(player.getScanSum());
        }
    }

}
