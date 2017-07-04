package com.bravesoft.android_develop.apputil.netutil;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.bravesoft.android_develop.BuildConfig;
import com.bravesoft.android_develop.apputil.AppUtil;

/**
 * Created by SCY on 2017/7/3 17:01.
 */

public class NetService extends Service {

    private Context context;
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        context=getApplicationContext();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (AppUtil.isAppAlive(context, BuildConfig.APPLICATION_ID)){
            if (NetUtils.isNetConnect(context)){
                if (NetUtils.getNetWorkState(context)==0){
                    //移动网络
                }else if (NetUtils.getNetWorkState(context)==1){
                    //WIFI
                }
            }else {
                //无网络
            }
        }
        return START_NOT_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        stopSelf();
    }
}
