/**
 * @Title AnswerDAOImpl.java 
 * @Package com.ogc.standard.dao.impl 
 * @Description 
 * @author taojian  
 * @date 2018年10月11日 下午1:16:44 
 * @version V1.0   
 */
package com.ogc.standard.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.ogc.standard.dao.IAnswerDAO;
import com.ogc.standard.dao.base.support.AMybatisTemplate;
import com.ogc.standard.domain.Answer;

/** 
 * @author: taojian 
 * @since: 2018年10月11日 下午1:16:44 
 * @history:
 */
@Repository("answerDAOImpl")
public class AnswerDAOImpl extends AMybatisTemplate implements IAnswerDAO {

    @Override
    public int insert(Answer data) {
        return super.insert(NAMESPACE.concat("insert_answer"), data);
    }

    @Override
    public int delete(Answer data) {
        return 0;
    }

    @Override
    public Answer select(Answer condition) {
        return super.select(NAMESPACE.concat("select_answer"), condition,
            Answer.class);
    }

    @Override
    public long selectTotalCount(Answer condition) {
        return super.selectTotalCount(NAMESPACE.concat("select_answer_count"),
            condition);
    }

    @Override
    public List<Answer> selectList(Answer condition) {
        return super.selectList(NAMESPACE.concat("select_answer"), condition,
            Answer.class);
    }

    @Override
    public List<Answer> selectList(Answer condition, int start, int count) {
        return super.selectList(NAMESPACE.concat("select_answer"), start,
            count, condition, Answer.class);
    }

    @Override
    public int updateStatus(Answer data) {
        return super.update(NAMESPACE.concat("update_status"), data);
    }

    @Override
    public int updateAnswer(Answer data) {
        return super.update(NAMESPACE.concat("update_answer"), data);
    }

}
