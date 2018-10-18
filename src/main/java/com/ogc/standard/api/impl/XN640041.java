package com.ogc.standard.api.impl;

import com.ogc.standard.ao.IActionAO;
import com.ogc.standard.api.AProcessor;
import com.ogc.standard.common.JsonUtil;
import com.ogc.standard.core.ObjValidater;
import com.ogc.standard.dto.req.XN640041Req;
import com.ogc.standard.dto.res.BooleanRes;
import com.ogc.standard.exception.BizException;
import com.ogc.standard.exception.ParaException;
import com.ogc.standard.spring.SpringContextHolder;

/**
 * 取消关注选手
 * @author: jiafr 
 * @since: 2018年10月17日 下午3:49:41 
 * @history:
 */
public class XN640041 extends AProcessor {

    private IActionAO actionAO = SpringContextHolder.getBean(IActionAO.class);

    private XN640041Req req;

    @Override
    public Object doBusiness() throws BizException {
        actionAO.cancelAction(req.getCreater(), req.getToCode());
        return new BooleanRes(true);
    }

    @Override
    public void doCheck(String inputparams, String operator)
            throws ParaException {
        req = JsonUtil.json2Bean(inputparams, XN640041Req.class);
        ObjValidater.validateReq(req);
    }

}
