package com.liuguilin.butlerservice.adapter;

/*
 *  项目名：  AndroidRepost 
 *  包名：    com.liuguilin.androidrepost
 *  文件名:   ChatListAdapter
 *  创建者:   LGL
 *  创建时间:  2016/8/23 17:27
 *  描述：    机器人聊天适配器
 */

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.liuguilin.butlerservice.R;
import com.liuguilin.butlerservice.entity.ChatListBean;
import com.liuguilin.butlerservice.utils.UtilTools;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class ChatListAdapter extends BaseAdapter{

    public static final int VALUE_LEFT_TEXT = 1;
    public static final int VALUE_RIGHT_TEXT = 2;
    private LayoutInflater mInflater;
    private Context mContext;
    private List<ChatListBean> myList;

    public ChatListAdapter(Context context, List<ChatListBean> myList) {
        this.myList = myList;
        this.mContext = context;
        mInflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    @Override
    public int getCount() {
        return myList.size();
    }

    @Override
    public Object getItem(int arg0) {
        return myList.get(arg0);
    }

    @Override
    public long getItemId(int arg0) {
        return arg0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup arg2) {

        ChatListBean msg = myList.get(position);
        int type = getItemViewType(position);
        ViewHolderRightText holderRightText = null;
        ViewHolderLeftText holderLeftText = null;

        if (convertView == null) {
            switch (type) {
                // 左边
                case VALUE_LEFT_TEXT:
                    holderLeftText = new ViewHolderLeftText();
                    convertView = mInflater.inflate(R.layout.chatlist_left, null);
                    holderLeftText.btnLeftText = (TextView) convertView
                            .findViewById(R.id.chatlist_tv_left);
                    holderLeftText.btnLeftText.setText(msg.getValue());
                    convertView.setTag(holderLeftText);
                    break;
                // 右边
                case VALUE_RIGHT_TEXT:
                    holderRightText = new ViewHolderRightText();
                    convertView = mInflater.inflate(R.layout.chatlist_right, null);
                    holderRightText.btnRightText = (TextView) convertView
                            .findViewById(R.id.chatlist_tv_right);
                    holderRightText.iv_user_icon = (CircleImageView) convertView.findViewById(R.id.iv_user_icon);
                    holderRightText.btnRightText.setText(msg.getValue());
                    convertView.setTag(holderRightText);
                    break;
            }

        } else {
            switch (type) {
                case VALUE_LEFT_TEXT:
                    holderLeftText = (ViewHolderLeftText) convertView.getTag();
                    holderLeftText.btnLeftText.setText(msg.getValue());
                    break;
                case VALUE_RIGHT_TEXT:
                    holderRightText = (ViewHolderRightText) convertView.getTag();
                    holderRightText.btnRightText.setText(msg.getValue());
                    UtilTools.getImageToShare(mContext, holderRightText.iv_user_icon);
                    break;
            }

        }
        return convertView;
    }

    /**
     * 根据数据源的position返回需要显示的的layout的type
     * <p/>
     * type的值必须从0开始
     */
    @Override
    public int getItemViewType(int position) {

        ChatListBean msg = myList.get(position);
        int type = msg.getType();
        return type;
    }

    /**
     * 返回所有的layout的数量
     */
    @Override
    public int getViewTypeCount() {
        return 3;
    }

    // 左边的文本
    class ViewHolderLeftText {
        private TextView btnLeftText;
    }

    // 右边的文本
    class ViewHolderRightText {
        private TextView btnRightText;
        private CircleImageView iv_user_icon;
    }

}
