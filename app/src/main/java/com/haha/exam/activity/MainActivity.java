package com.haha.exam.activity;

import android.app.FragmentManager;
import android.content.Intent;
import android.os.Build;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTabHost;
import android.support.v4.widget.DrawerLayout;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.google.gson.Gson;
import com.haha.exam.MyApplication;
import com.haha.exam.bean.AllCity;
import com.haha.exam.bean.AllQuestions;
import com.haha.exam.dao.ExamDao;
import com.haha.exam.fragment.HomeFragment;
import com.haha.exam.fragment.LeftFragment;
import com.haha.exam.fragment.OrderFragment;
import com.haha.exam.R;
import com.haha.exam.fragment.ToolsFragment;
import com.haha.exam.web.WebInterface;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.cache.CacheMode;
import com.lzy.okgo.callback.StringCallback;

import java.io.Serializable;
import java.util.List;

import okhttp3.Call;
import okhttp3.Response;

/**
 * 此处使用了TabHost，但是height为0，不显示，可以直接使用ViewPager也能实现该种效果
 * 但考虑到后期添加功能，因此此处暂时不做更改
 * <p/>
 * 获得当前城市
 */
public class MainActivity extends FragmentActivity implements View.OnClickListener {

    private ExamDao dao;

    private FragmentTabHost mTabHost;
    private LayoutInflater inflater;
    private Class mFragmentArray[] = {HomeFragment.class, OrderFragment.class, ToolsFragment.class};
    private int mImageArray[] = {R.drawable.tab_home, R.drawable.tab_order, R.drawable.tab_favorable};
    private String textArray[] = {"考驾照", "驾考圈", "工具"};//暂时未用到



