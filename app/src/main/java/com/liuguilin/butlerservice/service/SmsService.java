package com.liuguilin.butlerservice.service;/*
 *  项目名：  ButlerService 
 *  包名：    com.liuguilin.butlerservice.service
 *  文件名:   SmsService
 *  创建者:   LGL
 *  创建时间:  2016/8/24 15:22
 *  描述：    TODO
 */

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.PixelFormat;
import android.net.Uri;
import android.os.IBinder;
import android.telephony.SmsMessage;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.liuguilin.butlerservice.R;
import com.liuguilin.butlerservice.utils.L;
import com.liuguilin.butlerservice.view.SessionLinearLayout;

public class SmsService extends Service {

    private InnerSmsReceiver mReceiver;
    //窗口管理器
    private WindowManager wm;
    //view
    private SessionLinearLayout mView;
    //布局参数
    private WindowManager.LayoutParams layoutParams;

    private String originatingAddress;
    private String messageBody;

    private TextView tv_title, tv_content;
    private Button btnSend;

    //监听Home
    private HomeWatcherReceiver mHomeKeyReceiver;
    public static final String SYSTEM_DIALOG_REASON_KEY = "reason";
    public static final String SYSTEM_DIALOG_REASON_HOME_KEY = "homekey";


    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        // 拦截短信, 同等条件下,动态注册更优先获取广播
        mReceiver = new InnerSmsReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction("android.provider.Telephony.SMS_RECEIVED");
        filter.setPriority(Integer.MAX_VALUE);
        registerReceiver(mReceiver, filter);

        L.d("sms true");

        //注册Home监听广播
        mHomeKeyReceiver = new HomeWatcherReceiver();
        final IntentFilter homeFilter = new IntentFilter(Intent.ACTION_CLOSE_SYSTEM_DIALOGS);
        registerReceiver(mHomeKeyReceiver, homeFilter);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        // 注销短信监听
        unregisterReceiver(mReceiver);
        mReceiver = null;

        //取消监听
        unregisterReceiver(mHomeKeyReceiver);
    }

    class InnerSmsReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            L.d("sms");
            //获取短信内容返回的是一个Object数组
            Object[] objs = (Object[]) intent.getExtras().get("pdus");
            //遍历数组得到短信内容
            for (Object obj : objs) {// 超过140字节,会分多条短信发送
                //把数组元素转换成短信对象
                SmsMessage sms = SmsMessage.createFromPdu((byte[]) obj);
                originatingAddress = sms.getOriginatingAddress();
                messageBody = sms.getMessageBody();
                L.i("短信号码:" + originatingAddress + ";短信内容:"
                        + messageBody);
                //弹框显示
                showSmsWindow(originatingAddress, messageBody);
            }
        }
    }

    private void showSmsWindow(String title, String content) {
        //窗口管理器
        wm = (WindowManager) getApplicationContext().getSystemService(Context.WINDOW_SERVICE);
        //布局参数
        layoutParams = new WindowManager.LayoutParams();
        layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT;
        layoutParams.height = WindowManager.LayoutParams.MATCH_PARENT;
        layoutParams.flags =
                //WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE | 不能触摸
                WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON | WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH;
        //WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE | 沒有焦点
        //格式
        layoutParams.format = PixelFormat.TRANSLUCENT;
        //类型
        layoutParams.type = WindowManager.LayoutParams.TYPE_PHONE;

        mView = (SessionLinearLayout) View.inflate(getApplicationContext(), R.layout.window_item, null);
        tv_title = (TextView) mView.findViewById(R.id.tv_title);
        tv_title.setText("发件人：" + originatingAddress);
        tv_content = (TextView) mView.findViewById(R.id.tv_content);
        tv_content.setText("短信内容：" + messageBody);
        btnSend = (Button) mView.findViewById(R.id.btnSend);
        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mView.getParent() != null) {
                    wm.removeView(mView);
                }
                //send sms
                Uri uri = Uri.parse("smsto:" + originatingAddress);
                Intent i = new Intent(Intent.ACTION_SENDTO, uri);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                i.putExtra("sms_baby", "");
                startActivity(i);
            }
        });
        wm.addView(mView, layoutParams);

        //监听返回键
        mView.setDispatchKeyEventListener(mDispatchKeyEventListener);
    }

    /**
     * 返回鍵监听
     */
    private SessionLinearLayout.DispatchKeyEventListener mDispatchKeyEventListener = new SessionLinearLayout.DispatchKeyEventListener() {
        @Override
        public boolean dispatchKeyEvent(KeyEvent event) {
            if (event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
                if (mView.getParent() != null) {
                    wm.removeView(mView);
                }
                return true;
            }
            return false;
        }
    };

    /**
     * 监听Home键
     */
    class HomeWatcherReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action.equals(Intent.ACTION_CLOSE_SYSTEM_DIALOGS)) {
                String reason = intent.getStringExtra(SYSTEM_DIALOG_REASON_KEY);
                if (SYSTEM_DIALOG_REASON_HOME_KEY.equals(reason)) {
                    if (mView.getParent() != null) {
                        wm.removeView(mView);
                    }
                }
            }
        }
    }
}


