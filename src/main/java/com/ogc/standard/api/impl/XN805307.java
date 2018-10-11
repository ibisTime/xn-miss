/**
 * @Title XN805247.java 
 * @Package com.ogc.standard.api.impl 
 * @Description 
 * @author dl  
 * @date 2018年8月20日 下午1:36:41 
 * @version V1.0   
 */
package com.ogc.standard.api.impl;

import com.ogc.standard.ao.IEventAO;
import com.ogc.standard.api.AProcessor;
import com.ogc.standard.common.JsonUtil;
import com.ogc.standard.core.ObjValidater;
import com.ogc.standard.dto.req.XN805307Req;
import com.ogc.standard.exception.BizException;
import com.ogc.standard.exception.ParaException;
import com.ogc.standard.spring.SpringContextHolder;

/** 
 * 详情查询赛事信息
 * @author: dl 
 * @since: 2018年8月20日 下午1:36:41 
 * @history:
 */
public class XN805307 extends AProcessor {
    private IEventAO eventAO = SpringContextHolder.getBean(IEventAO.class);

    private XN805307Req req = null;

    @Override
    public Object doBusiness() throws BizException {
        return eventAO.getEvent(req.getCode());
    }

    @Override
    public void doCheck(String inputparams, String operator)
            throws ParaException {
        req = JsonUtil.json2Bean(inputparams, XN805307Req.class);
        ObjValidater.validateReq(req);

    }

}
