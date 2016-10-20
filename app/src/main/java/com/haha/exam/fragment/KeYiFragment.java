package com.haha.exam.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.haha.exam.R;
import com.haha.exam.activity.OrderTextActivity;
import com.haha.exam.activity.SpecialTextActivity;

/**
 * 科目一页面
 * Created by Administrator on 2016/10/19.
 */
public class KeYiFragment extends Fragment implements View.OnClickListener {

    private LinearLayout shunxu, suiji, zhuanxiang, zhangjie, yicuo;
    private LinearLayout moni, chengji, tongji, paihang, xuyuan;
    private LinearLayout fagui, jiqiao, cuoti, shoucang;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.ke_yi_fragment, container, false);
//        顺序练习
        shunxu = (LinearLayout) view.findViewById(R.id.shun_xu);
        suiji = (LinearLayout) view.findViewById(R.id.sui_ji);
        zhuanxiang = (LinearLayout) view.findViewById(R.id.zhuan_xiang);
        zhangjie = (LinearLayout) view.findViewById(R.id.zhang_jie);
        yicuo = (LinearLayout) view.findViewById(R.id.yi_cuo);
//        模拟考试
        moni = (LinearLayout) view.findViewById(R.id.mo_ni);
        chengji = (LinearLayout) view.findViewById(R.id.cheng_ji);
        tongji = (LinearLayout) view.findViewById(R.id.tong_ji);
        paihang = (LinearLayout) view.findViewById(R.id.pai_hang);
        xuyuan = (LinearLayout) view.findViewById(R.id.xu_yuan);

        fagui = (LinearLayout) view.findViewById(R.id.fa_gui);
        jiqiao = (LinearLayout) view.findViewById(R.id.ji_qiao);
        cuoti = (LinearLayout) view.findViewById(R.id.cuo_ti);
        shoucang = (LinearLayout) view.findViewById(R.id.shou_cang);

        shunxu.setOnClickListener(this);
        suiji.setOnClickListener(this);
        zhuanxiang.setOnClickListener(this);
        zhangjie.setOnClickListener(this);
        yicuo.setOnClickListener(this);
        moni.setOnClickListener(this);
        chengji.setOnClickListener(this);
        tongji.setOnClickListener(this);
        paihang.setOnClickListener(this);
        xuyuan.setOnClickListener(this);
        fagui.setOnClickListener(this);
        jiqiao.setOnClickListener(this);
        cuoti.setOnClickListener(this);
        shoucang.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.shun_xu:
                Intent intent=new Intent(getActivity(), OrderTextActivity.class);
                startActivity(intent);
                break;
            case R.id.zhuan_xiang:
                Intent intent1=new Intent(getActivity(), SpecialTextActivity.class);
                startActivity(intent1);
                break;
            case R.id.zhang_jie:
                Intent intent2=new Intent(getActivity(), SpecialTextActivity.class);
//                startActivity(intent2);
                break;
            case R.id.yi_cuo:
                Intent intent3=new Intent(getActivity(), SpecialTextActivity.class);
//                startActivity(intent3);
                break;
            case R.id.sui_ji:
                Intent intent4=new Intent(getActivity(), SpecialTextActivity.class);
//                startActivity(intent4);
                break;
            case R.id.mo_ni:
                Intent intent5=new Intent(getActivity(), SpecialTextActivity.class);
//                startActivity(intent5);
                break;
            case R.id.cheng_ji:
                Intent intent6=new Intent(getActivity(), SpecialTextActivity.class);
                startActivity(intent6);
                break;
            case R.id.tong_ji:
                Intent intent7=new Intent(getActivity(), SpecialTextActivity.class);
//                startActivity(intent7);
                break;
            case R.id.pai_hang:
                Intent intent8=new Intent(getActivity(), SpecialTextActivity.class);
//                startActivity(intent8);
                break;
            case R.id.xu_yuan:
                Intent intent9=new Intent(getActivity(), SpecialTextActivity.class);
//                startActivity(intent9);
                break;
            case R.id.fa_gui:
                Intent intent10=new Intent(getActivity(), SpecialTextActivity.class);
//                startActivity(intent10);
                break;
            case R.id.ji_qiao:
                Intent intent11=new Intent(getActivity(), SpecialTextActivity.class);
//                startActivity(intent11);
                break;
            case R.id.cuo_ti:
                Intent intent12=new Intent(getActivity(), SpecialTextActivity.class);
//                startActivity(intent12);
                break;
            case R.id.shou_cang:
                Intent intent13=new Intent(getActivity(), SpecialTextActivity.class);
//                startActivity(intent13);
                break;
        }
    }
}