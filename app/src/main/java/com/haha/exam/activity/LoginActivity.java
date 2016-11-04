package com.haha.exam.activity;

import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import com.haha.exam.R;
import com.haha.exam.adapter.MyFragmentPagerAdapter;
import com.haha.exam.fragment.HomeFragment;
import com.haha.exam.fragment.PasswordLoginFragment;
import com.haha.exam.fragment.YanZhengLoginFragment;

import java.util.ArrayList;

/**
 * 登录界面
 */
public class LoginActivity extends FragmentActivity {

    private ImageView back;
    private TextView yanzheng, mima;
    private ViewPager mViewPager;
    private View tabLine;
    private Animation animation;
    private ArrayList<Fragment> fragmentList;

    private int currIndex = 0;
    private int bottomLineWidth;
    private int offset = 0;
    private int position_one;
    private int position_two;
    private int position_three;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initAdapter();
        initView();
        initWidth();
        setTranslucentStatus();

    }

    private void initAdapter() {
        fragmentList = new ArrayList<>();

        YanZhengLoginFragment yanzhengFragment = new YanZhengLoginFragment();
        PasswordLoginFragment passwordFragment = new PasswordLoginFragment();
        fragmentList.add(yanzhengFragment);
        fragmentList.add(passwordFragment);

    }

    private void initView() {
        back = (ImageView) findViewById(R.id.back);
        yanzheng = (TextView) findViewById(R.id.tv_yanzheng);
        mima = (TextView) findViewById(R.id.tv_mima);
        mima.setAlpha(50);
        yanzheng.setAlpha(100);
        //初始化ViewPager，并且设置ViewPager的监听器
        mViewPager = (ViewPager) this.findViewById(R.id.pager);
        mViewPager.setCurrentItem(0);
        mViewPager.setAdapter(new MyFragmentPagerAdapter(getSupportFragmentManager(), fragmentList));//解决fragment嵌套问题

        mViewPager.setOnPageChangeListener(new MyOnPageChangeListener());

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        yanzheng.setOnClickListener(new MyOnClickListener(0));
        mima.setOnClickListener(new MyOnClickListener(1));
    }

    /**
     * 设置状态栏背景状态
     */
    private void setTranslucentStatus() {
        //判断当前SDK版本号，如果是4.4以上，就是支持沉浸式状态栏的
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);

        }

    }

    public class MyOnPageChangeListener implements ViewPager.OnPageChangeListener {

        @Override
        public void onPageSelected(int arg0) {

            switch (arg0) {
                case 0:
                    if (currIndex == 1) {
                        animation = new TranslateAnimation(position_one, 0, 0, 0);
                    } else if (currIndex == 2) {
                        animation = new TranslateAnimation(position_two, 0, 0, 0);
                    } else if (currIndex == 3) {
                        animation = new TranslateAnimation(position_three, 0, 0, 0);
                    }
                    break;
                case 1:
                    if (currIndex == 0) {
                        animation = new TranslateAnimation(offset, position_one, 0, 0);
                    } else if (currIndex == 2) {
                        animation = new TranslateAnimation(position_two, position_one, 0, 0);
                    } else if (currIndex == 3) {
                        animation = new TranslateAnimation(position_three, position_one, 0, 0);
                    }
                    break;
//                case 2:
//                    if (currIndex == 0) {
//                        animation = new TranslateAnimation(offset, position_two, 0, 0);
//                    } else if (currIndex == 1) {
//                        animation = new TranslateAnimation(position_one, position_two, 0, 0);
//                    } else if (currIndex == 3) {
//                        animation = new TranslateAnimation(position_three, position_two, 0, 0);
//                    }
//                    break;
//                case 3:
//                    if (currIndex == 0) {
//                        animation = new TranslateAnimation(offset, position_three, 0, 0);
//                    } else if (currIndex == 1) {
//                        animation = new TranslateAnimation(position_one, position_three, 0, 0);
//                    } else if (currIndex == 2) {
//                        animation = new TranslateAnimation(position_two, position_three, 0, 0);
//                    }
//                    break;
            }
            currIndex = arg0;
            animation.setFillAfter(true);
            animation.setDuration(100);
            tabLine.startAnimation(animation);
        }

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        }

        @Override
        public void onPageScrollStateChanged(int arg0) {
        }
    }

    private void initWidth() {
        tabLine = this.findViewById(R.id.tab_line);
        bottomLineWidth = tabLine.getLayoutParams().width;
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int screenW = dm.widthPixels;
        offset = (int) ((screenW / 2 - bottomLineWidth) / 2);

        position_one = (int) (screenW / 2.0);
        position_two = position_one * 2;
        position_three = position_one * 3;
    }

    public class MyOnClickListener implements View.OnClickListener {
        private int index = 0;

        public MyOnClickListener(int i) {
            index = i;
        }

        @Override
        public void onClick(View v) {
            mViewPager.setCurrentItem(index);
        }
    }

}
