package com.ogc.standard.api.impl;

import com.ogc.standard.ao.ISmsOutAO;
import com.ogc.standard.api.AProcessor;
import com.ogc.standard.common.JsonUtil;
import com.ogc.standard.core.ObjValidater;
import com.ogc.standard.dto.req.XN630093Req;
import com.ogc.standard.dto.res.BooleanRes;
import com.ogc.standard.exception.BizException;
import com.ogc.standard.exception.ParaException;
import com.ogc.standard.spring.SpringContextHolder;

/** 
 * 发送邮箱验证码
 * @author: taojian 
 * @since: 2018年9月12日 上午11:22:42 
 * @history:
 */
public class XN630093 extends AProcessor {

    private XN630093Req req = null;

    private ISmsOutAO smsOutAO = SpringContextHolder.getBean(ISmsOutAO.class);

    @Override
    public Object doBusiness() throws BizException {
        smsOutAO.sendEmailCaptcha(req.getEmail(), req.getBizType());
        return new BooleanRes(true);
    }

    @Override
    public void doCheck(String inputparams, String operator)
            throws ParaException {
        req = JsonUtil.json2Bean(inputparams, XN630093Req.class);
        ObjValidater.validateReq(req);
    }

}
