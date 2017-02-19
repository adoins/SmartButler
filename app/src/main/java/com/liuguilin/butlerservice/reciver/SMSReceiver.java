package com.liuguilin.butlerservice.reciver;

/*
 *  项目名：  ButlerService 
 *  包名：    com.liuguilin.butlerservice.reciver
 *  文件名:   SMSReceiver
 *  创建者:   LGL
 *  创建时间:  2016/9/2 23:25
 *  描述：    短信
 */

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;

import com.liuguilin.butlerservice.utils.L;

public class SMSReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        L.i("收到短信");
        //获取短信内容
        Bundle bundle = intent.getExtras();
        //返回的是一个Object数组
        Object[] objects = (Object[]) bundle.get("pdus");
        //遍历数组得到短信内容
        for (Object object : objects) {
            //把数组元素转换成短信对象
            SmsMessage sms = SmsMessage.createFromPdu((byte[]) object);
            //获取发件人号码
            String toPhone = sms.getOriginatingAddress();
            //获取短信内容
            String smsContent = sms.getMessageBody();
            //L.i("发件人号码:" + toPhone + "短信内容" + smsContent);
        }
    }
}

