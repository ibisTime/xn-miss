package com.ogc.standard.ao.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ogc.standard.ao.IAccountAO;
import com.ogc.standard.ao.IUserAO;
import com.ogc.standard.bo.IAwardBO;
import com.ogc.standard.bo.IGoogleAuthBO;
import com.ogc.standard.bo.IIdentifyBO;
import com.ogc.standard.bo.ISYSConfigBO;
import com.ogc.standard.bo.ISYSUserBO;
import com.ogc.standard.bo.ISignLogBO;
import com.ogc.standard.bo.ISmsOutBO;
import com.ogc.standard.bo.ITencentBO;
import com.ogc.standard.bo.ITradeOrderBO;
import com.ogc.standard.bo.IUserBO;
import com.ogc.standard.bo.IUserExtBO;
import com.ogc.standard.bo.IUserIdAuthBO;
import com.ogc.standard.bo.IUserRelationBO;
import com.ogc.standard.bo.base.Paginable;
import com.ogc.standard.common.DateUtil;
import com.ogc.standard.common.MD5Util;
import com.ogc.standard.common.PhoneUtil;
import com.ogc.standard.common.PwdUtil;
import com.ogc.standard.common.SysConstants;
import com.ogc.standard.domain.Award;
import com.ogc.standard.domain.SignLog;
import com.ogc.standard.domain.TradeOrder;
import com.ogc.standard.domain.User;
import com.ogc.standard.domain.UserExt;
import com.ogc.standard.domain.UserStatistics;
import com.ogc.standard.dto.req.XN802399Req;
import com.ogc.standard.dto.req.XN802400Req;
import com.ogc.standard.dto.req.XN805041Req;
import com.ogc.standard.dto.req.XN805042Req;
import com.ogc.standard.dto.req.XN805043Req;
import com.ogc.standard.dto.req.XN805081Req;
import com.ogc.standard.dto.res.XN625000Res;
import com.ogc.standard.enums.EBoolean;
import com.ogc.standard.enums.ECaptchaType;
import com.ogc.standard.enums.ECoin;
import com.ogc.standard.enums.EErrorCode_main;
import com.ogc.standard.enums.EIDKind;
import com.ogc.standard.enums.ERefType;
import com.ogc.standard.enums.ESignLogType;
import com.ogc.standard.enums.ESystemCode;
import com.ogc.standard.enums.EUser;
import com.ogc.standard.enums.EUserKind;
import com.ogc.standard.enums.EUserLevel;
import com.ogc.standard.enums.EUserPwd;
import com.ogc.standard.enums.EUserReleationType;
import com.ogc.standard.enums.EUserStatus;
import com.ogc.standard.exception.BizException;

/** 
 * @author: dl 
 * @since: 2018年8月20日 下午1:50:42 
 * @history:
 */
@Service
public class UserAOImpl implements IUserAO {
    @Autowired
    private ISmsOutBO smsOutBO;

    @Autowired
    private ITradeOrderBO tradeOrderBO;

    @Autowired
    private IAwardBO awardBO;

    @Autowired
    private IGoogleAuthBO googleAuthBO;

    @Autowired
    private IUserBO userBO;

    @Autowired
    private ISignLogBO signLogBO;

    @Autowired
    private IUserExtBO userExtBO;

    @Autowired
    private ISYSUserBO sysUserBO;

    @Autowired
    private IAccountAO accountAO;

    @Autowired
    private ISYSConfigBO sysConfigBO;

    @Autowired
    private ITencentBO tencentBO;

    @Autowired
    private IIdentifyBO identifyBO;

    @Autowired
    private IUserRelationBO userRelationBO;

    @Autowired
    private IUserIdAuthBO userIdAuthBO;

    @Override
    public void doCheckMobile(String mobile, String language) {
        userBO.isMobileExist(mobile);
    }

