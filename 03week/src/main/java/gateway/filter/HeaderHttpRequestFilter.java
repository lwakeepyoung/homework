package gateway.filter;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.FullHttpRequest;

/**
 * @Author: lwa
 * @Date: 2021/10/1 11:42
 */
public class HeaderHttpRequestFilter implements HttpRequestFilter{

    @Override
    public void filter(ChannelHandlerContext ctx, FullHttpRequest request) {
        request.headers().set("requestHeader","header");
    }
}
