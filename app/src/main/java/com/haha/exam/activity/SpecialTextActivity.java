package com.haha.exam.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;

import com.google.gson.Gson;
import com.haha.exam.R;
import com.haha.exam.adapter.GridAdapter;
import com.haha.exam.bean.SpecialType;
import com.haha.exam.utils.SPUtils;
import com.haha.exam.web.WebInterface;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;

import okhttp3.Call;
import okhttp3.Response;

/**
 * 专项练习
 */
public class SpecialTextActivity extends BaseActivity implements View.OnClickListener {

    private SPUtils spUtils = new SPUtils();
    private String cartype;
    private String subject;
    private GridView gridView;
    private ImageView back;
    private GridAdapter adapter;
    private Gson gson;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("专项练习");
        setTitleColor(getResources().getColor(R.color.title_color));
        setTitlebarBackground(R.color.white);
        setLeftBtnDrawable();
        subject = (String) spUtils.get(this, "subject0", "1");
        cartype = (String) spUtils.get(this, "cartype", "xc");
        initView();
        initData();
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_special_text;
    }

    private void initData() {
        gson = new Gson();
        new Thread(new Runnable() {
            @Override
            public void run() {
                OkGo.get(WebInterface.knowledge_type)
                        .params("cartype", cartype)
                        .params("subject", subject)
                        .tag(this)
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
                                        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                            @Override
                                            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                                                System.out.println("点击了第  " + position);
                                                Intent intent = new Intent(SpecialTextActivity.this, OrderTextActivity.class);
                                                intent.putExtra("knowtypeid", position + 1);
                                                intent.putExtra("knowtype", String.valueOf(specialType.getMsg().get(position).getSid()));
                                                startActivity(intent);
                                            }
                                        });
                                    }
                                });

                            }

                            //          使用缓存
                            @Override
                            public void onCacheSuccess(String s, Call call) {
                                super.onCacheSuccess(s, call);
                                final SpecialType specialType = gson.fromJson(s, SpecialType.class);
                                adapter = new GridAdapter(SpecialTextActivity.this, specialType);
                                gridView.setAdapter(adapter);
                                System.out.println("获得的数据是========" + specialType.getMsg().size());
                                gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                    @Override
                                    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                                        System.out.println("点击了第  " + position);
                                        Intent intent = new Intent(SpecialTextActivity.this, OrderTextActivity.class);
                                        intent.putExtra("knowtypeid", position + 1);
                                        intent.putExtra("knowtype", String.valueOf(specialType.getMsg().get(position).getSid()));
                                        startActivity(intent);
                                    }
                                });
                            }
                        });
            }
        }).start();


    }

    private void initView() {
        gridView = (GridView) findViewById(R.id.special_subject);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        //根据 Tag 取消请求
        OkGo.getInstance().cancelTag(this);
    }
}
