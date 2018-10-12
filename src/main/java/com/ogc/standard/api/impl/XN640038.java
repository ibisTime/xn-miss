package com.ogc.standard.api.impl;

import org.apache.commons.lang3.StringUtils;

import com.ogc.standard.ao.ITicketAO;
import com.ogc.standard.api.AProcessor;
import com.ogc.standard.common.JsonUtil;
import com.ogc.standard.core.ObjValidater;
import com.ogc.standard.core.StringValidater;
import com.ogc.standard.domain.Ticket;
import com.ogc.standard.dto.req.XN640038Req;
import com.ogc.standard.exception.BizException;
import com.ogc.standard.exception.ParaException;
import com.ogc.standard.spring.SpringContextHolder;

/**
 * 我的订单分页查询
 * @author: jiafr 
 * @since: 2018年10月12日 下午8:22:57 
 * @history:
 */
public class XN640038 extends AProcessor {

    private ITicketAO ticketAO = SpringContextHolder.getBean(ITicketAO.class);

    private XN640038Req req;

    @Override
    public Object doBusiness() throws BizException {
        Ticket condition = new Ticket();
        condition.setApplyUser(req.getUserId());
        condition.setStatusList(req.getStatusList());

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
        req = JsonUtil.json2Bean(inputparams, XN640038Req.class);
        ObjValidater.validateReq(req);
    }

}
