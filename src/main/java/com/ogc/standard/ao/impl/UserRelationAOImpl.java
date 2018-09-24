/**
 * @Title UserRelationAOImpl.java
 * @Package com.std.user.ao.impl
 * @Description
 * @author xieyj
 * @date 2016年8月31日 上午11:48:23
 * @version V1.0
 */
package com.ogc.standard.ao.impl;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ogc.standard.ao.IUserRelationAO;
import com.ogc.standard.bo.ITradeOrderBO;
import com.ogc.standard.bo.IUserBO;
import com.ogc.standard.bo.IUserRelationBO;
import com.ogc.standard.bo.base.Paginable;
import com.ogc.standard.domain.User;
import com.ogc.standard.domain.UserRelation;
import com.ogc.standard.domain.UserStatistics;
import com.ogc.standard.enums.EErrorCode_main;
import com.ogc.standard.enums.ESystemCode;
import com.ogc.standard.enums.EUserReleationType;
import com.ogc.standard.exception.BizException;

/**
 * @author: xieyj
 * @since: 2016年8月31日 上午11:48:23
 * @history:
 */
@Service
public class UserRelationAOImpl implements IUserRelationAO {

    @Autowired
    IUserRelationBO userRelationBO;

    @Autowired
    IUserBO userBO;

    @Autowired
    ITradeOrderBO tradeOrderBO;

    /**
     * @see com.std.user.ao.IUserRelationAO#queryUserRelationPage(int, int, com.std.user.domain.UserRelation)
     */
    @Override
    public Paginable<UserRelation> queryUserRelationPage(int start, int limit,
            UserRelation condition) {
        Paginable<UserRelation> page = userRelationBO.getPaginable(start, limit,
            condition);
        for (UserRelation userRelation : page.getList()) {

            User lookUser = null;

            if (StringUtils.isNotBlank(condition.getUserId())) {

                // 查询——我信任的
                User toUser = userBO.getUser(userRelation.getToUser());
                lookUser = toUser;
                //
                userRelation.setToUserInfo(toUser);

            } else {

                // 查询——信任我的
                User fromUser = userBO.getUser(userRelation.getUserId());
                lookUser = fromUser;
                userRelation.setFromUserInfo(fromUser);

            }

            // 查询统计信息
            // 查询对方的 统计信息
            if (lookUser != null) {

                UserStatistics userStatistics = new UserStatistics();
                userStatistics = this.tradeOrderBO
                    .obtainUserStatistics(lookUser.getUserId(), null);
                userStatistics.setBeiXinRenCount(
                    this.userRelationBO.getRelationCount(lookUser.getUserId(),
                        EUserReleationType.TRUST.getCode()));
                lookUser.setUserStatistics(userStatistics);
            }

        }

        return page;

    }

    /**
     * @see com.std.user.ao.IUserRelationAO#followUser(java.lang.String, java.lang.String)
     */
    @Transactional
    @Override
    public void followUser(String userId, String toUserId, String type) {
        userBO.getUser(userId);
        userBO.getUser(toUserId);

        if (userId.equals(toUserId)) {
            throw new BizException(EErrorCode_main.relatin_ONESELF.getCode());
        }
        // 判断两者关系是否存在
        if (userRelationBO.isExistUserRelation(userId, toUserId, type)) {
            throw new BizException(EErrorCode_main.relation_EXIST.getCode());
        }

        if (type.equals(EUserReleationType.BLACKLIST.getCode())) {

            // 1.如果是拉黑操作，先取消信任
            this.checkIsExitOppositionReleationThenRemove(userId, toUserId,
                EUserReleationType.TRUST.getCode());

        } else if (type.equals(EUserReleationType.TRUST.getCode())) {

            // 2.如果是信任操作，先取消拉黑
            this.checkIsExitOppositionReleationThenRemove(userId, toUserId,
                EUserReleationType.BLACKLIST.getCode());

        }

        userRelationBO.saveUserRelation(userId, toUserId, type,
            ESystemCode.BZ.getCode());

    }

    // 判断是否存在 "对立" 关系，并解除
    private void checkIsExitOppositionReleationThenRemove(String userId,
            String toUserId, String type) {

        if (userRelationBO.checkReleation(userId, toUserId, type)) {

            int count = userRelationBO.removeUserRelation(userId, toUserId,
                type);
            if (count <= 0) {
                throw new BizException(
                    EErrorCode_main.relation_CANCEL.getCode());
            }

        }
    }

    /**
     * @see com.std.user.ao.IUserRelationAO#unfollowUser(java.lang.String, java.lang.String)
     */
    @Override
    public void unfollowUser(String userId, String toUserId, String type) {
        userBO.getUser(userId);
        userBO.getUser(toUserId);
        // 判断两者关系是否存在
        if (!userRelationBO.isExistUserRelation(userId, toUserId, type)) {
            throw new BizException(
                EErrorCode_main.relation_NONETOCANCEL.getCode());
        }
        userRelationBO.removeUserRelation(userId, toUserId, type);
    }

    @Override
    public boolean isExistUserRelation(String userId, String toUser,
            String type) {
        List<UserRelation> userRelationList = userRelationBO
            .queryUserRelationList(userId, toUser, type);
        boolean flag = false;
        if (CollectionUtils.isNotEmpty(userRelationList)) {
            flag = true;
        }
        return flag;
    }
}
