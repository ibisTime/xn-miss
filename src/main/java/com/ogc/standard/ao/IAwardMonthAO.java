package com.ogc.standard.ao;

import com.ogc.standard.bo.base.Paginable;
import com.ogc.standard.domain.AwardMonth;
import com.ogc.standard.dto.res.XN802397Res;

/** 
 * @author: taojian 
 * @since: 2018年9月17日 下午3:02:24 
 * @history:
 */
public interface IAwardMonthAO {
    String DEFAULT_ORDER_COLUMN = "id";

    public Paginable<AwardMonth> queryAwardMonthPage(int start, int limit,
            AwardMonth condition);

    public AwardMonth getAwardMonth(AwardMonth condition);

    public XN802397Res statistics(String userId);
}
