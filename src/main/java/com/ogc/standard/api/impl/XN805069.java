package com.ogc.standard.api.impl;

import com.ogc.standard.ao.IUserAO;
import com.ogc.standard.api.AProcessor;
import com.ogc.standard.common.JsonUtil;
import com.ogc.standard.core.ObjValidater;
import com.ogc.standard.dto.req.XN805069Req;
import com.ogc.standard.dto.res.BooleanRes;
import com.ogc.standard.exception.BizException;
import com.ogc.standard.exception.ParaException;
import com.ogc.standard.spring.SpringContextHolder;

/**
 * 修改支付密码
 * @author: myb858 
 * @since: 2015年9月15日 下午2:30:11 
 * @history:
 */
public class XN805069 extends AProcessor {
    private IUserAO userAO = SpringContextHolder.getBean(IUserAO.class);

    private XN805069Req req = null;

    @Override
    public Object doBusiness() throws BizException {
        userAO.doModifyTradePwd(req.getUserId(), req.getOldTradePwd(),
            req.getNewTradePwd(), req.getLanguage());
        return new BooleanRes(true);
    }

    @Override
    public void doCheck(String inputparams, String operator)
            throws ParaException {
        req = JsonUtil.json2Bean(inputparams, XN805069Req.class);
        ObjValidater.validateReq(req);

    }

}
