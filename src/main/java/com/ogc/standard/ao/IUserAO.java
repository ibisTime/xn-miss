package com.ogc.standard.ao;

import com.ogc.standard.bo.base.Paginable;
import com.ogc.standard.domain.User;

/**
 * @author: xieyj 
 * @since: 2017年7月16日 下午2:46:32 
 * @history:
 */
public interface IUserAO {
    String DEFAULT_ORDER_COLUMN = "user_id";

    // 检查手机号是否存在
    public void doCheckMobile(String mobile, String language);

    // 绑定手机号
    public void doBindMobile(String isSendSms, String mobile, String smsCaptcha,
            String userId, String language);

    // 用户登录
    public String doLogin(String loginName, String loginPwd, String client,
            String location, String language);
    
    // 更换手机号（修改手机号方式一）
    public void doChangeMoblie(String userId, String newMobile,
            String smsCaptcha, String language);


    // 管理员重置推荐人
    public void doResetReferee(String userId, String userReferee,
            String updater);

    // 设置支付密码
    public void doSetTradePwd(String userId, String tradePwd,
            String smsCaptcha);

    // 重置支付密码
    public void doResetTradePwd(String userId, String newTradePwd,
            String smsCaptcha);


    // 修改支付密码
    public void doModifyTradePwd(String userId, String oldTradePwd,
            String newTradePwd, String language);

    // 修改头像
    public void modifyPhoto(String userId, String photo);


    // 注销/激活用户
    public void doCloseOpen(String userId, String updater, String remark,
            String language);

    //  设置角色
    // public void doRoleUser(String userId, String roleCode, String updater,
    // String remark);


    // 修改昵称
    public void doModifyNickname(String userId, String nickname);

    public Paginable<User> queryUserPage(int start, int limit, User condition);

    // public List<User> queryUserList(User condition);

    //详情查询用户
    public User doGetUser(String userId);

    
    //  校验支付密码
    // public void doCheckTradePwd(String userId, String tradePwd);

    // 更新最后一次登录时间
    public void lastLogin(String userId);




}
