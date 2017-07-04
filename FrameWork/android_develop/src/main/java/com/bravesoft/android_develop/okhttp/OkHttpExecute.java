package com.bravesoft.android_develop.okhttp;

import java.util.List;
import java.util.Map;

import okhttp3.Headers;

/**
 * Class are created, On 2017/07/04.
 */

public class OkHttpExecute {
    private String url;
    private String requestWay;
    private List<OkHttpUtils.Param> params;
    private Map<String, String> headers;

    OkHttpExecute(String url, String requestWay, List<OkHttpUtils.Param> params, Map<String, String> headers) {
        this.url = url;
        this.requestWay = requestWay;
        this.params = params;
        this.headers = headers;
    }

    public void execute(ResultCallback callback) {
        switch (requestWay) {
            case "get":
                appendGet();
                OkHttpBase.getInstance().getRequest(url, Headers.of(headers), callback);
                break;
            case "post":
                OkHttpBase.getInstance().postRequest(url, Headers.of(headers), callback, params);
                break;
            case "put":
                OkHttpBase.getInstance().putRequest(url, Headers.of(headers), callback, params);
                break;
            case "delete":
                OkHttpBase.getInstance().deleteRequest(url, Headers.of(headers), callback);
                break;
            case "postImage":
                OkHttpBase.getInstance().postImageRequest(url, callback, params);
                break;
        }
    }

    private void appendGet() {
        if (params.size() != 0) {
            StringBuffer buffer = new StringBuffer(url);
            buffer.append("?");
            for (OkHttpUtils.Param param : params) {
                buffer.append(param.key)
                        .append("=")
                        .append(param.value)
                        .append("&");
            }
            buffer.substring(0, buffer.length() - 1);
            url = String.valueOf(buffer);
        }
    }

}
