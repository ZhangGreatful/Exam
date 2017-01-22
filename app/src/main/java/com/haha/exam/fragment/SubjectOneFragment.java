package com.haha.exam.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.gson.Gson;
import com.haha.exam.R;
import com.haha.exam.bean.TimeBean;
import com.haha.exam.utils.SPUtils;
import com.lzy.okgo.OkGo;
import com.wanzheng.driver.util.SystemUtil;
import com.wanzheng.driver.view.TasksCompletedView;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

import de.greenrobot.event.EventBus;
import de.greenrobot.event.Subscribe;

/**
 * Created by Administrator on 2016/11/19.
 */

public class SubjectOneFragment extends Fragment {

    private SPUtils spUtils;
    private TextView progress_ptxt2;
    private TasksCompletedView tasks_view1;
    private String onlyID;
    private SystemUtil sys;
    private List<TimeBean.DataBean> list = new ArrayList<>();
    private Gson gson = new Gson();
    private Handler handler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    for (int i = 0; i < list.size(); i++) {
                        //获取格式化对象
                        int alltime = list.get(0).getAll_time() / 60;
                        int course = list.get(0).getCourse1() * 60;
                        if (list.get(0).getAll_time() != 0) {
//                        DecimalFormat df = new DecimalFormat("0.0");
                            NumberFormat nt = NumberFormat.getPercentInstance();
                            //设置百分数精确度2即保留两位小数
                            nt.setMinimumFractionDigits(1);
                            //最后格式化并输出
                            float percent = (float) alltime / course;
                            Log.d("SubjectOneFragment", "percent==========" + percent);
                            if (alltime > course) {
                                tasks_view1.setProgress(100);
                                progress_ptxt2.setText("100%");
                            } else {
                                //当前进度百分比
                                tasks_view1.setProgress((int) (percent * 100));
                                progress_ptxt2.setText(nt.format(percent));
                            }
                            //设置圆圈中进度
                            tasks_view1.setmTotalProgress(100);
                            //已学时间
                            practicedTime.setText(String.valueOf(alltime) + "分钟");
                        }
//                        学时时间
                        unpracticedTime.setText(course + "分钟");

                    }

                    break;

                default:
                    break;
            }
        }

    };


    private boolean isfirst = true;

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        if (!isfirst) {
            handler.sendEmptyMessage(1);
            Log.e("Eventbus", "加载");
        }

        super.setUserVisibleHint(isVisibleToUser);
    }

    private TextView practicedTime, unpracticedTime;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //注册事件
        EventBus.getDefault().register(this);
        Log.e("eventbus", "sub`1");

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.subject_one_fragment, container, false);
        progress_ptxt2 = (TextView) view.findViewById(R.id.progress_ptxt2);
        tasks_view1 = (TasksCompletedView) view.findViewById(R.id.tasks_view1);
        practicedTime = (TextView) view.findViewById(R.id.practicedTime);
        unpracticedTime = (TextView) view.findViewById(R.id.unpracticedTime);
        return view;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        sys = new SystemUtil(getActivity());
        onlyID = sys.showOnlyID();
    }

    @Subscribe
    public void onEventMainThread(TimeBean msg) {
        isfirst = false;
        TimeBean time = msg;
//        spUtils.put(getActivity(), "subject4_alltime", time.getData().get(0).getCourse4());
//        spUtils.put(getActivity(), "subject3_alltime", time.getData().get(0).getCourse3());
//        spUtils.put(getActivity(), "subject2_alltime", time.getData().get(0).getCourse2());
        list = time.getData();
        handler.sendEmptyMessage(1);
        Log.e("eventbus", "--------------------------1");

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        OkGo.getInstance().cancelTag(this);
        //解除事件订阅
        EventBus.getDefault().unregister(this);
    }
};

