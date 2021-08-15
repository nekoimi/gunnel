package com.nekoimi.gunnel.server.initializer;

import com.nekoimi.gunnel.common.context.GunnelContext;
import com.nekoimi.gunnel.common.initializer.GunnelChannelInitializer;
import com.nekoimi.gunnel.server.handler.GunnelServerHandler;
import io.netty.channel.ChannelPipeline;

/**
 * nekoimi  2021/8/14 17:16
 */
public class GunnelServerInitializer extends GunnelChannelInitializer {
    public GunnelServerInitializer(GunnelContext context) {
        super(context);
    }

    @Override
    protected void channel0(ChannelPipeline pipeline) {
        // Gunnel Server 逻辑实现处理
        pipeline.addLast(new GunnelServerHandler(context));
    }
}
