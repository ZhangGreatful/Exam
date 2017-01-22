package com.haha.exam.fragment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.haha.exam.R;
import com.haha.exam.activity.SettingActivity;
import com.haha.exam.activity.VideoCollectActivity;
import com.haha.exam.utils.SPUtils;
import com.haha.exam.view.circleimageview.CircleImageView;
import com.squareup.picasso.Picasso;
import com.wanzheng.driver.util.SystemUtil;

import okhttp3.OkHttpClient;

import static com.haha.exam.utils.SPUtils.get;

/**
 * Created by Administrator on 2016/10/19.
 */
public class MineFragment extends Fragment implements View.OnClickListener {

    private SPUtils spUtils = new SPUtils();
    private SystemUtil su;
    private String userName, telphone, schoolName;
    private LinearLayout settings;
    private TextView username, phone, school;
    private CircleImageView header;
    private String headerPath;
    private LinearLayout favorable_video, tikugengxin, yijiankefu;
    final OkHttpClient client = new OkHttpClient();
    private ProgressDialog progressDialog;
    private Gson gson = new Gson();
    private String cartype;
    private int subject1, subject2;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.mine_fragment, container, false);
        return view;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        su = new SystemUtil(getActivity());
        headerPath = su.showUserHeader();
        cartype = (String) get(getActivity(), "cartype", "xc");
        initView();
    }

    private void initView() {
        header = (CircleImageView) this.getActivity().findViewById(R.id.user_header);
        school = (TextView)  this.getActivity().findViewById(R.id.school);
        settings = (LinearLayout)  this.getActivity().findViewById(R.id.settings);
        username = (TextView)  this.getActivity().findViewById(R.id.user_name);
        phone = (TextView)  this.getActivity().findViewById(R.id.phone);
        favorable_video = (LinearLayout)  this.getActivity().findViewById(R.id.favorable_video);
        tikugengxin = (LinearLayout)  this.getActivity().findViewById(R.id.tikugengxin);
        yijiankefu = (LinearLayout)  this.getActivity().findViewById(R.id.yijiankefu);
        favorable_video.setOnClickListener(this);
        tikugengxin.setOnClickListener(this);
        yijiankefu.setOnClickListener(this);
        settings.setOnClickListener(this);
        userName = su.showName();
        telphone = su.showPhone();
        schoolName = su.showSchool();
        System.out.println("username=========" + userName.length());
        System.out.println("telphone=============" + telphone);
        System.out.println("telphone=============" + schoolName);
        System.out.println("telphone=============" + su.showCity());
        username.setText(userName);
        phone.setText(telphone);
        school.setText(schoolName);
        Picasso.with(getActivity()).load(headerPath).into(header);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.settings:
                Intent intent = new Intent(getActivity(), SettingActivity.class);
                getActivity().startActivity(intent);
                break;
            case R.id.favorable_video:
                Intent intent1 = new Intent(getActivity(), VideoCollectActivity.class);
                startActivity(intent1);
                break;
            case R.id.tikugengxin:
//                dbHelper.deleteDatabase(getActivity());//删除数据库
                progressDialog = ProgressDialog.show(getActivity(), "请稍等...", "更新题库中...");
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        progressDialog.dismiss();
                    }
                }, 5000);
                break;
            case R.id.yijiankefu:
                Intent phoneIntent = new Intent("android.intent.action.CALL", Uri.parse("tel:" + "4008004650"));
                startActivity(phoneIntent);
                break;
        }
    }


}
