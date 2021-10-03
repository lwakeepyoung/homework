package gateway.filter;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.FullHttpResponse;

/**
 * @Author: lwa
 * @Date: 2021/10/1 11:41
 */
public interface HttpResponseFilter {

    void filter(ChannelHandlerContext ctx, FullHttpResponse response);
}
