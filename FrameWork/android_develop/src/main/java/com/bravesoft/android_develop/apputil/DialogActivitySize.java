package com.bravesoft.android_develop.apputil;

import android.support.v7.app.AppCompatActivity;
import android.view.Display;
import android.view.WindowManager;

/**
 * Created by SCY on 2017/7/4 15:00.
 */

public class DialogActivitySize {

    /**
     *
     * @param activity
     * @param h 高度 h
     * @param w 宽度 w
     */
    public static void dialogActivitySize(AppCompatActivity activity, double h, double w){
        WindowManager m = activity.getWindowManager();
        Display d = m.getDefaultDisplay();  //スクリーンの高さと広さを取得
        WindowManager.LayoutParams p =activity.getWindow().getAttributes();  //ダイアログのバラメータを取得
        p.height = (int) (d.getHeight() * h);   //高さは0.65
        p.width = (int) (d.getWidth() * w);    //広さ0.7
        activity.getWindow().setAttributes(p);     //設置が有効になる
    }
}
