package com.ogc.standard.ao;

import com.ogc.standard.bo.base.Paginable;
import com.ogc.standard.domain.SignLog;
import com.ogc.standard.dto.req.XN805140Req;

public interface ISignLogAO {

    static final String DEFAULT_ORDER_COLUMN = "id";

    public int addSignLog(XN805140Req req);

    public Paginable<SignLog> querySignLogPage(int start, int limit,
            SignLog condition);

    // 连续签到天数
    public long keepCheckIn(String userId);

}
