package com.ogc.standard.api.impl;

import com.ogc.standard.ao.IUserAO;
import com.ogc.standard.api.AProcessor;
import com.ogc.standard.common.JsonUtil;
import com.ogc.standard.core.ObjValidater;
import com.ogc.standard.dto.req.XN805050Req;
import com.ogc.standard.dto.res.XN805050Res;
import com.ogc.standard.exception.BizException;
import com.ogc.standard.exception.ParaException;
import com.ogc.standard.spring.SpringContextHolder;

/**
 * 普通登录
 * @author: dl 
 * @since: 2018年8月20日 下午5:44:39 
 * @history:
 */
public class XN805050 extends AProcessor {
    private IUserAO userAO = SpringContextHolder.getBean(IUserAO.class);

    private XN805050Req req = null;

    @Override
    public Object doBusiness() throws BizException {
        return new XN805050Res(
            userAO.doLogin(req.getLoginName(), req.getLoginPwd(),
                req.getClient(), req.getLocation(), req.getLanguage()));
    }

    @Override
    public void doCheck(String inputparams, String operator)
            throws ParaException {
        req = JsonUtil.json2Bean(inputparams, XN805050Req.class);
        ObjValidater.validateReq(req);
    }
}
