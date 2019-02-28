package com.lemon.xsign;

/**
 * Created by AnxiangLemon on 2019/2/9.
 */


import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

import android.content.SharedPreferences;
import android.util.Log;

import com.lemon.xsign.alibaba.TBXsign;
import com.lemon.xsign.client.NettyClient;

import java.util.Map;

import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

import static de.robv.android.xposed.XposedBridge.log;

public class MyXposedInit implements IXposedHookLoadPackage {

    //广播处理
/*    class InternetDynamicBroadCastReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            if ("getXsign".equals(intent.getAction())) {
            //    TBXsign.hookTBXsign(TBXsign.classLoader);
                String t = System.currentTimeMillis() / 1000 + "";
                String api = "mtop.alimama.moon.provider.detail.orders.get";
                String data = "{\"tbTradeParentId\":\"8888\"}";
              String sign =   TBXsign.hookTBXsign(t,api,data);
                Log.e("广播事件", "onReceive: "+sign);
            }
        }
    }

    private InternetDynamicBroadCastReceiver mReceiver;
*/

    public void handleLoadPackage(final XC_LoadPackage.LoadPackageParam lpparam) throws Throwable {


//            if (!lpparam.isFirstApplication) {
//                //一个进程，只应该加载一次
//                return;
//            }

        if (lpparam.processName.equals("com.alimama.moon")) {
            /// return;
            if (TBXsign.classLoader == null) {
                TBXsign.classLoader = lpparam.classLoader;
                hookxsign(TBXsign.classLoader);
            }
        }


    }

    private void hookxsign(ClassLoader classLoader) {
        final Class<?> nsclass = XposedHelpers.findClassIfExists("com.taobao.login4android.session.SessionManager", classLoader);
        if (nsclass == null) return;

        Class<?> sclass = XposedHelpers.findClassIfExists("mtopsdk.security.InnerSignImpl", classLoader);
        if (sclass == null) return;

        Class<?> mtopConfigClass = XposedHelpers.findClassIfExists("mtopsdk.mtop.global.MtopConfig", classLoader);
        XposedHelpers.findAndHookMethod(sclass, "init", mtopConfigClass, new XC_MethodHook() {
            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                Object object = param.args[0];
                TBXsign.context = (Context) XposedHelpers.getObjectField(object, "context");

                log("Context：" + TBXsign.context.toString());

                    /*
                    *获取用户信息
                    * */
                // SharedPreferences storage = TBXsign.context.getSharedPreferences("userinfo", Context.MODE_MULTI_PROCESS);
                //String strsid = storage.getString("sid", "");
                Object sessionManager = XposedHelpers.callStaticMethod(nsclass, "getInstance", TBXsign.context);
                String msid = (String) XposedHelpers.getObjectField(sessionManager, "mSid");
                String muid = (String) XposedHelpers.getObjectField(sessionManager, "mUserId");

                //置参数
                TBXsign.hashMap.put("sid", msid);
                TBXsign.hashMap.put("uid", muid);

                log("sid:" + msid+"--mUserId:"+muid);

                //注册广播
//                    IntentFilter filter = new IntentFilter();
//                    filter.addAction("getXsign");
//                    mReceiver = new InternetDynamicBroadCastReceiver();
//                    TBXsign.context.registerReceiver(new InternetDynamicBroadCastReceiver(), filter);


                new NettyClient().connect(NettyClient.HOST,NettyClient.PORT);

                //   TBXsign.hookTBXsign(TBXsign.classLoader);
            }
        });

    }


}
