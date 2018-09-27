package com.ogc.standard.api.impl;

import com.ogc.standard.ao.IMarketAO;
import com.ogc.standard.api.AProcessor;
import com.ogc.standard.common.JsonUtil;
import com.ogc.standard.core.ObjValidater;
import com.ogc.standard.dto.req.XN650101Req;
import com.ogc.standard.exception.BizException;
import com.ogc.standard.exception.ParaException;
import com.ogc.standard.spring.SpringContextHolder;

/**
 * 获取币种行情
 * @author: lei 
 * @since: 2018年9月15日 下午3:17:30 
 * @history:
 */
public class XN650101 extends AProcessor {

    private IMarketAO marketAO = SpringContextHolder.getBean(IMarketAO.class);

    private XN650101Req req;

    @Override
    public Object doBusiness() throws BizException {
        return this.marketAO.marketListByReq(req);
    }

    @Override
    public void doCheck(String inputparams, String operator)
            throws ParaException {
        req = JsonUtil.json2Bean(inputparams, XN650101Req.class);
        ObjValidater.validateReq(req);
    }
}
