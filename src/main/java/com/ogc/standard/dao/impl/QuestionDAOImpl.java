/**
 * @Title QuestionDAOImpl.java 
 * @Package com.ogc.standard.dao.impl 
 * @Description 
 * @author taojian  
 * @date 2018年10月10日 下午7:45:52 
 * @version V1.0   
 */
package com.ogc.standard.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.ogc.standard.dao.IQuestionDAO;
import com.ogc.standard.dao.base.support.AMybatisTemplate;
import com.ogc.standard.domain.Question;

/** 
 * @author: taojian 
 * @since: 2018年10月10日 下午7:45:52 
 * @history:
 */
@Repository("questionDAOImpl")
public class QuestionDAOImpl extends AMybatisTemplate implements IQuestionDAO {

    @Override
    public int insert(Question data) {
        return super.insert(NAMESPACE.concat("insert_question"), data);
    }

    @Override
    public int delete(Question data) {
        return 0;
    }

    @Override
    public Question select(Question condition) {
        return super.select(NAMESPACE.concat("select_question"), condition,
            Question.class);
    }

    @Override
    public long selectTotalCount(Question condition) {
        return super.selectTotalCount(
            NAMESPACE.concat("select_question_count"), condition);
    }

    @Override
    public List<Question> selectList(Question condition) {
        return super.selectList(NAMESPACE.concat("select_question"), condition,
            Question.class);
    }

    @Override
    public List<Question> selectList(Question condition, int start, int count) {
        return super.selectList(NAMESPACE.concat("select_question"), start,
            count, condition, Question.class);
    }

    @Override
    public int updateStatus(Question data) {
        return update(NAMESPACE.concat("update_status"), data);
    }

}
