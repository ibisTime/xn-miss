package com.ogc.standard.api.impl;

import com.ogc.standard.ao.ITicketAO;
import com.ogc.standard.api.AProcessor;
import com.ogc.standard.common.JsonUtil;
import com.ogc.standard.core.ObjValidater;
import com.ogc.standard.dto.req.XN640032Req;
import com.ogc.standard.exception.BizException;
import com.ogc.standard.exception.ParaException;
import com.ogc.standard.spring.SpringContextHolder;

/**
 * 支付加油订单
 * @author: jiafr 
 * @since: 2018年10月12日 下午12:43:36 
 * @history:
 */
public class XN640032 extends AProcessor {
    private ITicketAO ticketAO = SpringContextHolder.getBean(ITicketAO.class);

    private XN640032Req req = null;

    @Override
    public Object doBusiness() throws BizException {
        return ticketAO.toPayTicket(req.getCode(), req.getPayType(),
            req.getTradePwd());
    }

    @Override
    public void doCheck(String inputparams, String operator)
            throws ParaException {
        req = JsonUtil.json2Bean(inputparams, XN640032Req.class);
        ObjValidater.validateReq(req);
    }

}
