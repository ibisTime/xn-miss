package com.ogc.standard.api.impl;

import com.ogc.standard.ao.IHLOrderAO;
import com.ogc.standard.api.AProcessor;
import com.ogc.standard.common.JsonUtil;
import com.ogc.standard.core.StringValidater;
import com.ogc.standard.dto.req.XN803801Req;
import com.ogc.standard.dto.res.BooleanRes;
import com.ogc.standard.exception.BizException;
import com.ogc.standard.exception.ParaException;
import com.ogc.standard.spring.SpringContextHolder;

/**
 * 人工调账
 * @author: myb858 
 * @since: 2017年4月24日 下午5:43:53 
 * @history:
 */
public class XN803801 extends AProcessor {
    private IHLOrderAO hlOrderAO = SpringContextHolder
        .getBean(IHLOrderAO.class);

    private XN803801Req req = null;

    @Override
    public synchronized Object doBusiness() throws BizException {
        hlOrderAO.approveOrder(req.getCode(), req.getAdjustResult(),
            req.getAdjustUser(), req.getAdjustNote(), req.getSystemCode());
        return new BooleanRes(true);
    }

    @Override
    public void doCheck(String inputparams, String operator)
            throws ParaException {
        req = JsonUtil.json2Bean(inputparams, XN803801Req.class);
        StringValidater.validateBlank(req.getCode(), req.getAdjustResult(),
            req.getAdjustUser(), req.getAdjustNote(), req.getSystemCode());
    }
}
