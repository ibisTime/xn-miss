package com.ogc.standard.ao.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.ogc.standard.ao.IUserAO;
import com.ogc.standard.bo.IAccountBO;
import com.ogc.standard.bo.ISYSConfigBO;
import com.ogc.standard.bo.ISYSUserBO;
import com.ogc.standard.bo.ISignLogBO;
import com.ogc.standard.bo.ISmsOutBO;
import com.ogc.standard.bo.IUserBO;
import com.ogc.standard.bo.IUserExtBO;
import com.ogc.standard.bo.base.Paginable;
import com.ogc.standard.common.DateUtil;
import com.ogc.standard.common.MD5Util;
import com.ogc.standard.common.PhoneUtil;
import com.ogc.standard.common.SysConstant;
import com.ogc.standard.common.SysConstants;
import com.ogc.standard.common.WechatConstant;
import com.ogc.standard.domain.SignLog;
import com.ogc.standard.domain.User;
import com.ogc.standard.domain.UserExt;
import com.ogc.standard.dto.req.XN805170Req;
import com.ogc.standard.dto.res.XN805170Res;
import com.ogc.standard.enums.EAccountType;
import com.ogc.standard.enums.EBoolean;
import com.ogc.standard.enums.ECaptchaType;
import com.ogc.standard.enums.EConfigType;
import com.ogc.standard.enums.ECurrency;
import com.ogc.standard.enums.EErrorCode_main;
import com.ogc.standard.enums.ESex;
import com.ogc.standard.enums.ESignLogType;
import com.ogc.standard.enums.EUser;
import com.ogc.standard.enums.EUserKind;
import com.ogc.standard.enums.EUserPwd;
import com.ogc.standard.enums.EUserStatus;
import com.ogc.standard.exception.BizException;
import com.ogc.standard.http.PostSimulater;

/** 
 * @author: dl 
 * @since: 2018年8月20日 下午1:50:42 
 * @history:
 */
@Service
public class UserAOImpl implements IUserAO {

    private static Logger logger = Logger.getLogger(UserAOImpl.class);

    @Autowired
    private ISmsOutBO smsOutBO;

    @Autowired
    private IUserBO userBO;

    @Autowired
    private ISignLogBO signLogBO;

    @Autowired
    private IUserExtBO userExtBO;

    @Autowired
    private ISYSUserBO sysUserBO;

    @Autowired
    private IAccountBO accountBO;

    @Autowired
    private ISYSConfigBO sysConfigBO;

    @Override
    @Transactional
    public XN805170Res doLoginWeChat(XN805170Req req) {
        // Step1：获取密码参数信息
        Map<String, String> configPwd = sysConfigBO
            .getConfigsMap(EConfigType.WEIXIN_H5.getCode());
        String appId = null;
        String appSecret = null;
        if (EConfigType.WEIXIN_H5.getCode().equals(req.getType())) {
            appId = configPwd.get(SysConstant.WX_H5_ACCESS_KEY);
            appSecret = configPwd.get(SysConstant.WX_H5_SECRET_KEY);
        } else {
            throw new BizException("XN000000", "登录类型不支持");
        }
        if (StringUtils.isBlank(appId)) {
            throw new BizException("XN000000", "参数appId配置获取失败，请检查配置");
        }
        if (StringUtils.isBlank(appSecret)) {
            throw new BizException("XN000000", "参数appSecret配置获取失败，请检查配置");
        }

        // Step2：通过Authorization Code获取Access Token
        String accessToken = "";
        Map<String, String> res = new HashMap<>();
        Properties fromProperties = new Properties();
        fromProperties.put("grant_type", "authorization_code");
        fromProperties.put("appid", appId);
        fromProperties.put("secret", appSecret);
        fromProperties.put("code", req.getCode());
        logger.info("appId:" + appId + ",appSecret:" + appSecret + ",code:"
                + req.getCode());
        XN805170Res result = null;
        try {
            String response = PostSimulater.requestPostForm(
                WechatConstant.WX_TOKEN_URL, fromProperties);
            res = getMapFromResponse(response);
            accessToken = (String) res.get("access_token");
            if (res.get("error") != null) {
                throw new BizException("XN000000", "微信登录失败原因："
                        + res.get("error"));
            }
            if (StringUtils.isBlank(accessToken)) {
                throw new BizException("XN000000", "accessToken不能为空");
            }
            // Step3：使用Access Token来获取用户的OpenID
            String openId = (String) res.get("openid");
            // 获取unionid
            Map<String, String> wxRes = new HashMap<>();
            Properties queryParas = new Properties();
            queryParas.put("access_token", accessToken);
            queryParas.put("openid", openId);
            queryParas.put("lang", "zh_CN");
            wxRes = getMapFromResponse(PostSimulater.requestPostForm(
                WechatConstant.WX_USER_INFO_URL, queryParas));
            String unionId = (String) wxRes.get("unionid");
            String h5OpenId = null;
            if (EConfigType.WEIXIN_H5.getCode().equals(req.getType())) {
                h5OpenId = (String) wxRes.get("openid");
            }
            // Step4：根据openId，unionId从数据库中查询用户信息
            User dbUser = userBO.doGetUserByOpenId(h5OpenId);
            if (null != dbUser) {// 如果user存在，说明用户授权登录过，直接登录
                result = new XN805170Res(dbUser.getUserId());
            } else {// user不存在，第一次授权登录
                String nickname = (String) wxRes.get("nickname");
                String photo = (String) wxRes.get("headimgurl");
                String gender = ESex.UNKNOWN.getCode();
                if (String.valueOf(wxRes.get("sex")).equals("1.0")) {
                    gender = ESex.MEN.getCode();
                } else if (String.valueOf(wxRes.get("sex")).equals("2.0")) {
                    gender = ESex.WOMEN.getCode();
                }
                // 注册
                result = doWxLoginReg(req, unionId, h5OpenId, nickname, photo,
                    gender);
            }
        } catch (Exception e) {
            throw new BizException("xn000000", e.getMessage());
        }
        return result;
    }

