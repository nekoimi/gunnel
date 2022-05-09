package com.nekoimi.gunnel.config;

import com.nekoimi.gunnel.logger.LoggerFactory;
import com.nekoimi.gunnel.utils.BoolUtils;
import com.nekoimi.gunnel.utils.NumberUtils;
import com.nekoimi.gunnel.utils.StringUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Properties;

/**
 * <p>配置属性</p>
 *
 * @author nekoimi  2022/4/6 11:21
 */
public abstract class BaseConfigProperties extends Properties implements ConfigProperties {

    /**
     * <p>配置文件路径</p>
     *
     * @return
     */
    protected abstract String configPath();

    @Override
    public void initialization() {
        InputStream resourceAsStream = getClass().getResourceAsStream(StringUtils.removeStartThenAppend(configPath(), "/"));
        if (resourceAsStream == null) {
            LoggerFactory.error(new IllegalArgumentException("配置文件[" + configPath() + "]不存在!!!"));
            return;
        }
        try (InputStreamReader reader = new InputStreamReader(resourceAsStream, StandardCharsets.UTF_8)) {
            load(reader);
        } catch (IOException e) {
            LoggerFactory.error(e);
        }
    }

    @Override
    public String getStr(String key) {
        return getProperty(key);
    }

    @Override
    public String getStr(String key, String defaultValue) {
        return getProperty(key, defaultValue);
    }

    @Override
    public Boolean getBool(String key) {
        return BoolUtils.toBool(getProperty(key));
    }

    @Override
    public Boolean getBool(String key, String defaultValue) {
        return BoolUtils.toBool(getProperty(key, defaultValue));
    }

    @Override
    public Integer getInt(String key) {
        String property = getProperty(key);
        if (!NumberUtils.isNumber(property)) {
            return -1;
        }
        return Integer.parseInt(property);
    }

    @Override
    public Integer getInt(String key, String defaultValue) {
        String property = getProperty(key, defaultValue);
        if (!NumberUtils.isNumber(property)) {
            return -1;
        }
        return Integer.parseInt(property);
    }
}
