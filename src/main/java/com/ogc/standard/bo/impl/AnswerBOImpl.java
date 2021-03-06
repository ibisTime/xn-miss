package com.ogc.standard.bo.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ogc.standard.bo.IAnswerBO;
import com.ogc.standard.bo.base.PaginableBOImpl;
import com.ogc.standard.core.OrderNoGenerater;
import com.ogc.standard.dao.IAnswerDAO;
import com.ogc.standard.domain.Answer;
import com.ogc.standard.enums.EBoolean;
import com.ogc.standard.enums.EGeneratePrefix;
import com.ogc.standard.exception.BizException;
import com.ogc.standard.exception.EBizErrorCode;

@Component
public class AnswerBOImpl extends PaginableBOImpl<Answer> implements IAnswerBO {

    @Autowired
    private IAnswerDAO answerDAO;

    @Override
    public String saveAnswer(String question, String answer, String updater,
            String remark) {
        String code = OrderNoGenerater.generate(EGeneratePrefix.answer
            .getCode());
        Answer data = new Answer();
        data.setCode(code);
        data.setQuestion(question);
        data.setAnswer(answer);
        data.setStatus(EBoolean.YES.getCode());
        data.setCreateDatetime(new Date());
        data.setUpdater(updater);
        data.setUpdateDatetime(new Date());
        data.setRemark(remark);
        answerDAO.insert(data);
        return code;
    }

    @Override
    public void refreshAnswer(Answer data, String question, String answer,
            String updater, String remark) {
        data.setQuestion(question);
        data.setAnswer(answer);
        data.setUpdater(updater);
        data.setUpdateDatetime(new Date());
        data.setRemark(remark);
        answerDAO.updateAnswer(data);
    }

    @Override
    public void deleteAnswer(String code) {
        Answer data = getAnswer(code);
        data.setStatus(EBoolean.NO.getCode());
        answerDAO.updateStatus(data);
    }

    @Override
    public List<Answer> queryAnswerList(Answer condition) {

        return answerDAO.selectList(condition);
    }

    @Override
    public Answer getAnswer(String code) {
        Answer condition = new Answer();
        condition.setCode(code);
        Answer dataAnswer = answerDAO.select(condition);
        if (null == dataAnswer) {
            throw new BizException(EBizErrorCode.DEFAULT.getCode(), "不存在该回复模版");
        }
        return dataAnswer;
    }

}
