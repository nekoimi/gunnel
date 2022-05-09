package com.nekoimi.gunnel.logger;

import com.nekoimi.gunnel.config.BaseConfigProperties;
import com.nekoimi.gunnel.factory.SingletonFactory;

/**
 * <p>日志配置</p>
 *
 * @author nekoimi  2022/4/6 9:27
 */
public class LogProperties extends BaseConfigProperties {

    /**
     * <p>日志等级</p>
     */
    private Level level;

    /**
     * <p>日志适配器配置</p>
     */
    private String adapter;

    public static LogProperties getInstance() {
        return SingletonFactory.singletonInstance(LogProperties.class);
    }

    static {
        // 初始化配置文件
        getInstance().initialization();
    }

    @Override
    protected String configPath() {
        return "log.properties";
    }


    @Override
    public void initialization() {
        super.initialization();
        // 初始化配置
        this.level = Level.of(getStr("logger.level"));
        this.adapter = getStr("logger.adapter");
    }

    public Level getLevel() {
        return level;
    }

    public String getAdapter() {
        return adapter;
    }
}
