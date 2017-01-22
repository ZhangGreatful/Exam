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
import com.haha.exam.bean.TimeSub4;
import com.haha.exam.utils.SPUtils;
import com.haha.exam.web.WebInterface;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.wanzheng.driver.network.JsonUtils;
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

public class SubjectFourFragment extends Fragment {

    TimeBean time;
    int hasTime;
    int totalTime;
    private long refreshTime = 0;
    private SPUtils spUtils;
    private TextView progress_ptxt2;
    private TasksCompletedView tasks_view1;
    private String onlyID;
    private SystemUtil sys;
    private List<TimeBean.DataBean> list=new ArrayList<>();
    private Gson gson = new Gson();
    private Handler handler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    for (int i = 0; i < list.size(); i++) {
                        int course = list.get(0).getCourse4() * 60;
                        if (list.get(0).getAll_time() != 0) {
                            int alltime = list.get(0).getAll_time() / 60;
                            Log.d("SubjectOneFragment", "learnedtime4=========" + alltime);
//                        DecimalFormat df = new DecimalFormat("0.0");
                            NumberFormat nt = NumberFormat.getPercentInstance();
                            //设置百分数精确度2即保留两位小数
                            nt.setMinimumFractionDigits(1);
                            //最后格式化并输出
                            float percent = (float) alltime / course;
                            Log.d("SubjectOneFragment", "percent4==========" + percent);
                            //当前进度百分比
                            tasks_view1.setProgress((int) (percent * 100));
                            //设置圆圈中进度
                            progress_ptxt2.setText(nt.format(percent));
                            tasks_view1.setmTotalProgress(100);
                            //已学时间
                            practicedTime.setText(String.valueOf(alltime) + "分钟");

                        }
                        //剩余时间
                        unpracticedTime.setText(course + "分钟");
                    }

                    break;

                default:
                    break;
            }
        }

    };

    private TextView practicedTime, unpracticedTime;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
        Log.e("eventbus","sub4");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.subject_one_fragment, container, false);
        progress_ptxt2 = (TextView) view.findViewById(R.id.progress_ptxt2);
        tasks_view1 = (TasksCompletedView) view.findViewById(R.id.tasks_view1);
        practicedTime = (TextView) view.findViewById(R.id.practicedTime);
        unpracticedTime = (TextView) view.findViewById(R.id.unpracticedTime);
        spUtils = new SPUtils();
//        int learned = (int) spUtils.get(getActivity(), "subject4_learned", 0);
//        int all = (int) spUtils.get(getActivity(), "subject4_alltime", 0);
//        int alltime = learned / 60;
//        int course = all * 60;
//        Log.d("SubjectOneFragment", "learnedtime41=========" + course);
//        Log.d("SubjectOneFragment", "learnedtime42=========" + learned);
////                        DecimalFormat df = new DecimalFormat("0.0");
//        NumberFormat nt = NumberFormat.getPercentInstance();
//        //设置百分数精确度2即保留两位小数
//        nt.setMinimumFractionDigits(1);
//        //最后格式化并输出
//        float percent = 0;
//        if (alltime != 0) {
//            percent = (float) alltime / course;
//        }
//
//        Log.d("SubjectOneFragment", "percent4==========" + percent);
//        if (alltime > course) {
//            tasks_view1.setProgress(100);
//            //设置圆圈中进度
//            progress_ptxt2.setText("100%");
//        } else {
//            //当前进度百分比
//            tasks_view1.setProgress((int) (percent * 100));
//            //设置圆圈中进度
//            progress_ptxt2.setText(nt.format(percent));
//        }
//        //当前进度百分比
////        tasks_view1.setProgress((int) (percent * 100));
//
//        tasks_view1.setmTotalProgress(100);
//        //已学时间
//        practicedTime.setText(String.valueOf(alltime) + "分钟");
//        unpracticedTime.setText(course + "分钟");
//////        initdata();
        refreshTime = 0;
        if (getUserVisibleHint()) {
//            initdata();

        }
        return view;
    }
    private boolean isfirst=true;

    public void setUserVisibleHint(boolean isVisibleToUser) {
        if (isVisibleToUser && isVisible()) {
            if (refreshTime == 0 || (refreshTime != 0 && (System.currentTimeMillis() - refreshTime) > 5 * 60 * 1000)) {
//                initdata();
                if(!isfirst){
                    handler.sendEmptyMessage(1);
                }

            }
        }
        super.setUserVisibleHint(isVisibleToUser);
    }

    private void initdata() {
        sys = new SystemUtil(getActivity());
        refreshTime = System.currentTimeMillis();
        onlyID = sys.showOnlyID();
        if (onlyID != null) {
            OkGo.post(WebInterface.get_li_lun_time)
                    .tag(this)
                    .params("signid", onlyID)
                    .params("subject", "4")
                    .execute(getRecordsCallBack);
            // 请求网络获取学院信息（以下为科二测试值）------------------------------------------------------------------------------
            //自定义进度条
            //一共百分比
        }
    }


    private StringCallback getRecordsCallBack = new StringCallback() {
        @Override
        public void onSuccess(String s, Call call, Response response) {
            int num = JsonUtils.parseNum(s);
            if (num == 1) {
                TimeBean time = gson.fromJson(s, TimeBean.class);
                list = time.getData();
                if(list==null){
                    return;
                }
                if(list.size()!=0){
                    handler.sendEmptyMessage(1);
                }

            }
        }
    };
    @Subscribe
    public void onEventMainThread(TimeSub4 msg){
        time=msg.getTimeBean();
        list = time.getData();
        if (list==null){
            return;
        }
        if(list.size()!=0){
            handler.sendEmptyMessage(1);
            isfirst=false;
        }


    }

}
