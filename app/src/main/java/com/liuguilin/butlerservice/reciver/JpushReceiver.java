package com.liuguilin.butlerservice.reciver;

/*
 *  项目名：  ButlerService 
 *  包名：    com.liuguilin.butlerservice.reciver
 *  文件名:   JpushReceiver
 *  创建者:   LGL
 *  创建时间:  2016/9/4 22:54
 *  描述：    Jpush
 */

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.liuguilin.butlerservice.utils.L;

import cn.jpush.android.api.JPushInterface;

public class JpushReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

        Bundle bundle = intent.getExtras();
        String action = intent.getAction();

        if (JPushInterface.ACTION_REGISTRATION_ID.equals(action)) {
            String regId = bundle.getString(JPushInterface.EXTRA_REGISTRATION_ID);
            L.d( "[MyReceiver] 接收Registration Id : " + regId);

        } else if (JPushInterface.ACTION_MESSAGE_RECEIVED.equals(action)) {
            L.d( "[MyReceiver] 接收到推送下来的自定义消息: " + bundle.getString(JPushInterface.EXTRA_MESSAGE));

        } else if (JPushInterface.ACTION_NOTIFICATION_RECEIVED.equals(intent.getAction())) {
            L.d( "[MyReceiver] 接收到推送下来的通知");
            int notifactionId = bundle.getInt(JPushInterface.EXTRA_NOTIFICATION_ID);
            L.d("[MyReceiver] 接收到推送下来的通知的ID: " + notifactionId);

        } else if (JPushInterface.ACTION_NOTIFICATION_OPENED.equals(intent.getAction())) {
            L.d( "[MyReceiver] 用户点击打开了通知");
            //打开自定义的Activity

        } else if (JPushInterface.ACTION_RICHPUSH_CALLBACK.equals(intent.getAction())) {
            L.d( "[MyReceiver] 用户收到到RICH PUSH CALLBACK: " + bundle.getString(JPushInterface.EXTRA_EXTRA));
            //在这里根据 JPushInterface.EXTRA_EXTRA 的内容处理代码，比如打开新的Activity， 打开一个网页等..

        } else if (JPushInterface.ACTION_CONNECTION_CHANGE.equals(intent.getAction())) {
            boolean connected = intent.getBooleanExtra(JPushInterface.EXTRA_CONNECTION_CHANGE, false);
            L.w( "[MyReceiver]" + intent.getAction() + " connected state change to " + connected);
        } else {
            L.d( "[MyReceiver] Unhandled intent - " + intent.getAction());
        }
    }
}