    @Override
    @Transactional
    public String doRegisterByMobile(XN805041Req req) {
        // 验证手机号是否存在
        userBO.isMobileExist(req.getMobile());

        // 验证短信验证码
        smsOutBO.checkCaptcha(req.getMobile(), req.getSmsCaptcha(),
            ECaptchaType.C_REG.getCode());

        User refereeUser = userBO.getUserByMobile(req.getUserReferee());
        // 注册用户
        String userId = userBO.doRegister(req.getMobile(), req.getNickname(),
            req.getLoginPwd(), refereeUser, req.getProvince(), req.getCity(),
            req.getArea());
        if (refereeUser != null) {
            // 推荐人分佣
            awardBO.saveRegistAward(refereeUser.getUserId(),
                refereeUser.getKind(), userId, "用户注册推荐人分佣");
        }

        // ext中添加数据
        userExtBO.addUserExt(userId);

        // 分配账户
        accountAO.distributeAccount(userId);
        return userId;
    }

    @Override
    @Transactional
    public String doRegisterByEmail(XN805043Req req) {
        // 验证邮箱是否存在
        userBO.isEmailExist(req.getEmail());

        // 验证邮箱验证码
        smsOutBO.checkCaptcha(req.getEmail(), req.getCaptcha(), "805043");

        User refereeUser = userBO.getUserByMobile(req.getUserReferee());
        // 注册用户
        String userId = userBO.doRegistByEmail(req);
        if (refereeUser != null) {
            // 推荐人分佣
            awardBO.saveRegistAward(refereeUser.getUserId(),
                refereeUser.getKind(), userId, "用户注册推荐人分佣");
        }

        // ext中添加数据
        userExtBO.addUserExt(userId);

        // 分配账户
        accountAO.distributeAccount(userId);
        return userId;
    }

    @Override
    @Transactional
    public String doAddUser(XN805042Req req) {
        String userId = null;
        User user = new User();
        user.setUserId(userId);
        user.setKind(EUserKind.Customer.getCode());
        user.setLoginName(req.getMobile());
        user.setMobile(req.getMobile());
        if (StringUtils.isBlank(req.getLoginPwd())) {
            req.setLoginPwd(EUserPwd.InitPwd.getCode());
        }
        user.setLoginPwd(MD5Util.md5(req.getLoginPwd()));
        user.setLoginPwdStrength(
            PwdUtil.calculateSecurityLevel(req.getLoginPwd()));
        user.setLevel(EUserLevel.ONE.getCode());
        user.setUserReferee(req.getUserReferee());
        user.setIdKind(req.getIdKind());
        user.setIdNo(req.getIdNo());
        user.setRealName(req.getRealName());
        user.setStatus(EUserStatus.NORMAL.getCode());
        user.setProvince(req.getProvince());
        user.setCity(req.getCity());
        user.setArea(req.getArea());
        user.setLatitude(req.getLatitude());
        user.setLongitude(req.getLongitude());
        Date date = new Date();
        user.setCreateDatetime(date);
        user.setUpdater(req.getUpdater());
        user.setRemark(req.getRemark());
        double tradeRate = sysConfigBO
            .getDoubleValue(SysConstants.TRADE_FEE_RATE);
        user.setTradeRate(tradeRate);
        userId = userBO.doAddUser(user);

        // 在userExt中添加一条数据
        userExtBO.addUserExt(userId);
        // 分配账户
        accountAO.distributeAccount(userId);
        return userId;
    }

    @Override // 渠道商用户代注册
    @Transactional
    public String doAddQDS(String mobile, String idKind, String idNo,
            String realName, String respArea, String language) {
        // 检查手机号是否存在
        userBO.isMobileExist(mobile);
        // 注册
        String loginPwd = (int) ((Math.random() * 9 + 1) * 100000) + "";
        String userId = userBO.doAddQDS(mobile, idKind, idNo, realName,
            respArea, loginPwd);
        // ext中添加数据
        userExtBO.addUserExt(userId);
        // 分配账户
        accountAO.distributeAccount(userId);
        // 发送短信
        String content = String.format(SysConstants.DO_ADD_USER_CN,
            PhoneUtil.hideMobile(mobile), loginPwd);
        smsOutBO.sendSmsOut(mobile, content, ESystemCode.BZ.getCode(),
            ESystemCode.BZ.getCode());
        return userId;
    }

    @Override
    public void lastLogin(String userId) {
        userBO.refreshLastLogin(userId);
    }

    @Override
    public void openGoogleAuth(String userId, String secret, String smsCaptcha,
            String googleCaptcha) {
        User user = this.doGetUser(userId);
        // 校验谷歌验证码
        googleAuthBO.checkCode(secret, googleCaptcha,
            System.currentTimeMillis());
        // 短信验证码是否正确
        smsOutBO.checkCaptcha(user.getMobile(), smsCaptcha, "805088");
        // 修改谷歌验证秘钥
        userBO.refreshGoogleSecret(userId, secret);
    }

