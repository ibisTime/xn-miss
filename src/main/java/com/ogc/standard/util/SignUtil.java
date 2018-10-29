/**
 * @Title SignUtil.java 
 * @Package com.std.sms.util 
 * @Description 
 * @author xieyj  
 * @date 2016年12月2日 下午2:08:45 
 * @version V1.0   
 */
package com.ogc.standard.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

/** 
 * @author: xieyj 
 * @since: 2016年12月2日 下午2:08:45 
 * @history:
 */
public class SignUtil {
    /**
     * 验证签名
     * @param signature
     * @param timestamp
     * @param nonce
     * @return
     */
    public static String getSignature(String jsapi_ticket, String timestamp,
            String noncestr, String url) {
        String[] paramArr = new String[] { "jsapi_ticket=" + jsapi_ticket,
                "timestamp=" + timestamp, "noncestr=" + noncestr, "url=" + url };
        // 字典序排序
        Arrays.sort(paramArr);
        // 将排序后的结果拼接成一个字符串
        String content = paramArr[0].concat("&" + paramArr[1])
            .concat("&" + paramArr[2]).concat("&" + paramArr[3]);
        MessageDigest md = null;
        String result = null;

        try {
            md = MessageDigest.getInstance("SHA-1");
            // 将三个参数字符串拼接成一个字符串进行sha1加密
            byte[] digest = md.digest(content.toString().getBytes());
            result = byteToStr(digest);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 将字节数组转换为十六进制字符串
     * 
     * @param byteArray
     * @return
     */
    private static String byteToStr(byte[] byteArray) {
        String strDigest = "";
        for (int i = 0; i < byteArray.length; i++) {
            strDigest += byteToHexStr(byteArray[i]);
        }
        return strDigest;
    }

    /**
     * 将字节转换为十六进制字符串
     * 
     * @param mByte
     * @return
     */
    private static String byteToHexStr(byte mByte) {
        char[] Digit = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a',
                'b', 'c', 'd', 'e', 'f' };
        char[] tempArr = new char[2];
        tempArr[0] = Digit[(mByte >>> 4) & 0X0F];
        tempArr[1] = Digit[mByte & 0X0F];

        String s = new String(tempArr);
        return s;
    }

    public static void sort(String a[]) {
        for (int i = 0; i < a.length - 1; i++) {
            for (int j = i + 1; j < a.length; j++) {
                if (a[j].compareTo(a[i]) < 0) {
                    String temp = a[i];
                    a[i] = a[j];
                    a[j] = temp;
                }
            }
        }
    }
}
