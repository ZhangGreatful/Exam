package com.haha.exam.fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.haha.exam.R;
import com.haha.exam.activity.FeedBackActivity;
import com.haha.exam.activity.SettingActivity;
import com.haha.exam.activity.VideoCollectActivity;
import com.haha.exam.bean.AllQuestions;
import com.haha.exam.dao.ExamDao;
import com.haha.exam.dialog.MyDialog;
import com.haha.exam.utils.SPUtils;
import com.haha.exam.view.circleimageview.CircleImageView;
import com.haha.exam.web.WebInterface;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.Picasso;
import com.wanzheng.driver.Entity.PersonalInfo;
import com.wanzheng.driver.home.Activity_PerInfor;
import com.wanzheng.driver.home.LoginActivity;
import com.wanzheng.driver.util.SystemUtil;

import java.io.IOException;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static com.haha.exam.utils.SPUtils.get;

/**
 * Created by Administrator on 2016/12/10.
 */

public class MyFragment extends Fragment implements View.OnClickListener {

    private String onlyId="";
    private String headerPath, username, userphone, userschool;
    private String usernum, sheng, shi, qu;
    private int usersex;
    private TextView userName, setting;
    private TextView userPhone, userSchool;
    private CircleImageView userHeader;
    private RelativeLayout yi_jian_fan_kui, yi_jian_ke_fu, geng_xin, she_zhi;
    private LinearLayout jia_xiao, you_hui_quan, coach_contact, shi_pin_shou_cang;
    private ProgressDialog progressDialog;

    private RelativeLayout user;
    private LinearLayout userInfo, login;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.my_fragment, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        headerPath = su.showUserHeader();
        username = su.showName();
        userphone = su.showPhone();
        userschool = su.showSchool();
        cartype = (String) spUtils.get(getActivity(), "cartype", "xc");
        dao = new ExamDao(getActivity());
//        progressDialog = ProgressDialog.show(getActivity(), "请稍等...", "加载题库中...");
        initView();
    }

    private void initView() {
//        setting = (TextView) this.getActivity().findViewById(R.id.setting);
        userName = (TextView) this.getActivity().findViewById(R.id.user_name);
        userHeader = (CircleImageView) this.getActivity().findViewById(R.id.user_header);
        userPhone = (TextView) this.getActivity().findViewById(R.id.user_phone);
        userSchool = (TextView) this.getActivity().findViewById(R.id.user_school);
        login = (LinearLayout) this.getActivity().findViewById(R.id.login);
        userInfo = (LinearLayout) this.getActivity().findViewById(R.id.user_info);
        user = (RelativeLayout) this.getActivity().findViewById(R.id.user);
        if (su.showOnlyID().equals("")) {//游客模式
            userInfo.setVisibility(View.GONE);
            login.setVisibility(View.VISIBLE);

        } else {//非游客模式
            login.setVisibility(View.GONE);
            userInfo.setVisibility(View.VISIBLE);
        }
        user.setOnClickListener(this);
        userSchool.setText(userschool);
        userPhone.setText(userphone);

        jia_xiao = (LinearLayout) this.getActivity().findViewById(R.id.jia_xiao);
        you_hui_quan = (LinearLayout) this.getActivity().findViewById(R.id.you_hui_quan);
        coach_contact = (LinearLayout) this.getActivity().findViewById(R.id.coach_contact);
        shi_pin_shou_cang = (LinearLayout) this.getActivity().findViewById(R.id.shi_pin_shou_cang);


        yi_jian_fan_kui = (RelativeLayout) this.getActivity().findViewById(R.id.yi_jian_fan_kui);
        yi_jian_ke_fu = (RelativeLayout) this.getActivity().findViewById(R.id.yi_jian_ke_fu);
        geng_xin = (RelativeLayout) this.getActivity().findViewById(R.id.geng_xin);
        she_zhi = (RelativeLayout) this.getActivity().findViewById(R.id.she_zhi);

//        setting.setOnClickListener(this);
        jia_xiao.setOnClickListener(this);
        you_hui_quan.setOnClickListener(this);
        coach_contact.setOnClickListener(this);
        shi_pin_shou_cang.setOnClickListener(this);

        yi_jian_ke_fu.setOnClickListener(this);
        yi_jian_fan_kui.setOnClickListener(this);
        geng_xin.setOnClickListener(this);
        she_zhi.setOnClickListener(this);
        userName.setText(username);
        if (!su.showOnlyID().equals("")) {
            Picasso.with(getActivity()).load(headerPath).memoryPolicy(MemoryPolicy.NO_CACHE, MemoryPolicy.NO_STORE).into(userHeader);
        }


    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        su = new SystemUtil(getActivity());
        onlyId = su.showOnlyID();
    }

    @Override
    public void onResume() {
        super.onResume();
        initInfo();
    }

    //      获取个人信息
    private void initInfo() {
        if (!onlyId.equals("")){
            String url = WebInterface.get_personal_info + "&uid=0" + "&onlyid=" + onlyId;
            OkGo.post(url)
                    .tag(this)
                    .execute(changePersonalInfo);
        }
    }

    private StringCallback changePersonalInfo = new StringCallback() {
        @Override
        public void onSuccess(String s, Call call, Response response) {
            PersonalInfo info = gson.fromJson(s, PersonalInfo.class);
            username = info.getReturnData().get(0).getXingming();
            usernum = info.getReturnData().get(0).getShenfenid();
            usersex = info.getReturnData().get(0).getXingbie();
            sheng = info.getReturnData().get(0).getSheng();
            shi = info.getReturnData().get(0).getChengshi();
            qu = info.getReturnData().get(0).getXian();
        }
    };

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.user:
                if (su.showOnlyID().equals("")) {//游客模式点击头像进入登录界面
                    startActivity(new Intent(getActivity(), LoginActivity.class));
                } else {//非游客模式点击头像查看个人信息
                    Intent intent = new Intent(getActivity(), Activity_PerInfor.class);
                    intent.putExtra("From", 1);
                    intent.putExtra("sheng", sheng);
                    intent.putExtra("shi", shi);
                    intent.putExtra("qu", qu);
                    intent.putExtra("name", username);
                    intent.putExtra("sex", usersex);
                    intent.putExtra("id", usernum);
                    startActivity(intent);
                }
                break;
            case R.id.jia_xiao:
                Toast.makeText(getActivity(), "敬请期待", Toast.LENGTH_SHORT).show();
                break;
            case R.id.you_hui_quan:
                Toast.makeText(getActivity(), "敬请期待", Toast.LENGTH_SHORT).show();
                break;
            case R.id.coach_contact:
