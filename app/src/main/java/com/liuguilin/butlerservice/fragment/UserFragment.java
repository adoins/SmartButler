package com.liuguilin.butlerservice.fragment;

/*
 *  项目名：  ButlerService 
 *  包名：    com.liuguilin.butlerservice.fragment
 *  文件名:   UserFragment
 *  创建者:   LGL
 *  创建时间:  2016/8/24 11:25
 *  描述：    TODO
 */

//https://github.com/hdodenhof/CircleImageView

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Base64;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.liuguilin.butlerservice.R;
import com.liuguilin.butlerservice.entity.MyUser;
import com.liuguilin.butlerservice.ui.BelongingQueryActivity;
import com.liuguilin.butlerservice.ui.LoginActivity;
import com.liuguilin.butlerservice.ui.LogisticsQueryActivity;
import com.liuguilin.butlerservice.utils.L;
import com.liuguilin.butlerservice.utils.ShareUtils;
import com.liuguilin.butlerservice.utils.UtilTools;
import com.liuguilin.butlerservice.view.CustomDialog;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UpdateListener;
import de.hdodenhof.circleimageview.CircleImageView;

public class UserFragment extends Fragment implements View.OnClickListener {


    private static final int IMAGE_REQUEST_CODE = 10;
    private static final int CAMERA_REQUEST_CODE = 11;
    //头像名称
    private static final String IMAGE_FILE_NAME = "faceImage.jpg";
    private static final int RESULT_REQUEST_CODE = 12;
    private File tempFile = null;

    private EditText et_user;
    private EditText et_sex;
    private EditText et_age;
    private EditText et_desc;

    private Button btn_exit_login;

    private CircleImageView profile_image;
    private CustomDialog dialog;

    private Button btn_camera;
    private Button btn_picture;
    private Button btn_cancel;

    private TextView tv_edit;
    private Button btn_edit_ok;

