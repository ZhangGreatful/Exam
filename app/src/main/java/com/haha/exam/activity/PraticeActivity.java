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
import com.haha.exam.adapter.PracticeAdapter;
import com.haha.exam.adapter.ReciteAdapter;
import com.haha.exam.adapter.TopicAdapter;
import com.haha.exam.bean.AllQuestions;
import com.haha.exam.web.WebInterface;
import com.lsjwzh.widget.recyclerviewpager.RecyclerViewPager;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.sothree.slidinguppanel.SlidingUpPanelLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import okhttp3.Call;
import okhttp3.Response;

/**
 * 模拟考试做题页面
 */
public class PraticeActivity extends BaseActivity implements View.OnClickListener {

    private RecyclerViewPager mRecyclerView;
    private PracticeAdapter practiceAdapter;
    private SlidingUpPanelLayout mLayout;
    private TopicAdapter topicAdapter;
    private RecyclerView recyclerView;
    public static boolean isClicked = false;
    private int prePosition;
    private int curPosition;
    private MainActivity mainActivity;
    private AllQuestions allQuestions;
    private Gson gson = new Gson();


    static int minute = -1;
    static int second = -1;
    final static String tag = "tag";
    TextView timeView;
    Timer timer;
    TimerTask timerTask;
    Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            System.out.println("handle!");
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


    private LinearLayout shoucang, fenxiang, jiaojuan;
    //    private MyHorizontalScrollView horizontalScrollView;
//    private HorizontalScrollViewAdapter adapter;
//    private List<String> datas = new ArrayList<>();
    private ImageView back, image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initView();
        initTime();
        initViewPager();
        initSlidingUoPanel();
        initList();
//        AnwerInfo anwerInfo = getAnwer();

        OkGo.post(WebInterface.all_questions)
                .tag(this)
                .params("cartype", "hc")
                .params("subject", "1")
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        Toast.makeText(PraticeActivity.this, "成功获取所有问题", Toast.LENGTH_SHORT).show();
                        allQuestions = gson.fromJson(s, AllQuestions.class);
                        List<AllQuestions.DataBean> datas = allQuestions.getData();
//        List<AllQuestions.DataBean> datas=mainActivity.questions;
//        Log.i("data.size=", "" + datas.size());

                        if (practiceAdapter != null) {
                            practiceAdapter.setDataList(datas);
                        }

                        if (topicAdapter != null) {
                            topicAdapter.setDataNum(datas.size());
                        }
                        System.out.println("一共有问题==============" + allQuestions.getData().size());
//                        dao.addAllQuestions(allQuestions);
                    }
                });
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


        back = (ImageView) findViewById(R.id.back);
        image = (ImageView) findViewById(R.id.iv_up);
        back.setOnClickListener(this);
//        layout= (LinearLayout) findViewById(R.id.id_gallery);
//        horizontalScrollView = (MyHorizontalScrollView) findViewById(R.id.id_horizontalScrollView);
//        adapter = new HorizontalScrollViewAdapter(this, datas);
//        //添加滚动回调
//        horizontalScrollView
//                .setCurrentImageChangeListener(new MyHorizontalScrollView.CurrentImageChangeListener() {
//                    @Override
//                    public void onCurrentImgChanged(int position,
//                                                    View viewIndicator) {
////                        mImg.setImageResource(mDatas.get(position));
//                    }
//                });
//        //添加点击回调
//        horizontalScrollView.setOnItemClickListener(new MyHorizontalScrollView.OnItemClickListener() {
//
//            @Override
//            public void onClick(View view, int position) {
////                mImg.setImageResource(mDatas.get(position));
////                view.setBackgroundColor(Color.parseColor("#AA024DA4"));
//            }
//        });
//
//        //设置适配器
//        horizontalScrollView.initDatas(adapter);
    }

    private void initList() {
        recyclerView = (RecyclerView) findViewById(R.id.list);


        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 6);

        topicAdapter = new TopicAdapter(this);

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
                mRecyclerView.smoothScrollToPosition(position);

                topicAdapter.notifyCurPosition(curPosition);
                topicAdapter.notifyPrePosition(prePosition);

                prePosition = curPosition;
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

//            状态监听
            @Override
            public void onPanelStateChanged(View panel, SlidingUpPanelLayout.PanelState previousState, SlidingUpPanelLayout.PanelState newState) {
                Log.i("", "onPanelStateChanged " + newState);
                if (newState.equals(SlidingUpPanelLayout.PanelState.EXPANDED)){
                    image.setImageResource(R.mipmap.down);
                }else {
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
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int i, int i2) {
    //
            }
        });
        mRecyclerView.addOnPageChangedListener(new RecyclerViewPager.OnPageChangedListener() {
            @Override
            public void OnPageChanged(int oldPosition, int newPosition) {
                Log.d("test", "oldPosition:" + oldPosition + " newPosition:" + newPosition);
                recyclerView.scrollToPosition(newPosition);
                practiceAdapter.notifyItemChanged(curPosition);

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
        }
    }

    @Override
    protected void onDestroy() {
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

        OkGo.getInstance().cancelTag(this);

        super.onDestroy();
    }
}
