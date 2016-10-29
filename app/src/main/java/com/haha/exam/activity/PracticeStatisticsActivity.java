package com.haha.exam.activity;

import android.app.Activity;
import android.os.Bundle;

import com.haha.exam.R;

/**
 * 练习统计界面
 */
public class PracticeStatisticsActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("练习统计");
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_practice_statistics;
    }
}
