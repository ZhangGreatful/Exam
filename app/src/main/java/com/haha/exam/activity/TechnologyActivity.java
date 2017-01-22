package com.haha.exam.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.gson.Gson;
import com.haha.exam.R;
import com.haha.exam.adapter.TechniqueAdapter;
import com.haha.exam.bean.Technique;
import com.haha.exam.web.WebInterface;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.wanzheng.driver.network.JsonUtils;

import java.util.List;

import okhttp3.Call;
import okhttp3.Response;

public class TechnologyActivity extends BaseActivity {

    private ListView listView;
    private Gson gson = new Gson();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initTitle();
        initView();
        initData();
    }

    private void initData() {
        OkGo.post(WebInterface.technique)
                .tag(this)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        int num = JsonUtils.parseNum(s);
                        if (num == 1) {
                            Technique technique = gson.fromJson(s, Technique.class);
                            final List<Technique.DataBean> list = technique.getData();
                            TechniqueAdapter adapter = new TechniqueAdapter(TechnologyActivity.this, list);
                            listView.setAdapter(adapter);
                            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                                    String url = list.get(i).getUrl();
                                    Intent intent = new Intent(TechnologyActivity.this, RuleActivity.class);
                                    intent.putExtra("url", url);
                                    intent.putExtra("from",1);
                                    startActivity(intent);
                                }
                            });
                        }
                    }
                });
    }

    private void initView() {
        listView = (ListView) findViewById(R.id.list);
    }

    private void initTitle() {
        setTitle("考试技巧");
        setTitlebarBackground(R.color.white);
        setTitleColor(getResources().getColor(R.color.title_color));
        setLeftBtnDrawable();
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_technology;
    }
}
