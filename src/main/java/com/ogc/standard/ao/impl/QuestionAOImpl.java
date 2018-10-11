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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ogc.standard.ao.IQuestionAO;
import com.ogc.standard.bo.IMissSessionBO;
import com.ogc.standard.bo.IQuestionBO;
import com.ogc.standard.domain.Question;
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
    public void reply(String sessionCode, String content) {
        // 会话不为空
        if (!questionBO.isSessionEmpty(sessionCode)) {
            int i = 0;
            // 拉取会话并按时间排序
            List<Question> questionList = questionBO
                .querySessionQuestions(sessionCode);
            questionBO.ListSort(questionList);
            // 如果上一个消息不是sys_user的，for循环改状态为已读直到sys_user为止
            if (!questionList.get(i).getUserId()
                .equals(ESysUser.SYS_USER.getCode())) {
                for (i = 0; !questionList.get(i).getUserId()
                    .equals(ESysUser.SYS_USER.getCode()); i++) {
                    questionBO.refreshStatus(questionList.get(i).getId());
                }
            }
            // 落地数据
            questionBO.saveSms(sessionCode, ESysUser.SYS_USER.getCode(),
                content);
            // 未读数量归零
            missSessionBO.resetUnreadSum(sessionCode);
        } else {
            throw new BizException(EBizErrorCode.DEFAULT.getCode(), "没有问题可以回复");
        }

    }

    @Override
    public void send(String sessionCode, String user1, String content) {
        if (!questionBO.isSessionEmpty(sessionCode)) {
            int i = 0;
            List<Question> questionList = questionBO
                .querySessionQuestions(sessionCode);
            questionBO.ListSort(questionList);
            if (!questionList.get(i).getUserId().equals(user1)) {
                for (i = 0; !questionList.get(i).getUserId().equals(user1); i++) {
                    questionBO.refreshStatus(questionList.get(i).getId());
                }
            }

        }
        questionBO.saveSms(sessionCode, user1, content);
        // 未读数量加一
        missSessionBO.addUnreadSum(sessionCode);

    }

}
