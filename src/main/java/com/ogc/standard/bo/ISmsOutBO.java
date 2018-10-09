package com.ogc.standard.bo;

public interface ISmsOutBO {
    /**
     * 发送验证码
     * @param mobile
     * @param bizType
     * @create: 2017年2月13日 下午3:02:02 xieyj
     * @history:
     */
    public void sendSmsCaptcha(String mobile, String bizType);

    public void checkCaptcha(String mobile, String captcha, String bizType);

    public void sendSmsOut(String mobile, String content, String bizType);

    void sendSmsOut(String mobile, String content, String companyCode,
            String systemCode);

    public void sendEmailCaptcha(String mobile, String bizType);
    
    public void sentContent(String userId, String content);
}
