/**
 * @Title IQuestionDAO.java 
 * @Package com.ogc.standard.dao 
 * @Description 
 * @author taojian  
 * @date 2018年10月10日 下午7:43:13 
 * @version V1.0   
 */
package com.ogc.standard.dao;

import com.ogc.standard.dao.base.IBaseDAO;
import com.ogc.standard.domain.Question;

/** 
 * @author: taojian 
 * @since: 2018年10月10日 下午7:43:13 
 * @history:
 */
public interface IQuestionDAO extends IBaseDAO<Question> {
    String NAMESPACE = IQuestionDAO.class.getName().concat(".");

    public int updateStatus(Question data);
}
