package com.iocoder.yudao.module.commons.config;


import com.iocoder.yudao.module.commons.exception.ServiceExceptionUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;

import java.util.Collection;
import java.util.List;
import java.util.Objects;

import static com.iocoder.yudao.module.commons.constant.ErrorCodeConstants.DATA_ERROR;


/**
 * 异常错误抛出工具
 */

@Component
public class Assertion {

    /**
     * 对象为空时
     *
     * @param obj
     * @param message
     */
    public static void isNull(Object obj, String message) {
        if (Objects.isNull(obj)) {
            message(message);
        }
    }


    static public void assertEquals(String message, int expected,
                                    int actual) {
        if (expected == actual) {
            return;
        } else {
            String cleanMessage = message == null ? "" : message;
            message(cleanMessage);
        }
    }


    /**
     * 对象不为空时
     *
     * @param obj
     * @param message
     */
    public static void isNotNull(Object obj, String message) {
        if (obj != null) {
            message(message);
        }
    }


    /**
     * 对象不为空时
     *
     * @param obj
     * @param message
     */
    public static void isNotNull(Integer obj, String message) {
        if (obj != null && obj != 0) {
            message(message);
        }
    }


    /**
     * 对象为空时
     *
     * @param obj
     */
    public static void isNull(Object obj) {
        if (Objects.isNull(obj)) {
            message();
        }
    }

    /**
     * 对象为空时
     *
     * @param obj
     * @param message
     */
    public static void nonNull(Object obj, String message) {
        if (Objects.nonNull(obj)) {
            message(message);
        }
    }

    /**
     * 集合为空时
     *
     * @param obj
     * @param message
     */
    public static void listNonNull(List obj, String message) {
        if (obj.size() > 0) {
            message(message);
        }
    }


    /**
     * 集合为空时
     *
     * @param list
     * @param message
     */
    public static void isEmpty(List list, String message) {
        if (CollectionUtils.isEmpty(list)) {
            message(message);
        }
    }

    /**
     * 文件为空时
     *
     * @param file
     * @param message
     */
    public static void isEmpty(MultipartFile file, String message) {
        if (file.isEmpty()) {
            message(message);
        }
    }


    /**
     * 对象不为空时
     *
     * @param obj
     */
    public static void nonNull(Object obj) {
        if (Objects.nonNull(obj)) {
            message();
        }
    }


    /**
     * 对象相等时
     *
     * @param val1
     * @param val2
     * @param message
     */
    public static void equals(Object val1, Object val2, String message) {
        if (Objects.equals(val1, val2)) {
            message(message);
        }
    }


    /**
     * 对象相等时
     *
     * @param val1
     * @param val2
     */
    public static void equals(Object val1, Object val2) {
        if (Objects.equals(val1, val2)) {
            message();
        }
    }

    /**
     * 对象不相等时
     *
     * @param val1
     * @param val2
     * @param message
     */
    public static void unequals(Object val1, Object val2, String message) {
        if (!Objects.equals(val1, val2)) {
            message(message);
        }
    }


    /**
     * 对象不相等时
     *
     * @param val1
     * @param val2
     */
    public static void unequals(Object val1, Object val2) {
        if (!Objects.equals(val1, val2)) {
            message();
        }
    }

    /**
     * 字符串相等时
     *
     * @param val1
     * @param val2
     * @param message
     */
    public static void equals(String val1, String val2, String message) {
        if (StringUtils.equals(val1, val2)) {
            message(message);
        }
    }


    /**
     * 字符串相等时
     *
     * @param val1
     * @param val2
     */
    public static void equals(String val1, String val2) {
        if (StringUtils.equals(val1, val2)) {
            message();
        }
    }

    /**
     * 字符串不相等时
     *
     * @param val1
     * @param val2
     * @param message
     */
    public static void unequals(String val1, String val2, String message) {
        if (!StringUtils.equals(val1, val2)) {
            message(message);
        }
    }


