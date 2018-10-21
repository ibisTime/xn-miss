/**
 * @Title ISessionBO.java 
 * @Package com.ogc.standard.bo 
 * @Description 
 * @author taojian  
 * @date 2018年10月10日 下午4:01:12 
 * @version V1.0   
 */
package com.ogc.standard.bo;

import java.util.List;

import com.ogc.standard.bo.base.IPaginableBO;
import com.ogc.standard.domain.MissSession;

/** 
 * @author: taojian 
 * @since: 2018年10月10日 下午4:01:12 
 * @history:
 */
public interface IMissSessionBO extends IPaginableBO<MissSession> {

    public List<MissSession> querySessionList(MissSession condition);

    public MissSession getSession(String code);

    public String saveSession(String type, String user1);

    public void addUnreadSum(MissSession data);

    public void resetUnreadSum(MissSession data);
}
