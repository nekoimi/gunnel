package com.nekoimi.gunnel.common.utils;

import java.util.Random;

/**
 * nekoimi  2021/8/27 14:45
 */
public class IdUtils {

    /**
     * @return
     */
    public static long randLong() {
        Random random = new Random();
        long id;
        do {
            id = random.nextLong();
        } while (id <= 0);
        return id;
    }
}
