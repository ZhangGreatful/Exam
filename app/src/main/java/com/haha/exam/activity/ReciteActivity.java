package com.haha.exam.activity;

import android.app.Activity;
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
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.google.gson.Gson;
import com.haha.exam.R;
import com.haha.exam.adapter.LayoutAdapter;
import com.haha.exam.adapter.ReciteAdapter;
import com.haha.exam.adapter.TopicAdapter;
import com.haha.exam.bean.AllQuestions;
import com.haha.exam.bean.AnwerInfo;
import com.haha.exam.web.WebInterface;
import com.lsjwzh.widget.recyclerviewpager.RecyclerViewPager;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.sothree.slidinguppanel.SlidingUpPanelLayout;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import okhttp3.Call;
import okhttp3.Response;

/**
 * 背题模式
 * 显示解释和正确选项
 */
public class ReciteActivity extends BaseActivity implements View.OnClickListener {

    private RecyclerViewPager mRecyclerView;
    private ReciteAdapter reciteAdapter;
    private SlidingUpPanelLayout mLayout;
    private TopicAdapter topicAdapter;
    private RecyclerView recyclerView;
    public static boolean isClicked = false;
    private int prePosition;
    private int curPosition;
    private MainActivity mainActivity;
    private AllQuestions allQuestions;
    private Gson gson = new Gson();


    private LinearLayout bianhao, shoucang, fenxiang, jieshi;
    //    private MyHorizontalScrollView horizontalScrollView;
//    private HorizontalScrollViewAdapter adapter;
//    private List<String> datas = new ArrayList<>();
    private ImageView back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initView();
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
                        Toast.makeText(ReciteActivity.this, "成功获取所有问题", Toast.LENGTH_SHORT).show();
                        allQuestions = gson.fromJson(s, AllQuestions.class);
                        List<AllQuestions.DataBean> datas = allQuestions.getData();
//        List<AllQuestions.DataBean> datas=mainActivity.questions;
//        Log.i("data.size=", "" + datas.size());

                        if (reciteAdapter != null) {
                            reciteAdapter.setDataList(datas);
                        }

                        if (topicAdapter != null) {
                            topicAdapter.setDataNum(datas.size());
                        }
                        System.out.println("一共有问题==============" + allQuestions.getData().size());
//                        dao.addAllQuestions(allQuestions);
                    }
                });

    }

    @Override
    protected int getContentView() {
        return R.layout.activity_recite;
    }

    @Override
    protected int getTitlebarResId() {
        return R.layout.text_bar;
    }

    private void initView() {

        bianhao = (LinearLayout) findViewById(R.id.bian_hao);
        shoucang = (LinearLayout) findViewById(R.id.shou_cang);
        fenxiang = (LinearLayout) findViewById(R.id.fen_xiang);
        jieshi = (LinearLayout) findViewById(R.id.jie_shi);

        bianhao.setOnClickListener(this);
        jieshi.setOnClickListener(this);

        back = (ImageView) findViewById(R.id.back);
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

    private AnwerInfo getAnwer() {

        try {
            InputStream in = getAssets().open("anwer.json");
            AnwerInfo anwerInfo = JSON.parseObject(inputStream2String(in), AnwerInfo.class);

            return anwerInfo;
        } catch (IOException e) {
            e.printStackTrace();
            Log.e("data.size=", e.toString());
        }

        return null;
    }

    public String inputStream2String(InputStream is) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        int i = -1;
        while ((i = is.read()) != -1) {
            baos.write(i);
        }
        return baos.toString();
    }

    private void initList() {
        recyclerView = (RecyclerView) findViewById(R.id.list);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 6);

        topicAdapter = new TopicAdapter(this,recyclerView);

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
        reciteAdapter = new ReciteAdapter(this, mRecyclerView);
        mRecyclerView.setAdapter(reciteAdapter);

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
                isClicked = false;
                jieshi.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (isClicked == false) {
                            isClicked = true;
                        } else {
                            isClicked = false;
                        }
                    }
                });
                reciteAdapter.notifyItemChanged(curPosition);

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
                reciteAdapter.notifyItemChanged(curPosition);
                break;
        }
    }
}
