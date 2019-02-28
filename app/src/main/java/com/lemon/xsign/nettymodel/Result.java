package com.lemon.xsign.nettymodel;

import java.io.Serializable;

/**
 * @ Author     : AnxiangLemon
 * @ Date       : Created in 23:00 2019/2/20
 * @ Description: this is class
 */

public class Result implements Serializable {
    private int code;
    private Object message;
    private String uniId;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public Object getMessage() {
        return message;
    }

    public void setMessage(Object message) {
        this.message = message;
    }

    public String getUniId() {
        return uniId;
    }

    public void setUniId(String uniId) {
        this.uniId = uniId;
    }

    @Override
    public String toString() {
        return "Result{" +
                "code=" + code +
                ", message=" + message +
                ", uniId='" + uniId + '\'' +
                '}';
    }
}
