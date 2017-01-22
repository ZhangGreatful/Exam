package com.haha.exam.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.haha.exam.R;
import com.haha.exam.activity.ChapterActivity;
import com.haha.exam.activity.CollectActivity;
import com.haha.exam.activity.MyErrorActivity;
import com.haha.exam.activity.MyGradeActivity;
import com.haha.exam.activity.OrderTextActivity;
import com.haha.exam.activity.PracticeStatisticsActivity;
import com.haha.exam.activity.PracticeTextActivity;
import com.haha.exam.activity.RankingActivity;
import com.haha.exam.activity.ReciteActivity;
import com.haha.exam.activity.RuleActivity;
import com.haha.exam.activity.SpecialTextActivity;
import com.haha.exam.activity.TakeOrderActivity;
import com.haha.exam.activity.TechnologyActivity;
import com.haha.exam.bean.TimeBean;
import com.haha.exam.utils.SPUtils;
import com.haha.exam.web.WebInterface;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.wanzheng.driver.network.JsonUtils;
import com.wanzheng.driver.util.SystemUtil;

import okhttp3.Call;
import okhttp3.Response;

/**
 * 科目一页面
 * Created by Administrator on 2016/10/19.
 */
public class KeYiFragment extends Fragment implements View.OnClickListener {

    private RelativeLayout shunxu, moni;
    private LinearLayout suiji, zhuanxiang, zhangjie, beiti;
    private LinearLayout chengji, tongji, paihang, yuyue;
    private LinearLayout fagui, jiqiao, cuoti, shoucang;
    private LinearLayout zai_xian_ke_tang, text_subject;
    private TextView theory_learned_time, theory_unlearn_time, counts;
    private TextView class_learned_time, class_unlearn_time;
    private SPUtils spUtils = new SPUtils();
    private SystemUtil su;
    private String onlyID;
    TimeBean time;
    private Gson gson = new Gson();
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    int learnedTime = time.getData().get(0).getAll_time();
                    Double x = Double.valueOf(learnedTime / 3600);
                    Log.d("KeYiFragment", "x=========" + x);
                    Log.d("KeYiFragment", "x=========" + learnedTime);
                    double unlearned = time.getData().get(0).getCourse1() - x;
                    theory_learned_time.setText(String.valueOf(x));
                    theory_unlearn_time.setText(String.valueOf(unlearned));
            }
        }
    };


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.ke_yi_fragment, container, false);
        counts = (TextView) view.findViewById(R.id.counts);

//        顺序练习
        shunxu = (RelativeLayout) view.findViewById(R.id.shun_xu);
        suiji = (LinearLayout) view.findViewById(R.id.sui_ji);
        zhuanxiang = (LinearLayout) view.findViewById(R.id.zhuan_xiang);
        zhangjie = (LinearLayout) view.findViewById(R.id.zhang_jie);
        beiti = (LinearLayout) view.findViewById(R.id.bei_ti);
//        模拟考试
        moni = (RelativeLayout) view.findViewById(R.id.mo_ni);
        chengji = (LinearLayout) view.findViewById(R.id.cheng_ji);
        tongji = (LinearLayout) view.findViewById(R.id.tong_ji);
        paihang = (LinearLayout) view.findViewById(R.id.pai_hang);
        yuyue = (LinearLayout) view.findViewById(R.id.yu_yue);

        fagui = (LinearLayout) view.findViewById(R.id.fa_gui);
        jiqiao = (LinearLayout) view.findViewById(R.id.ji_qiao);
        cuoti = (LinearLayout) view.findViewById(R.id.cuo_ti);
        shoucang = (LinearLayout) view.findViewById(R.id.shou_cang);
//        zai_xian_ke_tang = (LinearLayout) view.findViewById(R.id.zai_xian_ke_tang);
//        text_subject = (LinearLayout) view.findViewById(R.id.text_subject);

        shunxu.setOnClickListener(this);
        suiji.setOnClickListener(this);
        zhuanxiang.setOnClickListener(this);
        zhangjie.setOnClickListener(this);
        beiti.setOnClickListener(this);
        moni.setOnClickListener(this);
        chengji.setOnClickListener(this);
        tongji.setOnClickListener(this);
        paihang.setOnClickListener(this);
        yuyue.setOnClickListener(this);
        fagui.setOnClickListener(this);
        jiqiao.setOnClickListener(this);
        cuoti.setOnClickListener(this);
        shoucang.setOnClickListener(this);
