package com.bravesoft.android_develop.base;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import com.bravesoft.android_develop.apputil.AppManager;
import com.bravesoft.android_develop.apputil.netutil.NetBroadCastReceiver;

/**
 * Created by SCY on 2017/7/4 12:00.
 */

public abstract class BaseActivity extends AppCompatActivity implements View.OnClickListener {
    public NetBroadCastReceiver netBroadCastReceiver;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(getContentViewId());

        AppManager.getInstance().addActivity(this);
        netBroadCastReceiver = new NetBroadCastReceiver();
        registerReceiver(netBroadCastReceiver,
                NetBroadCastReceiver.NETWORK_INTENT_FILTER);
        // setTranslucentStatus();
        beforeInitView();
        initView();
        initData();

    }

    public abstract int getContentViewId();//layoutId设置

    public abstract void beforeInitView();//View初始化前的操作

    public abstract void initView();//View初期化

    public abstract void initData();//数据初始化


    /**
     * 设置屏幕竖屏，在设置布局之前调用
     */
    public void setPortrait(){
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }
    /**
     * actionBar隐藏
     */
    public void hideActionBar(){
        if (getSupportActionBar() != null)
            getSupportActionBar().hide();
    }
    /**
     * Android 4.4以上设置状态栏透明
     */
    public void setTranslucentStatus() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window window = getWindow();
            WindowManager.LayoutParams layoutParams = window.getAttributes();
            layoutParams.flags |= WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
            window.setAttributes(layoutParams);
        }
    }

    /**
     * 去掉findviewbyid的强制转换
     *
     * @param id
     * @param <T>
     * @return
     */
    public <T extends View> T findViewByIdNoCast(int id) {
        return (T) findViewById(id);
    }

    /**
     * 通过控件id设置点击事件
     * @param ids
     */
    public void setOnClick(int... ids) {
        for (int id : ids)
            findViewById(id).setOnClickListener(this);

    }

    /**
     * 通过控件设置点击
     * @param views
     */
    public void setOnClick(View... views) {
        for (View view : views)
            view.setOnClickListener(this);

    }

    /**
     * 通过控件设置返回键
     * @param view
     */
    protected void setToBack(View view){
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }
    /**
     * 通过控件id设置返回键
     */
    protected void setToBack(int id){
        findViewById(id).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

    /**
     * 隐藏软键盘
     */
    protected void dismissSoftKeyboard(Activity activity) {
        try {
            InputMethodManager inputMethodManage = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            inputMethodManage.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(),
                    InputMethodManager.HIDE_NOT_ALWAYS);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 显示软键盘
     *
     * @param view
     */
    protected void showKeyboard(View view) {
        try {
            InputMethodManager inputMethodManage = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            inputMethodManage.showSoftInput(view, InputMethodManager.SHOW_FORCED);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(netBroadCastReceiver);
        AppManager.getInstance().remove(this);
    }
}
