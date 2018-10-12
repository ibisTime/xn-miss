package com.ogc.standard.bo;

import java.util.List;

import com.ogc.standard.bo.base.IPaginableBO;
import com.ogc.standard.domain.Rank;

public interface IRankBO extends IPaginableBO<Rank> {

    public boolean isRankExist(String code);

    public String saveRank(Rank data);

    public int removeRank(String code);

    public int refreshRank(Rank data);

    public List<Rank> queryRankList(Rank condition);

    public Rank getRank(String code);

    public void refreshManualAdjustment(Rank data, String fakeTicket,
            String updater, String remark);

    public void refreshRanking(Rank data);

}
