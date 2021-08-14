package com.nekoimi.gunnel.server;

import com.nekoimi.gunnel.common.enums.MsgType;
import com.nekoimi.gunnel.common.protocol.GunnelMessage;
import org.junit.Test;

/**
 * nekoimi  2021/8/14 22:53
 */
public class GunnelMessageTests {

    @Test
    public void testMessageToByte() {
        GunnelMessage message = GunnelMessage.builder().message("hello world").type(MsgType.GU_DATA).build();
    }

}