    @Override
    public void closeGoogleAuth(String userId, String smsCaptcha,
            String googleCaptcha) {
        User user = this.doGetUser(userId);
        // 校验谷歌验证码
        googleAuthBO.checkCode(user.getGoogleSecret(), googleCaptcha,
            System.currentTimeMillis());
        // 短信验证码是否正确
        smsOutBO.checkCaptcha(user.getMobile(), smsCaptcha, "805089");
        // 修改谷歌验证秘钥
        userBO.refreshGoogleSecret(userId, null);
    }

    @Override
    @Transactional
    public String doLogin(String loginName, String loginPwd, String client,
            String location, String language) {
        User condition = new User();
        condition.setMobile(loginName);

        List<User> userList = userBO.queryUserList(condition);
        User condition1 = new User();
        condition1.setEmail(loginName);
        userList.addAll(userBO.queryUserList(condition1));
        if (CollectionUtils.isEmpty(userList)) {
            throw new BizException(EErrorCode_main.user_LOGINNAME.getCode());
        }
        User con = new User();
        con.setLoginPwd(MD5Util.md5(loginPwd));
        List<User> listUser = userBO.queryUserList(con);
        if (CollectionUtils.isEmpty(userList)) {
            throw new BizException(EErrorCode_main.user_LOGINPWD.getCode());
        }
        String userId = null;
        for (User user : listUser) {
            if (loginName.equals(user.getMobile())
                    || loginName.equals(user.getEmail())) {
                userId = user.getUserId();
                break;
            }
        }
        if (userId == null) {
            throw new BizException(EErrorCode_main.user_LOGINPWD.getCode());
        }
        User user = userBO.getUser(userId);
        if (!EUserStatus.NORMAL.getCode().equals(user.getStatus())) {
            throw new BizException(EErrorCode_main.user_ACCABNOMAL.getCode());
        }
        // 增加登陆日志
        SignLog data = new SignLog();
        data.setUserId(user.getUserId());
        data.setType(ESignLogType.LOGIN.getCode());
        data.setClient(client);
        data.setLocation(location);
        signLogBO.saveSignLog(data);

        userBO.refreshLastLogin(userId);

        return userId;
        // User condition = new User();
        //
        // condition.setLoginName(loginName);
        //
        // List<User> userList1 = userBO.queryUserList(condition);
        // if (CollectionUtils.isEmpty(userList1)) {
        // throw new BizException("xn805050", "登录名不存在");
        // }
        // condition.setLoginPwd(MD5Util.md5(loginPwd));
        // List<User> userList2 = userBO.queryUserList(condition);
        // if (CollectionUtils.isEmpty(userList2)) {
        // throw new BizException("xn805050", "登录密码错误");
        // }
        // User user = userList2.get(0);
        // if (!EUserStatus.NORMAL.getCode().equals(user.getStatus())) {
        // throw new BizException("xn805050",
        // "该账号" + EUserStatus.getMap().get(user.getStatus()).getValue()
        // + "，请联系工作人员");
        // }
        // // 增加登陆日志
        // SignLog data = new SignLog();
        // data.setUserId(user.getUserId());
        // data.setType(ESignLogType.LOGIN.getCode());
        // data.setClient(client);
        // data.setLocation(location);
        // signLogBO.saveSignLog(data);
        //
        // return user.getUserId();
    }

    @Override
    @Transactional
    public void doChangeMoblie(String userId, String newMobile,
            String smsCaptcha, String language) {
        User user = userBO.getUser(userId);

        String oldMobile = user.getMobile();
        if (newMobile.equals(oldMobile)) {
            // throw new BizException("000005",
            // ErrorMessage.getErrorMes("000005", language));
        }
        // 验证手机号
        userBO.isMobileExist(newMobile);
        // 短信验证码是否正确（往新手机号发送）
        smsOutBO.checkCaptcha(newMobile, smsCaptcha, "805061");
        userBO.refreshMobile(userId, newMobile);

        // 发送短信

        smsOutBO.sendSmsOut(oldMobile,
            String.format(SysConstants.DO_CHANGE_MOBILE_CN,
                PhoneUtil.hideMobile(oldMobile),
                DateUtil.dateToStr(new Date(), DateUtil.DATA_TIME_PATTERN_1),
                newMobile),
            ECaptchaType.MOBILE_CHANGE.getCode());

    }