    private TextView tv_logistics_query;
    private TextView tv_belonging_query;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user, null);
        findview(view);
        return view;
    }

    /**
     * 初始化
     *
     * @param view
     */
    private void findview(View view) {
        et_user = (EditText) view.findViewById(R.id.et_user);
        et_sex = (EditText) view.findViewById(R.id.et_sex);
        et_age = (EditText) view.findViewById(R.id.et_age);
        et_desc = (EditText) view.findViewById(R.id.et_desc);

        MyUser user = BmobUser.getCurrentUser(MyUser.class);
        et_user.setText(user.getUsername());
        boolean sex = user.isSex();
        if (sex) {
            et_sex.setText("男");
        } else {
            et_sex.setText("女");
        }
        et_age.setText(user.getAge() + "");
        et_desc.setText(user.getIntroduce());

        et_user.setEnabled(false);
        et_age.setEnabled(false);
        et_sex.setEnabled(false);
        et_desc.setEnabled(false);

        btn_exit_login = (Button) view.findViewById(R.id.btn_exit_login);
        btn_exit_login.setOnClickListener(this);

        profile_image = (CircleImageView) view.findViewById(R.id.profile_image);
        profile_image.setOnClickListener(this);

        String imageString = ShareUtils.getString(getActivity(),"image_title","");

        if (!imageString.equals("")) {
            // 第二步:利用Base64将字符串转换为ByteArrayInputStream
            byte[] byteArray = Base64.decode(imageString, Base64.DEFAULT);
            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(byteArray);
            // 第三步:利用ByteArrayInputStream生成Bitmap
            Bitmap bitmap = BitmapFactory.decodeStream(byteArrayInputStream);
            profile_image.setImageBitmap(bitmap);
        }

        dialog = new CustomDialog(getActivity(), 100, 100, R.layout.dialog_img, R.style.pop_anim_style, Gravity.BOTTOM);
        btn_camera = (Button) dialog.findViewById(R.id.btn_camera);
        btn_camera.setOnClickListener(this);
        btn_picture = (Button) dialog.findViewById(R.id.btn_picture);
        btn_picture.setOnClickListener(this);
        btn_cancel = (Button) dialog.findViewById(R.id.btn_cancel);
        btn_cancel.setOnClickListener(this);

        tv_edit = (TextView) view.findViewById(R.id.tv_edit);
        tv_edit.setOnClickListener(this);

        btn_edit_ok = (Button) view.findViewById(R.id.btn_edit_ok);
        btn_edit_ok.setOnClickListener(this);

        tv_logistics_query = (TextView) view.findViewById(R.id.tv_logistics_query);
        tv_logistics_query.setOnClickListener(this);

        tv_belonging_query = (TextView) view.findViewById(R.id.tv_belonging_query);
        tv_belonging_query.setOnClickListener(this);

    }


    /**
     * 点击事件
     *
     * @param view
     */
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_exit_login:
                //清除缓存用户对象
                BmobUser.logOut();
                startActivity(new Intent(getActivity(), LoginActivity.class));
                getActivity().finish();
                break;
            case R.id.profile_image:
                dialog.show();
                break;
            case R.id.btn_camera:
                getPhoto();
                break;
            case R.id.btn_picture:
                getPicture();
                break;
            case R.id.btn_cancel:
                dialog.dismiss();
                break;
            case R.id.tv_edit:
                //显示修改按钮
                btn_edit_ok.setVisibility(View.VISIBLE);
                //可以输入
                et_user.setEnabled(true);
                et_sex.setEnabled(true);
                et_age.setEnabled(true);
                et_desc.setEnabled(true);
                break;
            case R.id.btn_edit_ok:
                //更新用户
                String name = et_user.getText().toString();
                String age = et_age.getText().toString();
                String sex = et_sex.getText().toString();
                String desc = et_desc.getText().toString();
                if (!TextUtils.isEmpty(name) && !TextUtils.isEmpty(age) && !TextUtils.isEmpty(sex)) {
                    MyUser user = new MyUser();
                    user.setUsername(name);
                    user.setAge(Integer.parseInt(age));
                    if (sex.equals("男")) {
                        user.setSex(true);
                    } else if (sex.equals("女")) {
                        user.setSex(false);
                    } else {
                        user.setSex(true);
                    }
                    if (!TextUtils.isEmpty(desc)) {
                        user.setIntroduce(desc);
                    } else {
                        user.setIntroduce("这个人很懒，什么都没有留下！");
                    }
                    BmobUser bmobUser = BmobUser.getCurrentUser();
                    user.update(bmobUser.getObjectId(), new UpdateListener() {
                        @Override
                        public void done(BmobException e) {
                            if (e == null) {
                                Toast.makeText(getActivity(), "更新用户信息成功", Toast.LENGTH_LONG).show();
                                et_user.setEnabled(false);
                                et_sex.setEnabled(false);
                                et_age.setEnabled(false);
                                et_desc.setEnabled(false);
                                btn_edit_ok.setVisibility(View.GONE);
                            } else {
                                Toast.makeText(getActivity(), "更新用户信息失败", Toast.LENGTH_LONG).show();
                            }
                        }
                    });

                } else {
                    Toast.makeText(getActivity(), "输入框不能为空", Toast.LENGTH_LONG).show();
                }

                break;
            case R.id.tv_logistics_query:
                startActivity(new Intent(getActivity(), LogisticsQueryActivity.class));
                break;
            case R.id.tv_belonging_query:
                startActivity(new Intent(getActivity(), BelongingQueryActivity.class));
                break;

        }
    }

    /**
     * 图库选择
     */
    private void getPicture() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        this.startActivityForResult(intent, IMAGE_REQUEST_CODE);
        dialog.dismiss();
    }

    /**
     * 拍照
     */
    private void getPhoto() {
        Intent intentFromCapture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // 判断存储卡是否可以用，可用进行存储
        intentFromCapture.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(Environment.getExternalStorageDirectory(), IMAGE_FILE_NAME)));
        this.startActivityForResult(intentFromCapture, CAMERA_REQUEST_CODE);
        dialog.dismiss();
    }


    /**
     * 回调里面
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != getActivity().RESULT_CANCELED) {
            switch (requestCode) {
                case IMAGE_REQUEST_CODE:
                    startPhotoZoom(data.getData());
                    break;
                case CAMERA_REQUEST_CODE:
                    tempFile = new File(Environment.getExternalStorageDirectory(), IMAGE_FILE_NAME);
                    L.i("tempFile:" + tempFile);
                    startPhotoZoom(Uri.fromFile(tempFile));
                    break;
                case RESULT_REQUEST_CODE:
                    if (data != null) {
                        setImageToView(data);
                        if (tempFile != null) {
                            tempFile.delete();
                        }
                    }
                    break;
            }
        }
    }


    public void startPhotoZoom(Uri uri) {
        if (uri == null) {
            L.i("The uri is not exist.");
        }
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        // 设置裁剪
        intent.putExtra("crop", "true");
        // aspectX aspectY 是宽高的比例
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        // outputX outputY 是裁剪图片宽高
        intent.putExtra("outputX", 320);
        intent.putExtra("outputY", 320);
        intent.putExtra("return-data", true);
        startActivityForResult(intent, RESULT_REQUEST_CODE);
    }

    /*
        保存图片
     */
    private void setImageToView(Intent data) {
        Bundle extras = data.getExtras();
        if (extras != null) {
            final Bitmap photo = extras.getParcelable("data");
            profile_image.setImageBitmap(photo);
            UtilTools.putImageToShare(getActivity(), profile_image);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        // 保存头像
        BitmapDrawable bd = (BitmapDrawable) profile_image.getDrawable();
        Bitmap bitmap = bd.getBitmap();
        // 第一步:将Bitmap压缩至字节数组输出流ByteArrayOutputStream
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 80, byteArrayOutputStream);
        // 第二步:利用Base64将字节数组输出流中的数据转换成字符串String
        byte[] byteArray = byteArrayOutputStream.toByteArray();
        String imageString = new String(Base64.encodeToString(byteArray, Base64.DEFAULT));
        // 第三步:将String保持至SharedPreferences
        ShareUtils.putString(getActivity(), "image_title", imageString);
    }
}
