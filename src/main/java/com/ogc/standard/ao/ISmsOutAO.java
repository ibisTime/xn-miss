package com.ogc.standard.ao;

public interface ISmsOutAO {
    // 4位短信验证码
    public void sendSmsCaptcha(String mobile, String bizType, String language);

}
