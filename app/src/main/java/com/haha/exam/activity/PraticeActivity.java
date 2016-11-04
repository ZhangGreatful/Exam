package com.haha.exam.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.haha.exam.R;
import com.haha.exam.adapter.LayoutAdapter;
import com.haha.exam.adapter.PracticeAdapter;
import com.haha.exam.adapter.ReciteAdapter;
import com.haha.exam.adapter.TopicAdapter;
import com.haha.exam.bean.AllQuestions;
import com.haha.exam.bean.ErrorQuestion;
import com.haha.exam.bean.IsSave;
import com.haha.exam.dao.ExamDao;
import com.haha.exam.web.WebInterface;
import com.lsjwzh.widget.recyclerviewpager.RecyclerViewPager;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.sothree.slidinguppanel.SlidingUpPanelLayout;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import okhttp3.Call;
import okhttp3.Response;

/**
 * 模拟考试做题页面
 * 点击完交卷后，首先跳转到考试成绩界面，展示本次模拟考试成绩结果
 * 同时，还应将考试结果保存到数据库，在我的成绩界面展示出来
 */
public class PraticeActivity extends BaseActivity implements View.OnClickListener {

    private RecyclerViewPager mRecyclerView;
    private PracticeAdapter practiceAdapter;
    private SlidingUpPanelLayout mLayout;
    private TopicAdapter topicAdapter;
    private RecyclerView recyclerView;
    private int prePosition = 0;
    private int curPosition;
    private MainActivity mainActivity;
    private AllQuestions allQuestions;
    private ExamDao dao;
    private Gson gson = new Gson();
    public static int time;//考试用时
    private TextView rightCount, errorCount;
    public static int right, error;
    private List<AllQuestions.DataBean> datas;
    private String curentTime,date;

    private ErrorQuestion[] question;


    static int minute = -1;
    static int second = -1;
    final static String tag = "tag";
    TextView timeView;
    Timer timer;
    TimerTask timerTask;
    //    计时器
    Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            System.out.println("handle!");
            time++;
            if (minute == 0) {
                if (second == 0) {
                    timeView.setText("Time out !");
                    if (timer != null) {
                        timer.cancel();
                        timer = null;
                    }
                    if (timerTask != null) {
                        timerTask = null;
                    }
                } else {
                    second--;
                    String minutes = String.valueOf(minute);
                    String minute = minutes;
                    minute = "00";
                    if (second >= 10) {
                        minutes = String.valueOf(minute);
                        minute = minutes;
                        minute = "00";
                        timeView.setText(minute + ":" + second);/*"0"+minute + ":" + */
                    } else {
                        timeView.setText(minute + ":0" + second);/*"0"+minute + ":0" + */
                    }
                }
            } else {
                if (second == 0) {
                    second = 59;
                    minute--;
                    if (minute >= 10) {
                        timeView.setText(minute + ":" + second);
                    } else {
                        timeView.setText("0" + minute + ":" + second);
                    }
                } else {
                    second--;
                    if (second >= 10) {
                        if (minute >= 10) {
                            timeView.setText(minute + ":" + second);
                        } else {
                            timeView.setText("0" + minute + ":" + second);
                        }
                    } else {
                        if (minute >= 10) {
                            timeView.setText(minute + ":0" + second);
                        } else {
                            timeView.setText("0" + minute + ":0" + second);
                        }
                    }
                }
            }
        }

        ;
    };


    public LinearLayout shoucang, fenxiang, jiaojuan;
    //    private MyHorizontalScrollView horizontalScrollView;
//    private HorizontalScrollViewAdapter adapter;
//    private List<String> datas = new ArrayList<>();
    private ImageView back, image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dao = new ExamDao(PraticeActivity.this);
        time = 0;
        right = 0;
        initView();
        initTime();
        initViewPager();
        initSlidingUoPanel();
        initList();
        initData();
        getTime();
    }
