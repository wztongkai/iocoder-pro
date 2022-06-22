package com.iocoder.yudao.commons.utils.convert;


import com.iocoder.yudao.commons.utils.StringUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.ObjectUtils;

import java.lang.reflect.Field;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.*;

/**
 * 对象转换器
 * @author wu kai
 * @date 2022/6/17
 */
public class ObjectConvertUtils {
    /**
     * Object 转换为 boolean
     * 如果给定的值为空，或者转换失败，返回默认值<code>null</code>
     * 转换失败不会报错
     *
     * @param t 被转换的值
     * @return 结果
     */
    public static <T> Boolean toBool(T t) {
        return toBool(t, null);
    }

    /**
     * Object 转换为 boolean
     * String支持的值为：true、false、yes、ok、no，1,0 如果给定的值为空，或者转换失败，返回默认值<br>
     * 转换失败不会报错
     *
     * @param t            被转换的值
     * @param defaultValue 转换错误时的默认值
     * @return 结果
     */
    public static <T> Boolean toBool(T t, Boolean defaultValue) {
        if (t == null) {
            return defaultValue;
        }
        if (t instanceof Boolean) {
            return (Boolean) t;
        }
        String valueStr = toStr(t, null);
        if (StringUtils.isEmpty(valueStr)) {
            return defaultValue;
        }
        valueStr = valueStr.trim().toLowerCase();
        switch (valueStr) {
            case "true":
            case "yes":
            case "ok":
            case "1":
                return true;
            case "false":
            case "no":
            case "0":
                return false;
            default:
                return defaultValue;
        }
    }

    /**
     * 实体对象转换成 map
     *
     * @param t            对象
     * @param ignoreFields 忽略字段 (可不传)
     * @param <T>          泛型
     * @return map
     */
    public static <T> Map<String, Object> objectToMap(T t, String... ignoreFields) {
        Map<String, Object> map = new HashMap<>();
        Field[] declaredFields = t.getClass().getDeclaredFields();
        List<String> ignoreFieldList = Arrays.asList(ignoreFields);
        Arrays.stream(declaredFields).forEach(data -> {
            data.setAccessible(true);
            try {
                if (ignoreFieldList.isEmpty() || !ignoreFieldList.contains(data.getName())) {
                    map.put(data.getName(), data.get(t));
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        });
        return map;
    }

    /**
     * Object转换为int
     * 如果给定的值为空，或者转换失败，返回默认值
     * 转换失败不会报错
     *
     * @param t            被转换的值
     * @param defaultValue 转换错误时的默认值
     * @return 结果
     */
    public static <T> Integer toInt(T t, Integer defaultValue) {
        if (ObjectUtils.isEmpty(t)) {
            return defaultValue;
        }
        if (t instanceof Integer) {
            return (Integer) t;
        }
        if (t instanceof Number) {
            return ((Number) t).intValue();
        }
        final String valueStr = toStr(t, null);
        if (StringUtils.isEmpty(valueStr)) {
            return defaultValue;
        }
        try {
            return Integer.parseInt(valueStr.trim());
        } catch (Exception e) {
            return defaultValue;
        }
    }

    /**
     * 转换为字符串
     * 如果给定的值为null，或者转换失败，返回默认值
     * 转换失败不会报错
     *
     * @param t            被转换的值
     * @param defaultValue 转换错误时的默认值
     * @return 结果
     */
    public static <T> String toStr(T t, String defaultValue) {
        if (ObjectUtils.isEmpty(t)) {
            return defaultValue;
        }
        if (t instanceof String) {
            return (String) t;
        }
        return t.toString();
    }

    /**
     * 将对象转为字符串
     * 1、Byte数组和ByteBuffer会被转换为对应字符串的数组 2、对象数组会调用Arrays.toString方法
     *
     * @param obj 对象
     * @return 字符串
     */
    public static String utf8Str(Object obj) {
        return str(obj, StandardCharsets.UTF_8);
    }

    /**
     * 将对象转为字符串
     * 1、Byte数组和ByteBuffer会被转换为对应字符串的数组 2、对象数组会调用Arrays.toString方法
     *
     * @param obj     对象
     * @param charset 字符集
     * @return 字符串
     */
    public static String str(Object obj, Charset charset) {
        if (ObjectUtils.isEmpty(obj)) {
            return null;
        }

        if (obj instanceof String) {
            return (String) obj;
        } else if (obj instanceof byte[]) {
            return str((byte[]) obj, charset);
        } else if (obj instanceof Byte[]) {
            byte[] bytes = ArrayUtils.toPrimitive((Byte[]) obj);
            return str(bytes, charset);
        } else if (obj instanceof ByteBuffer) {
            return str((ByteBuffer) obj, charset);
        }
        return obj.toString();
    }

    /**
     * object 转 list 对象
     * @param obj object
     * @param clazz 对象.class
     * @param <T> 泛型
     * @return list对象
     */
    public static <T> List<T> objToList(Object obj, Class<T> clazz) {
        if(ObjectUtils.isEmpty(obj)){
            return null;
        }
        List<T> resList = new ArrayList<>();
        // 如果不是List<?>对象，是没有办法转换的
        if (obj instanceof List<?>) {
            for (Object o : (List<?>) obj) {
                // 将对应的元素进行类型转换
                resList.add(clazz.cast(o));
            }
            return resList;
        }
        return null;
    }
}
