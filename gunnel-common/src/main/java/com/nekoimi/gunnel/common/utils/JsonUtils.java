package com.nekoimi.gunnel.common.utils;

import com.google.gson.Gson;

/**
 * nekoimi  2021/8/15 12:14
 */
public class JsonUtils {

    /**
     * @param src
     * @return
     */
    public static String toJson(Object src) {
        return new Gson().toJson(src);
    }

    /**
     * @param json
     * @param javaType
     * @param <T>
     * @return
     */
    public static <T> T parse(String json, Class<T> javaType) {
        return new Gson().fromJson(json, javaType);
    }
}
