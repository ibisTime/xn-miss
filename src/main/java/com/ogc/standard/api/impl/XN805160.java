package com.ogc.standard.api.impl;

import com.ogc.standard.ao.IUserIdAuthAO;
import com.ogc.standard.api.AProcessor;
import com.ogc.standard.common.JsonUtil;
import com.ogc.standard.core.ObjValidater;
import com.ogc.standard.dto.req.XN805160Req;
import com.ogc.standard.dto.res.BooleanRes;
import com.ogc.standard.exception.BizException;
import com.ogc.standard.exception.ParaException;
import com.ogc.standard.spring.SpringContextHolder;

/** 
 * 申请认证
 * @author: taojian 
 * @since: 2018年9月13日 下午7:36:43 
 * @history:
 */
public class XN805160 extends AProcessor {

    private IUserIdAuthAO userIdAuthAO = SpringContextHolder
        .getBean(IUserIdAuthAO.class);

    private XN805160Req req;

    @Override
    public Object doBusiness() throws BizException {
        userIdAuthAO.apply(req);
        return new BooleanRes(true);
    }

    @Override
    public void doCheck(String inputparams, String operator)
            throws ParaException {
        req = JsonUtil.json2Bean(inputparams, XN805160Req.class);
        ObjValidater.validateReq(req);
    }

}
