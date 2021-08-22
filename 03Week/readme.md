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
   
目前存在问题，接入网关后，qps从单服务请求30000+降到了1000+，感觉瓶颈在channelRead里阻塞同步请求下游。