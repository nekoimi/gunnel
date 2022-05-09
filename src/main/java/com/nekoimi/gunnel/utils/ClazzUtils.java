package com.nekoimi.gunnel.utils;

import com.nekoimi.gunnel.logger.LoggerFactory;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * <p>Clazz工具类</p>
 *
 * @author nekoimi  2022/4/6 10:42
 */
public class ClazzUtils {

    /**
     * <p>判断当前类型是否是指定类型的实例</p>
     *
     * @param clazz  当前类型
     * @param target 指定类型
     * @return
     */
    public static boolean instanceOf(Class clazz, Class target) {
        if (clazz == null) return false;
        if (clazz == target) return true;
        if (target.isInterface()) {
            // 目标类型是接口，则当且仅当 clazz 实现了 target 接口
            for (Class clazzInterface : clazz.getInterfaces()) {
                if (clazzInterface == target) return true;
            }
        }

        if (clazz.isInterface()) {
            for (Class clazzInterface : clazz.getInterfaces()) {
                if (instanceOf(clazzInterface, target)) {
                    return true;
                }
            }
        } else {
            if (clazz.getSuperclass() == target) {
                return true;
            }
        }

        return instanceOf(clazz.getSuperclass(), target);
    }

    /**
     * <p>获取类中所有的字段</p>
     *
     * @param clazz 类型
     * @return
     */
    public static Field[] getAllFields(Class<?> clazz) {
        List<Field> fieldList = new ArrayList<>();
        while (clazz != null) {
            Field[] fields = clazz.getDeclaredFields();
            fieldList.addAll(Arrays.asList(fields));
            clazz = clazz.getSuperclass();
        }
        Field[] results = new Field[fieldList.size()];
        return fieldList.toArray(results);
    }

    /**
     * <p>查找类中的字段</p>
     *
     * @param clazz     类型
     * @param fieldName 字段名称
     * @return
     */
    public static Field findField(Class<?> clazz, String fieldName) {
        if (StringUtils.isEmpty(fieldName)) return null;
        while (clazz != null) {
            Field[] fields = clazz.getDeclaredFields();
            for (Field field : fields) {
                if (fieldName.equals(field.getName())) {
                    return field;
                }
            }
            clazz = clazz.getSuperclass();
        }
        return null;
    }

    /**
     * <p>获取实例</p>
     *
     * @param clazz 类型
     * @param args  构造函数参数
     * @param <T>   泛型
     * @return
     */
    public static <T> T newInstance(Class<T> clazz, Object... args) {
        try {
            return clazz.getDeclaredConstructor().newInstance(args);
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            LoggerFactory.error(e);
            return null;
        }
    }
}
