package gateway.filter;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.FullHttpResponse;

/**
 * @Author: lwa
 * @Date: 2021/10/1 11:42
 */
public class HeaderHttpResponseFilter implements HttpResponseFilter{

    @Override
    public void filter(ChannelHandlerContext ctx, FullHttpResponse response) {
        response.headers().set("responseHeader","2");
    }
}
