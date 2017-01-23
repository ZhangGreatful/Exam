package com.haha.exam.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Looper;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTabHost;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.haha.exam.R;
import com.haha.exam.bean.AllQuestions;
import com.haha.exam.dao.ExamDao;
import com.haha.exam.fragment.FavorableFragment;
import com.haha.exam.fragment.HomeFragment;
import com.haha.exam.fragment.HomePageFragmentNew;
import com.haha.exam.fragment.HomePageFragmentNew1;
import com.haha.exam.fragment.MyFragment;
import com.haha.exam.utils.SPUtils;
import com.haha.exam.web.WebInterface;
import com.lzy.okgo.OkGo;
import com.umeng.analytics.MobclickAgent;
import com.wanzheng.driver.util.RecordsEntity;
import com.wanzheng.driver.util.SystemUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static com.haha.exam.utils.SPUtils.get;

/**
 * 主页面
 */
public class HomeActivity extends FragmentActivity {

    private SPUtils spUtils = new SPUtils();
    private SystemUtil su;
    private String cartype, onlyID;
    private int xid;
    private ExamDao dao;
    private Gson gson = new Gson();
    public static FragmentTabHost mTabHost;
    private LayoutInflater inflater;
    private Class mFragmentArray[] = {HomePageFragmentNew.class, FavorableFragment.class, HomeFragment.class,
            MyFragment.class};
    private Class mFragmentArray1[] = {HomePageFragmentNew1.class, FavorableFragment.class, HomeFragment.class,
            MyFragment.class};
    private int mImageArray[] = {R.drawable.tab_home, R.drawable.tab_order,
            R.drawable.tab_favorable, R.drawable.tab_mine,};
    private String textArray[] = {"首页", "在线学习", "自我测评", "我的"};
    final OkHttpClient client = new OkHttpClient();
    private ProgressDialog progressDialog;
    public static Activity instance;
    private int subject1, subject2;
    private int count1, count2;
    private ArrayList<RecordsEntity> list;
    private int currentTime = 0;



    private class DataThread implements Runnable {

