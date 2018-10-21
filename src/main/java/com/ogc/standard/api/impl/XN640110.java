/**
 * @Title XN640110.java 
 * @Package com.ogc.standard.api.impl 
 * @Description 
 * @author taojian  
 * @date 2018年10月11日 下午2:49:34 
 * @version V1.0   
 */
package com.ogc.standard.api.impl;

import com.ogc.standard.ao.IAnswerAO;
import com.ogc.standard.api.AProcessor;
import com.ogc.standard.common.JsonUtil;
import com.ogc.standard.core.ObjValidater;
import com.ogc.standard.dto.req.XN640110Req;
import com.ogc.standard.dto.res.PKCodeRes;
import com.ogc.standard.exception.BizException;
import com.ogc.standard.exception.ParaException;
import com.ogc.standard.spring.SpringContextHolder;

/** 
 * 新增回复模版
 * @author: taojian 
 * @since: 2018年10月11日 下午2:49:34 
 * @history:
 */
public class XN640110 extends AProcessor {

    private IAnswerAO answerAO = SpringContextHolder.getBean(IAnswerAO.class);

    private XN640110Req req;

    @Override
    public Object doBusiness() throws BizException {
        return new PKCodeRes(answerAO.addAnswer(req.getQuestion(),
            req.getAnswer(), req.getUpdater(), req.getRemark()));
    }

    @Override
    public void doCheck(String inputparams, String operator)
            throws ParaException {
        req = JsonUtil.json2Bean(inputparams, XN640110Req.class);
        ObjValidater.validateReq(req);
    }

}
