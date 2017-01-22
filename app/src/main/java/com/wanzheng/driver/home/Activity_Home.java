package com.wanzheng.driver.home;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

import com.haha.exam.R;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.wanzheng.driver.Entity.BaseResult;
import com.wanzheng.driver.network.JsonUtils;
import com.wanzheng.driver.network.NetInterface;
import com.wanzheng.driver.network.NetWorkCallBack;
import com.wanzheng.driver.network.NetWorkUtils;
import com.wanzheng.driver.util.RecordsEntity;
import com.wanzheng.driver.util.SystemUtil;
import com.wanzheng.driver.view.TasksCompletedView;

import java.text.NumberFormat;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Response;

public class Activity_Home extends Activity implements OnClickListener {

    private TextView progress_ptxt2;
    private TasksCompletedView tasks_view2;
    private TextView tv_alread2_zhi;
    private TextView tv_all2_zhi;
    private TextView progress_ptxt3;
    private TasksCompletedView tasks_view3;
    private TextView tv_alread3_zhi;
    private TextView tv_all3_zhi;
    private int xid;
    private SystemUtil sys;
    private ArrayList<RecordsEntity> list;
    private Handler handler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    for (int i = 0; i < list.size(); i++) {
                        if ("科二".equals(list.get(i).getSubject())) {
                            //获取格式化对象
                            double percent = 0;
                            NumberFormat nt = NumberFormat.getPercentInstance();
                            //设置百分数精确度2即保留两位小数
                            nt.setMinimumFractionDigits(1);
                            //最后格式化并输出
                            if (list.get(i).getTotalTimes() != 0) {
                                percent = list.get(i).getHasTimes() / (double) list.get(i).getTotalTimes();
                                //当前进度百分比
                                tasks_view2.setProgress((int) (percent * 100));
                                //设置圆圈中进度
                                progress_ptxt2.setText(nt.format(percent));
                            }
                            tasks_view2.setmTotalProgress(100);
                            //已学时间
                            tv_alread2_zhi.setText(list.get(i).getHasTimes() + "分钟");
                            //一共时间
                            tv_all2_zhi.setText(list.get(i).getTotalTimes() - list.get(i).getHasTimes() + "分钟");
                        }
                        if ("科三".equals(list.get(i).getSubject())) {
                            //获取格式化对象
                            double percent = 0;
                            NumberFormat nt = NumberFormat.getPercentInstance();
                            //设置百分数精确度2即保留两位小数
                            nt.setMinimumFractionDigits(1);
                            //最后格式化并输出
                            if (list.get(i).getTotalTimes() != 0) {
                                percent = list.get(i).getHasTimes() / (double) list.get(i).getTotalTimes();
                                //当前进度百分比
                                tasks_view3.setProgress((int) (percent * 100));
                                //设置圆圈中进度
                                progress_ptxt3.setText(nt.format(percent));
                            }
                            tasks_view3.setmTotalProgress(100);
                            //已学时间
                            tv_alread3_zhi.setText(list.get(i).getHasTimes() + "分钟");
                            //一共时间
                            tv_all3_zhi.setText(list.get(i).getTotalTimes() - list.get(i).getHasTimes() + "分钟");
                        }
                    }

                    break;

