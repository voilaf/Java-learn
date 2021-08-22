package com.voilaf.rpcClient.okhttp;

import com.voilaf.rpcClient.HttpClient;
import com.voilaf.util.ResponseUtil;
import io.netty.channel.ChannelHandlerContext;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;

public class OkHttpClientImpl implements HttpClient {

    private final OkHttpClient client;

    public OkHttpClientImpl() {
        client = new OkHttpClient();
    }

    @Override
    public void doGet(String url, ChannelHandlerContext clientCtx) {
        Request request = new Request.Builder().url(url).get().build();
        Response response = null;
        String message = "";
        try {
            response = client.newCall(request).execute();
            if (response != null && response.isSuccessful()) {
                message = response.body().string();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (response != null) {
                response.close();
            }

            ResponseUtil.handlerResponse(clientCtx, message);
            clientCtx.close();
        }
    }
}
