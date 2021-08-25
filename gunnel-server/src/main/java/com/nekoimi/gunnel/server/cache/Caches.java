package com.nekoimi.gunnel.server.cache;

import com.github.benmanes.caffeine.cache.Caffeine;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.function.Supplier;

/**
 * nekoimi  2021/8/25 9:09
 */
public class Caches {
    private static final Supplier<ConcurrentMap<Object, Object>> cacheSupplier;

    private static boolean caffeinePresent() {
        try {
            Class.forName("com.github.benmanes.caffeine.cache.Cache");
            return true;
        } catch (ClassNotFoundException e) {
            return false;
        }
    }

    private static ConcurrentMap<Object, Object> createCaffeine() {
        return Caffeine.newBuilder().build().asMap();
    }

    static {
        if (caffeinePresent()) {
            cacheSupplier = Caches::createCaffeine;
        } else {
            cacheSupplier = ConcurrentHashMap::new;
        }
    }

    public static <K, V> ConcurrentMap<K, V> newCache() {
        return (ConcurrentMap<K, V>) cacheSupplier.get();
    }
}
