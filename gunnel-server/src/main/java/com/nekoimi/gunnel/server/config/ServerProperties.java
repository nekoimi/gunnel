package com.nekoimi.gunnel.server.config;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * nekoimi  2021/8/16 21:42
 */
@Getter
@Setter
@ToString
public class ServerProperties {
    private int port = 9933;
    private IdleCheck idleCheck;

    @Getter
    @Setter
    @ToString
    public static class IdleCheck {
        private long readerTime = 60;
    }
}
