package com.haha.exam.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.haha.exam.R;
import com.haha.exam.adapter.LayoutAdapter;
import com.haha.exam.adapter.TopicAdapter;
import com.haha.exam.bean.AllQuestions;
import com.haha.exam.bean.IsSave;
import com.haha.exam.dao.ExamDao;
import com.haha.exam.utils.SPUtils;
import com.haha.exam.web.WebInterface;
import com.lsjwzh.widget.recyclerviewpager.RecyclerViewPager;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.sothree.slidinguppanel.SlidingUpPanelLayout;
import com.umeng.socialize.Config;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.wanzheng.driver.util.SystemUtil;

import java.util.List;

import okhttp3.Call;
import okhttp3.Response;

/**
 * 用不到了
 * 顺序练习界面
 * 点击收藏，进行2个部分的收藏
 * 1，本地收藏
 * 2，网络收藏 （只有登录后才能进行网络收藏）
 */
public class RandomTextActivity extends BaseActivity implements View.OnClickListener {


    private SystemUtil su;
    private int uid;
    private SPUtils spUtils = new SPUtils();
    private String subject,subject0;
    private String cartype;
    private RecyclerViewPager mRecyclerView;
    private LayoutAdapter layoutAdapter;
    private SlidingUpPanelLayout mLayout;
    private TopicAdapter topicAdapter;
    private RecyclerView recyclerView;
    private int prePosition;
    public static int curPosition;
    public static int isClicked;
    private SPUtils sp = new SPUtils();
    private int rightCount, errorCount;

    private ExamDao dao;
    private Gson gson = new Gson();
    private List<AllQuestions.DataBean> datas;


    private LinearLayout bianhao;
    private LinearLayout shoucang;
    private LinearLayout fenxiang;
    public LinearLayout jieshi;
    private TextView current, count;
    private TextView right, error, unanswer;
    private ImageView back;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Config.dialogSwitch = true;

