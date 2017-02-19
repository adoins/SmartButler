package com.liuguilin.butlerservice.ui;

/*
 *  项目名：  ButlerService 
 *  包名：    com.liuguilin.butlerservice.ui
 *  文件名:   LoginActivity
 *  创建者:   LGL
 *  创建时间:  2016/9/1 16:24
 *  描述：    登录
 */

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.liuguilin.butlerservice.MainActivity;
import com.liuguilin.butlerservice.R;
import com.liuguilin.butlerservice.entity.MyUser;
import com.liuguilin.butlerservice.utils.L;
import com.liuguilin.butlerservice.utils.ShareUtils;
import com.liuguilin.butlerservice.view.CustomDialog;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText et_name;
    private EditText et_pass;
    private CheckBox keep_psw;
    private Button btnLogin, btnRegistered;
    private TextView forget_paw;

    private CustomDialog dialog;

    private BmobUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().setElevation(0);

        initView();
    }

    /**
     * 初始化View
     */
    private void initView() {
        et_name = (EditText) findViewById(R.id.et_name);
        et_pass = (EditText) findViewById(R.id.et_pass);
        keep_psw = (CheckBox) findViewById(R.id.keep_psw);
        btnLogin = (Button) findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(this);
        forget_paw = (TextView) findViewById(R.id.forget_paw);
        forget_paw.setOnClickListener(this);
        btnRegistered = (Button) findViewById(R.id.btnRegistered);
        btnRegistered.setOnClickListener(this);

        dialog = new CustomDialog(this, 100, 100, R.layout.dialog_loding, R.style.pop_anim_style, Gravity.CENTER);
        dialog.setCancelable(false);

        boolean isCheck = ShareUtils.getBoolean(this, "keepPass", false);
        keep_psw.setChecked(isCheck);
        if (isCheck) {
            et_name.setText(ShareUtils.getString(this, "name", ""));
            et_pass.setText(ShareUtils.getString(this, "password", ""));
            login();
        }
    }

    /**
     * 点击事件
     *
     * @param view
     */
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnLogin:
                login();
                break;
            case R.id.btnRegistered:
                startActivity(new Intent(this, RegisteredActivity.class));
                break;
            case R.id.forget_paw:
                startActivity(new Intent(this, ResetPassActivity.class));
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        L.i("isChecked:" + keep_psw.isChecked());
        ShareUtils.putBoolean(this, "keepPass", keep_psw.isChecked());

        if (keep_psw.isChecked()) {
            ShareUtils.putString(this, "password", et_pass.getText().toString().trim());
            ShareUtils.putString(this, "name", et_name.getText().toString().trim());
        } else {
            ShareUtils.deleShare(this, "name");
            ShareUtils.deleShare(this, "password");
        }

    }

    private void login() {
        String name = et_name.getText().toString().trim();//用户名
        String password = et_pass.getText().toString().trim();//密码
        if (!TextUtils.isEmpty(name) && !TextUtils.isEmpty(password)) {
            dialog.show();
            user = new BmobUser();
            user.setUsername(name);
            user.setPassword(password);
            user.login(new SaveListener<MyUser>() {
                @Override
                public void done(MyUser myUser, BmobException e) {
                    dialog.dismiss();
                    if (e == null) {//账号密码正确
                        if (!user.getEmailVerified()) {//账号存在但未在邮箱进行验证
                            Toast.makeText(LoginActivity.this, "请前往邮箱验证", Toast.LENGTH_LONG).show();
                        } else {
                            startActivity(new Intent(LoginActivity.this, MainActivity.class));
                            finish();
                        }
                    } else {
                        Toast.makeText(LoginActivity.this, "登录失败：" + e, Toast.LENGTH_LONG).show();
                    }
                }
            });
        } else {
            Toast.makeText(this, "输入框不能为空", Toast.LENGTH_LONG).show();
        }
    }

}
