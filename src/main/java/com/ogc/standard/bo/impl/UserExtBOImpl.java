package com.ogc.standard.bo.impl;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ogc.standard.bo.IUserExtBO;
import com.ogc.standard.bo.base.PaginableBOImpl;
import com.ogc.standard.dao.IUserExtDAO;
import com.ogc.standard.domain.UserExt;
import com.ogc.standard.enums.EErrorCode_main;
import com.ogc.standard.exception.BizException;

@Component
public class UserExtBOImpl extends PaginableBOImpl<UserExt>
        implements IUserExtBO {
    @Autowired
    private IUserExtDAO userExtDAO;

    @Override
    public long getTotalCount(UserExt condition) {
        return userExtDAO.selectTotalCount(condition);
    }

    @Override
    public void refreshUserExt(UserExt data) {
        userExtDAO.updateUserExt(data);
    }

    @Override
    public String addUserExt(String userId) {
        UserExt data = new UserExt();
        data.setUserId(userId);
        userExtDAO.insert(data);
        return userId;

    }

    @Override
    public UserExt getUserExt(String userId) {
        UserExt condition = new UserExt();
        condition.setUserId(userId);
        return userExtDAO.getUserExt(condition);
    }

    @Override
    public void isEmailExit(String email) {
        if (StringUtils.isNotBlank(email)) {
            UserExt condition = new UserExt();
            condition.setEmail(email);
            long count = getTotalCount(condition);
            if (count > 0) {
                throw new BizException(
                    EErrorCode_main.user_EMAILEXIST.getCode());
            }
        }
    }

    @Override
    public void refreshEmail(UserExt data) {
        userExtDAO.bindEmail(data);

    }

}
