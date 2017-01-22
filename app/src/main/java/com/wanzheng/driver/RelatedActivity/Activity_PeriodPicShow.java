package com.wanzheng.driver.RelatedActivity;


import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.haha.exam.R;
import com.wanzheng.driver.Entity.BaseResult;
import com.wanzheng.driver.network.JsonUtils;
import com.wanzheng.driver.network.NetInterface;
import com.wanzheng.driver.network.NetWorkCallBack;
import com.wanzheng.driver.network.NetWorkUtils;
import com.wanzheng.driver.order.CustomListView;

import java.util.ArrayList;
import java.util.List;


public class Activity_PeriodPicShow extends Activity implements OnClickListener {
    private String xueshiID;
    private CustomListView listviewPass,listviewNopass;
    private List<PassPicEntity> listPass,listNopass;
    private PeriodPicAdapter picPassAdapter,nopassAdapter;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_period_pic);
        isConnect(this);
        init();
        initView();
        initData();
    }

    private void init() {

        xueshiID=getIntent().getExtras().getString("xueshiid");
        listPass=new ArrayList<PassPicEntity>();
        listNopass=new ArrayList<PassPicEntity>();
    }

    private void initData() {
        new NetWorkUtils().getInstance().work(
                new NetInterface().getInstance().queryStudyPic(
                        xueshiID, 1+""),
                orderCurrenCallBack);
        new NetWorkUtils().getInstance().work(
                new NetInterface().getInstance().queryStudyPic(
                        xueshiID, 0+""),
                orderCurrenCallBack2);
    }


    @Override
    protected void onStart() {
        // TODO Auto-generated method stub
        super.onStart();

    }

    private void initView() {
        // TODO Auto-generated method stub
        RelativeLayout titlebar = (RelativeLayout) findViewById(R.id.titlebar);
        TextView txt = (TextView) titlebar.findViewById(R.id.titlebar_tv);
        txt.setText("抓拍照片");
        ImageView titleR = (ImageView) titlebar.findViewById(R.id.titlebar_back);
        titleR.setVisibility(View.VISIBLE);
        titleR.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                finish();

            }
        });

        listviewPass = (CustomListView) findViewById(R.id.pic_pass);
        listviewPass.setCanLoadMore(false);
        listviewPass.setCanRefresh(false);

        listviewNopass = (CustomListView) findViewById(R.id.pic_nopass);
        listviewNopass.setCanLoadMore(false);
        listviewNopass.setCanRefresh(false);





    }

    public boolean isConnect(Context context) {
        ConnectivityManager connectivity = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity != null) {
            NetworkInfo info = connectivity.getActiveNetworkInfo();
            if (info != null && info.isConnected()) {
                if (info.getState() == NetworkInfo.State.CONNECTED) {
                    return true;
                }
            }
        }
        Toast.makeText(context, "请检查你的网络", Toast.LENGTH_SHORT).show();
        return false;
    }





    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.orderlist_1:
                break;
            case R.id.orderlist_2:
                break;

            default:
                break;
        }
    }


    private NetWorkCallBack<BaseResult> cardOrMoneyCallBack = new NetWorkCallBack<BaseResult>() {

        @Override
        public void onComplete(String json) {
            // TODO Auto-generated method stub
            int msg = JsonUtils.parseMsg(json);
            if (msg == 1) {
            }
        }
    };

    private void initListView() {
        if (listPass != null && listPass.size() != 0) {
            picPassAdapter = new PeriodPicAdapter(this, listPass);
            listviewPass.setAdapter(picPassAdapter);
        }
    }

    private void initListView2() {
        if (listNopass != null && listNopass.size() != 0) {
            nopassAdapter = new PeriodPicAdapter(this, listNopass);
            listviewNopass.setAdapter(nopassAdapter);
        }
    }
    private NetWorkCallBack<BaseResult> orderCurrenCallBack = new NetWorkCallBack<BaseResult>() {

        @Override
        public void onComplete(String json) {
            listPass = JsonUtils.periodPicGuiji(json);
            initListView();

        }
    };
    private NetWorkCallBack<BaseResult> orderCurrenCallBack2 = new NetWorkCallBack<BaseResult>() {

        @Override
        public void onComplete(String json) {
            listNopass = JsonUtils.periodPicGuiji(json);
            initListView2();
        }
    };


}