    public static List<AllQuestions.DataBean> questions;
    private DrawerLayout drawer_layout;
    private FrameLayout fly_content;
    private View topbar;
    private LeftFragment fg_left_menu;
    private FragmentManager fManager;
    private ImageView iv_mine;
    private Gson gson;
    private AllCity allCity;
    private String city;
    //声明AMapLocationClient类对象，定位发起端
    private AMapLocationClient mLocationClient = null;
    //声明mLocationOption对象，定位参数
    public AMapLocationClientOption mLocationOption = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        gson = new Gson();
        dao=new ExamDao(MainActivity.this);
        fManager = getFragmentManager();
        fg_left_menu = (LeftFragment) fManager.findFragmentById(R.id.fg_left_menu);
        initViews();
        initData();
        //初始化定位
        mLocationClient = new AMapLocationClient(getApplicationContext());
        //设置定位回调监听
        mLocationClient.setLocationListener(mLocationListener);
        initLocation();
        setTranslucentStatus();
    }

    @Override
    public void onPause() {
        super.onPause();
        mLocationClient.stopLocation();//停止定位后，本地定位服务并不会被销毁
        mLocationClient.onDestroy();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mLocationClient.onDestroy();//销毁定位客户端，同时销毁本地定位服务。

    }

    private void initLocation() {
//        mLocationClient = new AMapLocationClient(getApplicationContext());
        //初始化AMapLocationClientOption对象
        mLocationOption = new AMapLocationClientOption();
        //设置定位回调监听
        mLocationClient.setLocationListener(mLocationListener);
        //设置定位模式为AMapLocationMode.Hight_Accuracy，高精度模式。
        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        //获取一次定位结果：
//该方法默认为false。
        mLocationOption.setOnceLocation(true);
        //设置是否强制刷新WIFI，默认为true，强制刷新。
        mLocationOption.setWifiActiveScan(false);
        //给定位客户端对象设置定位参数
        mLocationClient.setLocationOption(mLocationOption);

//启动定位
        mLocationClient.startLocation();
    }

    /**
     * 声明定位回调监听器
     */
    public AMapLocationListener mLocationListener = new AMapLocationListener() {
        @Override
        public void onLocationChanged(AMapLocation amapLocation) {
            if (amapLocation != null) {
                if (amapLocation.getErrorCode() == 0) {
                    //定位成功回调信息，设置相关消息
//                    amapLocation.getLocationType();//获取当前定位结果来源，如网络定位结果，详见定位类型表
//                    amapLocation.getLatitude();//获取纬度
//                    amapLocation.getLongitude();//获取经度
//                    amapLocation.getAccuracy();//获取精度信息
//                    amapLocation.getAddress();//地址，如果option中设置isNeedAddress为false，则没有此结果，网络定位结果中会有地址信息，GPS定位不返回地址信息。
//                    amapLocation.getCountry();//国家信息
//                    amapLocation.getProvince();//省信息
                    city = amapLocation.getCity();//城市信息
                    System.out.println("city=========" + city + amapLocation.getDistrict());
//                    amapLocation.getDistrict();//城区信息
//                    amapLocation.getStreet();//街道信息
//                    amapLocation.getStreetNum();//街道门牌号信息
//                    amapLocation.getCityCode();//城市编码
//                    amapLocation.getAdCode();//地区编码
//                    amapLocation.getAoiName();//获取当前定位点的AOI信息

                } else {
                    //显示错误信息ErrCode是错误码，errInfo是错误信息，详见错误码表。
                    Log.e("AmapError", "location Error, ErrCode:"
                            + amapLocation.getErrorCode() + ", errInfo:"
                            + amapLocation.getErrorInfo());
                }
            }
        }
    };


    @Override
    protected void onStop() {
        super.onStop();
        mLocationClient.stopLocation();//停止定位
    }

    //      获取城市列表，利用定位得到城市id
    private void initData() {
        OkGo.get(WebInterface.all_citys)
                .tag(this)
                .cacheKey("all_citys")
                .cacheMode(CacheMode.IF_NONE_CACHE_REQUEST)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {

                        allCity = gson.fromJson(s, AllCity.class);
                        System.out.println("数据请求成功");
                    }

                    @Override
                    public void onCacheSuccess(String s, Call call) {
                        super.onCacheSuccess(s, call);
                        if (allCity == null) {
                            allCity = gson.fromJson(s, AllCity.class);
                            System.out.println(allCity.getMsg().size());
                            System.out.println("读取缓存数据成功");
                        }

                    }
                });




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

    private void initViews() {
        drawer_layout = (DrawerLayout) findViewById(R.id.drawer_layout);
        fly_content = (FrameLayout) findViewById(R.id.fly_content);
        topbar = findViewById(R.id.topbar);
        iv_mine = (ImageView) topbar.findViewById(R.id.mine);
        fg_left_menu.setDrawerLayout(drawer_layout);

        inflater = LayoutInflater.from(this);
        mTabHost = (FragmentTabHost) findViewById(R.id.tabhost);

        mTabHost.setup(this, getSupportFragmentManager(), R.id.fly_content);
        mTabHost.getTabWidget().setDividerDrawable(R.color.white);//去掉tabhost的分割线


        iv_mine.setOnClickListener(this);

        int count = mFragmentArray.length;
        for (int i = 0; i < count; i++) {
            TabHost.TabSpec tabSpec = mTabHost.newTabSpec(textArray[i]).setIndicator(getTabItemView(i));
            mTabHost.addTab(tabSpec, mFragmentArray[i], null);
        }
        updateTab(mTabHost);
        mTabHost.setOnTabChangedListener(tabChangeListener);
    }

    private TabHost.OnTabChangeListener tabChangeListener = new TabHost.OnTabChangeListener() {
        @Override
        public void onTabChanged(String s) {
            updateTab(mTabHost);
        }
    };

    //          更新tab键文字
    private void updateTab(FragmentTabHost mTabHost) {
        for (int i = 0; i < mTabHost.getTabWidget().getChildCount(); i++) {
            View view = mTabHost.getTabWidget().getChildAt(i);
            TextView tv = (TextView) mTabHost.getTabWidget().getChildAt(i).findViewById(R.id.textview);
            if (mTabHost.getCurrentTab() == i) {//选中
                tv.setTextColor(this.getResources().getColorStateList(
                        R.color.orange));
            } else {//不选中
                tv.setTextColor(this.getResources().getColorStateList(
                        android.R.color.darker_gray));
            }
        }
    }

    //      为tab键设置图片和文字
    private View getTabItemView(int index) {
        View view = inflater.inflate(R.layout.tab_item_view, null);
        ImageView imageView = (ImageView) view.findViewById(R.id.imageview);
        TextView textView = (TextView) view.findViewById(R.id.textview);
        imageView.setImageResource(mImageArray[index]);
        textView.setText(textArray[index]);
        return view;

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.mine:
                drawer_layout.openDrawer(Gravity.LEFT);
                break;
        }
    }


}
