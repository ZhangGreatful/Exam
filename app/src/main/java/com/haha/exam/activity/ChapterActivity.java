package com.haha.exam.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;

import com.haha.exam.R;
import com.haha.exam.adapter.ChapterAdapter;

/**
 * 章节练习
 */
public class ChapterActivity extends Activity implements View.OnClickListener {

    private ListView listView;
    private ImageView back;
    private ChapterAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chapter);
        initView();
    }

    private void initView() {
        listView = (ListView) findViewById(R.id.chapter_list);
        adapter = new ChapterAdapter(this);
        back = (ImageView) findViewById(R.id.back);

        back.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
        }
    }
}
