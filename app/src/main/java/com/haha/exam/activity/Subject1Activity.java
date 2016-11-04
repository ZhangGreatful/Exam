package com.haha.exam.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.haha.exam.R;
import com.haha.exam.adapter.ViewPagerAdapter;
import com.haha.exam.fragment.ChapterFourFragment;
import com.haha.exam.fragment.ChapterOneFragment;
import com.haha.exam.fragment.ChapterThreeFragment;
import com.haha.exam.fragment.ChapterTwoFragment;
import com.haha.exam.view.NavitationScrollLayout;

import java.util.ArrayList;
import java.util.List;

public class Subject1Activity extends BaseActivity {
    private NavitationScrollLayout navitationScrollLayout;
    private ViewPager viewPager;
    private String[] titles = new String[]{"第一章", "第二章", "第三章", "第四章"};
    private ViewPagerAdapter viewPagerAdapter;
    private List<Fragment> fragments;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("科目一");
        initView();
    }

    private void initView() {
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        navitationScrollLayout = (NavitationScrollLayout) findViewById(R.id.bar);
        fragments =  new ArrayList<>();
        fragments.add(new ChapterOneFragment());
        fragments.add(new ChapterTwoFragment());
        fragments.add(new ChapterThreeFragment());
        fragments.add(new ChapterFourFragment());
        viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(), fragments);
        viewPager.setAdapter(viewPagerAdapter);

        navitationScrollLayout.setViewPager(this, titles, viewPager, R.color.title_color, R.color.title_color, 16, 20, 8, true, R.color.title_color, 0f, 15f, 15f, 90);
        navitationScrollLayout.setBgLine(this, 1, R.color.white);
        navitationScrollLayout.setNavLine(this, 2, R.color.title_color);
        navitationScrollLayout.setOnTitleClickListener(new NavitationScrollLayout.OnTitleClickListener() {
            @Override
            public void onTitleClick(View v) {
                Toast.makeText(Subject1Activity.this, ((TextView)v).getText(), Toast.LENGTH_SHORT).show();
            }
        });

        navitationScrollLayout.setOnNaPageChangeListener(new NavitationScrollLayout.OnNaPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_subject1;
    }
}
