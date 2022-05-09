package com.nekoimi.gunnel.logger;

import com.nekoimi.gunnel.utils.ArrayUtils;
import com.nekoimi.gunnel.utils.StringUtils;

import java.util.Objects;

/**
 * <p>日志</p>
 *
 * @author nekoimi  2022/4/2 14:01
 */
public class Log {

    /**
     * <p>日志填充符</p>
     */
    private final static String FORMAT_SYMBOL = "{}";

    /**
     * <p>日志填充符长度</p>
     */
    private final static int FORMAT_SYMBOL_LENGTH = FORMAT_SYMBOL.length();

    /**
     * <p>日志原始信息</p>
     */
    private final String message;

    /**
     * <p>格式化信息</p>
     */
    private final String[] format;

    /**
     * <p>日志信息分词后最后一段</p>
     */
    private String suffix;

    public Log(String message) {
        this.message = message;
        this.format = takeFormat();
    }

    /**
     * <p>根据 {FORMAT_SYMBOL} 分词</p>
     *
     * @return
     */
    private String[] takeFormat() {
        // 默认{format}数组开始索引
        int index = 0;
        // 默认{format}数组长度
        String[] format = new String[Byte.SIZE];
        int last = 0;
        int pos = 0;
        while ((pos = message.indexOf(FORMAT_SYMBOL, last)) != -1) {
            format[index++] = message.substring(last, pos);
            last = pos + FORMAT_SYMBOL_LENGTH;
            if (index > format.length) {
                format = ArrayUtils.resize(format, String.class, Byte.SIZE);
            }
        }
        if (last != 0 && last < message.length()) {
            suffix = message.substring(last);
        }
        String[] formatReturns = new String[index];
        System.arraycopy(format, 0, formatReturns, 0, index);
        return formatReturns;
    }

    /**
     * <p>格式化日志</p>
     *
     * @param args
     * @return
     */
    public final String format(Object... args) {
        StringBuilder builder = new StringBuilder();
        return format(builder, args).toString();
    }

    /**
     * <p>格式化日志</p>
     *
     * @param builder
     * @param args
     * @return
     */
    public final StringBuilder format(StringBuilder builder, Object... args) {
        builder = Objects.requireNonNullElse(builder, new StringBuilder());
        if (ArrayUtils.isEmpty(args) || ArrayUtils.isEmpty(format)) {
            builder.append(message);
            return builder;
        }
        int argLength = args.length;
        int formatLength = format.length;
        for (int i = 0; i < formatLength; i++) {
            builder.append(format[i]);
            if (i >= argLength) {
                // args的数量超过了format中{}符号的数量
                // 没有可以替换的args参数，所以直接拼接{}符号
                builder.append(FORMAT_SYMBOL);
            } else {
                builder.append(args[i]);
            }
        }
        if (StringUtils.isNotEmpty(suffix)) {
            builder.append(suffix);
        }
        return builder;
    }

    /**
     * <p>获取异常参数</p>
     *
     * @param args
     * @return
     */
    public final Throwable throwable(Object... args) {
        if (ArrayUtils.isEmpty(args)) {
            return null;
        }
        Object arg = args[args.length - 1];
        if (arg instanceof Throwable t) {
            return t;
        }
        return null;
    }
}
