package com.haha.exam.activity;

import android.app.FragmentManager;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTabHost;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TextView;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.google.gson.Gson;
import com.haha.exam.R;
import com.haha.exam.bean.AllCity;
import com.haha.exam.bean.AllQuestions;
import com.haha.exam.dao.DatabaseHelper;
import com.haha.exam.dao.ExamDao;
import com.haha.exam.fragment.HomeFragment;
import com.haha.exam.fragment.LeftFragment;
import com.haha.exam.fragment.OrderFragment;
import com.haha.exam.fragment.ToolsFragment;
import com.lzy.okgo.OkGo;

import java.util.List;

/**
 * 此处使用了TabHost，但是height为0，不显示，可以直接使用ViewPager也能实现该种效果
 * 但考虑到后期添加功能，因此此处暂时不做更改
 * <p/>
 * 获得当前城市
 */
public class MainActivity extends FragmentActivity implements View.OnClickListener {

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
    private ExamDao dao;
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dao = new ExamDao(MainActivity.this);
        gson = new Gson();
        fManager = getFragmentManager();
        fg_left_menu = (LeftFragment) fManager.findFragmentById(R.id.fg_left_menu);
        initViews();
        initData();
        //初始化定位
        mLocationClient = new AMapLocationClient(getApplicationContext());
        //设置定位回调监听q
        mLocationClient.setLocationListener(mLocationListener);
        initLocation();
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
        OkGo.getInstance().cancelTag(this);

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

    /**
     * 声明定位回调监听器
     */
    public AMapLocationListener mLocationListener = new AMapLocationListener() {
        @Override
        public void onLocationChanged(AMapLocation amapLocation) {
            if (amapLocation != null) {
                if (amapLocation.getErrorCode() == 0) {
                    city = amapLocation.getCity();//城市信息
                    System.out.println("city=========" + city + amapLocation.getDistrict());

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

//        判断数据库所有题目是否为空，若为空，则加载数据，不为空，则不加载数据
        dbHelper = new DatabaseHelper(MainActivity.this);
//        if (dao.exits("xc") == true) {
//            int count = dao.getContactsCount("xc");
//            System.out.println("数据库共有数据==========" + count);
//            if (count == 0) {
//                String url = WebInterface.all_questions + "/cartype/" + "xc" + "/subject/" + "1";
//                OkGo.post(url)
//                        .tag(this)
//                        .cacheMode(CacheMode.DEFAULT)
//                        .execute(new StringCallback() {
//                            @Override
//                            public void onSuccess(String s, Call call, Response response) {
//                                Toast.makeText(MainActivity.this, "成功获取所有问题", Toast.LENGTH_SHORT).show();
//                                gson = new Gson();
//                                AllQuestions allQuestions = gson.fromJson(s, AllQuestions.class);
//                                dao.addAllQuestions(allQuestions, "xc");
//                                List<AllQuestions.DataBean> datas = allQuestions.getData();
//                                System.out.print("datas  size=========" + datas.size());
//                            }
//
////                    @Override
////                    public void onCacheSuccess(String s, Call call) {
////                        super.onCacheSuccess(s, call);
////                        gson = new Gson();
////                        AllQuestions allQuestions = gson.fromJson(s, AllQuestions.class);
////                        dao.addAllQuestions(allQuestions);
////                        List<AllQuestions.DataBean> datas = allQuestions.getData();
////                        System.out.print("datas  size=========" + datas.size());
////                        count.setText(String.valueOf(datas.size()));
//////        Log.i("data.size=", "" + datas.size());
////
////                        if (layoutAdapter != null) {
////                            layoutAdapter.setDataList(datas);
////                        }
////
////                        if (topicAdapter != null) {
////                            topicAdapter.setDataNum(datas.size());
////                        }
////                    }
//                        });
//            }
//        } else {
//            System.out.println("表不存在，获取网络数据，添加信息到数据库");
//            String url = WebInterface.all_questions + "/cartype/" + "xc" + "/subject/" + "1";
//            OkGo.post(url)
//                    .tag(this)
//                    .cacheMode(CacheMode.DEFAULT)
//                    .execute(new StringCallback() {
//                        @Override
//                        public void onSuccess(String s, Call call, Response response) {
//                            Toast.makeText(MainActivity.this, "成功获取所有问题", Toast.LENGTH_SHORT).show();
//                            gson = new Gson();
//                            AllQuestions allQuestions = gson.fromJson(s, AllQuestions.class);
//                            dao.addAllQuestions(allQuestions, "xc");
//                            List<AllQuestions.DataBean> datas = allQuestions.getData();
//                            System.out.print("datas  size=========" + datas.size());
//                        }
//
////                    @Override
////                    public void onCacheSuccess(String s, Call call) {
////                        super.onCacheSuccess(s, call);
////                        gson = new Gson();
////                        AllQuestions allQuestions = gson.fromJson(s, AllQuestions.class);
////                        dao.addAllQuestions(allQuestions);
////                        List<AllQuestions.DataBean> datas = allQuestions.getData();
////                        System.out.print("datas  size=========" + datas.size());
////                        count.setText(String.valueOf(datas.size()));
//////        Log.i("data.size=", "" + datas.size());
////
////                        if (layoutAdapter != null) {
////                            layoutAdapter.setDataList(datas);
////                        }
////
////                        if (topicAdapter != null) {
////                            topicAdapter.setDataNum(datas.size());
////                        }
////                    }
//                    });
//        }
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
        ImageView imageView = (ImageView) view.findViewById(R.id.imageview_1);
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
