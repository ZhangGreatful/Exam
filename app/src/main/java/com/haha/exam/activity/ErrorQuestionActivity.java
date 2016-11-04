package com.haha.exam.activity;

import android.content.Intent;
import android.os.Bundle;
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

import com.alibaba.fastjson.JSON;
import com.google.gson.Gson;
import com.haha.exam.R;
import com.haha.exam.adapter.LayoutAdapter;
import com.haha.exam.adapter.PracticeAdapter;
import com.haha.exam.adapter.TopicAdapter;
import com.haha.exam.bean.AllQuestions;
import com.haha.exam.bean.AnwerInfo;
import com.haha.exam.bean.DeleteAll;
import com.haha.exam.bean.IsSave;
import com.haha.exam.dao.DatabaseHelper;
import com.haha.exam.dao.ExamDao;
import com.haha.exam.web.WebInterface;
import com.lsjwzh.widget.recyclerviewpager.RecyclerViewPager;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.sothree.slidinguppanel.SlidingUpPanelLayout;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Response;

/**
 * 错题展示界面
 * <p/>
 * <p/>
 * Created by Administrator on 2016/10/31.
 */
public class ErrorQuestionActivity extends BaseActivity implements View.OnClickListener {
    private String questionid = "1", tel = "18266142739";
    private RecyclerViewPager mRecyclerView;
    private LayoutAdapter layoutAdapter;
    private SlidingUpPanelLayout mLayout;
    private TopicAdapter topicAdapter;
    private RecyclerView recyclerView;
    public static boolean isClicked = false;
    private int prePosition;
    private static int curPosition;
    private MainActivity mainActivity;
    private AllQuestions allQuestions;

    private ExamDao dao;
    private DatabaseHelper dbHelper;
    private Gson gson = new Gson();
    private List<AllQuestions.DataBean> datas;


    private LinearLayout bianhao, shanchu, fenxiang, jieshi;
    private TextView current, count;
    private ImageView back;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        initViewPager();
        initSlidingUoPanel();
        initList();
        initData();
    }

    private void initData() {
        dao = new ExamDao(ErrorQuestionActivity.this);
        Intent intent = getIntent();
//        获取对应chapterid的题目（从网络获取题目）
        if (intent.getStringExtra("allerror").equals("0")) {//获取全部错题
//            从网络获取错题
            OkGo.post(WebInterface.check_error)
                    .tag(this)
                    .params("telphone", "18266142739")
                    .execute(new StringCallback() {
                        @Override
                        public void onSuccess(String s, Call call, Response response) {
                            Toast.makeText(ErrorQuestionActivity.this, "从网络获取错题成功", Toast.LENGTH_SHORT).show();
                            AllQuestions allQuestions = gson.fromJson(s, AllQuestions.class);
                            datas = allQuestions.getData();
                            if (layoutAdapter != null) {
                                layoutAdapter.setDataList(datas);
                                topicAdapter.setDataList(datas);
                            }
                            if (topicAdapter != null) {
                                topicAdapter.setDataNum(datas.size());
                            }
                            count.setText("" + datas.size());

                        }
                    });
        } else if (intent.getStringExtra("allerror").equals("-1")) {//错题回顾




        } else {
//            从网络获取错题（错题分类列表对应题目）
            OkGo.post(WebInterface.check_error)
                    .tag(this)
                    .params("telphone", "18266142739")
                    .params("chapterid", intent.getStringExtra("allerror"))
                    .execute(new StringCallback() {
                        @Override
                        public void onSuccess(String s, Call call, Response response) {
                            Toast.makeText(ErrorQuestionActivity.this, "从网络获取错题成功", Toast.LENGTH_SHORT).show();
                            AllQuestions allQuestions = gson.fromJson(s, AllQuestions.class);
                            datas = allQuestions.getData();
                            if (layoutAdapter != null) {
                                layoutAdapter.setDataList(datas);
                                topicAdapter.setDataList(datas);
                            }
                            if (topicAdapter != null) {
                                topicAdapter.setDataNum(datas.size());
                            }
                            count.setText("" + datas.size());

                        }
                    });
        }

        //从本地加载数据
//       else {
//            datas = dao.queryAllQuestions("xc");
//        }


//        if (layoutAdapter != null) {
//            layoutAdapter.setDataList(datas);
//        }
//
//        if (topicAdapter != null) {
//            topicAdapter.setDataNum(datas.size());
//        }

    }

    @Override
    protected int getContentView() {
        return R.layout.activity_order_text;
    }

    @Override
    protected int getTitlebarResId() {
        return R.layout.error_question_bar;
    }

    private void initView() {

        bianhao = (LinearLayout) findViewById(R.id.bian_hao);
        shanchu = (LinearLayout) findViewById(R.id.shan_chu);
        fenxiang = (LinearLayout) findViewById(R.id.fen_xiang);
        jieshi = (LinearLayout) findViewById(R.id.jie_shi);
        current = (TextView) findViewById(R.id.current_page);
        count = (TextView) findViewById(R.id.count);


        bianhao.setOnClickListener(this);
        jieshi.setOnClickListener(this);
        shanchu.setOnClickListener(this);
        fenxiang.setOnClickListener(this);

        back = (ImageView) findViewById(R.id.back);
        back.setOnClickListener(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //根据 Tag 取消请求
        OkGo.getInstance().cancelTag(this);
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
//                mRecyclerView.smoothScrollToPosition(position);
                mRecyclerView.scrollToPosition(position);
                current.setText(String.valueOf(curPosition + 1));
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

            @Override
            public void onPanelStateChanged(View panel, SlidingUpPanelLayout.PanelState previousState, SlidingUpPanelLayout.PanelState newState) {
                Log.i("", "onPanelStateChanged " + newState);
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
        layoutAdapter = new LayoutAdapter(this, mRecyclerView);
        mRecyclerView.setAdapter(layoutAdapter);

        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLongClickable(true);
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int scrollState) {
//                updateState(scrollState);
                System.out.println("执行了滑动========");
                jieshi.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        switch (view.getId()) {
                            case R.id.jie_shi:
                                System.out.println("点击了解释");
                                if (isClicked == false) {
                                    isClicked = true;
                                } else {
                                    isClicked = false;
                                }
                                break;
                        }
                    }
                });