    private XN805170Res doWxLoginReg(XN805170Req req, String unionId,
            String h5OpenId, String nickname, String photo, String gender) {
        XN805170Res result;
        userBO.doCheckOpenId(unionId, h5OpenId);
        // 插入用户信息
        String userId = userBO.doRegister(unionId, h5OpenId, null,
            EUserKind.Customer.getCode(), EUserPwd.InitPwd.getCode(), nickname,
            photo, gender);

        distributeAccount(userId, nickname, EUserKind.Customer.getCode());
        result = new XN805170Res(userId, EBoolean.NO.getCode());
        return result;
    }

    /**
     * @param response  可能是Json & Jsonp字符串 & urlParas
     *          eg：urlParas：access_token=xxx&expires_in=7776000&refresh_token=xxx
     * @return
     */
    public Map<String, String> getMapFromResponse(String response) {
        if (StringUtils.isBlank(response)) {
            return new HashMap<>();
        }

        Map<String, String> result = new HashMap<>();
        int begin = response.indexOf("{");
        int end = response.lastIndexOf("}") + 1;

        if (begin >= 0 && end > 0) {
            result = new Gson().fromJson(response.substring(begin, end),
                new TypeToken<Map<String, Object>>() {
                }.getType());
        } else {
            String[] paras = response.split("&");
            for (String para : paras) {
                result.put(para.split("=")[0], para.split("=")[1]);
            }
        }

        return result;
    }

    // 分配账号
    private String distributeAccount(String userId, String mobile, String kind) {
        String currency = null;
        String type = null;
        if (EUserKind.Customer.getCode().equals(kind)) {
            currency = ECurrency.CNY.getCode();
            type = EAccountType.Customer.getCode();
        }
        return accountBO.distributeAccount(userId, mobile, type, currency);
    }

    @Override
    public void doCheckMobile(String mobile) {
        userBO.isMobileExist(mobile);
    }

    @Override
    public void lastLogin(String userId) {
        userBO.refreshLastLogin(userId);
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
            throw new BizException(EErrorCode_main.user_SAMEMOBILE.getCode());
        }
        // 验证手机号
        userBO.isMobileExist(newMobile);
        // 短信验证码是否正确（往新手机号发送）
        smsOutBO.checkCaptcha(newMobile, smsCaptcha, "805061");
        userBO.refreshMobile(userId, newMobile);

        // 发送短信

        smsOutBO.sendSmsOut(oldMobile, String.format(
            SysConstants.DO_CHANGE_MOBILE_CN, PhoneUtil.hideMobile(oldMobile),
            DateUtil.dateToStr(new Date(), DateUtil.DATA_TIME_PATTERN_1),
            newMobile), ECaptchaType.MOBILE_CHANGE.getCode());

    }

    @Override
    @Transactional
    public void doSetTradePwd(String userId, String tradePwd, String smsCaptcha) {
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
        smsOutBO.sendSmsOut(
            mobile,
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
            throw new BizException(EErrorCode_main.user_TRADEPWDWRONG.getCode());
        }
        if (oldTradePwd.equals(newTradePwd)) {
            throw new BizException(EErrorCode_main.user_SAMETRADEPWD.getCode());
        }
        userBO.refreshTradePwd(userId, newTradePwd);
        String mobile = user.getMobile();
        // 发短信
        smsOutBO.sendSmsOut(
            mobile,
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
        // user.setUserIdAuthInfo(userIdAuthBO.getApproveByUser(userId));

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

        return user;
    }

    @Override
    public void doBindMobile(String isSendSms, String mobile,
            String smsCaptcha, String userId, String language) {
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
            smsOutBO.sendSmsOut(mobile, String.format(
                SysConstants.DO_BIND_MOBILE_CN, PhoneUtil.hideMobile(mobile),
                DateUtil.dateToStr(new Date(), DateUtil.DATA_TIME_PATTERN_1),
                mobile), ECaptchaType.MOBILE_CHANGE.getCode());
        }
    }

    @Override
    public void doResetReferee(String userId, String userReferee, String updater) {
        User data = userBO.getUser(userId);
        userBO.refreshReferee(userId, userReferee, updater);
    }

}
