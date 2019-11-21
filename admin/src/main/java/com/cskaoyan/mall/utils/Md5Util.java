package com.cskaoyan.mall.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Md5Util {

    public static String getMd5(String content) throws NoSuchAlgorithmException {

        MessageDigest messageDigest = MessageDigest.getInstance("md5");
        byte[] bytes = content.getBytes();
        byte[] digest = messageDigest.digest(bytes);
        //将byte转换成16进制值字符串的形式
        StringBuffer stringBuffer = new StringBuffer();
        for (byte b : digest) {
            int i = b & 0xff; //0-255 00-ff
            String s = Integer.toHexString(i);
            //0a
            if (s.length() == 1){
                stringBuffer.append(0);
            }
            stringBuffer.append(s);

        }
        return stringBuffer.toString();

    }
}
