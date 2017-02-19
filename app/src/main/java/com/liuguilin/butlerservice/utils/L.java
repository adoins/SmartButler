package com.liuguilin.butlerservice.utils;

/*
 *  项目名：  ButlerService 
 *  包名：    com.liuguilin.butlerservice.utils
 *  文件名:   L
 *  创建者:   LGL
 *  创建时间:  2016/8/24 10:40
 *  描述：    Log封装类
 */

import android.util.Log;

/**
 * Log等级：i,v,d,w,e
 */
public class L {

    //公共的Tag
    public static final String TAG = "ButlerService";

    //调试开关
    public static boolean DEBUG = true;


    public static void i(String log) {
        if (DEBUG) {
            Log.i(TAG, log);
        }
    }

    public static void v(String log) {
        if (DEBUG) {
            Log.v(TAG, log);
        }
    }

    public static void d(String log) {
        if (DEBUG) {
            Log.d(TAG, log);
        }
    }

    public static void w(String log) {
        if (DEBUG) {
            Log.w(TAG, log);
        }
    }

    public static void e(String log) {
        if (DEBUG) {
            Log.e(TAG, log);
        }
    }


}
