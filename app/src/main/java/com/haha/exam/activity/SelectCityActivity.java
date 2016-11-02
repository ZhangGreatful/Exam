package com.haha.exam.activity;

import android.os.Bundle;

import com.google.gson.Gson;
import com.haha.exam.R;
import com.haha.exam.bean.AllCity;
import com.haha.exam.web.WebInterface;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;

import okhttp3.Call;
import okhttp3.Response;

/**
 * 选择城市
 */
public class SelectCityActivity extends BaseActivity {

    private Gson gson=new Gson();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("选择城市");

        iniView();
        initData();
    }

    private void initData() {

        OkGo.post(WebInterface.all_citys)
                .tag(this)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        AllCity allCity=gson.fromJson(s, AllCity.class);

                    }
                });
    }

    private void iniView() {

    }

    @Override
    protected int getContentView() {
        return R.layout.activity_select_city;
    }

}
