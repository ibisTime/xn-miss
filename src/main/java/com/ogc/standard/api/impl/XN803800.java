package com.ogc.standard.api.impl;

import java.math.BigDecimal;

import com.ogc.standard.ao.IJourAO;
import com.ogc.standard.api.AProcessor;
import com.ogc.standard.common.JsonUtil;
import com.ogc.standard.core.StringValidater;
import com.ogc.standard.dto.req.XN803800Req;
import com.ogc.standard.dto.res.BooleanRes;
import com.ogc.standard.exception.BizException;
import com.ogc.standard.exception.ParaException;
import com.ogc.standard.spring.SpringContextHolder;

/** 
 * 人工对账
 * @author: xieyj 
 * @since: 2016年12月25日 下午3:48:54 
 * @history:
 */
public class XN803800 extends AProcessor {
    private IJourAO jourAO = SpringContextHolder.getBean(IJourAO.class);

    private XN803800Req req = null;

    @Override
    public synchronized Object doBusiness() throws BizException {
        BigDecimal checkAmount = StringValidater.toBigDecimal(req
            .getCheckAmount());
        jourAO.checkJour(req.getCode(), checkAmount, req.getCheckUser(),
            req.getCheckNote(), req.getSystemCode());
        return new BooleanRes(true);
    }

    @Override
    public void doCheck(String inputparams, String operator)
            throws ParaException {
        req = JsonUtil.json2Bean(inputparams, XN803800Req.class);
        StringValidater.validateBlank(req.getCode(), req.getCheckNote(),
            req.getCheckUser(), req.getSystemCode());
        StringValidater.validateAmount(req.getCheckAmount());
    }
}
