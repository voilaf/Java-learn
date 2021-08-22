package com.voilaf.handler;

import com.voilaf.util.ResponseUtil;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.*;
import io.netty.util.ReferenceCountUtil;

public class HttpApiFilterHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        // 实现一个api前缀过滤器
        FullHttpRequest fullHttpRequest = (FullHttpRequest) msg;
        String uri = fullHttpRequest.uri();
        if (uri.startsWith("/api")) {
            super.channelRead(ctx, msg);
        } else {
            try {
                ResponseUtil.handlerResponse(ctx, "路由地址前缀必须为/api");
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                ReferenceCountUtil.release(msg);
            }
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
