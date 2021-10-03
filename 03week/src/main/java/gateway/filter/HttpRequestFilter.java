package gateway.filter;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.FullHttpRequest;

/**
 * @Author: lwa
 * @Date: 2021/10/1 11:41
 */
public interface HttpRequestFilter {
    void filter(ChannelHandlerContext ctx, FullHttpRequest request);
}
