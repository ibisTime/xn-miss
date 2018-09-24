package com.ogc.standard.api.impl;

import com.ogc.standard.ao.IUserFieldApproveAO;
import com.ogc.standard.api.AProcessor;
import com.ogc.standard.common.JsonUtil;
import com.ogc.standard.core.ObjValidater;
import com.ogc.standard.dto.req.XN805131Req;
import com.ogc.standard.dto.res.BooleanRes;
import com.ogc.standard.enums.EApplyType;
import com.ogc.standard.exception.BizException;
import com.ogc.standard.exception.ParaException;
import com.ogc.standard.spring.SpringContextHolder;

/** 
 * 修改邮箱申请
 * @author: taojian 
 * @since: 2018年9月13日 下午2:54:34 
 * @history:
 */
public class XN805131 extends AProcessor {

    private IUserFieldApproveAO userFieldApproveAO = SpringContextHolder
        .getBean(IUserFieldApproveAO.class);

    private XN805131Req req;

    @Override
    public Object doBusiness() throws BizException {
        userFieldApproveAO.apply(req.getUserId(), req.getIdHold(),
            req.getEmail(), req.getCaptcha(), EApplyType.EMAIL.getCode());
        return new BooleanRes(true);
    }

    @Override
    public void doCheck(String inputparams, String operator)
            throws ParaException {
        req = JsonUtil.json2Bean(inputparams, XN805131Req.class);
        ObjValidater.validateReq(req);
    }

}
