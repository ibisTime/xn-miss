package com.ogc.standard.domain;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.ogc.standard.dao.base.ABaseDO;

/**
 * 用户
 * @author: dl 
 * @since: 2018年8月20日 上午10:23:10 
 * @history:
 */
public class User extends ABaseDO {

    private static final long serialVersionUID = 1975331351390818527L;
    // ***********db properties***********

    // 用户编号
    private String userId;

    // 登陆名
    private String loginName;

    // 手机号
    private String mobile;

    // 邮件
    private String email;

    // 类型
    private String kind;

    // 头像
    private String photo;

    // 昵称
    private String nickname;

    // 登陆密码
    private String loginPwd;

    // 登陆密码强度
    private String loginPwdStrength;

    // 用户等级
    private String level;

    // 推荐人
    private String userReferee;

    // 分成比例1
    private Double divRate1;

    // 分成比例2
    private Double divRate2;

    // 证件类型
    private String idKind;

    // 证件号码
    private String idNo;

    // 真实姓名
    private String realName;

    // 证件照正面
    private String idFace;

    // 证件照反面
    private String idOppo;

    // 证件照手持面
    private String idHold;

    // 安全密码
    private String tradePwd;

    // 安全密码强度
    private String tradePwdStrength;

    // 谷歌验证码
    private String googleSecret;

    // 状态
    private String status;

    // 省
    private String province;

    // 市
    private String city;

    // 区(县)
    private String area;

    // 详细地址
    private String address;

    // 经度
    private String longitude;

    // 维度
    private String latitude;

    // 负责区域
    private String respArea;

    // 交易广告费率
    private Double tradeRate;

    // 注册时间
    private Date createDatetime;

    // 更新人
    private String updater;

    // 更新时间
    private Date updateDatetime;

    // 备注
    private String remark;

    // 最后一次登录时间
    private Date lastLogin;

    // ***********db properties***********

    // 登录名模糊查询
    private String loginNameForQuery;

    // 昵称模糊查询
    private String nicknameForQuery;

    // 手机号模糊查询
    private String mobileForQuery;

    // 真实姓名模糊查询
    private String realNameForQuery;

    // 省份
    private String provinceForQuery;

    // 城市
    private String cityForQuery;

    // 县区
    private String areaForQuery;

    // 注册时间起
    private Date createDatetimeStart;

    // 注册时间止
    private Date createDatetimeEnd;

    // 用户编号列表
    private List<String> userRefereeList;

    // 注册分成奖励
    private BigDecimal regAwardCount;

    // 交易分成奖励
    private BigDecimal tradeAwardCount;

    // 交易金额
    private BigDecimal tradeCount;

    public List<String> getUserRefereeList() {
        return userRefereeList;
    }

    public void setUserRefereeList(List<String> userRefereeList) {
        this.userRefereeList = userRefereeList;
    }

    public BigDecimal getRegAwardCount() {
        return regAwardCount;
    }

    public void setRegAwardCount(BigDecimal regAwardCount) {
        this.regAwardCount = regAwardCount;
    }

    public BigDecimal getTradeAwardCount() {
        return tradeAwardCount;
    }

    public void setTradeAwardCount(BigDecimal tradeAwardCount) {
        this.tradeAwardCount = tradeAwardCount;
    }

    public BigDecimal getTradeCount() {
        return tradeCount;
    }

    public void setTradeCount(BigDecimal tradeCount) {
        this.tradeCount = tradeCount;
    }

    /***** 辅助字段 ******/

    private String userRefereeLevel;

    public String getUserRefereeLevel() {
        return userRefereeLevel;
    }

    public void setUserRefereeLevel(String userRefereeLevel) {
        this.userRefereeLevel = userRefereeLevel;
    }

    public Double getTradeRate() {
        return tradeRate;
    }

    public void setTradeRate(Double tradeRate) {
        this.tradeRate = tradeRate;
    }

