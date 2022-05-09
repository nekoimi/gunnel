package com.nekoimi.gunnel.factory;

import com.nekoimi.gunnel.utils.ClazzUtils;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * <p>单例工厂</p>
 *
 * @author nekoimi 2022/05/09
 */
public class SingletonFactory {
    private final ConcurrentMap<Class<?>, Object> instanceCache;

    private SingletonFactory() {
        instanceCache = new ConcurrentHashMap<>();
    }

    /**
     * <p>单例</p>
     */
    private static class SingletonHolder {
        public static SingletonFactory instance = new SingletonFactory();
    }

    public static SingletonFactory getInstance() {
        return SingletonFactory.SingletonHolder.instance;
    }

    /**
     * <p>获取单例</p>
     *
     * @param targetClazz 目标类型
     * @param <T>
     * @return
     */
    public static <T> T singletonInstance(Class<T> targetClazz) {
        return (T) getInstance().instanceCache.computeIfAbsent(targetClazz, clazz -> ClazzUtils.newInstance(clazz));
    }

    /**
     * <p>获取单例</p>
     *
     * @param targetClazz 目标类型
     * @param <T>
     * @return
     */
    public static <T> T singletonInstance(Class<T> targetClazz, Object...args) {
        return (T) getInstance().instanceCache.computeIfAbsent(targetClazz, clazz -> ClazzUtils.newInstance(clazz, args));
    }
}
