package com.lemon.xsign.client;

/**
 * Created by AnxiangLemon on 2019/2/25.
 */


import android.util.Log;

import java.util.concurrent.TimeUnit;

import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.EventLoop;

/**
 * 负责监听启动时连接失败，重新连接功能
 * @author yinjihuan
 *
 */
public class ConnectionListener implements ChannelFutureListener {

    private NettyClient nettyClient = new NettyClient();

    @Override
    public void operationComplete(ChannelFuture channelFuture) throws Exception {
        if (!channelFuture.isSuccess()) {
            final EventLoop loop = channelFuture.channel().eventLoop();
            loop.schedule(new Runnable() {
                @Override
                public void run() {
                    Log.e("ConnectionListener: " ,"服务端链接不上，开始重连操作.." );
                    nettyClient.connect(NettyClient.HOST, NettyClient.PORT);

                }
            }, 1L, TimeUnit.SECONDS);
        } else {
            Log.e("ConnectionListener: " ,"服务端链接成功..." );
        }
    }
}