package com.liuguilin.butlerservice.ui;

/*
 *  项目名：  ButlerService 
 *  包名：    com.liuguilin.butlerservice.ui
 *  文件名:   GuideActivity
 *  创建者:   LGL
 *  创建时间:  2016/8/24 10:54
 *  描述：    TODO
 */

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.liuguilin.butlerservice.R;
import com.liuguilin.butlerservice.utils.L;
import com.liuguilin.butlerservice.utils.UtilTools;

import java.util.ArrayList;
import java.util.List;

public class GuideActivity extends AppCompatActivity {

    private ViewPager mViewpager;
    private ImageView point1, point2, point3;
    //容器
    private List<View> mList = new ArrayList<>();
    private View mView1, mView2, mView3;
    private Button btnStart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);

        initView();
    }

    private void initView() {

        point1 = (ImageView) findViewById(R.id.point1);
        point1.setBackgroundResource(R.drawable.point_on);
        point2 = (ImageView) findViewById(R.id.point2);
        point2.setBackgroundResource(R.drawable.point_off);
        point3 = (ImageView) findViewById(R.id.point3);
        point3.setBackgroundResource(R.drawable.point_off);

        mViewpager = (ViewPager) findViewById(R.id.mViewpager);

        mView1 = View.inflate(this, R.layout.viewpager_item_one, null);
        mView2 = View.inflate(this, R.layout.viewpager_item_two, null);
        mView3 = View.inflate(this, R.layout.viewpager_item_three, null);

        UtilTools.setFontText(this, (TextView) mView1.findViewById(R.id.tv_left));
        UtilTools.setFontText(this, (TextView) mView1.findViewById(R.id.tv_right));

        UtilTools.setFontText(this, (TextView) mView2.findViewById(R.id.tv_left));
        UtilTools.setFontText(this, (TextView) mView2.findViewById(R.id.tv_right));

        UtilTools.setFontText(this, (TextView) mView3.findViewById(R.id.tv_left));
        UtilTools.setFontText(this, (TextView) mView3.findViewById(R.id.tv_right));

        btnStart = (Button) mView3.findViewById(R.id.btnStart);

        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(GuideActivity.this, LoginActivity.class));
                finish();
            }
        });

        mList.add(mView1);
        mList.add(mView2);
        mList.add(mView3);

        mViewpager.setAdapter(new GuidePagerAdapter());

        mViewpager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                L.i("Position:" + position);
                if (position == 0) {
                    point1.setBackgroundResource(R.drawable.point_on);
                    point2.setBackgroundResource(R.drawable.point_off);
                    point3.setBackgroundResource(R.drawable.point_off);
                } else if (position == 1) {
                    point1.setBackgroundResource(R.drawable.point_off);
                    point2.setBackgroundResource(R.drawable.point_on);
                    point3.setBackgroundResource(R.drawable.point_off);
                } else if (position == 2) {
                    point1.setBackgroundResource(R.drawable.point_off);
                    point2.setBackgroundResource(R.drawable.point_off);
                    point3.setBackgroundResource(R.drawable.point_on);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    class GuidePagerAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return mList.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            ((ViewPager) container).removeView(mList.get(position));
            //super.destroyItem(container, position, object);
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            ((ViewPager) container).addView(mList.get(position));
            return mList.get(position);
        }

        @Override
        public int getItemPosition(Object object) {
            return super.getItemPosition(object);
        }
    }
}
