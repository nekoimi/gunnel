package com.nekoimi.gunnel.utils;

import java.lang.reflect.Array;

/**
 * <p>数组操作工具类</p>
 *
 * @author nekoimi  2022/4/2 14:36
 */
public class ArrayUtils {

    /**
     * <p>创建新数组</p>
     *
     * @param elementType 数组元素类型
     * @param length      数组长度
     * @param <T>
     * @return
     */
    public static <T> T[] newArray(Class<T> elementType, int length) {
        return (T[]) Array.newInstance(elementType, length);
    }

    /**
     * <p>数组扩容</p>
     *
     * @param arraySrc    数组
     * @param elementType 数组元素类型
     * @param length      在原数组已有长度的基础上再增加 {length} 的容量
     * @param <T>
     * @return 扩容后的新数组
     */
    public static <T> T[] resize(T[] arraySrc, Class<T> elementType, int length) {
        T[] arrayDest = newArray(elementType, arraySrc.length + length);
        System.arraycopy(arraySrc, 0, arrayDest, 0, arraySrc.length);
        return arrayDest;
    }

    /**
     * <p>判断数组是否为空</p>
     *
     * @param objects 数组对象
     * @return
     */
    public static boolean isEmpty(Object[] objects) {
        return !isNotEmpty(objects);
    }

    /**
     * <p>判断数组是否不为空</p>
     *
     * @param objects 数组对象
     * @return
     */
    public static boolean isNotEmpty(Object[] objects) {
        return objects != null && objects.length > 0;
    }
}
