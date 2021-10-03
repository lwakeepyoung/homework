package gateway;

import gateway.inbound.NettyServer;

import java.util.Arrays;
import java.util.stream.Stream;

/**
 * @Author: lwa
 * @Date: 2021/10/2 18:16
 */
public class NettyServerApplication {
    public final static String GATEWAY_NAME = "NIOGateway";
    public final static String GATEWAY_VERSION = "3.0.0";

    public static void main(String[] args) {
        String proxyPort = System.getProperty("proxyPort","8888");

        String proxyServers = System.getProperty("proxyServers","http://localhost:8801,http://localhost:8802");
        System.out.println(GATEWAY_NAME + " " + GATEWAY_VERSION +" starting...");
        int port = Integer.parseInt(proxyPort);
        NettyServer nettyServer = new NettyServer(port, Arrays.asList(proxyServers.split(",")));
        System.out.println(GATEWAY_NAME + " " + GATEWAY_VERSION +" started at http://localhost:" + port + " for server:" + nettyServer.toString());
        nettyServer.run();
    }
}
