/**
 * @Title XN805086.java 
 * @Package com.ogc.standard.api.impl 
 * @Description 
 * @author taojian  
 * @date 2018年9月12日 下午1:19:13 
 * @version V1.0   
 */
package com.ogc.standard.api.impl;

import com.ogc.standard.ao.IUserAO;
import com.ogc.standard.api.AProcessor;
import com.ogc.standard.common.JsonUtil;
import com.ogc.standard.core.ObjValidater;
import com.ogc.standard.dto.req.XN805086Req;
import com.ogc.standard.dto.res.BooleanRes;
import com.ogc.standard.exception.BizException;
import com.ogc.standard.exception.ParaException;
import com.ogc.standard.spring.SpringContextHolder;

/** 
 * 绑定邮箱
 * @author: taojian 
 * @since: 2018年9月12日 下午1:19:13 
 * @history:
 */
public class XN805086 extends AProcessor {
    private XN805086Req req = null;

    private IUserAO userAO = SpringContextHolder.getBean(IUserAO.class);

    @Override
    public Object doBusiness() throws BizException {
        userAO.bindEmail(req.getCaptcha(), req.getEmail(), req.getUserId(),
            req.getLanguage());
        return new BooleanRes(true);
    }

    @Override
    public void doCheck(String inputparams, String operator)
            throws ParaException {
        req = JsonUtil.json2Bean(inputparams, XN805086Req.class);
        ObjValidater.validateReq(req);
    }

}
