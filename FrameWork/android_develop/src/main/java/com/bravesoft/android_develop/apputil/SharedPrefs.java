package com.bravesoft.android_develop.apputil;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.StreamCorruptedException;

/** SharedPreferences 工具类，<br>注意保存的实体类需要序列化<br/>
 * <br>序列化后，显示的生成序列化ID
 * Created by SCY on 2017/7/4 13:47.
 */

public class SharedPrefs {
    public static final String USERS="users";
    public static final String OTHERS="others";

    public static void putObject(Context context, String key, Object obj) {
        try {
            // object保存
            SharedPreferences.Editor data = context.getSharedPreferences(USERS, Context.MODE_PRIVATE).edit();
            //内存流
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            ObjectOutputStream os = new ObjectOutputStream(bos);
            //object序列化后的byte
            os.writeObject(obj);
            //序列化后16进制保存
            String bytesToHexString = bytesToHexString(bos.toByteArray());
            //16进制键值对保存
            data.putString(key, bytesToHexString);
            data.commit();
            os.close();
            bos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * desc:保存的object获取
     *
     * @param context
     * @param key
     * @return modified:
     */
    public static Object getObject(Context context, String key) {
        try {
            SharedPreferences data = context.getSharedPreferences(USERS, Context.MODE_PRIVATE);
            if (data.contains(key)) {
                String string = data.getString(key, "");
                if (TextUtils.isEmpty(string)) {
                    return null;
                } else {
                    //16进法のデータを配列に転換
                    byte[] stringToBytes = StringToBytes(string);
                    ByteArrayInputStream bis = new ByteArrayInputStream(stringToBytes);
                    ObjectInputStream is = new ObjectInputStream(bis);
                    //反序列化に取得したobjectに戻る
                    Object readObject = is.readObject();

                    is.close();
                    bis.close();
                    return readObject;
                }
            }
        } catch (StreamCorruptedException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        //異常レスポンスnull
        return null;

    }

    /**
     * 存储String类型数据
     * @param context
     * @param key
     * @param values
     */
    public static synchronized boolean putString(Context context,String key,String values){
        SharedPreferences sp = context.getSharedPreferences(OTHERS,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(key,values);
        return editor.commit();
    }

    /**
     * boolean 类型存储
     * @param context
     * @param key
     * @param value
     * @return
     */
    public static synchronized boolean putBoolean(Context context, String key, boolean value) {
        SharedPreferences.Editor editor = context.getSharedPreferences(OTHERS,
                Context.MODE_PRIVATE).edit();
        editor.putBoolean(key, value);
        return editor.commit();

    }

    /**
     * long 型数据存储
     * @param context
     * @param key
     * @param value
     * @return
     */
    public static synchronized boolean putLong(Context context, String key,
                                               long value) {
        SharedPreferences.Editor editor = context.getSharedPreferences(OTHERS,
                Context.MODE_PRIVATE).edit();
        editor.putLong(key, value);
        return editor.commit();
    }

    /**
     * int 型数据存储
     * @param context
     * @param key
     * @param value
     * @return
     */
    public static synchronized boolean putInt(Context context, String key,
                                              int value) {

        SharedPreferences.Editor editor = context.getSharedPreferences(OTHERS,
                Context.MODE_PRIVATE).edit();
        editor.putInt(key, value);
        return editor.commit();
    }

    /**
     * float 浮点型数据存储
     * @param context
     * @param key
     * @param value
     * @return
     */
    public static synchronized boolean putFloat(Context context, String key,
                                                float value) {
        SharedPreferences.Editor editor = context.getSharedPreferences(OTHERS,
                Context.MODE_PRIVATE).edit();
        editor.putFloat(key, value);
        return editor.commit();

    }
    /***
     * string数据读取 默认数据为："null"
     * @param context
     * @param key
     * @return
     */
    public static String getString(Context context,String key){
        SharedPreferences sp = context.getSharedPreferences(OTHERS,Context.MODE_PRIVATE);
        String result = sp.getString(key,"null");
        return result;
    }

    /**
     * 读取boolean 数据，默认false
     * @param context
     * @param key
     * @return
     */
    public static synchronized boolean getBoolean(Context context, String key) {
        return context.getSharedPreferences(OTHERS,Context.MODE_PRIVATE)
                .getBoolean(key, false);
    }

    /**
     * 获取int数据，默认值为-1
     * @param context
     * @param key
     * @return
     */
    public static synchronized int getInt(Context context, String key) {
        Integer result = context.getSharedPreferences(OTHERS,Context.MODE_PRIVATE)
                .getInt(key, -1);
        return result;
    }

    /**
     * 获取 float类型数据 默认为0.0f
     * @param context
     * @param key
     * @return
     */
    public static synchronized float getFloat(Context context, String key) {
        return context.getSharedPreferences(OTHERS,Context.MODE_PRIVATE)
                .getFloat(key, 0.0f);
    }

    /**
     * long型数据获取，默认0L
     * @param context
     * @param key
     * @return
     */
    public static synchronized long getLong(Context context, String key) {
        return context.getSharedPreferences(OTHERS,Context.MODE_PRIVATE)
                .getLong(key, 0L);
    }

    /**
     * 根据键值移除基本数据类型数据
     * @param context
     * @param key
     */
    public static synchronized void clearValueByKey(Context context, String key) {
        SharedPreferences.Editor editor = context.
                getSharedPreferences(OTHERS,Context.MODE_PRIVATE).edit();
        editor.remove(key);
        editor.apply();
    }

    /**
     * 根据键值移除object 数据
     * @param context
     * @param key
     */
    public static synchronized void clearObjByKey(Context context, String key) {
        SharedPreferences.Editor editor = context.
                getSharedPreferences(USERS,Context.MODE_PRIVATE).edit();
        editor.remove(key);
        editor.apply();
    }

    /**
     * 清除sp中object数据
     * @param context
     */
    public static void clearObj(Context context) {
        SharedPreferences s = context.getSharedPreferences(USERS, Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = s.edit();
        s.getAll().clear();
        edit.clear().apply();
    }

    /**
     * 清除基本数据类型数据
     * @param context
     */
    public static void clearOther(Context context) {
        SharedPreferences ss = context.getSharedPreferences(OTHERS, Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = ss.edit();
        ss.getAll().clear();
        edit.clear().apply();
    }

    /**
     * 清除sp中所有数据
     * @param context
     */
    public static void clearAll(Context context) {
        clearObj(context);
        clearOther(context);
    }
    /**
     * desc:16进制转换
     *
     * @param bArray
     * @return modified:
     */
    private static String bytesToHexString(byte[] bArray) {
        if (bArray == null) {
            return null;
        }
        if (bArray.length == 0) {
            return "";
        }
        StringBuffer sb = new StringBuffer(bArray.length);
        String sTemp;
        for (int i = 0; i < bArray.length; i++) {
            sTemp = Integer.toHexString(0xFF & bArray[i]);
            if (sTemp.length() < 2)
                sb.append(0);
            sb.append(sTemp.toUpperCase());
        }
        return sb.toString();
    }
    /**
     * desc:16进制转换
     * @param data
     * @return
     */

    private static byte[] StringToBytes(String data) {
        String hexString = data.toUpperCase().trim();
        if (hexString.length() % 2 != 0) {
            return null;
        }
        byte[] retData = new byte[hexString.length() / 2];
        for (int i = 0; i < hexString.length(); i++) {
            int int_ch;  // 10进法に転換
            char hex_char1 = hexString.charAt(i);
            int int_ch1;
            if (hex_char1 >= '0' && hex_char1 <= '9')
                int_ch1 = (hex_char1 - 48) * 16;   //// 0 のAscll - 48
            else if (hex_char1 >= 'A' && hex_char1 <= 'F')
                int_ch1 = (hex_char1 - 55) * 16; //// A のAscll - 65
            else
                return null;
            i++;
            char hex_char2 = hexString.charAt(i); ///2桁の16进法の第二桁
            int int_ch2;
            if (hex_char2 >= '0' && hex_char2 <= '9')
                int_ch2 = (hex_char2 - 48); //// 0 のAscll - 48
            else if (hex_char2 >= 'A' && hex_char2 <= 'F')
                int_ch2 = hex_char2 - 55; //// A のAscll - 65
            else
                return null;
            int_ch = int_ch1 + int_ch2;
            retData[i / 2] = (byte) int_ch;//转换后的byte
        }
        return retData;
    }
}
