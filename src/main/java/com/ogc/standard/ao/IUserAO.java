package com.ogc.standard.ao;

import com.ogc.standard.bo.base.Paginable;
import com.ogc.standard.domain.User;
import com.ogc.standard.dto.req.XN802399Req;
import com.ogc.standard.dto.req.XN802400Req;
import com.ogc.standard.dto.req.XN805041Req;
import com.ogc.standard.dto.req.XN805042Req;
import com.ogc.standard.dto.req.XN805043Req;
import com.ogc.standard.dto.req.XN805081Req;
import com.ogc.standard.dto.res.XN625000Res;

/**
 * @author: xieyj 
 * @since: 2017年7月16日 下午2:46:32 
 * @history:
 */
public interface IUserAO {
    String DEFAULT_ORDER_COLUMN = "user_id";

    // 检查手机号是否存在
    public void doCheckMobile(String mobile, String language);

    // 注册前端用户
    public String doRegisterByMobile(XN805041Req req);

    // 邮箱注册
    public String doRegisterByEmail(XN805043Req req);

    // 代注册
    public String doAddUser(XN805042Req req);

    public void editTradeRate(String userId, Double tradeRate);

    public void doBindMobile(String isSendSms, String mobile, String smsCaptcha,
            String userId, String language);

    // 平台代注册渠道商用户
    public String doAddQDS(String mobile, String idKind, String idNo,
            String realName, String respArea, String language);

    // // 申请注册
    // public String doApplyRegUser(XN805043Req req);

    // 用户登录
    public String doLogin(String loginName, String loginPwd, String client,
            String location, String language);

    // 修改定位信息
    public void doChangeLocation(XN805081Req req);

    // // 验证码登录注册
    // public XN805041Res doCaptchaLoginReg(String countryCode, String mobile,
    // String smsCaptcha, String inviteCode, String companyCode,
    // String systemCode);
    //
    // // 检查登录密码是否正确
    // public void doCheckLoginPwd(String userId, String password);
    //
    // // 绑定邮箱
    // public void doBindEmail(String userId, String email, String captcha);
    //
    // // 添加备注
    // public void doAddRemark(String userId, String remark);
    //
    // 更换手机号
    public void doChangeMoblie(String userId, String newMobile,
            String smsCaptcha, String language);

    // 更换手机号_需支付密码
    public void doChangeMoblie(String userId, String newMobile,
            String smsCaptcha, String tradePwd, String language);

    // 重置登录密码
    public void doResetLoginPwd(String mobile, String smsCaptcha,
            String newLoginPwd);

    // 修改登录密码
    public void doModifyLoginPwd(String userId, String oldLoginPwd,
            String newLoginPwd, String language);

    // 管理员重置用户密码
    public void doResetLoginPwdByOss(String userId, String loginPwd,
            String adminUserId, String adminPwd);

    // 管理员重置推荐人
    public void doResetReferee(String userId, String userReferee,
            String updater);

    // 设置支付密码
    public void doSetTradePwd(String userId, String tradePwd,
            String smsCaptcha);

    // 重置支付密码
    public void doResetTradePwd(String userId, String newTradePwd,
            String smsCaptcha);

    // 重置支付密码(需实名认证)
    public void doResetTradePwd(String userId, String newTradePwd,
            String smsCaptcha, String idKind, String idNo, String language);

    // 修改支付密码
    public void doModifyTradePwd(String userId, String oldTradePwd,
            String newTradePwd, String language);

    // 获取腾讯云签名
    public XN625000Res getTencentSign(String userId);

    // 本系统实名认证
    public void doIdentify(String userId, String idKind, String idNo,
            String realName);

    // 修改头像
    public void modifyPhoto(String userId, String photo);

    // // 完善手机号和身份信息
    // public void doModfiyMobileAndIds(String userId, String mobile,
    // String realName, String idKind, String idNo);
    //
    // // 修改经纬度
    // public void doModifyLngLat(String userId, String longitude,
    // String latitude);

    // 注销/激活用户
    public void doCloseOpen(String userId, String updater, String remark,
            String language);

    // // 设置角色
    // public void doRoleUser(String userId, String roleCode, String updater,
    // String remark);
    //
    // // 修改分成比例
    // public void doModifyDivRate(String userId, Double divRate1, Double
    // divRate2,
    // String updater, String remark);
    //
    // // 修改交易广告费率
    // public void doModifyTradeRate(String userId, Double tradeRate,
    // String updater, String remark);
    //
    // // 修改用户等级
    // public void doUpLevel(String userId, String level);
    //
    // 修改昵称
    public void doModifyNickname(String userId, String nickname);

    //
    // // 审核注册用户
    // public void doApproveUser(String userId, String approver,
    // String approveResult, String divRate, String remark);
    //
    // // 修改用户信息
    // public void doModifyUser(XN805095Req req);
    //
    // 两要素实名认证
    public void doTwoIdentify(String userId, String idKind, String idNo,
            String realName);

    // 三四要素实名认证
    public void doFourIdentify(String userId, String idKind, String idNo,
            String realName, String cardNo, String bindMobile);

    // // 芝麻认证（支付宝渠道）
    // public Object doAlipayZhimaIdentify(String userId, String idKind,
    // String idNo, String realName);
    //
    // // 芝麻认证查询（支付宝渠道）
    // public Object doAlipayZhimaQuery(String userId, String bizNo);
    //
    // // 芝麻认证（芝麻信用渠道）
    // public Object doZhimaIdentify(String userId, String idKind, String idNo,
    // String realName, String returnUrl, String localCheck);
    //
    // // 芝麻认证查询（芝麻信用渠道）
    // public Object doZhimaQuery(String userId, String bizNo);

    public Paginable<User> queryUserPage(int start, int limit, User condition);

    // public List<User> queryUserList(User condition);

    public User doGetUser(String userId);

    // public List<User> getUserRefereeList(String userId);
    //
    // // 根据手机号，种类，公司编号，系统编号获取用户编号
    // public String doGetUserIdByCondition(String mobile, String kind,
    // String companyCode, String systemCode);
    //
    // // 校验支付密码
    // public void doCheckTradePwd(String userId, String tradePwd);
    //
    // public XN625000Res getTencentSign(String userId);
    //
    // 查询推荐信息
    // public XN805123Res getInviteInfo(String userId);

    // 查询推荐信息
    // public Object getInviteInfoList(String userId);

    // 更新最后一次登录时间
    public void lastLogin(String userId);

    // 开启/修改谷歌认证
    public void openGoogleAuth(String userId, String secret, String smsCaptcha,
            String googleCaptcha);

    // 开启/修改谷歌认证
    public void closeGoogleAuth(String userId, String smsCaptcha,
            String googleCaptcha);

    // 绑定邮箱
    public void bindEmail(String captcha, String email, String userId,
            String language);

    // 修改负责区域
    public void editRespArea(String userId, String respArea, String updater,
            String language);

    // 直退用户查询
    public Paginable<User> queryFirstRefPage(XN802399Req req, int start,
            int limit);

    //
    public Paginable<User> querySecondRefPage(XN802400Req req, int start,
            int limit);
    // // 关闭认证
    // public void closeGoogleAuth(String userId, String smsCaptcha,
    // String googleCaptcha);
    //
    // // 添加推荐人
    // public void addUserReferee(String userId, String userReferee,
    // String userRefereeKind);

}
