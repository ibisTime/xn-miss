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
import com.ogc.standard.bo.IQuestionBO;
import com.ogc.standard.bo.IUserBO;
import com.ogc.standard.bo.base.Paginable;
import com.ogc.standard.domain.MissSession;
import com.ogc.standard.domain.Question;

/** 
 * @author: taojian 
 * @since: 2018年10月10日 下午4:34:25 
 * @history:
 */
@Service
public class MissSessionAOImpl implements IMissSessionAO {

    @Autowired
    private IMissSessionBO missSessionBO;

    @Autowired
    private IQuestionBO questionBO;

    @Autowired
    private IUserBO userBO;

    @Override
    public String addMissSession(String type, String user1) {
        userBO.getUser(user1);
        return missSessionBO.saveSession(type, user1);
    }

    @Override
    public Paginable<MissSession> querySessionPage(int start, int limit,
            MissSession condition) {
        Paginable<MissSession> page = missSessionBO.getPaginable(start, limit,
            condition);
        for (MissSession missSession : page.getList()) {
            missSession.setDataList(questionBO
                .querySessionQuestions(missSession.getCode()));
            missSession.setUser1Nickname(userBO.getUser(missSession.getUser1())
                .getNickname());
        }
        return page;
    }

    @Override
    public MissSession getSession(String code) {
        MissSession data = missSessionBO.getSession(code);
        List<Question> dataList = questionBO.querySessionQuestions(code);
        data.setDataList(dataList);
        data.setUser1Nickname(userBO.getUser(data.getUser1()).getNickname());
        return data;
    }

    @Override
    public List<MissSession> querySessionList(MissSession condition) {
        List<MissSession> missSessionList = missSessionBO
            .querySessionList(condition);
        for (MissSession missSession : missSessionList) {
            missSession.setDataList(questionBO
                .querySessionQuestions(missSession.getCode()));
            missSession.setUser1Nickname(userBO.getUser(missSession.getUser1())
                .getNickname());
        }
        return missSessionList;
    }

}
