package com.iocoder.yudao.commons.utils.convert;

import com.iocoder.yudao.commons.utils.StringUtils;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ObjectUtils;

import java.nio.charset.Charset;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * 类型转化器
 *
 * @Author: kai wu
 * @Date: 2022/6/3 22:22
 */
public class CommonConvert {


    /**
     * 将byte数组转为字符串
     *
     * @param bytes   byte数组
     * @param charset 字符集
     * @return 字符串
     */
    public static String str(byte[] bytes, String charset) {
        return ObjectConvertUtils.str(bytes, StringUtils.isEmpty(charset) ? Charset.defaultCharset() : Charset.forName(charset));
    }

    /**
     * 判断目标参数中是否包含元数据
     *
     * @param source  元数据
     * @param targets 目标集合
     * @return 判断结果
     */
    public static boolean containsAny(Object source, Object... targets) {
        return Arrays.asList(targets).contains(source);
    }

    /**
     * 查找传入的可变集合参数中的空集合，找到一个直接返回true，不在继续匹配
     *
     * @param collections 可变集合
     * @return boolean
     */
    public static boolean isAnyEmpty(Collection<?>... collections) {
        return Arrays.stream(collections).anyMatch(CollectionUtils::isEmpty);
    }

    /**
     * 匹配集合中与给定条件相同的元素，组成新的集合
     *
     * @param from      旧集合
     * @param predicate 条件
     * @param <T>       泛型
     * @return 新集合
     */
    public static <T> List<T> filterList(Collection<T> from, Predicate<T> predicate) {
        if (CollectionUtils.isEmpty(from)) {
            return new ArrayList<>();
        }
        return from.stream().filter(predicate).collect(Collectors.toList());
    }

    /**
     * 判断两个集合是否存在同一个元素
     *
     * @param source     集合一
     * @param candidates 集合二
     * @return 判断结果，存在 --> true 不存在 --> false
     */
    public static boolean containsAny(Collection<?> source, Collection<?> candidates) {
        return CollectionUtils.containsAny(source, candidates);
    }

    /**
     * 如果对象不为空，将对象添加进集合
     *
     * @param coll 集合
     * @param item 对象
     * @param <T>  泛型
     */
    public static <T> void addIfNotNull(Collection<T> coll, T item) {
        if (ObjectUtils.isEmpty(item)) {
            return;
        }
        coll.add(item);
    }

    public static <T> Collection<T> singleton(T deptId) {
        return deptId == null ? Collections.emptyList() : Collections.singleton(deptId);
    }




}
