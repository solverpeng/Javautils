package com.solverpeng.util;

import java.util.UUID;

/**
 * <pre>
 *      author: solverpeng
 *      blog  : http://solverpeng.com
 *      time  : 2017/2/22
 *      desc  : Uuid工具类
 * </pre>
 */
public abstract class UuidUtil {

    /**
     * 生成 Uuid
     *
     * @return 生成的 Uuid
     */
    public static String generateUuid() {
        String uuidString = UUID.randomUUID().toString();
        return uuidString.replaceAll("-", "");
    }

}
