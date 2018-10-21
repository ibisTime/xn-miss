package com.ogc.standard.api.impl;

import com.ogc.standard.ao.IEventAO;
import com.ogc.standard.api.AProcessor;
import com.ogc.standard.common.JsonUtil;
import com.ogc.standard.core.ObjValidater;
import com.ogc.standard.dto.req.XN805301Req;
import com.ogc.standard.dto.res.BooleanRes;
import com.ogc.standard.exception.BizException;
import com.ogc.standard.exception.ParaException;
import com.ogc.standard.spring.SpringContextHolder;

/** 
 * 修改赛事信息
 * @author: dl 
 * @since: 2018年8月22日 下午3:07:39 
 * @history:
 */
public class XN805301 extends AProcessor {

    private IEventAO eventAO = SpringContextHolder.getBean(IEventAO.class);

    private XN805301Req req = null;

    @Override
    public Object doBusiness() throws BizException {
        eventAO.editEvent(req);
        return new BooleanRes(true);
    }

    @Override
    public void doCheck(String inputparams, String operator)
            throws ParaException {
        req = JsonUtil.json2Bean(inputparams, XN805301Req.class);
        ObjValidater.validateReq(req);
    }

}