//                startActivity(new Intent(getActivity(), WebExaminationActivity.class));
                Toast.makeText(getActivity(), "敬请期待", Toast.LENGTH_SHORT).show();
                break;
            case R.id.shi_pin_shou_cang:
                Intent intent1 = new Intent(getActivity(), VideoCollectActivity.class);
                startActivity(intent1);
                break;
            case R.id.yi_jian_ke_fu:
                Intent phoneIntent = new Intent("android.intent.action.CALL", Uri.parse("tel:" + "4008004650"));
                startActivity(phoneIntent);
                break;
            case R.id.yi_jian_fan_kui:
                Intent intent2 = new Intent(getActivity(), FeedBackActivity.class);
                startActivity(intent2);
                break;
            case R.id.geng_xin:
                final MyDialog dialog = new MyDialog(getActivity());
                dialog.setMessage("是否更新题库？");
                dialog.show();
                dialog.setNoOnclickListener("否", new MyDialog.onNoOnclickListener() {
                    @Override
                    public void onNoClick() {
                        dialog.dismiss();
                    }
                });
                dialog.setYesOnclickListener("是", new MyDialog.onYesOnclickListener() {
                    @Override
                    public void onYesClick() {
                        dialog.dismiss();
                        dao.clearTable("xc_one_questions");//删除数据库,重新加载数据
                        dao.clearTable("xc_four_questions");
                        progressDialog = ProgressDialog.show(getActivity(), "请稍等...", "更新题库中...");
                        DataThread dataThread = new DataThread();
                        DataThread1 dataThread1 = new DataThread1();
                        Thread thread = new Thread(dataThread);
                        Thread thread1 = new Thread(dataThread1);
                        thread.start();
                        thread1.start();
                        initData();
                    }
                });
                break;
            case R.id.she_zhi:
                Intent intent3 = new Intent(getActivity(), SettingActivity.class);
                getActivity().startActivity(intent3);
                break;
        }
    }

    private ExamDao dao;
    private SPUtils spUtils = new SPUtils();
    final OkHttpClient client = new OkHttpClient();
    private Gson gson = new Gson();
    private String cartype;
    private SystemUtil su;
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
                int size = (int) spUtils.get(getActivity(), "subject1", 0);
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
                            spUtils.put(getActivity(), "subject1", datas.size());
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
                            spUtils.put(getActivity(), "subject1", datas.size());
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
                int size = (int) spUtils.get(getActivity(), "subject2", 0);
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
                            spUtils.put(getActivity(), "subject2", datas.size());
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
                            spUtils.put(getActivity(), "subject2", datas.size());
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

    private int subject1, subject2;
    private int count1, count2;

    private void initData() {
        final Timer timer = new Timer();
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                Log.d("HomeActivity", "isConnect==========" + isConnect(getActivity()));
                currentTime++;
                String table1 = cartype + "_one";
                String table2 = cartype + "_four";
                if (dao.exits(table1) == true && dao.exits(table2) == true) {
                    count1 = dao.getContactsCount(table1);
                    count2 = dao.getContactsCount(table2);
                    subject1 = (int) get(getActivity(), "subject1", 0);
                    subject2 = (int) get(getActivity(), "subject2", 0);
                    System.out.println("76687====" + count1 + "323123====" + count2);
                    System.out.println("76687====" + subject1 + "323123====" + subject2);
                    if (count1 != 0 && count2 != 0) {
                        if (subject1 == count1 && subject2 == count2) {
                            Looper.prepare();
                            progressDialog.dismiss();
                            Looper.loop();
                            timer.cancel();
                        } else if (isConnect(getActivity()) == false || currentTime == 60) {
                            Looper.prepare();
                            Toast.makeText(getActivity(), "加载数据失败，请确保当前网络良好，退出程序重新加载", Toast.LENGTH_LONG).show();
                            progressDialog.dismiss();
                            Looper.loop();
                            timer.cancel();
                        }
                    } else if (isConnect(getActivity()) == false) {
                        Toast.makeText(getActivity(), "加载数据失败，请确保当前网络良好，退出程序重新加载", Toast.LENGTH_LONG).show();
                        progressDialog.dismiss();
                        Looper.loop();
                        timer.cancel();
                    }
                }
            }
        };
        timer.schedule(timerTask, 0, 1000);
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
}
