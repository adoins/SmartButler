package com.liuguilin.butlerservice.ui;

/*
 *  项目名：  ButlerService 
 *  包名：    com.liuguilin.butlerservice.ui
 *  文件名:   LogisticsQueryActivity
 *  创建者:   LGL
 *  创建时间:  2016/9/6 21:00
 *  描述：    物流
 */

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.kymjs.rxvolley.RxVolley;
import com.kymjs.rxvolley.client.HttpCallback;
import com.liuguilin.butlerservice.R;
import com.liuguilin.butlerservice.adapter.CourierAdapter;
import com.liuguilin.butlerservice.entity.CourierData;
import com.liuguilin.butlerservice.entity.StaticClass;
import com.liuguilin.butlerservice.utils.L;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class LogisticsQueryActivity extends BaseActivity{

    private EditText et_name, et_number;
    private Button btnGo;
    private ListView mListView;
    private List<String> mListTime = new ArrayList<>();
    private List<String> mListContent = new ArrayList<>();
    private List<CourierData> mList = new ArrayList<>();
    private CourierAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logisticsquery);

        findview();
    }
    private void findview() {
        et_name = (EditText) findViewById(R.id.et_name);
        et_number = (EditText) findViewById(R.id.et_number);
        btnGo = (Button) findViewById(R.id.btnGo);
        mListView = (ListView) findViewById(R.id.mListView);

        btnGo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //开始解析
                String name = et_name.getText().toString();
                String number = et_number.getText().toString();
                if (!TextUtils.isEmpty(name) && !TextUtils.isEmpty(number)) {
                    String url = "http://v.juhe.cn/exp/index?key=" + StaticClass.COURIER_KEY + "&com=" + name + "&no=" + "3950371339303";
                    RxVolley.get(url, new HttpCallback() {
                        @Override
                        public void onSuccess(String t) {
                            L.i(t);
                            getJson(t);
                        }
                    });
                } else {
                    //输入框不能为空
                }
            }
        });
    }

    private void getJson(String t) {
        try {
            JSONObject jsonObject = new JSONObject(t);
            JSONObject jsonResult = jsonObject.getJSONObject("result");
            JSONArray jsonArray = jsonResult.getJSONArray("list");
            for (int i = 0; i < jsonArray.length(); i++)  {
                JSONObject json = (JSONObject) jsonArray.get(i);
                CourierData data = new CourierData();
                data.setRemark(json.getString("remark"));
                data.setZone(json.getString("zone"));
                data.setDatetime(json.getString("datetime"));
                mList.add(data);
            }
            Collections.reverse(mList);
            CourierAdapter adapter = new CourierAdapter(this, mList);
            mListView.setAdapter(adapter);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
