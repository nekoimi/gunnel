package com.nekoimi.gunnel.logger;

import com.nekoimi.gunnel.contract.Releasable;
import com.nekoimi.gunnel.utils.IOUtils;

import java.io.IOException;
import java.io.OutputStream;

/**
 * <p>日志适配器</p>
 *
 * @author nekoimi  2022/4/6 9:40
 */
public abstract class LoggerAdapter implements Releasable {

    /**
     * <p>是否准备好</p>
     */
    private volatile boolean available = true;

    /**
     * <p>是否关闭错误输出</p>
     */
    private boolean closeErrorOutput = true;

    /**
     * <p>日志输出</p>
     */
    protected OutputStream output;

    /**
     * <p>错误日志输出</p>
     */
    protected OutputStream errorOutput;

    /**
     */
    protected LoggerAdapter() {
        this.output = this.output();
        this.errorOutput = this.errorOutput();
        this.closeErrorOutput = this.output != this.errorOutput;
    }

    /**
     * <p>输出日志</p>
     *
     * @param message 日志内容
     */
    public void output(String message) {
        if (this.available) {
            try {
                output.write(message.getBytes());
            } catch (IOException e) {
                LoggerFactory.error(e);
            }
        }
    }

    /**
     * <p>输出错误日志</p>
     *
     * @param message 日志内容
     */
    public void errorOutput(String message) {
        if (this.available) {
            try {
                errorOutput.write(message.getBytes());
            } catch (IOException e) {
                LoggerFactory.error(e);
            }
        }
    }

    /**
     * <p>关闭清除日志</p>
     */
    public void release() {
        this.available = false;
        IOUtils.flushClose(this.output);
        if (this.closeErrorOutput) {
            IOUtils.flushClose(errorOutput);
        }
    }

    /**
     * <p>常规日志输出</p>
     *
     * @return
     */
    protected abstract OutputStream output();

    /**
     * <p>错误日志输出</p>
     *
     * @return
     */
    protected abstract OutputStream errorOutput();
}
