package com.haha.exam.fragment;


import android.content.Context;
import android.content.Intent;
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
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.haha.exam.R;
import com.haha.exam.activity.ExaminationActivity;
import com.haha.exam.activity.HomeActivity;
import com.haha.exam.activity.RuleActivity;
import com.haha.exam.adapter.MyFragmentPagerAdapter;
import com.wanzheng.driver.home.Activity_OrderShow;
import com.wanzheng.driver.home.Activity_QRcode;

import java.util.ArrayList;

/**
 * 首页界面
 * Created by Administrator on 2016/11/19.
 */
public class HomaPageFragment extends Fragment implements View.OnClickListener {

    private ArrayList<Fragment> fragmentList;
    private Fragment subjectOneFragment, subjectTwoFragment, subjectThreeFragment, subjectFourFragment;
    private TextView tab1Text, tab2Text, tab3Text, tab4Text;
    private ViewPager mViewPager;
    private View tabLine;
    private Animation animation;
    private Context mContext;
    private int currIndex = 0;
    private int bottomLineWidth;
    private int offset = 0;
    private int position_one;
    private int position_two;
    private int position_three;
    private LinearLayout ding_dan, sui_xue_sao_miao, jiao_xue_shi_pin, yu_yue, yu_yue_sao_ma, li_lun_xue_shi,
            xue_che_zi_xun, zai_xian_yue_kao;

    private OnButtonClick onButtonClick;//2、定义接口成员变量
    private HomeActivity homeActivity;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.home_page_fragment, container, false);
        jiao_xue_shi_pin = (LinearLayout) view.findViewById(R.id.jiao_xue_shi_pin);
        yu_yue = (LinearLayout) view.findViewById(R.id.yu_yue);
        yu_yue_sao_ma = (LinearLayout) view.findViewById(R.id.yu_yue_sao_ma);
        li_lun_xue_shi = (LinearLayout) view.findViewById(R.id.li_lun_xue_shi);
        xue_che_zi_xun = (LinearLayout) view.findViewById(R.id.xue_che_zi_xun);
        zai_xian_yue_kao = (LinearLayout) view.findViewById(R.id.zai_xian_yue_kao);
        ding_dan = (LinearLayout) view.findViewById(R.id.ding_dan);
        sui_xue_sao_miao = (LinearLayout) view.findViewById(R.id.sui_xue_sao_ma);
        tab1Text = (TextView) view.findViewById(R.id.tab1);
        tab2Text = (TextView) view.findViewById(R.id.tab2);
        tab3Text = (TextView) view.findViewById(R.id.tab3);
        tab4Text = (TextView) view.findViewById(R.id.tab4);
        mViewPager = (ViewPager) view.findViewById(R.id.pager);
        tabLine = view.findViewById(R.id.tab_line);

        return view;
    }

    //定义接口变量的get方法
    public OnButtonClick getOnButtonClick() {
        return onButtonClick;
    }

    //定义接口变量的set方法
    public void setOnButtonClick(OnButtonClick onButtonClick) {
        this.onButtonClick = onButtonClick;
    }

    //1、定义接口
    public interface OnButtonClick {
        public void onClick(View view);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ding_dan:
                Intent intent = new Intent(getActivity(), Activity_OrderShow.class);
                mContext.startActivity(intent);
                break;
            case R.id.sui_xue_sao_ma:
                Intent intent1 = new Intent(getActivity(), Activity_QRcode.class);
                startActivity(intent1);
                break;
            case R.id.jiao_xue_shi_pin:
                homeActivity.mTabHost.setCurrentTab(1);
                break;
            case R.id.yu_yue:
//                Intent intent3 = new Intent(getActivity(), CoachActivity.class);
//                startActivity(intent3);
                Toast.makeText(mContext, "敬请期待", Toast.LENGTH_SHORT).show();
                break;
            case R.id.yu_yue_sao_ma:
                Intent intent4 = new Intent(getActivity(), Activity_QRcode.class);
                startActivity(intent4);
                break;
            case R.id.li_lun_xue_shi:
                homeActivity.mTabHost.setCurrentTab(2);
                break;
            case R.id.xue_che_zi_xun:
                Intent intent6 = new Intent(getActivity(), ExaminationActivity.class);
                startActivity(intent6);
                break;
            case R.id.zai_xian_yue_kao:
                Intent intent7 = new Intent(getActivity(), RuleActivity.class);
                intent7.putExtra("from", 2);
                startActivity(intent7);
                break;
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        homeActivity = new HomeActivity();
        mContext = getActivity();
        ding_dan.setOnClickListener(this);
        sui_xue_sao_miao.setOnClickListener(this);
        jiao_xue_shi_pin.setOnClickListener(this);
        li_lun_xue_shi.setOnClickListener(this);
        yu_yue_sao_ma.setOnClickListener(this);
        yu_yue.setOnClickListener(this);
        xue_che_zi_xun.setOnClickListener(this);
        zai_xian_yue_kao.setOnClickListener(this);
        initAdapter();
        initView();

        initWidth();
    }

    private void initView() {


        tab1Text.setOnClickListener(new HomaPageFragment.MyOnClickListener(0));
        tab2Text.setOnClickListener(new HomaPageFragment.MyOnClickListener(1));
        tab3Text.setOnClickListener(new HomaPageFragment.MyOnClickListener(2));
        tab4Text.setOnClickListener(new HomaPageFragment.MyOnClickListener(3));


        //初始化ViewPager，并且设置ViewPager的监听器

        mViewPager.setCurrentItem(0);
        mViewPager.setAdapter(new MyFragmentPagerAdapter(getChildFragmentManager(), fragmentList));//解决fragment嵌套问题

        mViewPager.setOnPageChangeListener(new HomaPageFragment.MyOnPageChangeListener());
    }

    private void initAdapter() {
        fragmentList = new ArrayList<>();
        subjectOneFragment = new SubjectOneFragment();
        subjectTwoFragment = new SubjectTwoFragment();
        subjectThreeFragment = new SubjectThreeFragment();
        subjectFourFragment = new SubjectFourFragment();

        fragmentList.add(subjectOneFragment);
        fragmentList.add(subjectTwoFragment);
        fragmentList.add(subjectThreeFragment);
        fragmentList.add(subjectFourFragment);
    }

    private void initWidth() {

        bottomLineWidth = tabLine.getLayoutParams().width;
        DisplayMetrics dm = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);
        int screenW = dm.widthPixels;
        offset = (int) ((screenW / 4 - bottomLineWidth) / 2);

        position_one = (int) (screenW / 4.0);
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
                case 2:
                    if (currIndex == 0) {
                        animation = new TranslateAnimation(offset, position_two, 0, 0);
                    } else if (currIndex == 1) {
                        animation = new TranslateAnimation(position_one, position_two, 0, 0);
                    } else if (currIndex == 3) {
                        animation = new TranslateAnimation(position_three, position_two, 0, 0);
                    }
                    break;
                case 3:
                    if (currIndex == 0) {
                        animation = new TranslateAnimation(offset, position_three, 0, 0);
                    } else if (currIndex == 1) {
                        animation = new TranslateAnimation(position_one, position_three, 0, 0);
                    } else if (currIndex == 2) {
                        animation = new TranslateAnimation(position_two, position_three, 0, 0);
                    }
                    break;
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
