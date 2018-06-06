package com.gjn.baserecycleradapter;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.HanyuPinyinVCharType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

/**
 * PinyinUtils
 * Author: gjn.
 * Time: 2018/2/28.
 */

public class PinyinUtils {

    public static String getPinYin(String str) {
        return getPinYin(str, false);
    }

    public static String getPinYin(String str, boolean isFristUpperCase) {
        return getPinYin(str, isFristUpperCase, defualtListener());
    }

    public static String getPinYin(String str, TongYinListener listener) {
        return getPinYin(str, false, listener);
    }

    public static String getPinYin(String str, boolean isFristUpperCase, TongYinListener listener) {
        HanyuPinyinOutputFormat format = new HanyuPinyinOutputFormat();
        //全部小写
        format.setCaseType(HanyuPinyinCaseType.LOWERCASE);
        //不标声调
        format.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
        //u:转为v
        format.setVCharType(HanyuPinyinVCharType.WITH_V);

        char[] c = str.toCharArray();
        String result = "";

        for (int i = 0; i < c.length; i++) {
            //非ASCII码
            if (c[i] > 128) {
                try {
                    String[] temp = PinyinHelper.toHanyuPinyinStringArray(c[i], format);
                    //非中文
                    if (temp == null) {
                        result += c[i];
                    } else {
                        //是否首字母大写
                        if (isFristUpperCase) {
                            result += FirstUpperCase(listener.onTongYinListener(c[i], temp, i));
                        } else {
                            result += listener.onTongYinListener(c[i], temp, i);
                        }
                    }
                } catch (BadHanyuPinyinOutputFormatCombination badHanyuPinyinOutputFormatCombination) {
                    badHanyuPinyinOutputFormatCombination.printStackTrace();
                }
            } else {
                result += c[i];
            }
        }
        if (result.isEmpty()) {
            return str;
        }
        return result;
    }

    private static String FirstUpperCase(String str) {
        if (str.isEmpty()) {
            return str;
        }
        char first = str.charAt(0);
        return String.valueOf(first).toUpperCase().concat(str.substring(1));
    }

    public static String getPinYinFrist(String str) {
        return getPinYinFrist(str, defualtListener());
    }

    public static String getPinYinFrist(String str, TongYinListener listener) {
        if (str.isEmpty()) {
            return str;
        }
        String result = getPinYin(str, listener);
        if (result.length() > 1) {
            result = result.substring(0, 1).toUpperCase();
        }
        return result;
    }

    public static TongYinListener defualtListener() {
        return new TongYinListener() {
            @Override
            public String onTongYinListener(char c, String[] ty, int index) {
                return ty[0];
            }
        };
    }

    public interface TongYinListener {
        String onTongYinListener(char c, String[] ty, int index);
    }
}
