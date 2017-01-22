package com.haha.exam.activity;

import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.hardware.Camera;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Chronometer;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.haha.exam.R;
import com.haha.exam.adapter.ReviewErrorAdapter;
import com.haha.exam.adapter.TopicAdapter;
import com.haha.exam.bean.AllQuestions;
import com.haha.exam.bean.IsSave;
import com.haha.exam.dao.ExamDao;
import com.haha.exam.dialog.StartTimingDialog;
import com.haha.exam.utils.SPUtils;
import com.haha.exam.view.MySurfaceView;
import com.haha.exam.web.WebInterface;
import com.lsjwzh.widget.recyclerviewpager.RecyclerViewPager;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.sothree.slidinguppanel.SlidingUpPanelLayout;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.wanzheng.driver.home.FaceLogin;
import com.wanzheng.driver.network.JsonUtils;
import com.wanzheng.driver.util.SystemUtil;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import okhttp3.Call;
import okhttp3.Response;

/**
 * 错题回顾界面
 */
public class ReviewErrorActivity extends BaseActivity implements View.OnClickListener {


    private SystemUtil su;
    private SPUtils spUtils;
    private String onlyID;
    private String phone;
    private String subject, subject0;
    private RecyclerViewPager mRecyclerView;
    private ReviewErrorAdapter layoutAdapter;
    private SlidingUpPanelLayout mLayout;
    private TopicAdapter topicAdapter;
    private RecyclerView recyclerView;
    private int prePosition;
    public int curPosition;
    public static int isClicked;
    private SPUtils sp = new SPUtils();

    private ExamDao dao;
    private Gson gson = new Gson();
    public static List<AllQuestions.DataBean> datas;
    private PracticeResultActivity practiceResultActivity;


    private LinearLayout chongzuo;
    private LinearLayout shoucang;
    private LinearLayout fenxiang;
    private TextView right, error;
    private ImageView close;
    private TextView currentPage, counts;
    private int rightCount, errorCount;
    private ImageView back;

