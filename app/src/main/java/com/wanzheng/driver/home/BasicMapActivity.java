package com.wanzheng.driver.home;


import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationClientOption.AMapLocationMode;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdate;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.LocationSource;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.CameraPosition;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.maps.model.PolylineOptions;
import com.haha.exam.R;
import com.wanzheng.driver.Entity.BaseResult;
import com.wanzheng.driver.RelatedActivity.PeriodAdapter;
import com.wanzheng.driver.network.JsonUtils;
import com.wanzheng.driver.network.NetWorkCallBack;
import com.wanzheng.driver.util.GpsEntity;
import com.wanzheng.driver.util.ToastUtil;

import java.util.List;

/**
 * AMapV2地图中介绍定位三种模式的使用，包括定位，追随，旋转
 */
public class BasicMapActivity extends Activity implements LocationSource,
        AMapLocationListener, OnCheckedChangeListener {
    private AMap aMap;
    private MapView mapView;
    private OnLocationChangedListener mListener;
    private AMapLocationClient mlocationClient;
    private AMapLocationClientOption mLocationOption;
    private List<GpsEntity> listGps;
    private PeriodAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);// 不显示程序的标题栏
        setContentView(R.layout.basicmap_activity);
        mapView = (MapView) findViewById(R.id.map);
        mapView.onCreate(savedInstanceState);// 此方法必须重写
        adapter=new PeriodAdapter();
        init();
        initView();
    }


    private void initView() {
        RelativeLayout titlebar = (RelativeLayout) findViewById(R.id.titlebar);
        TextView txt = (TextView) titlebar.findViewById(R.id.titlebar_tv);
        txt.setText("轨迹记录");
        ImageView titleR = (ImageView) titlebar.findViewById(R.id.titlebar_back);
        titleR.setVisibility(View.VISIBLE);
        titleR.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                finish();

            }
        });
    }

    /**
     * 初始化
     */
    private void init() {
        if (aMap == null) {
            aMap = mapView.getMap();
            //setUpMap();
        }
        listGps=adapter.listGps;
//        listGps = (List<GpsEntity>) getIntent().getExtras().getSerializable("GPS");
        if (listGps==null||listGps.size()==0) {
            ToastUtil.show(this, "暂无轨迹");
            return;
        }
        int size = listGps.size();
        LatLng A = new LatLng(listGps.get(0).getLat(), listGps.get(0).getLng());
        LatLng B = null;

        changeCamera(
                CameraUpdateFactory.newCameraPosition(new CameraPosition(
                        A, 18, 30, 30)));
        aMap.clear();
        aMap.addMarker(new MarkerOptions().title("起点").position(A)
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.mark_start)));
        if (size > 0) {
            B = new LatLng(listGps.get(size - 1).getLat(), listGps.get(size - 1).getLng());
            aMap.addMarker(new MarkerOptions().title("终点").position(B)
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.mark_end)));
        }


        addPolylinessoild(listGps);

    }

    /**
     * 根据动画按钮状态，调用函数animateCamera或moveCamera来改变可视区域
     */
    private void changeCamera(CameraUpdate update) {

        aMap.moveCamera(update);

    }

    /**
     * 设置一些amap的属性
     */
    private void setUpMap() {
        aMap.setLocationSource(this);// 设置定位监听
        aMap.getUiSettings().setMyLocationButtonEnabled(true);// 设置默认定位按钮是否显示
        aMap.setMyLocationEnabled(true);// 设置为true表示显示定位层并可触发定位，false表示隐藏定位层并不可触发定位，默认是false
        // 设置定位的类型为定位模式 ，可以由定位、跟随或地图根据面向方向旋转几种
        aMap.setMyLocationType(AMap.LOCATION_TYPE_LOCATE);
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId) {

        }

    }

    /**
     * 方法必须重写
     */
    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();
    }

    /**
     * 方法必须重写
     */
    @Override
    protected void onPause() {
        super.onPause();
        mapView.onPause();
        deactivate();
    }


    /**
     * 方法必须重写
     */
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }

    /**
     * 方法必须重写
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
        if (null != mlocationClient) {
            mlocationClient.onDestroy();
        }
    }

    /**
     * 定位成功后回调函数
     */
    @Override
    public void onLocationChanged(AMapLocation amapLocation) {
        if (mListener != null && amapLocation != null) {
            if (amapLocation != null
                    && amapLocation.getErrorCode() == 0) {

                mListener.onLocationChanged(amapLocation);// 显示系统小蓝点
            } else {
                String errText = "定位失败," + amapLocation.getErrorCode() + ": " + amapLocation.getErrorInfo();
                Log.e("AmapErr", errText);

            }
        }
    }

    /**
     * 激活定位
     */
    @Override
    public void activate(OnLocationChangedListener listener) {
        mListener = listener;
        if (mlocationClient == null) {
            mlocationClient = new AMapLocationClient(this);
            mLocationOption = new AMapLocationClientOption();
            //设置定位监听
            mlocationClient.setLocationListener(this);
            //设置为高精度定位模式
            mLocationOption.setLocationMode(AMapLocationMode.Hight_Accuracy);
            //设置定位参数
            mlocationClient.setLocationOption(mLocationOption);
            // 此方法为每隔固定时间会发起一次定位请求，为了减少电量消耗或网络流量消耗，
            // 注意设置合适的定位时间的间隔（最小间隔支持为2000ms），并且在合适时间调用stopLocation()方法来取消定位请求
            // 在定位结束后，在合适的生命周期调用onDestroy()方法
            // 在单次定位情况下，定位无论成功与否，都无需调用stopLocation()方法移除请求，定位sdk内部会移除
            mlocationClient.startLocation();
        }
    }

    /**
     * 停止定位
     */
    @Override
    public void deactivate() {
        mListener = null;
        if (mlocationClient != null) {
            mlocationClient.stopLocation();
            mlocationClient.onDestroy();
        }
        mlocationClient = null;
    }

    //绘制一条实线
    private void addPolylinessoild(List<GpsEntity> list) {

        PolylineOptions polylineOptions = new PolylineOptions();
        for (int i = 0; i < list.size(); i++) {
            LatLng ll = new LatLng(list.get(i).getLat(), list.get(i).getLng());
            polylineOptions.add(ll);
        }
        polylineOptions.width(15);
        polylineOptions.color(Color.BLUE);
        aMap.addPolyline(polylineOptions);

    }

    private NetWorkCallBack<BaseResult> orderCurrenCallBack2 = new NetWorkCallBack<BaseResult>() {

        @Override
        public void onComplete(String json) {
            listGps = JsonUtils.periodGpsGuiji(json);
            if (listGps == null && listGps.size() == 0) {
                return;
            }
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    LatLng f = new LatLng(listGps.get(0).getLng(), listGps.get(0).getLat());

                    changeCamera(
                            CameraUpdateFactory.newCameraPosition(new CameraPosition(
                                    f, 18, 30, 30)));
                    aMap.clear();
                    aMap.addMarker(new MarkerOptions().position(f)
                            .icon(BitmapDescriptorFactory
                                    .defaultMarker(BitmapDescriptorFactory.HUE_RED)));
//					addPolylinessoild(listGps);
                }
            });


        }
    };

}