package com.nekoimi.gunnel.common.holder;

import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;

/**
 * nekoimi  2021/8/14 20:28
 *
 * 服务端:
 *      对外暴露ChannelGroup
 *      把对外暴露内网的连接单独分组
 *
 * 使用ChannelGroup可以在channel关闭时自动移除channel
 */
public class GunnelHolder {
    private final static ChannelGroup channels = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);
    public static ChannelGroup channels() {
        return channels;
    }
}