    @Override
    @Transactional
    public void doChangeMoblie(String userId, String newMobile,
            String smsCaptcha, String tradePwd, String language) {
        User user = userBO.getUser(userId);

        String oldMobile = user.getMobile();
        if (newMobile.equals(oldMobile)) {
            throw new BizException(EErrorCode_main.user_SAMEMOBILE.getCode());
        }
        userBO.isMobileExist(newMobile);
        // 验证支付密码
        if (StringUtils.isNotBlank(tradePwd)) {
            userBO.checkTradePwd(userId, tradePwd);
        }
        // 短信验证码是否正确（往新手机号发送）
        smsOutBO.checkCaptcha(newMobile, smsCaptcha, "805062");
        userBO.refreshMobile(userId, newMobile);

        // 发送短信
        smsOutBO.sendSmsOut(oldMobile,
            String.format(SysConstants.DO_CHANGE_MOBILE_CN,
                PhoneUtil.hideMobile(oldMobile),
                DateUtil.dateToStr(new Date(), DateUtil.DATA_TIME_PATTERN_1),
                newMobile),
            ECaptchaType.MOBILE_CHANGE.getCode());
    }

    @Override
    @Transactional
    public void doResetLoginPwd(String mobile, String smsCaptcha,
            String newLoginPwd) {
        String userId = userBO.getUserId(mobile);
        // 短信验证码是否正确
        smsOutBO.checkCaptcha(mobile, smsCaptcha, "805063");
        userBO.refreshLoginPwd(userId, newLoginPwd);
        // 发送短信
        smsOutBO.sendSmsOut(mobile,
            String.format(SysConstants.DO_RESET_LOGIN_PWD_CN,
                PhoneUtil.hideMobile(mobile)),
            ECaptchaType.LOGIN_PWD_RESET.getCode());
    }

    @Override
    @Transactional
    public void doModifyLoginPwd(String userId, String oldLoginPwd,
            String newLoginPwd, String language) {
        User user = userBO.getUser(userId);
        // 验证当前登录密码是否正确
        userBO.checkLoginPwd(userId, oldLoginPwd);
        if (oldLoginPwd.equals(newLoginPwd)) {
            throw new BizException(EErrorCode_main.user_SAMELOGINPWD.getCode());
        }

        // 重置
        userBO.refreshLoginPwd(userId, newLoginPwd);
        // 发送短信

        smsOutBO.sendSmsOut(user.getMobile(),
            String.format(SysConstants.DO_MODIFY_LOGIN_PWD_CN,
                PhoneUtil.hideMobile(user.getMobile())),
            ECaptchaType.MODIFY_LOGIN_PWD.getCode());

    }

    @Override
    @Transactional
    public void doResetLoginPwdByOss(String userId, String loginPwd,
            String adminUserId, String adminPwd) {
        // 验证当前登录密码是否正确
        sysUserBO.checkLoginPwd(adminUserId, adminPwd);
        userBO.refreshLoginPwd(userId, loginPwd);
    }

    @Override
    @Transactional
    public void doSetTradePwd(String userId, String tradePwd,
            String smsCaptcha) {
        User user = userBO.getUser(userId);
        // 短信验证码是否正确
        smsOutBO.checkCaptcha(user.getMobile(), smsCaptcha, "805066");
        // 修改支付密码
        userBO.refreshTradePwd(userId, tradePwd);
        // 发送短信
        // String mobile = user.getMobile();
        // smsOutBO.sendInterSmsOut(user.getInterCode(), mobile,
        // "尊敬的" + PhoneUtil.hideMobile(mobile)
        // + "用户，您的资金密码设置成功。请妥善保管您的账户相关信息。",
        // user.getCompanyCode(), user.getSystemCode());
    }

    @Override
    public void doResetTradePwd(String userId, String newTradePwd,
            String smsCaptcha) {
        User user = userBO.getUser(userId);
        // 短信验证码是否正确
        String mobile = user.getMobile();
        smsOutBO.checkCaptcha(mobile, smsCaptcha, "805067");
        userBO.refreshTradePwd(userId, newTradePwd);
        // 发短信
        smsOutBO.sendSmsOut(mobile,
            String.format(SysConstants.DO_RESET_TRADE_PWD_CN,
                PhoneUtil.hideMobile(mobile)),
            ECaptchaType.RESET_TRADE_PWD.getCode());

    }