//                layoutAdapter.notifyItemChanged(curPosition);

            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int i, int i2) {
                isClicked = false;
            }
        });

        mRecyclerView.addOnPageChangedListener(new RecyclerViewPager.OnPageChangedListener() {
            @Override
            public void OnPageChanged(int oldPosition, int newPosition) {
                Log.d("test", "oldPosition:" + oldPosition + " newPosition:" + newPosition);
                recyclerView.scrollToPosition(newPosition);
                System.out.println("newPosition======" + newPosition);
                System.out.println("oldPosition======" + oldPosition);
                current.setText(String.valueOf(newPosition + 1));
//                layoutAdapter.notifyItemChanged(curPosition);
                curPosition = newPosition;
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
            case R.id.bian_hao:
                if (mLayout != null &&
                        (mLayout.getPanelState() == SlidingUpPanelLayout.PanelState.EXPANDED || mLayout.getPanelState() == SlidingUpPanelLayout.PanelState.ANCHORED)) {
                    mLayout.setPanelState(SlidingUpPanelLayout.PanelState.COLLAPSED);
                }
                mLayout.setPanelState(SlidingUpPanelLayout.PanelState.EXPANDED);
                break;
            case R.id.jie_shi:
                if (isClicked == false) {
                    isClicked = true;
                } else {
                    isClicked = false;
                }
                layoutAdapter.notifyItemChanged(curPosition);
                break;
            case R.id.shan_chu:

                OkGo.post(WebInterface.delete_error)
                        .tag(this)
                        .params("telphone", "18266142739")
                        .params("questionid", datas.get(curPosition).getSid())
                        .execute(new StringCallback() {
                            @Override
                            public void onSuccess(String s, Call call, Response response) {
                                DeleteAll string = gson.fromJson(s, DeleteAll.class);
                                Toast.makeText(ErrorQuestionActivity.this, string.getMsg(), Toast.LENGTH_SHORT).show();
                            }
                        });
                datas.remove(curPosition);
                System.out.println("curPosition===========" + curPosition);
                topicAdapter.setDataNum(datas.size());
                count.setText("" + datas.size());
                layoutAdapter.notifyItemChanged(curPosition);

//                删除错题
//                dao.deleteQuestion("one", datas.get(curPosition).getSid());//删除完错题，数据库更新（总题数减少）

                break;

        }
    }
}
