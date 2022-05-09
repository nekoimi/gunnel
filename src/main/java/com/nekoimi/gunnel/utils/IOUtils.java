package com.nekoimi.gunnel.utils;

import com.nekoimi.gunnel.logger.LoggerFactory;

import java.io.IOException;
import java.io.OutputStream;

/**
 * <p>IO 工具类</p>
 *
 * @author nekoimi  2022/4/6 11:35
 */
public class IOUtils {

    /**
     * <p>关闭资源</p>
     *
     * @param closeable 资源
     */
    public static void close(AutoCloseable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (Exception e) {
                LoggerFactory.error(e);
            }
        }
    }

    /**
     * <p>刷新缓冲区并关闭资源</p>
     *
     * @param output 资源
     */
    public static void flushClose(OutputStream output) {
        if (output != null) {
            try {
                output.flush();
                output.close();
            } catch (IOException e) {
                LoggerFactory.error(e);
            }
        }
    }
}
