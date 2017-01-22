package com.haha.exam.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;

import com.haha.exam.R;
import com.haha.exam.adapter.MyFragmentPagerAdapter;
import com.haha.exam.fragment.CoachFragment;
import com.haha.exam.fragment.PlaceFragment;
import com.haha.exam.fragment.SchoolFragment;

import java.util.ArrayList;

/**
 * 预约界面
 */
public class OrderActivity extends BaseActivity implements View.OnClickListener {

    private ImageView iv_1, iv_2, iv_3, back;
    private ViewPager viewPager;
    private ArrayList<Fragment> fragmentList;
    private Fragment schoolFragment, coachFragment, placeFragment;
    private int position;

    @Override
    protected int getContentView() {
        return R.layout.activity_order;
    }

    @Override
    protected int getTitlebarResId() {
        return R.layout.order_bar;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        position = intent.getIntExtra("tab", 0);
        initView();
        initViewPager();
    }

    private void initViewPager() {
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        fragmentList = new ArrayList<>();
        schoolFragment = new SchoolFragment();
        coachFragment = new CoachFragment();
        placeFragment = new PlaceFragment();
        fragmentList.add(schoolFragment);
        fragmentList.add(coachFragment);
        fragmentList.add(placeFragment);

        viewPager.setAdapter(new MyFragmentPagerAdapter(getSupportFragmentManager(), fragmentList));
        viewPager.setCurrentItem(position);
        viewPager.setOnPageChangeListener(new MyOnPageChangeListener());
    }

    public class MyOnPageChangeListener implements ViewPager.OnPageChangeListener {

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {
            // TODO Auto-generated method stub

        }

        @Override
        public void onPageScrollStateChanged(int arg0) {
            // TODO Auto-generated method stub

        }

        @Override
        public void onPageSelected(int i) {
            // TODO Auto-generated method stub
            if (i == 0) {
                iv_1.setSelected(true);
                iv_2.setSelected(false);
                iv_3.setSelected(false);
            } else if (i == 1) {
                iv_1.setSelected(false);
                iv_2.setSelected(true);
                iv_3.setSelected(false);
            } else if (i == 2) {
                iv_1.setSelected(false);
                iv_2.setSelected(false);
                iv_3.setSelected(true);
            }

        }
    }

    private void initView() {
        back = (ImageView) findViewById(R.id.back);
        iv_1 = (ImageView) findViewById(R.id.iv_jia_xiao);
        iv_2 = (ImageView) findViewById(R.id.iv_jiao_lian);
        iv_3 = (ImageView) findViewById(R.id.iv_chang_di);
        back.setOnClickListener(this);
        iv_1.setOnClickListener(new imageListener(0));
        iv_2.setOnClickListener(new imageListener(1));
        iv_3.setOnClickListener(new imageListener(2));


    }

    public class imageListener implements View.OnClickListener {
        private int index = 0;

        public imageListener(int i) {
            index = i;
        }

        @Override
        public void onClick(View view) {
            viewPager.setCurrentItem(index);
            if (index == 0) {
                iv_1.setSelected(true);
                iv_2.setSelected(false);
                iv_3.setSelected(false);
            } else if (index == 1) {
                iv_1.setSelected(false);
                iv_2.setSelected(true);
                iv_3.setSelected(false);
            } else if (index == 2) {
                iv_1.setSelected(false);
                iv_2.setSelected(false);
                iv_3.setSelected(true);
            }
        }
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
        }
    }
}
