package com.liuguilin.butlerservice.ui;/*


 *  项目名：  ButlerService 
 *  包名：    com.liuguilin.butlerservice.ui
 *  文件名:   BelongingQueryActivity
 *  创建者:   LGL
 *  创建时间:  2016/9/9 22:30
 *  描述：    归属地
 */

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.kymjs.rxvolley.RxVolley;
import com.kymjs.rxvolley.client.HttpCallback;
import com.liuguilin.butlerservice.R;

import org.json.JSONException;
import org.json.JSONObject;

public class BelongingQueryActivity extends BaseActivity implements View.OnClickListener {

    //输入框
    private EditText et_number;
    //logo
    private ImageView ivOperator;
    //结果
    private TextView tv_result;
    //按钮
    private Button btn0, btn1, btn2, btn3, btn4, btn5, btn6, btn7, btn8, btn9, btn_del, btn_query;

    private boolean flag = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_belonging_query);

        initView();
    }

    /**
     * 初始化View
     */
    private void initView() {
        btn0 = (Button) findViewById(R.id.btn0);
        btn0.setOnClickListener(this);
        btn1 = (Button) findViewById(R.id.btn1);
        btn1.setOnClickListener(this);
        btn2 = (Button) findViewById(R.id.btn2);
        btn2.setOnClickListener(this);
        btn3 = (Button) findViewById(R.id.btn3);
        btn3.setOnClickListener(this);
        btn4 = (Button) findViewById(R.id.btn4);
        btn4.setOnClickListener(this);
        btn5 = (Button) findViewById(R.id.btn5);
        btn5.setOnClickListener(this);
        btn6 = (Button) findViewById(R.id.btn6);
        btn6.setOnClickListener(this);
        btn7 = (Button) findViewById(R.id.btn7);
        btn7.setOnClickListener(this);
        btn8 = (Button) findViewById(R.id.btn8);
        btn8.setOnClickListener(this);
        btn9 = (Button) findViewById(R.id.btn9);
        btn9.setOnClickListener(this);
        btn_del = (Button) findViewById(R.id.btn_del);
        btn_del.setOnClickListener(this);
        btn_query = (Button) findViewById(R.id.btn_query);
        btn_query.setOnClickListener(this);

        et_number = (EditText) findViewById(R.id.et_number);
        ivOperator = (ImageView) findViewById(R.id.ivOperator);
        tv_result = (TextView) findViewById(R.id.tv_result);

        btn_del.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                et_number.setText("");
                return false;
            }
        });
    }

    /**
     * 点击事件
     *
     * @param view
     */
    @Override
    public void onClick(View view) {
        String str = et_number.getText().toString();
        switch (view.getId()) {
            case R.id.btn0:
            case R.id.btn1:
            case R.id.btn2:
            case R.id.btn3:
            case R.id.btn4:
            case R.id.btn5:
            case R.id.btn6:
            case R.id.btn7:
            case R.id.btn8:
            case R.id.btn9:
                if (flag) {
                    flag = false;
                    str = "";
                    et_number.setText("");
                }
                et_number.setText(str + ((Button) view).getText());
                et_number.setSelection(str.length() + 1);
                break;
            case R.id.btn_del:
                if (!TextUtils.isEmpty(str) && str.length() > 0) {
                    et_number.setText(str.substring(0, str.length() - 1));
                    et_number.setSelection(str.length()-1);
                }
                break;
            case R.id.btn_query:
                if (!TextUtils.isEmpty(str) && str.length() >= 6) {
                    getPhone(str);
                }
                break;
        }
    }

    /**
     * 获取号码
     */
    private void getPhone(String text) {
        String url = "http://apis.juhe.cn/mobile/get?phone=" + text + "&key=22a6ba14995ce26dd0002216be51dabb";
        RxVolley.get(url, new HttpCallback() {
            @Override
            public void onSuccess(String t) {
                parsingJson(t);
            }
        });
    }

    /**
     * 解析json
     *
     * @param t
     */
    private void parsingJson(String t) {
        try {
            JSONObject jsonObject = new JSONObject(t);
            JSONObject jsonResult = jsonObject.getJSONObject("result");
            String city = jsonResult.getString("province") + "-" + jsonResult.getString("city");
            String areacode = jsonResult.getString("areacode");
            String zip = jsonResult.getString("zip");
            String company = jsonResult.getString("company");
            String card = jsonResult.getString("card");

            tv_result.setText("归属地：" + city + "\n区号：" + areacode + "\n邮编：" + zip + "\n运营商："
                    + company + "\n类型：" + card);

            if (company.equals("中国移动")) {
                ivOperator.setImageResource(R.drawable.china_moblie);
            } else if (company.equals("中国联通")) {
                ivOperator.setImageResource(R.drawable.china_ubicom);
            } else if (company.equals("中国电信")) {
                ivOperator.setImageResource(R.drawable.china_telecom);
            }

            flag = true;
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
