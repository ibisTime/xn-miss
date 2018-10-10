/**
 * @Title XN805145.java 
 * @Package com.ogc.standard.api.impl 
 * @Description 
 * @author dl  
 * @date 2018年8月20日 下午7:04:50 
 * @version V1.0   
 */
package com.ogc.standard.api.impl;

import com.ogc.standard.ao.IEventAO;
import com.ogc.standard.api.AProcessor;
import com.ogc.standard.common.JsonUtil;
import com.ogc.standard.core.ObjValidater;
import com.ogc.standard.domain.Event;
import com.ogc.standard.dto.req.XN805306Req;
import com.ogc.standard.exception.BizException;
import com.ogc.standard.exception.ParaException;
import com.ogc.standard.spring.SpringContextHolder;

/** 
 * 列表查询赛事信息
 * @author: dl 
 * @since: 2018年8月20日 下午7:04:50 
 * @history:
 */
public class XN805306 extends AProcessor {
    private IEventAO smsAO = SpringContextHolder.getBean(IEventAO.class);

    private XN805306Req req = null;

    @Override
    public Object doBusiness() throws BizException {
        Event condition = new Event();
        condition.setTitle(req.getTitle());
        condition.setUpdater(req.getUpdater());
        condition.setStatus(req.getStatus());
        return smsAO.querySmsList(condition);
    }

    @Override
    public void doCheck(String inputparams, String operator)
            throws ParaException {
        req = JsonUtil.json2Bean(inputparams, XN805306Req.class);
        ObjValidater.validateReq(req);
    }

}
