package com.ogc.standard.api.impl;

import com.ogc.standard.ao.IJourAO;
import com.ogc.standard.api.AProcessor;
import com.ogc.standard.common.JsonUtil;
import com.ogc.standard.core.ObjValidater;
import com.ogc.standard.dto.req.XN802321Req;
import com.ogc.standard.exception.BizException;
import com.ogc.standard.exception.ParaException;
import com.ogc.standard.spring.SpringContextHolder;

/**
 * 流水详情
 * @author: xieyj 
 * @since: 2016年12月24日 上午8:17:00 
 * @history:
 */
public class XN802321 extends AProcessor {

    private IJourAO jourAO = SpringContextHolder.getBean(IJourAO.class);

    private XN802321Req req = null;

    @Override
    public Object doBusiness() throws BizException {
        return jourAO.getJour(req.getCode());
    }

    @Override
    public void doCheck(String inputparams, String operator)
            throws ParaException {
        req = JsonUtil.json2Bean(inputparams, XN802321Req.class);
        ObjValidater.validateReq(req);
    }
}
