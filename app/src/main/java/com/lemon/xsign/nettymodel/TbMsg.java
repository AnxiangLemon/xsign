package com.lemon.xsign.nettymodel;


/**
 * @ Author     : AnxiangLemon
 * @ Date       : Created in 17:54 2019/2/23
 * @ Description: this is class
 */

public class TbMsg {

    private String api;
    private String t;
    private String data;
    //上面是web端发过来 下面的是Android端返回去
    private String sid;
    private String uid;
    private String xsign;

    @Override
    public String toString() {
        return "TbMsg{" +
                "api='" + api + '\'' +
                ", t='" + t + '\'' +
                ", data='" + data + '\'' +
                ", sid='" + sid + '\'' +
                ", uid='" + uid + '\'' +
                ", xsign='" + xsign + '\'' +
                '}';
    }

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getXsign() {
        return xsign;
    }

    public void setXsign(String xsign) {
        this.xsign = xsign;
    }

    public String getApi() {
        return api;
    }

    public void setApi(String api) {
        this.api = api;
    }

    public String getT() {
        return t;
    }

    public void setT(String t) {
        this.t = t;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
