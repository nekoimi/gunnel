package com.nekoimi.gunnel.logger;

/**
 * <p>日志接口</p>
 *
 * @author nekoimi  2022/4/2 11:37
 */
public interface Logger {

    /**
     * <p>日志格式化</p>
     *
     * @param level  日志等级
     * @param format 格式化字符串
     * @param args   格式化参数
     * @return
     */
    String format(Level level, String format, Object... args);

    /**
     * <p>记录日志</p>
     *
     * @param level  日志等级
     * @param format 格式化字符串
     * @param args   格式化参数
     */
    void log(Level level, String format, Object... args);

    /**
     * <p>记录DEBUG级别日志</p>
     *
     * @param format
     * @param args
     */
    void debug(String format, Object... args);

    /**
     * <p>记录INFO级别日志</p>
     *
     * @param format
     * @param args
     */
    void info(String format, Object... args);

    /**
     * <p>记录WARN级别日志</p>
     *
     * @param format
     * @param args
     */
    void warn(String format, Object... args);

    /**
     * <p>记录ERROR级别日志</p>
     *
     * @param format
     * @param args
     */
    void error(String format, Object... args);

    /**
     * <p>判断是否支持{level}级别的日志</p>
     *
     * @param level
     * @return
     */
    boolean isEnable(Level level);

    /**
     * <p>日志级别是否是DEBUG</p>
     *
     * @return
     */
    boolean isEnableDebug();

    /**
     * <p>日志级别是否是INFO</p>
     *
     * @return
     */
    boolean isEnableInfo();

    /**
     * <p>日志级别是否是WARN</p>
     *
     * @return
     */
    boolean isEnableWarn();

    /**
     * <p>日志级别是否是ERROR</p>
     *
     * @return
     */
    boolean isEnableError();
}
