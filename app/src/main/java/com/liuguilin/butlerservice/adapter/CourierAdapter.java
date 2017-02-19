package com.liuguilin.butlerservice.adapter;

/*
 *  项目名：  ButlerService 
 *  包名：    com.liuguilin.butlerservice.adapter
 *  文件名:   CourierAdapter
 *  创建者:   LGL
 *  创建时间:  2016/8/24 14:03
 *  描述：    TODO
 */

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.liuguilin.butlerservice.R;
import com.liuguilin.butlerservice.entity.CourierData;

import java.util.List;

public class CourierAdapter extends BaseAdapter {

    private Context mContext;
    private LayoutInflater inflater;
    private List<CourierData> mList;
    private CourierData bean;

    public CourierAdapter(Context mContext, List<CourierData> mList) {
        this.mContext = mContext;
        this.mList = mList;

        inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int i) {
        return mList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewH = null;
        if (view == null) {
            viewH = new ViewHolder();
            view = inflater.inflate(R.layout.courier_item, null);
            viewH.img = (ImageView) view.findViewById(R.id.image);
            viewH.time = (TextView) view.findViewById(R.id.time);
            viewH.context = (TextView) view.findViewById(R.id.content);
            view.setTag(viewH);
        } else {
            viewH = (ViewHolder) view.getTag();
        }

        bean = mList.get(i);

        if (i == 0) {
            viewH.img.setBackgroundResource(R.drawable.point_off);
        } else {
            viewH.img.setBackgroundResource(R.drawable.timeline_green);
        }
        viewH.time.setText(bean.getDatetime());
        viewH.context.setText(bean.getRemark());
        return view;
    }

    class ViewHolder {
        private ImageView img;
        private TextView time;
        private TextView context;
    }
}
