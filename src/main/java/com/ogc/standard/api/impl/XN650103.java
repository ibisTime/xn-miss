package com.ogc.standard.api.impl;

import com.ogc.standard.ao.IPropertyAO;
import com.ogc.standard.api.AProcessor;
import com.ogc.standard.common.JsonUtil;
import com.ogc.standard.core.ObjValidater;
import com.ogc.standard.dto.req.XN650103Req;
import com.ogc.standard.exception.BizException;
import com.ogc.standard.exception.ParaException;
import com.ogc.standard.spring.SpringContextHolder;

/**
 * 
 * @author: lei 
 * @since: 2018年9月15日 下午3:17:30 
 * @history:
 */
public class XN650103 extends AProcessor {

    private IPropertyAO propertyAO = SpringContextHolder
        .getBean(IPropertyAO.class);

    private XN650103Req req;

    @Override
    public Object doBusiness() throws BizException {
        return this.propertyAO.getProperty(req.getUserId(), req.getCurrency());
    }

    @Override
    public void doCheck(String inputparams, String operator)
            throws ParaException {
        req = JsonUtil.json2Bean(inputparams, XN650103Req.class);
        ObjValidater.validateReq(req);
    }
}
