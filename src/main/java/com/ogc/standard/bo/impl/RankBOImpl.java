package com.ogc.standard.bo.impl;

import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ogc.standard.bo.IRankBO;
import com.ogc.standard.bo.base.PaginableBOImpl;
import com.ogc.standard.common.DateUtil;
import com.ogc.standard.core.OrderNoGenerater;
import com.ogc.standard.core.StringValidater;
import com.ogc.standard.dao.IRankDAO;
import com.ogc.standard.domain.Player;
import com.ogc.standard.domain.Rank;
import com.ogc.standard.domain.Ticket;
import com.ogc.standard.enums.EGeneratePrefix;
import com.ogc.standard.enums.ERankType;
import com.ogc.standard.exception.BizException;

@Component
public class RankBOImpl extends PaginableBOImpl<Rank> implements IRankBO {

    @Autowired
    private IRankDAO rankDAO;

    @Override
    public void refreshManualAdjustment(Rank data, String fakeTicket,
            String updater, String remark) {
        data.setFakeTicketSum(data.getFakeTicketSum()
                + StringValidater.toInteger(fakeTicket));
        data.setUpdater(updater);
        data.setUpdateDatetime(new Date());
        data.setRemark(remark);
        rankDAO.updateManualAdjustment(data);
    }

    @Override
    public void refreshRanking(String type, Rank rank) {
        Rank condition = new Rank();
        condition.setType(type);
        condition.setBatch(rank.getBatch());
        condition.setMatch(rank.getMatch());
        List<Rank> list = rankDAO.selectList(condition);
        sort(list);
        for (Rank r1 : list) {
            r1.setRank(list.indexOf(r1) + 1);
            rankDAO.updateRanking(r1);
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

    @Override
    public Rank getRankByPlayerCodeAndType(String playerCode, String type) {
        Rank data = null;
        if (StringUtils.isNotBlank(playerCode) && StringUtils.isNotBlank(type)) {
            Rank condition = new Rank();
            condition.setPlayerCode(playerCode);
            condition.setType(type);
            data = rankDAO.select(condition);
        }
        return data;
    }

    @Override
    public boolean isRankExist(String code) {
        Rank condition = new Rank();
        condition.setCode(code);
        if (rankDAO.selectTotalCount(condition) > 0) {
            return true;
        }
        return false;
    }

    @Override
    public String saveRank(Rank data) {
        String code = null;
        if (data != null) {
            code = OrderNoGenerater.generate(EGeneratePrefix.RANK.getCode());
            data.setCode(code);
            rankDAO.insert(data);
        }
        return code;
    }

    @Override
    public int removeRank(String code) {
        int count = 0;
        if (StringUtils.isNotBlank(code)) {
            Rank data = new Rank();
            data.setCode(code);
            count = rankDAO.delete(data);
        }
        return count;
    }

    @Override
    public int refreshRank(Rank data) {
        int count = 0;
        if (StringUtils.isNotBlank(data.getCode())) {
            count = rankDAO.update(data);
        }
        return count;
    }

    @Override
    public List<Rank> queryRankList(Rank condition) {
        return rankDAO.selectList(condition);
    }

    @Override
    public Rank getRank(String code) {
        Rank data = null;
        if (StringUtils.isNotBlank(code)) {
            Rank condition = new Rank();
            condition.setCode(code);
            data = rankDAO.select(condition);
            if (data == null) {
                throw new BizException("xn0000", "榜单不存在");
            }
        }
        return data;
    }

    @Override
    public Rank saveRank(Player player, String type, Ticket ticket) {
        Rank data = null;
        if (player != null) {
            data = new Rank();
            String code = OrderNoGenerater.generate(EGeneratePrefix.RANK
                .getCode());
            data.setCode(code);
            data.setType(type);
            if (ERankType.DAY.getCode().equals(type)) {
                data.setBatch(DateUtil.getToday(DateUtil.DB_DATE_FORMAT_STRING));
            } else {
                data.setBatch("总榜");
            }
            data.setPlayerCode(player.getCode());
            data.setMatch(player.getMatch());
            data.setCreateDatetime(new Date());
            data.setTicketSum(ticket.getTicket());
            data.setFakeTicketSum(0L);
            data.setAttentionSum(0L);
            data.setShareSum(0L);
            data.setScanSum(0L);
            rankDAO.insert(data);
        }
        return data;
    }
}
