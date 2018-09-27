package com.ogc.standard.ao;

import com.ogc.standard.domain.SimuMatchResultHistory;

public interface ISimuMatchResultHistoryAO {
    static final String DEFAULT_ORDER_COLUMN = "code";

    public void querySimuMatchResultHistory(SimuMatchResultHistory condition);

}
