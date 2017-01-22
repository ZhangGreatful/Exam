package com.haha.exam.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.hardware.Camera;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.util.Log;
import android.view.KeyEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.haha.exam.R;
import com.haha.exam.adapter.PracticeAdapter;
import com.haha.exam.adapter.TopicAdapter;
import com.haha.exam.bean.AllQuestions;
import com.haha.exam.bean.ErrorQuestion;
import com.haha.exam.bean.IsSave;
import com.haha.exam.dao.ExamDao;
import com.haha.exam.dialog.MyDialog;
import com.haha.exam.utils.SPUtils;
import com.haha.exam.web.WebInterface;
import com.lsjwzh.widget.recyclerviewpager.RecyclerViewPager;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.sothree.slidinguppanel.SlidingUpPanelLayout;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.wanzheng.driver.home.FaceLogin;
import com.wanzheng.driver.network.JsonUtils;
import com.wanzheng.driver.util.FaceUtil;
import com.wanzheng.driver.util.FormatUtil;
import com.wanzheng.driver.util.SystemUtil;

import org.json.JSONArray;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
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
 * <p>
 * 只有在计时模式下才会上传成绩，生成订单，同时，做题过程中进行抓拍
 */
public class PraticeActivity extends BaseActivity implements View.OnClickListener {

    private int come_in_times = 0;
    private SystemUtil su;
    private String onlyID;
    private SPUtils spUtils = new SPUtils();
    private String subject, subject0;
    private String cartype;
    private ReviewErrorActivity reviewErrorActivity;
    private RecyclerViewPager mRecyclerView;
    private PracticeAdapter practiceAdapter;
    private SlidingUpPanelLayout mLayout;
    private TopicAdapter topicAdapter;
    private RecyclerView recyclerView;
    private int prePosition = 0;
    private int curPosition;
    private ExamDao dao;
    private Gson gson = new Gson();
    public static int time;//考试用时
    private TextView rightCount, errorCount, counts;
    public static int right, error;
    public static List<AllQuestions.DataBean> datas;
    private TextView currentPage;

    private ErrorQuestion[] question;
    private Map map;
    private List<Map> list;
    public static int orderId;


    private SurfaceView frontSurfaceView;
    private SurfaceHolder frontHolder;
    private Camera mFrontCamera;
    private TimerTask timerTask2, timerTask1;
    private int learnTime;
    private String result;
    private String phone;


