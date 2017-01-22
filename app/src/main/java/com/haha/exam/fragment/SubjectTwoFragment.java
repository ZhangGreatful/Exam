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
import com.haha.exam.bean.TimeSub2;
import com.haha.exam.utils.SPUtils;
import com.haha.exam.web.WebInterface;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.wanzheng.driver.network.JsonUtils;
import com.wanzheng.driver.util.RecordsEntity;
import com.wanzheng.driver.util.SystemUtil;
import com.wanzheng.driver.view.TasksCompletedView;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

import de.greenrobot.event.EventBus;
import de.greenrobot.event.Subscribe;
import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by Administrator on 2016/11/19.
 */

public class SubjectTwoFragment extends Fragment {

    private boolean isViewPrepared = false;//是否初始化完成
    private long refreshTime = 0;
    int hasTime;
    int totalTime;
    private SPUtils spUtils;
    private TimeBean time;
    private Gson gson = new Gson();

    private TextView progress_ptxt2;
    private TasksCompletedView tasks_view2;
    private int xid;
    private String onlyID;
    private SystemUtil sys;
    private ArrayList<RecordsEntity> list=new ArrayList<>();
    private List<TimeBean.DataBean> list1 = new ArrayList<>();
    private TextView practiceDistance, practiceAllDistance;
    private TextView theoryLearned, theoryAllTime;
    private TextView practicedTime, practiceAllTime;
    private Handler handler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
//                  获取理论学时
//                    int learned = (int) spUtils.get(getActivity(), "subject2_learned", 0);
//                    int all = (int) spUtils.get(getActivity(), "subject2_alltime", 0);
//                    int alltime = learned / 60;
//                    int course = all * 60;
//                    //已学时间
//                    theoryLearned.setText(String.valueOf(alltime) + "分钟");
//                    theoryAllTime.setText(String.valueOf(course) + "分钟");

//                    实践学时
                    hasTime = 0;
                    totalTime = 0;


                    for (int i = 0; i < list.size(); i++) {
                        if ("科二".equals(list.get(i).getSubject())) {
                            hasTime = list.get(i).getHasTimes();
                            totalTime = list.get(i).getTotalTimes();
                            //获取格式化对象
                        }
                    }
//                    double percent = 0;
//                    NumberFormat nt = NumberFormat.getPercentInstance();
//                    //设置百分数精确度2即保留两位小数
//                    nt.setMinimumFractionDigits(1);
//                    //最后格式化并输出
//                    if ((hasTime + alltime) != 0) {
//                        percent = (hasTime + alltime) / (double) (totalTime + course);
//                        Log.d("SubjectTwoFragment", "percent==============" + percent);
//                        if ((hasTime + alltime) > (totalTime + course)) {
//                            tasks_view2.setProgress(100);
//                            progress_ptxt2.setText("100%");
//                        } else {
//                            //当前进度百分比
//                            tasks_view2.setProgress((int) (percent * 100));
//                            progress_ptxt2.setText(nt.format(percent));
//                        }
//                        //设置圆圈中进度
//                        tasks_view2.setmTotalProgress(100);
//                    }
                    //已学时间
                    practicedTime.setText(hasTime + "分钟");
                    //总时间
                    practiceAllTime.setText(totalTime + "分钟");

                    break;
                case 2:
//                    int learned = (int) spUtils.get(getActivity(), "subject2_learned", 0);
//                    int all = (int) spUtils.get(getActivity(), "subject2_alltime", 0);
                    if (time != null) {
                        int learned = time.getData().get(0).getAll_time();
                        int all = time.getData().get(0).getCourse2();
                        int alltime = learned / 60;
                        int course = all * 60;
                        //已学时间
                        theoryLearned.setText(String.valueOf(alltime) + "分钟");
                        theoryAllTime.setText(String.valueOf(course) + "分钟");
                        double percent = 0;
                        NumberFormat nt = NumberFormat.getPercentInstance();
                        //设置百分数精确度2即保留两位小数
                        nt.setMinimumFractionDigits(1);
                        //最后格式化并输出
                        if ((hasTime + alltime) != 0) {
                            percent = (hasTime + alltime) / (double) (totalTime + course);
                            Log.d("SubjectTwoFragment", "percent==============" + percent);
                            if ((hasTime + alltime) > (totalTime + course)) {
                                tasks_view2.setProgress(100);
                                progress_ptxt2.setText("100%");
                            } else {
                                //当前进度百分比
                                tasks_view2.setProgress((int) (percent * 100));
                                progress_ptxt2.setText(nt.format(percent));
                            }
                            //设置圆圈中进度
                            tasks_view2.setmTotalProgress(100);
                        }

                    }
                    break;

