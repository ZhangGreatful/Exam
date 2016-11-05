package com.haha.exam.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
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
import com.haha.exam.bean.IsSave;
import com.haha.exam.dao.ExamDao;
import com.haha.exam.dao.DatabaseHelper;
import com.haha.exam.utils.SPUtils;
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
import java.util.Set;

import okhttp3.Call;
import okhttp3.Response;

/**
 * 顺序练习界面
 * 点击收藏，进行2个部分的收藏
 * 1，本地收藏
 * 2，网络收藏 （只有登录后才能进行网络收藏）
 */
public class OrderTextActivity extends BaseActivity implements View.OnClickListener {

    private String tel = "18266142739";
    private RecyclerViewPager mRecyclerView;
    private LayoutAdapter layoutAdapter;
    private PracticeAdapter practiceAdapter;
    private SlidingUpPanelLayout mLayout;
    private TopicAdapter topicAdapter;
    private RecyclerView recyclerView;
    private int prePosition;
    public static int curPosition;
    private MainActivity mainActivity;
    private AllQuestions allQuestions;
    public static int isClicked;
    private Map<String, Integer> status = new HashMap<>();
    private SPUtils sp = new SPUtils();

    private ExamDao dao;
    private DatabaseHelper dbHelper;
    private Gson gson = new Gson();
    private List<AllQuestions.DataBean> datas;


    private LinearLayout bianhao;
    private LinearLayout shoucang;
    private LinearLayout fenxiang;
    public LinearLayout jieshi;
    private TextView current, count;
    private TextView right, error, unanswer;
    private ImageView close;
    //    private MyHorizontalScrollView horizontalScrollView;
//    private HorizontalScrollViewAdapter adapter;
//    private List<String> datas = new ArrayList<>();
    private ImageView back;
    //    private int mScreenWitdh;
//    private LinearLayout layout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        initData();
        initView();
        initViewPager();
        initSlidingUoPanel();
        initList();
        initData();
//        AnwerInfo anwerInfo = getAnwer();

//        List<AnwerInfo.DataBean.SubDataBean> datas = anwerInfo.getData().getData();
        int currentPage = (int) sp.get(this, "currentPage", 1);
        mRecyclerView.scrollToPosition(currentPage);

    }

    private void initData() {
        dao = new ExamDao(OrderTextActivity.this);
        Intent intent = getIntent();
        if (intent.getIntExtra("knowtype", 0) != 0) {
            int i = intent.getIntExtra("knowtype", 0);
            String msg = String.valueOf(i);
            System.out.println("knowtype==========" + msg);
            datas = dao.getKnowledgetypeQuestions("xc", msg);
        } else if (intent.getIntExtra("chapterid", 0) != 0) {
            int j = intent.getIntExtra("chapterid", 0);
            String msg = String.valueOf(j);
            System.out.println("chapterid========" + msg);
            datas = dao.getChapterQuestions("xc", msg);
        } else {
            datas = dao.queryAllQuestions("xc");
        }

        count.setText("" + datas.size());

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

        dbHelper=new DatabaseHelper(this);
        bianhao = (LinearLayout) findViewById(R.id.bian_hao);
        shoucang = (LinearLayout) findViewById(R.id.shou_cang);
        fenxiang = (LinearLayout) findViewById(R.id.fen_xiang);
        jieshi = (LinearLayout) findViewById(R.id.jie_shi);
        current = (TextView) findViewById(R.id.current_page);
        count = (TextView) findViewById(R.id.count);
        right = (TextView) findViewById(R.id.tv_right);
        error = (TextView) findViewById(R.id.tv_error);
        close = (ImageView) findViewById(R.id.close);


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


        sp.put(this, "currentPage", curPosition);
        //根据 Tag 取消请求
        OkGo.getInstance().cancelTag(this);
    }

//    private AnwerInfo getAnwer() {
//
//        try {
//            InputStream in = getAssets().open("anwer.json");
//            AnwerInfo anwerInfo = JSON.parseObject(inputStream2String(in), AnwerInfo.class);
//
//            return anwerInfo;
//        } catch (IOException e) {
//            e.printStackTrace();
//            Log.e("data.size=", e.toString());
//        }
//
//        return null;
//    }
//
//    public String inputStream2String(InputStream is) throws IOException {
//        ByteArrayOutputStream baos = new ByteArrayOutputStream();
//        int i = -1;
//        while ((i = is.read()) != -1) {
//            baos.write(i);
//        }
//        return baos.toString();
//    }

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
//                layoutAdapter.notifyItemChanged(curPosition);
                isClicked = 0;
                layoutAdapter.notifyItemChanged(prePosition);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int i, int i2) {
                String rightQuery = "SELECT  * FROM one_right_questions";
                String errorQuery="SELECT  * FROM one_error_questions";
                SQLiteDatabase db = dbHelper.getReadableDatabase();
                Cursor cursor = db.rawQuery(rightQuery, null);
               int rightCount= cursor.getCount();
                Cursor cursor1=db.rawQuery(errorQuery,null);
                int errorCount=cursor1.getCount();
                right.setText(String.valueOf(rightCount));
                error.setText(String.valueOf(errorCount));
                unanswer.setText(String.valueOf(datas.size()-rightCount-errorCount));
            }
        });

        mRecyclerView.addOnPageChangedListener(new RecyclerViewPager.OnPageChangedListener() {
            @Override
            public void OnPageChanged(int oldPosition, int newPosition) {
                Log.d("test", "oldPosition:" + oldPosition + " newPosition:" + newPosition);
                recyclerView.scrollToPosition(newPosition);
//                layoutAdapter.notifyItemChanged(curPosition);
//                rightCount.setText(String.valueOf(practiceAdapter.rightCount));
//                errorCount.setText(String.valueOf(practiceAdapter.error_count));
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
                        .params("tel", tel)
                        .execute(new StringCallback() {
                            @Override
                            public void onSuccess(String s, Call call, Response response) {
                                IsSave isSave = gson.fromJson(s, IsSave.class);
                                Toast.makeText(OrderTextActivity.this, isSave.getMsg(), Toast.LENGTH_SHORT).show();
                            }
                        });

//                本地收藏到数据库
                dao.addCollectQuestions(datas.get(curPosition + 1), "one");

                break;

        }
    }
}


