package com.iocoder.yudao.module.commons.utils;

import com.alibaba.fastjson2.JSON;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

import java.beans.PropertyDescriptor;
import java.io.*;
import java.lang.reflect.Field;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 对象、实体转化工具类
 *
 * @Author: kai wu
 * @Date: 2022/6/3 17:02
 */
public class BeanUtil {

    public static <T> List<T> copyListByJson(List<T> list, Class<T> tClass) {
        List<T> arrayList = new ArrayList<>();
        if (CollectionUtils.isEmpty(list)) {
            return arrayList;
        }
        return JSON.parseArray(JSON.toJSONString(list), tClass);
    }

    /**
     * 对象之间的拷贝
     *
     * @param source           原对象
     * @param target           目标对象
     * @param ignoreProperties 忽略字段
     */
    public static void copyProperties(Object source, Object target, String... ignoreProperties) {
        BeanUtils.copyProperties(source, target, ignoreProperties);
    }

    /**
     * 对象之间的拷贝
     *
     * @param source 原对象
     * @param target 目标对象
     */
    public static void copyProperties(Object source, Object target) {
        BeanUtils.copyProperties(source, target);
    }

    /**
     * * 对象List之间拷贝
     *
     * @param sourceList       原数据集合
     * @param targetList       目标集合
     * @param targetClass      目标类
     * @param ignoreProperties 忽略字段
     */
    public static <T, E> void copyListProperties(List<E> sourceList, List<T> targetList, Class<T> targetClass, String... ignoreProperties) {
        sourceList.stream().map(source -> clone(source, targetClass, ignoreProperties)).forEach(targetList::add);
    }

    /**
     * * 对象List之间拷贝
     *
     * @param sourceList  原数据集合
     * @param targetList  目标集合
     * @param targetClass 目标类
     */
    public static <T, E> void copyListProperties(List<E> sourceList, List<T> targetList, Class<T> targetClass) {
        sourceList.stream().map(source -> clone(source, targetClass)).forEach(targetList::add);
    }

    /**
     * 获取值是null的字段name
     */
    public static String[] getNullPropertyNames(Object source) {
        final BeanWrapper src = new BeanWrapperImpl(source);
        PropertyDescriptor[] pds = src.getPropertyDescriptors();

        Set<String> emptyNames = new HashSet<>();
        for (PropertyDescriptor pd : pds) {
            Object srcValue = src.getPropertyValue(pd.getName());
            if (srcValue == null) {
                emptyNames.add(pd.getName());
            }
        }
        String[] result = new String[emptyNames.size()];
        return emptyNames.toArray(result);
    }

    /**
     * copy时忽略空值的字段
     */
    public static void copyPropertiesIgnoreNull(Object source, Object target) {
        BeanUtils.copyProperties(source, target, getNullPropertyNames(source));
    }

    /**
     * 完成深克隆被克隆的类和类的引用类
     * 均实现Serializable接口
     */
    public static Object deepClone(Object object) {
        /*
         *本次实现深克隆使用 ByteArrayOutputStream
         * 和ByteArrayInputStream
         *作为复制过程中字符数组存储中介
         */
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutputStream oos = null;
        ObjectInputStream ois = null;
        try {
            //将bos作为收集字节数组中介
            oos = new ObjectOutputStream(bos);
            //将传入参数orderParent类写入bos中
            oos.writeObject(object);
            //将读取到数据传入ObjectInputStream
            ois = new ObjectInputStream(new ByteArrayInputStream(bos.toByteArray()));
            return ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            //JDK 1.7后引入 可以同时用| 优化代码可读性
            e.printStackTrace();
            return null;
        } finally {
            try {
                bos.close();
                Objects.requireNonNull(oos).close();
                Objects.requireNonNull(ois).close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    /**
     * 对象之间拷贝-有返回值
     */
    public static <T, E> E clone(T source, Class<E> classType, String... ignoreProperties) {
        if (null == source) {
            return null;
        }
        E targetInstance;
        try {
            targetInstance = classType.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
        BeanUtils.copyProperties(source, targetInstance, ignoreProperties);
        return targetInstance;
    }

    public static <T, E> E clone(T source, Class<E> classType) {
        if (null == source) {
            return null;
        }
        E targetInstance;
        try {
            targetInstance = classType.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
        BeanUtils.copyProperties(source, targetInstance);
        return targetInstance;
    }

    /**
     * 拷贝数组对象-有返回值
     *
     * @param sourceList 要进行拷贝的list 对象
     * @param classType  对象泛型
     * @return 拷贝新的数组对象
     */
    public static <T, E> List<E> batchClone(List<T> sourceList, Class<E> classType) {
        if (null == sourceList) {
            return null;
        }
        return sourceList.stream().map(t -> clone(t, classType)).collect(Collectors.toList());
    }

    /**
     * 获取所有Fields,包含父类field
     *
     * @param clazz clazz
     * @return list
     */
    public static List<Field> getAllFields(Class<?> clazz) {
        if (!clazz.equals(Object.class)) {
            List<Field> fields = new ArrayList<>(Arrays.asList(clazz.getDeclaredFields()));
            List<Field> fields2 = getAllFields(clazz.getSuperclass());
            if (fields2 != null) {
                fields.addAll(fields2);
            }
            return fields;
        } else {
            return null;
        }
    }

    /**
     * 转换对象为map
     *
     * @param object object
     * @return map
     */
    public static Map<String, Object> objectToMap(Object object) {
        return objectToMap(object, (String) null);
    }

    /**
     * 转换对象为map
     *
     * @param object object
     * @param ignore ignore
     * @return map
     */
    public static Map<String, Object> objectToMap(Object object, String... ignore) {
        Map<String, Object> tempMap = new LinkedHashMap<>();
        final BeanWrapper src = new BeanWrapperImpl(object);
        PropertyDescriptor[] pds = src.getPropertyDescriptors();

        for (PropertyDescriptor pd : pds) {
            boolean ig = false;
            if (ignore != null && ignore.length > 0) {
                ig = Arrays.stream(ignore).anyMatch(i -> i.equals(pd.getName()));
            }
            if (!ig) {
                Object o = src.getPropertyValue(pd.getName());
                if (o != null) {
                    tempMap.put(pd.getName(), o);
                }
            }
        }
        tempMap.remove("class");
        return tempMap;
    }


}