    static int minute;
    static int second;
    final static String tag = "tag";
    TextView timeView;
    Timer timer;
    TimerTask timerTask, timerTask3;
    //    计时器
    Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            System.out.println("handle!");
            time++;
            learnTime++;
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
//                    到点自动上传成绩
                    commitGrade();
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
    private ImageView back, image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /**
         * 初始化前置相机参数
         */
        // 初始化surface view
        frontSurfaceView = (SurfaceView) findViewById(R.id.front_surfaceview);
        // 初始化surface holder
        frontHolder = frontSurfaceView.getHolder();
        frontHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        su = new SystemUtil(PraticeActivity.this);
//        默认是不计时状态
        su.saveTiming(0);
        phone = su.showPhone();
        onlyID = su.showOnlyID();
        dao = new ExamDao(PraticeActivity.this);
        subject = (String) spUtils.get(this, "subject", "one");
        subject0 = (String) spUtils.get(this, "subject0", "1");
        cartype = (String) spUtils.get(this, "cartype", "xc");
        reviewErrorActivity = new ReviewErrorActivity();
        list = new ArrayList<>();
        minute = -1;
        second = -1;
        time = 0;
        right = 0;
        error = 0;
        initView();
        initTime();
        initViewPager();
        initSlidingUoPanel();
        initList();
        initData();
//        getTime();


    }

    @Override
    protected void onStart() {
        super.onStart();
        if (!su.showOnlyID().equals("") && su.showTiming() == 0) {//不是游客模式登录
            initState();
        }
        result = "";
        if (su.showTiming() == 1 && come_in_times == 1) {

//           计时模式下， 发送消息，生成订单
            OkGo.post(WebInterface.theoretical_record_create)
                    .tag(this)
                    .params("signid", onlyID)
                    .params("subject", subject0)
                    .params("type", "2")//2 模拟考试
                    .execute(new StringCallback() {
                        @Override
                        public void onSuccess(String s, Call call, Response response) {
                            int num = JsonUtils.parseNum(s);
                            if (num == 1) {
                                Toast.makeText(PraticeActivity.this, "创建订单成功", Toast.LENGTH_SHORT).show();

                                orderId = JsonUtils.parseOrderId(s);
                                initCamera();//开始计时
                                frontSurfaceView.setVisibility(View.VISIBLE);
                            }
                        }
                    });


        } else {
            frontSurfaceView.setVisibility(View.GONE);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (minute > 10 && second > 10) {
            timeView.setText(minute + ":" + second);
        } else if (minute > 10 && second < 10) {
            timeView.setText(minute + ":0" + second);
        } else if (minute < 10 && second > 10) {
            timeView.setText("0" + minute + ":" + second);
        } else if (minute < 10 && second < 10) {
            timeView.setText("0" + minute + ":0" + second);
        }
        Log.d("PraticeActivity", "minute===" + minute + "second=====" + second);
        if (su.showTiming() == 1 || su.showTiming() == -1) {
            learnTime = 0;
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
    }

//    }


    MyDialog dialog;

    //      初始化状态,判断是计时模式还是不计时模式
    private void initState() {
        Log.d("FaceLogin", "showTiming2==========" + su.showTiming());
//        if (su.showTiming() == 1) {//计时模式
//            startTime();//开始计时
//            frontSurfaceView.setVisibility(View.VISIBLE);
//        } else
//        if (su.showTiming() == 0||su.showTiming()==2) {//不计时模式
        dialog = new MyDialog(this);
        dialog.setMessage("不计时考试时间将不会统计到学时信息，是否开始计时？");
        dialog.setNoMessage("不计时");
        dialog.setYesMessage("计时");
        dialog.show();
        dialog.setNoOnclickListener("", new MyDialog.onNoOnclickListener() {
            @Override
            public void onNoClick() {
                dialog.dismiss();
                su.saveTiming(0);//不计时
                frontSurfaceView.setVisibility(View.GONE);
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
        });
        dialog.setYesOnclickListener("", new MyDialog.onYesOnclickListener() {
            @Override
            public void onYesClick() {
//                发送消息，生成订单号

                dialog.dismiss();
//                    人脸识别认证
                Intent intent = new Intent();
                intent.putExtra("phone", phone);
                intent.putExtra("from", 1);
                intent.setClass(PraticeActivity.this, FaceLogin.class);
                startActivity(intent);
            }
        });
        dialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialogInterface, int i, KeyEvent keyEvent) {
                if (i == KeyEvent.KEYCODE_BACK) {
                    if (dialog.isShowing()) {
                        PraticeActivity.this.finish();
                    }
                }
                return false;
            }
        });
//        }
    }

    private void initCamera() {
        frontSurfaceView.setVisibility(View.VISIBLE);
        frontHolder.addCallback(surfaceCallback);
        new Thread() {
            @Override
            public void run() {
                super.run();
//                            开始计时并上传照片
                if (timer == null) {
                    timer = new Timer();
                }

                timerTask2 = new TimerTask() {

                    @Override
                    public void run() {
                        if (mFrontCamera != null && learnTime > 4) {
                            mFrontCamera.takePicture(null, null, myPicCallback);
                        }
                    }
                };
                timer.schedule(timerTask2, 5000, 60000);//1分钟执行一次
//

            }
//            catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
        }.start();

    }

    // SurfaceView当前的持有者的回调接口的实现
    SurfaceHolder.Callback surfaceCallback = new SurfaceHolder.Callback() {

        @Override
        public void surfaceCreated(SurfaceHolder holder) {
            if (mFrontCamera == null) {
                mFrontCamera = Camera.open(Camera.CameraInfo.CAMERA_FACING_FRONT);
            }

            Log.d("VideoActivity", "摄像头Open完成");
            try {
                mFrontCamera.setPreviewDisplay(holder);
            } catch (IOException e) {
                mFrontCamera.release();
                mFrontCamera = null;
            }
        }

        @Override
        public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
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
//            parameters.setPictureFormat(ImageFormat.JPEG);
            mFrontCamera.setParameters(parameters);
//            mFrontCamera.autoFocus(mAutoFocus);
            mFrontCamera.startPreview();
        }

        @Override
        public void surfaceDestroyed(SurfaceHolder holder) {
            if (null != mFrontCamera) {
                mFrontCamera.setPreviewCallback(null);
                mFrontCamera.stopPreview();
                mFrontCamera.release();
                mFrontCamera = null;
            }
        }
    };

    //拍照成功回调函数
    private Camera.PictureCallback myPicCallback = new Camera.PictureCallback() {

        @Override
        public void onPictureTaken(byte[] data, Camera camera) {
            mFrontCamera.startPreview();
            Log.d("OrderTextActivity", "拍照成功");
            // 将得到的照片进行270°旋转，使其竖直

            Bitmap byteToBitmap = byteToBitmap(data);
            byteToBitmap = FaceUtil.rotateImage(180, byteToBitmap);
            byteToBitmap = FormatUtil.comp(byteToBitmap, 10);
            result = FormatUtil.getBitmapStrBase64(byteToBitmap);

//            Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
//            Matrix matrix = new Matrix();
//            matrix.preRotate(180);
//            bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
//            final Bitmap finalBitmap = bitmap;
            new Thread(new Runnable() {
                @Override
                public void run() {
//                    Bitmap byteToBitmap = FormatUtil.comp(finalBitmap, 10);
//                    result = bitmapToBase64(byteToBitmap);

                    try {
                        su.saveLearnedTime(learnTime);
                        Log.d("PraticeActivity", "learnTime==============" + learnTime);
                        OkGo.post(WebInterface.theoretical_pic_info)//上传图片信息
                                .tag(this)
                                .params("signid", onlyID)
                                .params("orderid", orderId)
                                .params("type", "3")
                                .params("time", learnTime)
                                .params("picture", result)
                                .execute(new StringCallback() {
                                    @Override
                                    public void onSuccess(String s, Call call, Response response) {
                                        int num = JsonUtils.parseNum(s);
                                        if (num == 1) {
                                            Toast.makeText(PraticeActivity.this, "上传照片成功", Toast.LENGTH_SHORT).show();
                                        } else {
                                        }
                                    }
                                });
                        learnTime = 0;
                    } catch (Exception e) {
                        Log.d("VideoActivity", "上传照片失败");
                    }
                }
            }).start();

        }
    };

    private Bitmap byteToBitmap(byte[] data) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        Bitmap b = BitmapFactory.decodeByteArray(data, 0, data.length, options);
        int i = 0;
        while (true) {
            if ((options.outWidth >> i <= 1000) && (options.outHeight >> i <= 1000)) {
                options.inSampleSize = (int) Math.pow(2.0D, i);
                options.inJustDecodeBounds = false;
                b = BitmapFactory.decodeByteArray(data, 0, data.length, options);
                break;
            }
            i += 1;
        }
        return b;
    }

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

    /**
     * 停止自动减时
     */
    private void stopTime() {
        //完成拍照后关闭Activity
        if (timer != null)
            timer.cancel();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            //                显示提示框，还有多少题目没有完成
            int undo = datas.size() - right - error;
            final MyDialog dialog = new MyDialog(this);
            dialog.setMessage("还有" + undo + "道题目没有做，确认退出吗?");
            dialog.setTitleVisible(true);
            dialog.show();
            dialog.setNoOnclickListener("", new MyDialog.onNoOnclickListener() {
                @Override
                public void onNoClick() {
                    dialog.dismiss();
                }
            });
            dialog.setYesOnclickListener("", new MyDialog.onYesOnclickListener() {
                @Override
                public void onYesClick() {
                    finish();
                    dialog.dismiss();
                }
            });
//            }

        }

        return false;

    }

    //      获取当前的日期和时间,考试用时
    private void getTime() {
//        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
//        Date curDate = new Date(System.currentTimeMillis());//获取当前时间
//        date = formatter.format(curDate);
//
//        SimpleDateFormat formatter1 = new SimpleDateFormat("HH:mm:ss");
//        curentTime = formatter1.format(curDate);

    }

    //      初始化数据
    private void initData() {
//     随机从数据库中抽取80道单选题，20道判断题
        Intent intent = getIntent();
        if (intent.getIntExtra("FROM", 0) == 1) {//从错题回顾过来的
            datas = reviewErrorActivity.datas;
        } else {//从模拟考试页面过来的
            datas = dao.getSubject1PractiseQuestions(cartype, subject);
        }
        System.out.println("模拟考试题目长度是： " + datas.size());
        if (practiceAdapter != null) {
            practiceAdapter.setDataList(datas);
        }

        if (topicAdapter != null) {
            topicAdapter.setDataNum(datas.size());
            topicAdapter.setDataList(datas);
        }
//        }
    }

    private void initTime() {
        //            设置倒计时时间
        if (minute == -1 && second == -1) {
            if (subject0.equals("1")) {
                minute = 45;
            } else if (subject0.equals("4")) {
                minute = 30;
            }
            second = 00;
        }
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
        currentPage = (TextView) findViewById(R.id.current_page);
        counts = (TextView) findViewById(R.id.counts);
        currentPage.setText("1");
        if (subject.equals("one")) {
            counts.setText("100");
        } else {
            counts.setText("50");
        }


        back = (ImageView) findViewById(R.id.back);
        image = (ImageView) findViewById(R.id.iv_up);
        back.setOnClickListener(this);
        jiaojuan.setOnClickListener(this);
        shoucang.setOnClickListener(this);
        fenxiang.setOnClickListener(this);
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
                System.out.println("执行了滑动========");

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
                rightCount.setText(String.valueOf(practiceAdapter.rightCount));
                errorCount.setText(String.valueOf(practiceAdapter.error_count));
                currentPage.setText(String.valueOf(newPosition + 1));
                curPosition = newPosition;
                prePosition = oldPosition;
                topicAdapter.notifyCurPosition(newPosition);
                topicAdapter.notifyPrePosition(oldPosition);
                topicAdapter.notifyPrePosition(newPosition - 1);
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
//                显示提示框，还有多少题目没有完成
                int undo = datas.size() - practiceAdapter.rightCount - practiceAdapter.error_count;
                final MyDialog dialog = new MyDialog(this);
                dialog.setMessage("还有" + undo + "道题目没有做，确认退出吗?");
                dialog.setTitleVisible(true);
                dialog.show();
                dialog.setNoOnclickListener("", new MyDialog.onNoOnclickListener() {
                    @Override
                    public void onNoClick() {
                        dialog.dismiss();
                    }
                });
                dialog.setYesOnclickListener("", new MyDialog.onYesOnclickListener() {
                    @Override
                    public void onYesClick() {
                        finish();
                        dialog.dismiss();
                    }
                });
                break;
            case R.id.jiao_juan:
                int undo1 = datas.size() - practiceAdapter.rightCount - practiceAdapter.error_count;
                if (undo1 != 0) {
                    final MyDialog dialog1 = new MyDialog(this);
                    dialog1.setMessage("还有" + undo1 + "道题目没有做，确认交卷吗?");
                    dialog1.setTitleVisible(true);
                    dialog1.show();
                    dialog1.setNoOnclickListener("", new MyDialog.onNoOnclickListener() {
                        @Override
                        public void onNoClick() {
                            dialog1.dismiss();
                        }
                    });
                    dialog1.setYesOnclickListener("", new MyDialog.onYesOnclickListener() {
                        @Override
                        public void onYesClick() {
                            if (su.showTiming() == 1) {//只有在计时模式下，点击交卷才会上传成绩
                                commitGrade();
                            }
                            dialog1.dismiss();
                            Intent intent = new Intent(PraticeActivity.this, PracticeResultActivity.class);
                            intent.putExtra("msg", -1);
                            startActivity(intent);
                            finish();
                        }
                    });
                } else {
                    if (su.showTiming() == 1) {//计时模式下，点击交卷才会上传成绩
                        commitGrade();
                    }
                    Intent intent = new Intent(PraticeActivity.this, PracticeResultActivity.class);
                    intent.putExtra("msg", -1);
                    startActivity(intent);
                    finish();
                }

                break;
            case R.id.shou_cang:
                if (onlyID.equals("")) {
                    Toast.makeText(this, "请先登录", Toast.LENGTH_SHORT).show();
                } else {
//                网络收藏
                    OkGo.post(WebInterface.is_save)
                            .tag(this)
                            .params("subject", subject0)
                            .params("questionid", datas.get(curPosition).getSid())
                            .params("signid", onlyID)
                            .execute(new StringCallback() {
                                @Override
                                public void onSuccess(String s, Call call, Response response) {
                                    IsSave isSave = gson.fromJson(s, IsSave.class);
                                    Toast.makeText(PraticeActivity.this, isSave.getMsg(), Toast.LENGTH_SHORT).show();
                                }
                            });
                }
                break;
            case R.id.fen_xiang:
//                截屏，分享截屏图片，删除截屏图片
                screenshot();//截屏
                new ShareAction(PraticeActivity.this).setDisplayList(SHARE_MEDIA.QQ, SHARE_MEDIA.QZONE, SHARE_MEDIA.WEIXIN, SHARE_MEDIA.WEIXIN_CIRCLE)
                        .withMedia(new UMImage(this, file))
                        .withText("fenxiang")
                        .setCallback(umShareListener)
                        .open();

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


    //    上传成绩
    public void commitGrade() {
        int score = 0;
        if (subject.equals("one")) {
            score = right;
        } else {
            score = 2 * right;
        }
//                上传成绩到服务器
        for (int i = 0; i < datas.size(); i++) {
            map = new HashMap();
            map.put("sid", datas.get(i).getSid());
            map.put("isdo", String.valueOf(datas.get(i).getIsdo()));
            map.put("choose", String.valueOf(datas.get(i).getChoose()));
            list.add(map);
        }
        JSONArray jsonObject = new JSONArray(list);
        System.out.println("json=============" + jsonObject.toString());

        Log.d("PraticeActivity", "learnTime==========" + String.valueOf(time));
        Log.d("PraticeActivity", "question_info=============" + jsonObject.toString());
//        上传成绩
        OkGo.post(WebInterface.theoretical_order_submit)
                .tag(this)
                .params("signid", onlyID)
                .params("type", "2")
                .params("orderid", orderId)
                .params("use_time", time)
                .params("score", String.valueOf(score))
                .params("all_question_info", jsonObject.toString())
                .params("totalnumber", datas.size())
                .params("correctnumber", right)
                .params("wrongnumber", error)
                .params("pass_status", "90")
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        int num = JsonUtils.parseNum(s);
                        if (num == 1) {
                            Toast.makeText(PraticeActivity.this, "成绩上传成功", Toast.LENGTH_SHORT).show();
                        }
                    }
                });


