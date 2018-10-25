/**
 * @Title QuestionAOImpl.java 
 * @Package com.ogc.standard.ao.impl 
 * @Description 
 * @author taojian  
 * @date 2018年10月10日 下午8:38:24 
 * @version V1.0   
 */
package com.ogc.standard.ao.impl;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ogc.standard.ao.IQuestionAO;
import com.ogc.standard.bo.IMissSessionBO;
import com.ogc.standard.bo.IQuestionBO;
import com.ogc.standard.domain.MissSession;
import com.ogc.standard.domain.Question;
import com.ogc.standard.enums.EQuestionStatus;
import com.ogc.standard.enums.ESysUser;
import com.ogc.standard.exception.BizException;
import com.ogc.standard.exception.EBizErrorCode;

/** 
 * @author: taojian 
 * @since: 2018年10月10日 下午8:38:24 
 * @history:
 */
@Service
public class QuestionAOImpl implements IQuestionAO {

    @Autowired
    private IQuestionBO questionBO;

    @Autowired
    private IMissSessionBO missSessionBO;

    @Override
    @Transactional
    public void reply(String sessionCode, String content) {
        MissSession missSession = missSessionBO.getSession(sessionCode);
        // 会话不为空
        if (!questionBO.isSessionEmpty(missSession.getCode())) {
            int i = 0;
            // 拉取会话并按时间排序
            List<Question> questionList = questionBO
                .querySessionQuestions(sessionCode);
            questionBO.ListSort(questionList);
            // 如果上一个消息不是sys_user的，for循环改状态为已读直到sys_user为止
            if (!ESysUser.SYS_USER.getCode().equals(
                questionList.get(i).getUserId())) {
                for (i = 0; i < questionList.size(); i++) {
                    if (!questionList.get(i).getUserId()
                        .equals(ESysUser.SYS_USER.getCode())) {
                        questionBO.refreshStatus(questionList.get(i).getId());
                    }
                }
            }
            // 落地数据
            questionBO.saveSms(sessionCode, ESysUser.SYS_USER.getCode(),
                content);
            // 未读数量归零
            missSessionBO.resetUnreadSum(missSession);
        } else {
            throw new BizException(EBizErrorCode.DEFAULT.getCode(), "没有问题可以回复");
        }

    }

    @Override
    @Transactional
    public void send(String sessionCode, String user1, String content) {
        MissSession missSession = missSessionBO.getSession(sessionCode);
        long count = 0L;
        List<Question> questionList = questionBO
            .querySessionQuestions(sessionCode);
        if (CollectionUtils.isNotEmpty(questionList)) {
            int size = questionList.size();
            for (Question question : questionList) {
                if (question.getUserId() != user1
                        && EQuestionStatus.TO_READ.getCode().equals(
                            question.getStatus())) {
                    questionBO.refreshStatus(question.getId());
                    size -= 1;
                }
            }
            count = (long) size;
        }
        questionBO.saveSms(sessionCode, user1, content);
        // 未读数量加一
        missSessionBO.addUnreadSum(missSession, count);
    }

}
