/**
 * @Title QuestionBOImpl.java 
 * @Package com.ogc.standard.bo.impl 
 * @Description 
 * @author taojian  
 * @date 2018年10月10日 下午8:15:16 
 * @version V1.0   
 */
package com.ogc.standard.bo.impl;

import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ogc.standard.bo.IQuestionBO;
import com.ogc.standard.bo.base.PaginableBOImpl;
import com.ogc.standard.dao.IQuestionDAO;
import com.ogc.standard.domain.Question;
import com.ogc.standard.enums.EQuestionStatus;

/** 
 * @author: taojian 
 * @since: 2018年10月10日 下午8:15:16 
 * @history:
 */
@Component
public class QuestionBOImpl extends PaginableBOImpl<Question> implements
        IQuestionBO {

    @Autowired
    private IQuestionDAO questionDAO;

    @Override
    public void saveSms(String sessionCode, String userId, String content) {
        Question data = new Question();
        data.setSessionCode(sessionCode);
        data.setUserId(userId);
        data.setContent(content);
        data.setStatus(EQuestionStatus.TO_READ.getCode());
        data.setCreateDatetime(new Date());
        questionDAO.insert(data);
    }

    @Override
    public void refreshStatus(long id) {
        if (id != 0) {
            Question condition = new Question();
            condition.setId(id);
            Question data = questionDAO.select(condition);
            data.setStatus(EQuestionStatus.READ.getCode());
            questionDAO.updateStatus(data);
        }
    }

    @Override
    public List<Question> querySessionQuestions(String sessionCode,
            String userId) {
        Question condition = new Question();
        condition.setSessionCode(sessionCode);
        condition.setUserId(userId);
        return questionDAO.selectList(condition);
    }

    // 将List按照日期排序
    @Override
    public void ListSort(List<Question> list) {
        Collections.sort(list, new Comparator<Question>() {
            @Override
            public int compare(Question o1, Question o2) {

                if (o1.getCreateDatetime().getTime() > o2.getCreateDatetime()
                    .getTime()) {
                    return -1;
                } else if (o1.getCreateDatetime().getTime() < o2
                    .getCreateDatetime().getTime()) {
                    return 1;
                } else {
                    return 0;
                }
            }
        });
    }

}
