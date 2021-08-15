package com.nekoimi.gunnel.common.config;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * nekoimi  2021/8/15 15:52
 */
@Getter
@Setter
@ToString
public class ClientProperties {
    private Server server;

    @Getter
    @Setter
    @ToString
    public static class Server {
        private String address;
        private int port;
    }
}
