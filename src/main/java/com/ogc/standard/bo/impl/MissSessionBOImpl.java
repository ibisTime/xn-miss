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

import org.apache.commons.lang3.StringUtils;
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
        session.setUser1UnreadSum(Long.valueOf(1));
        session.setUser2UnreadSum(0L);
        missSessionDAO.insert(session);
        return code;
    }

    @Override
    public void updateUnreadSum(MissSession data, int i) {
        if (1 == i) {
            data.setUser1UnreadSum(0L);
            data.setUser2UnreadSum(getLong(data.getUser2UnreadSum()) + 1);
        }
        if (2 == i) {
            data.setUser1UnreadSum(getLong(data.getUser1UnreadSum()) + 1);
            data.setUser2UnreadSum(0L);
        }
        missSessionDAO.updateUnreadSum(data);
    }

    @Override
    public MissSession getSessionByUser1(String user1) {
        MissSession data = null;
        if (StringUtils.isNotBlank(user1)) {
            MissSession condition = new MissSession();
            condition.setUser1(user1);
            data = missSessionDAO.select(condition);
        }
        return data;
    }

    private Long getLong(Object obj) {
        if (null == obj) {
            return 0L;
        } else {
            return (Long) obj;
        }
    }

}
