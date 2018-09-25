package com.ogc.standard.dto.req;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

public class XN805041Req extends BaseReq {

    private static final long serialVersionUID = 8744249340472332893L;

    @NotBlank
    private String mobile; // 手机号

    @Length(min = 2, max = 10, message = "昵称长度必须在2-10之间")
    private String nickname; // 昵称

    @NotBlank
    private String loginPwd; // 登录密码

    private String userReferee; // 推荐人(选填)

    @NotBlank
    private String smsCaptcha; // 手机验证码

    private String longitude;// 经度

    private String latitude;// 纬度

    private String province; // 省(选填)

    private String city; // 市(选填)

    private String area; // 区(选填)

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

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
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

    public String getUserReferee() {
        return userReferee;
    }

    public void setUserReferee(String userReferee) {
        this.userReferee = userReferee;
    }

    public String getSmsCaptcha() {
        return smsCaptcha;
    }

    public void setSmsCaptcha(String smsCaptcha) {
        this.smsCaptcha = smsCaptcha;
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

}
