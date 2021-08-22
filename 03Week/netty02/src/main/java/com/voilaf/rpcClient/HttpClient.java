package com.voilaf.rpcClient;

import io.netty.channel.ChannelHandlerContext;

public interface HttpClient {

    void doGet(String url, ChannelHandlerContext ctx);
}
