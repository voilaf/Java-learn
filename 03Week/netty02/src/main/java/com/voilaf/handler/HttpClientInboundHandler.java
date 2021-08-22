package com.voilaf.handler;

import com.voilaf.ApplicationContext;
import com.voilaf.service.ServiceDispatcher;
import com.voilaf.util.ResponseUtil;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.*;

public class HttpClientInboundHandler extends ChannelInboundHandlerAdapter {

    private ChannelHandlerContext clientCtx;

    public HttpClientInboundHandler(ChannelHandlerContext clientCtx) {
        super();
        this.clientCtx = clientCtx;
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if(msg instanceof HttpContent) {
            HttpContent content = (HttpContent)msg;
            ByteBuf buf = content.content();
            String result = buf.toString(io.netty.util.CharsetUtil.UTF_8);
            buf.release();
            ResponseUtil.handlerResponse(clientCtx, result);
            ctx.close();
            clientCtx.close();
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
