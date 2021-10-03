package gateway.inbound;

import gateway.filter.HeaderHttpRequestFilter;
import gateway.outbound.HttpOutboundHandler;
import gateway.outbound.httpclient4.HttpClientHttpOutBoundHandler;
import gateway.outbound.okhttp.OkHttpHttpOutBoundHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.util.ReferenceCountUtil;

import java.util.List;

/**
 * @Author: lwa
 * @Date: 2021/10/1 11:02
 */
public class NioServerHandler extends ChannelInboundHandlerAdapter {

    private HeaderHttpRequestFilter filter = new HeaderHttpRequestFilter();

    private HttpOutboundHandler handler;

    private List<String> serverUrls;

    public NioServerHandler(List endpointUrls){
        handler = new OkHttpHttpOutBoundHandler(endpointUrls);
        //handler = new HttpClientHttpOutBoundHandler(endpointUrls);
    }


    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        FullHttpRequest request = (FullHttpRequest)msg;
        try{
            handler.handle(request,ctx,filter);
        }finally {
            ReferenceCountUtil.release(msg);
        }
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.flush();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        super.exceptionCaught(ctx, cause);
    }
}
