package com.haifan.forum.utils;

import org.apache.commons.codec.digest.DigestUtils;

/**
 * 用于MD5加密的工具类
 *
 * @Author HaiFan
 */

public class MD5Util {


    /**
     * 对字符串进行md5加密
     * @param str
     * @return 加密后的密码
     */
    public static String md5(String str) {
        return DigestUtils.md5Hex(str);
    }

    /**
     * @param str 明文密码
     * @param salt 扰动字符
     * @return 密文
     */
    public static String md5Salt (String str, String salt) {
        return md5(md5(str) + salt);
    }
}
