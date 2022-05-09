package com.nekoimi.gunnel.logger.adapter;

import com.nekoimi.gunnel.logger.LoggerAdapter;

import java.io.OutputStream;

/**
 * <p>控制台日志适配器</p>
 *
 * @author nekoimi  2022/4/6 11:29
 */
public class ConsoleLoggerAdapter extends LoggerAdapter {

    public static final String ADAPTER = "console";

    @Override
    protected OutputStream output() {
        return System.out;
    }

    @Override
    protected OutputStream errorOutput() {
        return System.err;
    }
}
