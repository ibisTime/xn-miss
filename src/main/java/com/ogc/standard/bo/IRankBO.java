package com.ogc.standard.bo;

import java.util.List;

import com.ogc.standard.bo.base.IPaginableBO;
import com.ogc.standard.domain.Player;
import com.ogc.standard.domain.Rank;
import com.ogc.standard.domain.Ticket;

public interface IRankBO extends IPaginableBO<Rank> {

    public boolean isRankExist(String code);

    public String saveRank(Rank data);

    public int removeRank(String code);

    public int refreshRank(Rank data);

    public List<Rank> queryRankList(Rank condition);

    public Rank getRank(String code);

    public void refreshManualAdjustment(Rank data, String fakeTicket,
            String updater, String remark);

    public void refreshRanking(String type, Rank rankDay);

    public Rank getRankByPlayerCodeAndType(String playerCode, String type);

    public Rank saveRank(Player player, String type, Ticket ticket);

    public void refreshRankTicketSum(Rank rankDay, Long ticket);

}
