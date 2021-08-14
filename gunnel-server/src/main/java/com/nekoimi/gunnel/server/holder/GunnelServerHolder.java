package com.nekoimi.gunnel.server.holder;

import io.netty.channel.Channel;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;


/**
 * nekoimi  2021/8/14 18:37
 */
public class GunnelServerHolder {
    private final static ChannelGroup gunnelChannels = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);
    public static boolean addChannel(Channel channel) {
        return gunnelChannels.add(channel);
    }
}