//            得到错题信息
        question = new ErrorQuestion[practiceAdapter.list.size()];
        for (int i = 0; i < question.length; i++) {
            question[i] = practiceAdapter.list.get(i);
            System.out.println("错误信息=======" + practiceAdapter.list.get(i).getSid());
        }
    }

    @Override
    protected void onStop() {
        super.onStop();

        come_in_times++;
        //        结束计时
        Log.v(tag, "log---------->onDestroy!");
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
        if (timerTask != null) {
            timerTask = null;
        }
        if (timerTask1 != null) {
            timerTask1 = null;
        }
        if (timerTask2 != null) {
            timerTask2 = null;
        }
        if (timerTask3 != null) {
            timerTask3 = null;
        }

        if (su.showTiming() == 1 && result != "") {
            new Thread() {
                @Override
                public void run() {
                    super.run();
                    Log.d("CollectQuestionActivity", "learnTime=========" + learnTime);
                    su.saveLearnedTime(learnTime);
                    OkGo.post(WebInterface.theoretical_pic_info)
                            .tag(this)
                            .params("signid", onlyID)
                            .params("orderid", orderId)
                            .params("picture", result)
                            .params("time", learnTime)
                            .params("type", "2")
                            .execute(new StringCallback() {
                                @Override
                                public void onSuccess(String s, Call call, Response response) {
                                    int num = JsonUtils.parseNum(s);
                                    if (num == 1) {
                                        Toast.makeText(PraticeActivity.this, "上传照片成功", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                    learnTime = 0;
                }
            }.start();
        }

//        停止发送照片，计时
        stopTime();
        timer = null;
    }

    @Override
    protected void onDestroy() {

        super.onDestroy();
        OkGo.getInstance().cancelTag(this);
        stopTime();
        Thread.interrupted();
        if (mFrontCamera != null) {
            mFrontCamera.stopPreview();
            mFrontCamera.release();
            mFrontCamera = null;
        }
    }
}
