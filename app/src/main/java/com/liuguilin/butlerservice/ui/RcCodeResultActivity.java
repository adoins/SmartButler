package com.liuguilin.butlerservice.ui;

/*
 *  项目名：  ButlerService 
 *  包名：    com.liuguilin.butlerservice.ui
 *  文件名:   RcCodeResultActivity
 *  创建者:   LGL
 *  创建时间:  2016/8/26 17:14
 *  描述：    TODO
 */

import android.os.Bundle;
import android.widget.TextView;

import com.liuguilin.butlerservice.R;

public class RcCodeResultActivity extends BaseActivity {

    private TextView tv_result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qrcode_result);

        tv_result = (TextView) findViewById(R.id.tv_result);

        Bundle b = getIntent().getExtras();
        tv_result.setText(b.getString("scanResult"));
    }
}
