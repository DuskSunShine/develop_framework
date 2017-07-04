package com.bravesoft.android_develop.okhttp;

import okhttp3.Headers;

/**
 * Class are created, On 2017/07/04.
 */

public abstract class ResultCallback {

    /**
     * コールバック成功
     */
    public abstract void onSuccess(String body, Headers headers, int statusCode);

    /**
     * コールバック失敗
     */
    public abstract void onFailure(Exception e);

}
