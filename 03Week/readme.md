1. 整合 httpclient/okhttp

        resources/com.voilaf.rpcClient.HttpClient
        配置文件修改：
        com.voilaf.rpcClient.okhttp.OkHttpClientImpl
        实现类：
        com.voilaf.rpcClient.okhttp.OkHttpClientImpl

2. 使用 netty 实现后端 http 访问

        resources/com.voilaf.rpcClient.HttpClient
        配置文件修改：
        com.voilaf.rpcClient.netty.NettyHttpClientImpl
        实现类：
        com.voilaf.rpcClient.netty.NettyHttpClientImpl

3. 实现过滤器

        com.voilaf.handler.HttpApiFilterHandler
        过滤URI非api的请求

        com.voilaf.handler.HttpHandler
        处理http请求并转发到下游服务

4. 实现路由

   gatewayServiceStart.sh脚本启动多个单服务，未区分不同服务

   com.voilaf.service.ServiceDispatcher随机选择单个服务

   启动时命令行参数配置(分号分割)：
   ```
   http://localhost:8001;http://localhost:8002
   ```

---

2021-08-22 20:44

目前存在问题，接入网关后，qps从单服务请求30000+降到了1000+，感觉瓶颈在channelRead里阻塞同步请求下游。


2021-08-22 23:20

附两个压测对比数据
```
wrk -c2 -d10s http://localhost:8001/api/hello --latency
Running 10s test @ http://localhost:8001/api/hello
  2 threads and 2 connections
  Thread Stats   Avg      Stdev     Max   +/- Stdev
    Latency   122.99us  112.65us   3.12ms   98.42%
    Req/Sec     8.48k   365.74     9.20k    84.50%
  Latency Distribution
     50%  110.00us
     75%  121.00us
     90%  134.00us
     99%  482.00us
  168847 requests in 10.00s, 20.16MB read
Requests/sec:  16883.91
Transfer/sec:      2.02MB
```

```
 wrk -c2 -d10s http://localhost:8888/api/hello --latency
Running 10s test @ http://localhost:8888/api/hello
  2 threads and 2 connections
  Thread Stats   Avg      Stdev     Max   +/- Stdev
    Latency   640.35us    1.21ms  34.10ms   98.44%
    Req/Sec     1.28k   414.47     2.02k    74.58%
  Latency Distribution
     50%  505.00us
     75%  590.00us
     90%  747.00us
     99%    2.31ms
  15284 requests in 10.10s, 1.55MB read
  Socket errors: connect 0, read 15284, write 0, timeout 2
Requests/sec:   1513.16
Transfer/sec:    156.64KB
```

通过压测对比Latency能较明显看出区别。
1. 请求下游服务，建立连接 => 网络通信请求 => 网络通信响应
2. 请求网关服务，建立连接 => 网络通信请求 => EventLoop处理 => 下游服务建立连接 => 网络通信请求下游服务 => 下游服务网络通信响应 => EventLoop处理 => 网络通信响应

API网关流程增加了多个环节，还有潜在的线程上下文切换，qps下降应该是正常的事情，只是不知道是否是这个量级。

2021-08-23
网关服务关闭了http请求下游服务，qps不见起色，还是网关服务本身的问题
