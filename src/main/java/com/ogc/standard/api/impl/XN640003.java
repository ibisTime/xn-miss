package com.ogc.standard.api.impl;

import com.ogc.standard.ao.IPlayerAO;
import com.ogc.standard.api.AProcessor;
import com.ogc.standard.common.JsonUtil;
import com.ogc.standard.core.ObjValidater;
import com.ogc.standard.dto.req.XN640003Req;
import com.ogc.standard.dto.res.BooleanRes;
import com.ogc.standard.exception.BizException;
import com.ogc.standard.exception.ParaException;
import com.ogc.standard.spring.SpringContextHolder;

/**
 * 上架选手
 * @author: jiafr 
 * @since: 2018年10月11日 下午1:48:00 
 * @history:
 */
public class XN640003 extends AProcessor {
    private IPlayerAO playerAO = SpringContextHolder.getBean(IPlayerAO.class);

    private XN640003Req req = null;

    @Override
    public Object doBusiness() throws BizException {
        playerAO.upPlayer(req.getCode(), req.getLocation(), req.getOrderNo(),
            req.getUpdater());
        return new BooleanRes(true);
    }

    @Override
    public void doCheck(String inputparams, String operator)
            throws ParaException {
        req = JsonUtil.json2Bean(inputparams, XN640003Req.class);
        ObjValidater.validateReq(req);
    }

}
