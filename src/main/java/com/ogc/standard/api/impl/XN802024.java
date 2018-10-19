package com.ogc.standard.api.impl;

import com.ogc.standard.ao.IBankcardAO;
import com.ogc.standard.api.AProcessor;
import com.ogc.standard.common.JsonUtil;
import com.ogc.standard.core.StringValidater;
import com.ogc.standard.dto.req.XN802024Req;
import com.ogc.standard.dto.res.BooleanRes;
import com.ogc.standard.exception.BizException;
import com.ogc.standard.exception.ParaException;
import com.ogc.standard.spring.SpringContextHolder;

/**
 * 启用/未启用银行卡
 * @author: lei 
 * @since: 2018年9月11日 下午5:36:16 
 * @history:
 */
public class XN802024 extends AProcessor {
    private IBankcardAO bankCardAO = SpringContextHolder
        .getBean(IBankcardAO.class);

    private XN802024Req req = null;

    @Override
    public Object doBusiness() throws BizException {
        bankCardAO.onOffBankcard(req.getCode());
        return new BooleanRes(true);
    }

    @Override
    public void doCheck(String inputparams, String operator)
            throws ParaException {
        req = JsonUtil.json2Bean(inputparams, XN802024Req.class);
        StringValidater.validateBlank(req.getCode());

    }

}
