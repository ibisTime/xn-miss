/**
 * @Title UserBOImpl.java 
 * @Package com.ibis.pz.impl 
 * @Description 
 * @author miyb  
 * @date 2015-3-7 上午9:21:25 
 * @version V1.0   
 */
package com.ogc.standard.bo.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ogc.standard.bo.ISYSConfigBO;
import com.ogc.standard.bo.IUserBO;
import com.ogc.standard.bo.base.PaginableBOImpl;
import com.ogc.standard.common.MD5Util;
import com.ogc.standard.common.PhoneUtil;
import com.ogc.standard.common.PwdUtil;
import com.ogc.standard.core.OrderNoGenerater;
import com.ogc.standard.dao.IUserDAO;
import com.ogc.standard.domain.User;
import com.ogc.standard.enums.EErrorCode_main;
import com.ogc.standard.enums.EUserKind;
import com.ogc.standard.enums.EUserStatus;
import com.ogc.standard.exception.BizException;

/** 
 * @author: miyb 
 * @since: 2015-3-7 上午9:21:25 
 * @history:
 */
@Component
public class UserBOImpl extends PaginableBOImpl<User> implements IUserBO {
    @Autowired
    private IUserDAO userDAO;

    @Autowired
    private ISYSConfigBO sysConfigBO;

    @Override
    public void refreshWxInfo(String userId, String unionId, String h5OpenId,
            String nickname, String photo, String gender) {
        User dbUser = getUser(userId);
        dbUser.setUnionId(unionId);
        if (StringUtils.isNotBlank(h5OpenId)) {
            dbUser.setH5OpenId(h5OpenId);
        }
        dbUser.setNickname(nickname);
        dbUser.setPhoto(photo);
        dbUser.setGender(gender);
        userDAO.updateWxInfo(dbUser);
    }

    @Override
    public void isMobileExist(String mobile) {
        if (StringUtils.isNotBlank(mobile)) {
            // 判断格式
            PhoneUtil.checkMobile(mobile);
            User condition = new User();
            condition.setMobile(mobile);
            long count = getTotalCount(condition);
            if (count > 0) {
                throw new BizException(
                    EErrorCode_main.user_MOBILEEXIST.getCode());
            }
        }
    }

    @Override
    public void isEmailExist(String email) {
        if (StringUtils.isNotBlank(email)) {
            User condition = new User();
            condition.setEmail(email);
            long count = getTotalCount(condition);
            if (count > 0) {
                throw new BizException(
                    EErrorCode_main.user_EMAILEXIST.getCode());
            }
        }
    }

    @Override
    public void isNicknameExist(String nickname) {
        if (StringUtils.isNotBlank(nickname)) {
            // 判断格式
            User condition = new User();
            condition.setNickname(nickname);
            long count = getTotalCount(condition);
            if (count > 0) {
                throw new BizException(
                    EErrorCode_main.user_NICKNAMEEXIST.getCode());
            }
        }
    }

    @Override
    public String doAddUser(User data) {
        String userId = OrderNoGenerater.generate("U");
        data.setUserId(userId);
        data.setKind(EUserKind.Customer.getCode());
        data.setNickname(userId.substring(userId.length() - 8, userId.length()));
        userDAO.insert(data);
        return userId;
    }

    @Override
    public User getUserByMobile(String mobile) {
        User user = null;
        if (StringUtils.isNotBlank(mobile)) {
            User condition = new User();
            condition.setMobile(mobile);
            List<User> list = userDAO.selectList(condition);
            if (CollectionUtils.isNotEmpty(list)) {
                user = list.get(0);
            }
        }
        return user;
    }

