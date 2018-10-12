package com.ogc.standard.api.impl;

import org.apache.commons.lang3.StringUtils;

import com.ogc.standard.ao.ITicketAO;
import com.ogc.standard.api.AProcessor;
import com.ogc.standard.common.DateUtil;
import com.ogc.standard.common.JsonUtil;
import com.ogc.standard.core.ObjValidater;
import com.ogc.standard.core.StringValidater;
import com.ogc.standard.domain.Ticket;
import com.ogc.standard.dto.req.XN640035Req;
import com.ogc.standard.exception.BizException;
import com.ogc.standard.exception.ParaException;
import com.ogc.standard.spring.SpringContextHolder;

/**
 * 分页查询加油订单
 * @author: jiafr 
 * @since: 2018年10月12日 下午8:22:57 
 * @history:
 */
public class XN640035 extends AProcessor {

    private ITicketAO ticketAO = SpringContextHolder.getBean(ITicketAO.class);

    private XN640035Req req;

    @Override
    public Object doBusiness() throws BizException {
        Ticket condition = new Ticket();
        condition.setPlayerCode(req.getPlayerCode());
        condition.setApplyUser(req.getApplyUser());
        condition.setStatus(req.getStatus());
        condition.setCreateDatetimeStart(DateUtil.strToDate(
            req.getCreateDatetimeStart(), DateUtil.FRONT_DATE_FORMAT_STRING));
        condition.setCreateDatetimeEnd(DateUtil.strToDate(
            req.getCreateDatetimeEnd(), DateUtil.FRONT_DATE_FORMAT_STRING));
        condition.setPayDatetimeStart(DateUtil.strToDate(
            req.getPayDatetimeStart(), DateUtil.FRONT_DATE_FORMAT_STRING));
        condition.setPayDatetimeEnd(DateUtil.strToDate(req.getPayDatetimeEnd(),
            DateUtil.FRONT_DATE_FORMAT_STRING));

        String column = req.getOrderColumn();
        if (StringUtils.isBlank(column)) {
            column = ITicketAO.DEFAULT_ORDER_COLUMN;
        }
        condition.setOrder(column, req.getOrderDir());
        int start = StringValidater.toInteger(req.getStart());
        int limit = StringValidater.toInteger(req.getLimit());
        return ticketAO.queryTicketPage(start, limit, condition);
    }

    @Override
    public void doCheck(String inputparams, String operator)
            throws ParaException {
        req = JsonUtil.json2Bean(inputparams, XN640035Req.class);
        ObjValidater.validateReq(req);
    }

}