    // 是否绑定银行卡
    private String bankcardFlag;

    // 是否设置登录密码
    private boolean loginPwdFlag;

    // 是否设置交易密码
    private boolean tradepwdFlag;

    // 是否设置谷歌验证密码
    private boolean googleAuthFlag;

    // 是否绑定邮箱
    private boolean emailBindFlag;

    // 是否已证件认证
    private UserIdAuth userIdAuth;

    // 用户推荐人
    private User refereeUser;

    // 性别(1 男 0 女)
    private String gender;

    // 生日
    private String birthday;

    // 学位
    private String diploma;

    // 职业
    private String occupation;

    // 毕业年限
    private Date gradDatetime;

    // 工作年限
    private String workTime;

    // 用户资料
    private String pdf;

    // 自我介绍
    private String introduce;

    private UserStatistics userStatistics;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getKind() {
        return kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getLoginPwd() {
        return loginPwd;
    }

    public void setLoginPwd(String loginPwd) {
        this.loginPwd = loginPwd;
    }

    public String getLoginPwdStrength() {
        return loginPwdStrength;
    }

    public void setLoginPwdStrength(String loginPwdStrength) {
        this.loginPwdStrength = loginPwdStrength;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getUserReferee() {
        return userReferee;
    }

    public void setUserReferee(String userReferee) {
        this.userReferee = userReferee;
    }

    public Double getDivRate1() {
        return divRate1;
    }

    public void setDivRate1(Double divRate1) {
        this.divRate1 = divRate1;
    }

    public Double getDivRate2() {
        return divRate2;
    }

    public void setDivRate2(Double divRate2) {
        this.divRate2 = divRate2;
    }

    public String getIdKind() {
        return idKind;
    }

    public void setIdKind(String idKind) {
        this.idKind = idKind;
    }

    public String getIdNo() {
        return idNo;
    }

    public void setIdNo(String idNo) {
        this.idNo = idNo;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getIdFace() {
        return idFace;
    }

    public void setIdFace(String idFace) {
        this.idFace = idFace;
    }

    public String getIdOppo() {
        return idOppo;
    }

    public void setIdOppo(String idOppo) {
        this.idOppo = idOppo;
    }

    public String getIdHold() {
        return idHold;
    }

    public void setIdHold(String idHold) {
        this.idHold = idHold;
    }

    public String getTradePwd() {
        return tradePwd;
    }

    public void setTradePwd(String tradePwd) {
        this.tradePwd = tradePwd;
    }

    public String getTradePwdStrength() {
        return tradePwdStrength;
    }

    public void setTradePwdStrength(String tradePwdStrength) {
        this.tradePwdStrength = tradePwdStrength;
    }

    public String getGoogleSecret() {
        return googleSecret;
    }

    public void setGoogleSecret(String googleSecret) {
        this.googleSecret = googleSecret;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getRespArea() {
        return respArea;
    }

    public void setRespArea(String respArea) {
        this.respArea = respArea;
    }

    public Date getCreateDatetime() {
        return createDatetime;
    }

    public void setCreateDatetime(Date createDatetime) {
        this.createDatetime = createDatetime;
    }

    public String getUpdater() {
        return updater;
    }

    public void setUpdater(String updater) {
        this.updater = updater;
    }

    public Date getUpdateDatetime() {
        return updateDatetime;
    }

    public void setUpdateDatetime(Date updateDatetime) {
        this.updateDatetime = updateDatetime;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Date getLastLogin() {
        return lastLogin;
    }

    public void setLastLogin(Date lastLogin) {
        this.lastLogin = lastLogin;
    }

    public String getLoginNameForQuery() {
        return loginNameForQuery;
    }

    public void setLoginNameForQuery(String loginNameForQuery) {
        this.loginNameForQuery = loginNameForQuery;
    }

    public String getNicknameForQuery() {
        return nicknameForQuery;
    }

    public void setNicknameForQuery(String nicknameForQuery) {
        this.nicknameForQuery = nicknameForQuery;
    }

    public String getMobileForQuery() {
        return mobileForQuery;
    }

    public void setMobileForQuery(String mobileForQuery) {
        this.mobileForQuery = mobileForQuery;
    }

    public String getRealNameForQuery() {
        return realNameForQuery;
    }

    public void setRealNameForQuery(String realNameForQuery) {
        this.realNameForQuery = realNameForQuery;
    }

    public String getProvinceForQuery() {
        return provinceForQuery;
    }

    public void setProvinceForQuery(String provinceForQuery) {
        this.provinceForQuery = provinceForQuery;
    }

    public String getCityForQuery() {
        return cityForQuery;
    }

    public void setCityForQuery(String cityForQuery) {
        this.cityForQuery = cityForQuery;
    }

    public String getAreaForQuery() {
        return areaForQuery;
    }

    public void setAreaForQuery(String areaForQuery) {
        this.areaForQuery = areaForQuery;
    }

    public Date getCreateDatetimeStart() {
        return createDatetimeStart;
    }

    public void setCreateDatetimeStart(Date createDatetimeStart) {
        this.createDatetimeStart = createDatetimeStart;
    }

    public Date getCreateDatetimeEnd() {
        return createDatetimeEnd;
    }

    public void setCreateDatetimeEnd(Date createDatetimeEnd) {
        this.createDatetimeEnd = createDatetimeEnd;
    }

    public String getBankcardFlag() {
        return bankcardFlag;
    }

    public void setBankcardFlag(String bankcardFlag) {
        this.bankcardFlag = bankcardFlag;
    }

    public boolean isLoginPwdFlag() {
        return loginPwdFlag;
    }

    public void setLoginPwdFlag(boolean loginPwdFlag) {
        this.loginPwdFlag = loginPwdFlag;
    }

    public boolean isTradepwdFlag() {
        return tradepwdFlag;
    }

    public void setTradepwdFlag(boolean tradepwdFlag) {
        this.tradepwdFlag = tradepwdFlag;
    }

    public boolean isGoogleAuthFlag() {
        return googleAuthFlag;
    }

    public void setGoogleAuthFlag(boolean googleAuthFlag) {
        this.googleAuthFlag = googleAuthFlag;
    }

    public boolean isEmailBindFlag() {
        return emailBindFlag;
    }

    public void setEmailBindFlag(boolean emailBindFlag) {
        this.emailBindFlag = emailBindFlag;
    }

    public UserIdAuth getUserIdAuth() {
        return userIdAuth;
    }

    public void setUserIdAuth(UserIdAuth userIdAuth) {
        this.userIdAuth = userIdAuth;
    }

    public User getRefereeUser() {
        return refereeUser;
    }

    public void setRefereeUser(User refereeUser) {
        this.refereeUser = refereeUser;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getDiploma() {
        return diploma;
    }

    public void setDiploma(String diploma) {
        this.diploma = diploma;
    }

    public String getOccupation() {
        return occupation;
    }

    public void setOccupation(String occupation) {
        this.occupation = occupation;
    }

    public Date getGradDatetime() {
        return gradDatetime;
    }

    public void setGradDatetime(Date gradDatetime) {
        this.gradDatetime = gradDatetime;
    }

    public String getWorkTime() {
        return workTime;
    }

    public void setWorkTime(String workTime) {
        this.workTime = workTime;
    }

    public String getPdf() {
        return pdf;
    }

    public void setPdf(String pdf) {
        this.pdf = pdf;
    }

    public String getIntroduce() {
        return introduce;
    }

    public void setIntroduce(String introduce) {
        this.introduce = introduce;
    }

    public UserStatistics getUserStatistics() {
        return userStatistics;
    }

    public void setUserStatistics(UserStatistics userStatistics) {
        this.userStatistics = userStatistics;
    }

}
