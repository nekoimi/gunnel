package com.nekoimi.gunnel.config;

/**
 * <p>配置对象</p>
 *
 * @author nekoimi  2022/4/6 14:06
 */
public interface ConfigProperties {

    /**
     * <p>初始化配置</p>
     */
    void initialization();

    /**
     * <p>获取配置</p>
     *
     * @param key 配置键值
     * @return
     */
    String getStr(String key);

    /**
     * <p>获取配置</p>
     *
     * @param key          配置键值
     * @param defaultValue 缺省值
     * @return
     */
    String getStr(String key, String defaultValue);

    /**
     * <p>获取配置</p>
     *
     * @param key 配置键值
     * @return
     */
    Boolean getBool(String key);

    /**
     * <p>获取配置</p>
     *
     * @param key          配置键值
     * @param defaultValue 缺省值
     * @return
     */
    Boolean getBool(String key, String defaultValue);

    /**
     * <p>获取配置</p>
     *
     * @param key 配置键值
     * @return
     */
    Integer getInt(String key);

    /**
     * <p>获取配置</p>
     *
     * @param key          配置键值
     * @param defaultValue 缺省值
     * @return
     */
    Integer getInt(String key, String defaultValue);
}
