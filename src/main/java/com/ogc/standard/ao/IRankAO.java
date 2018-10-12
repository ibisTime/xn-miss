package com.ogc.standard.ao;

import java.util.List;

import org.springframework.stereotype.Component;

import com.ogc.standard.bo.base.Paginable;
import com.ogc.standard.domain.Rank;

@Component
public interface IRankAO {
    static final String DEFAULT_ORDER_COLUMN = "code";

    public String addRank(Rank data);

    public int dropRank(String code);

    public int editRank(Rank data);

    public Paginable<Rank> queryRankPage(int start, int limit, Rank condition);

    public List<Rank> queryRankList(Rank condition);

    public Rank getRank(String code);

    public void manualAdjustment(String code, String fakeTicket,
            String updater, String remark);

}
