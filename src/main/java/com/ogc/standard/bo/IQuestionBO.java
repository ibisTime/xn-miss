/**
 * @Title IQuestionBO.java 
 * @Package com.ogc.standard.bo 
 * @Description 
 * @author taojian  
 * @date 2018年10月10日 下午7:59:11 
 * @version V1.0   
 */
package com.ogc.standard.bo;

import java.util.List;

import com.ogc.standard.bo.base.IPaginableBO;
import com.ogc.standard.domain.Question;

/** 
 * @author: taojian 
 * @since: 2018年10月10日 下午7:59:11 
 * @history:
 */
public interface IQuestionBO extends IPaginableBO<Question> {

    public void saveSms(String sessionCode, String userId, String content);

    // 状态改为已读
    public void refreshStatus(long id);

    public List<Question> querySessionQuestions(String sessionCode);

    public boolean isSessionEmpty(String sessionCode);

    // 将List按照日期排序

    public void ListSort(List<Question> list);

}
