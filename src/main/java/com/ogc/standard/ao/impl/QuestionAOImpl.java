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
import com.ogc.standard.enums.ESysUser;

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
    public void send(String sessionCode, String user1, String content) {
        MissSession missSession = missSessionBO.getSession(sessionCode);
        List<Question> questionList = questionBO.querySessionQuestions(
            sessionCode, missSession.getUser2());
        if (CollectionUtils.isNotEmpty(questionList)) {
            for (Question question : questionList) {
                questionBO.refreshStatus(question.getId());
            }
        }
        questionBO.saveSms(sessionCode, user1, content);
        missSessionBO.updateUnreadSum(missSession, 1);
    }

    @Override
    @Transactional
    public void read(String sessionCode, String userId) {
        MissSession missSession = missSessionBO.getSession(sessionCode);
        String toUserId = "";
        if (missSession.getUser1().equals(userId)) {
            toUserId = missSession.getUser2();
            missSessionBO.updateUnreadSum(missSession, 3);
        } else {
            toUserId = missSession.getUser1();
            missSessionBO.updateUnreadSum(missSession, 4);
        }
        List<Question> questionList = questionBO.querySessionQuestions(
            sessionCode, toUserId);

        if (CollectionUtils.isNotEmpty(questionList)) {
            for (Question question : questionList) {
                questionBO.refreshStatus(question.getId());
            }
        }
    }

    @Override
    @Transactional
    public void reply(String sessionCode, String content) {
        MissSession missSession = missSessionBO.getSession(sessionCode);
        List<Question> questionList = questionBO.querySessionQuestions(
            sessionCode, missSession.getUser1());
        if (CollectionUtils.isNotEmpty(questionList)) {
            // 拉取会话并按时间排序
            questionBO.ListSort(questionList);
            for (Question question : questionList) {
                questionBO.refreshStatus(question.getId());
            }
            questionBO.saveSms(sessionCode, ESysUser.SYS_USER.getCode(),
                content);
            missSessionBO.updateUnreadSum(missSession, 2);
        }

    }

}
