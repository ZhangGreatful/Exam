package com.haha.exam.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

import com.haha.exam.R;
import com.umeng.analytics.MobclickAgent;

/**
 * 在线学习界面
 */
public class StudyActivity extends AppCompatActivity implements View.OnClickListener {
    private RelativeLayout keyi, keer, kesan, kesan_theory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_study);
        initView();
    }

    private void initView() {
        keyi = (RelativeLayout) findViewById(R.id.ke_yi);
        keer = (RelativeLayout) findViewById(R.id.ke_er);
        kesan = (RelativeLayout) findViewById(R.id.ke_san);
        kesan_theory = (RelativeLayout) findViewById(R.id.ke_san_theory);

        keyi.setOnClickListener(this);
        keer.setOnClickListener(this);
        kesan.setOnClickListener(this);
        kesan_theory.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ke_yi:
                Intent intent = new Intent(StudyActivity.this, Subject1Activity.class);
                startActivity(intent);
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onPageStart("StudyActivity");
        MobclickAgent.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd("StudyActivity");
        MobclickAgent.onPause(this);
    }
}
