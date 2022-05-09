package com.nekoimi.gunnel.logger;

/**
 * <p>日志等级</p>
 *
 * @author nekoimi  2022/4/2 11:01
 */
public enum Level {

    /**
     * <p>DEBUG</p>
     */
    DEBUG(100, "DEBUG"),

    /**
     * <p>INFO</p>
     */
    INFO(200, "INFO"),

    /**
     * <p>WARN</p>
     */
    WARN(300, "WARN"),

    /**
     * <p>ERROR</p>
     */
    ERROR(400, "ERROR"),
    ;

    /**
     * <p>日志级别</p>
     */
    private final int level;

    /**
     * <p>日志级别名称</p>
     */
    private final String name;

    Level(int level, String name) {
        this.level = level;
        this.name = name;
    }

    /**
     * <p>获取日志级别</p>
     *
     * @return
     */
    public int getLevel() {
        return level;
    }

    /**
     * <p>获取日志级别名称</p>
     *
     * @return
     */
    public String getName() {
        return name;
    }

    /**
     * <p>通过名称获取日志等级</p>
     *
     * @param name 日志等级名称
     * @return
     */
    public static Level of(String name) {
        Level[] values = Level.values();
        for (Level value : values) {
            if (value.name.equalsIgnoreCase(name)) {
                return value;
            }
        }
        return Level.INFO;
    }
}
