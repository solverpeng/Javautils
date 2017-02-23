package com.solverpeng.util;

import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.beanutils.converters.DateConverter;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

/**
 * <pre>
 *      author: solverpeng
 *      blog  : http://solverpeng.com
 *      time  : 2017/2/22
 *      desc  : 反射工具类
 * </pre>
 */
public class ReflectionUtils {
    private static final Logger LOGGER = LoggerFactory.getLogger(ReflectionUtils.class);

    /**
     * 创建实例
     *
     * @param cls 需要创建实例的 Class
     * @return 实例对象
     */
    public static Object newInstance(Class<?> cls) {
        Object instance;

        try {
            instance = cls.newInstance();
        } catch (Exception e) {
            LOGGER.error("new instance failure", e);
            throw new RuntimeException(e);
        }

        return instance;
    }

    /**
     * 调用方法
     *
     * @param obj    调用方法的对象
     * @param method 调用的方法
     * @param args   方法的参数
     * @return 调用结果
     */
    public static Object invokeMethod(Object obj, Method method, Object... args) {
        Object result;
        try {
            method.setAccessible(true);
            result = method.invoke(obj, args);
        } catch (Exception e) {
            LOGGER.error("invoke method failure", e);
            throw new RuntimeException(e);
        }
        return result;
    }

    /**
     * 设置属性
     *
     * @param obj   设置属性的对象
     * @param field 属性对象
     * @param value 属性值
     */
    public static void setField(Object obj, Field field, Object value) {
        try {
            field.setAccessible(true);
            field.set(obj, value);
        } catch (IllegalAccessException e) {
            LOGGER.error("set field failure", e);
            throw new RuntimeException(e);
        }

    }

    /**
     * 将反射时的 "检查异常" 转换为 "运行时异常"
     */
    public static IllegalArgumentException convertToUncheckedException(
            Exception ex) {
        if (ex instanceof IllegalAccessException
                || ex instanceof IllegalArgumentException
                || ex instanceof NoSuchMethodException) {
            throw new IllegalArgumentException("反射异常", ex);
        } else {
            throw new IllegalArgumentException(ex);
        }
    }

    /**
     * 转换字符串类型到 toType 类型, 或 toType 转为字符串
     *
     * @param value:  待转换的字符串
     * @param toType: 提供类型信息的 Class, 可以是基本数据类型的包装类或指定格式日期型
     */
    public static <T> T convertValue(Object value, Class<T> toType) {
        try {
            DateConverter dc = new DateConverter();
            dc.setUseLocaleFormat(true);
            dc.setPatterns(new String[]{ConstUtil.DATE_PATTERN_YYYY_MM_DD, ConstUtil.DATE_PATTERN_YYYY_MM_DD_HH_MM_SS});
            ConvertUtils.register(dc, Date.class);
            return (T) ConvertUtils.convert(value, toType);
        } catch (Exception e) {
            e.printStackTrace();
            throw convertToUncheckedException(e);
        }
    }

    /**
     * 通过getter方法提取集合中的对象的属性, 组成 List
     *
     * @param collection:   来源集合
     * @param propertyName: 要提取的属性名
     * @return 对象属性组合成的List
     */
    public static List fetchElementPropertyToList(Collection collection, String propertyName) {
        List list = new ArrayList();
        try {
            for (Object obj : collection) {
                list.add(PropertyUtils.getProperty(obj, propertyName));
            }
        } catch (Exception e) {
            e.printStackTrace();
            convertToUncheckedException(e);
        }
        return list;
    }

    /**
     * 通过getter方法提取集合中的对象属性, 组合成由分隔符分隔的字符串
     *
     * @param collection:   来源集合
     * @param propertyName: 要提取的属性名
     * @param seperator:    分隔符
     */
    public static String fetchElementPropertyToString(Collection collection, String propertyName, String seperator) {
        List list = fetchElementPropertyToList(collection, propertyName);
        return StringUtils.join(list, seperator);
    }

    /**
     * 通过反射, 获得定义 Class 时声明的父类的泛型参数列表对应参数的类型
     * <p>${@code public EmployeeDao extends BaseDao<Employee, String> }</p>
     *
     * @param clazz 需要获取父类泛型参数类型的类
     * @param index 泛型参数列表索引
     * @return 泛型参数类型
     */
    public static Class getSuperClassGenricType(Class clazz, int index) {
        Type genType = clazz.getGenericSuperclass();
        if (!(genType instanceof ParameterizedType)) {
            LOGGER.warn(clazz.getSimpleName() + "'s superclass not ParameterizedType");
            return Object.class;
        }
        Type[] params = ((ParameterizedType) genType).getActualTypeArguments();
        if (index >= params.length || index < 0) {
            LOGGER.warn("Index: " + index + ", Size of " + clazz.getSimpleName() + "'s Parameterized Type: " + params.length);
            return Object.class;
        }
        if (!(params[index] instanceof Class)) {
            LOGGER.warn(clazz.getSimpleName() + " not set the actual class on superclass generic parameter");
            return Object.class;
        }
        return (Class) params[index];
    }

