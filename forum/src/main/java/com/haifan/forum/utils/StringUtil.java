package com.haifan.forum.utils;

public class StringUtil {

    /**
     * @param value 字符串
     * @return 判断是否位空
     */
    public static boolean isEmpty (String value) {
        return value == null || value.length() == 0;
    }
}