//        zai_xian_ke_tang.setOnClickListener(this);
//        text_subject.setOnClickListener(this);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
//        initData();
        su = new SystemUtil(getActivity());
        onlyID = su.showOnlyID();
        counts.setText("共" + String.valueOf(spUtils.get(getActivity(), "subject1", 0)) + "题");

    }

    //      获取学时信息
    private void initData() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                OkGo.post(WebInterface.get_li_lun_time)
                        .tag(this)
                        .params("signid", onlyID)
                        .params("subject", "1")
                        .execute(new StringCallback() {
                            @Override
                            public void onSuccess(String s, Call call, Response response) {
                                int num = JsonUtils.parseNum(s);
                                if (num == 1) {
                                    time = gson.fromJson(s, TimeBean.class);
                                    handler.sendEmptyMessage(1);
                                }
                            }
                        });
            }
        }).start();

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        OkGo.getInstance().cancelTag(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.shun_xu:
                Intent intent = new Intent(getActivity(), OrderTextActivity.class);
                intent.putExtra("chapterid", 0);
                intent.putExtra("knowtype", 0);
                startActivity(intent);
                break;
            case R.id.zhuan_xiang:
                Intent intent1 = new Intent(getActivity(), SpecialTextActivity.class);
                startActivity(intent1);
                break;
            case R.id.zhang_jie:
                Intent intent2 = new Intent(getActivity(), ChapterActivity.class);
                startActivity(intent2);
                break;
            case R.id.bei_ti:
                Intent intent3 = new Intent(getActivity(), ReciteActivity.class);
                startActivity(intent3);
                break;
            case R.id.sui_ji:
                Intent intent4 = new Intent(getActivity(), OrderTextActivity.class);
                intent4.putExtra("random", 1);
                startActivity(intent4);
                break;
            case R.id.mo_ni:
                if (!onlyID.equals("")) {
                    Intent intent5 = new Intent(getActivity(), PracticeTextActivity.class);
                    startActivity(intent5);
                } else {
                    Toast.makeText(getActivity(), "请先登录", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.cheng_ji:
                if (!onlyID.equals("")) {
                    Intent intent6 = new Intent(getActivity(), MyGradeActivity.class);
                    startActivity(intent6);
                } else {
                    Toast.makeText(getActivity(), "请先登录", Toast.LENGTH_SHORT).show();
                }

                break;
            case R.id.tong_ji:
                if (!onlyID.equals("")) {
                    Intent intent7 = new Intent(getActivity(), PracticeStatisticsActivity.class);
                    startActivity(intent7);
                } else {
                    Toast.makeText(getActivity(), "请先登录", Toast.LENGTH_SHORT).show();
                }

                break;
            case R.id.pai_hang:
                if (!onlyID.equals("")) {
                    Intent intent8 = new Intent(getActivity(), RankingActivity.class);
                    startActivity(intent8);
                } else {
                    Toast.makeText(getActivity(), "请先登录", Toast.LENGTH_SHORT).show();
                }

                break;
            case R.id.yu_yue:
                if (!onlyID.equals("")) {
                    Intent intent9 = new Intent(getActivity(), TakeOrderActivity.class);
                    startActivity(intent9);
                } else {
                    Toast.makeText(getActivity(), "请先登录", Toast.LENGTH_SHORT).show();
                }

                break;
            case R.id.fa_gui:
                Intent intent10 = new Intent(getActivity(), RuleActivity.class);
                intent10.putExtra("from", 0);
                startActivity(intent10);
                break;
            case R.id.ji_qiao:
                Intent intent11 = new Intent(getActivity(), TechnologyActivity.class);
                startActivity(intent11);
                break;
            case R.id.cuo_ti:
                if (!onlyID.equals("")) {
                    Intent intent12 = new Intent(getActivity(), MyErrorActivity.class);
                    startActivity(intent12);
                } else {
                    Toast.makeText(getActivity(), "请先登录", Toast.LENGTH_SHORT).show();
                }

                break;
            case R.id.shou_cang:
                if (!onlyID.equals("")) {
                    Intent intent13 = new Intent(getActivity(), CollectActivity.class);
                    startActivity(intent13);
                } else {
                    Toast.makeText(getActivity(), "请先登录", Toast.LENGTH_SHORT).show();
                }

                break;
//            case R.id.zai_xian_ke_tang:
//                final ClassDialog dialog = new ClassDialog(getActivity());
//                dialog.setYesMessage("确定");
//                dialog.setMessage("具体时间请看通知或咨询驾校");
//                dialog.show();
//                dialog.setYesOnclickListener(new ClassDialog.onYesOnclickListener() {
//                    @Override
//                    public void onYesClick() {
//                        dialog.dismiss();
//                    }
//                });
//                break;
//            case R.id.text_subject:
//                Intent intent14 = new Intent(getActivity(), Subject1Activity.class);
//                intent14.putExtra("subject", "1");
//                startActivity(intent14);
//                break;
        }
    }
}
