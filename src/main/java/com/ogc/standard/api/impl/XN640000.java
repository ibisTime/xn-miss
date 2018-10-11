package com.ogc.standard.api.impl;

import com.ogc.standard.ao.IPlayerAO;
import com.ogc.standard.api.AProcessor;
import com.ogc.standard.common.JsonUtil;
import com.ogc.standard.core.ObjValidater;
import com.ogc.standard.dto.req.XN640000Req;
import com.ogc.standard.exception.BizException;
import com.ogc.standard.exception.ParaException;
import com.ogc.standard.spring.SpringContextHolder;

/**
 * 新增选手
 * @author: jiafr 
 * @since: 2018年10月11日 下午1:48:00 
 * @history:
 */
public class XN640000 extends AProcessor {
    private IPlayerAO playerAO = SpringContextHolder.getBean(IPlayerAO.class);

    private XN640000Req req = null;

    @Override
    public Object doBusiness() throws BizException {
        return playerAO.addPlayer(req);
    }

    @Override
    public void doCheck(String inputparams, String operator)
            throws ParaException {
        req = JsonUtil.json2Bean(inputparams, XN640000Req.class);
        ObjValidater.validateReq(req);
    }

}
