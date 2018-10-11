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
import com.ogc.standard.enums.EAnswerStauts;
import com.ogc.standard.enums.EBoolean;
import com.ogc.standard.enums.EPublishType;
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
            String remark, String bizType) {
        String code = null;
        if (EPublishType.SAVE.getCode().equals(bizType)) {
            code = answerBO.saveAnswer(question, answer, updater, remark,
                EAnswerStauts.DRAFT.getCode());
        } else {
            code = answerBO.saveAnswer(question, answer, updater, remark,
                EAnswerStauts.TOAPPROVE.getCode());
        }
        return code;
    }

    @Override
    public void editAnswer(String code, String question, String answer,
            String updater, String remark, String bizType) {
        Answer data = answerBO.getAnswer(code);
        String status = data.getStatus();
        // 状态判断
        if (!EAnswerStauts.DRAFT.getCode().equals(status)
                && !EAnswerStauts.FAILED.getCode().equals(status)
                && !EAnswerStauts.OFF.getCode().equals(status)) {
            throw new BizException(EBizErrorCode.DEFAULT.getCode(), "该回复模版不能修改");
        }
        if (EPublishType.SAVE.getCode().equals(bizType)) {
            answerBO.refreshAnswer(data, question, answer, updater, remark,
                EAnswerStauts.DRAFT.getCode());
        } else {
            answerBO.refreshAnswer(data, question, answer, updater, remark,
                EAnswerStauts.TOAPPROVE.getCode());
        }
    }

    @Override
    public void approveAnswer(String code, String approveResult,
            String updater, String remark) {
        Answer data = answerBO.getAnswer(code);
        String status = data.getStatus();
        if (!EAnswerStauts.TOAPPROVE.getCode().equals(status)) {
            throw new BizException(EBizErrorCode.DEFAULT.getCode(),
                "该回复模版状态不能被审核");
        }
        if (EBoolean.YES.getCode().equals(approveResult)) {
            answerBO.refreshStatus(code, updater, remark,
                EAnswerStauts.PASS.getCode());
        } else {
            answerBO.refreshStatus(code, updater, remark,
                EAnswerStauts.FAILED.getCode());
        }
    }

    @Override
    public void releaseAnswer(String code, String updater) {
        Answer data = answerBO.getAnswer(code);
        String status = data.getStatus();
        if (!EAnswerStauts.PASS.getCode().equals(status)) {
            throw new BizException(EBizErrorCode.DEFAULT.getCode(),
                "该回复模版状态不能上架");
        }
        answerBO.refreshStatus(code, updater, "上架回复模版",
            EAnswerStauts.ON.getCode());
    }

    @Override
    public void obtainAnswer(String code, String updater) {
        Answer data = answerBO.getAnswer(code);
        String status = data.getStatus();
        if (!EAnswerStauts.ON.getCode().equals(status)) {
            throw new BizException(EBizErrorCode.DEFAULT.getCode(),
                "该回复模版状态不能下架");
        }
        answerBO.refreshStatus(code, updater, "下架回复模版",
            EAnswerStauts.OFF.getCode());

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