        @Override
        public void run() {
            System.out.println("执行线程1");
            System.out.println("执行加载数据方法");

//        判断数据库所有题目是否为空，若为空，则加载数据，不为空，则不加载数据
            String table_name = cartype + "_one";
            if (dao.exits(table_name) == true) {
                int count = dao.getContactsCount(table_name);
                int size = (int) spUtils.get(HomeActivity.this, "subject1", 0);
                System.out.println("数据库共有数据==========" + count);
                if (count == 0) {
                    String url = WebInterface.all_questions + "/cartype/" + cartype + "/subject/" + "1";

                    Request request = new Request.Builder()
                            .get()
                            .tag(this)
                            .url(url)
                            .build();
                    Response response = null;
                    try {
                        response = client.newCall(request).execute();
                        if (response.isSuccessful()) {
                            String str = response.body().string();
                            AllQuestions allQuestions = gson.fromJson(str, AllQuestions.class);
                            dao.addAllQuestions(allQuestions, cartype, "one");
                            List<AllQuestions.DataBean> datas = allQuestions.getData();
                            spUtils.put(HomeActivity.this, "subject1", datas.size());
                            System.out.print("datas  size one=========" + datas.size());
                        } else {
                            throw new IOException("Unexpected code " + response);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                } else if (count != size) {
                    dao.clearTable(table_name + "_questions");
                    String url = WebInterface.all_questions + "/cartype/" + cartype + "/subject/" + "1";

                    Request request = new Request.Builder()
                            .get()
                            .tag(this)
                            .url(url)
                            .build();
                    Response response = null;
                    try {
                        response = client.newCall(request).execute();
                        if (response.isSuccessful()) {
                            String str = response.body().string();
                            AllQuestions allQuestions = gson.fromJson(str, AllQuestions.class);
                            dao.addAllQuestions(allQuestions, cartype, "one");
                            List<AllQuestions.DataBean> datas = allQuestions.getData();
                            spUtils.put(HomeActivity.this, "subject1", datas.size());
                            System.out.print("datas  size one=========" + datas.size());
                        } else {
                            throw new IOException("Unexpected code " + response);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

        }
    }


    private class DataThread1 implements Runnable {

        @Override
        public synchronized void run() {
            System.out.println("执行线程2");
            String table_name = cartype + "_four";
            if (dao.exits(table_name) == true) {
                int count = dao.getContactsCount(table_name);
                int size = (int) spUtils.get(HomeActivity.this, "subject2", 0);
                System.out.println("数据库共有数据==========" + count);
                if (count == 0) {
                    String url = WebInterface.all_questions + "/cartype/" + cartype + "/subject/" + "4";

                    Request request = new Request.Builder()
                            .get()
                            .tag(this)
                            .url(url)
                            .build();
                    Response response = null;
                    try {
                        response = client.newCall(request).execute();
                        if (response.isSuccessful()) {
                            String str = response.body().string();
                            AllQuestions allQuestions = gson.fromJson(str, AllQuestions.class);
                            dao.addAllQuestions(allQuestions, cartype, "four");
                            List<AllQuestions.DataBean> datas = allQuestions.getData();
                            spUtils.put(HomeActivity.this, "subject2", datas.size());
                            System.out.print("datas  size four=========" + datas.size());
                        } else {
                            throw new IOException("Unexpected code " + response);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else if (count != size) {
                    dao.clearTable(table_name + "_questions");
                    String url = WebInterface.all_questions + "/cartype/" + cartype + "/subject/" + "4";

                    Request request = new Request.Builder()
                            .get()
                            .tag(this)
                            .url(url)
                            .build();
                    Response response = null;
                    try {
                        response = client.newCall(request).execute();
                        if (response.isSuccessful()) {
                            String str = response.body().string();
                            AllQuestions allQuestions = gson.fromJson(str, AllQuestions.class);
                            dao.addAllQuestions(allQuestions, cartype, "four");
                            List<AllQuestions.DataBean> datas = allQuestions.getData();
                            spUtils.put(HomeActivity.this, "subject2", datas.size());
                            System.out.print("datas  size four=========" + datas.size());
                        } else {
                            throw new IOException("Unexpected code " + response);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        isConnect(this);
        instance = this;
        su = new SystemUtil(this);
        su.saveTiming(0);//每次进入应用，默认不计时
        su.removeLearnedTime();
        setContentView(R.layout.activity_home);
        cartype = (String) get(this, "cartype", "xc");
        dao = new ExamDao(HomeActivity.this);
        progressDialog = ProgressDialog.show(this, "请稍等...", "加载题库中...");
        DataThread dataThread = new DataThread();
        DataThread1 dataThread1 = new DataThread1();
        Thread thread = new Thread(dataThread);
        Thread thread1 = new Thread(dataThread1);
        thread.start();
        thread1.start();
        initView();
        initData();
        MobclickAgent.setScenarioType(this, MobclickAgent.EScenarioType.E_UM_NORMAL);

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
//        Toast.makeText(context, "请检查你的网络", Toast.LENGTH_SHORT).show();
        return false;
    }

    private void initData() {
        final Timer timer = new Timer();
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                Log.d("HomeActivity", "isConnect==========" + isConnect(HomeActivity.this));
                currentTime++;
                String table1 = cartype + "_one";
                String table2 = cartype + "_four";
                if (dao.exits(table1) == true && dao.exits(table2) == true) {
                    count1 = dao.getContactsCount(table1);
                    count2 = dao.getContactsCount(table2);
                    subject1 = (int) get(HomeActivity.this, "subject1", 0);
                    subject2 = (int) get(HomeActivity.this, "subject2", 0);
                    System.out.println("76687====" + count1 + "323123====" + count2);
                    System.out.println("76687====" + subject1 + "323123====" + subject2);
                    if (count1 != 0 && count2 != 0) {
                        if (subject1 == count1 && subject2 == count2) {
                            Looper.prepare();
                            progressDialog.dismiss();
                            Looper.loop();
                            timer.cancel();
                        } else if (isConnect(HomeActivity.this) == false || currentTime == 60) {
                            Looper.prepare();
                            Toast.makeText(HomeActivity.this, "加载数据失败，请确保当前网络良好，退出程序重新加载", Toast.LENGTH_LONG).show();
                            progressDialog.dismiss();
                            Looper.loop();
                            timer.cancel();
                        }
                    } else if (isConnect(HomeActivity.this) == false) {
                        Toast.makeText(HomeActivity.this, "加载数据失败，请确保当前网络良好，退出程序重新加载", Toast.LENGTH_LONG).show();
                        progressDialog.dismiss();
                        Looper.loop();
                        timer.cancel();
                    }
                }
            }
        };
        timer.schedule(timerTask, 0, 1000);
    }


    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onPageStart("HomeActivity");
        MobclickAgent.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd("HomeActivity");
        MobclickAgent.onPause(this);
    }

    /**
     * 初始化组件
     */

    private void initView() {
        inflater = LayoutInflater.from(this);
        mTabHost = (FragmentTabHost) findViewById(R.id.tabhost);
        mTabHost.setup(this, getSupportFragmentManager(), R.id.content);
        mTabHost.getTabWidget().setDividerDrawable(R.color.white);//去掉tabhost的分割线

        int count = mFragmentArray.length;
        for (int i = 0; i < count; i++) {
            TabHost.TabSpec tabSpec = mTabHost.newTabSpec(textArray[i]).setIndicator(getTabItemView(i));
            mTabHost.addTab(tabSpec, mFragmentArray1[i], null);
//            if (su.showOnlyID().equals("")) {
//                mTabHost.addTab(tabSpec, mFragmentArray[i], null);
//            } else {
//                mTabHost.addTab(tabSpec, mFragmentArray1[i], null);
//            }

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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        OkGo.getInstance().cancelTag(this);
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
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK) {
            Intent home = new Intent(Intent.ACTION_MAIN);
            home.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            home.addCategory(Intent.CATEGORY_HOME);
            startActivity(home);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
