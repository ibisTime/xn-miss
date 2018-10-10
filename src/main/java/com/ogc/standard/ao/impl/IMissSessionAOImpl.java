/**
 * @Title ISessionAOImpl.java 
 * @Package com.ogc.standard.ao.impl 
 * @Description 
 * @author taojian  
 * @date 2018年10月10日 下午4:34:25 
 * @version V1.0   
 */
package com.ogc.standard.ao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ogc.standard.ao.IMissSessionAO;
import com.ogc.standard.bo.IMissSessionBO;
import com.ogc.standard.bo.base.Paginable;
import com.ogc.standard.domain.MissSession;

/** 
 * @author: taojian 
 * @since: 2018年10月10日 下午4:34:25 
 * @history:
 */
@Service
public class IMissSessionAOImpl implements IMissSessionAO {

    @Autowired
    private IMissSessionBO missSessionBO;

    @Override
    public String addMissSession(String type, String user1) {
        return missSessionBO.saveSession(type, user1);
    }

    @Override
    public Paginable<MissSession> querySessionPage(int start, int limit,
            MissSession condition) {
        Paginable<MissSession> page = missSessionBO.getPaginable(start, limit,
            condition);
        return page;
    }

    @Override
    public MissSession getSession(String code) {
        return missSessionBO.getSession(code);
    }

    @Override
    public List<MissSession> querySessionList(MissSession condition) {
        List<MissSession> missSessionList = missSessionBO
            .querySessionList(condition);
        return missSessionList;
    }

}