    @Override
    @Transactional
    public void doResetTradePwd(String userId, String newTradePwd,
            String smsCaptcha, String idKind, String idNo, String language) {
        User user = userBO.getUser(userId);
        if (user.getIdKind() == null || user.getIdNo() == null) {
            throw new BizException(EErrorCode_main.user_DOIDFIRST.getCode());
        }
        // 证件是否正确
        if (!(user.getIdKind().equalsIgnoreCase(idKind)
                && user.getIdNo().equalsIgnoreCase(idNo))) {
            throw new BizException(EErrorCode_main.user_IDWRONG.getCode());
        }
        // 短信验证码是否正确
        String mobile = user.getMobile();
        smsOutBO.checkCaptcha(mobile, smsCaptcha, "805068");
        userBO.refreshTradePwd(userId, newTradePwd);
        // 发短信
        smsOutBO.sendSmsOut(mobile,
            String.format(SysConstants.DO_RESET_TRADE_PWD_CN,
                PhoneUtil.hideMobile(mobile)),
            ECaptchaType.RESET_TRADE_PWD.getCode());
    }

    @Override
    @Transactional
    public void doModifyTradePwd(String userId, String oldTradePwd,
            String newTradePwd, String language) {

        User conditon = new User();
        conditon.setUserId(userId);
        conditon.setTradePwd(MD5Util.md5(oldTradePwd));
        List<User> list = userBO.queryUserList(conditon);
        User user = null;
        if (CollectionUtils.isNotEmpty(list)) {
            user = list.get(0);
        } else {
            throw new BizException(
                EErrorCode_main.user_TRADEPWDWRONG.getCode());
        }
        if (oldTradePwd.equals(newTradePwd)) {
            throw new BizException(EErrorCode_main.user_SAMETRADEPWD.getCode());
        }
        userBO.refreshTradePwd(userId, newTradePwd);
        String mobile = user.getMobile();
        // 发短信
        smsOutBO.sendSmsOut(mobile,
            String.format(SysConstants.DO_MODIFY_TRADE_PWD_CN,
                PhoneUtil.hideMobile(mobile)),
            ECaptchaType.MODIFY_TRADE_PWD.getCode());
    }

    @Override
    public void modifyPhoto(String userId, String photo) {
        userBO.refreshPhoto(userId, photo);
    }

    @Override
    public void doModifyNickname(String userId, String nickname) {
        userBO.refreshNickname(userId, nickname);
    }

    @Override
    public void doCloseOpen(String userId, String updater, String remark,
            String language) {
        User user = userBO.getUser(userId);
        // admin 不注销
        if (EUser.ADMIN.getCode().equals(user.getLoginName())) {
            throw new BizException(EErrorCode_main.user_LOGOUT.getCode());
        }
        String mobile = user.getMobile();
        String smsContent = "";
        EUserStatus userStatus = null;
        if (EUserStatus.NORMAL.getCode().equalsIgnoreCase(user.getStatus())) {
            smsContent = String.format(SysConstants.DO_LOCK_USER_CN,
                PhoneUtil.hideMobile(mobile));
            userStatus = EUserStatus.Ren_Locked;
        } else {
            smsContent = String.format(SysConstants.DO_UNLOCK_USER_CN,
                PhoneUtil.hideMobile(mobile));
            userStatus = EUserStatus.NORMAL;

        }
        userBO.refreshStatus(userId, userStatus, updater, remark);
        smsOutBO.sendSmsOut(mobile, smsContent,
            ECaptchaType.ACTIVATE_OR_LOGOFF.getCode());
    }

    @Override
    public Paginable<User> queryUserPage(int start, int limit, User condition) {

        Paginable<User> page = null;
        try {
            page = userBO.getPaginable(start, limit, condition);
        } catch (Exception e) {
            e.printStackTrace();
        }

        List<User> list = page.getList();
        for (User user : list) {
            // 推荐人转义
            User userReferee = userBO.getUserByMobile(user.getUserReferee());
            if (userReferee != null) {
                user.setRefereeUser(userReferee);
            }
        }
        return page;
    }

