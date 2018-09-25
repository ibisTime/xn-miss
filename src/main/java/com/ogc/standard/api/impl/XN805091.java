/**
 * @Title XN805091.java 
 * @Package com.ogc.standard.api.impl 
 * @Description 
 * @author taojian  
 * @date 2018年9月14日 下午3:07:44 
 * @version V1.0   
 */
package com.ogc.standard.api.impl;

import com.ogc.standard.ao.IUserAO;
import com.ogc.standard.api.AProcessor;
import com.ogc.standard.common.JsonUtil;
import com.ogc.standard.core.ObjValidater;
import com.ogc.standard.dto.req.XN805091Req;
import com.ogc.standard.dto.res.BooleanRes;
import com.ogc.standard.exception.BizException;
import com.ogc.standard.exception.ParaException;
import com.ogc.standard.spring.SpringContextHolder;

/** 
 * 修改负责区域
 * @author: taojian 
 * @since: 2018年9月14日 下午3:07:44 
 * @history:
 */
public class XN805091 extends AProcessor {

    private IUserAO userAO = SpringContextHolder.getBean(IUserAO.class);

    private XN805091Req req;

    @Override
    public Object doBusiness() throws BizException {
        userAO.editRespArea(req.getUserId(), req.getRespArea(),
            req.getUpdater(), req.getLanguage());
        return new BooleanRes(true);
    }

    @Override
    public void doCheck(String inputparams, String operator)
            throws ParaException {
        req = JsonUtil.json2Bean(inputparams, XN805091Req.class);
        ObjValidater.validateReq(req);
    }

}
