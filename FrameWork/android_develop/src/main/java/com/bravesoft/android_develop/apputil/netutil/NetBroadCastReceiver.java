package com.bravesoft.android_develop.apputil.netutil;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;

/**
 * Created by SCY on 2017/7/3 17:33.
 */

public class NetBroadCastReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(ConnectivityManager.CONNECTIVITY_ACTION)) {
            Intent intent1 = new Intent(context, NetService.class);
            context.startService(intent1);//这种服务，开启后APP结束任然在后台
        }
    }
}
