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
import android.widget.TextView;

import com.haha.exam.R;
import com.haha.exam.adapter.MyFragmentPagerAdapter;
import com.haha.exam.dialog.EndTimingDialog;
import com.haha.exam.dialog.MyDialog;
import com.haha.exam.utils.SPUtils;
import com.wanzheng.driver.home.FaceLogin;
import com.wanzheng.driver.util.SystemUtil;

import java.util.ArrayList;

import static com.haha.exam.R.id.date;

/**
 * 考驾照页面
 * Created by Administrator on 2016/10/19.
 */
public class HomeFragment extends Fragment implements View.OnClickListener {

    private SystemUtil su;
    private int check_success;
    private String phone;
    private Context mContext;
    private ArrayList<Fragment> fragmentList;
    private Fragment keyiFragment, keerFragment, kesanFragment, kesiFragment;
    private TextView tab1Text, tab2Text, tab3Text, tab4Text;
    private ViewPager mViewPager;
    private View tabLine;
    private Animation animation;
    private SPUtils spUtils = new SPUtils();
//    private TextView start_Timing;


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
        su = new SystemUtil(mContext);
        phone = su.showPhone();
        initAdapter();
        initView();

        initWidth();
    }

    private void initView() {
        tab1Text = (TextView) this.getActivity().findViewById(R.id.tab1);
        tab2Text = (TextView) this.getActivity().findViewById(R.id.tab2);
//        tab3Text = (TextView) this.getActivity().findViewById(R.id.tab3);
//        tab4Text = (TextView) this.getActivity().findViewById(R.id.tab4);
//        start_Timing = (TextView) this.getActivity().findViewById(R.id.start_timing);


        tab1Text.setOnClickListener(new MyOnClickListener(0));
        tab2Text.setOnClickListener(new MyOnClickListener(1));
////        tab3Text.setOnClickListener(new MyOnClickListener(2));
//        tab4Text.setOnClickListener(new MyOnClickListener(3));
//        start_Timing.setOnClickListener(this);


        //初始化ViewPager，并且设置ViewPager的监听器
        mViewPager = (ViewPager) this.getActivity().findViewById(R.id.pager);
        mViewPager.setCurrentItem(0);
        spUtils.put(getActivity(), "subject", "one");
        spUtils.put(getActivity(), "subject0", "1");
        mViewPager.setAdapter(new MyFragmentPagerAdapter(getChildFragmentManager(), fragmentList));//解决fragment嵌套问题

        mViewPager.setOnPageChangeListener(new MyOnPageChangeListener());
    }

    @Override
    public void onResume() {
        super.onResume();
//        check_success = su.showTiming();
//        if (check_success == 1) {
//            start_Timing.setText("正在计时");
//        } else {
//            start_Timing.setText("开始计时");
//        }
    }

    private void initAdapter() {
        fragmentList = new ArrayList<>();
        keyiFragment = new KeYiFragment();
        kesiFragment = new KeSiFragment();
//        keerFragment = new KeErFragment();
//        kesanFragment = new KeSanFragment();

        fragmentList.add(keyiFragment);
        fragmentList.add(kesiFragment);
//        fragmentList.add(keerFragment);
//        fragmentList.add(kesanFragment);

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

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.start_timing:

                if (check_success == 0 || check_success == 2) {//未登录状态
                    final MyDialog dialog = new MyDialog(getActivity());
                    dialog.setMessage("是否开始计时？");
                    dialog.setNoMessage("不计时");
                    dialog.setYesMessage("计时");
                    dialog.show();
                    dialog.setYesOnclickListener("", new MyDialog.onYesOnclickListener() {
                        @Override
                        public void onYesClick() {
                            Intent intent = new Intent();
                            intent.putExtra("phone", phone);
                            intent.putExtra("from", 1);
                            intent.setClass(getActivity(), FaceLogin.class);
                            startActivity(intent);
                            dialog.dismiss();
                        }
                    });
                    dialog.setNoOnclickListener("", new MyDialog.onNoOnclickListener() {
                        @Override
                        public void onNoClick() {
                            dialog.dismiss();
                            su.saveTiming(0);// 0  不计时模式
                        }
                    });
                } else {//登录状态
//                    获取本次学时信息
//
//                    int time = su.showLearnedTime();
//                    Log.d("HomeFragment", "获取学时时间===========" + String.valueOf(time));
//                    String learnedTime = formatNumberToHourMinuteSecond(time);
//                    Log.d("HomeFragment", "learnedTime============" + learnedTime);
                    final EndTimingDialog dialog = new EndTimingDialog(getActivity());
                    String time = formatNumberToHourMinuteSecond(su.showLearnedTime());
                    dialog.setTime(time);
//                    dialog.setTime("是否结束计时？");
                    dialog.setNoMessage("继续训练");
                    dialog.setYesMessage("结束计时");
                    dialog.show();
                    dialog.setYesOnclickListener(new EndTimingDialog.onYesOnclickListener() {
                        @Override
                        public void onYesClick() {
                            Intent intent = new Intent();
                            intent.putExtra("phone", phone);
                            intent.putExtra("from", 1);
                            intent.setClass(getActivity(), FaceLogin.class);
                            startActivity(intent);
                            dialog.dismiss();

                        }
                    });
                    dialog.setNoOnclickListener(new EndTimingDialog.onNoOnclickListener() {
                        @Override
                        public void onNoClick() {
                            dialog.dismiss();

                        }
                    });
                }


                break;
        }
    }

    public String formatNumberToHourMinuteSecond(int dateDou) {
        String ft = "00:00:00";//没匹配上时:1.等于0时; 2.大于等于86400时.
//        BigDecimal d = new BigDecimal(dateDou).setScale(0, BigDecimal.ROUND_HALF_UP);//四舍五入
//        int date = Integer.valueOf(d.toString());
        if (dateDou > 0 && dateDou < 60) {
            ft = "00:00:" + (dateDou >= 10 ? dateDou : "0" + dateDou);
        } else if (dateDou >= 60 && dateDou < 3600) {
            ft = "00:" + (dateDou / 60 >= 10 ? dateDou / 60 : "0" + dateDou / 60) + ":" + (dateDou % 60 >= 10 ? dateDou % 60 : "0" + dateDou % 60);
        } else if (dateDou >= 3600 && dateDou < 86400) {
            ft = (dateDou / 3600 >= 10 ? dateDou / 3600 : "0" + dateDou / 3600) + ":" + (dateDou % 3600 / 60 >= 10 ? dateDou % 3600 / 60 : "0" + dateDou % 3600 / 60) + ":" + (date % 60 >= 10 ? date % 60 : "0" + date % 60);
        }
        return ft;
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
                    spUtils.put(getActivity(), "subject", "one");
                    spUtils.put(getActivity(), "subject0", "1");
                    if (currIndex == 1) {
                        animation = new TranslateAnimation(position_one, 0, 0, 0);
                    } else if (currIndex == 2) {
                        animation = new TranslateAnimation(position_two, 0, 0, 0);
                    } else if (currIndex == 3) {
                        animation = new TranslateAnimation(position_three, 0, 0, 0);
                    }
                    break;
                case 1:
                    spUtils.put(getActivity(), "subject", "four");
                    spUtils.put(getActivity(), "subject0", "4");
//                    spUtils.put(getActivity(), "subject", "two");
//                    spUtils.put(getActivity(), "subject0", "2");
                    if (currIndex == 0) {
                        animation = new TranslateAnimation(offset, position_one, 0, 0);
                    } else if (currIndex == 2) {
                        animation = new TranslateAnimation(position_two, position_one, 0, 0);
                    } else if (currIndex == 3) {
                        animation = new TranslateAnimation(position_three, position_one, 0, 0);
                    }
                    break;
//                case 2:
//                    spUtils.put(getActivity(), "subject", "three");
//                    spUtils.put(getActivity(), "subject0", "3");
//                    if (currIndex == 0) {
//                        animation = new TranslateAnimation(offset, position_two, 0, 0);
//                    } else if (currIndex == 1) {
//                        animation = new TranslateAnimation(position_one, position_two, 0, 0);
//                    } else if (currIndex == 3) {
//                        animation = new TranslateAnimation(position_three, position_two, 0, 0);
//                    }
//                    break;
//                case 3:
//                    spUtils.put(getActivity(), "subject", "four");
//                    spUtils.put(getActivity(), "subject0", "4");
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
