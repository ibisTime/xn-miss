/**
 * @Title IAnswerDAO.java 
 * @Package com.ogc.standard.dao 
 * @Description 
 * @author taojian  
 * @date 2018年10月11日 下午1:14:30 
 * @version V1.0   
 */
package com.ogc.standard.dao;

import com.ogc.standard.dao.base.IBaseDAO;
import com.ogc.standard.domain.Answer;

/** 
 * @author: taojian 
 * @since: 2018年10月11日 下午1:14:30 
 * @history:
 */
public interface IAnswerDAO extends IBaseDAO<Answer> {
    String NAMESPACE = IAnswerDAO.class.getName().concat(".");

    public int updateStatus(Answer data);

    public int updateAnswer(Answer data);
}
