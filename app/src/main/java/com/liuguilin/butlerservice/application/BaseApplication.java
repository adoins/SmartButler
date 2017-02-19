package com.liuguilin.butlerservice.application;/*
 *  项目名：  ButlerService 
 *  包名：    com.liuguilin.butlerservice.application
 *  文件名:   BaseApplication
 *  创建者:   LGL
 *  创建时间:  2016/8/24 13:35
 *  描述：    TODO
 */

import android.app.Application;

import com.baidu.mapapi.SDKInitializer;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechUtility;

import cn.bmob.v3.Bmob;
import cn.jpush.android.api.JPushInterface;

public class BaseApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        //极光推送
        JPushInterface.init(this);
        //在使用SDK各组件之前初始化context信息，传入ApplicationContext
        //注意该方法要再setContentView方法之前实现
        SDKInitializer.initialize(getApplicationContext());
        // 将“12345678”替换成您申请的APPID，申请地址：http://open.voicecloud.cn
        SpeechUtility.createUtility(this, SpeechConstant.APPID + "=57849562");

        //第一：默认初始化
        Bmob.initialize(this, "ef06c82190cc7e9e3dd0c9fb6aab91b3");

    }
}