    private MySurfaceView frontSurfaceView;
    private SurfaceHolder frontHolder;
    private Camera mFrontCamera;
    private Timer timer;
    private TimerTask timerTask, timerTask1;
    private Chronometer chronometer;
    private String time = "";
    private TextView close_timer;
    private int learnTime = 0;
    private String result;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    initCamera();
                    break;
            }
        }
    };


    /**
     * 自动对焦的回调方法，用来处理对焦成功/不成功后的事件
     */
    private Camera.AutoFocusCallback mAutoFocus = new Camera.AutoFocusCallback() {

        @Override
        public void onAutoFocus(boolean success, Camera camera) {
            //TODO:空实现
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        practiceResultActivity = new PracticeResultActivity();

        /**
         * 初始化前置相机参数
         */
        // 初始化surface view
//        frontSurfaceView = (MySurfaceView) findViewById(R.id.front_surfaceview);
//        // 初始化surface holder
//        frontHolder = frontSurfaceView.getHolder();
//        frontHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);

        su = new SystemUtil(ReviewErrorActivity.this);
        onlyID = su.showOnlyID();
        phone = su.showPhone();
        spUtils = new SPUtils();
        subject = (String) spUtils.get(this, "subject", "one");
        subject0 = (String) spUtils.get(this, "subject0", "1");
        dao = new ExamDao(this);
        rightCount = 0;
        errorCount = 0;
        initView();
//        chronometer.setBase(SystemClock.elapsedRealtime());//计时器清零
//        int hour = (int) ((SystemClock.elapsedRealtime() - chronometer.getBase()) / 1000 / 60);
//        chronometer.setFormat("0" + String.valueOf(hour) + ":%s");
        initViewPager();
        initSlidingUoPanel();
        initList();
        initData();
//        initState();


    }

    @Override
    protected void onResume() {
        super.onResume();
        learnTime = 0;
        chronometer.setBase(SystemClock.elapsedRealtime());//计时器清零
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_review_error;
    }

    @Override
    protected int getTitlebarResId() {
        return R.layout.review_error_bar;
    }

    private void initData() {
//
        datas = practiceResultActivity.datas;
        counts.setText(String.valueOf(datas.size()));

        if (layoutAdapter != null) {
            layoutAdapter.setDataList(datas);
            topicAdapter.setDataList(datas);
        }

        if (topicAdapter != null) {
            topicAdapter.setDataNum(datas.size());
        }
        for (int i = 0; i < datas.size(); i++) {
            if (datas.get(i).getIsdo() == 1) {//做了该题目
//                判断对错
                if (datas.get(i).getAnswer().equals(String.valueOf(datas.get(i).getChoose()))) {
                    rightCount++;
                } else {
                    errorCount++;
                }
            }
        }
        right.setText(String.valueOf(rightCount));
        error.setText(String.valueOf(errorCount));
    }

    private void initView() {

        chronometer = (Chronometer) findViewById(R.id.timer);
        close_timer = (TextView) findViewById(R.id.close_timer);
        close_timer.setOnClickListener(this);

        chongzuo = (LinearLayout) findViewById(R.id.chong_zuo);
        shoucang = (LinearLayout) findViewById(R.id.shou_cang);
        fenxiang = (LinearLayout) findViewById(R.id.fen_xiang);
        currentPage = (TextView) findViewById(R.id.current_page);
        right = (TextView) findViewById(R.id.tv_right);
        error = (TextView) findViewById(R.id.tv_error);
        close = (ImageView) findViewById(R.id.close);
        counts = (TextView) findViewById(R.id.counts);
        currentPage.setText("1");

        chongzuo.setOnClickListener(this);

        shoucang.setOnClickListener(this);
        fenxiang.setOnClickListener(this);

        back = (ImageView) findViewById(R.id.back);
        back.setOnClickListener(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
//        result = "";
//        initState();
    }

    //      初始化状态,判断是计时模式还是不计时模式
    private void initState() {
        if (su.showTiming() == 1) {//计时模式
            startTime();//开始计时
            frontSurfaceView.setVisibility(View.VISIBLE);
            chronometer.setVisibility(View.VISIBLE);
            close_timer.setVisibility(View.VISIBLE);
        } else if (su.showTiming() == 0) {//不计时模式
            final StartTimingDialog dialog = new StartTimingDialog(this);
            dialog.setMessage("是否开始计时？");
            dialog.show();
            dialog.setNoOnclickListener(new StartTimingDialog.onNoOnclickListener() {
                @Override
                public void onNoClick() {
                    dialog.dismiss();
                    su.saveTiming(0);//不计时
                    chronometer.setVisibility(View.GONE);
                    close_timer.setVisibility(View.GONE);
                    frontSurfaceView.setVisibility(View.GONE);
                }
            });
            dialog.setYesOnclickListener(new StartTimingDialog.onYesOnclickListener() {
                @Override
                public void onYesClick() {
                    dialog.dismiss();
//                    人脸识别认证
                    Intent intent = new Intent();
                    intent.putExtra("phone", phone);
                    intent.putExtra("from", 1);
                    intent.setClass(ReviewErrorActivity.this, FaceLogin.class);
                    startActivity(intent);
                }
            });
            dialog.setNeverOnclickListener(new StartTimingDialog.onNeverOnclickListener() {
                @Override
                public void onNeverClick() {
                    dialog.dismiss();
                    su.saveTiming(2);
                    chronometer.setVisibility(View.GONE);
                    close_timer.setVisibility(View.GONE);
                    frontSurfaceView.setVisibility(View.GONE);
                }
            });
        } else if (su.showTiming() == 2) {
//            不再提示是否计时
            chronometer.setVisibility(View.GONE);
            close_timer.setVisibility(View.GONE);
            frontSurfaceView.setVisibility(View.GONE);
        }
    }

    private void initCamera() {
        new Thread() {
            @Override
            public void run() {
                super.run();
                try {
                    if (mFrontCamera == null) {
                        mFrontCamera = Camera.open(Camera.CameraInfo.CAMERA_FACING_FRONT);
                    }

                    Camera.Parameters parameters = mFrontCamera.getParameters();

                    if (getResources().getConfiguration().orientation
                            != Configuration.ORIENTATION_LANDSCAPE) {
                        parameters.set("orientation", "portrait");
                        mFrontCamera.setDisplayOrientation(90);
                        parameters.setRotation(90);
                    } else {
                        parameters.set("orientation", "landscape");
                        mFrontCamera.setDisplayOrientation(0);
                        parameters.setRotation(0);
                    }
                    mFrontCamera.setParameters(parameters);

                    mFrontCamera.setPreviewDisplay(frontHolder);
                    mFrontCamera.startPreview();

//自动对焦
                    mFrontCamera.autoFocus(mAutoFocus);
                    //对焦后拍照
//                            开始计时并上传照片
                    if (timer == null) {
                        timer = new Timer();
                    }

                    timerTask = new TimerTask() {

                        @Override
                        public void run() {
                            mFrontCamera.takePicture(null, null, myPicCallback);
                        }
                    };
                    timer.schedule(timerTask, 60000, 60000);//1分钟执行一次


                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();

    }

    //拍照成功回调函数
    private Camera.PictureCallback myPicCallback = new Camera.PictureCallback() {

        @Override
        public void onPictureTaken(byte[] data, Camera camera) {
            mFrontCamera.startPreview();
            Log.d("OrderTextActivity", "拍照成功");
            // 将得到的照片进行270°旋转，使其竖直
            Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
            Matrix matrix = new Matrix();
            matrix.preRotate(0);
            bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
            result = bitmapToBase64(bitmap);
//            System.out.println("--------" + result);
            System.out.println("---------------" + chronometer.getText().toString());
            time = getChronometerSeconds(chronometer);
            new Thread() {
                @Override
                public void run() {
                    super.run();
                    Log.d("CollectQuestionActivity", "time=========" + time);
                    OkGo.post(WebInterface.theoretical_timing_two)
                            .tag(this)
                            .params("signid", onlyID)
                            .params("subject", subject0)
                            .params("type", "1")
                            .params("time", time)
                            .params("picture", result)
                            .execute(new StringCallback() {
                                @Override
                                public void onSuccess(String s, Call call, Response response) {
                                    int num = JsonUtils.parseNum(s);
                                    if (num == 1) {
                                        su.saveLearnedTime(learnTime);
                                        learnTime = 0;
                                    } else {
                                    }
                                }
                            });
//                    mFrontCamera.stopPreview();
//                    mFrontCamera.release();
//                    mFrontCamera = null;
                }
            }.start();

        }
    };

    /**
     * bitmap转为base64
     *
     * @param bitmap
     * @return
     */
    public static String bitmapToBase64(Bitmap bitmap) {

        String result = null;
        ByteArrayOutputStream baos = null;
        try {
            if (bitmap != null) {
                baos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);

                baos.flush();
                baos.close();

                byte[] bitmapBytes = baos.toByteArray();
                result = Base64.encodeToString(bitmapBytes, Base64.DEFAULT);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (baos != null) {
                    baos.flush();
                    baos.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    //      开始计时
    private void startTime() {
        OkGo.post(WebInterface.theoretical_timing)
                .tag(this)
                .params("signid", onlyID)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        int num = JsonUtils.parseNum(s);
                        if (num == 1) {

//                            开始计时并上传照片
                            initCamera();
                            chronometer.start();


                            if (timer == null) {
                                timer = new Timer();
                            }
                            timerTask1 = new TimerTask() {

                                @Override
                                public void run() {
                                    learnTime++;
                                }
                            };
                            timer.schedule(timerTask1, 0, 1000);//1000ms执行一次
                        }
                    }
                });
    }

    @Override
    protected void onStop() {
        super.onStop();
//        if (su.showTiming() == 1 && result != "") {
//            new Thread() {
//                @Override
//                public void run() {
//                    super.run();
//                    Log.d("CollectQuestionActivity", "time=========" + time);
//                    OkGo.post(WebInterface.theoretical_timing_two)
//                            .tag(this)
//                            .params("signid", onlyID)
//                            .params("subject", subject0)
//                            .params("type", "1")
//                            .params("time", time)
//                            .params("picture", result)
//                            .execute(new StringCallback() {
//                                @Override
//                                public void onSuccess(String s, Call call, Response response) {
//                                    int num = JsonUtils.parseNum(s);
//                                    if (num == 1) {
//                                        su.saveLearnedTime(learnTime);
//                                        learnTime = 0;
////                                        Toast.makeText(OrderTextActivity.this, "发送照片成功", Toast.LENGTH_SHORT).show();
//                                    } else {
////                                        Toast.makeText(OrderTextActivity.this, "发送照片失败", Toast.LENGTH_SHORT).show();
//                                    }
//                                }
//                            });
//                }
//            }.start();
//        }
//
//        stopTime();
//        timer = null;
//        chronometer.stop();
//        if (mFrontCamera != null) {
//            mFrontCamera.stopPreview();
//            mFrontCamera.release();
//            mFrontCamera = null;
//        }
//
    }


    @Override
    protected void onRestart() {
        super.onRestart();
//        chronometer.start();
//        startTime();
    }

    /**
     * 停止自动减时
     */
    private void stopTime() {
        //完成拍照后关闭Activity
        if (timer != null)
            timer.cancel();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        stopTime();
//        Thread.interrupted();
//        if (mFrontCamera != null) {
//            mFrontCamera.stopPreview();
//            mFrontCamera.release();
//            mFrontCamera = null;
//        }
        UMShareAPI.get(this).release();
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
        layoutAdapter = new ReviewErrorAdapter(this, mRecyclerView);
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
                currentPage.setText(String.valueOf(curPosition + 1));
                layoutAdapter.notifyItemChanged(prePosition);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int i, int i2) {
            }
        });

        mRecyclerView.addOnPageChangedListener(new RecyclerViewPager.OnPageChangedListener() {
            @Override
            public void OnPageChanged(int oldPosition, int newPosition) {
                Log.d("test", "oldPosition:" + oldPosition + " newPosition:" + newPosition);
                recyclerView.scrollToPosition(newPosition);
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
            case R.id.chong_zuo://重做
                for (int i = 0; i < datas.size(); i++) {
                    datas.get(i).setIsdo(0);
                    datas.get(i).setChoose(0);
                }
                Intent intent = new Intent(ReviewErrorActivity.this, PraticeActivity.class);
                intent.putExtra("FROM", 1);
                startActivity(intent);
                finish();
                break;
            case R.id.shou_cang:
                //网络收藏
                gson = new Gson();
                OkGo.post(WebInterface.is_save)
                        .tag(this)
                        .params("subject", subject0)
                        .params("questionid", datas.get(curPosition).getSid())
                        .params("signid", onlyID)
                        .execute(new StringCallback() {
                            @Override
                            public void onSuccess(String s, Call call, Response response) {
                                IsSave isSave = gson.fromJson(s, IsSave.class);
                                Toast.makeText(ReviewErrorActivity.this, isSave.getMsg(), Toast.LENGTH_SHORT).show();
                            }
                        });

//                本地收藏到数据库
                dao.addCollectQuestions(datas.get(curPosition + 1), "one");

                break;
            case R.id.fen_xiang:
//                截屏，分享截屏图片，删除截屏图片
//                UmengTool.getSignature(this);

                screenshot();//截屏
                new ShareAction(ReviewErrorActivity.this).setDisplayList(SHARE_MEDIA.QQ, SHARE_MEDIA.QZONE, SHARE_MEDIA.WEIXIN, SHARE_MEDIA.WEIXIN_CIRCLE)
                        .withMedia(new UMImage(this, file))
                        .withText("fenxiang")
                        .setCallback(umShareListener)
                        .open();
                break;
            case R.id.close_timer:
                chronometer.setVisibility(View.GONE);
                close_timer.setVisibility(View.GONE);
                break;

        }
    }

    File file;

    private void screenshot() {
        // 获取屏幕
        View dView = getWindow().getDecorView();
        dView.setDrawingCacheEnabled(true);
        dView.buildDrawingCache();
        Bitmap bmp = dView.getDrawingCache();
        if (bmp != null) {
            try {
                // 获取内置SD卡路径
                String sdCardPath = Environment.getExternalStorageDirectory().getPath();
                // 图片文件路径
                String filePath = sdCardPath + File.separator + "screenshot.png";
                System.out.println("picPath====" + filePath);
                file = new File(filePath);
                FileOutputStream os = new FileOutputStream(file);
                bmp.compress(Bitmap.CompressFormat.PNG, 100, os);
                os.flush();
                os.close();
            } catch (Exception e) {
            }
        }
    }

    //分享平台回调
    private UMShareListener umShareListener = new UMShareListener() {
        @Override
        public void onResult(SHARE_MEDIA platform) {
            Log.d("plat", "platform" + platform);
//        分享成功，删除截屏
            if (file.exists()) {
                file.delete();
            }
        }

        @Override
        public void onError(SHARE_MEDIA share_media, Throwable throwable) {
            if (throwable != null) {
                Log.d("throw", "throw:" + throwable.getMessage());
            }
        }

        @Override
        public void onCancel(SHARE_MEDIA share_media) {
        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
        Log.d("result", "onActivityResult");
    }

    /**
     * @param cmt Chronometer控件
     * @return 小时+分钟+秒数  的所有秒数
     */
    public String getChronometerSeconds(Chronometer cmt) {
        int totalss = 0;
        String string = cmt.getText().toString();
        if (string.length() == 8) {

            String[] split = string.split(":");
            String string2 = split[0];
            int hour = Integer.parseInt(string2);
            int Hours = hour * 3600;
            String string3 = split[1];
            int min = Integer.parseInt(string3);
            int Mins = min * 60;
            int SS = Integer.parseInt(split[2]);
            totalss = Hours + Mins + SS;
            return String.valueOf(totalss);
        } else if (string.length() == 5) {

            String[] split = string.split(":");
            String string3 = split[0];
            int min = Integer.parseInt(string3);
            int Mins = min * 60;
            int SS = Integer.parseInt(split[1]);

            totalss = Mins + SS;
            return String.valueOf(totalss);
        }
        System.out.println("908098080======" + String.valueOf(totalss));
        return String.valueOf(totalss);
    }

}
