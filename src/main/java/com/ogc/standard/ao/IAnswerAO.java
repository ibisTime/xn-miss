/**
 * @Title IAnswerAO.java 
 * @Package com.ogc.standard.ao 
 * @Description 
 * @author taojian  
 * @date 2018年10月11日 下午1:45:47 
 * @version V1.0   
 */
package com.ogc.standard.ao;

import java.util.List;

import com.ogc.standard.bo.base.Paginable;
import com.ogc.standard.domain.Answer;

/** 
 * @author: taojian 
 * @since: 2018年10月11日 下午1:45:47 
 * @history:
 */
public interface IAnswerAO {

    public String addAnswer(String question, String answer, String updater,
            String remark);

    public void editAnswer(String code, String question, String answer,
            String updater, String remark, String bizType);

    public void dorpAnswer(String code);

    public Paginable<Answer> queryAnswerPage(int start, int limit,
            Answer condition);

    public Answer getAnswer(String code);

    public List<Answer> queryAnswerList(Answer condition);
}