    @Override
    public String doRegister(String mobile, String nickname, String loginPwd,
            User refereeUser, String province, String city, String area) {

        String userId = OrderNoGenerater.generate("U");
        User user = new User();
        user.setUserId(userId);
        user.setLoginName(mobile);
        user.setMobile(mobile);
        user.setKind(EUserKind.Customer.getCode());
        if (StringUtils.isNotBlank(loginPwd)) {
            user.setLoginPwd(MD5Util.md5(loginPwd));
            user.setLoginPwdStrength(PwdUtil.calculateSecurityLevel(loginPwd));
        }
        if (nickname == null) {
            user.setNickname(userId.substring(userId.length() - 8,
                userId.length()));
        }
        user.setNickname(nickname);
        if (refereeUser != null) {
            user.setUserReferee(refereeUser.getUserId());
        }
        user.setLevel(null);
        user.setStatus(EUserStatus.NORMAL.getCode());
        user.setProvince(province);
        user.setCity(city);
        user.setArea(area);
        Date date = new Date();
        user.setCreateDatetime(date);
        user.setTradeRate(0.0D);
        userDAO.insert(user);
        return userId;
    }

    @Override
    public String doRegister(String unionId, String h5OpenId, String mobile,
            String kind, String loginPwd, String nickname, String photo,
            String gender) {
        String userId = OrderNoGenerater.generate("U");
        User user = new User();
        user.setUserId(userId);
        user.setUnionId(unionId);
        user.setH5OpenId(h5OpenId);

        user.setLoginName(mobile);
        user.setMobile(mobile);
        user.setKind(kind);
        user.setLoginPwd(MD5Util.md5(loginPwd));
        user.setLoginPwdStrength(PwdUtil.calculateSecurityLevel(loginPwd));

        user.setNickname(nickname);
        user.setPhoto(photo);
        user.setGender(gender);
        user.setLevel(null);
        user.setStatus(EUserStatus.NORMAL.getCode());

        user.setCreateDatetime(new Date());
        userDAO.insert(user);
        return userId;
    }

    @Override
    public String saveUser(String mobile, String kind, String companyCode,
            String systemCode) {
        String userId = null;
        if (StringUtils.isNotBlank(mobile) && StringUtils.isNotBlank(kind)) {
            userId = OrderNoGenerater.generate("U");
            User data = new User();
            data.setUserId(userId);
            data.setMobile(mobile);
            userDAO.insert(data);
        }
        return userId;
    }

    @Override
    public int refreshIdentity(String userId, String realName, String idKind,
            String idNo) {
        User data = new User();
        data.setUserId(userId);
        data.setRealName(realName);
        data.setIdKind(idKind);
        data.setIdNo(idNo);
        int count = 0;
        if (data != null && StringUtils.isNotBlank(data.getUserId())) {
            count = userDAO.updateIdentity(data);
        }
        return count;
    }

    @Override
    public int refreshIdentity(String userId, String realName, String idKind,
            String idNo, String idFace, String idOppo, String idHold) {
        User data = new User();
        data.setUserId(userId);
        data.setRealName(realName);
        data.setIdKind(idKind);
        data.setIdNo(idNo);
        data.setIdFace(idFace);
        data.setIdOppo(idOppo);
        data.setIdHold(idHold);
        int count = 0;
        if (data != null && StringUtils.isNotBlank(data.getUserId())) {
            count = userDAO.updateIdentity(data);
        }
        return count;

    }

    @Override
    public int refreshRealName(String userId, String realName) {
        User data = new User();
        data.setUserId(userId);
        data.setRealName(realName);
        int count = 0;
        if (data != null && StringUtils.isNotBlank(data.getUserId())) {
            count = userDAO.updateRealName(data);
        }
        return count;
    }

    @Override
    public int refreshTradePwd(String userId, String tradePwd) {
        int count = 0;
        if (StringUtils.isNotBlank(userId)) {
            User data = new User();
            data.setUserId(userId);
            data.setTradePwd(MD5Util.md5(tradePwd));
            data.setTradePwdStrength(PwdUtil.calculateSecurityLevel(tradePwd));
            count = userDAO.updateTradePwd(data);
        }
        return count;
    }

    @Override
    public User getUser(String userId) {
        User data = null;
        if (StringUtils.isNotBlank(userId)) {
            User condition = new User();
            condition.setUserId(userId);
            data = userDAO.select(condition);
        }
        if (data == null) {
            throw new BizException(
                EErrorCode_main.user_USERIDUNEXIST.getCode(), (Object) userId);
        }
        return data;
    }

