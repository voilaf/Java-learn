package com.voilaf;

import com.voilaf.handler.HttpServerInitializer;
import com.voilaf.rpcClient.HttpClient;
import com.voilaf.service.ServiceDispatcher;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

import java.net.InetSocketAddress;
import java.util.ServiceLoader;

public class NettyServerApplication {

    public static void main(String[] args) throws Exception {
        if (args.length == 0) {
            System.out.println("httpServer启动失败，请输入服务地址");
            return;
        }
        // 主线程初始化路由分发器
        ServiceDispatcher serviceDispatcher = ServiceDispatcher.getInstance();
        serviceDispatcher.setServiceConfig(args[0]);
        // 主线程初始化单例对象存储上下文
        ApplicationContext applicationContext = ApplicationContext.getInstance();
        // 初始化RPC客户端
        ServiceLoader<HttpClient> clients = ServiceLoader.load(HttpClient.class);
        for (HttpClient httpClient : clients) {
            applicationContext.setHttpClient(httpClient);
            break;
        }

        NioEventLoopGroup bossGroup = new NioEventLoopGroup(2);
        NioEventLoopGroup workGroup = new NioEventLoopGroup(16);
        ServerBootstrap serverBootstrap = new ServerBootstrap();
        serverBootstrap.group(bossGroup, workGroup)
                .channel(NioServerSocketChannel.class)
                .handler(new LoggingHandler(LogLevel.DEBUG))
                .childHandler(new HttpServerInitializer());

        int port = 8888;
        System.out.println("httpServer启动，监听地址:http://localhost:"+port);
        serviceDispatcher.printServiceAddress();
        ChannelFuture channelFuture = serverBootstrap.bind(new InetSocketAddress(port)).sync();
        channelFuture.channel().closeFuture().sync();
    }
}
