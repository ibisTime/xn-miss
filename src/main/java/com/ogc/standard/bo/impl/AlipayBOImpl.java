package com.ogc.standard.bo.impl;

import java.util.Properties;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import com.ogc.standard.bo.IAlipayBO;
import com.ogc.standard.domain.CallbackResult;
import com.ogc.standard.exception.BizException;
import com.ogc.standard.http.PostSimulater;

@Component
public class AlipayBOImpl implements IAlipayBO {
    static Logger logger = Logger.getLogger(AlipayBOImpl.class);

    public static final String CHARSET = "utf-8";

    @Override
    public void doBizCallback(CallbackResult callbackResult) {
        try {
            Properties formProperties = new Properties();
            formProperties.put("isSuccess", callbackResult.isSuccess());
            formProperties.put("systemCode", callbackResult.getSystemCode());
            formProperties.put("companyCode", callbackResult.getCompanyCode());
            formProperties.put("payGroup", callbackResult.getPayGroup());
            formProperties.put("payCode", callbackResult.getJourCode());
            formProperties.put("bizType", callbackResult.getBizType());
            formProperties.put("transAmount", callbackResult.getTransAmount());
            PostSimulater.requestPostForm(callbackResult.getUrl(),
                formProperties);
        } catch (Exception e) {
            throw new BizException("xn000000", "回调业务biz异常");
        }
    }

}
