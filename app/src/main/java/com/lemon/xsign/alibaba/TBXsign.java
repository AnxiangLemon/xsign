package com.lemon.xsign.alibaba;

import android.content.Context;
import android.os.RemoteException;
import android.util.Log;


import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedHelpers;

import static de.robv.android.xposed.XposedBridge.log;

/**
 * Created by AnxiangLemon on 2019/2/16.
 */

public class TBXsign {

    public static ClassLoader classLoader = null;

    public static Context context = null;

    //public static String  msid = null;

    public static final String APPKEY = "21549244";

    public static HashMap<String, String> hashMap = new HashMap<String, String>() {
        {
            put("v", "1.0");
            put("deviceId", "ArBzYdNY2eQYDUn7n6ruHllA3laoC1HikcAgh5Vydvxp");
            put("appKey", "21549244");
            put("utdid", "XFr7yPsl5dwDAHzMrrSmqxqh");
            put("x-features", "27");
            put("ttid", "600000@moon_android_6.2.0");
            put("sid", "");
            put("uid", "2144187778");
            //  put("t","1549960346");
//            put("api", "mtop.alimama.moon.provider.detail.orders.get");
//            put("data", "{\"tbTradeParentId\":\"8888\"}");
        }
    };


    /**
     *
     * @param t  时间戳
     * @param api api
     * @param data 提交参数
     * @return  xsign
     */
    public static String hookTBXsign(String t,String api,String data) {

        //获取管理类
        Class<?> securityGuardManagerClass = XposedHelpers.findClassIfExists("com.alibaba.wireless.security.open.SecurityGuardManager", classLoader);
        Class<?> securityGuardParamContextClass = XposedHelpers.findClassIfExists("com.alibaba.wireless.security.open.SecurityGuardParamContext", classLoader);


        Log.d("hookXsign", "hook "+securityGuardManagerClass+"---"+securityGuardParamContextClass+"--Clossloader: "+classLoader);

        if (securityGuardManagerClass==null || securityGuardParamContextClass==null){
            return null;
        }

        String xsign = null;


        try {
            Object sgMgr = XposedHelpers.callStaticMethod(securityGuardManagerClass, "getInstance", context);
            Object iSecureSignatureComponent = XposedHelpers.callMethod(sgMgr, "getSecureSignatureComp");

           // log("sgMgr" + sgMgr);
           // log("iSecureSignatureComponent" + iSecureSignatureComponent);

            //构造参数
            hashMap.put("t", t);
            hashMap.put("api", api);
            hashMap.put("data", data);


            //构造参数
            Object securityGuardParamContext = securityGuardParamContextClass.newInstance();
            Map convertInnerBaseStrMap = convertInnerBaseStrMap(hashMap, APPKEY);
            XposedHelpers.setObjectField(securityGuardParamContext, "appKey", APPKEY);
            XposedHelpers.setObjectField(securityGuardParamContext, "paramMap", convertInnerBaseStrMap);
            XposedHelpers.setIntField(securityGuardParamContext, "requestType", 7);

           // log(getKeyAndValue(securityGuardParamContext).toString());

            //执行方法
            xsign = (String) XposedHelpers.callMethod(iSecureSignatureComponent, "signRequest", securityGuardParamContext, null);
       //     log(xsign + "---" + hashMap.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }

        return xsign;
    }



    //构造map2
    public static Map<String, String> convertInnerBaseStrMap(Map<String, String> map, String str) {

        Map<String, String> map2 = map;
        if (map2 == null || map.size() < 1) {
            return null;
        }
        String str2 = (String) map2.get("utdid");
        String str3 = (String) map2.get("uid");
        String str4 = (String) map2.get("reqbiz-ext");
        String str5 = (String) map2.get("data");
        String str6 = (String) map2.get("t");
        String str7 = (String) map2.get("api");
        String str8 = (String) map2.get("v");
        String str9 = (String) map2.get("sid");
        String str10 = (String) map2.get("ttid");
        String str11 = (String) map2.get("deviceId");
        String str12 = (String) map2.get("lat");
        String str13 = (String) map2.get("lng");
        String str14 = (String) map2.get("extdata");
        String str15 = (String) map2.get("x-features");
        StringBuilder stringBuilder = new StringBuilder(64);
        stringBuilder.append(SecurityUtils.convertNull2Default(str2));
        stringBuilder.append("&");
        stringBuilder.append(SecurityUtils.convertNull2Default(str3));
        stringBuilder.append("&");
        stringBuilder.append(SecurityUtils.convertNull2Default(str4));
        stringBuilder.append("&");
        stringBuilder.append(str);
        stringBuilder.append("&");
        stringBuilder.append(SecurityUtils.getMd5(str5));
        stringBuilder.append("&");
        stringBuilder.append(str6);
        stringBuilder.append("&");
        stringBuilder.append(str7);
        stringBuilder.append("&");
        stringBuilder.append(str8);
        stringBuilder.append("&");
        stringBuilder.append(SecurityUtils.convertNull2Default(str9));
        stringBuilder.append("&");
        stringBuilder.append(SecurityUtils.convertNull2Default(str10));
        stringBuilder.append("&");
        stringBuilder.append(SecurityUtils.convertNull2Default(str11));
        stringBuilder.append("&");
        stringBuilder.append(SecurityUtils.convertNull2Default(str12));
        stringBuilder.append("&");
        stringBuilder.append(SecurityUtils.convertNull2Default(str13));
        stringBuilder.append("&");
        if (StringUtils.isNotBlank(str14)) {
            stringBuilder.append(str14);
            stringBuilder.append("&");
        }
        stringBuilder.append(str15);
        map2 = new HashMap(2);
        map2.put("INPUT", stringBuilder.toString());
        return map2;
    }


    //反射获取对象值
    public static Map<String, Object> getKeyAndValue(Object obj) {
        Map<String, Object> map = new HashMap<String, Object>();
        // 得到类对象
        Class userCla = (Class) obj.getClass();
        /* 得到类中的所有属性集合 */
        Field[] fs = userCla.getDeclaredFields();
        for (int i = 0; i < fs.length; i++) {
            Field f = fs[i];
            f.setAccessible(true); // 设置些属性是可以访问的
            Object val;
            try {
                val = f.get(obj);
                // 得到此属性的值
                map.put(f.getName(), val);// 设置键值
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        System.out.println(obj.toString() + "单个对象的所有键值==反射==" + map.toString());
        return map;
    }

}