                default:
                    break;
            }
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);
        initview();
        initdata();
    }

    @Override
    protected void onStart() {
        // TODO Auto-generated method stub
        super.onStart();
    }


    private void initdata() {
        sys = new SystemUtil(this);
        xid = sys.showUid();
        if (xid != -1) {
//		new NetWorkUtils().getInstance()
//		.work(new NetInterface().getInstance().getRecords(
//				xid), getRecordsCallBack);
            String s = "http://120.26.118.158:8082/xueshi.ashx?do=querymyxueshi&xid=" + xid;
            OkGo.post(s)
                    .tag(this)
                    .execute(stringCallBack);
            OkGo.post(s)
                    .tag(this)
                    .execute(stringCallBack);
            // 请求网络获取学院信息（以下为科二测试值）------------------------------------------------------------------------------
            //自定义进度条
            //一共百分比

        }
    }

    private StringCallback stringCallBack = new StringCallback() {
        @Override
        public void onSuccess(String s, Call call, Response response) {
            list = JsonUtils.parseRecords(s);
            if (list.size() != 0) {
                handler.sendEmptyMessage(1);
            }
        }
    };

    private void initview() {

        findViewById(R.id.titlebar_tv).setVisibility(View.GONE);
        findViewById(R.id.titlebar_back).setVisibility(View.GONE);
        findViewById(R.id.titlebar_layout_left111).setVisibility(View.VISIBLE);
        ImageView titlebar_back = (ImageView) findViewById(R.id.titlebar_back111);
        titlebar_back.setImageResource(R.drawable.title_voice);
        titlebar_back.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Activity_Home.this, Activity_VPR.class));
            }
        });
        TextView tv_title = (TextView) findViewById(R.id.tv_title111);
        tv_title.setText("学时进度");
        TextView titlebar_tuichu = (TextView) findViewById(R.id.titlebar_tuichu111);
        titlebar_tuichu.setText("退出登录");
        titlebar_tuichu.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // 退出登录执行的操作------------------------------------------------------------------------------
                sys.saveUid(-1);
                sys.saveZhifuR(-1);
                sys.saveRegesterState(-1);
                sys.savePwd("");
                Intent intent = new Intent(Activity_Home.this, LoginActivity.class);
                startActivity(intent);
                finish();
                // 退出登录执行的操作------------------------------------------------------------------------------
            }
        });
        // progress_ptxt2 %
        // tasks_view2 进
        // tv_alread2_zhi
        // tv_all2_zhi
        // 科二
        progress_ptxt2 = (TextView) findViewById(R.id.progress_ptxt2);
        tasks_view2 = (TasksCompletedView) findViewById(R.id.tasks_view2);
        tv_alread2_zhi = (TextView) findViewById(R.id.tv_alread2_zhi);
        tv_all2_zhi = (TextView) findViewById(R.id.tv_all2_zhi);
        // 科三
        progress_ptxt3 = (TextView) findViewById(R.id.progress_ptxt3);
        tasks_view3 = (TasksCompletedView) findViewById(R.id.tasks_view3);
        tv_alread3_zhi = (TextView) findViewById(R.id.tv_alread3_zhi);
        tv_all3_zhi = (TextView) findViewById(R.id.tv_all3_zhi);

        findViewById(R.id.bt_xueshirefresh).setOnClickListener(this);
        findViewById(R.id.bt_order).setOnClickListener(this);
        findViewById(R.id.bt_ketwo).setOnClickListener(this);
        findViewById(R.id.bt_kethree).setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.bt_kethree:
            case R.id.bt_ketwo:
                //进入二维码扫描界面
                Intent intent = new Intent(Activity_Home.this, Activity_QRcode.class);
                startActivity(intent);
                break;
            case R.id.bt_xueshirefresh:
                // 刷新信息操作
                // ---------------------------------刷新信息操作------------------------------------------------------------------------------
                if (xid != -1)
                    new NetWorkUtils().getInstance()
                            .work(new NetInterface().getInstance().getRecords(
                                    xid), getRecordsCallBack);
                // ---------------------------------刷新信息操作------------------------------------------------------------------------------

                break;
            case R.id.bt_order:
                   Intent intent2 = new Intent(Activity_Home.this, Activity_OrderShow.class);
                startActivity(intent2);
                break;
            default:
                break;
        }

    }

    private NetWorkCallBack<BaseResult> getRecordsCallBack = new NetWorkCallBack<BaseResult>() {

        @Override
        public void onComplete(String json) {
            list = JsonUtils.parseRecords(json);
            if (list.size() != 0) {
                handler.sendEmptyMessage(1);
            }
        }
    };

}
