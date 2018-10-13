package com.ogc.standard.api.impl;

import com.ogc.standard.ao.IJourHistoryAO;
import com.ogc.standard.api.AProcessor;
import com.ogc.standard.common.JsonUtil;
import com.ogc.standard.core.StringValidater;
import com.ogc.standard.dto.req.XN803532Req;
import com.ogc.standard.exception.BizException;
import com.ogc.standard.exception.ParaException;
import com.ogc.standard.spring.SpringContextHolder;

/**
 * 历史流水详情查询
 * @author: xieyj 
 * @since: 2016年12月24日 上午7:59:19 
 * @history:
 */
public class XN803532 extends AProcessor {
    private IJourHistoryAO jourHistoryAO = SpringContextHolder
        .getBean(IJourHistoryAO.class);

    private XN803532Req req = null;

    @Override
    public Object doBusiness() throws BizException {
        return jourHistoryAO.getJour(req.getCode(), req.getSystemCode());
    }

    @Override
    public void doCheck(String inputparams, String operator)
            throws ParaException {
        req = JsonUtil.json2Bean(inputparams, XN803532Req.class);
        StringValidater.validateBlank(req.getCode(), req.getSystemCode());
    }
}
