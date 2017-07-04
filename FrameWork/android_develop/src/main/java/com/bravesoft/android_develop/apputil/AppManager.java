package com.bravesoft.android_develop.apputil;

import android.app.ActivityManager;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**app 管理类
 * Created by SCY on 2017/7/4 13:29.
 */

public class AppManager {

    /**
     * Activity存储堆栈
     */
    private static Stack<AppCompatActivity> mActivityStack;

    /**
     * AppManager单例object
     */
    private static AppManager mAppManager;

    private AppManager() {
        if (mActivityStack == null) {
            mActivityStack = new Stack<AppCompatActivity>();
        }
    }

    /**
     * 单例
     */
    public static AppManager getInstance() {
        if (mAppManager == null) {
            synchronized (AppManager.class){
                if (mAppManager == null) {
                    mAppManager = new AppManager();
                }
            }
        }
        return mAppManager;
    }

    /**
     * 将activity加入栈中
     */
    public void addActivity(AppCompatActivity activity) {
        if (mActivityStack == null) {
            mActivityStack = new Stack<AppCompatActivity>();
        }
        mActivityStack.add(activity);
    }

    /**
     * 栈顶activity
     */
    public AppCompatActivity getTopActivity() {
        AppCompatActivity activity = mActivityStack.lastElement();
        return activity;
    }

    /**
     * 结束栈里最后的Activity
     */
    public void killTopActivity() {
        AppCompatActivity activity = mActivityStack.lastElement();
        killActivity(activity);
    }

    /**
     * 指定Activity结束
     */
    public void killActivity(AppCompatActivity activity) {
        if (activity != null) {
            mActivityStack.remove(activity);
            activity.finish();
        }
    }

    /**
     * 指定Activity结束
     */
    public synchronized void killActivity(Class<?>... mClass) {

        if (mActivityStack == null || mActivityStack.isEmpty())
            return;

        List<AppCompatActivity> activities = new ArrayList<>();

        for (Class cls : mClass) {
            for (AppCompatActivity activity : mActivityStack) {
                if (activity.getClass().equals(cls)) {
                    activities.add(activity);
                }
            }
        }

        for (AppCompatActivity activity : activities) {
            killActivity(activity);
        }

    }

    /**
     * 结束栈里 所有activity
     */
    public void killAllActivity() {
        if (mActivityStack == null || mActivityStack.isEmpty()) {
            return;
        }
        for (int i = 0, size = mActivityStack.size(); i < size; i++) {
            if (null != mActivityStack.get(i)) {
                mActivityStack.get(i).finish();
            }
        }
        mActivityStack.clear();
    }

    /**
     * 结束当前activity以外的其他activity
     *
     * @param activity
     */
    public void killOthersActivity(AppCompatActivity activity) {
        if (activity == null) {
            return;
        }
        for (int i = 0, size = mActivityStack.size(); i < size; i++) {
            if (null != mActivityStack.get(i) && activity != mActivityStack.get(i)) {
                mActivityStack.get(i).finish();
            }
        }
        mActivityStack.clear();
        mActivityStack.add(activity);
    }

    /**
     * Activity存在判断
     *
     * @param className
     * @return
     */
    public boolean existActivity(String className) {
        AppCompatActivity activity =  getActivityByName(className);
        if (activity != null && !activity.isFinishing()) {
            return true;
        }
        return false;
    }

    /**
     * 通过类名获取到activity
     *
     * @param className
     * @return
     */
    public AppCompatActivity getActivityByName(String className) {
        AppCompatActivity activity = null;
        for (int i = 0, size = mActivityStack.size(); i < size; i++) {
            if (null != mActivityStack.get(i)) {
                if (mActivityStack.get(i).getClass().getName().equals(className)) {
                    activity = mActivityStack.get(i);
                }
            }
        }
        return activity;
    }

    /**
     * Activity结束
     *
     * @param activity
     */
    public void finishActivity(AppCompatActivity activity) {

        int pos = -1;
        if (activity != null && mActivityStack != null) {
            for (int i = 0, size = mActivityStack.size(); i < size; i++) {
                if (null != mActivityStack.get(i)) {
                    if (activity == mActivityStack.get(i)) {
                        pos = i;
                        activity.finish();
                    }
                }
            }
            if (pos != -1) {
                mActivityStack.remove(pos);
            }
        }
    }

    /**
     * activity移除
     *
     * @param activity
     */
    public void remove(AppCompatActivity activity) {
        if (activity != null && mActivityStack != null) {
            mActivityStack.remove(activity);
        }
    }
    public void removeClass(Class<?> activity) {
        if (activity != null && mActivityStack != null) {
            mActivityStack.remove(activity);
        }
    }
    /**
     * 退出APP 需要权限
     */
    public void AppExit(Context context) {
        try {
            killAllActivity();
            ActivityManager activityMgr = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
            activityMgr.restartPackage(context.getPackageName());
            System.exit(0);
        } catch (Exception e) {

        }
    }
}