    /**
     * 字符串不相等时
     *
     * @param val1
     * @param val2
     */
    public static void unequals(String val1, String val2) {
        if (!StringUtils.equals(val1, val2)) {
            message();
        }
    }


    /**
     * 集合不为空时
     *
     * @param coll
     * @param message
     */
    public static void isNotEmpty(Collection coll, String message) {
        if (!CollectionUtils.isEmpty(coll)) {
            message(message);
        }
    }


    /**
     * 集合不为空时
     *
     * @param coll
     */
    public static void isNotEmpty(Collection coll) {
        if (!CollectionUtils.isEmpty(coll)) {
            message();
        }
    }

    /**
     * 集合为空时
     *
     * @param coll
     * @param message
     */
    public static void isEmpty(Collection coll, String message) {
        if (CollectionUtils.isEmpty(coll)) {
            message(message);
        }
    }


    /**
     * int为空时
     *
     * @param coll
     * @param message
     */
    public static void isIntBlank(Integer coll, String message) {
        if (coll == null) {
            message(message);
        }
    }

    /**
     * 集合为空时
     *
     * @param coll
     */
    public static void isEmpty(Collection coll) {
        if (CollectionUtils.isEmpty(coll)) {
            message();
        }
    }


    /**
     * 字符串为空
     *
     * @param str
     * @param message
     */
    public static void isBlank(String str, String message) {
        if (StringUtils.isBlank(str)) {
            message(message);
        }
    }



    /**
     * 字符串为空
     *
     * @param str
     */
    public static void isBlank(String str) {
        if (StringUtils.isBlank(str)) {
            message();
        }
    }

    /**
     * 字符串不为空
     *
     * @param str
     * @param message
     */
    public static void isNotBlank(String str, String message) {
        if (StringUtils.isNotBlank(str)) {
            message(message);
        }
    }

    /**
     * 字符串不为空
     *
     * @param str
     */
    public static void isNotBlank(String str) {
        if (StringUtils.isNotBlank(str)) {
            message();
        }
    }

    /**
     * 判断为真时
     *
     * @param judgment
     * @param message
     * @
     */
    public static void isTrue(boolean judgment, String message) {
        if (judgment) {
            message(message);
        }
    }

    /**
     * 判断为真时
     *
     * @param judgment
     * @
     */
    public static void isTrue(boolean judgment) {
        if (judgment) {
            message();
        }
    }



    /**
     * 判断为假时
     *
     * @param judgment
     * @param message
     * @
     */
    public static void isFalse(boolean judgment, String message) {
        isTrue(!judgment, message);
    }



    /**
     * 判断为假时
     *
     * @param judgment
     * @
     */
    public static void isFalse(boolean judgment) {
        isTrue(!judgment);
    }


    /**
     * 集合中包含obj时
     *
     * @param coll
     * @param obj
     * @param message
     * @param <T>
     * @
     */
    public static <T> void contains(Collection<T> coll, T obj, String message) {
        if (coll.contains(obj)) {
            message(message);
        }
    }


    /**
     * 集合中包含obj时
     *
     * @param coll
     * @param obj
     * @param <T>
     * @
     */
    public static <T> void contains(Collection<T> coll, T obj) {
        if (coll.contains(obj)) {
            message();
        }
    }

    /**
     * 集合中不包含obj时
     *
     * @param coll
     * @param obj
     * @param message
     * @param <T>
     * @
     */
    public static <T> void notContains(Collection<T> coll, T obj, String message) {
        if (!coll.contains(obj)) {
            message(message);
        }
    }


    /**
     * 集合中不包含obj时
     *
     * @param coll
     * @param obj
     * @param <T>
     * @
     */
    public static <T> void notContains(Collection<T> coll, T obj) {
        if (!coll.contains(obj)) {
            message();
        }
    }

    /**
     * 默认数据异常
     */
    public static void message() {
        message(DATA_ERROR.getMsg());
    }

    /**
     * 抛出业务异常
     *
     * @param message
     */
    public static void message(String message) {
        throw ServiceExceptionUtil.exception(DATA_ERROR);
    }



}

