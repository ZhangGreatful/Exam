package com.haha.exam.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.TextView;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.haha.exam.R;
import com.haha.exam.adapter.MyFragmentPagerAdapter;
import com.haha.exam.fragment.Ranking1Fragment;
import com.haha.exam.fragment.RankingFragment;
import com.haha.exam.utils.SPUtils;
import com.wanzheng.driver.util.SystemUtil;

import java.util.ArrayList;

/**
 * 排行榜
 */
public class RankingActivity extends BaseActivity {

    private Context mContext;
    private SystemUtil su;
    private SPUtils spUtils = new SPUtils();
    private String city;
    private ArrayList<Fragment> fragmentList;
    private Fragment rankingFragment, ranking1Fragment;
    private TextView tab1Text, tab2Text;
    private ViewPager mViewPager;
    private View tabLine;
    private Animation animation;

    //声明AMapLocationClient类对象，定位发起端
    private AMapLocationClient mLocationClient = null;
    //    //声明mLocationOption对象，定位参数
    public AMapLocationClientOption mLocationOption = null;

    private int currIndex = 0;
    private int bottomLineWidth;
    private int offset = 0;
    private int position_one;
    private int position_two;
    private int position_three;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        isConnect(this);
        su = new SystemUtil(this);
        city = su.showCity();
        setTitle("排行榜");
        setTitleColor(getResources().getColor(R.color.title_color));
        setTitlebarBackground(R.color.white);
        setLeftBtnDrawable();
        initAdapter();
        initView();
        initWidth();
        //初始化定位
        mLocationClient = new AMapLocationClient(getApplicationContext());
//        //设置定位回调监听
        mLocationClient.setLocationListener(mLocationListener);
        initLocation();
    }

    private void initLocation() {
        //初始化AMapLocationClientOption对象
        mLocationOption = new AMapLocationClientOption();
        //设置定位回调监听
        mLocationClient.setLocationListener(mLocationListener);
        //设置定位模式为AMapLocationMode.Hight_Accuracy，高精度模式。
        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        //该方法默认为false。
        mLocationOption.setOnceLocation(true);
        //设置是否强制刷新WIFI，默认为true，强制刷新。
        mLocationOption.setWifiActiveScan(false);
        //给定位客户端对象设置定位参数
        mLocationClient.setLocationOption(mLocationOption);
        //启动定位
        mLocationClient.startLocation();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mLocationClient.stopLocation();//停止定位

    }

    @Override
    public void onPause() {
        super.onPause();
        mLocationClient.stopLocation();//停止定位后，本地定位服务并不会被销毁

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mLocationClient.onDestroy();//销毁定位客户端，同时销毁本地定位服务。

    }

    /**
     * //     * 声明定位回调监听器
     * //
     */
    public AMapLocationListener mLocationListener = new AMapLocationListener() {
        @Override
        public void onLocationChanged(AMapLocation amapLocation) {
            if (amapLocation != null) {
                if (amapLocation.getErrorCode() == 0) {
                    String curCity = amapLocation.getCity();
                    spUtils.put(RankingActivity.this, "cityname", curCity);
                    if (city.equals("")){
                        tab1Text.setText(curCity);
                    }
                } else {
                    //显示错误信息ErrCode是错误码，errInfo是错误信息，详见错误码表。
                    tab1Text.setText("本地");
                    Log.e("AmapError", "location Error, ErrCode:"
                            + amapLocation.getErrorCode() + ", errInfo:"
                            + amapLocation.getErrorInfo());
                }
            }
        }
    };

    @Override
    protected int getContentView() {
        return R.layout.activity_ranking;
    }

    private void initView() {
        tab1Text = (TextView) findViewById(R.id.tab1);
        tab2Text = (TextView) findViewById(R.id.tab2);
        tab1Text.setText(city);


        tab1Text.setOnClickListener(new MyOnClickListener(0));
        tab2Text.setOnClickListener(new MyOnClickListener(1));

        //初始化ViewPager，并且设置ViewPager的监听器
        mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPager.setCurrentItem(0);
        mViewPager.setAdapter(new MyFragmentPagerAdapter(getSupportFragmentManager(), fragmentList));//解决fragment嵌套问题

        mViewPager.setOnPageChangeListener(new MyOnPageChangeListener());
    }

    private void initAdapter() {
        fragmentList = new ArrayList<>();
        rankingFragment = new RankingFragment();
        ranking1Fragment = new Ranking1Fragment();
        fragmentList.add(rankingFragment);
        fragmentList.add(ranking1Fragment);
    }

    private void initWidth() {
        tabLine = findViewById(R.id.tab_line);
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
