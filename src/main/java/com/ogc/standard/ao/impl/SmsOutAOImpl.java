package com.ogc.standard.ao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ogc.standard.ao.ISmsOutAO;
import com.ogc.standard.bo.ISmsOutBO;
import com.ogc.standard.bo.IUserBO;
import com.ogc.standard.enums.ECaptchaType;

@Service
public class SmsOutAOImpl implements ISmsOutAO {

    @Autowired
    ISmsOutBO smsOutBO;

    @Autowired
    IUserBO userBO;

    @Override
    public void sendSmsCaptcha(String mobile, String bizType, String language) {
        if (ECaptchaType.C_REG.getCode().equals(bizType)) {
            userBO.isMobileExist(mobile);
        }
        smsOutBO.sendSmsCaptcha(mobile, bizType);
    }
}
