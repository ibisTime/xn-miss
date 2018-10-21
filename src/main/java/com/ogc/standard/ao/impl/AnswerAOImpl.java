/**
 * @Title AnswerAOImpl.java 
 * @Package com.ogc.standard.ao.impl 
 * @Description 
 * @author taojian  
 * @date 2018年10月11日 下午2:00:52 
 * @version V1.0   
 */
package com.ogc.standard.ao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ogc.standard.ao.IAnswerAO;
import com.ogc.standard.bo.IAnswerBO;
import com.ogc.standard.bo.base.Paginable;
import com.ogc.standard.domain.Answer;
import com.ogc.standard.enums.EBoolean;
import com.ogc.standard.exception.BizException;
import com.ogc.standard.exception.EBizErrorCode;

/** 
 * @author: taojian 
 * @since: 2018年10月11日 下午2:00:52 
 * @history:
 */
@Service
public class AnswerAOImpl implements IAnswerAO {

    @Autowired
    private IAnswerBO answerBO;

    @Override
    public String addAnswer(String question, String answer, String updater,
            String remark) {
        return answerBO.saveAnswer(question, answer, updater, remark);
    }

    @Override
    public void dorpAnswer(String code) {
        answerBO.deleteAnswer(code);
    }

    @Override
    public void editAnswer(String code, String question, String answer,
            String updater, String remark, String bizType) {
        Answer data = answerBO.getAnswer(code);
        // 状态判断
        if (!EBoolean.YES.getCode().equals(data.getStatus())) {
            throw new BizException(EBizErrorCode.DEFAULT.getCode(), "该回复模版不能修改");
        }
        answerBO.refreshAnswer(data, question, answer, updater, remark);
    }

    @Override
    public Paginable<Answer> queryAnswerPage(int start, int limit,
            Answer condition) {
        return answerBO.getPaginable(start, limit, condition);
    }

    @Override
    public Answer getAnswer(String code) {
        return answerBO.getAnswer(code);
    }

    @Override
    public List<Answer> queryAnswerList(Answer condition) {
        return answerBO.queryAnswerList(condition);
    }

}
