package com.bravesoft.android_develop.apputil;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

/**
 * Created by SCY on 2017/7/4 14:39.
 */

public class GoOutBrowser {
    /**
     * 外部浏览器打开链接
     */
    public static void startBrowser(Context context, String url) {
        Intent intent = new Intent();
        intent.setAction("android.intent.action.VIEW");
        Uri content_url = Uri.parse(url);
        intent.setData(content_url);
        context.startActivity(intent);
    }
}
