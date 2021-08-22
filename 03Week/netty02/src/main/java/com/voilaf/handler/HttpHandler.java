package com.voilaf.handler;

import com.voilaf.ApplicationContext;
import com.voilaf.service.ServiceDispatcher;
import com.voilaf.util.ResponseUtil;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.*;
import io.netty.util.ReferenceCountUtil;


public class HttpHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        try {
            FullHttpRequest fullHttpRequest = (FullHttpRequest) msg;
            String uri = fullHttpRequest.uri();

            String serviceUrl = ServiceDispatcher.getInstance().getRandomServiceAddress();
//            System.out.println("路由转发请求start:" + serviceUrl + uri);
            ApplicationContext.getInstance().getHttpClient().doGet(serviceUrl + uri, ctx);
//            System.out.println("路由转发请求end:" + serviceUrl + uri);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            ReferenceCountUtil.release(msg);
        }
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.flush();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
