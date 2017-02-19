package com.liuguilin.butlerservice.entity;

/*
 *  项目名：  ButlerService 
 *  包名：    com.liuguilin.butlerservice.entity
 *  文件名:   MyUser
 *  创建者:   LGL
 *  创建时间:  2016/9/2 9:43
 *  描述：    用戶
 */

import cn.bmob.v3.BmobUser;

public class MyUser extends BmobUser {

    //name age sex
    //性别
    private boolean sex;
    //年龄
    private int age;
    //简介
    private String introduce;

    public boolean isSex() {
        return sex;
    }

    public void setSex(boolean sex) {
        this.sex = sex;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getIntroduce() {
        return introduce;
    }

    public void setIntroduce(String introduce) {
        this.introduce = introduce;
    }
}
