package com.zyw.recommend_system.utils;

import java.util.UUID;

/**
 * 为用户生成唯一标识 免登录 uniqueID
 */
public class UUIDUtil {

    public static String generateGUID(){
        return  UUID.randomUUID().toString();
    }
}
