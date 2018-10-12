/**
 * @Title XN640040.java 
 * @Package com.ogc.standard.api.impl 
 * @Description 
 * @author taojian  
 * @date 2018年10月12日 下午3:35:02 
 * @version V1.0   
 */
package com.ogc.standard.api.impl;

import com.ogc.standard.ao.IActionAO;
import com.ogc.standard.api.AProcessor;
import com.ogc.standard.common.JsonUtil;
import com.ogc.standard.core.ObjValidater;
import com.ogc.standard.dto.req.XN640040Req;
import com.ogc.standard.dto.res.PKCodeRes;
import com.ogc.standard.exception.BizException;
import com.ogc.standard.exception.ParaException;
import com.ogc.standard.spring.SpringContextHolder;

/** 
 * 新增用户行为
 * @author: taojian 
 * @since: 2018年10月12日 下午3:35:02 
 * @history:
 */
public class XN640040 extends AProcessor {

    private IActionAO actionAO = SpringContextHolder.getBean(IActionAO.class);

    private XN640040Req req;

    @Override
    public Object doBusiness() throws BizException {

        return new PKCodeRes(actionAO.addAction(req.getType(), req.getToType(),
            req.getToCode(), req.getCreater()));
    }

    @Override
    public void doCheck(String inputparams, String operator)
            throws ParaException {
        req = JsonUtil.json2Bean(inputparams, XN640040Req.class);
        ObjValidater.validateReq(req);
    }

}
