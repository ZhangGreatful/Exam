package com.haha.exam.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.haha.exam.R;
import com.haha.exam.adapter.ChapterAdapter;
import com.haha.exam.dialog.MyDialog;

/**
 * 章节练习
 */
public class ChapterActivity extends BaseActivity implements View.OnClickListener {

    private ListView listView;
    private ImageView back;
    private ChapterAdapter adapter;
    private MyDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_chapter);

        setTitle("章节练习");
        initView();

    }

    @Override
    protected int getContentView() {

        return R.layout.activity_chapter;

    }

    private void initView() {
        listView = (ListView) findViewById(R.id.chapter_list);
        adapter = new ChapterAdapter(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
        }
    }
}
