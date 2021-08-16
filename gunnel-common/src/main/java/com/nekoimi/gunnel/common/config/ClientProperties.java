package com.nekoimi.gunnel.common.config;

import com.nekoimi.gunnel.common.enums.Protocol;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.LinkedList;
import java.util.List;

/**
 * nekoimi  2021/8/15 15:52
 */
@Getter
@Setter
@ToString
public class ClientProperties {
    private Protocol protocol;
    private Server server;
    private ID id;
    private Proxy proxy;

    @Getter
    @Setter
    @ToString
    public static class Server {
        private String address;
        private int port;
    }

    @Getter
    @Setter
    @ToString
    public static class ID {
        private String identifier;
        private String key;
    }

    @Getter
    @Setter
    @ToString
    public static class Proxy {
        private List<TcpProxyProperties> tcp = new LinkedList<>();
        private List<HttpProxyProperties> http = new LinkedList<>();
    }
}
