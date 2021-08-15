package com.nekoimi.gunnel.common.config;

import com.nekoimi.gunnel.common.utils.JsonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.yaml.snakeyaml.Yaml;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * nekoimi  2021/8/15 15:37
 */
public class GunnelConfigParser {
    private final static Logger logger = LoggerFactory.getLogger(GunnelConfigParser.class);
    private final static String DEFAULT_CONFIG = "config.yaml";
    private final static String DEFAULT_PREFIX = "gunnel";
    private static Map<String, Object> properties = new HashMap<>();
    private final static Yaml yaml = new Yaml();
    private final static ServerProperties server;
    private final static ClientProperties client;
    static {
        try (InputStream stream = GunnelConfigParser.class.getClassLoader().getResourceAsStream(DEFAULT_CONFIG);) {
            Map map = yaml.loadAs(stream, Map.class);
            if (map.containsKey(DEFAULT_PREFIX)) {
                properties = (Map<String, Object>) map.get(DEFAULT_PREFIX);
            }
            logger.debug(properties.toString());
        } catch (IOException e) {
            logger.error(e.getMessage());
        }

        String json = JsonUtils.toJson(properties);
        server = JsonUtils.parse(json, ServerProperties.class);
        logger.debug(server.toString());
        client = JsonUtils.parse(json, ClientProperties.class);
        logger.debug(client.toString());
    }


    public static ServerProperties getServer() {
        return server;
    }

    public static ClientProperties getClient() {
        return client;
    }
}
