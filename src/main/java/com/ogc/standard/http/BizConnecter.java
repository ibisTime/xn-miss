/**
 * @Title BizConnecter.java 
 * @Package com.ibis.pz.http 
 * @Description 
 * @author miyb  
 * @date 2015-5-12 下午9:44:59 
 * @version V1.0   
 */
package com.ogc.standard.http;

import java.lang.reflect.Type;
import java.util.Properties;

import com.ogc.standard.common.PropertiesUtil;
import com.ogc.standard.core.RegexUtils;
import com.ogc.standard.exception.BizException;

/** 
 * @author: miyb 
 * @since: 2015-5-12 下午9:44:59 
 * @history:
 */
public class BizConnecter {
    public static final String YES = "0";

    public static final String CORE_URL = PropertiesUtil.Config.CORE_URL;

    public static final String SMS_URL = PropertiesUtil.Config.SMS_URL;

    public static final String CERTI_URL = PropertiesUtil.Config.CERTI_URL;

    public static final String BC_WALLET_URL = PropertiesUtil.Config.BC_WALLET_URL;

    public static final String BLOCKCHAIN_DATA_URL = PropertiesUtil.Config.BLOCKCHAIN_DATA_URL;

    public static final String CTQ_URL = PropertiesUtil.Config.CTQ_URL;

    public static final String POST_URL = "...";

    public static <T> T getBizData(String code, String json, Class<T> clazz) {
        String data = getBizData(code, json);
        return JsonUtils.json2Bean(data, clazz);
    }

    public static <T> T getBizData(String code, String json, Type type) {
        String data = getBizData(code, json);
        return JsonUtils.json2ComplexObj(data, type);
    }

    public static String getBizData(String code, String json) {
        String data = null;
        String resJson = null;
        try {
            Properties formProperties = new Properties();
            formProperties.put("code", code);
            formProperties.put("json", json);
            resJson = PostSimulater.requestPostForm(getPostUrl(code),
                formProperties);
        } catch (Exception e) {
            throw new BizException("Biz000", "链接请求超时，请联系管理员");
        }
        // 开始解析响应json
        String errorCode = RegexUtils.find(resJson, "errorCode\":\"(.+?)\"", 1);
        String errorInfo = RegexUtils.find(resJson, "errorInfo\":\"(.+?)\"", 1);
        if (YES.equalsIgnoreCase(errorCode)) {
            data = RegexUtils.find(resJson, "data\":(.*)\\}", 1);
        } else {
            System.out
                .println("*******************posterrorstart*************************");
            System.out.println("code:\n" + code);
            System.out.println("json:\n" + json);
            System.out.println("errorInfo:\n" + errorInfo);
            System.out
                .println("*******************posterrorend*******************************");
            throw new BizException("Biz000", errorInfo);
        }
        return data;
    }

    private static String getPostUrl(String code) {
        String postUrl = POST_URL;
        if (code.startsWith("798")) {
            postUrl = CERTI_URL;
        } else if (code.startsWith("804")) {
            postUrl = SMS_URL;
        } else if (code.startsWith("802")) {
            postUrl = BC_WALLET_URL;
        } else if (code.startsWith("660")) {
            postUrl = CORE_URL;
        } else if (code.equals("626206") || code.equals("626026")) {
            postUrl = BLOCKCHAIN_DATA_URL;
        } else if (code.equals("626020") || code.equals("626100")
                || code.equals("626080") || code.equals("626000")
                || code.equals("626140") || code.equals("626120")) {
            postUrl = CTQ_URL;
        }
        return postUrl;
    }
}
