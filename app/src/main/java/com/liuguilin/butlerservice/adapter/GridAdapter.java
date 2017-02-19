package com.liuguilin.butlerservice.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.kymjs.rxvolley.http.RequestQueue;
import com.liuguilin.butlerservice.R;
import com.liuguilin.butlerservice.entity.GirlBean;
import com.liuguilin.butlerservice.utils.L;
import com.liuguilin.butlerservice.utils.PicassoUtils;

import java.util.List;

/**
 * 妹子图片
 * Created by LGL on 2016/6/19.
 */
public class GridAdapter extends BaseAdapter {

    private Context mContext;
    private LayoutInflater inflater;
    private List<GirlBean> mList;
    private RequestQueue mQueue;

    private GirlBean bean;

    private int w,h;

    public GridAdapter(Context mContext, List<GirlBean> mList) {
        this.mContext = mContext;
        this.mList = mList;

        inflater = (LayoutInflater) mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        w = mContext.getResources().getDisplayMetrics().widthPixels;
        h = mContext.getResources().getDisplayMetrics().heightPixels;

    }


    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHoldwe vHoldwe = null;
        if (convertView == null) {
            vHoldwe = new ViewHoldwe();
            convertView = inflater.inflate(R.layout.girl_item, null);
            vHoldwe.iv_url = (ImageView) convertView
                    .findViewById(R.id.iv_url);
            vHoldwe.tv_time = (TextView) convertView
                    .findViewById(R.id.tv_time);
            convertView.setTag(vHoldwe);
        } else {
            vHoldwe = (ViewHoldwe) convertView.getTag();
        }

        bean = mList.get(position);
        //使用Picasso解析
        L.i("-----------" + bean.getUrl());
        if (!TextUtils.isEmpty(bean.getUrl())) {
            PicassoUtils.loadImageViewSize(mContext, bean.getUrl(), w/2, h/4-20, vHoldwe.iv_url);
        } else {
            vHoldwe.iv_url.setBackgroundResource(R.drawable.iv_error);
        }
        vHoldwe.tv_time.setText(bean.getTime());

        return convertView;
    }

    class ViewHoldwe {
        private ImageView iv_url;
        private TextView tv_time;
    }
}
