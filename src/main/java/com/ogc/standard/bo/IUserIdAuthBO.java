package com.ogc.standard.bo;

import java.util.List;

import com.ogc.standard.bo.base.IPaginableBO;
import com.ogc.standard.domain.UserIdAuth;
import com.ogc.standard.dto.req.XN805160Req;

/** 
 * @author: taojian 
 * @since: 2018年9月13日 下午5:15:21 
 * @history:
 */
public interface IUserIdAuthBO extends IPaginableBO<UserIdAuth> {
    // 提交申请
    public void saveApply(XN805160Req req);

    // 审核
    public void refreshApprove(UserIdAuth data, String ApproveUser,
            String remark);

    public List<UserIdAuth> queryUserIdAuthList(UserIdAuth condition);

    public UserIdAuth getUserIdAuth(Long id);

    public void checkApproveStatus(String userId);

    public UserIdAuth getUserIdAuthWithNoCheck(Long id);

    public UserIdAuth getApproveByUser(String userId);
}
