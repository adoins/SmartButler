package com.liuguilin.butlerservice.entity;/*
 *  项目名：  AndroidRepost 
 *  包名：    com.liuguilin.androidrepost
 *  文件名:   ChatListBean
 *  创建者:   LGL
 *  创建时间:  2016/8/23 17:27
 *  描述：    TODO
 */

public class ChatListBean {

    // 指定方向
    private int type;
    // 文本
    private String value;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

}
