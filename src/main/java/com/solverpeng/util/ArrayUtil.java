package com.solverpeng.util;

import org.apache.commons.lang3.ArrayUtils;

/**
 * <pre>
 *      author: solverpeng
 *      blog  : http://solverpeng.com
 *      time  : 2017/2/23
 *      desc  : 数组工具类
 * </pre>
 */
public abstract class ArrayUtil {
    /**
     * 判断数组是否为非空
     *
     * @param array 需要判断的数组
     */
    public static boolean isNotEmpty(Object[] array) {
        return !ArrayUtils.isEmpty(array);
    }

    /**
     * 判断数组是否为空
     *
     * @param array 需要判断的数组
     */
    public static boolean isEmpty(Object[] array) {
        return ArrayUtils.isEmpty(array);
    }


}
