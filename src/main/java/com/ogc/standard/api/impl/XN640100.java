/**
 * @Title XN640100.java 
 * @Package com.ogc.standard.api.impl 
 * @Description 
 * @author taojian  
 * @date 2018年10月10日 下午5:21:35 
 * @version V1.0   
 */
package com.ogc.standard.api.impl;

import com.ogc.standard.ao.IMissSessionAO;
import com.ogc.standard.api.AProcessor;
import com.ogc.standard.common.JsonUtil;
import com.ogc.standard.core.ObjValidater;
import com.ogc.standard.dto.req.XN640100Req;
import com.ogc.standard.dto.res.PKCodeRes;
import com.ogc.standard.exception.BizException;
import com.ogc.standard.exception.ParaException;
import com.ogc.standard.spring.SpringContextHolder;

/** 
 * 新增会话
 * @author: taojian 
 * @since: 2018年10月10日 下午5:21:35 
 * @history:
 */
public class XN640100 extends AProcessor {

    private IMissSessionAO missSessionAO = SpringContextHolder
        .getBean(IMissSessionAO.class);

    private XN640100Req req;

    @Override
    public Object doBusiness() throws BizException {
        return new PKCodeRes(missSessionAO.addMissSession(req.getType(),
            req.getUser1()));
    }

    @Override
    public void doCheck(String inputparams, String operator)
            throws ParaException {
        req = JsonUtil.json2Bean(inputparams, XN640100Req.class);
        ObjValidater.validateReq(req);

    }

}
