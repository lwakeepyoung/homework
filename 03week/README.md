### 03week作业说明

1. 必做1：整合你上次作业的 httpclient/okhttp；
在outbound包下的HttpClient4 和 okhttp包中
2. 必做2实现过滤器，在filter包下


思路 
1. 使用netty实现接受http请求。
2. 使用okhttp/httpClient4实现发送请求
3. 处理请求，转发到实际处理业务的后端
4. 处理业务后端响应
5. 使用多线程处理发送请求（优化）
6. 实现过滤器，增加header

    