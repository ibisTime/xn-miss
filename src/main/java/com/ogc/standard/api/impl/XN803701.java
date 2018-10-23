package com.ogc.standard.api.impl;

import com.ogc.standard.ao.IChargeAO;
import com.ogc.standard.api.AProcessor;
import com.ogc.standard.common.JsonUtil;
import com.ogc.standard.core.ObjValidater;
import com.ogc.standard.dto.req.XN803701Req;
import com.ogc.standard.dto.res.BooleanRes;
import com.ogc.standard.exception.BizException;
import com.ogc.standard.exception.ParaException;
import com.ogc.standard.spring.SpringContextHolder;

/**
 * 批量审批线下充值订单
 * @author: myb858 
 * @since: 2017年5月3日 上午9:24:44 
 * @history:
 */
public class XN803701 extends AProcessor {
    private IChargeAO chargeAO = SpringContextHolder.getBean(IChargeAO.class);

    private XN803701Req req = null;

    /** 
    * @see com.xnjr.base.api.IProcessor#doBusiness()
    */
    @Override
    public synchronized Object doBusiness() throws BizException {
        synchronized (XN803701.class) {
            for (String code : req.getCodeList()) {
                chargeAO.payOrder(code, req.getPayUser(), req.getPayResult(),
                    req.getPayNote());
            }
            return new BooleanRes(true);
        }
    }

    /** 
    * @see com.xnjr.base.api.IProcessor#doCheck(java.lang.String)
    */
    @Override
    public void doCheck(String inputparams, String operator)
            throws ParaException {
        req = JsonUtil.json2Bean(inputparams, XN803701Req.class);
        ObjValidater.validateReq(req);
    }
}
