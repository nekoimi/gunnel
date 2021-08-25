package com.nekoimi.gunnel.server.config;

import com.google.common.eventbus.Subscribe;
import com.nekoimi.gunnel.common.constants.SystemConstants;
import com.nekoimi.gunnel.common.utils.YamlUtils;
import com.nekoimi.gunnel.server.context.GunnelContext;
import com.nekoimi.gunnel.server.event.ShutdownEvent;
import com.nekoimi.gunnel.server.gunnel.GunnelApplication;
import lombok.extern.slf4j.Slf4j;

/**
 * nekoimi  2021/8/16 21:47
 */
@Slf4j
public class ConfigApplication extends GunnelApplication {
    private ServerProperties serverProperties = new ServerProperties();
    public ConfigApplication(String name, GunnelContext context) {
        super(name, context);
    }

    private void loadServerPropertiesByYaml() {
        try {
            serverProperties = YamlUtils.loadAsType(SystemConstants.DEFAULT_CONFIG, SystemConstants.APPLICATION_NAME, ServerProperties.class);
        } catch (Exception e) {
            log.error("Load server config error! {}", e.getMessage());
        }
    }

    @Override
    public void start() {
        context().eventBus.register(this);
        loadServerPropertiesByYaml();
        context().setProperties(serverProperties);
    }

    @Override
    public void restart() {
        start();
    }

    @Override
    @Subscribe
    public void shutdown(ShutdownEvent event) {
        context().eventBus.unregister(this);
    }
}
