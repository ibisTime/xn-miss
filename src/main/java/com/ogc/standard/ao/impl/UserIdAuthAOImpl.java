/**
 * @Title UserIdAuthAOImpl.java 
 * @Package com.ogc.standard.ao.impl 
 * @Description 
 * @author taojian  
 * @date 2018年9月13日 下午5:49:41 
 * @version V1.0   
 */
package com.ogc.standard.ao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ogc.standard.ao.IUserIdAuthAO;
import com.ogc.standard.bo.IUserBO;
import com.ogc.standard.bo.IUserIdAuthBO;
import com.ogc.standard.bo.base.Paginable;
import com.ogc.standard.domain.User;
import com.ogc.standard.domain.UserIdAuth;
import com.ogc.standard.dto.req.XN805160Req;
import com.ogc.standard.enums.EApproveStatus;
import com.ogc.standard.enums.EErrorCode_main;
import com.ogc.standard.enums.EResultType;
import com.ogc.standard.exception.BizException;

/** 
 * @author: taojian 
 * @since: 2018年9月13日 下午5:49:41 
 * @history:
 */
@Service
public class UserIdAuthAOImpl implements IUserIdAuthAO {

    @Autowired
    private IUserIdAuthBO userIdAuthBO;

    @Autowired
    private IUserBO userBO;

    @Override
    public void apply(XN805160Req req) {
        User applyUser = userBO.getUser(req.getApplyUser());
        if (applyUser.getIdKind() != null && applyUser.getIdNo() != null) {
            throw new BizException(EErrorCode_main.idauth_SUCCESSED.getCode());
        }
        // 检验审批状态
        userIdAuthBO.checkApproveStatus(req.getApplyUser());
        // 增加申请
        userIdAuthBO.saveApply(req);
    }

    @Override
    public void approve(Long id, String approveResult, String approveUser,
            String remark) {

        UserIdAuth data = userIdAuthBO.getUserIdAuth(id);
        if (!EApproveStatus.TOAPPROVE.getCode().equals(data.getStatus())) {
            throw new BizException(EErrorCode_main.idauth_APPROVED.getCode());
        }
        if (EResultType.PASS.getCode().equals(approveResult)) {
            data.setStatus(EApproveStatus.PASS.getCode());
            userBO.refreshIdentity(data.getApplyUser(), data.getRealName(),
                data.getIdKind(), data.getIdNo(), data.getIdFace(),
                data.getIdOppo(), data.getIdHold());

        } else {
            data.setStatus(EApproveStatus.REFUSE.getCode());
        }

        userIdAuthBO.refreshApprove(data, approveUser, remark);

    }

    @Override
    public UserIdAuth getUserIdAuth(Long id) {
        return userIdAuthBO.getUserIdAuth(id);
    }

    @Override
    public List<UserIdAuth> getUserIdAuthByUserId(String userId) {
        UserIdAuth condition = new UserIdAuth();
        condition.setApplyUser(userId);
        return userIdAuthBO.queryUserIdAuthList(condition);
    }

    @Override
    public Paginable<UserIdAuth> queryUserIdAuthPage(int start, int limit,
            UserIdAuth condition) {
        Paginable<UserIdAuth> page = userIdAuthBO.getPaginable(start, limit,
            condition);
        for (UserIdAuth data : page.getList()) {
            data.setUser(userBO.getUser(data.getApplyUser()));
        }
        return page;
    }

}
