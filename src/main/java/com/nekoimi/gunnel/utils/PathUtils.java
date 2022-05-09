package com.nekoimi.gunnel.utils;

import java.io.File;

/**
 * <p>路径工具</p>
 *
 * @author nekoimi  2022/4/6 14:56
 */
public class PathUtils {

    /**
     * <p>创建目录</p>
     *
     * @param paths 目录
     */
    public static void mkDirs(String paths) {
        File pathFile = new File(paths);
        pathFile.mkdirs();
    }
}