    /**
     * 通过反射, 获得 Class 定义中声明的父类的泛型参数类型
     * <p>${@code public EmployeeDao extends BaseDao<Employee, String> }</p>
     *
     * @param clazz 需要获取父类泛型参数类型的类
     * @return 父类泛型参数类型
     */
    @SuppressWarnings("unchecked")
    public static <T> Class<T> getSuperGenericType(Class clazz) {
        return getSuperClassGenricType(clazz, 0);
    }

    /**
     * 循环向上转型, 获取对象的 DeclaredMethod
     *
     * @param object         对象
     * @param methodName     方法名
     * @param parameterTypes 参数类型
     * @return 公共访问权限的方法对象
     */
    public static Method getDeclaredMethod(Object object, String methodName, Class<?>[] parameterTypes) {
        for (Class<?> superClass = object.getClass(); superClass != Object.class; superClass = superClass.getSuperclass()) {
            try {
                return superClass.getDeclaredMethod(methodName, parameterTypes);
            } catch (NoSuchMethodException e) {
            }
        }
        return null;
    }

    /**
     * 使 filed 变为可访问
     *
     * @param field 需要设置为可访问的属性
     */
    public static void makeAccessible(Field field) {
        if (!Modifier.isPublic(field.getModifiers())) {
            field.setAccessible(true);
        }
    }

    /**
     * 循环向上转型, 获取对象的 DeclaredField
     *
     * @param object    对象
     * @param filedName 对象的属性名
     * @return 公共访问权限的属性对象
     */
    public static Field getDeclaredField(Object object, String filedName) {
        for (Class<?> superClass = object.getClass(); superClass != Object.class; superClass = superClass.getSuperclass()) {
            try {
                return superClass.getDeclaredField(filedName);
            } catch (NoSuchFieldException e) {
            }
        }
        return null;
    }

    /**
     * 直接调用对象方法, 而忽略修饰符(private, protected)
     *
     * @param object         对象
     * @param methodName     方法名
     * @param parameterTypes 参数类型
     * @param parameters     参数
     * @return 方法调用结果
     */
    public static Object invokeMethod(Object object, String methodName, Class<?>[] parameterTypes, Object[] parameters)
            throws InvocationTargetException {
        Method method = getDeclaredMethod(object, methodName, parameterTypes);
        if (method == null) {
            throw new IllegalArgumentException("Could not find method [" + methodName + "] on target [" + object + "]");
        }
        method.setAccessible(true);
        try {
            return method.invoke(object, parameters);
        } catch (IllegalAccessException e) {
            LOGGER.error("不可能抛出的异常:{}", e.getMessage());
        }
        return null;
    }

    /**
     * 直接设置对象属性值, 忽略 private/protected 修饰符, 也不经过 setter
     *
     * @param object    对象
     * @param fieldName 对象的属性
     */
    public static void setFieldValue(Object object, String fieldName, Object value) {
        Field field = getDeclaredField(object, fieldName);
        if (field == null) {
            throw new IllegalArgumentException("Could not find field [" + fieldName + "] on target [" + object + "]");
        }
        makeAccessible(field);
        try {
            field.set(object, value);
        } catch (IllegalAccessException e) {
            LOGGER.error("不可能抛出的异常:{}", e.getMessage());
        }
    }

    /**
     * 直接读取对象的属性值, 忽略 private/protected 修饰符, 也不经过 getter
     *
     * @param object    对象
     * @param fieldName 对象的属性
     * @return 对应属性的值
     */
    public static Object getFieldValue(Object object, String fieldName) {
        Field field = getDeclaredField(object, fieldName);
        if (field == null) {
            throw new IllegalArgumentException("Could not find field [" + fieldName + "] on target [" + object + "]");
        }
        makeAccessible(field);
        Object result = null;
        try {
            result = field.get(object);
        } catch (IllegalAccessException e) {
            LOGGER.error("不可能抛出的异常{}", e.getMessage());
        }
        return result;
    }
}
