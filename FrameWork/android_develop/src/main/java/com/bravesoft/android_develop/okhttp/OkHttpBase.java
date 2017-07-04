package com.bravesoft.android_develop.okhttp;

import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;

import com.bravesoft.android_develop.apputil.Unicode;

import java.io.File;
import java.io.IOException;
import java.util.List;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * Class are created, On 2017/07/04.
 */

class OkHttpBase {
    private static OkHttpBase mInstance;
    private OkHttpClient mOkHttpClient;
    private Handler mDelivery;

    private OkHttpBase() {
        mOkHttpClient = new OkHttpClient();
        mDelivery = new Handler(Looper.getMainLooper());
    }

    synchronized static OkHttpBase getInstance() {
        if (mInstance == null) {
            mInstance = new OkHttpBase();
        }
        return mInstance;
    }

    void getRequest(String url, Headers header, final ResultCallback callback) {
        Request request;
        if (header.size() > 0) {
            request = new Request.Builder().url(url).headers(header).build();
        } else {
            request = new Request.Builder().url(url).build();
        }
        deliveryResult(callback, request);
    }


    void postRequest(String url, Headers header, final ResultCallback callback, List<OkHttpUtils.Param> params) {
        Request request = buildPostRequest(url, header, params);
        deliveryResult(callback, request);
    }


    void putRequest(String url, Headers header, final ResultCallback callback, List<OkHttpUtils.Param> params) {
        Request request = buildPutRequest(url, header, params);
        deliveryResult(callback, request);
    }


    void deleteRequest(String url, Headers header, final ResultCallback callback) {
        Request request = buildDeleteRequest(url, header);
        deliveryResult(callback, request);
    }


    private void deliveryResult(final ResultCallback callback, final Request request) {
        mOkHttpClient.newCall(request).enqueue(new Callback() {

            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                sendFailCallback(callback, e);
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                try {
                    ResponseBody body = response.body();
                    int statusCode = response.code();
                    Headers headers = response.headers();
                    String str = "";
                    if (body != null) {
                        str = body.string();
                    }
                    sendSuccessCallBack(callback, Unicode.decodeUnicode(str), headers, statusCode);
                } catch (Exception e) {
                    sendFailCallback(callback, e);
                }
            }
        });
    }

    private void sendFailCallback(final ResultCallback callback, final Exception e) {
        mDelivery.post(new Runnable() {
            @Override
            public void run() {
                if (callback != null) {
                    callback.onFailure(e);
                }
            }
        });
    }

    private void sendSuccessCallBack(final ResultCallback callback, final String body, final Headers headers, final int statusCode) {
        mDelivery.post(new Runnable() {
            @Override
            public void run() {
                if (callback != null) {
                    callback.onSuccess(body, headers, statusCode);
                }
            }
        });
    }

    private Request buildPostRequest(String url, Headers header, List<OkHttpUtils.Param> params) {
        FormBody.Builder builder = new FormBody.Builder();
        for (OkHttpUtils.Param param : params) {
            builder.add(param.key, param.value);
        }
        RequestBody requestBody = builder.build();
        if (header.size() > 0) {
            return new Request.Builder().url(url).post(requestBody).headers(header).build();
        } else {
            return new Request.Builder().url(url).post(requestBody).build();
        }
    }


    private Request buildPutRequest(String url, Headers header, List<OkHttpUtils.Param> params) {
        FormBody.Builder builder = new FormBody.Builder();
        for (OkHttpUtils.Param param : params) {
            builder.add(param.key, param.value);
        }
        RequestBody requestBody = builder.build();
        if (header.size() > 0) {
            return new Request.Builder().url(url).put(requestBody).headers(header).build();
        } else {
            return new Request.Builder().url(url).put(requestBody).build();
        }
    }


    private Request buildDeleteRequest(String url, Headers header) {
        FormBody.Builder builder = new FormBody.Builder();
        RequestBody requestBody = builder.build();
        if (header.size() > 0) {
            return new Request.Builder().url(url).delete(requestBody).headers(header).build();
        } else {
            return new Request.Builder().url(url).delete(requestBody).build();
        }
    }

    void postImageRequest(String url, ResultCallback callback, List<OkHttpUtils.Param> params) {
        Request request = buildPostImageRequest(url, params);
        deliveryResult(callback, request);
    }

    private Request buildPostImageRequest(String url, List<OkHttpUtils.Param> params) {
        if (params == null || params.size() == 0) {
            throw new RuntimeException("画像アップパスがnull");
        }
        MultipartBody.Builder builder = new MultipartBody.Builder().setType(MultipartBody.FORM);
        for (OkHttpUtils.Param param : params) {
            File file = new File(param.value);
            if (file.exists()) {
                builder.addFormDataPart(param.key, file.getName(),
                        RequestBody.create(MediaType.parse("image/*"), file));
            }
        }
        RequestBody requestBody = builder.build();
        return new Request.Builder().url(url).post(requestBody).build();
    }

}
