/**
 * @Title IAnswerBO.java 
 * @Package com.ogc.standard.bo 
 * @Description 
 * @author taojian  
 * @date 2018年10月11日 下午1:22:22 
 * @version V1.0   
 */
package com.ogc.standard.bo;

import java.util.List;

import com.ogc.standard.bo.base.IPaginableBO;
import com.ogc.standard.domain.Answer;

/** 
 * @author: taojian 
 * @since: 2018年10月11日 下午1:22:22 
 * @history:
 */
public interface IAnswerBO extends IPaginableBO<Answer> {

    public String saveAnswer(String question, String answer, String updater,
            String remark, String status);

    public void refreshAnswer(Answer data, String question, String answer,
            String updater, String remark, String status);

    public void refreshStatus(String code, String updater, String remark,
            String status);

    public List<Answer> queryAnswerList(Answer condition);

    public Answer getAnswer(String code);
}
