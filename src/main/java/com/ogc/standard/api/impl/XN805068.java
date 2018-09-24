package com.ogc.standard.api.impl;

import com.ogc.standard.ao.IUserAO;
import com.ogc.standard.api.AProcessor;
import com.ogc.standard.common.JsonUtil;
import com.ogc.standard.core.ObjValidater;
import com.ogc.standard.dto.req.XN805068Req;
import com.ogc.standard.dto.res.BooleanRes;
import com.ogc.standard.exception.BizException;
import com.ogc.standard.exception.ParaException;
import com.ogc.standard.spring.SpringContextHolder;

/**
 * 重置支付密码(需实名认证)
 * @author: xieyj 
 * @since: 2017年7月16日 下午2:29:05 
 * @history:
 */
public class XN805068 extends AProcessor {
    private IUserAO userAO = SpringContextHolder.getBean(IUserAO.class);

    private XN805068Req req = null;

    @Override
    public Object doBusiness() throws BizException {
        userAO.doResetTradePwd(req.getUserId(), req.getNewTradePwd(),
            req.getSmsCaptcha(), req.getIdKind(), req.getIdNo(),
            req.getLanguage());
        return new BooleanRes(true);
    }

    @Override
    public void doCheck(String inputparams, String operator)
            throws ParaException {
        req = JsonUtil.json2Bean(inputparams, XN805068Req.class);
        ObjValidater.validateReq(req);
    }

}
