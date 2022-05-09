package com.nekoimi.gunnel.utils;

/**
 * <p>字符串工具类</p>
 *
 * @author nekoimi  2022/4/2 15:31
 */
public class StringUtils {

    /**
     * <p>删除开头的{remove}</p>
     *
     * @param str    文本
     * @param remove 删除的内容
     * @return
     */
    public static String removeStart(CharSequence str, CharSequence remove) {
        if (str.isEmpty() || remove.isEmpty()) {
            return str.toString();
        }

        if (String.valueOf(str.charAt(0)).equalsIgnoreCase(remove.toString())) {
            return str.subSequence(1, str.length()).toString();
        }

        return str.toString();
    }

    /**
     * <p>删除开头{remove}, 然后拼接一个{remove}到开头</p>
     *
     * @param str    文本
     * @param remove 删除拼接内容
     * @return
     */
    public static String removeStartThenAppend(CharSequence str, CharSequence remove) {
        return remove + removeStart(str, remove);
    }

    /**
     * <p>删除末尾的{remove}</p>
     *
     * @param str    文本
     * @param remove 删除的内容
     * @return
     */
    public static String removeEnd(CharSequence str, CharSequence remove) {
        if (str.isEmpty() || remove.isEmpty()) {
            return str.toString();
        }

        if (String.valueOf(str.charAt(str.length())).equalsIgnoreCase(remove.toString())) {
            return str.subSequence(0, str.length() - 1).toString();
        }

        return str.toString();
    }

    /**
     * <p>删除末尾{remove}, 然后拼接一个{remove}到末尾</p>
     *
     * @param str    文本
     * @param remove 删除拼接内容
     * @return
     */
    public static String removeEndThenAppend(CharSequence str, CharSequence remove) {
        return removeEnd(str, remove) + remove;
    }

    /**
     * <p>判断字符是否为空</p>
     *
     * @param cs 字符对象
     * @return
     */
    public static boolean isEmpty(CharSequence cs) {
        return !isNotEmpty(cs);
    }

    /**
     * <p>判断字符是否不为空</p>
     *
     * @param cs 字符对象
     * @return
     */
    public static boolean isNotEmpty(CharSequence cs) {
        return cs != null && cs.length() > 0;
    }

    /**
     * <p>判断字符串是否是空白字符</p>
     *
     * @param cs 字符对象
     * @return
     */
    public static boolean isBlank(CharSequence cs) {
        return !isNotBlank(cs);
    }

    /**
     * <p>判断字符是否不为空白字符</p>
     * <p> StringUtils.isNotBlank(null) ==> false </p>
     * <p> StringUtils.isNotBlank("") ==> false </p>
     * <p> StringUtils.isNotBlank("   ") ==> false </p>
     * <p> StringUtils.isNotBlank(" \r\n") ==> false </p>
     *
     * @param cs 字符对象
     * @return
     */
    public static boolean isNotBlank(CharSequence cs) {
        int length;

        if (cs == null || (length = cs.length()) <= 0) {
            return false;
        }

        for (int i = 0; i < length; i++) {
            if (isBlankChar(cs.charAt(i))) {
                return false;
            }
        }

        return true;
    }

    /**
     * <p>是否是空白字符</p>
     *
     * @param c 字符
     * @return
     */
    public static boolean isBlankChar(char c) {
        return Character.isWhitespace(c)
                || Character.isSpaceChar(c)
                || c == '\ufeff'
                || c == '\u202a'
                || c == '\u0000';
    }
}
