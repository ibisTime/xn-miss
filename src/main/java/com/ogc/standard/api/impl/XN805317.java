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
import com.ogc.standard.dto.req.XN805317Req;
import com.ogc.standard.exception.BizException;
import com.ogc.standard.exception.ParaException;
import com.ogc.standard.spring.SpringContextHolder;

/** 
 * 详情查询我的消息
 * @author: dl 
 * @since: 2018年8月22日 下午9:24:33 
 * @history:
 */
public class XN805317 extends AProcessor {

    private IReadAO readAO = SpringContextHolder.getBean(IReadAO.class);

    private XN805317Req req = null;

    @Override
    public Object doBusiness() throws BizException {
        long id = StringValidater.toLong(req.getId());
        return readAO.getRead(id);
    }

    @Override
    public void doCheck(String inputparams, String operator)
            throws ParaException {
        req = JsonUtil.json2Bean(inputparams, XN805317Req.class);
        ObjValidater.validateReq(req);
    }

}