    @Override
    public User getNormalUser(String userId) {
        User data = null;
        if (StringUtils.isNotBlank(userId)) {
            User condition = new User();
            condition.setUserId(userId);
            data = userDAO.select(condition);
        }
        if (data == null) {
            throw new BizException(
                EErrorCode_main.user_USERIDUNEXIST.getCode(), (Object) userId);
        }
        if (!EUserStatus.NORMAL.getCode().equals(data.getStatus())) {
            throw new BizException(
                EErrorCode_main.USER_STATUS_NOT_NORMAL.getCode(),
                (Object) userId);
        }
        return data;
    }

    @Override
    public User getUserUnCheck(String userId) {
        User data = null;
        if (StringUtils.isNotBlank(userId)) {
            User condition = new User();
            condition.setUserId(userId);
            data = userDAO.select(condition);
        }

        return data;
    }

    @Override
    public List<User> getUsersByUserReferee(String userReferee) {
        List<User> userList = new ArrayList<User>();
        if (StringUtils.isNotBlank(userReferee)) {
            User condition = new User();
            condition.setUserReferee(userReferee);
            userList = userDAO.selectList(condition);
        }
        return userList;
    }

    @Override
    public List<User> queryUserList(User data) {
        return userDAO.selectList(data);
    }

    @Override
    public int refreshLoginPwd(String userId, String loginPwd) {
        int count = 0;
        if (StringUtils.isNotBlank(userId)) {
            User data = new User();
            data.setUserId(userId);
            data.setLoginPwd(MD5Util.md5(loginPwd));
            data.setLoginPwdStrength(PwdUtil.calculateSecurityLevel(loginPwd));
            count = userDAO.updateLoginPwd(data);
        }
        return count;
    }

    @Override
    public int refreshMobile(String userId, String mobile) {
        int count = 0;
        if (StringUtils.isNotBlank(userId) && StringUtils.isNotBlank(mobile)) {
            User data = new User();
            data.setUserId(userId);
            data.setMobile(mobile);
            count = userDAO.updateMobile(data);
        }
        return count;
    }

    @Override
    public boolean isUserExist(String userId) {
        User condition = new User();
        condition.setUserId(userId);
        if (userDAO.selectTotalCount(condition) > 0) {
            return true;
        }
        return false;
    }

    @Override
    public void checkTradePwd(String userId, String tradePwd) {
        if (StringUtils.isNotBlank(userId) && StringUtils.isNotBlank(tradePwd)) {
            User user = this.getNormalUser(userId);
            if (StringUtils.isBlank(user.getTradePwdStrength())) {
                throw new BizException(
                    EErrorCode_main.user_TRADEPWDFIRST.getCode());
            }
            User condition = new User();
            condition.setUserId(userId);
            condition.setTradePwd(MD5Util.md5(tradePwd));
            if (this.getTotalCount(condition) != 1) {
                throw new BizException(
                    EErrorCode_main.user_TRADEPWDISWRONG.getCode());
            }
        } else {
            throw new BizException(
                EErrorCode_main.user_TRADEPWDISWRONG.getCode());
        }
    }

    @Override
    public void checkLoginPwd(String userId, String loginPwd) {
        if (StringUtils.isNotBlank(userId) && StringUtils.isNotBlank(loginPwd)) {
            User condition = new User();
            condition.setUserId(userId);
            condition.setLoginPwd(MD5Util.md5(loginPwd));
            long count = this.getTotalCount(condition);
            if (count != 1) {
                throw new BizException(
                    EErrorCode_main.user_OLDLOGINPWDWRONG.getCode());
            }
        } else {
            throw new BizException(
                EErrorCode_main.user_OLDLOGINPWDWRONG.getCode());
        }
    }

    @Override
    public void refreshStatus(String userId, EUserStatus status,
            String updater, String remark) {
        if (StringUtils.isNotBlank(userId)) {
            User data = new User();
            data.setUserId(userId);
            data.setStatus(status.getCode());
            data.setUpdater(updater);
            data.setUpdateDatetime(new Date());
            data.setRemark(remark);
            userDAO.updateStatus(data);
        }
    }

