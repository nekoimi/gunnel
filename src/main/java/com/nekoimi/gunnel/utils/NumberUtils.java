package com.nekoimi.gunnel.utils;

/**
 * <p>Number 工具类</p>
 *
 * @author nekoimi  2022/4/6 14:32
 */
public class NumberUtils {

    /**
     * <p>判断字符串是否是由数字组成</p>
     *
     * @param value 字符串值
     * @return
     */
    public static boolean isNumber(String value) {
        if (StringUtils.isEmpty(value)) {
            return false;
        }
        int length = value.length();
        for (int i = 0; i < length; i++) {
            char c = value.charAt(i);
            if (!Character.isDigit(c)) {
                return false;
            }
        }
        return true;
    }
}
