package com.liuguilin.butlerservice.ui;

/*
 *  项目名：  ButlerService 
 *  包名：    com.liuguilin.butlerservice.ui
 *  文件名:   ResetPassActivity
 *  创建者:   LGL
 *  创建时间:  2016/9/2 13:46
 *  描述：    TODO
 */

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.liuguilin.butlerservice.R;
import com.liuguilin.butlerservice.entity.MyUser;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UpdateListener;

public class ResetPassActivity extends BaseActivity implements View.OnClickListener {

    private EditText et_now_pass;
    private EditText et_new_pass;
    private EditText et_new_password;

    private Button btnUpdate;

    private EditText et_email;
    private Button btnForget;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset);

        initView();
    }

    /**
     * 初始化view
     */
    private void initView() {
        et_now_pass = (EditText) findViewById(R.id.et_now_pass);
        et_new_pass = (EditText) findViewById(R.id.et_new_pass);
        et_new_password = (EditText) findViewById(R.id.et_new_password);

        btnUpdate = (Button) findViewById(R.id.btnUpdate);
        btnUpdate.setOnClickListener(this);
        et_email = (EditText) findViewById(R.id.et_email);
        btnForget = (Button) findViewById(R.id.btnForget);
        btnForget.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnUpdate:
                String now_pass = et_now_pass.getText().toString();
                String new_pass = et_new_pass.getText().toString();
                String new_password = et_new_password.getText().toString();
                if (!TextUtils.isEmpty(now_pass) && !TextUtils.isEmpty(new_pass) && !TextUtils.isEmpty(new_password)) {
                    if (new_pass.equals(new_password)) {
                        MyUser.updateCurrentUserPassword(now_pass, new_pass, new UpdateListener() {
                            @Override
                            public void done(BmobException e) {
                                if (e == null) {
                                    Toast.makeText(ResetPassActivity.this, "密码修改成功，可以用新密码进行登录啦", Toast.LENGTH_LONG).show();
                                    finish();
                                } else {
                                    Toast.makeText(ResetPassActivity.this, "失败" + e.getMessage(), Toast.LENGTH_LONG).show();
                                }
                            }
                        });
                    } else {
                        Toast.makeText(this, "两次输入的密码不相同", Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(this, "输入框不能为空", Toast.LENGTH_LONG).show();
                }

                break;
            case R.id.btnForget:
                final String email = et_email.getText().toString();
                if (!TextUtils.isEmpty(email)) {
                    MyUser.resetPasswordByEmail(email, new UpdateListener() {
                        @Override
                        public void done(BmobException e) {
                            if (e == null) {
                                Toast.makeText(ResetPassActivity.this, "重置密码请求成功，请到" + email + "邮箱进行密码重置操作", Toast.LENGTH_LONG).show();
                                finish();
                            } else {
                                Toast.makeText(ResetPassActivity.this, "失败" + e.getMessage(), Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                } else {
                    Toast.makeText(this, "输入框不能为空", Toast.LENGTH_LONG).show();
                }
                break;
        }
    }
}
