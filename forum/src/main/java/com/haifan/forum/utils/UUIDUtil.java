package com.haifan.forum.utils;


import java.util.UUID;

/**
 * 生成随机的uuid
 * @Author HaiFan
 */
public class UUIDUtil {

    /**
     * @return 生成一个标准的UUID
     */
    public static String UUID_36() {
        return UUID.randomUUID().toString();
    }

    /**
     * @return 生成一个32位的UUID
     */
    public static String UUID_32() {
        return UUID.randomUUID().toString().replace("-", "");
    }
}
