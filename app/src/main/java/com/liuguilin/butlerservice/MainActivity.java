package com.liuguilin.butlerservice;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.liuguilin.butlerservice.fragment.ButlerServiceFragment;
import com.liuguilin.butlerservice.fragment.MyLocationFragment;
import com.liuguilin.butlerservice.fragment.UserFragment;
import com.liuguilin.butlerservice.fragment.WeChatFragment;
import com.liuguilin.butlerservice.ui.RcCodeResultActivity;
import com.liuguilin.butlerservice.ui.SettingActivity;
import com.liuguilin.butlerservice.ui.WebViewActivity;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private TabLayout mTablayout;
    private ViewPager mViewPager;
    private List<String> mTitles;
    private List<Fragment> mFragments;
    private FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().setElevation(0);

        initDate();
        initView();
    }

    private void initDate() {
        mTitles = new ArrayList<>();
        mTitles.add("服务管家");
        mTitles.add("微信精选");
        mTitles.add("美女如云");
        mTitles.add("个人中心");
    }

    private void initView() {
        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setVisibility(View.INVISIBLE);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, SettingActivity.class));
            }
        });
        //初始化View
        mTablayout = (TabLayout) findViewById(R.id.id_tab_layout);
        mViewPager = (ViewPager) findViewById(R.id.id_view_pager);

        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (position == 0) {
                    fab.setVisibility(View.INVISIBLE);
                } else {
                    fab.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        //预加载
        mViewPager.setOffscreenPageLimit(3);

        //初始化List<Fragment>
        mFragments = new ArrayList<>();
        mFragments.add(new ButlerServiceFragment());
        mFragments.add(new WeChatFragment());
        mFragments.add(new MyLocationFragment());
        mFragments.add(new UserFragment());

        //给ViewPage设置Adapter
        mViewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return mFragments.get(position);
            }

            @Override
            public int getCount() {
                return mFragments.size();
            }

            @Override
            public CharSequence getPageTitle(int position) {
                return mTitles.get(position);
            }
        });
        mTablayout.setupWithViewPager(mViewPager);
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.menu, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        if (item.getItemId() == R.id.action_qrCode) {
//
//        }
//        return super.onOptionsItemSelected(item);
//    }


}
