package com.bravesoft.android_develop.apputil;

import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.graphics.Bitmap;
import android.os.Environment;
import android.provider.Settings;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

/**
 * Created by SCY on 2017/7/4 15:27.
 */

public class AppUtil {

    // 图片保存sdcard 的路径
    public static final String PHOTOS_SDCARD_PATH = Environment
            .getExternalStorageDirectory().getAbsolutePath()
            + File.separator +"user/photos/";
    /**
     * 取得语言的版本
     * @param context
     * @return
     */
    public static boolean isZh(Context context) {
        Locale locale = context.getResources().getConfiguration().locale;
        String language = locale.getLanguage();
        if (language.endsWith("zh"))
            return true;
        else
            return false;

    }

    public static boolean isEn(Context context) {
        Locale locale = context.getResources().getConfiguration().locale;
        String language = locale.getLanguage();
        if (language.endsWith("en"))
            return true;
        else
            return false;

    }

    public static boolean isJp(Context context) {
        Locale locale = context.getResources().getConfiguration().locale;
        String language = locale.getLanguage();
        if (language.endsWith("ja"))
            return true;
        else
            return false;

    }

    /**
     * 手机id
     *
     * @return
     */
    public static String getAndroidId(Context context) {
        String androidId = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
        return androidId;
    }

    /**
     * app版本名称
     *
     * @param context
     * @return versionName
     */
    public static String getAppVersionName(Context context) {
        try {
            PackageInfo packageInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            if (packageInfo != null) {
                return packageInfo.versionName;
            }
        } catch (Exception e) {

        }
        return "1.0.0";
    }

    /**
     * app版本号
     *
     * @param context
     * @return versionCode
     */
    public static int getAppVersionCode(Context context) {
        try {
            PackageInfo packageInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            if (packageInfo != null) {
                return packageInfo.versionCode;
            }
        } catch (Exception e) {

        }
        return 1;
    }

    /**
     * app 版本比较
     * @param context
     * @param oldVersion 获取到的旧版本号，和本次APP版本号不同
     * @return
     */
    public static boolean CompareVersion(Context context,String oldVersion) {
        String versionCode = getAppVersionName(context);
        String[] split = oldVersion.split("\\.");
        String[] split1 = versionCode.split("\\.");
        if (Integer.parseInt(split[0]) > Integer.parseInt(split1[0])) {
            return true;
        } else if (Integer.parseInt(split[0]) < Integer.parseInt(split1[0])) {
            return false;
        } else {
            if (Integer.parseInt(split[1]) > Integer.parseInt(split1[1])) {
                return true;
            } else if (Integer.parseInt(split[1]) < Integer.parseInt(split1[1])) {
                return false;
            } else {
                if (Integer.parseInt(split[2]) > Integer.parseInt(split1[2])) {
                    return true;
                } else if (Integer.parseInt(split[2]) < Integer.parseInt(split1[2])) {
                    return false;
                }
            }
        }
        return false;
    }

    /**
     * app 是否在前台
     * @param context
     * @param packageName
     * @return
     */
    public static boolean isAppAlive(Context context,String packageName){
        ActivityManager activityManager=
                (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> processInfos
                =activityManager.getRunningAppProcesses();
        if (android.os.Build.VERSION.SDK_INT <= android.os.Build.VERSION_CODES.LOLLIPOP){
            ComponentName cn = activityManager.getRunningTasks(1).get(0).topActivity;
            String currentPackageName = cn.getPackageName();
            if(currentPackageName != null && currentPackageName.equals(packageName)){
                return true;
            }else {
                return false;
            }
        }else {
            for (int i = 0; i < processInfos.size(); i++) {
                if (processInfos.get(i).processName.equals(packageName)){
                    if (processInfos.get(i).importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
                        return true;
                    }else {
                        return false;
                    }
                }
            }
        }

        return  false;
    }

    /**
     * 保存图片到本地(JPG)
     *
     * @param bm
     *            保存的图片
     * @return 图片路径
     */
    public static String saveToLocal(Bitmap bm) {

        if (!Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED)) {
            return null;
        }
        FileOutputStream fileOutputStream = null;
        File file = new File(PHOTOS_SDCARD_PATH);
        if (!file.exists()) {
            file.mkdirs();
        }
        String fileName = UUID.randomUUID().toString() + ".jpg";
        String filePath = PHOTOS_SDCARD_PATH + fileName;
        File f = new File(filePath);
        if (!f.exists()) {
            try {
                f.createNewFile();
                fileOutputStream = new FileOutputStream(filePath);
                bm.compress(Bitmap.CompressFormat.JPEG, 100, fileOutputStream);
            } catch (IOException e) {
                return null;
            } finally {
                try {
                    fileOutputStream.flush();
                    fileOutputStream.close();
                } catch (IOException e) {
                    return null;
                }
            }
        }
        return filePath;
    }

    /**
     * 将dp转换为px
     *
     * @param context
     * @param dipValue
     * @return
     */
    public static int dip2px(Context context, float dipValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }
    /**
     * 将px转换为dp
     *
     * @param ctx
     * @param pxValue
     * @return
     */
    public static int px2dip(Context ctx,float pxValue) {
        final float scale = ctx.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }
}
