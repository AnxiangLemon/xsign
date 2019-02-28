package com.lemon.xsign.client;

/**
 * Created by AnxiangLemon on 2019/2/17.
 */


import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;


public class NettyClient  {
    public final static String HOST = "192.168.31.178";
    public final static int PORT = 9527;


    private static EventLoopGroup workerGroup = null;

    private Channel channel;

    //重连调用的函数
    public Channel connect(String host, int port) {
        doConnect(host, port);
        return this.channel;
    }


    public void doConnect(String host, int port) {

        try {
            //设置一个多线程循环器
            workerGroup = new NioEventLoopGroup();
            //启动附注类
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(workerGroup);
            //指定所使用的NIO传输channel
            bootstrap.channel(NioSocketChannel.class);
            //指定客户端初始化处理
            bootstrap.handler(new ClientIniterHandler());

            //连接服务
            //   Channel channel = bootstrap.connect(host, port).sync().channel();

            ChannelFuture f = bootstrap.connect(host, port);
            f.addListener(new ConnectionListener());
            channel = f.channel();

        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    //关闭链接
    public static void closeLink() {
        workerGroup.shutdownGracefully();
    }

}


