package com.liuguilin.butlerservice.ui;

/*
 *  项目名：  ButlerService 
 *  包名：    com.liuguilin.butlerservice.ui
 *  文件名:   UpdateActivity
 *  创建者:   LGL
 *  创建时间:  2016/8/24 16:14
 *  描述：    TODO
 */

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.TextView;

import com.daimajia.numberprogressbar.NumberProgressBar;
import com.kymjs.rxvolley.RxVolley;
import com.kymjs.rxvolley.client.HttpCallback;
import com.kymjs.rxvolley.client.ProgressListener;
import com.kymjs.rxvolley.toolbox.FileUtils;
import com.liuguilin.butlerservice.R;
import com.liuguilin.butlerservice.entity.StaticClass;
import com.liuguilin.butlerservice.utils.L;

import java.io.File;

public class UpdateActivity extends BaseActivity {

    private String path;
    private NumberProgressBar numberPro;

    private TextView tvPress;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 10000:
                    Bundle bundle = msg.getData();
                    //当前进度/总进度  60/100 = 0.6*100  进度条的进度
                    long start = bundle.getLong("transferredBytes");
                    long all = bundle.getLong("totalSize");
                    numberPro.setProgress((int) (((float) start / (float) all) * 100));
                    tvPress.setText(bundle.getLong("transferredBytes") + "/" + bundle.getLong("totalSize"));
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        path = FileUtils.getSDCardPath() + "/" + System.currentTimeMillis() + ".apk";

        numberPro = (NumberProgressBar) findViewById(R.id.number_progress_bar);
        numberPro.setMax(100);
        tvPress = (TextView) findViewById(R.id.tvPress);
        L.i("test");
        RxVolley.download(path, StaticClass.NEW_VERSION, new ProgressListener() {
            @Override
            public void onProgress(long transferredBytes, long totalSize) {
                L.i("请求");
                Message message = new Message();
                message.what = 10000;
                Bundle bundle = new Bundle();
                bundle.putLong("totalSize", totalSize);
                bundle.putLong("transferredBytes", transferredBytes);
                message.setData(bundle);
                handler.sendMessage(message);
            }
        }, new HttpCallback() {
            @Override
            public void onSuccess(String t) {
                //下載成功
                // 跳转系统安装页面
                Intent i = new Intent();
                i.setAction(Intent.ACTION_VIEW);
                i.addCategory(Intent.CATEGORY_DEFAULT);
                i.setDataAndType(Uri.fromFile(new File(path)), "application/vnd.android.package-archive");
                startActivity(i);
                finish();
            }
        });
    }
}
