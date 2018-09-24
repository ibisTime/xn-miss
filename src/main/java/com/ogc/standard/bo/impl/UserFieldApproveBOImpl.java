/**
 * @Title UserFieldApproveBOImpl.java 
 * @Package com.ogc.standard.bo.impl 
 * @Description 
 * @author taojian  
 * @date 2018年9月13日 下午12:53:26 
 * @version V1.0   
 */
package com.ogc.standard.bo.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ogc.standard.bo.IUserBO;
import com.ogc.standard.bo.IUserFieldApproveBO;
import com.ogc.standard.bo.base.PaginableBOImpl;
import com.ogc.standard.dao.IUserFieldApproveDAO;
import com.ogc.standard.domain.UserFieldApprove;
import com.ogc.standard.enums.EApproveStatus;
import com.ogc.standard.enums.EErrorCode_main;
import com.ogc.standard.exception.BizException;

/** 
 * @author: taojian 
 * @since: 2018年9月13日 下午12:53:26 
 * @history:
 */
@Component
public class UserFieldApproveBOImpl extends PaginableBOImpl<UserFieldApprove>
        implements IUserFieldApproveBO {

    @Autowired
    private IUserBO userBO;

    @Autowired
    private IUserFieldApproveDAO userFieldApproveDAO;

    @Override
    public void saveApply(String userId, String idHold, String field,
            String captcha, String type) {
        UserFieldApprove data = new UserFieldApprove();
        data.setType(type);
        data.setApplyUser(userId);
        data.setIdHold(idHold);
        data.setField(field);
        data.setCaptcha(captcha);
        data.setApplyDatetime(new Date());
        data.setStatus(EApproveStatus.TOAPPROVE.getCode());
        userFieldApproveDAO.insert(data);
    }

    @Override
    public void refreshApprove(UserFieldApprove data, String approveUser,
            String remark) {
        data.setApproveUser(approveUser);
        data.setRemark(remark);
        data.setApproveDatetime(new Date());
        userFieldApproveDAO.updateResult(data);
    }

    @Override
    public UserFieldApprove getUserFieldApprove(Long id) {
        UserFieldApprove condition = new UserFieldApprove();
        condition.setId(id);
        UserFieldApprove data = userFieldApproveDAO.select(condition);
        if (data == null) {
            throw new BizException(EErrorCode_main.id_NOTEXIST.getCode());
        }
        return data;
    }

    @Override
    public void checkApproveStatus(String applyUser) {
        UserFieldApprove condition = new UserFieldApprove();
        condition.setApplyUser(applyUser);
        condition.setStatus(EApproveStatus.TOAPPROVE.getCode());
        if (getTotalCount(condition) > 0) {
            throw new BizException(EErrorCode_main.user_INAPPROVE.getCode());
        }
    }

}
