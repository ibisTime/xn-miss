package com.ogc.standard.api.impl;

import com.ogc.standard.ao.ITicketAO;
import com.ogc.standard.api.AProcessor;
import com.ogc.standard.common.JsonUtil;
import com.ogc.standard.core.ObjValidater;
import com.ogc.standard.dto.req.XN640031Req;
import com.ogc.standard.dto.res.BooleanRes;
import com.ogc.standard.exception.BizException;
import com.ogc.standard.exception.ParaException;
import com.ogc.standard.spring.SpringContextHolder;

/**
 * 取消加油订单
 * @author: jiafr 
 * @since: 2018年10月12日 下午12:43:36 
 * @history:
 */
public class XN640031 extends AProcessor {
    private ITicketAO ticketAO = SpringContextHolder.getBean(ITicketAO.class);

    private XN640031Req req = null;

    @Override
    public Object doBusiness() throws BizException {
        ticketAO.cancelTicket(req.getCode(), req.getUserId(), req.getRemark());
        return new BooleanRes(true);
    }

    @Override
    public void doCheck(String inputparams, String operator)
            throws ParaException {
        req = JsonUtil.json2Bean(inputparams, XN640031Req.class);
        ObjValidater.validateReq(req);
    }

}
