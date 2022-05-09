package com.nekoimi.gunnel.logger;

import com.nekoimi.gunnel.contract.Releasable;
import com.nekoimi.gunnel.factory.SingletonFactory;
import com.nekoimi.gunnel.logger.adapter.ConsoleLoggerAdapter;
import com.nekoimi.gunnel.utils.ClazzUtils;
import com.nekoimi.gunnel.utils.PathUtils;
import com.nekoimi.gunnel.utils.StringUtils;

import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * <p>日志工厂</p>
 *
 * @author nekoimi  2022/4/6 9:49
 */
public final class LoggerFactory {
    /**
     * <p>日志文件目录</p>
     */
    public static final String LOG_PATH = "logs";

    /**
     * <p>基础错误日志文件</p>
     */
    private static final String errorLogPath = LOG_PATH + "/error.log";

    /**
     * <p>日志适配器注册列表</p>
     */
    private static final Map<String, Class<? extends LoggerAdapter>> loggerAdapterRegistry = new ConcurrentHashMap<>();

    static {
        PathUtils.mkDirs(LOG_PATH);
        LoggerFactory.adapterRegistry(ConsoleLoggerAdapter.ADAPTER.toLowerCase(), ConsoleLoggerAdapter.class);
    }

    /**
     * <p>日志列表</p>
     */
    private final Map<String, Logger> loggers;

    /**
     * <p>日志适配器列表</p>
     */
    private final List<LoggerAdapter> adapters;

    private LoggerFactory() {
        loggers = new ConcurrentHashMap<>();
        List<LoggerAdapter> loggerAdapterList = new ArrayList<>();
        String configAdapter = LogProperties.getInstance().getAdapter();
        if (StringUtils.isNotEmpty(configAdapter)) {
            List<String> adapterList = List.of(configAdapter.split("[,]"));
            for (String adapterName : adapterList) {
                if (StringUtils.isNotEmpty(adapterName)) {
                    Class<? extends LoggerAdapter> adapterClazz = loggerAdapterRegistry.get(adapterName.toLowerCase());
                    if (adapterClazz != null) {
                        LoggerAdapter adapter = ClazzUtils.newInstance(adapterClazz);
                        if (adapter != null) {
                            loggerAdapterList.add(adapter);
                        }
                    }
                }
            }
        }
        adapters = loggerAdapterList;
    }

    /**
     * <p>获取实例</p>
     *
     * @return 实例
     */
    public static LoggerFactory getInstance() {
        return SingletonFactory.singletonInstance(LoggerFactory.class);
    }

    /**
     * <p>注册日志适配器</p>
     *
     * @param adapterName  适配器名称
     * @param adapterClazz 适配器类型
     */
    public static void adapterRegistry(String adapterName, Class<? extends LoggerAdapter> adapterClazz) {
        loggerAdapterRegistry.putIfAbsent(adapterName, adapterClazz);
    }

    /**
     * <p>获取日志</p>
     *
     * @param clazz 类型
     * @return
     */
    public static Logger getLogger(Class<?> clazz) {
        return getInstance().loggers.computeIfAbsent(clazz.getName(), LoggerImpl::new);
    }

    /**
     * <p>获取日志适配器列表</p>
     *
     * @return
     */
    public static List<LoggerAdapter> getAdapters() {
        return getInstance().adapters;
    }

    /**
     * <p>异常消息日志</p>
     *
     * @param t 异常
     */
    public static void error(Throwable t) {
        try (FileOutputStream outputStream = new FileOutputStream(errorLogPath, true);
             PrintWriter writer = new PrintWriter(outputStream)) {
            t.printStackTrace(writer);
            writer.flush();
        } catch (Exception e) {
            error(e);
        }
    }

    /**
     * <p>关闭日志</p>
     */
    public static void shutdown() {
        getInstance().adapters.forEach(Releasable::release);
    }
}
