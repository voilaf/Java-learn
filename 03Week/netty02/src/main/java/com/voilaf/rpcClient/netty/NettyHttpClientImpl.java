package com.voilaf.rpcClient.netty;

import com.voilaf.handler.HttpClientInboundHandler;
import com.voilaf.rpcClient.HttpClient;
import com.voilaf.util.ResponseUtil;
import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.http.*;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

public class NettyHttpClientImpl implements HttpClient {

    private EventLoopGroup eventLoopGroup;

    public NettyHttpClientImpl() {
        EventLoopGroup eventLoopGroup = new NioEventLoopGroup(4);
        this.eventLoopGroup = eventLoopGroup;
    }

    @Override
    public void doGet(String url, ChannelHandlerContext ctx) {
        Bootstrap bootstrap = new Bootstrap();
        bootstrap.group(eventLoopGroup)
                .channel(NioSocketChannel.class)
                .option(ChannelOption.SO_KEEPALIVE, true)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {
                        ChannelPipeline pipeline = ch.pipeline();
                        // 客户端接收到的是httpResponse响应，所以要使用HttpResponseDecoder进行解码
                        pipeline.addLast(new HttpResponseDecoder());
                        // 客户端发送的是httprequest，所以要使用HttpRequestEncoder进行编码
                        pipeline.addLast(new HttpRequestEncoder());
                        pipeline.addLast(new HttpClientInboundHandler(ctx));
                    }
                });
        try {
            URL urlObj = new URL(url);
            String host = urlObj.getHost();
            int port = urlObj.getPort();
            ChannelFuture channelFuture = bootstrap.connect(host, port).sync();
            URI uri = new URI(url);
            String msg = "";
            DefaultFullHttpRequest request = new DefaultFullHttpRequest(HttpVersion.HTTP_1_1, HttpMethod.GET,
                    uri.toASCIIString(), Unpooled.wrappedBuffer(msg.getBytes("UTF-8")));

            // 构建http请求
            request.headers().set(HttpHeaders.Names.HOST, host);
            request.headers().set(HttpHeaders.Names.CONNECTION, HttpHeaders.Values.KEEP_ALIVE);
            request.headers().set(HttpHeaders.Names.CONTENT_LENGTH, request.content().readableBytes());
            // 发送http请求
            channelFuture.channel().writeAndFlush(request);
            channelFuture.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }
}