    @Override
    public User doGetUser(String userId) {
        User user = userBO.getUser(userId);

        if (StringUtils.isNotBlank(user.getUserReferee())) {
            // 拉取推荐人信息
            User refereeUser = userBO.getUser(user.getUserReferee());
            user.setRefereeUser(refereeUser);
        }

        // 是否设置过交易密码
        if (StringUtils.isNotBlank(user.getTradePwdStrength())) {
            user.setTradepwdFlag(true);
        } else {
            user.setTradepwdFlag(false);
        }

        // 是否设置过登录密码
        if (StringUtils.isNotBlank(user.getLoginPwdStrength())) {
            user.setLoginPwdFlag(true);
        } else {
            user.setLoginPwdFlag(false);
        }

        // 是否绑定邮箱
        if (StringUtils.isNotBlank(user.getEmail())) {
            user.setEmailBindFlag(true);
        } else {
            user.setEmailBindFlag(false);
        }

        // 是否开始谷歌认证
        if (StringUtils.isNotBlank(user.getGoogleSecret())) {
            user.setGoogleAuthFlag(true);
        } else {
            user.setGoogleAuthFlag(false);
        }

        // 证件认证状态
        user.setUserIdAuthInfo(userIdAuthBO.getApproveByUser(userId));

        // 拉取ext数据
        UserExt data = userExtBO.getUserExt(userId);
        if (data != null) {
            user.setGender(data.getGender());
            user.setBirthday(data.getBirthday());
            user.setDiploma(data.getDiploma());
            user.setIntroduce(data.getIntroduce());
            user.setOccupation(data.getOccupation());
            user.setPdf(data.getPdf());
            user.setWorkTime(data.getWorkTime());
            user.setGradDatetime(data.getGradDatetime());
        }

        // 用户统计信息
        UserStatistics userStatistics = this.tradeOrderBO
            .obtainUserStatistics(userId, "");
        // 获取信任数量
        userStatistics.setBeiXinRenCount(this.userRelationBO
            .getRelationCount(userId, EUserReleationType.TRUST.getCode()));
        user.setUserStatistics(userStatistics);

        return user;
    }

    @Override
    public void doBindMobile(String isSendSms, String mobile, String smsCaptcha,
            String userId, String language) {
        User user = userBO.getUser(userId);

        if (user.getMobile() != null) {
            throw new BizException(
                EErrorCode_main.user_HAVEBOUNDMOBILE.getCode());
        }
        // 验证手机号
        userBO.isMobileExist(mobile);
        // 短信验证码是否正确
        smsOutBO.checkCaptcha(mobile, smsCaptcha, "805060");
        userBO.refreshMobile(userId, mobile);

        // 发送短信
        if (isSendSms.equals(EBoolean.YES.getCode())) {
            smsOutBO.sendSmsOut(mobile,
                String.format(SysConstants.DO_BIND_MOBILE_CN,
                    PhoneUtil.hideMobile(mobile),
                    DateUtil.dateToStr(new Date(),
                        DateUtil.DATA_TIME_PATTERN_1),
                    mobile),
                ECaptchaType.MOBILE_CHANGE.getCode());
        }
    }

    @Override
    public void doChangeLocation(XN805081Req req) {
        User data = userBO.getUser(req.getUserId());

        data.setAddress(req.getAdress());
        data.setArea(req.getArea());
        data.setCity(req.getCity());
        data.setLatitude(req.getLatitude());
        data.setLongitude(req.getLongitude());
        data.setProvince(req.getProvince());
        data.setRemark(req.getRemark());
        data.setUpdater(req.getUpdater());
        userBO.refreshLocation(data);
    }

    @Override
    public void doResetReferee(String userId, String userReferee,
            String updater) {
        User data = userBO.getUser(userId);
        userBO.refreshReferee(userId, userReferee, updater);
    }

    @Override
    public XN625000Res getTencentSign(String userId) {

        return tencentBO.getSign(userId, ESystemCode.BZ.getCode(),
            ESystemCode.BZ.getCode());
    }

    @Override
    public void doIdentify(String userId, String idKind, String idNo,
            String realName) {
        // 更新用户表
        userBO.refreshIdentity(userId, realName, EIDKind.ID_CARD.getCode(),
            idNo);
    }

