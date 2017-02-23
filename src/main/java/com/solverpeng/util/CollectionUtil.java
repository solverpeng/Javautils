package com.solverpeng.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * <pre>
 *      author: solverpeng
 *      blog  : http://solverpeng.com
 *      time  : 2017/2/23
 *      desc  : 集合工具类
 * </pre>
 */
public abstract class CollectionUtil {

    /**
     * 返回a+b的新List
     *
     * @param a 集合a
     * @param b 集合b
     * @return 返回的集合结果
     */
    public static <T> List<T> union(final Collection<T> a, final Collection<T> b) {
        List<T> result = new ArrayList<T>(a);
        result.addAll(b);
        return result;
    }

    /**
     * 返回a-b的新List
     *
     * @param a 集合a
     * @param b 集合b
     * @return 集合a去除集合b后的集合结果
     */
    public static <T> List<T> subtract(final Collection<T> a, final Collection<T> b) {
        List<T> list = new ArrayList<T>(a);
        list.removeAll(b);
        return list;
    }

    /**
     * 返回a与b的交集的新List
     *
     * @param a 集合a
     * @param b 集合b
     * @return 集合a与集合b的交集集合
     */
    public static <T> List<T> intersection(Collection<T> a, Collection<T> b) {
        List<T> list = new ArrayList<T>();
        for (T element : a) {
            if (b.contains(element)) {
                list.add(element);
            }
        }
        return list;
    }

}
