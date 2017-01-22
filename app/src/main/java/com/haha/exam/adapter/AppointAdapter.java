package com.haha.exam.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;

/**
 * Created by shaowei on 2016/12/13.
 *  预约界面 viewpager 的adapter
 */

public class AppointAdapter extends FragmentPagerAdapter {
    ArrayList<Fragment> list;
    public AppointAdapter(FragmentManager fm, ArrayList<Fragment> list) {
        super(fm);
        this.list=list;
    }

    @Override
    public Fragment getItem(int position) {
        return list.get(position);
    }

    @Override
    public int getCount() {
        return list.size();
    }
}
