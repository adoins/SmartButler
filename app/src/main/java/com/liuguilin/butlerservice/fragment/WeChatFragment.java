package com.liuguilin.butlerservice.fragment;

/*
 *  项目名：  ButlerService 
 *  包名：    com.liuguilin.butlerservice.fragment
 *  文件名:   ButlerServiceFragment
 *  创建者:   LGL
 *  创建时间:  2016/8/24 11:22
 *  描述：    TODO
 */

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.kymjs.rxvolley.RxVolley;
import com.kymjs.rxvolley.client.HttpCallback;
import com.liuguilin.butlerservice.R;
import com.liuguilin.butlerservice.adapter.WechatAdapter;
import com.liuguilin.butlerservice.entity.StaticClass;
import com.liuguilin.butlerservice.entity.WechatBean;
import com.liuguilin.butlerservice.ui.WebViewActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class WeChatFragment extends Fragment {

    private ListView mListView;

    private List<WechatBean> mList = new ArrayList<WechatBean>();

    private WechatAdapter adapter;

    private List<String> urlList = new ArrayList<String>();

    private List<String> titleList = new ArrayList<String>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_wechat, null);
        findview(view);
        return view;
    }

    private void findview(View view) {
        mListView = (ListView) view.findViewById(R.id.list_view);

        RxVolley.get(StaticClass.WECHAT_URL, new HttpCallback() {
            @Override
            public void onSuccess(String t) {
                Volley_news(t);
            }
        });

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                Intent i = new Intent(getActivity(), WebViewActivity.class);
                Bundle b = new Bundle();
                b.putString("title", titleList.get(position));
                b.putString("url", urlList.get(position));
                i.putExtras(b);
                startActivity(i);
            }
        });

    }


    /**
     * 解析Json
     *
     * @param json
     */
    private void Volley_news(String json) {
        try {
            JSONObject jsonObject = new JSONObject(json);
            JSONObject jsonresult = jsonObject.getJSONObject("result");
            JSONArray jArray = jsonresult.getJSONArray("list");

            for (int i = 0; i < jArray.length(); i++) {
                JSONObject jb = (JSONObject) jArray.get(i);
                WechatBean bean = new WechatBean();
                bean.setTitle(jb.getString("title"));
                bean.setType(jb.getString("source"));
                bean.setUrl(jb.getString("firstImg"));
                mList.add(bean);

                urlList.add(jb.getString("url"));
                titleList.add(jb.getString("title"));
            }
            adapter = new WechatAdapter(getActivity(), mList);
            mListView.setAdapter(adapter);

        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
