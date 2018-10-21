package com.ogc.standard.api.impl;

import com.ogc.standard.ao.IPlayerAO;
import com.ogc.standard.api.AProcessor;
import com.ogc.standard.common.JsonUtil;
import com.ogc.standard.core.ObjValidater;
import com.ogc.standard.dto.req.XN640016Req;
import com.ogc.standard.exception.BizException;
import com.ogc.standard.exception.ParaException;
import com.ogc.standard.spring.SpringContextHolder;

/**
 * 详情查询选手
 * @author: jiafr 
 * @since: 2018年10月11日 下午5:04:01 
 * @history:
 */
public class XN640016 extends AProcessor {
    private IPlayerAO playerAO = SpringContextHolder.getBean(IPlayerAO.class);

    private XN640016Req req = null;

    @Override
    public Object doBusiness() throws BizException {
        return playerAO.getPlayer(req.getCode(), req.getUserId());
    }

    @Override
    public void doCheck(String inputparams, String operator)
            throws ParaException {
        req = JsonUtil.json2Bean(inputparams, XN640016Req.class);
        req.setUserId(operator);
        ObjValidater.validateReq(req);
    }

}
