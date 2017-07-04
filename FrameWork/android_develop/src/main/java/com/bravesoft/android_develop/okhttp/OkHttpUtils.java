package com.bravesoft.android_develop.okhttp;

import android.os.Looper;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;


/**
 * Class are created, On 2017/07/04.
 */

public class OkHttpUtils {
    private static OkHttpUtils mInstance;
    private static List<Param> params = new ArrayList<>();
    private static Map<String, String> headers = new Hashtable<>();
    private static String requestWay;
    private static String url;

    private synchronized static OkHttpUtils getInstance(String way) {
        //call in the main thread
        if (!(Looper.getMainLooper().getThread() == Thread.currentThread())) {
            throw new RuntimeException("not in child thread");
        }
        if (mInstance == null) {
            mInstance = new OkHttpUtils();
        }
        requestWay = way;
        url = "";
        params.clear();
        headers.clear();
        return mInstance;
    }


    public static OkHttpUtils get() {
        return getInstance("get");
    }

    public static OkHttpUtils post() {
        return getInstance("post");
    }

    public static OkHttpUtils put() {
        return getInstance("put");
    }

    public static OkHttpUtils postImage() {
        return getInstance("postImage");
    }

    public static OkHttpUtils delete() {
        return getInstance("delete");
    }

    public OkHttpUtils url(String url) {
        OkHttpUtils.url = url;
        return mInstance;
    }

    public OkHttpUtils addParms(String key, String value) {
        params.add(new Param(key, value));
        return mInstance;
    }

    public OkHttpUtils addHeads(String key, String value) {
        headers.put(key, value);
        return mInstance;
    }

    public OkHttpExecute build() {
        return new OkHttpExecute(url, requestWay, params, headers);
    }

    class Param {
        String key;
        String value;

        Param(String key, String value) {
            this.key = key;
            this.value = value;
        }
    }


}
