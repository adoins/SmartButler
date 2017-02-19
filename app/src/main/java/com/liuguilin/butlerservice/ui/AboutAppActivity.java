package com.liuguilin.butlerservice.ui;

/*
 *  项目名：  ButlerService 
 *  包名：    com.liuguilin.butlerservice.ui
 *  文件名:   AboutAppActivity
 *  创建者:   LGL
 *  创建时间:  2016/9/10 9:00
 *  描述：    关于软件
 */

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.TextView;

import com.liuguilin.butlerservice.R;
import com.liuguilin.butlerservice.utils.UtilTools;

public class AboutAppActivity extends BaseActivity {

    private TextView tv_app_name;
    private TextView tv_app_version;
    private TextView tv_motto;
    private TextView tv_author_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_app);

        initView();
    }

    /**
     * 初始化View
     */
    private void initView() {
        tv_app_name = (TextView) findViewById(R.id.tv_app_name);
        tv_app_name.setText("应用名：" + getString(R.string.app_name));
        tv_app_version = (TextView) findViewById(R.id.tv_app_version);
        tv_app_version.setText(getVersion());

        tv_motto = (TextView) findViewById(R.id.tv_motto);
        UtilTools.setFontText(this,tv_motto);

        tv_author_name = (TextView) findViewById(R.id.tv_author_name);
        UtilTools.setFontText(this,tv_author_name);
    }

    /**
     * 获取版本号
     *
     * @return
     */
    public String getVersion() {
        try {
            PackageManager manager = getPackageManager();
            PackageInfo info = manager.getPackageInfo(getPackageName(), 0);
            String version = info.versionName;
            return "版本号：" + version;
        } catch (Exception e) {
            e.printStackTrace();
            return "无法获取版本号";
        }
    }
}
