package gateway.outbound;

import gateway.filter.HttpRequestFilter;
import gateway.filter.HttpResponseFilter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.FullHttpRequest;

/**
 * @Author: lwa
 * @Date: 2021/10/2 11:02
 */
public interface HttpOutboundHandler {

    void handle(FullHttpRequest request, ChannelHandlerContext cxt, HttpRequestFilter filter);

    void exceptionCaught(ChannelHandlerContext ctx, Throwable cause);
}
