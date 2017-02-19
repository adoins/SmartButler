package com.liuguilin.butlerservice.view;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;

import com.liuguilin.butlerservice.R;


/*
 *  项目名：  OKL_Voice
 *  包名：    com.okl.okl_voice.view
 *  文件名:   CustomDialog
 *  创建者:   LGL
 *  创建时间:  2016/7/6 9:42
 *  描述：    自定义Dialog
 */
public class CustomDialog extends Dialog {

    /**
     * 标准
     *
     * @param context 上下文
     * @param layout  布局
     * @param style   主题
     */
    public CustomDialog(Context context, int layout, int style) {
        this(context, WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.WRAP_CONTENT, layout, style,
                Gravity.CENTER);
    }

    /**
     * 有动画
     *
     * @param context 上下文
     * @param width   宽
     * @param height  高
     * @param layout  布局
     * @param style   主题
     * @param gravity 方位
     * @param anim    动画
     */
    public CustomDialog(Context context, int width, int height, int layout,
                        int style, int gravity, int anim) {
        super(context, style);

        setContentView(layout);
        Window window = getWindow();
        WindowManager.LayoutParams params = window.getAttributes();
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        params.gravity = gravity;
        window.setAttributes(params);
        window.setWindowAnimations(anim);
    }

    /**
     * 无动画
     *
     * @param context 上下文
     * @param width   宽
     * @param height  高
     * @param layout  布局
     * @param style   主题
     * @param gravity 方位
     */
    public CustomDialog(Context context, int width, int height, int layout,
                        int style, int gravity) {
        this(context, width, height, layout, style, gravity,
                R.style.pop_anim_style);
    }
}