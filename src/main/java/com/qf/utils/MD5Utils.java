package com.qf.utils;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Oldman 2019/9/10 16:28
 * bug? 不存在的
 */
public class MD5Utils {
    
    public static String md5(String s) {
        try {
            MessageDigest md5 = MessageDigest.getInstance("md5");
            md5.update(s.getBytes());
            byte[] digest = md5.digest();

            // 1正数  0 表示0  -1 表示负数
            //转化为大整数，再转化成字符串返回
            BigInteger bigInteger = new BigInteger(1, digest);
            String secret = bigInteger.toString(16);
            return secret;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }
}
