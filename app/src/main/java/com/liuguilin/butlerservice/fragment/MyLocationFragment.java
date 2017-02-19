package com.liuguilin.butlerservice.fragment;

/*
 *  项目名：  ButlerService 
 *  包名：    com.liuguilin.butlerservice.fragment
 *  文件名:   ButlerServiceFragment
 *  创建者:   LGL
 *  创建时间:  2016/8/24 11:22
 *  描述：    妹子
 */

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.kymjs.rxvolley.RxVolley;
import com.kymjs.rxvolley.client.HttpCallback;
import com.liuguilin.butlerservice.R;
import com.liuguilin.butlerservice.adapter.GridAdapter;
import com.liuguilin.butlerservice.entity.GirlBean;
import com.liuguilin.butlerservice.utils.L;
import com.liuguilin.butlerservice.view.CustomDialog;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import uk.co.senab.photoview.PhotoViewAttacher;

public class MyLocationFragment extends Fragment {

    //列表
    private GridView mGridView;

    private List<GirlBean> mList = new ArrayList<>();

    private GridAdapter adapter;
    //支持缩放
    private PhotoViewAttacher mAttacher;

    //存储url
    private List<String> urlList = new ArrayList<>();

    //提示框
     private CustomDialog imgDialog;

    private ImageView iv_girl_pow;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my_location, null);
        findview(view);
        return view;
    }

    private void findview(View view) {
        mGridView = (GridView) view.findViewById(R.id.mGridView);
        imgDialog = new CustomDialog(getActivity(), LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT, R.layout.dialog_girl, R.style.Theme_dialog,
                Gravity.CENTER, R.style.pop_anim_style);
        iv_girl_pow = (ImageView) imgDialog.findViewById(R.id.iv_girl_pow);
        //Gank的接口
        String welfare = null;
        try {
            //Gank升級 需要转码
            welfare = URLEncoder.encode("福利", "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        String url = "http://gank.io/api/search/query/listview/category/" + welfare + "/count/50/page/1";
        RxVolley.get(url, new HttpCallback() {
            @Override
            public void onSuccess(String t) {
                Volley_news(t);
            }
        });
    }

    /**
     * json解析
     *
     * @param json
     */
    private void Volley_news(String json) {

        try {
            JSONObject jsonObject = new JSONObject(json);
            JSONArray jsonArray = jsonObject.getJSONArray("results");

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject1 = (JSONObject) jsonArray.get(i);
                GirlBean bean = new GirlBean();
                bean.setTime(jsonObject1.getString("publishedAt"));
                bean.setUrl(jsonObject1.getString("url"));
                mList.add(bean);
                urlList.add(jsonObject1.getString("url"));
            }
            adapter = new GridAdapter(getActivity(), mList);
            mGridView.setAdapter(adapter);
            //点击事件
            mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    L.i(urlList.get(position));
//                    Glide.with(getActivity()).load(urlList.get(position)).into(iv_girl_pow);
                    Volley_Iv(urlList.get(position));
                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }


    // 加载图片
    protected void Volley_Iv(String imgUrl) {
        //成功就直接设置获取到的bitmap图片
        Picasso.with(getActivity()).load(imgUrl).into(iv_girl_pow);
        //PicassoUtils.loadImageViewCrop(getActivity(),imgUrl,iv_girl_pow);
        mAttacher = new PhotoViewAttacher(iv_girl_pow);
        mAttacher.update();
        imgDialog.show();
    }
}
