package com.nekoimi.gunnel.client.initializer;

import com.nekoimi.gunnel.client.handler.GunnelClientHandler;
import com.nekoimi.gunnel.common.context.GunnelContext;
import com.nekoimi.gunnel.common.initializer.GunnelChannelInitializer;
import io.netty.channel.ChannelPipeline;

/**
 * nekoimi  2021/8/14 17:16
 */
public class GunnelClientInitializer extends GunnelChannelInitializer {

    public GunnelClientInitializer(GunnelContext context) {
        super(context);
    }

    @Override
    protected void channel0(ChannelPipeline pipeline) {
        // Gunnel Client 逻辑实现处理
        pipeline.addLast(new GunnelClientHandler(context));
    }
}
