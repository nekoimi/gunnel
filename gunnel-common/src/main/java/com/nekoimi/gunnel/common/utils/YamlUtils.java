package com.nekoimi.gunnel.common.utils;

import lombok.extern.slf4j.Slf4j;
import org.yaml.snakeyaml.Yaml;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

/**
 * nekoimi  2021/8/16 21:21
 */
@Slf4j
public class YamlUtils {
    private final static Yaml yaml;
    private final static ClassLoader loader;

    static {
        yaml = new Yaml();
        loader = YamlUtils.class.getClassLoader();
    }

    public static Map<String, Object> loadAsMap(String name) {
        try (InputStream inputStream = loader.getResourceAsStream(name)) {
            Map<String, Object> map = yaml.loadAs(inputStream, Map.class);
            return map;
        } catch (IOException e) {
            log.error("Load yaml (" + name + ") error, " + e.getMessage());
        }
        return null;
    }

    public static Map<String, Object> loadAsMap(String name, String prefix) {
        Map<String, Object> map = loadAsMap(name);
        if (map != null && map.containsKey(prefix)) {
            return (Map<String, Object>) map.get(prefix);
        }
        return null;
    }

    public static <T> T loadAsType(String name, Class<T> javaType) {
        Map<String, Object> map = loadAsMap(name);
        if (map == null)
            return null;

        return JsonUtils.parse(JsonUtils.toJson(map), javaType);
    }

    public static <T> T loadAsType(String name, String prefix, Class<T> javaType) {
        Map<String, Object> map = loadAsMap(name, prefix);
        if (map == null)
            return null;

        return JsonUtils.parse(JsonUtils.toJson(map), javaType);
    }
}
