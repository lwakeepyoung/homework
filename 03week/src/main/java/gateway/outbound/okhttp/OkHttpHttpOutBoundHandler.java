package gateway.outbound.okhttp;

import gateway.factory.NameThreadFactory;
import gateway.filter.HeaderHttpResponseFilter;
import gateway.filter.HttpRequestFilter;
import gateway.filter.HttpResponseFilter;
import gateway.outbound.HttpOutboundHandler;
import gateway.route.HttpEndpointRouter;
import gateway.route.RandomRouter;
import gateway.utils.UrlUtils;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelOutboundHandlerAdapter;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpUtil;
import okhttp3.*;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import static io.netty.handler.codec.http.HttpResponseStatus.NO_CONTENT;
import static io.netty.handler.codec.http.HttpResponseStatus.OK;
import static io.netty.handler.codec.http.HttpVersion.HTTP_1_1;

/**
 * @Author: lwa
 * @Date: 2021/10/2 11:08
 */
public class OkHttpHttpOutBoundHandler implements HttpOutboundHandler {

    private OkHttpClient okHttpClient;

    private List<String> backendUrls;

    private HttpEndpointRouter router;

    private ExecutorService proxyService;

    private HttpResponseFilter responseFilter = new HeaderHttpResponseFilter();


    public OkHttpHttpOutBoundHandler(List<String> backends){
        //格式化url
        this.backendUrls = backends.stream().map(b->UrlUtils.formatUrl(b)).collect(Collectors.toList());
        //初始化路由
        router = new RandomRouter();
        //初始化okhttp
        okHttpClient = new OkHttpClient();

    }


    @Override
    public void handle(FullHttpRequest request, ChannelHandlerContext cxt, HttpRequestFilter requestFilter) {
        String backend = router.router(backendUrls);
        // 过滤器 增加header信息
        requestFilter.filter(cxt,request);
        int cores = Runtime.getRuntime().availableProcessors();
        long keepAliveTime = 1000;
        int queueSize = 2048;

        // .DiscardPolicy();
        RejectedExecutionHandler handler = new ThreadPoolExecutor.CallerRunsPolicy();

        proxyService = new ThreadPoolExecutor(cores, cores,
                keepAliveTime, TimeUnit.MILLISECONDS, new ArrayBlockingQueue<>(queueSize),
                new NameThreadFactory("proxyService"), handler);

//        ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors(),
//                new NameThreadFactory("proxyService"));

        Dispatcher dispatcher = new Dispatcher(proxyService);
        dispatcher.setMaxRequests(1024);
        dispatcher.setMaxRequestsPerHost(40);
        okHttpClient = okHttpClient.newBuilder()
                .dispatcher(dispatcher)
                .build();
        Request okHttpRequest = new Request.Builder()
                .url(backend)
                .build();
        okHttpClient.newCall(okHttpRequest)
                        .enqueue(new Callback() {
                            @Override
                            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                                exceptionCaught(cxt,e);
                            }

                            @Override
                            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                                handleResponse(request,response,cxt,responseFilter);
                            }
                        });
    }

    private void handleResponse(FullHttpRequest request, Response response,ChannelHandlerContext ctx, HttpResponseFilter filter) {

        FullHttpResponse fullHttpResponse = null;

        try {
            byte[] body = response.body().bytes();
            fullHttpResponse = new DefaultFullHttpResponse(HTTP_1_1, OK,
                    Unpooled.wrappedBuffer(body));

            fullHttpResponse.headers().set("Content-Type", "application/json");
            fullHttpResponse.headers().setInt("Content-Length", body.length);

            responseFilter.filter(ctx,fullHttpResponse);

        } catch (IOException e) {
            fullHttpResponse =  new DefaultFullHttpResponse(HTTP_1_1,NO_CONTENT);
            exceptionCaught(ctx, e);
        } finally {
            if (request != null) {
                if (!HttpUtil.isKeepAlive(request)) {
                    ctx.write(fullHttpResponse).addListener(ChannelFutureListener.CLOSE);
                } else {
                    //response.headers().set(CONNECTION, KEEP_ALIVE);
                    ctx.write(fullHttpResponse);
                }
            }
            ctx.flush();
        }

    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }


}
