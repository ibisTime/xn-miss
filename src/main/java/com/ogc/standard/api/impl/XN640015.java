package com.ogc.standard.api.impl;

import org.apache.commons.lang3.StringUtils;

import com.ogc.standard.ao.IPlayerAO;
import com.ogc.standard.api.AProcessor;
import com.ogc.standard.common.DateUtil;
import com.ogc.standard.common.JsonUtil;
import com.ogc.standard.core.ObjValidater;
import com.ogc.standard.core.StringValidater;
import com.ogc.standard.domain.Player;
import com.ogc.standard.dto.req.XN640015Req;
import com.ogc.standard.exception.BizException;
import com.ogc.standard.exception.ParaException;
import com.ogc.standard.spring.SpringContextHolder;

/**
 * 分页查询选手
 * @author: jiafr 
 * @since: 2018年10月11日 下午4:31:43 
 * @history:
 */
public class XN640015 extends AProcessor {
    private IPlayerAO playerAO = SpringContextHolder.getBean(IPlayerAO.class);

    private XN640015Req req = null;

    @Override
    public Object doBusiness() throws BizException {
        Player condition = new Player();
        condition.setMatch(req.getMatch());
        condition.setMatchPlayCode(req.getMatchPlayCode());
        condition.setCnameQuery(req.getCname());
        condition.setEnameQuery(req.getEname());
        condition.setNativePlaceQuery(req.getNativePlace());
        condition.setStatus(req.getStatus());
        condition.setCreateDatetimeStart(DateUtil.strToDate(
            req.getCreateDatetimeStart(), DateUtil.FRONT_DATE_FORMAT_STRING));
        condition.setCreateDatetimeEnd(DateUtil.strToDate(
            req.getCreateDatetimeEnd(), DateUtil.FRONT_DATE_FORMAT_STRING));

        String column = req.getOrderColumn();
        if (StringUtils.isBlank(column)) {
            column = IPlayerAO.DEFAULT_ORDER_COLUMN;
        }
        condition.setOrder(column, req.getOrderDir());
        int start = StringValidater.toInteger(req.getStart());
        int limit = StringValidater.toInteger(req.getLimit());
        return playerAO.queryPlayerPage(start, limit, condition);
    }

    @Override
    public void doCheck(String inputparams, String operator)
            throws ParaException {
        req = JsonUtil.json2Bean(inputparams, XN640015Req.class);
        ObjValidater.validateReq(req);
    }
}
