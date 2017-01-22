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
import com.haha.exam.bean.TimeSub3;
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

public class SubjectThreeFragment extends Fragment {


    private long refreshTime = 0;
    private SPUtils spUtils;
    private Gson gson = new Gson();
    private String onlyID;
    private TextView progress_ptxt2;
    private TasksCompletedView tasks_view2;
    private int xid;
    TimeBean time;           //timebean
    int hasTime;
    int totalTime;
    private ArrayList<RecordsEntity> list=new ArrayList<>();
    private List<TimeBean.DataBean> list1;
    private SystemUtil sys;

    private TextView practiceDistance, practiceAllDistance;
    private TextView theoryLearned, theoryAllTime;
    private TextView practicedTime, practiceAllTime;

    private Handler handler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:

//                    //                    实践学时
//                    int hasTime = 0;
//                    int totalTime = 0;
//
//
//                    for (int i = 0; i < list.size(); i++) {
//                        if ("科三".equals(list.get(i).getSubject())) {
//                            hasTime = list.get(i).getHasTimes();
//                            totalTime = list.get(i).getTotalTimes();
//                            //获取格式化对象
//                        }
//                    }
//                    double percent = 0;
//                    NumberFormat nt = NumberFormat.getPercentInstance();
//                    //设置百分数精确度2即保留两位小数
//                    nt.setMinimumFractionDigits(1);
//                    if ((hasTime + alltime) != 0) {
//                        percent = (hasTime + alltime) / (double) (totalTime + course);
//                        if ((hasTime + alltime) > (totalTime + course)) {
//                            tasks_view2.setProgress(100);
//                            progress_ptxt2.setText("100%");
//                        } else {
//                            //当前进度百分比
//                            tasks_view2.setProgress((int) (percent * 100));
//                            progress_ptxt2.setText(nt.format(percent));
//                        }
//                        //当前进度百分比
////                        tasks_view2.setProgress((int) (percent * 100));
//                        //设置圆圈中进度
//                        tasks_view2.setmTotalProgress(100);
//                    }
//                    //已学时间
//                    practicedTime.setText(hasTime + "分钟");
//                    //总时间
//                    practiceAllTime.setText(totalTime + "分钟");
                    hasTime = 0;
                    totalTime = 0;
                    for (int i = 0; i < list.size(); i++) {
                        if ("科三".equals(list.get(i).getSubject())) {
                            hasTime = list.get(i).getHasTimes();
                            totalTime = list.get(i).getTotalTimes();
                            //获取格式化对象
                        }
                    }
                    //已学时间
                    practicedTime.setText(hasTime + "分钟");
                    //总时间
                    practiceAllTime.setText(totalTime + "分钟");


                    break;
                case 2:
                    if (time!=null){
                        int learned = time.getData().get(0).getAll_time();
                        int all = time.getData().get(0).getCourse3();
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
        }
        return view;
    }
    private boolean isfirst=true;
    public void setUserVisibleHint(boolean isVisibleToUser) {
        if (isVisibleToUser && isVisible()) {
            if (refreshTime == 0 || (refreshTime != 0 && (System.currentTimeMillis() - refreshTime) > 5*60 * 1000)) {
                if(!isfirst){
                    handler.sendEmptyMessage(1);
                    handler.sendEmptyMessage(2);
                }

            }
        }
        super.setUserVisibleHint(isVisibleToUser);
    }

    private void initdata() {
        sys = new SystemUtil(getActivity());
        refreshTime = System.currentTimeMillis();
        //xid  onlyid
        xid = sys.showUid();
        onlyID = sys.showOnlyID();
        if (xid != -1) {

            String s = "http://120.26.118.158:8082/xueshi.ashx?do=querymyxueshi&xid=" + xid;

            OkGo.post(s)
                    .tag(this)
                    .execute(getRecordsCallBack);
        }
        if (onlyID != null) {
            OkGo.post(WebInterface.get_li_lun_time)
                    .tag(this)
                    .params("signid", onlyID)
                    .params("subject", "3")
                    .execute(getRecordsCallBack1);
        }

        // 请求网络获取学院信息（以下为科二测试值）------------------------------------------------------------------------------
        //自定义进度条
        //一共百分比

    }


    private StringCallback getRecordsCallBack = new StringCallback() {
        @Override
        public void onSuccess(String s, Call call, Response response) {
            list = JsonUtils.parseRecords(s);
            if(list==null){
                return;
            }
            if (list.size() != 0) {
                handler.sendEmptyMessage(1);
            }
        }
    };
    //    科目三理论学时
    private StringCallback getRecordsCallBack1 = new StringCallback() {
        @Override
        public void onSuccess(String s, Call call, Response response) {
            int num = JsonUtils.parseNum(s);
            if (num == 1) {
                time = gson.fromJson(s, TimeBean.class);
                handler.sendEmptyMessage(2);
                Log.d("SubjectThreeFragment", "科三理论时间获取成功");
            }
        }
    };

    @Subscribe
    public void onEventMainThread( ArrayList<RecordsEntity> msg){
        isfirst=false;
         list=msg;
        if (list==null){
            return;
        }
        if(list.size()!=0){
            handler.sendEmptyMessage(1);
        }

        Log.e("eventbus","--------------------------3");

    }
    @Subscribe
    public void onEventMainThread(TimeSub3 msg){
        isfirst=false;
        time=msg.getTimeBean();
        handler.sendEmptyMessage(2);
        Log.e("eventbus","--------------------------33");

    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
        Log.e("eventbus","sub3");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
};
//    private NetWorkCallBack<BaseResult> getRecordsCallBack = new NetWorkCallBack<BaseResult>() {
//
//        @Override
//        public void onComplete(String json) {
//            list = JsonUtils.parseRecords(json);
//            if (list.size() != 0) {
//                handler.sendEmptyMessage(1);
//            }
//        }
//    };
//}
