package com.ogc.standard.api.impl;

import org.apache.commons.lang3.StringUtils;

import com.ogc.standard.ao.IRankAO;
import com.ogc.standard.api.AProcessor;
import com.ogc.standard.common.DateUtil;
import com.ogc.standard.common.JsonUtil;
import com.ogc.standard.core.ObjValidater;
import com.ogc.standard.core.StringValidater;
import com.ogc.standard.domain.Rank;
import com.ogc.standard.dto.req.XN640025Req;
import com.ogc.standard.exception.BizException;
import com.ogc.standard.exception.ParaException;
import com.ogc.standard.spring.SpringContextHolder;

/**
 * 分页查询榜单
 * @author: jiafr 
 * @since: 2018年10月12日 上午10:51:57 
 * @history:
 */
public class XN640025 extends AProcessor {

    private IRankAO rankAO = SpringContextHolder.getBean(IRankAO.class);

    private XN640025Req req = null;

    @Override
    public Object doBusiness() throws BizException {
        Rank condition = new Rank();
        condition.setType(req.getType());
        condition.setBatch(req.getBatch());
        condition.setPlayerCode(req.getPlayerCode());
        condition.setRank(StringValidater.toInteger(req.getRank()));
        condition.setMatch(req.getMatch());
        condition.setCreateDatetimeStart(DateUtil.strToDate(
            req.getCreateDatetimeStart(), DateUtil.FRONT_DATE_FORMAT_STRING));
        condition.setCreateDatetimeEnd(DateUtil.strToDate(
            req.getCreateDatetimeEnd(), DateUtil.FRONT_DATE_FORMAT_STRING));

        String column = req.getOrderColumn();
        if (StringUtils.isBlank(column)) {
            column = IRankAO.DEFAULT_ORDER_COLUMN;
        }
        condition.setOrder(column, req.getOrderDir());
        int start = StringValidater.toInteger(req.getStart());
        int limit = StringValidater.toInteger(req.getLimit());

        return rankAO.queryRankPage(start, limit, condition);
    }

    @Override
    public void doCheck(String inputparams, String operator)
            throws ParaException {
        req = JsonUtil.json2Bean(inputparams, XN640025Req.class);
        ObjValidater.validateReq(req);
    }
}
