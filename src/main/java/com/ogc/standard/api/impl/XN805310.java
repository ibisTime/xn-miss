/**
 * @Title XN805310.java 
 * @Package com.ogc.standard.api.impl 
 * @Description 
 * @author dl  
 * @date 2018年8月22日 下午9:24:33 
 * @version V1.0   
 */
package com.ogc.standard.api.impl;

import com.ogc.standard.ao.IReadAO;
import com.ogc.standard.api.AProcessor;
import com.ogc.standard.common.JsonUtil;
import com.ogc.standard.core.ObjValidater;
import com.ogc.standard.core.StringValidater;
import com.ogc.standard.dto.req.XN805310Req;
import com.ogc.standard.dto.res.BooleanRes;
import com.ogc.standard.exception.BizException;
import com.ogc.standard.exception.ParaException;
import com.ogc.standard.spring.SpringContextHolder;

/** 
 * 阅读我的消息
 * @author: dl 
 * @since: 2018年8月22日 下午9:24:33 
 * @history:
 */
public class XN805310 extends AProcessor {

    private IReadAO readAO = SpringContextHolder.getBean(IReadAO.class);

    private XN805310Req req = null;

    @Override
    public Object doBusiness() throws BizException {
        readAO.readEvent(StringValidater.toLong(req.getId()));
        return new BooleanRes(true);
    }

    @Override
    public void doCheck(String inputparams, String operator)
            throws ParaException {
        req = JsonUtil.json2Bean(inputparams, XN805310Req.class);
        ObjValidater.validateReq(req);
    }

}
