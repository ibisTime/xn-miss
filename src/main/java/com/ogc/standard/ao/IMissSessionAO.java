/**
 * @Title ISessionAO.java 
 * @Package com.ogc.standard.ao 
 * @Description 
 * @author taojian  
 * @date 2018年10月10日 下午4:31:07 
 * @version V1.0   
 */
package com.ogc.standard.ao;

import java.util.List;

import com.ogc.standard.bo.base.Paginable;
import com.ogc.standard.domain.MissSession;

/** 
 * @author: taojian 
 * @since: 2018年10月10日 下午4:31:07 
 * @history:
 */
public interface IMissSessionAO {

    public String addMissSession(String user1);

    public Paginable<MissSession> querySessionPage(int start, int limit,
            MissSession condition);

    public MissSession getSession(String code);

    public List<MissSession> querySessionList(MissSession condition);
}
