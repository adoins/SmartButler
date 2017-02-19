package com.liuguilin.butlerservice.ui;

/*
 *  项目名：  ButlerService 
 *  包名：    com.liuguilin.butlerservice.ui
 *  文件名:   IndexActivity
 *  创建者:   LGL
 *  创建时间:  2016/8/24 10:35
 *  描述：    TODO
 */

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.liuguilin.butlerservice.R;
import com.liuguilin.butlerservice.entity.StaticClass;
import com.liuguilin.butlerservice.utils.L;
import com.liuguilin.butlerservice.utils.ShareUtils;
import com.liuguilin.butlerservice.utils.UtilTools;

public class IndexActivity extends AppCompatActivity {

    private TextView index_text_title;

    private TextView tv_left;
    private TextView tv_right;

    /**
     * 本Activity的实现逻辑
     * 判断是否第一次进入
     * 第一次进入跳转到GuideActivity
     * 否则直接进入MainActivity
     */

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case StaticClass.IS_FIRST:
                    if (isFirstRun()) {
                        startActivity(new Intent(IndexActivity.this, LoginActivity.class));
                    } else {
                        startActivity(new Intent(IndexActivity.this, GuideActivity.class));
            }
            finish();
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_index);

        index_text_title = (TextView) findViewById(R.id.index_text_title);
        UtilTools.setFontText(this, index_text_title);

        tv_left = (TextView) findViewById(R.id.tv_left);
        tv_right = (TextView) findViewById(R.id.tv_right);
        UtilTools.setFontText(this, tv_left);
        UtilTools.setFontText(this, tv_right);

        handler.sendEmptyMessageDelayed(StaticClass.IS_FIRST, 2000);
    }

    /**
     * 是否第一次运行
     */
    private boolean isFirstRun() {
        boolean isFirst = ShareUtils.getBoolean(this, "isFirst", false);
        L.i("isFirst :" + isFirst);
        if (isFirst) {
            return true;
        } else {
            //设置已经第一次进来过了
            ShareUtils.putBoolean(this, "isFirst", true);
            return false;
        }
    }


    /**
     * 屏蔽返回键
     */
    @Override
    public void onBackPressed() {
        //super.onBackPressed();
    }

    /**
     * 设置字体
     *
     * @param mContext
     * @param textView
     */
    public static void setTextViewFont(Context mContext, TextView textView) {
        final Typeface fontFace = Typeface.createFromAsset(mContext.getAssets(),
                "fonts/YYG.TTF");
        textView.setTypeface(fontFace);
    }

}
