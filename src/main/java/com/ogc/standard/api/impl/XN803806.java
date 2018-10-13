package com.ogc.standard.api.impl;

import com.ogc.standard.ao.IHLOrderAO;
import com.ogc.standard.api.AProcessor;
import com.ogc.standard.common.JsonUtil;
import com.ogc.standard.core.StringValidater;
import com.ogc.standard.dto.req.XN803806Req;
import com.ogc.standard.exception.BizException;
import com.ogc.standard.exception.ParaException;
import com.ogc.standard.spring.SpringContextHolder;

/**
 * 详情查询红冲蓝补订单
 * @author: xieyj 
 * @since: 2017年5月15日 下午4:07:42 
 * @history:
 */
public class XN803806 extends AProcessor {
    private IHLOrderAO hlOrderAO = SpringContextHolder
        .getBean(IHLOrderAO.class);

    private XN803806Req req = null;

    @Override
    public Object doBusiness() throws BizException {
        return hlOrderAO.getHLOrder(req.getCode(), req.getSystemCode());
    }

    @Override
    public void doCheck(String inputparams, String operator)
            throws ParaException {
        req = JsonUtil.json2Bean(inputparams, XN803806Req.class);
        StringValidater.validateBlank(req.getCode(), req.getSystemCode());
    }
}
