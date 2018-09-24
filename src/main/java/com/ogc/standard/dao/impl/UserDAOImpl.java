/**
 * @Title UserDAOImpl.java 
 * @Package com.ibis.pz.impl 
 * @Description 
 * @author miyb  
 * @date 2015-2-6 上午10:22:53 
 * @version V1.0   
 */
package com.ogc.standard.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.ogc.standard.dao.IUserDAO;
import com.ogc.standard.dao.base.support.AMybatisTemplate;
import com.ogc.standard.domain.User;

/** 
 * 
 * @author: dl 
 * @since: 2018年8月20日 上午10:57:07 
 * @history:
 */
@Repository("userDAOImpl")
public class UserDAOImpl extends AMybatisTemplate implements IUserDAO {

    @Override
    public int insert(User data) {
        return super.insert(NAMESPACE.concat("insert_user"), data);
    }

    @Override
    public int delete(User data) {
        return 0;
    }

    @Override
    public User select(User condition) {
        return super.select(NAMESPACE.concat("select_user"), condition,
            User.class);
    }

    @Override
    public long selectTotalCount(User condition) {
        return super.selectTotalCount(NAMESPACE.concat("select_user_count"),
            condition);
    }

    @Override
    public List<User> selectList(User condition) {
        return super.selectList(NAMESPACE.concat("select_user"), condition,
            User.class);
    }

    @Override
    public List<User> selectList(User condition, int start, int count) {
        return super.selectList(NAMESPACE.concat("select_user"), start, count,
            condition, User.class);
    }

    @Override
    public int updateIdentity(User data) {
        return super.update(NAMESPACE.concat("update_identity"), data);
    }

    @Override
    public int updateRealName(User data) {
        return super.update(NAMESPACE.concat("update_real_name"), data);
    }

    @Override
    public int updateTradePwd(User data) {
        return super.update(NAMESPACE.concat("update_trade_pwd"), data);
    }

    @Override
    public int updateLoginPwd(User data) {
        return super.update(NAMESPACE.concat("update_login_pwd"), data);
    }

    @Override
    public int updateRespArea(User data) {
        return super.update(NAMESPACE.concat("update_resp_area"), data);
    }

    @Override
    public int updateMobile(User data) {
        return super.update(NAMESPACE.concat("update_bind_mobile"), data);
    }

    @Override
    public int updateStatus(User data) {
        return super.update(NAMESPACE.concat("update_status"), data);
    }

    @Override
    public int updateNickname(User data) {
        return super.update(NAMESPACE.concat("update_user_nickname"), data);
    }

    @Override
    public int updatePhoto(User data) {
        return super.update(NAMESPACE.concat("update_user_photo"), data);
    }

    @Override
    public int updateSupple(User data) {
        return super.update(NAMESPACE.concat("update_user_supple"), data);
    }

    @Override
    public int updateLevel(User data) {
        return super.update(NAMESPACE.concat("update_level"), data);
    }

    @Override
    public int updateLocation(User data) {
        return super.update(NAMESPACE.concat("update_location"), data);
    }

    @Override
    public int updateReferee(User data) {
        return super.update(NAMESPACE.concat("update_referee"), data);
    }

    @Override
    public int updateLastLogin(User data) {
        return super.update(NAMESPACE.concat("update_lastLogin"), data);
    }

    @Override
    public int updateGoogleSecret(User data) {
        return super.update(NAMESPACE.concat("update_googleSecret"), data);
    }

    @Override
    public int updateTradeRate(User data) {
        return super.update(NAMESPACE.concat("update_trade_rate"), data);
    }

    @Override
    public int updateEmail(User data) {
        return super.update(NAMESPACE.concat("update_email"), data);
    }

}
