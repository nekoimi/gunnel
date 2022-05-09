package com.nekoimi.gunnel.utils;

/**
 * <p>Bool 工具类</p>
 *
 * @author nekoimi  2022/4/6 14:29
 */
public class BoolUtils {

    /**
     * <p>通过 Integer 创建bool</p>
     *
     * @param value 值
     * @return
     */
    public static boolean toBool(Integer value) {
        return value != null && value == 1;
    }

    /**
     * <p>通过 String 创建 bool</p>
     *
     * @param value 值
     * @return
     */
    public static boolean toBool(String value) {
        if (StringUtils.isEmpty(value)) {
            return false;
        }

        if (NumberUtils.isNumber(value)) {
            return "1".equals(value);
        }

        return "true".equalsIgnoreCase(value);
    }
}
