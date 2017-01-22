package com.haha.exam.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.gson.Gson;
import com.haha.exam.R;
import com.haha.exam.adapter.ChapterAdapter;
import com.haha.exam.bean.ChapterQuestion;
import com.haha.exam.utils.SPUtils;
import com.haha.exam.web.WebInterface;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.cache.CacheMode;
import com.lzy.okgo.callback.StringCallback;

import okhttp3.Call;
import okhttp3.Response;

/**
 * 章节练习
 */
public class ChapterActivity extends BaseActivity implements View.OnClickListener {

    private SPUtils spUtils=new SPUtils();
    private String subject0;
    private String cartype;
    private ListView listView;
    private ChapterAdapter adapter;
    private Gson gson;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        subject0= (String) spUtils.get(this,"subject0","1");
        cartype= (String) spUtils.get(this,"cartype","xc");
        setTitle("章节练习");
        setTitleColor(getResources().getColor(R.color.title_color));
        setTitlebarBackground(R.color.white);
        setLeftBtnDrawable();
        initView();
        initData();

    }

    /**
     * 初始化数据，获取章节列表
     */
    private void initData() {
        gson = new Gson();
        new Thread(new Runnable() {
            @Override
            public void run() {
                OkGo.post(WebInterface.chapter_question)
                        .params("cartype", cartype)
                        .params("subject",subject0)
                        .tag(this)
                        .cacheMode(CacheMode.IF_NONE_CACHE_REQUEST)
                        .execute(new StringCallback() {
                            @Override
                            public void onSuccess(String s, Call call, Response response) {
                                final ChapterQuestion question = gson.fromJson(s, ChapterQuestion.class);
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        System.out.println("章节练习分类请求成功，共有数据：" + question.getMsg().size());
                                        adapter = new ChapterAdapter(ChapterActivity.this, question.getMsg());
                                        listView.setAdapter(adapter);
                                        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                            @Override
                                            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                                                Intent intent = new Intent(ChapterActivity.this, OrderTextActivity.class);
                                                intent.putExtra("chapter", position+1);
                                                intent.putExtra("chapterid", question.getMsg().get(position).getSid());
                                                startActivity(intent);
                                            }
                                        });
                                    }
                                });

                            }
                            //                从缓存中拿数据
                            @Override
                            public void onCacheSuccess(String s, Call call) {
                                super.onCacheSuccess(s, call);
                                final ChapterQuestion question = gson.fromJson(s, ChapterQuestion.class);
                                adapter = new ChapterAdapter(ChapterActivity.this, question.getMsg());
                                listView.setAdapter(adapter);
                                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                    @Override
                                    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                                        Intent intent = new Intent(ChapterActivity.this, OrderTextActivity.class);
                                        intent.putExtra("chapter", position+1);
                                        intent.putExtra("chapterid", question.getMsg().get(position).getSid());
                                        startActivity(intent);
                                    }
                                });
                            }
                        });
            }
        }).start();


    }

    @Override
    protected int getContentView() {

        return R.layout.activity_chapter;

    }
//      初始化布局
    private void initView() {
        listView = (ListView) findViewById(R.id.chapter_list);

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
