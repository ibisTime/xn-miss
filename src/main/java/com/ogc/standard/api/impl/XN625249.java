package com.ogc.standard.api.impl;

import com.ogc.standard.ao.ITradeOrderAO;
import com.ogc.standard.api.AProcessor;
import com.ogc.standard.common.JsonUtil;
import com.ogc.standard.core.ObjValidater;
import com.ogc.standard.dto.req.XN625249Req;
import com.ogc.standard.dto.res.BooleanRes;
import com.ogc.standard.exception.BizException;
import com.ogc.standard.exception.ParaException;
import com.ogc.standard.spring.SpringContextHolder;

/** 
 * 删除交易订单
 * @author: haiqingzheng 
 * @since: 2017年11月14日 下午12:40:28 
 * @history:
 */
public class XN625249 extends AProcessor {

    private ITradeOrderAO tradeOrderAO = SpringContextHolder
        .getBean(ITradeOrderAO.class);

    private XN625249Req req;

    /** 
     * @see com.ogc.standard.api.IProcessor#doBusiness()
     */
    @Override
    public Object doBusiness() throws BizException {
        tradeOrderAO.dropTradeOrder(req.getCode());
        return new BooleanRes(true);
    }

    /** 
     * @see com.ogc.standard.api.IProcessor#doCheck(java.lang.String)
     */
    @Override
    public void doCheck(String inputparams, String operator)
            throws ParaException {
        req = JsonUtil.json2Bean(inputparams, XN625249Req.class);
        ObjValidater.validateReq(req);
    }

}
