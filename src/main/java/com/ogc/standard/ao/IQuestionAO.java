/**
 * @Title IQuestionAO.java 
 * @Package com.ogc.standard.ao 
 * @Description 
 * @author taojian  
 * @date 2018年10月10日 下午8:33:34 
 * @version V1.0   
 */
package com.ogc.standard.ao;

/** 
 * @author: taojian 
 * @since: 2018年10月10日 下午8:33:34 
 * @history:
 */
public interface IQuestionAO {

    public void reply(String sessionCode, String content);

    public void send(String sessionCode, String user1, String content);
}
