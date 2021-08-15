package com.nekoimi.gunnel.common.context;

import io.netty.channel.group.ChannelGroup;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * nekoimi  2021/8/15 20:43
 * <p>
 * Gunnel Context
 */
public class GunnelContext {
    /**
     * channel group for proxy channel
     * <p>
     * 把对外暴露内网的连接单独分组
     * <p>
     * 使用ChannelGroup可以在channel关闭时自动移除channel
     */
    private ConcurrentMap<String, ChannelGroup> channelsMap = new ConcurrentHashMap<>();
}