    @Override
    public void doTwoIdentify(String userId, String idKind, String idNo,
            String realName) {
        User user = userBO.getUser(userId);
        identifyBO.doTwoIdentify(ESystemCode.BZ.getCode(),
            ESystemCode.BZ.getCode(), userId, realName, idKind, idNo);
        // 更新用户表
        userBO.refreshIdentity(userId, realName, EIDKind.ID_CARD.getCode(),
            idNo);

    }

    @Override
    public void doFourIdentify(String userId, String idKind, String idNo,
            String realName, String cardNo, String bindMobile) {
        // 三方认证
        identifyBO.doFourIdentify(userId, realName, idKind, idNo, cardNo,
            bindMobile);
        // 更新用户表
        userBO.refreshIdentity(userId, realName, EIDKind.ID_CARD.getCode(),
            idNo);

    }

    @Override
    public void editTradeRate(String userId, Double tradeRate) {
        // 检验用户是否存在
        userBO.getUser(userId);
        // 更新手续费率
        userBO.refreshTradeRate(userId, tradeRate);
    }

    @Override
    public void bindEmail(String captcha, String email, String userId,
            String language) {
        smsOutBO.checkCaptcha(email, captcha, "805086");
        User data = userBO.getUser(userId);
        if (data.getEmail() != null) {
            throw new BizException(
                EErrorCode_main.user_HAVEBOUNDEMAIL.getCode());
        }
        userBO.isEmailExist(email);
        userBO.refreshEmail(userId, email);
    }

    @Override
    public void editRespArea(String userId, String respArea, String updater,
            String language) {
        // 判断用户是否存在
        User user = userBO.getUser(userId);
        // 判断是否为渠道商
        if (!EUserKind.QDS.getCode().equals(user.getKind())) {
            throw new BizException(EErrorCode_main.user_ISNOTQDS.getCode());
        }
        // 修改
        userBO.refreshRespArea(userId, respArea, updater);
    }

    @Override
    public Paginable<User> queryFirstRefPage(XN802399Req req, int start,
            int limit) {
        User user = userBO.getUser(req.getUserId());
        User condition = new User();
        condition.setUserReferee(user.getMobile());
        Paginable<User> page = userBO.getPaginable(start, limit, condition);
        // 落地数据
        for (User refUser : page.getList()) {
            Award regCondition = new Award();
            regCondition.setUserId(user.getUserId());
            regCondition.setRefType(ERefType.REGIST.getCode());
            regCondition.setRefCode(refUser.getUserId());
            refUser.setRegAwardCount(awardBO.count(regCondition));
            TradeOrder tradeCondition = new TradeOrder();
            tradeCondition.setBuyUser(refUser.getUserId());
            tradeCondition.setTradeCoin(ECoin.X.getCode());
            refUser.setTradeCount(tradeOrderBO.count(tradeCondition));
            refUser.setTradeAwardCount(awardBO.tradeCount(tradeCondition));
        }
        return page;
    }

    @Override
    public Paginable<User> querySecondRefPage(XN802400Req req, int start,
            int limit) {
        User user = userBO.getUser(req.getUserId());
        User condition = new User();
        condition.setUserReferee(user.getMobile());
        List<User> userList = userBO.queryUserList(condition);
        // 获得userRefereeList
        List<String> userRefereeList = new ArrayList<String>();
        for (User refUser : userList) {
            userRefereeList.add(refUser.getMobile());
        }
        User refCondition = new User();
        refCondition.setUserRefereeList(userRefereeList);
        Paginable<User> page = userBO.getPaginable(start, limit, refCondition);
        // 落地数据
        for (User refUser : page.getList()) {
            Award regCondition = new Award();
            regCondition.setUserId(user.getUserId());
            regCondition.setRefType(ERefType.REGIST.getCode());
            regCondition.setRefCode(refUser.getUserId());
            refUser.setRegAwardCount(awardBO.count(regCondition));
            TradeOrder tradeCondition = new TradeOrder();
            tradeCondition.setBuyUser(refUser.getUserId());
            tradeCondition.setTradeCoin(ECoin.X.getCode());
            refUser.setTradeCount(tradeOrderBO.count(tradeCondition));
            refUser.setTradeAwardCount(awardBO.tradeCount(tradeCondition));
        }
        return page;
    }

}
