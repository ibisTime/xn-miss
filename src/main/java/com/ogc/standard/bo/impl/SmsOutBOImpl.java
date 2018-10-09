package com.ogc.standard.bo.impl;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import com.ogc.standard.bo.ISmsOutBO;
import com.ogc.standard.dto.req.XN804080Req;
import com.ogc.standard.dto.req.XN804081Req;
import com.ogc.standard.dto.req.XN804082Req;
import com.ogc.standard.dto.req.XN804083Req;
import com.ogc.standard.dto.res.BooleanRes;
import com.ogc.standard.dto.res.PKCodeRes;
import com.ogc.standard.enums.ESystemCode;
import com.ogc.standard.http.BizConnecter;
import com.ogc.standard.http.JsonUtils;
import com.std.account.dto.req.XN001200Req;

/** 
 * @author: xieyj 
 * @since: 2016年5月25日 上午8:15:46 
 * @history:
 */
@Component
public class SmsOutBOImpl implements ISmsOutBO {
    static Logger logger = Logger.getLogger(SmsOutBOImpl.class);

    @Override
    public void sendSmsCaptcha(String mobile, String bizType) {
        try {
            XN804081Req req = new XN804081Req();
            req.setMobile(mobile);
            req.setBizType(bizType);
            req.setSystemCode(ESystemCode.BZ.getCode());
            req.setCompanyCode(ESystemCode.BZ.getCode());
            BizConnecter.getBizData("804081", JsonUtils.object2Json(req),
                PKCodeRes.class);
        } catch (Exception e) {
            logger.error("调用内部短信发送服务异常");
        }
    }

    @Override
    public void checkCaptcha(String mobile, String captcha, String bizType) {
        XN804082Req req = new XN804082Req();
        req.setMobile(mobile);
        req.setCaptcha(captcha);
        req.setBizType(bizType);
        req.setSystemCode(ESystemCode.BZ.getCode());
        req.setCompanyCode(ESystemCode.BZ.getCode());
        BizConnecter.getBizData("804082", JsonUtils.object2Json(req),
            BooleanRes.class);
    }

    @Override
    public void sendSmsOut(String mobile, String content, String bizType) {
        try {
            XN804080Req req = new XN804080Req();
            req.setMobile(mobile);
            req.setContent(content);
            req.setType("M");
            req.setCompanyCode(ESystemCode.BZ.getCode());
            req.setSystemCode(ESystemCode.BZ.getCode());
            BizConnecter.getBizData("804080", JsonUtils.object2Json(req),
                PKCodeRes.class);
        } catch (Exception e) {
            logger.error("调用短信发送服务异常");
        }
    }

    @Override
    public void sendSmsOut(String mobile, String content, String companyCode,
            String systemCode) {
        try {
            XN804080Req req = new XN804080Req();
            req.setMobile(mobile);
            req.setContent(content);
            req.setType("M");
            req.setCompanyCode(companyCode);
            req.setSystemCode(systemCode);
            BizConnecter.getBizData("804080", JsonUtils.object2Json(req),
                PKCodeRes.class);
        } catch (Exception e) {
            logger.error("调用短信发送服务异常");
        }
    }

    @Override
    public void sendEmailCaptcha(String email, String bizType) {
        try {
            XN804083Req req = new XN804083Req();
            req.setEmail(email);
            req.setBizType(bizType);
            req.setSystemCode(ESystemCode.BZ.getCode());
            req.setCompanyCode(ESystemCode.BZ.getCode());
            BizConnecter.getBizData("804083", JsonUtils.object2Json(req),
                PKCodeRes.class);
        } catch (Exception e) {
            logger.error("调用邮件发送验证码异常");
        }
    }
    
    @Override
    public void sentContent(String ownerId, String content) {
        try {
            XN001200Req req = new XN001200Req();
            req.setTokenId(ownerId);
            req.setUserId(ownerId);
            req.setContent(content);
            BizConnecter.getBizData("001200", JsonUtils.object2Json(req),
                Object.class);
        } catch (Exception e) {
            logger.error("调用短信发送服务异常, 原因：" + e.getMessage());
        }
    }
}
