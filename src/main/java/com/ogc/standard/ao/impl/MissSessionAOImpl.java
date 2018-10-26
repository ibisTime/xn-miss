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

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ogc.standard.ao.IMissSessionAO;
import com.ogc.standard.bo.IMissSessionBO;
import com.ogc.standard.bo.IQuestionBO;
import com.ogc.standard.bo.ISYSConfigBO;
import com.ogc.standard.bo.IUserBO;
import com.ogc.standard.bo.base.Paginable;
import com.ogc.standard.common.SysConstant;
import com.ogc.standard.domain.MissSession;
import com.ogc.standard.domain.Question;
import com.ogc.standard.domain.SYSConfig;
import com.ogc.standard.domain.User;
import com.ogc.standard.enums.EMissSessionType;
import com.ogc.standard.enums.ESysUser;

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

    @Autowired
    private ISYSConfigBO sysConfigBO;

    public String addMissSession(String user1) {
        String code = missSessionBO.saveSession(
            EMissSessionType.COMMIT_QUESTION.getCode(), user1);
        SYSConfig value = sysConfigBO.getConfigValue(SysConstant.FIRST_CHAT);
        // 新增第一条消息
        questionBO
            .saveSms(code, ESysUser.SYS_USER.getCode(), value.getCvalue());
        return code;
    }

    @Override
    public Paginable<MissSession> querySessionPage(int start, int limit,
            MissSession condition) {
        if (StringUtils.isNotBlank(condition.getUser1())) {
            MissSession sessionByUser1 = missSessionBO
                .getSessionByUser1(condition.getUser1());
            if (null == sessionByUser1) {
                addMissSession(condition.getUser1());
            }
        }
        Paginable<MissSession> page = missSessionBO.getPaginable(start, limit,
            condition);
        for (MissSession missSession : page.getList()) {
            initSession(missSession);
        }
        return page;
    }

    @Override
    public MissSession getSession(String code) {
        MissSession data = missSessionBO.getSession(code);
        initSession(data);
        return data;
    }

    @Override
    public List<MissSession> querySessionList(MissSession condition) {
        List<MissSession> missSessionList = missSessionBO
            .querySessionList(condition);
        for (MissSession missSession : missSessionList) {
            initSession(missSession);
        }
        return missSessionList;
    }

    private void initSession(MissSession missSession) {
        List<Question> querySessionQuestions = questionBO
            .querySessionQuestions(missSession.getCode(), null);
        missSession.setQuestionsList(querySessionQuestions);
        if (!"UCOIN201700000000000001".equals(missSession.getUser1())) {
            User user = userBO.getUser(missSession.getUser1());
            missSession.setUser1Nickname(user.getNickname());
            missSession.setMobile(user.getMobile());
        }
    }

}
