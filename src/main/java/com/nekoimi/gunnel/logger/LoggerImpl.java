package com.nekoimi.gunnel.logger;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * <p>日志实现</p>
 *
 * @author nekoimi  2022/4/2 11:59
 */
public class LoggerImpl implements Logger {
    /**
     * <p>默认日期时间格式化</p>
     */
    private final static DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    /**
     * <p>日志等级</p>
     */
    private final Level level;

    /**
     * <p>日志名称</p>
     */
    private final String name;

    /**
     * <p>日志格式化对象缓存map</p>
     */
    private final Map<String, Log> logFormatMap;

    /**
     * <p>日志适配器列表</p>
     */
    private final List<LoggerAdapter> adapterList;

    /**
     * @param name 日志名称
     */
    public LoggerImpl(String name) {
        this.level = LogProperties.getInstance().getLevel();
        this.name = name;
        this.logFormatMap = new ConcurrentHashMap<>();
        this.adapterList = LoggerFactory.getAdapters();
    }

    @Override
    public String format(Level level, String format, Object... args) {
        Log logFormat = logFormatMap.computeIfAbsent(format, Log::new);
        StringBuilder builder = new StringBuilder(DATE_TIME_FORMATTER.format(LocalDateTime.now()));
        builder.append(" [");
        builder.append(Thread.currentThread().getName());
        builder.append("] ");
        builder.append(level.getName());
        builder.append(" [");
        builder.append(this.name);
        builder.append("] ");
        builder.append(callerFormatInfo());
        builder.append(" - ");
        builder = logFormat.format(builder, args);
        Throwable throwable = logFormat.throwable(args);
        if (throwable != null) {
            StringWriter stringWriter = new StringWriter();
            PrintWriter printWriter = new PrintWriter(stringWriter);
            throwable.printStackTrace(printWriter);
            builder.append(stringWriter);
        }
        return builder.append("\n").toString();
    }

    /**
     * <p>处理日志</p>
     *
     * @param level
     * @param format
     * @param args
     */
    public void doLog(Level level, String format, Object... args) {
        if (this.isEnable(level)) {
            String message = this.format(level, format, args);
            boolean isError = level.getLevel() >= Level.ERROR.getLevel();
            if (isError) {
                this.adapterList.forEach(adapter -> adapter.errorOutput(message));
            } else {
                this.adapterList.forEach(adapter -> adapter.output(message));
            }
        }
    }

    @Override
    public void log(Level level, String format, Object... args) {
        this.doLog(level, format, args);
    }

    @Override
    public void debug(String format, Object... args) {
        this.doLog(Level.DEBUG, format, args);
    }

    @Override
    public void info(String format, Object... args) {
        this.doLog(Level.INFO, format, args);
    }

    @Override
    public void warn(String format, Object... args) {
        this.doLog(Level.WARN, format, args);
    }

    @Override
    public void error(String format, Object... args) {
        this.doLog(Level.ERROR, format, args);
    }

    @Override
    public boolean isEnable(Level level) {
        return this.level.getLevel() <= level.getLevel();
    }

    @Override
    public boolean isEnableDebug() {
        return this.isEnable(Level.DEBUG);
    }

    @Override
    public boolean isEnableInfo() {
        return this.isEnable(Level.INFO);
    }

    @Override
    public boolean isEnableWarn() {
        return this.isEnable(Level.WARN);
    }

    @Override
    public boolean isEnableError() {
        return this.isEnable(Level.ERROR);
    }

    /**
     * <p>调用者格式化信息</p>
     *
     * @return
     */
    public String callerFormatInfo() {
        StackTraceElement[] trace = Thread.currentThread().getStackTrace();
        // 调用深度和当前方法调用位置有关
        int callerDeep = 5;
        StringBuilder builder = new StringBuilder();
//        builder.append(" [");
//        builder.append(trace[callerDeep].getClassName());
//        builder.append("@");
//        builder.append(trace[callerDeep].getMethodName());
//        builder.append("] ");
        builder.append(trace[callerDeep].getFileName());
        builder.append(":");
        builder.append(trace[callerDeep].getLineNumber());
        return builder.toString();
    }
}
