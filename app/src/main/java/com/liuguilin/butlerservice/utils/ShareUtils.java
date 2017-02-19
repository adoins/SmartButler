package com.liuguilin.butlerservice.utils;

/*
 *  项目名：  ButlerService 
 *  包名：    com.liuguilin.butlerservice.utils
 *  文件名:   ShareUtils
 *  创建者:   LGL
 *  创建时间:  2016/8/24 10:44
 *  描述：    封装SharedPreferences
 */

import android.content.Context;
import android.content.SharedPreferences;

public class ShareUtils {

    /**
     * 存字符串
     *
     * @param mContext
     * @param key
     * @param values
     */
    public static void putString(Context mContext, String key, String values) {
        SharedPreferences sp = mContext.getSharedPreferences("config", Context.MODE_PRIVATE);
        sp.edit().putString(key, values).commit();
    }

    /**
     * 取字符串
     *
     * @param mContext
     * @param key
     * @param values   默认值
     * @return 取出的值
     */
    public static String getString(Context mContext, String key, String values) {
        SharedPreferences sp = mContext.getSharedPreferences("config", Context.MODE_PRIVATE);
        return sp.getString(key, values);
    }


    /**
     * 存布尔值
     *
     * @param mContext
     * @param key
     * @param values
     */
    public static void putBoolean(Context mContext, String key, boolean values) {
        SharedPreferences sp = mContext.getSharedPreferences("config", Context.MODE_PRIVATE);
        sp.edit().putBoolean(key, values).commit();
    }



    /**
     * 取布尔值
     *
     * @param mContext
     * @param key
     * @param values   默认值
     * @return true/false
     */
    public static boolean getBoolean(Context mContext, String key, boolean values) {
        SharedPreferences sp = mContext.getSharedPreferences("config", Context.MODE_PRIVATE);
        return sp.getBoolean(key, values);
    }


    /**
     * 存int值
     *
     * @param mContext
     * @param key
     * @param values   值
     */
    public static void putInt(Context mContext, String key, int values) {
        SharedPreferences sp = mContext.getSharedPreferences("config", Context.MODE_PRIVATE);
        sp.edit().putInt(key, values).commit();
    }

    /**
     * 取int值
     *
     * @param mContext
     * @param key
     * @param values   默认值
     * @return
     */
    public static int getInt(Context mContext, String key, int values) {
        SharedPreferences sp = mContext.getSharedPreferences("config", Context.MODE_PRIVATE);
        return sp.getInt(key, values);
    }
    /**
     * 删除一条字段
     *
     * @param mContext
     * @param key
     */
    public static void deleShare(Context mContext, String key) {
        SharedPreferences sp = mContext.getSharedPreferences("config", Context.MODE_PRIVATE);
        sp.edit().remove(key).commit();
    }

    /**
     * 删除全部数据
     *
     * @param mContext
     */
    public static void deleShareAll(Context mContext) {
        SharedPreferences sp = mContext.getSharedPreferences("config", Context.MODE_PRIVATE);
        sp.edit().clear().commit();
    }


}