        su = new SystemUtil(RandomTextActivity.this);
        uid = su.showUid();
        subject = (String) spUtils.get(RandomTextActivity.this, "subject", "one");
        subject0= (String) spUtils.get(RandomTextActivity.this,"subject0","1");
        cartype = (String) spUtils.get(this, "cartype", "xc");
        System.out.println("subject==========" + subject);
        initView();
        initViewPager();
        initSlidingUoPanel();
        initList();
        initData();
        rightCount = 0;
        errorCount = 0;
    }

    private void initData() {
        dao = new ExamDao(RandomTextActivity.this);
        datas = dao.getRandomPractiseQuestions(cartype, subject);


        count.setText("" + datas.size());
        unanswer.setText("" + datas.size());
        if (layoutAdapter != null) {
            layoutAdapter.setDataList(datas);
            topicAdapter.setDataList(datas);
        }

        if (topicAdapter != null) {
            topicAdapter.setDataNum(datas.size());
        }

    }

    @Override
    protected int getContentView() {
        return R.layout.activity_order_text;
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
        current = (TextView) findViewById(R.id.current_page);
        count = (TextView) findViewById(R.id.count);
        right = (TextView) findViewById(R.id.tv_right);
        error = (TextView) findViewById(R.id.tv_error);
        unanswer = (TextView) findViewById(R.id.tv_unanswer);


        bianhao.setOnClickListener(this);

        shoucang.setOnClickListener(this);
        fenxiang.setOnClickListener(this);
        jieshi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isClicked == 1) {
                    isClicked = 0;
                } else {
                    isClicked = 1;
                }
                System.out.println("currentPosition====" + curPosition);
                System.out.println("isClicked=========" + isClicked);
                layoutAdapter.notifyItemChanged(curPosition);
            }
        });

        back = (ImageView) findViewById(R.id.back);
        back.setOnClickListener(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
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
                mRecyclerView.scrollToPosition(position);

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
                System.out.println("执行了滑动========");
                isClicked = 0;
                layoutAdapter.notifyItemChanged(prePosition);
                topicAdapter.notifyCurPosition(curPosition);
                topicAdapter.notifyPrePosition(prePosition);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int i, int i2) {
                rightCount = layoutAdapter.rightCount;
                errorCount = layoutAdapter.errorCount;
                right.setText(String.valueOf(rightCount));
                error.setText(String.valueOf(errorCount));
                unanswer.setText(String.valueOf(datas.size() - rightCount - errorCount));
            }
        });

        mRecyclerView.addOnPageChangedListener(new RecyclerViewPager.OnPageChangedListener() {
            @Override
            public void OnPageChanged(int oldPosition, int newPosition) {
                Log.d("test", "oldPosition:" + oldPosition + " newPosition:" + newPosition);
                recyclerView.scrollToPosition(newPosition);
                current.setText(String.valueOf(newPosition + 1));
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
            case R.id.bian_hao:
                if (mLayout != null &&
                        (mLayout.getPanelState() == SlidingUpPanelLayout.PanelState.EXPANDED || mLayout.getPanelState() == SlidingUpPanelLayout.PanelState.ANCHORED)) {
                    mLayout.setPanelState(SlidingUpPanelLayout.PanelState.COLLAPSED);
                }
                mLayout.setPanelState(SlidingUpPanelLayout.PanelState.EXPANDED);
                break;
            case R.id.shou_cang:
                //网络收藏
                gson = new Gson();
                OkGo.post(WebInterface.is_save)
                        .tag(this)
                        .params("questionid", datas.get(curPosition).getSid())
                        .params("subject",subject0)
                        .params("signid", String.valueOf(uid))
                        .execute(new StringCallback() {
                            @Override
                            public void onSuccess(String s, Call call, Response response) {
                                IsSave isSave = gson.fromJson(s, IsSave.class);
                                Toast.makeText(RandomTextActivity.this, isSave.getMsg(), Toast.LENGTH_SHORT).show();
                            }
                        });

//                本地收藏到数据库
                dao.addCollectQuestions(datas.get(curPosition + 1), "one");
                break;
            case R.id.fen_xiang:
                new ShareAction(RandomTextActivity.this).setDisplayList(SHARE_MEDIA.QQ, SHARE_MEDIA.QZONE, SHARE_MEDIA.WEIXIN, SHARE_MEDIA.WEIXIN_CIRCLE)
                        .withTitle("请选择平台")
                        .setCallback(umShareListener)
                        .open();
                break;

        }
    }

    //分享平台回调
    private UMShareListener umShareListener = new UMShareListener() {
        @Override
        public void onResult(SHARE_MEDIA platform) {
            Log.d("plat", "platform" + platform);
            Toast.makeText(RandomTextActivity.this, platform + " 分享成功啦", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onError(SHARE_MEDIA share_media, Throwable throwable) {
            Toast.makeText(RandomTextActivity.this, throwable.getMessage(), Toast.LENGTH_LONG).show();
            if (throwable != null) {
                Log.d("throw", "throw:" + throwable.getMessage());
            }
        }

        @Override
        public void onCancel(SHARE_MEDIA share_media) {
            Toast.makeText(RandomTextActivity.this, share_media + " 取消分享平台", Toast.LENGTH_SHORT).show();
        }
    };



    float x_temp01 = 0f;
    float y_temp01 = 0f;
    float x_temp02 = 0f;
    float y_temp02 = 0f;

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        float x = event.getX();
        float y = event.getY();

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN: {
                x_temp01 = x;
                y_temp01 = y;
            }
            break;
            case MotionEvent.ACTION_UP: {
                x_temp02 = x;
                y_temp02 = y;

                if (x_temp01 != 0 && y_temp01 != 0)//
                {
                    // 比较x_temp01和x_temp02
                    // x_temp01>x_temp02         //向左
                    // x_temp01==x_temp02         //竖直方向或者没动
                    // x_temp01<x_temp02         //向右

                    if (x_temp01 > x_temp02)//向左
                    {
                        //移动了x_temp01-x_temp02
                        if (x_temp01 - x_temp02 > 20) {
                            mRecyclerView.scrollToPosition(curPosition + 1);
                        }
                    }

                    if (x_temp01 < x_temp02)//向右
                    {
                        //移动了x_temp02-x_temp01
                        if (x_temp02 - x_temp01 > 20) {
                            mRecyclerView.scrollToPosition(curPosition - 1);
                        }
                    }
                }

            }
            break;
        }


        return super.onTouchEvent(event);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
        Log.d("result", "onActivityResult");
    }
}




