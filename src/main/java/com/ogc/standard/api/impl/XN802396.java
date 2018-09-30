
package com.ogc.standard.api.impl;

import com.ogc.standard.ao.IAwardMonthAO;
import com.ogc.standard.api.AProcessor;
import com.ogc.standard.common.DateUtil;
import com.ogc.standard.common.JsonUtil;
import com.ogc.standard.core.ObjValidater;
import com.ogc.standard.core.StringValidater;
import com.ogc.standard.domain.AwardMonth;
import com.ogc.standard.dto.req.XN802396Req;
import com.ogc.standard.exception.BizException;
import com.ogc.standard.exception.ParaException;
import com.ogc.standard.spring.SpringContextHolder;

/** 
 * 统计用户/渠道商月结算记录分页查询
 * @author: taojian 
 * @since: 2018年9月17日 下午9:06:28 
 * @history:
 */
public class XN802396 extends AProcessor {
    private IAwardMonthAO awardMonthAO = SpringContextHolder
        .getBean(IAwardMonthAO.class);

    private XN802396Req req;

    @Override
    public Object doBusiness() throws BizException {
        AwardMonth condition = new AwardMonth();
        condition.setCreateDatetimeStart(
            DateUtil.getFrontDate(req.getDateStart(), false));
        condition.setCreateDatetimeEnd(
            DateUtil.getFrontDate(req.getDateEnd(), true));
        int start = StringValidater.toInteger(req.getStart());
        int limit = StringValidater.toInteger(req.getLimit());
        return awardMonthAO.queryAwardMonthPage(start, limit, condition);
    }

    @Override
    public void doCheck(String inputparams, String operator)
            throws ParaException {
        req = JsonUtil.json2Bean(inputparams, XN802396Req.class);
        ObjValidater.validateReq(req);
    }

}