//      获取当前的日期和时间,考试用时
    private void getTime() {
        SimpleDateFormat formatter = new SimpleDateFormat ("yyyy-MM-dd");
        Date curDate = new Date(System.currentTimeMillis());//获取当前时间
        date = formatter.format(curDate);

        SimpleDateFormat formatter1 = new SimpleDateFormat ("HH:mm:ss");
        curentTime=formatter1.format(curDate);

    }

    //      初始化数据
    private void initData() {
//     随机从数据库中抽取80道单选题，20道判断题
        datas = dao.getSubject1PractiseQuestions("xc");
        System.out.println("模拟考试题目长度是： " + datas.size());
        if (practiceAdapter != null) {
            practiceAdapter.setDataList(datas);
        }

        if (topicAdapter != null) {
            topicAdapter.setDataNum(datas.size());
            topicAdapter.setDataList(datas);
        }
    }

    private void initTime() {
        if (minute == -1 && second == -1) {
//            设置倒计时时间
            minute = 45;
            second = 00;
        }

        timeView.setText(minute + ":" + second);

        timerTask = new TimerTask() {

            @Override
            public void run() {
                Message msg = new Message();
                msg.what = 0;
                handler.sendMessage(msg);
            }
        };

        timer = new Timer();
        timer.schedule(timerTask, 0, 1000);


    }


    @Override
    protected int getContentView() {
        return R.layout.activity_pratice;
    }

    @Override
    protected int getTitlebarResId() {
        return R.layout.practice_bar;
    }

    private void initView() {

        shoucang = (LinearLayout) findViewById(R.id.shou_cang);
        fenxiang = (LinearLayout) findViewById(R.id.fen_xiang);
        jiaojuan = (LinearLayout) findViewById(R.id.jiao_juan);
        timeView = (TextView) findViewById(R.id.myTime);
        rightCount = (TextView) findViewById(R.id.tv_right);
        errorCount = (TextView) findViewById(R.id.tv_error);


        back = (ImageView) findViewById(R.id.back);
        image = (ImageView) findViewById(R.id.iv_up);
        back.setOnClickListener(this);
        jiaojuan.setOnClickListener(this);
        shoucang.setOnClickListener(this);
    }

    private void initList() {
        recyclerView = (RecyclerView) findViewById(R.id.list);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 6);

        topicAdapter = new TopicAdapter(this, recyclerView);

        recyclerView.setLayoutManager(gridLayoutManager);

        recyclerView.setAdapter(topicAdapter);


        topicAdapter.setOnTopicClickListener(new TopicAdapter.OnTopicClickListener() {
            @Override
            public void onClick(TopicAdapter.TopicViewHolder holder, int position) {
                curPosition = position;
                Log.i("点击了==>", position + "");
                if (mLayout != null &&
                        (mLayout.getPanelState() == SlidingUpPanelLayout.PanelState.EXPANDED || mLayout.getPanelState() == SlidingUpPanelLayout.PanelState.ANCHORED)) {
                    mLayout.setPanelState(SlidingUpPanelLayout.PanelState.COLLAPSED);
                }
                mRecyclerView.scrollToPosition(position);
                topicAdapter.notifyCurPosition(curPosition);
                topicAdapter.notifyPrePosition(prePosition);

//                prePosition = curPosition;
            }
        });


    }


    private void initSlidingUoPanel() {
        mLayout = (SlidingUpPanelLayout) findViewById(R.id.sliding_layout);

        int height = getWindowManager().getDefaultDisplay().getHeight();

        LinearLayout dragView = (LinearLayout) findViewById(R.id.dragView);
        SlidingUpPanelLayout.LayoutParams params = new SlidingUpPanelLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, (int) (height * 0.8f));
        dragView.setLayoutParams(params);


        mLayout.addPanelSlideListener(new SlidingUpPanelLayout.PanelSlideListener() {
            @Override
            public void onPanelSlide(View panel, float slideOffset) {
                Log.i("", "onPanelSlide, offset " + slideOffset);
            }

            @Override
            public void onPanelStateChanged(View panel, SlidingUpPanelLayout.PanelState previousState, SlidingUpPanelLayout.PanelState newState) {
                Log.i("", "onPanelStateChanged " + newState);
                if (newState == SlidingUpPanelLayout.PanelState.EXPANDED) {
                    image.setImageResource(R.mipmap.down);
                } else {
                    image.setImageResource(R.mipmap.up);
                }
            }
        });
        mLayout.setFadeOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mLayout.setPanelState(SlidingUpPanelLayout.PanelState.COLLAPSED);
            }
        });
    }

    protected void initViewPager() {
        mRecyclerView = (RecyclerViewPager) findViewById(R.id.viewpager);
        LinearLayoutManager layout = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL,
                false);
        mRecyclerView.setLayoutManager(layout);
        mRecyclerView.setSinglePageFling(true);
        mRecyclerView.setFlingFactor(0.1f);
        mRecyclerView.setTriggerOffset(0.1f);
        practiceAdapter = new PracticeAdapter(this, mRecyclerView);
        mRecyclerView.setAdapter(practiceAdapter);

        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLongClickable(true);
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int scrollState) {
//                updateState(scrollState);
                System.out.println("执行了滑动========");
//                layoutAdapter.notifyItemChanged(curPosition);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int i, int i2) {
                right = practiceAdapter.rightCount;
                error = practiceAdapter.error_count;
            }
        });

        mRecyclerView.addOnPageChangedListener(new RecyclerViewPager.OnPageChangedListener() {
            @Override
            public void OnPageChanged(int oldPosition, int newPosition) {
                Log.d("test", "oldPosition:" + oldPosition + " newPosition:" + newPosition);
                recyclerView.scrollToPosition(newPosition);
//                layoutAdapter.notifyItemChanged(curPosition);
                rightCount.setText(String.valueOf(practiceAdapter.rightCount));
                errorCount.setText(String.valueOf(practiceAdapter.error_count));

                curPosition = newPosition;
                prePosition = oldPosition;
                topicAdapter.notifyCurPosition(newPosition);
                topicAdapter.notifyPrePosition(oldPosition);

                Log.i("DDD", newPosition + "");

            }
        });


        mRecyclerView.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
            @Override
            public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {


            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.jiao_juan:
//            得到错题信息
                question = new ErrorQuestion[practiceAdapter.list.size()];
                for (int i = 0; i < question.length; i++) {
                    question[i] = practiceAdapter.list.get(i);
                    System.out.println("错误信息=======" + practiceAdapter.list.get(i).getSid());
                }
//                "create  table  grade (id integer primary key autoincrement," +
//            " date text, time text, telphone text, grade text, rightcount text, question text," +
//            " cartype text,  subject text)";
                dao.addMyGrade(date,curentTime,"18266142739",String.valueOf(practiceAdapter.rightCount),question,"xc","1");

                Intent intent = new Intent(PraticeActivity.this, PracticeResultActivity.class);
                startActivity(intent);
                finish();
                break;
            case R.id.shou_cang:
//                网络收藏
                OkGo.post(WebInterface.is_save)
                        .tag(this)
                        .params("questionid", datas.get(curPosition).getSid())
                        .params("tel", "18266142739")
                        .execute(new StringCallback() {
                            @Override
                            public void onSuccess(String s, Call call, Response response) {
                                IsSave isSave = gson.fromJson(s, IsSave.class);
                                Toast.makeText(PraticeActivity.this, isSave.getMsg(), Toast.LENGTH_SHORT).show();
                            }
                        });

//                本地收藏

                break;

        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        //        结束计时
        Log.v(tag, "log---------->onDestroy!");
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
        if (timerTask != null) {
            timerTask = null;
        }
        minute = -1;
        second = -1;
    }

    @Override
    protected void onDestroy() {

        super.onDestroy();
    }
}
