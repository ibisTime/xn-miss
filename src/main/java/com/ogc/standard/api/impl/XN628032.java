package com.ogc.standard.api.impl;

import com.ogc.standard.ao.IPostAO;
import com.ogc.standard.api.AProcessor;
import com.ogc.standard.common.JsonUtil;
import com.ogc.standard.core.ObjValidater;
import com.ogc.standard.dto.req.XN628032Req;
import com.ogc.standard.dto.res.BooleanRes;
import com.ogc.standard.exception.BizException;
import com.ogc.standard.exception.ParaException;
import com.ogc.standard.spring.SpringContextHolder;

/**
 * 审核帖子(oss)
 * @author: silver 
 * @since: 2018年8月22日 下午3:59:46 
 * @history:
 */
public class XN628032 extends AProcessor {
    private IPostAO postAO = SpringContextHolder.getBean(IPostAO.class);

    private XN628032Req req = null;

    @Override
    public Object doBusiness() throws BizException {
        postAO.approvePost(req.getCode(), req.getApproveResult(),
            req.getApprover(), req.getApproveNote());
        return new BooleanRes(true);
    }

    @Override
    public void doCheck(String inputparams, String operator)
            throws ParaException {
        req = JsonUtil.json2Bean(inputparams, XN628032Req.class);
        ObjValidater.validateReq(req);
    }

}