package com.liuguilin.butlerservice.fragment;

/*
 *  项目名：  ButlerService 
 *  包名：    com.liuguilin.butlerservice.fragment
 *  文件名:   ButlerServiceFragment
 *  创建者:   LGL
 *  创建时间:  2016/8/24 11:22
 *  描述：    TODO
 */

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechError;
import com.iflytek.cloud.SpeechSynthesizer;
import com.iflytek.cloud.SynthesizerListener;
import com.kymjs.rxvolley.RxVolley;
import com.kymjs.rxvolley.client.HttpCallback;
import com.liuguilin.butlerservice.R;
import com.liuguilin.butlerservice.adapter.ChatListAdapter;
import com.liuguilin.butlerservice.entity.ChatListBean;
import com.liuguilin.butlerservice.utils.L;
import com.liuguilin.butlerservice.utils.ShareUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ButlerServiceFragment extends Fragment implements View.OnClickListener {

    // 会话数据源
    private List<ChatListBean> ListData;
    // 会话Adapter
    private ChatListAdapter chatListAdapter;
    // 会话列表
    private ListView mListView;
    // 消息
    private String message;
    // 按钮
    private Button btn_right;

    private EditText et_text;

    private SpeechSynthesizer mTts;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_butler_service, null);
        findview(view);
        return view;
    }

    private void findview(View view) {
        et_text = (EditText) view.findViewById(R.id.et_text);
        mListView = (ListView) view.findViewById(R.id.mListView);
        btn_right = (Button) view.findViewById(R.id.btn_right);
        btn_right.setOnClickListener(this);

        ListData = new ArrayList<ChatListBean>();
        ChatListBean chatListBean = new ChatListBean();
        chatListBean.setType(ChatListAdapter.VALUE_LEFT_TEXT);
        chatListBean.setValue("你好！");
        ListData.add(chatListBean);

        chatListAdapter = new ChatListAdapter(getActivity(), ListData);
        mListView.setAdapter(chatListAdapter);

        mTts = SpeechSynthesizer.createSynthesizer(getActivity(), null);
        // 2.合成参数设置，详见《科大讯飞MSC API手册(Android)》SpeechSynthesizer 类
        mTts.setParameter(SpeechConstant.VOICE_NAME, "xiaoqi");// 设置发音人
        mTts.setParameter(SpeechConstant.SPEED, "50");// 设置语速
        mTts.setParameter(SpeechConstant.VOLUME, "80");// 设置音量，范围0~100
        mTts.setParameter(SpeechConstant.ENGINE_TYPE, SpeechConstant.TYPE_CLOUD); // 设置云端

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_right:
                String text = et_text.getText().toString();
                if (!TextUtils.isEmpty(text)) {
                    listRight(text);
                    String url = "http://op.juhe.cn/robot/index?info=" + text + "&key=7a48539921338ef90866922b21e25f6d";
                    RxVolley.get(url, new HttpCallback() {
                        @Override
                        public void onSuccess(String t) {
                            getJson(t);
                        }

                        @Override
                        public void onFailure(int errorNo, String strMsg) {
                            listLeft("我好像出现问题了");
                        }
                    });
                } else {
                    Toast.makeText(getActivity(), "输入框不能为空", Toast.LENGTH_LONG).show();
                }
                break;
        }
    }

    private void getJson(String t) {
        try {
            JSONObject jsonObject = new JSONObject(t);
            JSONObject jsonResult = jsonObject.getJSONObject("result");
            listLeft(jsonResult.getString("text"));
            //
            et_text.setText("");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    /**
     * 显示列表左边
     *
     * @param text
     */
    private void listLeft(String text) {

        boolean is = ShareUtils.getBoolean(getActivity(), "isSpeak", false);
        L.i("is: ++" + is);
        if (is) {
            //播報
            startApk(text);
        }

        ChatListBean left = new ChatListBean();
        left.setType(ChatListAdapter.VALUE_LEFT_TEXT);
        left.setValue(text);
        ListData.add(left);
        chatListAdapter.notifyDataSetChanged();
        // 滚动到最底部
        mListView.setSelection(mListView.getBottom());
    }

    /**
     * 显示右边列表
     *
     * @param text
     */
    private void listRight(String text) {
        ChatListBean right = new ChatListBean();
        right.setType(ChatListAdapter.VALUE_RIGHT_TEXT);
        right.setValue(text);
        ListData.add(right);
        chatListAdapter.notifyDataSetChanged();
        // 滚动到最底部
        mListView.setSelection(mListView.getBottom());
    }

    private void startApk(String text) {
        mTts.startSpeaking(text, mSynListener);
    }

    // 合成监听器
    private SynthesizerListener mSynListener = new SynthesizerListener() {

        @Override
        public void onBufferProgress(int arg0, int arg1, int arg2, String arg3) {
            // TODO Auto-generated method stub

        }

        @Override
        public void onCompleted(SpeechError arg0) {
            // TODO Auto-generated method stub

        }

        @Override
        public void onEvent(int arg0, int arg1, int arg2, Bundle arg3) {
            // TODO Auto-generated method stub

        }

        @Override
        public void onSpeakBegin() {
            // TODO Auto-generated method stub

        }

        @Override
        public void onSpeakPaused() {
            // TODO Auto-generated method stub

        }

        @Override
        public void onSpeakProgress(int arg0, int arg1, int arg2) {
            // TODO Auto-generated method stub

        }

        @Override
        public void onSpeakResumed() {
            // TODO Auto-generated method stub

        }

    };
}
