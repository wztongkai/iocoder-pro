package com.iocoder.yudao.module.commons.utils;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 中文字符转化为拼音的工具类  支持多音字
 * @author wu kai
 * @since 2022/9/8
 */
public class ChineseCharConvertUtil {
    /**
     * 将多个汉字转成拼音集合 eq: 得报 -> [DeBao, DeiBao]
     *
     * @param chinese 汉字字符串
     * @return 拼音
     */
    public static String[] chineseConversionPinyin(String chinese) {
        if (chinese == null || "".equals(chinese.trim())) {
            return new String[]{};
        }
        char[] chars = chinese.toCharArray();
        // 递归获取字符的拼音集合
        return getChineseCharPinyin(chars, 0);
    }

    /**
     * 将单个汉字转成拼音
     *
     * @param chinese 汉字字符
     * @return 拼音
     */
    public static String[] chineseCharConversionPinyin(char chinese) {
        HanyuPinyinOutputFormat outputFormat = new HanyuPinyinOutputFormat();
        outputFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
        String[] res;
        try {
            res = PinyinHelper.toHanyuPinyinStringArray(chinese, outputFormat);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return dealTreeSet(res);
    }

    /**
     * TreeSet对字符串数组去重并排序
     *
     * @param arrays 数组
     * @return 去重数组
     */
    public static String[] dealTreeSet(String[] arrays) {
        TreeSet<String> set = new TreeSet<>();
        Collections.addAll(set, arrays);
        return set.toArray(new String[0]);
    }




    /**
     * 递归获取字符的拼音集合
     * @param chars 中文字符数组
     * @param idx 标志位
     * @return 拼音集合
     */
    private static String[] getChineseCharPinyin(char[] chars, int idx) {
        char aChar = chars[idx];
        // 将汉字字符转换为首字母大写的多音字字符集合
        String[] singleChar = charConvert(aChar);

        if (++idx < chars.length) {
            String[] tempChar = getChineseCharPinyin(chars, idx);
            String[] resultChar = new String[singleChar.length * tempChar.length];
            int innerIdx = 0;
            for (String sc : singleChar) {
                for (String value : tempChar) {
                    resultChar[innerIdx] = sc + value;
                    innerIdx++;
                }
            }
            return resultChar;
        }
        return singleChar;
    }

    /**
     * 转单个汉字，返回首字母大写的多音字集合
     * @param unit 汉字字符
     * @return 多音字集合
     */
    private static String[] charConvert(char unit) {
        String regExp = "^[\u4E00-\u9FFF]+$";
        String[] pinyins = new String[]{};
        if (match(String.valueOf(unit), regExp)){
            // 将单个汉字转成拼音
            pinyins = chineseCharConversionPinyin(unit);
        }
        // 将拼音首字母改为大写
        for (int i = 0; i < Objects.requireNonNull(pinyins).length; i++) {
            pinyins[i] = pinyins[i].substring(0, 1).toUpperCase(Locale.ROOT) + pinyins[i].substring(1);
        }
        return pinyins;
    }

    /**
     * 根据字符和正则表达式进行匹配
     *
     * @param str   源字符串
     * @param regex 正则表达式
     * @return true：匹配成功  false：匹配失败
     */
    private static boolean match(String str, String regex) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(str);
        return matcher.find();
    }

//    public static void main(String[] args) {
//        String str = "得报";
//        String[] strings = chineseConversionPinyin(str);
//        System.out.println(Arrays.toString(strings));
//
//    }
}
