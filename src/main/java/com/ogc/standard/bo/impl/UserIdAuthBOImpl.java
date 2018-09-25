/**
 * @Title UserIdAuthBOImpl.java 
 * @Package com.ogc.standard.bo.impl 
 * @Description 
 * @author taojian  
 * @date 2018年9月13日 下午5:17:06 
 * @version V1.0   
 */
package com.ogc.standard.bo.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ogc.standard.bo.IUserIdAuthBO;
import com.ogc.standard.bo.base.PaginableBOImpl;
import com.ogc.standard.dao.IUserIdAuthDAO;
import com.ogc.standard.domain.UserIdAuth;
import com.ogc.standard.dto.req.XN805160Req;
import com.ogc.standard.enums.EApproveStatus;
import com.ogc.standard.enums.EErrorCode_main;
import com.ogc.standard.exception.BizException;

/** 
 * @author: taojian 
 * @since: 2018年9月13日 下午5:17:06 
 * @history:
 */
@Component
public class UserIdAuthBOImpl extends PaginableBOImpl<UserIdAuth>
        implements IUserIdAuthBO {

    @Autowired
    private IUserIdAuthDAO userIdAuthDAO;

    @Override
    public void saveApply(XN805160Req req) {
        UserIdAuth data = new UserIdAuth();
        data.setApplyUser(req.getApplyUser());
        data.setRealName(req.getRealName());
        data.setCountry(req.getCountry());
        data.setIdFace(req.getIdFace());
        data.setIdKind(req.getIdKind());
        data.setIdHold(req.getIdHold());
        data.setIdNo(req.getIdNo());
        data.setIdOppo(req.getIdOppo());
        data.setApplyDatetime(new Date());
        data.setStatus(EApproveStatus.TOAPPROVE.getCode());
        userIdAuthDAO.insert(data);
    }

    @Override
    public void refreshApprove(UserIdAuth data, String approveUser,
            String remark) {
        data.setApproveUser(approveUser);
        data.setRemark(remark);
        data.setApproveDatetime(new Date());
        userIdAuthDAO.updateresult(data);
    }

    @Override
    public UserIdAuth getUserIdAuth(Long id) {
        UserIdAuth data = null;
        UserIdAuth condition = new UserIdAuth();
        condition.setId(id);
        data = userIdAuthDAO.select(condition);
        if (data == null) {
            throw new BizException(EErrorCode_main.id_NOTEXIST.getCode());
        }
        return data;
    }

    @Override
    public List<UserIdAuth> queryUserIdAuthList(UserIdAuth condition) {
        return userIdAuthDAO.selectList(condition);
    }

    @Override
    public UserIdAuth getUserIdAuthWithNoCheck(Long id) {
        UserIdAuth data = null;
        UserIdAuth condition = new UserIdAuth();
        condition.setId(id);
        data = userIdAuthDAO.select(condition);
        return data;
    }

    @Override
    public void checkApproveStatus(String userId) {
        UserIdAuth condition = new UserIdAuth();
        condition.setApplyUser(userId);
        condition.setStatus(EApproveStatus.PASS.getCode());
        if (userIdAuthDAO.select(condition) != null) {
            throw new BizException(EErrorCode_main.idauth_SUCCESSED.getCode());
        }
        condition.setStatus(EApproveStatus.TOAPPROVE.getCode());
        if (userIdAuthDAO.select(condition) != null) {
            throw new BizException(EErrorCode_main.idauth_INAPPROVE.getCode());
        }

    }

}
