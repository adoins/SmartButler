package com.liuguilin.butlerservice.ui;


/*
 *  项目名：  ButlerService 
 *  包名：    com.liuguilin.butlerservice.ui
 *  文件名:   QrCodeActivity
 *  创建者:   LGL
 *  创建时间:  2016/8/26 16:48
 *  描述：    TODO
 */

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.Bundle;
import android.widget.ImageView;

import com.liuguilin.butlerservice.R;

public class QrCodeActivity extends BaseActivity {

    private ImageView img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qrcode);

        int w = getResources().getDisplayMetrics().widthPixels;
        Resources res=getResources();
        Bitmap qrCodeBitmap= BitmapFactory.decodeResource(res, R.drawable.cli_500px);
        Matrix matrix = new Matrix();
        matrix.postScale(0.5f,0.5f); //长和宽放大缩小的比例
        Bitmap resizeBmp = Bitmap.createBitmap(qrCodeBitmap,0,0,qrCodeBitmap.getWidth(),qrCodeBitmap.getHeight(),matrix,true);

        img = (ImageView) findViewById(R.id.img);
//        Bitmap qrCodeBitmap = EncodingUtils.createQRCode("Hello 我是管家服务", w * 2 / 3, w * 2 / 3,
//                BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher));
        img.setImageBitmap(resizeBmp);
    }
}
