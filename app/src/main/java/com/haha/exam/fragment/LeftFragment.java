package com.haha.exam.fragment;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.DrawerLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.haha.exam.R;
import com.haha.exam.activity.SettingActivity;

/**
 * 侧滑菜单
 * Created by Administrator on 2016/10/18.
 */
public class LeftFragment extends Fragment implements View.OnClickListener {

    private DrawerLayout drawerLayout;
    private LinearLayout jiaxiao, tiku, xiaoxi, yijian, pingjia, shezhi;
    private RelativeLayout mineInfo;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.left_fragment, container, false);
        mineInfo = (RelativeLayout) view.findViewById(R.id.mine_info);
        jiaxiao = (LinearLayout) view.findViewById(R.id.jia_xiao);
        tiku = (LinearLayout) view.findViewById(R.id.ti_ku);
        xiaoxi = (LinearLayout) view.findViewById(R.id.xiao_xi);
        yijian = (LinearLayout) view.findViewById(R.id.yi_jian);
        pingjia = (LinearLayout) view.findViewById(R.id.ping_jia);
        shezhi = (LinearLayout) view.findViewById(R.id.she_zhi);

        mineInfo.setOnClickListener(this);
        jiaxiao.setOnClickListener(this);
        tiku.setOnClickListener(this);
        xiaoxi.setOnClickListener(this);
        yijian.setOnClickListener(this);
        pingjia.setOnClickListener(this);
        shezhi.setOnClickListener(this);
        return view;
    }

    //暴露给Activity，用于传入DrawerLayout，因为点击后想关掉DrawerLayout
    public void setDrawerLayout(DrawerLayout drawer_layout) {
        this.drawerLayout = drawer_layout;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.mine_info:
                break;
            case R.id.jia_xiao:
                break;
            case R.id.ti_ku:
                break;
            case R.id.xiao_xi:
                break;
            case R.id.yi_jian:
                break;
            case R.id.ping_jia:
                break;
            case R.id.she_zhi:
                Intent intent = new Intent(getActivity(), SettingActivity.class);
                startActivity(intent);
                break;
        }
    }
}
