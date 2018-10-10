/**
 * @Title SessionDAOImpl.java 
 * @Package com.ogc.standard.dao.impl 
 * @Description 
 * @author taojian  
 * @date 2018年10月10日 下午3:49:30 
 * @version V1.0   
 */
package com.ogc.standard.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.ogc.standard.dao.IMissSessionDAO;
import com.ogc.standard.dao.base.support.AMybatisTemplate;
import com.ogc.standard.domain.MissSession;

/** 
 * @author: taojian 
 * @since: 2018年10月10日 下午3:49:30 
 * @history:
 */
@Repository("missSessionDAOImpl")
public class MissSessionDAOImpl extends AMybatisTemplate implements
        IMissSessionDAO {

    @Override
    public int insert(MissSession data) {
        return super.insert(NAMESPACE.concat("insert_session"), data);
    }

    @Override
    public int delete(MissSession data) {
        return 0;
    }

    @Override
    public MissSession select(MissSession condition) {
        return super.select(NAMESPACE.concat("select_session"), condition,
            MissSession.class);
    }

    @Override
    public long selectTotalCount(MissSession condition) {
        return super.selectTotalCount(NAMESPACE.concat("select_session_count"),
            condition);
    }

    @Override
    public List<MissSession> selectList(MissSession condition) {
        return super.selectList(NAMESPACE.concat("select_session"), condition,
            MissSession.class);
    }

    @Override
    public List<MissSession> selectList(MissSession condition, int start,
            int count) {
        return super.selectList(NAMESPACE.concat("select_session"), start,
            count, condition, MissSession.class);
    }

    @Override
    public int updateUnreadSum(MissSession data) {
        return super.update(NAMESPACE.concat("update_unread_sum"), data);
    }

}
