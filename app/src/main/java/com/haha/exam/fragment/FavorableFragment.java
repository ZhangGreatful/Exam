package com.haha.exam.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.haha.exam.R;
import com.haha.exam.activity.Subject1Activity;
import com.haha.exam.dialog.EndTimingDialog;
import com.haha.exam.dialog.MyDialog;
import com.wanzheng.driver.home.FaceLogin;
import com.wanzheng.driver.util.SystemUtil;

import static com.haha.exam.R.id.date;

/**
 * Created by Administrator on 2016/10/19.
 */
public class FavorableFragment extends Fragment implements View.OnClickListener {

    private RelativeLayout keyi, keer, kesan, kesan_theory;
    private TextView start_Timing;
    private int check_success;
    private SystemUtil su;
    private String phone;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.activity_study, container, false);
        keyi = (RelativeLayout) view.findViewById(R.id.ke_yi);
        keer = (RelativeLayout) view.findViewById(R.id.ke_er);
        kesan = (RelativeLayout) view.findViewById(R.id.ke_san);
        kesan_theory = (RelativeLayout) view.findViewById(R.id.ke_san_theory);
        start_Timing = (TextView) view.findViewById(R.id.start_timing);


        start_Timing.setOnClickListener(this);
        keyi.setOnClickListener(this);
        keer.setOnClickListener(this);
        kesan.setOnClickListener(this);
        kesan_theory.setOnClickListener(this);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        su = new SystemUtil(getActivity());
        if (su.showOnlyID().equals("")) {//游客模式下不计时
            start_Timing.setVisibility(View.GONE);
        }
        phone = su.showPhone();
    }

    @Override
    public void onResume() {
        super.onResume();
        check_success = su.showTiming();
        if (check_success == 1) {
            start_Timing.setText("正在计时");
        } else {
            start_Timing.setText("开始计时");
            su.removeLearnedTime();
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.start_timing:
                if (check_success!=1) {//未登录状态
                    final MyDialog dialog = new MyDialog(getActivity());
                    dialog.setMessage("是否开始计时？");
                    dialog.setNoMessage("不计时");
                    dialog.setYesMessage("计时");
                    dialog.show();
                    dialog.setYesOnclickListener("", new MyDialog.onYesOnclickListener() {
                        @Override
                        public void onYesClick() {
                            Intent intent = new Intent();
                            intent.putExtra("phone", phone);
                            intent.putExtra("from", 1);
                            intent.setClass(getActivity(), FaceLogin.class);
                            startActivity(intent);
                            dialog.dismiss();
                        }
                    });
                    dialog.setNoOnclickListener("", new MyDialog.onNoOnclickListener() {
                        @Override
                        public void onNoClick() {
                            dialog.dismiss();
                            su.saveTiming(0);// 0  不计时模式
                        }
                    });
                } else {//登录状态
//                    获取本次学时信息
//
//                    int time = su.showLearnedTime();
//                    Log.d("HomeFragment", "获取学时时间===========" + String.valueOf(time));
//                    String learnedTime = formatNumberToHourMinuteSecond(time);
//                    Log.d("HomeFragment", "learnedTime============" + learnedTime);
                    final EndTimingDialog dialog = new EndTimingDialog(getActivity());
                    String time = formatNumberToHourMinuteSecond(su.showLearnedTime());
                    dialog.setTime(time);
//                    dialog.setTime("是否结束计时？");
                    dialog.setNoMessage("继续训练");
                    dialog.setYesMessage("结束计时");
                    dialog.show();
                    dialog.setYesOnclickListener(new EndTimingDialog.onYesOnclickListener() {
                        @Override
                        public void onYesClick() {
                            Intent intent = new Intent();
                            intent.putExtra("phone", phone);
                            intent.putExtra("from", 1);
                            intent.setClass(getActivity(), FaceLogin.class);
                            startActivity(intent);
                            dialog.dismiss();

                        }
                    });
                    dialog.setNoOnclickListener(new EndTimingDialog.onNoOnclickListener() {
                        @Override
                        public void onNoClick() {
                            dialog.dismiss();

                        }
                    });
                }
                break;
            case R.id.ke_yi:
                if (su.showOnlyID().equals("")) {
                    Toast.makeText(getActivity(), "请先登录", Toast.LENGTH_SHORT).show();
                } else {
                    Intent intent = new Intent(getActivity(), Subject1Activity.class);
                    intent.putExtra("subject", "1");
                    startActivity(intent);
                }

                break;
            case R.id.ke_er:
                if (su.showOnlyID().equals("")) {
                    Toast.makeText(getActivity(), "请先登录", Toast.LENGTH_SHORT).show();
                } else {
                    Intent intent1 = new Intent(getActivity(), Subject1Activity.class);
                    intent1.putExtra("subject", "2");
                    startActivity(intent1);
                }

                break;
            case R.id.ke_san:
                if (su.showOnlyID().equals("")) {
                    Toast.makeText(getActivity(), "请先登录", Toast.LENGTH_SHORT).show();
                } else {
                    Intent intent2 = new Intent(getActivity(), Subject1Activity.class);
                    intent2.putExtra("subject", "3");
                    startActivity(intent2);
                }

                break;
            case R.id.ke_san_theory:
                if (su.showOnlyID().equals("")) {
                    Toast.makeText(getActivity(), "请先登录", Toast.LENGTH_SHORT).show();
                } else {
                    Intent intent3 = new Intent(getActivity(), Subject1Activity.class);
                    intent3.putExtra("subject", "4");
                    startActivity(intent3);
                }

                break;
        }
    }

    public String formatNumberToHourMinuteSecond(int dateDou) {
        String ft = "00:00:00";//没匹配上时:1.等于0时; 2.大于等于86400时.
//        BigDecimal d = new BigDecimal(dateDou).setScale(0, BigDecimal.ROUND_HALF_UP);//四舍五入
//        int date = Integer.valueOf(d.toString());
        if (dateDou > 0 && dateDou < 60) {
            ft = "00:00:" + (dateDou >= 10 ? dateDou : "0" + dateDou);
        } else if (dateDou >= 60 && dateDou < 3600) {
            ft = "00:" + (dateDou / 60 >= 10 ? dateDou / 60 : "0" + dateDou / 60) + ":" + (dateDou % 60 >= 10 ? dateDou % 60 : "0" + dateDou % 60);
        } else if (dateDou >= 3600 && dateDou < 86400) {
            ft = (dateDou / 3600 >= 10 ? dateDou / 3600 : "0" + dateDou / 3600) + ":" + (dateDou % 3600 / 60 >= 10 ? dateDou % 3600 / 60 : "0" + dateDou % 3600 / 60) + ":" + (date % 60 >= 10 ? date % 60 : "0" + date % 60);
        }
        return ft;
    }
}
