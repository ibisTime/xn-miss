/**
 * @Title PhoneUtils.java 
 * @Package com.hsnet.pz.utils 
 * @Description 
 * @author miyb  
 * @date 2014-8-19 下午3:39:39 
 * @version V1.0   
 */
package com.ogc.standard.common;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;

import com.google.i18n.phonenumbers.NumberParseException;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.Phonenumber.PhoneNumber;
import com.ogc.standard.exception.ParaException;

/** 
 * @author: miyb 
 * @since: 2014-8-19 下午3:39:39 
 * @history:
 */
public class PhoneUtil {
    /** 
     * 手机号验证 
     * @param  mobile 
     * @return 验证通过返回true 
     */
    public static void checkMobile(String mobile) {
        // if (StringUtils.isBlank(mobile)) {
        // throw new ParaException("FA0000", "手机号格式非法");
        // }
        // Pattern p = Pattern.compile("^1[34578]\\d{9}$"); // 验证手机号
        // Matcher m = p.matcher(mobile);
        // if (!m.matches()) {
        // throw new ParaException("FA0000", "手机号格式非法");
        // }
    }

    /** 
     * 国际手机号验证 
     * @param  mobile 
     * @return 验证通过返回true 
     */
    public static void checkInterMobile(String mobile) {
        if (StringUtils.isBlank(mobile)) {
            throw new ParaException("FA0000", "手机号格式非法");
        }

        PhoneNumberUtil phoneUtil = PhoneNumberUtil.getInstance();
        try {
            PhoneNumber swissNumberProto = phoneUtil.parse(mobile, "CH");
        } catch (NumberParseException e) {
            throw new ParaException("FA0000", "手机号格式非法");
        }

    }

    /** 
     * 手机号验证 
     * @param  mobile 
     * @return 验证通过返回true 
     */
    public static void checkMobile(String mobile, String message) {
        if (StringUtils.isBlank(mobile)) {
            throw new ParaException("FA0000", message);
        }
        Pattern p = Pattern.compile("^1[34578]\\d{9}$"); // 验证手机号
        Matcher m = p.matcher(mobile);
        if (!m.matches()) {
            throw new ParaException("FA0000", message);
        }
    }

    /** 
     * 隐藏手机号
     * @param  mobile 
     * @return 验证通过返回true 
     */
    public static String hideMobile(String mobile) {
        // 判断格式
        PhoneUtil.checkMobile(mobile);
        StringBuffer sb = new StringBuffer(mobile);
        return sb.replace(3, 7, "****").toString();
    }

    public static void main(String[] args) {
        PhoneUtil.checkInterMobile("1526850148");
    }
}
