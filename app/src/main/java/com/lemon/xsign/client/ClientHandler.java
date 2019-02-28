package com.lemon.xsign.client;

/**
 * Created by AnxiangLemon on 2019/2/17.
 */


import android.content.Context;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.lemon.xsign.alibaba.TBXsign;
import com.lemon.xsign.nettymodel.Result;
import com.lemon.xsign.nettymodel.TbMsg;

import java.util.concurrent.TimeUnit;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.EventLoop;
import io.netty.channel.SimpleChannelInboundHandler;


public class ClientHandler extends SimpleChannelInboundHandler<String> {
    private NettyClient nettyClient = new NettyClient();

    //public static Context signContext = null;

    //接收消息
    protected void channelRead0(ChannelHandlerContext ctx, String smsg) throws Exception {


        Log.d("channelRead0", "channelRead0: ---------------channelRead0 have received : " + smsg + "  ");

        if (smsg.equals("closeClient")){
            NettyClient.closeLink();
            return;
        }

        //获取消息对象
        Result result = JSON.parseObject(smsg, Result.class);
        if (result == null) {
            Log.e("channelRead0: ", "Result json解析失败！");
            return;
        }

        TbMsg tbMsg = ((JSONObject) result.getMessage()).toJavaObject(TbMsg.class);

        Log.d("result-------", "result.getMessage():" + result.getMessage());



        // TbMsg tbMsg = JSON.parseObject(params, TbMsg.class);
        if (tbMsg == null) {
            Log.e("channelRead0: ", "tbMsg json解析失败！");
            return;
        }

        //获取参数
        String t = tbMsg.getT();
        String api = tbMsg.getApi();
        String data = tbMsg.getData();

        //调用算法
        String sign = TBXsign.hookTBXsign(t, api, data);


        tbMsg.setSid(TBXsign.hashMap.get("sid"));
        tbMsg.setUid(TBXsign.hashMap.get("uid"));
        tbMsg.setXsign(sign);

        result.setCode(0);
        result.setMessage(tbMsg);


        String remsg = JSON.toJSONString(result);
//            Intent intent = new Intent("getXsign");
//            // 广播带参数
//            intent.putExtra("t", t);
//            intent.putExtra("api", api);
//            intent.putExtra("data", data);
//
//           signContext.sendBroadcast(intent);

        // sign =  CustomService.getClient().getTBXsign(t,api,data);
        //    getTBXsign(t,api,data);


        ctx.channel().writeAndFlush(remsg);
    }


    //有异常 关闭
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        super.exceptionCaught(ctx, cause);
        ctx.close();
    }

    //服务端突然挂了
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {


        Log.e("channelInactive: " ,"掉线了..." );
        //使用过程中断线重连
        final EventLoop eventLoop = ctx.channel().eventLoop();
        eventLoop.schedule(new Runnable() {
            @Override
            public void run() {
                nettyClient.connect(NettyClient.HOST, NettyClient.PORT);
            }
        }, 1L, TimeUnit.SECONDS);
        super.channelInactive(ctx);
    }

}
