package com.ogc.standard.api.impl;

import com.ogc.standard.ao.IAccountAO;
import com.ogc.standard.api.AProcessor;
import com.ogc.standard.common.JsonUtil;
import com.ogc.standard.core.ObjValidater;
import com.ogc.standard.dto.req.XN803504Req;
import com.ogc.standard.enums.ECurrency;
import com.ogc.standard.exception.BizException;
import com.ogc.standard.exception.ParaException;
import com.ogc.standard.spring.SpringContextHolder;

/**
 * 列表查询各端账户总余额
 * @author: jiafr 
 * @since: 2018年10月15日 下午3:20:05 
 * @history:
 */
public class XN803504 extends AProcessor {

    private IAccountAO accountAO = SpringContextHolder
        .getBean(IAccountAO.class);

    private XN803504Req req = null;

    @Override
    public Object doBusiness() throws BizException {
        return accountAO.getAccountAmountSumList(ECurrency.CNY.getCode(),
            req.getStatus());
    }

    @Override
    public void doCheck(String inputparams, String operator)
            throws ParaException {
        req = JsonUtil.json2Bean(inputparams, XN803504Req.class);
        ObjValidater.validateReq(req);
    }
}
