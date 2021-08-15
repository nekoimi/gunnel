package com.nekoimi.gunnel.server;

import com.nekoimi.gunnel.server.net.GunnelServer;
import lombok.extern.slf4j.Slf4j;

/**
 * nekoimi  2021/8/13 20:52
 */
@Slf4j
public class GunnelServerApplication {

    public static void main(String[] args) {
        GunnelServer.run(args);
    }
}
