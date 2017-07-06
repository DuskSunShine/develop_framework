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
    private OnNetChangeListener onNetChangeListener;
    private static Context serviceContext;
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        context=getApplicationContext();
        serviceContext=NetService.this;
    }

    public static Context getServiceContext() {
        return serviceContext;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (AppUtil.isAppAlive(context, BuildConfig.APPLICATION_ID)){
            if (NetUtils.isNetConnect(context)){
                if (NetUtils.getNetWorkState(context)==0){
                    //移动网络
                    onNetChangeListener.onNetMobile(NetUtils.getNetWorkState(context));
                }else if (NetUtils.getNetWorkState(context)==1){
                    //WIFI
                    onNetChangeListener.onNetWifi(NetUtils.getNetWorkState(context));
                }
            }else {
                //无网络
                onNetChangeListener.onNoNetWork(NetUtils.getNetWorkState(context));
            }
        }
        return START_NOT_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        stopSelf();
        serviceContext=null;
    }
    public interface OnNetChangeListener{
        void onNetMobile(int netCode);
        void onNetWifi(int netCode);
        void onNoNetWork(int netCode);
    }
    
    public void SetOnNetChangeListener(OnNetChangeListener onNetChangeListener){
        this.onNetChangeListener=onNetChangeListener;
    }
}
