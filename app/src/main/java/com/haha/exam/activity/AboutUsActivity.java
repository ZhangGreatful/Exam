package com.haha.exam.activity;

import android.os.Bundle;

import com.haha.exam.R;

public class AboutUsActivity extends BaseActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initTitle();
    }




    private void initTitle() {
        setTitle("关于我们");
        setTitleColor(getResources().getColor(R.color.title_color));
        setTitlebarBackground(R.color.white);
        setLeftBtnDrawable();
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_about_us;
    }
}
