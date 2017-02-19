package com.liuguilin.butlerservice.ui;

/*
 *  项目名：  ButlerService 
 *  包名：    com.liuguilin.butlerservice.ui
 *  文件名:   RegisteredActivity
 *  创建者:   LGL
 *  创建时间:  2016/9/1 16:29
 *  描述：    注册
 */

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.liuguilin.butlerservice.R;
import com.liuguilin.butlerservice.entity.MyUser;
import com.liuguilin.butlerservice.utils.L;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

public class RegisteredActivity extends BaseActivity implements View.OnClickListener {

    private EditText et_user;
    private EditText et_pass;
    private EditText et_password;
    private EditText et_email;
    private Button btnRegistered;

    private EditText et_age;
    private EditText et_desc;

    private boolean isGender;
    private RadioGroup mRadioGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registered);

        initView();
    }

    /**
     * 初始化View
     */
    private void initView() {
        et_user = (EditText) findViewById(R.id.et_user);
        et_pass = (EditText) findViewById(R.id.et_pass);
        et_password = (EditText) findViewById(R.id.et_password);
        et_email = (EditText) findViewById(R.id.et_email);
        btnRegistered = (Button) findViewById(R.id.btnRegistered);
        btnRegistered.setOnClickListener(this);
        et_age = (EditText) findViewById(R.id.et_age);
        et_desc = (EditText) findViewById(R.id.et_desc);

        mRadioGroup = (RadioGroup) findViewById(R.id.mRadioGroup);
    }

    /**
     * 点击事件
     *
     * @param view
     */
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnRegistered:
                String name = et_user.getText().toString().trim();//用户名
                String pass = et_pass.getText().toString().trim();//密码
                String password = et_password.getText().toString().trim();//确认密码
                String email = et_email.getText().toString().trim();//邮箱
                String age = et_age.getText().toString();//年龄
                String desc = et_desc.getText().toString();//描述

                //先判断性别
                mRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(RadioGroup radioGroup, int i) {
                        L.i("i:" + i);
                        if (i == R.id.rb_boy) {
                            isGender = true;
                        } else if (i == R.id.rb_girl) {
                            isGender = false;
                        }
                    }
                });

                if (!TextUtils.isEmpty(name) && !TextUtils.isEmpty(pass) && !TextUtils.isEmpty(password) && !TextUtils.isEmpty(email)
                        && !TextUtils.isEmpty(age)) {
                    if (pass.equals(password)) {
                        MyUser user = new MyUser();
                        user.setUsername(name);
                        user.setSex(isGender);
                        user.setAge(Integer.parseInt(age));
                        if (!TextUtils.isEmpty(desc)) {
                            user.setIntroduce(desc);
                        } else {
                            user.setIntroduce("这个人很懒，什么都没有留下！");
                        }
                        user.setPassword(password);
                        user.setEmail(email);
                        user.signUp(new SaveListener<MyUser>() {
                            @Override
                            public void done(MyUser myUser, BmobException e) {
                                if (e == null) {
                                    finish();
                                    L.i("userid:" + myUser.getObjectId());
                                    Toast.makeText(RegisteredActivity.this, "注册成功,请前往邮箱验证", Toast.LENGTH_LONG).show();
                                } else {
                                    Toast.makeText(RegisteredActivity.this, "注册失败：" + e, Toast.LENGTH_LONG).show();
                                }
                            }
                        });
                    } else {
                        Toast.makeText(this, "两次输入不相同", Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(this, "输入框不能为空", Toast.LENGTH_LONG).show();
                }
                break;
        }
    }
}
