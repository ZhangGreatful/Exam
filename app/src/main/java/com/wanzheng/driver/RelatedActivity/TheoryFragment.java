package com.wanzheng.driver.RelatedActivity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.haha.exam.R;

/**
 * 理论学时信息展示界面
 * Created by Administrator on 2017/1/9.
 */

public class TheoryFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.theory_fragment,container,false);
        return view;
    }
}
