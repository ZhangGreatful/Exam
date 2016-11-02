package com.haha.exam.activity;

import android.app.Activity;
import android.os.Bundle;

import com.google.gson.Gson;
import com.haha.exam.R;
import com.haha.exam.bean.AllSchool;
import com.haha.exam.web.WebInterface;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;

import okhttp3.Call;
import okhttp3.Response;

public class SelectSchoolActivity extends BaseActivity {

    private Gson gson = new Gson();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("选择驾校");
        initView();
        initData();

    }

    private void initData() {

        OkGo.post(WebInterface.driving_school)
                .tag(this)
                .params("cityid", "")
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        AllSchool school = gson.fromJson(s, AllSchool.class);
                    }
                });
    }

    private void initView() {
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_select_school;
    }
}
