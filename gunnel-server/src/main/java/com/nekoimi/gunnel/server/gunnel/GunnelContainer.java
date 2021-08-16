package com.nekoimi.gunnel.server.gunnel;

import com.nekoimi.gunnel.server.contract.GunnelApplication;
import lombok.extern.slf4j.Slf4j;

import java.util.LinkedList;
import java.util.List;

/**
 * nekoimi  2021/8/16 22:08
 */
@Slf4j
public class GunnelContainer {
    private final List<GunnelApplication> applications = new LinkedList<>();

    public void register(GunnelApplication app) {
        applications.add(app);
    }

    public void runAll() {
        for (GunnelApplication app : applications) {
            app.start();
        }
    }
}
