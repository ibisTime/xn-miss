package com.ogc.standard.api.impl;

import com.ogc.standard.ao.IRankAO;
import com.ogc.standard.api.AProcessor;
import com.ogc.standard.common.JsonUtil;
import com.ogc.standard.core.ObjValidater;
import com.ogc.standard.dto.req.XN640020Req;
import com.ogc.standard.dto.res.BooleanRes;
import com.ogc.standard.exception.BizException;
import com.ogc.standard.exception.ParaException;
import com.ogc.standard.spring.SpringContextHolder;

/**
 * 人工调节
 * @author: jiafr
 * @since: 2018年10月12日 上午9:25:15 
 * @history:
 */
public class XN640020 extends AProcessor {
    private IRankAO rankAO = SpringContextHolder.getBean(IRankAO.class);

    private XN640020Req req = null;

    @Override
    public Object doBusiness() throws BizException {
        rankAO.manualAdjustment(req.getCode(), req.getFakeTicket(),
            req.getUpdater(), req.getRemark());
        return new BooleanRes(true);
    }

    @Override
    public void doCheck(String inputparams, String operator)
            throws ParaException {
        req = JsonUtil.json2Bean(inputparams, XN640020Req.class);
        ObjValidater.validateReq(req);
    }

}
