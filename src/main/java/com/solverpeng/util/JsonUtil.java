package com.solverpeng.util;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * <pre>
 *      author: solverpeng
 *      blog  : http://solverpeng.com
 *      time  : 2017/2/23
 *      desc  : JSON工具类
 * </pre>
 */
public abstract class JsonUtil {
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
    private static final Gson GSON = new GsonBuilder().create();

    //============================== jackson ==============================================

    /**
     * 将 POJO 转换为 JSON
     *
     * @param obj POJO对象
     * @return JSON字符串
     */
    public static <T> String toJson(T obj) {
        String json;
        try {
            json = OBJECT_MAPPER.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return json;
    }

    /**
     * 将 JSON 转换为 POJO
     *
     * @param json JSON字符串
     * @param type POJO Class 类型
     * @return POJO对象
     */
    public static <T> T fromJson(String json, Class<T> type) {
        T pojo;
        try {
            pojo = OBJECT_MAPPER.readValue(json, type);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return pojo;
    }

    /**
     * 将 JSON 转换为 JavaType 类型的对象
     *
     * @param json     JSON 字符串
     * @param javaType JavaType 类型的参数
     * @return 对应类型的一个 Object 对象，使用的时候需要强转为对应的类型
     * @see #getCollectionType(Class, Class[])
     */
    public static Object fromJson(String json, JavaType javaType) {
        Object object = null;
        try {
            object = OBJECT_MAPPER.readValue(json, javaType);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return object;
    }

    /**
     * 根据集合类型和集合中泛型的类型返回对应的集合类型
     *
     * @param collectionClass 集合的类型
     * @param elementClasses  集合中泛型的类型
     * @return 带有泛型类型的集合类型
     */
    public static JavaType getCollectionType(Class<?> collectionClass, Class<?>... elementClasses) {
        return OBJECT_MAPPER.getTypeFactory().constructParametricType(collectionClass, elementClasses);
    }


    //============================== fastjson ==============================================

    /**
     * 使用 fastjson 将Java实体转换为 JSON 串
     *
     * @param obj Java实体
     * @return 转换后的 JSON 串
     */
    public static String bean2Json(Object obj) {
        return JSON.toJSONString(obj);
    }

    /**
     * 使用 fastjson 将JSON串转换为Java实体
     *
     * @param jsonStr  JSON串
     * @param objClass 实体类型
     * @return 转换后的Java实体
     */
    public static <T> T json2Bean(String jsonStr, Class<T> objClass) {
        return JSON.parseObject(jsonStr, objClass);
    }

    /**
     * 使用 fastjson 将JSON数组字符串转换为对应集合
     *
     * @param jsonArrayStr JSON数组字符串
     * @param classType    集合泛型类型
     * @return 转换后的集合
     */
    public static <T> List<T> jsonArray2Beans(String jsonArrayStr, Class<T> classType) {
        return JSONArray.parseArray(jsonArrayStr, classType);
    }

    //============================== gson ==============================================

    /**
     * 使用 gson 将Java实体对象转换为JSON串
     *
     * @param object Java实体对象
     * @return JSON串
     */
    public static String objectToJson(Object object) {
        return GSON.toJson(object);
    }

    /**
     * 使用 gson 将JSON串转换为对应的实体类型的对象
     *
     * @param jsonString 需要转换的 JSON 串
     * @param classType  对应实体类型
     * @return 对应的实体类型的对象
     */
    public static <T> T toBean(String jsonString, Class<T> classType) {
        return GSON.fromJson(jsonString, classType);
    }

    //============================== test ==============================================
    static class User {
        private String userName;

        public User() {
        }

        public User(String userName) {
            this.userName = userName;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        @Override
        public String toString() {
            return "User{" +
                    "userName='" + userName + '\'' +
                    '}';
        }
    }

    public static void main(String[] args) throws IOException {
        User user = new User("tom");
        User user1 = new User("jerry");
        List<User> users = Arrays.asList(user, user1);
        String jsonStr = toJson(users);

        System.out.println("JACKSON 将JSON串转换为对应的集合类型的实体：");
        JavaType collectionType = getCollectionType(ArrayList.class, User.class);
        List<User> userList = (List<User>) fromJson(jsonStr, collectionType);
        System.out.println(userList);

        System.out.println("GSON 将JSON串转换为对应的集合实体类型：");
        Type listType = new TypeToken<ArrayList<User>>() {
        }.getType();
        List<User> userList2 = new Gson().fromJson(jsonStr, listType);
        System.out.println(userList2);
    }
}