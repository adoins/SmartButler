package com.liuguilin.butlerservice.ui;


/*
 *  项目名：  ButlerService 
 *  包名：    com.liuguilin.butlerservice.ui
 *  文件名:   SettingActivity
 *  创建者:   LGL
 *  创建时间:  2016/8/24 11:36
 *  描述：    TODO
 */

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Switch;

import com.liuguilin.butlerservice.R;
import com.liuguilin.butlerservice.service.SmsService;
import com.liuguilin.butlerservice.utils.L;
import com.liuguilin.butlerservice.utils.ShareUtils;
import com.xys.libzxing.zxing.activity.CaptureActivity;

public class SettingActivity extends BaseActivity {

    private Switch isSpeak, isSms;

    private LinearLayout ll_update;

    private LinearLayout ll_qrcode;

    private LinearLayout ll_location;

    private LinearLayout ll_Capture;

    private LinearLayout ll_about_app;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        initView();
    }

    private void initView() {

        ll_location = (LinearLayout) findViewById(R.id.ll_location);
        ll_location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SettingActivity.this, LocationActivity.class));
            }
        });

        ll_update = (LinearLayout) findViewById(R.id.ll_update);
        isSpeak = (Switch) findViewById(R.id.isSpeak);
        ll_qrcode = (LinearLayout) findViewById(R.id.ll_qrcode);
        ll_qrcode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SettingActivity.this, QrCodeActivity.class));
            }
        });

        boolean isSpeaks = ShareUtils.getBoolean(this, "isSpeak", false);
        L.i("is:" + isSpeaks);
        if (isSpeaks) {
            isSpeak.setChecked(true);
            startService(new Intent(SettingActivity.this, SmsService.class));
        } else {
            isSpeak.setChecked(false);
            stopService(new Intent(SettingActivity.this, SmsService.class));
        }
        isSpeak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isSpeak.setSelected(!isSpeak.isSelected());
                ShareUtils.putBoolean(SettingActivity.this, "isSpeak", isSpeak.isChecked());
            }
        });


        isSms = (Switch) findViewById(R.id.isSms);
        boolean isSmss = ShareUtils.getBoolean(this, "isSms", false);
        L.i("is:" + isSmss);
        if (isSmss) {
            isSms.setChecked(true);
        } else {
            isSms.setChecked(false);
        }

        isSms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isSms.setSelected(!isSms.isChecked());
                ShareUtils.putBoolean(SettingActivity.this, "isSms", isSms.isChecked());
                if (isSms.isChecked()) {
                    startService(new Intent(SettingActivity.this, SmsService.class));
                } else {
                    stopService(new Intent(SettingActivity.this, SmsService.class));
                }
            }
        });

        ll_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AlertDialog.Builder(SettingActivity.this).setTitle("有新版本更新啦").setMessage("更新內容：\n 1.新增xxx \n 2. 优化xxx")
                        .setPositiveButton("更新", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                startActivity(new Intent(SettingActivity.this, UpdateActivity.class));
                            }
                        }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                }).show();
            }
        });

        ll_Capture = (LinearLayout) findViewById(R.id.ll_Capture);
        ll_Capture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                L.i("二维码");
                Intent openCameraIntent = new Intent(SettingActivity.this, CaptureActivity.class);
                startActivityForResult(openCameraIntent, 0);
            }
        });

        ll_about_app = (LinearLayout) findViewById(R.id.ll_about_app);
        ll_about_app.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SettingActivity.this,AboutAppActivity.class));
            }
        });
    }

    /**
     * 二維碼回調
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            Bundle bundle = data.getExtras();
            String scanResult = bundle.getString("result");
            //拿到路径之后跳转
            Bundle b = new Bundle();
            if (scanResult.startsWith("http")) {
                Intent i = new Intent(SettingActivity.this, WebViewActivity.class);
                b.putString("title", "扫描结果");
                b.putString("url", scanResult);
                i.putExtras(b);
                startActivity(i);
            } else {
                Intent i = new Intent(SettingActivity.this, RcCodeResultActivity.class);
                b.putString("scanResult", scanResult);
                i.putExtras(b);
                startActivity(i);
            }
        }
    }

}
