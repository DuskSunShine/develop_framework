package com.bravesoft.android_develop.apputil.netutil;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;

import com.bravesoft.android_develop.BuildConfig;
import com.bravesoft.android_develop.apputil.AppUtil;

/**
 * Created by SCY on 2017/7/3 17:33.
 */

public class NetBroadCastReceiver extends BroadcastReceiver {

    public static final IntentFilter NETWORK_INTENT_FILTER = new IntentFilter(
            ConnectivityManager.CONNECTIVITY_ACTION);
    private OnNetChangeListener onNetChangeListener;
    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(ConnectivityManager.CONNECTIVITY_ACTION)) {
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
        }
    }
    public interface OnNetChangeListener{
        void onNetMobile(int netCode);
        void onNetWifi(int netCode);
        void onNoNetWork(int netCode);
    }
    public void setOnNetChangeListener(OnNetChangeListener onNetChangeListener){
        this.onNetChangeListener=onNetChangeListener;
    }
}
