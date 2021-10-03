package gateway.inbound;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;

import java.util.List;

/**
 * @Author: lwa
 * @Date: 2021/10/1 15:37
 */
public class HttpInitializer extends ChannelInitializer<SocketChannel> {

    private List<String> proxyServer;

    public HttpInitializer(List<String> proxyServer) {
        this.proxyServer = proxyServer;
    }

    @Override
    protected void initChannel(SocketChannel ch) {
        //指定解码器
        ch.pipeline().addLast(new HttpServerCodec());
        ch.pipeline().addLast(new HttpObjectAggregator(1024 * 1024));
        ch.pipeline().addLast(new NioServerHandler(proxyServer));
    }
}
