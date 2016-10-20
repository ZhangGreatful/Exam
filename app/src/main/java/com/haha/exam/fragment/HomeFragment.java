package com.haha.exam.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.TextView;

import com.haha.exam.adapter.MyFragmentPagerAdapter;
import com.haha.exam.R;

import java.util.ArrayList;

/**
 * 考驾照页面
 * Created by Administrator on 2016/10/19.
 */
public class HomeFragment extends Fragment {

    private Context mContext;
    private ArrayList<Fragment> fragmentList;
    private Fragment keyiFragment, kesiFragment;
    private TextView tab1Text, tab2Text;
    private ViewPager mViewPager;
    private View tabLine;
    private Animation animation;

    private int currIndex = 0;
    private int bottomLineWidth;
    private int offset = 0;
    private int position_one;
    private int position_two;
    private int position_three;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.home_fragment, container, false);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mContext = getActivity();
        initAdapter();
        initView();

        initWidth();
    }

    private void initView() {
        tab1Text = (TextView) this.getActivity().findViewById(R.id.tab1);
        tab2Text = (TextView) this.getActivity().findViewById(R.id.tab2);


        tab1Text.setOnClickListener(new MyOnClickListener(0));
        tab2Text.setOnClickListener(new MyOnClickListener(1));

        //初始化ViewPager，并且设置ViewPager的监听器
        mViewPager = (ViewPager) this.getActivity().findViewById(R.id.pager);
        mViewPager.setCurrentItem(0);
        mViewPager.setAdapter(new MyFragmentPagerAdapter(getChildFragmentManager(), fragmentList));//解决fragment嵌套问题

        mViewPager.setOnPageChangeListener(new MyOnPageChangeListener());
    }

    private void initAdapter() {
        fragmentList = new ArrayList<>();
        keyiFragment = new KeYiFragment();
        kesiFragment = new KeSiFragment();
        fragmentList.add(keyiFragment);
        fragmentList.add(kesiFragment);
    }

    private void initWidth() {
        tabLine = this.getActivity().findViewById(R.id.tab_line);
        bottomLineWidth = tabLine.getLayoutParams().width;
        DisplayMetrics dm = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);
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
}
