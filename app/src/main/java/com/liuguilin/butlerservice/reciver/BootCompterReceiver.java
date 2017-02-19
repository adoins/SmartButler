package com.liuguilin.butlerservice.reciver;/*
 *  项目名：  ButlerService 
 *  包名：    com.liuguilin.butlerservice.reciver
 *  文件名:   BootCompterReceiver
 *  创建者:   LGL
 *  创建时间:  2016/8/24 15:23
 *  描述：    TODO
 */

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.liuguilin.butlerservice.service.SmsService;
import com.liuguilin.butlerservice.utils.ShareUtils;

public class BootCompterReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if(ShareUtils.getBoolean(context,"isSms",false)){
            context.startService(new Intent(context, SmsService.class));
        }
    }
}
