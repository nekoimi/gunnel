package com.nekoimi.gunnel.server;

import com.nekoimi.gunnel.server.net.proxy.GunnelServer;
import lombok.extern.slf4j.Slf4j;

/**
 * nekoimi  2021/8/13 20:52
 */
@Slf4j
public class ServerApplication {

    public static void main(String[] args) {
        new GunnelServer().start();
    }
}
