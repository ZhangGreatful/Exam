package com.haha.exam.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.haha.exam.R;

public class MySchoolActivity extends BaseActivity implements View.OnClickListener {

    private ImageView back;
    private boolean isFirstLoc=true;


    @Override
    protected int getContentView() {
        return R.layout.activity_my_school;
    }

    @Override
    protected int getTitlebarResId() {
        return R.layout.my_school_bar;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
    }



    private void initView() {
        back = (ImageView) findViewById(R.id.back);

        back.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
        }
    }

}
