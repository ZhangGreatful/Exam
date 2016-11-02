package com.haha.exam.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.haha.exam.R;
import com.haha.exam.adapter.ChapterAdapter;
import com.haha.exam.bean.ChapterQuestion;
import com.haha.exam.dao.ExamDao;
import com.haha.exam.dialog.MyDialog;
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

    private ListView listView;
    private ImageView back;
    private ChapterAdapter adapter;
    private MyDialog dialog;
    private Gson gson;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_chapter);

        setTitle("章节练习");
        initView();
        initData();

    }

    private void initData() {
        gson = new Gson();
        OkGo.post(WebInterface.chapter_question)
                .params("cartype", "xc")
                .tag(this)
                .cacheMode(CacheMode.DEFAULT)
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
                            }
                        });

                    }
                });

    }

    @Override
    protected int getContentView() {

        return R.layout.activity_chapter;

    }

    private void initView() {
        listView = (ListView) findViewById(R.id.chapter_list);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Intent intent = new Intent(ChapterActivity.this, OrderTextActivity.class);
                intent.putExtra("chapterid", position + 1);
                startActivity(intent);
            }
        });
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
