package com.liuguilin.butlerservice.utils;

/*
 *  项目名：  ButlerService 
 *  包名：    com.liuguilin.butlerservice.utils
 *  文件名:   UtilTools
 *  创建者:   LGL
 *  创建时间:  2016/9/2 17:37
 *  描述：    工具類
 */

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.util.Base64;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

public class UtilTools {

    /**
     * 设置字体
     * @param mContext
     * @param tv
     */
    public static void setFontText(Context mContext, TextView tv) {
        Typeface fontFace = Typeface.createFromAsset(mContext.getAssets(),
                "fonts/YYG.TTF");
        tv.setTypeface(fontFace);
    }

    public static void putImageToShare(Context mContext, ImageView imageView) {
        BitmapDrawable drawable = (BitmapDrawable) imageView.getDrawable();
        Bitmap bitmap = drawable.getBitmap();
        ByteArrayOutputStream byStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 80, byStream);
        byte[] byteArray = byStream.toByteArray();
        String imgString = new String(Base64.encodeToString(byteArray, Base64.DEFAULT));
        ShareUtils.putString(mContext, "image_title", imgString);
        L.e(imgString);
    }

    public static void getImageToShare(Context mContext, ImageView imageView) {
        String imgString = ShareUtils.getString(mContext, "image_title", "");
        if (!imgString.equals("")) {
            byte[] byteArray = Base64.decode(imgString, Base64.DEFAULT);
            ByteArrayInputStream byStream = new ByteArrayInputStream(byteArray);
            Bitmap bitmap = BitmapFactory.decodeStream(byStream);
            imageView.setImageBitmap(bitmap);
        }
    }



}