                default:
                    break;
            }
        }

    };


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.subject_two_fragment, container, false);
//        roundProgressBar3 = (RoundProgressBar) view.findViewById(R.id.roundProgressBar3);
        progress_ptxt2 = (TextView) view.findViewById(R.id.progress_ptxt2);
        tasks_view2 = (TasksCompletedView) view.findViewById(R.id.tasks_view2);

        theoryLearned = (TextView) view.findViewById(R.id.theory_learned);
        theoryAllTime = (TextView) view.findViewById(R.id.theory_alltime);

        practicedTime = (TextView) view.findViewById(R.id.practicedTime);
        practiceAllTime = (TextView) view.findViewById(R.id.practice_alltime);
        practiceDistance = (TextView) view.findViewById(R.id.practiceDistance);
        practiceAllDistance = (TextView) view.findViewById(R.id.practice_alldistance);
        refreshTime = 0;
        if (getUserVisibleHint()) {
//            initdata();
//            initdata1();
        }
        return view;
    }


    private boolean isfirst = true;

    public void setUserVisibleHint(boolean isVisibleToUser) {
        if (isVisibleToUser && isVisible()) {
            if (refreshTime == 0 || (refreshTime != 0 && (System.currentTimeMillis() - refreshTime) > 5 * 60 * 1000)) {
                if (!isfirst) {
                    handler.sendEmptyMessage(1);
                    handler.sendEmptyMessage(2);
                }

            }
        }
        super.setUserVisibleHint(isVisibleToUser);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
//        initdata();
    }

    private void initdata() {
        sys = new SystemUtil(getActivity());
        xid = sys.showUid();
        onlyID = sys.showOnlyID();
        refreshTime = System.currentTimeMillis();
        if (xid != -1) {
//            new NetWorkUtils().getInstance()
//                    .work(new NetInterface().getInstance().getRecords(
//                            xid), getRecordsCallBack);
            String s = "http://120.26.118.158:8082/xueshi.ashx?do=querymyxueshi&xid=" + xid;
            OkGo.post(s)
                    .tag(this)
                    .execute(getRecordsCallBack);
        }
        if (onlyID != null) {
            OkGo.post(WebInterface.get_li_lun_time)
                    .tag(this)
                    .params("signid", onlyID)
                    .params("subject", "2")
                    .execute(getRecordsCallBack1);
        }

    }


    private StringCallback getRecordsCallBack = new StringCallback() {
        @Override
        public void onSuccess(String s, Call call, Response response) {
            Log.d("SubjectTwoFragment", "=============" + s);
            list = JsonUtils.parseRecords(s);
            if (list.size() != 0) {
                handler.sendEmptyMessage(1);
            }
        }
    };
    private StringCallback getRecordsCallBack1 = new StringCallback() {
        @Override
        public void onSuccess(String s, Call call, Response response) {
            Log.d("SubjectTwoFragment", "++++++++++++++++++++" + s);
            int num = JsonUtils.parseNum(s);
            if (num == 1) {
                time = gson.fromJson(s, TimeBean.class);
                handler.sendEmptyMessage(2);
            }
        }
    };

    @Subscribe
    public void onEventMainThread(ArrayList<RecordsEntity> msg) {
        list = msg;
        if (list == null) {
            return;
        }
        if (list.size() != 0) {
            isfirst = false;
            handler.sendEmptyMessage(1);
        }


    }

    @Subscribe
    public void onEventMainThread(TimeSub2 msg) {
        time = msg.getTimeBean();
        isfirst = false;
        handler.sendEmptyMessage(2);


    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }


};


