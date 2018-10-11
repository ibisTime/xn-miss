/**
 * @Title XN640110.java 
 * @Package com.ogc.standard.api.impl 
 * @Description 
 * @author taojian  
 * @date 2018年10月11日 下午2:49:34 
 * @version V1.0   
 */
package com.ogc.standard.api.impl;

import org.apache.commons.lang3.StringUtils;

import com.ogc.standard.ao.IAnswerAO;
import com.ogc.standard.api.AProcessor;
import com.ogc.standard.common.DateUtil;
import com.ogc.standard.common.JsonUtil;
import com.ogc.standard.core.ObjValidater;
import com.ogc.standard.domain.Answer;
import com.ogc.standard.dto.req.XN640127Req;
import com.ogc.standard.exception.BizException;
import com.ogc.standard.exception.ParaException;
import com.ogc.standard.spring.SpringContextHolder;

/** 
 * 列表查回复模版
 * @author: taojian 
 * @since: 2018年10月11日 下午2:49:34 
 * @history:
 */
public class XN640127 extends AProcessor {

    private IAnswerAO answerAO = SpringContextHolder.getBean(IAnswerAO.class);

    private XN640127Req req;

    @Override
    public Object doBusiness() throws BizException {
        Answer condition = new Answer();
        condition.setQuestion(req.getQuestion());
        condition.setStatus(req.getStatus());
        condition.setUpdater(req.getUpdater());
        condition.setCreateDatetimeStart(DateUtil.getFrontDate(
            req.getCreateDatetimeStart(), false));
        condition.setCreateDatetimeEnd(DateUtil.getFrontDate(
            req.getCreateDatetimeEnd(), true));

        String column = req.getOrderColumn();
        if (StringUtils.isBlank(column)) {
            column = "code";
        }
        condition.setOrder(column, req.getOrderDir());
        return answerAO.queryAnswerList(condition);
    }

    @Override
    public void doCheck(String inputparams, String operator)
            throws ParaException {
        req = JsonUtil.json2Bean(inputparams, XN640127Req.class);
        ObjValidater.validateReq(req);
    }

}
