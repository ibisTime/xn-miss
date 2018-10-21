package com.ogc.standard.common;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class PropertiesUtil {

    private static Properties props;
    static {
        props = new Properties();
        try {
            props.load(Thread.currentThread().getContextClassLoader()
                .getResourceAsStream("config.properties"));
        } catch (FileNotFoundException e) {
            throw new RuntimeException("找不到config.properties文件", e);
        } catch (IOException e) {
            throw new RuntimeException("读取config.properties文件出错", e);
        }
    }

    public static String getProperty(String key) {
        if (props == null) {
            // throw new Exception("配置文件初始化失败");
        }
        return props.getProperty(key);
    }

    public static final class Config {
        public static String SMS_URL = props.getProperty("SMS_URL");

        public static String CERTI_URL = props.getProperty("CERTI_URL");

        public static String CORE_URL = props.getProperty("CORE_URL");// 基础数据

        public static String TOKEN_URL = props.getProperty("TOKEN_URL");

        public static String KEY_STORE_PATH = props
            .getProperty("KEY_STORE_PATH");

        public static String USER_URL = props.getProperty("USER_URL");

        public static String WECHAT_APP_BACKURL = props
            .getProperty("WECHAT_APP_BACKURL");

        public static String WECHAT_H5_BACKURL = props
            .getProperty("WECHAT_H5_BACKURL");

        public static String WECHAT_H5_QzBACKURL = props
            .getProperty("WECHAT_H5_QzBACKURL");

        public static String WECHAT_NATIVE_BACKURL = props
            .getProperty("WECHAT_NATIVE_BACKURL");

        public static String ALIPAY_APP_BACKURL = props
            .getProperty("ALIPAY_APP_BACKURL");

        public static String SELF_PAY_BACKURL = props
            .getProperty("SELF_PAY_BACKURL");

    }
}
