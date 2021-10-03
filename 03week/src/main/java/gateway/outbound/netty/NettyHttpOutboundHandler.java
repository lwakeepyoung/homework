package gateway.outbound.netty;

import gateway.filter.HttpRequestFilter;
import gateway.outbound.HttpOutboundHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelOutboundHandlerAdapter;
import io.netty.handler.codec.http.FullHttpRequest;

/**
 * @Author: lwa
 * @Date: 2021/10/2 21:39
 */
public class NettyHttpOutboundHandler extends ChannelOutboundHandlerAdapter {


    public void handle(FullHttpRequest request, ChannelHandlerContext cxt, HttpRequestFilter filter) {

    }

}
