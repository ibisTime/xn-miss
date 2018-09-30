package com.ogc.standard.api.impl;

import com.ogc.standard.ao.IEthWAddressAO;
import com.ogc.standard.api.AProcessor;
import com.ogc.standard.common.JsonUtil;
import com.ogc.standard.core.ObjValidater;
import com.ogc.standard.core.StringValidater;
import com.ogc.standard.dto.req.XN802521Req;
import com.ogc.standard.dto.res.BooleanRes;
import com.ogc.standard.exception.BizException;
import com.ogc.standard.exception.ParaException;
import com.ogc.standard.spring.SpringContextHolder;

/** 
 * 弃用以太坊散取/归集地址
 * @author: haiqingzheng 
 * @since: 2017年11月8日 下午3:12:21 
 * @history:
 */
public class XN802521 extends AProcessor {

    private IEthWAddressAO ethWAddressAO = SpringContextHolder
        .getBean(IEthWAddressAO.class);

    private XN802521Req req = null;

    @Override
    public Object doBusiness() throws BizException {
        ethWAddressAO.abandon(StringValidater.toLong(req.getId()));
        return new BooleanRes(true);
    }

    @Override
    public void doCheck(String inputparams, String operator)
            throws ParaException {
        req = JsonUtil.json2Bean(inputparams, XN802521Req.class);
        ObjValidater.validateReq(req);
    }

}
