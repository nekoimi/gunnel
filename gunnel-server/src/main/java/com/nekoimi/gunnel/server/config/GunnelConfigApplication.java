package com.nekoimi.gunnel.server.config;

import com.nekoimi.gunnel.common.constants.SystemConstants;
import com.nekoimi.gunnel.common.utils.YamlUtils;
import com.nekoimi.gunnel.server.context.GunnelContext;
import com.nekoimi.gunnel.server.gunnel.AbstractGunnelApplication;
import lombok.extern.slf4j.Slf4j;

/**
 * nekoimi  2021/8/16 21:47
 */
@Slf4j
public class GunnelConfigApplication extends AbstractGunnelApplication {
    private ServerProperties serverProperties;
    public GunnelConfigApplication(String name, GunnelContext context) {
        super(name, context);
    }

    private void loadServerPropertiesByYaml() {
        serverProperties = YamlUtils.loadAsType(SystemConstants.DEFAULT_CONFIG, SystemConstants.APPLICATION_NAME, ServerProperties.class);
        if (serverProperties == null) {
            log.error("Load server config error!");
        }
    }

    @Override
    public void start() {
        loadServerPropertiesByYaml();
        context.setProperties(serverProperties);
    }

    @Override
    public void restart() {
        loadServerPropertiesByYaml();
        context.setProperties(serverProperties);
    }

    @Override
    public void shutdown() {
        context.setProperties(null);
    }

    @Override
    public void run() {
        // TODO Ignore...
    }
}
