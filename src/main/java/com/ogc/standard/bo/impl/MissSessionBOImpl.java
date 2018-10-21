/**
 * @Title SessionBOImpl.java 
 * @Package com.ogc.standard.bo.impl 
 * @Description 
 * @author taojian  
 * @date 2018年10月10日 下午4:07:33 
 * @version V1.0   
 */
package com.ogc.standard.bo.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ogc.standard.bo.IMissSessionBO;
import com.ogc.standard.bo.base.PaginableBOImpl;
import com.ogc.standard.core.OrderNoGenerater;
import com.ogc.standard.dao.IMissSessionDAO;
import com.ogc.standard.domain.MissSession;
import com.ogc.standard.enums.EGeneratePrefix;
import com.ogc.standard.enums.ESysUser;
import com.ogc.standard.exception.BizException;
import com.ogc.standard.exception.EBizErrorCode;

/** 
 * @author: taojian 
 * @since: 2018年10月10日 下午4:07:33 
 * @history:
 */
@Component
public class MissSessionBOImpl extends PaginableBOImpl<MissSession> implements
        IMissSessionBO {

    @Autowired
    private IMissSessionDAO missSessionDAO;

    @Override
    public List<MissSession> querySessionList(MissSession condition) {
        return missSessionDAO.selectList(condition);
    }

    @Override
    public MissSession getSession(String code) {
        MissSession condition = new MissSession();
        condition.setCode(code);
        MissSession session = missSessionDAO.select(condition);
        if (null == session) {
            throw new BizException(EBizErrorCode.DEFAULT.getCode(), "该会话不存在");
        }
        return session;
    }

    @Override
    public String saveSession(String type, String user1) {
        MissSession session = new MissSession();
        String code = OrderNoGenerater.generate(EGeneratePrefix.session
            .getCode());
        session.setCode(code);
        session.setType(type);
        session.setUser1(user1);
        session.setUser2(ESysUser.SYS_USER.getCode());
        session.setCreateDatetime(new Date());
        session.setUnreadSum(Long.valueOf(0));
        missSessionDAO.insert(session);
        return code;
    }

    @Override
    public void addUnreadSum(MissSession data) {
        data.setUnreadSum(data.getUnreadSum() + 1);
        missSessionDAO.updateUnreadSum(data);
    }

    @Override
    public void resetUnreadSum(MissSession data) {
        data.setUnreadSum(Long.valueOf(0));
        missSessionDAO.updateUnreadSum(data);
    }

}
