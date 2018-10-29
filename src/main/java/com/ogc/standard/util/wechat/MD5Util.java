package com.ogc.standard.util.wechat;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.apache.commons.lang3.StringUtils;

public class MD5Util {

    public static String md5(String pswd) {
        if (StringUtils.isNotBlank(pswd)) {
            try {
                MessageDigest md5 = MessageDigest.getInstance("MD5");
                StringBuffer strbuf = new StringBuffer();

                md5.update(pswd.getBytes(), 0, pswd.length());
                byte[] digest = md5.digest();

                for (int i = 0; i < digest.length; i++) {
                    strbuf.append(byte2Hex(digest[i]));
                }

                return strbuf.toString();
            } catch (NoSuchAlgorithmException e) {
                throw new RuntimeException(e);
            }
        } else {
            return null;
        }
    }

    private static String byte2Hex(byte b) {
        int value = (b & 0x7F) + (b < 0 ? 0x80 : 0);
        return (value < 0x10 ? "0" : "")
                + Integer.toHexString(value).toLowerCase();
    }

    private static String byteArrayToHexString(byte b[]) {
        StringBuffer resultSb = new StringBuffer();
        for (int i = 0; i < b.length; i++)
            resultSb.append(byteToHexString(b[i]));

        return resultSb.toString();
    }

    private static String byteToHexString(byte b) {
        int n = b;
        if (n < 0)
            n += 256;
        int d1 = n / 16;
        int d2 = n % 16;
        return hexDigits[d1] + hexDigits[d2];
    }

    public static String MD5Encode(String origin, String charsetname) {
        String resultString = null;
        try {
            resultString = new String(origin);
            MessageDigest md = MessageDigest.getInstance("MD5");
            if (charsetname == null || "".equals(charsetname))
                resultString = byteArrayToHexString(md.digest(resultString
                    .getBytes()));
            else
                resultString = byteArrayToHexString(md.digest(resultString
                    .getBytes(charsetname)));
        } catch (Exception exception) {
        }
        return resultString;
    }

    private static final String hexDigits[] = { "0", "1", "2", "3", "4", "5",
            "6", "7", "8", "9", "a", "b", "c", "d", "e", "f" };

}
