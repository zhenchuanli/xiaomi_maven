package com.qf.utils;

/**
 * Oldman 2019/9/10 16:15
 * bug? 不存在的
 */
public class StringUtils {

    public static boolean isEmpty(String s) {
        if (s == null || s.trim().length() == 0) {
            return true;
        }
        return false;
    }
}
