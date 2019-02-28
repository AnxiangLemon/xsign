package com.lemon.xsign.alibaba;

import java.security.MessageDigest;

/**
 * Created by AnxiangLemon on 2019/2/12.
 */

public class SecurityUtils {

    public static String convertNull2Default(String str) {
        return str == null ? "" : str;
    }


    public static String getMd5(String str) {
        if (StringUtils.isBlank(str)) {
            return str;
        }
        try {
            MessageDigest instance = MessageDigest.getInstance("MD5");
            instance.update(str.getBytes("utf-8"));
            byte[] digest = instance.digest();
            StringBuffer stringBuffer = new StringBuffer();
            for (byte b : digest) {
                String toHexString = Integer.toHexString(b & 255);
                while (toHexString.length() < 2) {
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("0");
                    stringBuilder.append(toHexString);
                    toHexString = stringBuilder.toString();
                }
                stringBuffer.append(toHexString);
            }
            return stringBuffer.toString();
        } catch (Throwable e) {
            StringBuilder stringBuilder2 = new StringBuilder();
            stringBuilder2.append("[getMd5] compute md5 value failed for source str=");
            stringBuilder2.append(str);
            return null;
        }
    }

}