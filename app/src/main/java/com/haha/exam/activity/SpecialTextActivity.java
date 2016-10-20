package com.haha.exam.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageView;

import com.google.gson.Gson;
import com.haha.exam.R;
import com.haha.exam.adapter.GridAdapter;
import com.haha.exam.bean.SpecialType;
import com.haha.exam.web.WebInterface;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.cache.CacheMode;
import com.lzy.okgo.callback.StringCallback;

import okhttp3.Call;
import okhttp3.Response;

/**
 * 专项练习
 */
public class SpecialTextActivity extends Activity implements View.OnClickListener {

    private GridView gridView;
    private ImageView back;
    private GridAdapter adapter;
    private Gson gson;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_special_text);
        initView();
        initData();
    }

    private void initData() {
        gson = new Gson();
        OkGo.get(WebInterface.knowledge_type)
                .params("cartype", "xc")
                .tag(this)
                .cacheKey("cacheKey")
                .cacheMode(CacheMode.IF_NONE_CACHE_REQUEST)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        final SpecialType specialType = gson.fromJson(s, SpecialType.class);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                adapter = new GridAdapter(SpecialTextActivity.this, specialType);
                                gridView.setAdapter(adapter);
                                System.out.println("获得的数据是========" + specialType.getMsg().size());
                            }
                        });

                    }

                    //          使用缓存
                    @Override
                    public void onCacheSuccess(String s, Call call) {
                        super.onCacheSuccess(s, call);
                        final SpecialType specialType = gson.fromJson(s, SpecialType.class);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                adapter = new GridAdapter(SpecialTextActivity.this, specialType);
                                gridView.setAdapter(adapter);
                                System.out.println("获得的数据是========" + specialType.getMsg().size());
                            }
                        });
                    }
                });

    }

    private void initView() {
        gridView = (GridView) findViewById(R.id.special_subject);
        back = (ImageView) findViewById(R.id.iv_back);

        back.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        //根据 Tag 取消请求
        OkGo.getInstance().cancelTag(this);
    }
}
