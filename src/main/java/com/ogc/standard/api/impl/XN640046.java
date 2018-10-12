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
import com.ogc.standard.dto.req.XN640046Req;
import com.ogc.standard.exception.BizException;
import com.ogc.standard.exception.ParaException;
import com.ogc.standard.spring.SpringContextHolder;

/** 
 * 详情查用户行为
 * @author: taojian 
 * @since: 2018年10月12日 下午3:35:02 
 * @history:
 */
public class XN640046 extends AProcessor {

    private IActionAO actionAO = SpringContextHolder.getBean(IActionAO.class);

    private XN640046Req req;

    @Override
    public Object doBusiness() throws BizException {

        return actionAO.getAction(req.getCode());
    }

    @Override
    public void doCheck(String inputparams, String operator)
            throws ParaException {
        req = JsonUtil.json2Bean(inputparams, XN640046Req.class);
        ObjValidater.validateReq(req);
    }

}
