package com.mmall.util;

import com.google.common.collect.Lists;
import com.mmall.pojo.User;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializationConfig;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.type.JavaType;
import org.codehaus.jackson.type.TypeReference;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by Isaac on 2018/8/8.
 */
@Slf4j
public class JsonUtil {
    private static ObjectMapper objectMapper = new ObjectMapper();

    static {
//        对象的所有属性都序列化
        objectMapper.setSerializationInclusion(JsonSerialize.Inclusion.ALWAYS);

//        忽略在json中存在但是在Java对象中不存在对应属性的情况，防止出错
        objectMapper.configure(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);

//        取消默认转换timestamp形式
        objectMapper.configure(SerializationConfig.Feature.WRITE_DATES_AS_TIMESTAMPS, false);

//        所有的日期格式都转化为：yyyy-MM-dd HH:mm:ss
        objectMapper.setDateFormat(new SimpleDateFormat(DateTimeUtil.STANDARD_FORMAT));

//        忽略空bean转换json的错误
        objectMapper.configure(SerializationConfig.Feature.FAIL_ON_EMPTY_BEANS, false);
    }

    public static <T> String obj2str(T obj) {
        if (obj == null) {
            return null;
        }
        try {
            return obj instanceof String ? (String) obj : objectMapper.writeValueAsString(obj);
        } catch (IOException e) {
            log.warn("Parse object to String error", e);
            return null;
        }
    }

    public static <T> String obj2strPretty(T obj) {
        if (obj == null) {
            return null;
        }
        try {
            return obj instanceof String ? (String) obj : objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(obj);
        } catch (IOException e) {
            log.warn("Parse object to String error", e);
            return null;
        }
    }

    public static <T> T str2obj(String str, Class<T> clazz) {
        if (StringUtils.isEmpty(str) || clazz == null) {
            return null;
        }
        try {
            return clazz.equals(String.class) ? (T)str : objectMapper.readValue(str,clazz);
        } catch (IOException e) {
            log.warn("Parse String to Object error",e);
            return null;
        }
    }

    public static <T> T str2obj(String str, TypeReference<T> typeReference) {
        if (StringUtils.isEmpty(str) || typeReference == null) {
            return null;
        }
        try {
            return (T)(typeReference.getType().equals(String.class) ? str : objectMapper.readValue(str,typeReference));
        } catch (IOException e) {
            log.warn("Parse String to Object error",e);
            return null;
        }
    }

    public static <T> T str2obj(String str, Class<?> collectionClass, Class<?>... elememtClasses) {
       JavaType javaType = objectMapper.getTypeFactory().constructParametricType(collectionClass,elememtClasses);
        try {
            return objectMapper.readValue(str, javaType);
        } catch (IOException e) {
            log.warn("Parse String to Object error",e);
            return null;
        }
    }

    public static void main(String[] args) {
        User user = new User();
        user.setId(2);
        user.setEmail("isaac@qq.com");
        user.setCreateTime(new Date());
        String userJsonPretty = JsonUtil.obj2strPretty(user);
        log.info("userJson:{}",userJsonPretty);
        String userJson = JsonUtil.obj2str(user);
        log.info("userJson:{}",userJson);
        User user1 = JsonUtil.str2obj(userJson,User.class);

        List<User> userList = Lists.newArrayList();
        userList.add(user);
        userList.add(user1);
        String userListStr = JsonUtil.obj2strPretty(userList);
        log.info(userListStr);
        List<User> userListObj1 = JsonUtil.str2obj(userListStr, new TypeReference<List<User>>() {});
        List<User> userListObj2 = JsonUtil.str2obj(userListStr,List.class,User.class);
    }
}
