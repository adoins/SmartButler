package com.liuguilin.butlerservice.view;

/*
 *  项目名：  OKL_Voice 
 *  包名：    com.okl.okl_voice.view
 *  文件名:   SessionLinearLayout
 *  创建者:   LGL
 *  创建时间:  2016/8/19 11:27
 *  描述：    事件分发/拦截返回按钮
 */

import android.content.Context;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.widget.LinearLayout;

public class SessionLinearLayout extends LinearLayout {

    private DispatchKeyEventListener mDispatchKeyEventListener;

    public SessionLinearLayout(Context context) {
        super(context);
    }

    public SessionLinearLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SessionLinearLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        if (mDispatchKeyEventListener != null) {
            return mDispatchKeyEventListener.dispatchKeyEvent(event);
        }
        return super.dispatchKeyEvent(event);
    }

    public DispatchKeyEventListener getDispatchKeyEventListener() {
        return mDispatchKeyEventListener;
    }

    public void setDispatchKeyEventListener(DispatchKeyEventListener mDispatchKeyEventListener) {
        this.mDispatchKeyEventListener = mDispatchKeyEventListener;
    }

    //监听接口
    public static interface DispatchKeyEventListener {
        boolean dispatchKeyEvent(KeyEvent event);
    }

}
