package com.haha.exam.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioGroup;

import com.haha.exam.R;
import com.haha.exam.adapter.AppointAdapter;
import com.haha.exam.fragment.Appointment1Fragment;
import com.haha.exam.fragment.Appointment2Fragment;
import com.haha.exam.fragment.Appointment3Fragment;

import java.util.ArrayList;

/**
 * 2016/12/13 shaowei  预约界面
 */
public class AppointmentActivity extends AppCompatActivity implements RadioGroup.OnCheckedChangeListener, ViewPager.OnPageChangeListener, View.OnClickListener {
    private ViewPager mPager;
    private ArrayList<Fragment> fragmentList;
    private RadioGroup mRadioGroup;
    private ImageView mBack;
    private int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointment);
        Intent intent = getIntent();
        position = intent.getIntExtra("tab", 0);
        initView();
    }

    public void initView() {
        mPager = (ViewPager) this.findViewById(R.id.appointment_viewpager);
        mRadioGroup = (RadioGroup) this.findViewById(R.id.appointment_radioGroup);
        mBack = (ImageView) this.findViewById(R.id.appointment_back111);
        mBack.setOnClickListener(this);
        fragmentList = new ArrayList<Fragment>();
        //添加fragment
        Fragment firstFr = new Appointment1Fragment();//驾校
        Fragment secondFr = new Appointment2Fragment();//教练
        Fragment thirdFr = new Appointment3Fragment();//场地
        fragmentList.add(firstFr);
        fragmentList.add(secondFr);
        fragmentList.add(thirdFr);
        //给ViewPager设置适配器
        mPager.setAdapter(new AppointAdapter(getSupportFragmentManager(), fragmentList));
        mPager.setOnPageChangeListener(this);
        mRadioGroup.setOnCheckedChangeListener(this);
        mPager.setCurrentItem(position);//设置当前显示标签页为第一页

    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId) {
            case R.id.appointment_radio_tuijian:
                mPager.setCurrentItem(0);
                break;
            case R.id.appointment_radio_haoping:
                mPager.setCurrentItem(1);
                break;
            case R.id.appointment_radio_changdi:
                mPager.setCurrentItem(2);
                break;
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        switch (position) {
            case 0:
                mRadioGroup.check(R.id.appointment_radio_tuijian);
                break;
            case 1:
                mRadioGroup.check(R.id.appointment_radio_haoping);
                break;
            case 2:
                mRadioGroup.check(R.id.appointment_radio_changdi);
                break;
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.appointment_back111:
                onBackPressed();
                break;
        }
    }
}
