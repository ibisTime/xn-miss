/**
 * @Title XN805060.java 
 * @Package com.ogc.standard.api.impl 
 * @Description 
 * @author dl  
 * @date 2018年8月21日 下午3:03:39 
 * @version V1.0   
 */
package com.ogc.standard.api.impl;

import com.ogc.standard.ao.IUserAO;
import com.ogc.standard.api.AProcessor;
import com.ogc.standard.common.JsonUtil;
import com.ogc.standard.core.ObjValidater;
import com.ogc.standard.dto.req.XN805060Req;
import com.ogc.standard.dto.res.BooleanRes;
import com.ogc.standard.exception.BizException;
import com.ogc.standard.exception.ParaException;
import com.ogc.standard.spring.SpringContextHolder;

/** 
 * 绑定手机
 * @author: dl 
 * @since: 2018年8月21日 下午3:03:39 
 * @history:
 */
public class XN805060 extends AProcessor {

    private IUserAO userAO = SpringContextHolder.getBean(IUserAO.class);

    private XN805060Req req = null;

    @Override
    public Object doBusiness() throws BizException {
        userAO.doBindMobile(req.getMobile(), req.getSmsCaptcha(),
            req.getUserId(), req.getLanguage());
        return new BooleanRes(true);
    }

    @Override
    public void doCheck(String inputparams, String operator)
            throws ParaException {
        req = JsonUtil.json2Bean(inputparams, XN805060Req.class);
        ObjValidater.validateReq(req);
    }

}