    @Override
    public void refreshNickname(String userId, String nickname) {
        if (StringUtils.isNotBlank(userId)) {
            User data = new User();
            data.setUserId(userId);
            data.setNickname(nickname);
            userDAO.updateNickname(data);
        }
    }

    @Override
    public void refreshPhoto(String userId, String photo) {
        if (StringUtils.isNotBlank(userId)) {
            User data = new User();
            data.setUserId(userId);
            data.setPhoto(photo);
            userDAO.updatePhoto(data);
        }
    }

    @Override
    public void refreshLevel(User data) {
        userDAO.updateLevel(data);
    }

    @Override
    public List<User> queryUserList(String mobile, String kind,
            String systemCode) {
        User condition = new User();
        condition.setMobile(mobile);
        return userDAO.selectList(condition);
    }

    public Long totalUser(User condition) {
        return userDAO.selectTotalCount(condition);
    }

    @Override
    public void refreshLocation(User data) {
        if (StringUtils.isNotBlank(data.getUserId())) {
            data.setUpdateDatetime(new Date());
            userDAO.updateLocation(data);
        }

    }

    @Override
    public void refreshReferee(String userId, String userReferee, String updater) {
        if (StringUtils.isNotBlank(userId)) {
            User data = new User();
            data.setUserId(userId);
            data.setUserReferee(userReferee);
            data.setUpdater(updater);
            data.setUpdateDatetime(new Date());
            userDAO.updateReferee(data);
        }
    }

    @Override
    public void refreshLastLogin(String userId) {
        if (StringUtils.isNotBlank(userId)) {
            User data = new User();
            data.setUserId(userId);
            data.setLastLogin(new Date());
            userDAO.updateLastLogin(data);
        }
    }

    @Override
    public void refreshGoogleSecret(String userId, String secret) {
        User data = new User();
        data.setUserId(userId);
        data.setGoogleSecret(secret);
        userDAO.updateGoogleSecret(data);
    }

    @Override
    public void refreshTradeRate(String userId, Double tradeRate) {
        User data = new User();
        data.setUserId(userId);
        data.setTradeRate(tradeRate);
        userDAO.updateTradeRate(data);
    }

    @Override
    public int refreshEmail(String userId, String email) {
        int count = 0;
        if (StringUtils.isNotBlank(email) && StringUtils.isNotBlank(userId)) {
            User data = new User();
            data.setUserId(userId);
            data.setEmail(email);
            count = userDAO.updateEmail(data);
        }
        return count;
    }

    @Override
    public void refreshRespArea(String userId, String respArea, String updater) {
        User data = new User();
        data.setUserId(userId);
        data.setRespArea(respArea);
        data.setUpdater(updater);
        data.setUpdateDatetime(new Date());
        userDAO.updateRespArea(data);
    }

    @Override
    public User doGetUserByOpenId(String h5OpenId) {
        User user = null;
        if (StringUtils.isNotBlank(h5OpenId)) {
            User condition = new User();
            condition.setH5OpenId(h5OpenId);
            List<User> userList = userDAO.selectList(condition);
            if (CollectionUtils.isNotEmpty(userList)) {
                user = userList.get(0);
                if (!EUserStatus.NORMAL.getCode().equals(user.getStatus())) {
                    throw new BizException("user_lock", "用户状态异常");
                }
            }
        } else {
            throw new BizException("li01004", "第三方登录异常");
        }
        return user;
    }

    @Override
    public void doCheckOpenId(String unionId, String h5OpenId) {
        User condition = new User();
        condition.setUnionId(unionId);
        condition.setH5OpenId(h5OpenId);
        long count = getTotalCount(condition);
        if (count > 0) {
            throw new BizException("xn702002", "微信编号已存在");
        }
    }

    @Override
    public void refreshRemark(String userId, String remark) {
        if (StringUtils.isNotBlank(userId) && StringUtils.isNotBlank(remark)) {
            User data = new User();
            data.setUserId(userId);
            data.setMobile(remark);
            userDAO.updateRemark(data);
        }
    }

}
