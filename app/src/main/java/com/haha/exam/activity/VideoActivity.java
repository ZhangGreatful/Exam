package com.haha.exam.activity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.hardware.Camera;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.util.Log;
import android.view.KeyEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.WindowManager;
import android.widget.Chronometer;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.mytest1.JCUserAction;
import com.example.administrator.mytest1.JCUserActionStandard;
import com.example.administrator.mytest1.JCVideoPlayer;
import com.example.administrator.mytest1.JCVideoPlayerStandard;
import com.example.administrator.mytest1.TimerCallBack;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.gson.Gson;
import com.haha.exam.R;
import com.haha.exam.adapter.RecycleVideoAdapter;
import com.haha.exam.bean.VideoCollect;
import com.haha.exam.bean.VideoInfo;
import com.haha.exam.dialog.StartTimingDialog;
import com.haha.exam.view.HorizontalListView;
import com.haha.exam.web.WebInterface;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.umeng.analytics.MobclickAgent;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMVideo;
import com.wanzheng.driver.home.FaceLogin;
import com.wanzheng.driver.network.JsonUtils;
import com.wanzheng.driver.util.FaceUtil;
import com.wanzheng.driver.util.FormatUtil;
import com.wanzheng.driver.util.SystemUtil;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import okhttp3.Call;
import okhttp3.Response;

/**
 * 视频计时逻辑
 * 监听视频的播放位置playposition,取playposition和开始播放的位置startposition的时间差为视频播放的总时间total
 * 每隔一段时间，上传照片，上传照片的时间total为oldposition，每次上传照片的时间learnTime=total(当前的播放时间段)-oldposition(上次上传照片的时间段)
 * <p>
 * 上传的时间包括：
 * 1.上传照片时，上传上传照片的时间段learnTime（用于记录学时）；
 * 2.上传本次播放视频共播放多长时间usetime(上传total),用于学时订单中显示用时
 * 3.刷新播放进度的时间playposition，
 * 4.若视频播放完成，上传标签end_status(//0:已结束  1：未结束)
 * <p>
 * 为了避免学时重复，每次onStop()的时候，将startposition=playposition,total,learnTime全部重置为0，
 * 再次进入的时候重新创建订单
 * <p>
 * alltime  暂时未用到
 * oldposition      上一次传照片的时间
 * playposition  当前的播放位置
 * total=playposition-startposition  视频播放的总时间
 * startposition    视频开始播放的位置
 * learnTime =total-oldposition  上传照片的时间段
 * <p>
 * Created by Administrator on 2016/11/7.
 */
public class VideoActivity extends AppCompatActivity implements View.OnClickListener, TimerCallBack {

    private Bundle bundle;
    private int oldposition;//上一次传照片的时间
    private int playposition;
    private String orderid;
    private SystemUtil su;
    private String onlyID;
    private String subject, subject0, phone;
    private String result;
    private String currentTime;
    private long mRecordTime;
    private boolean isPlaying;
    private int total, alltime;
    private int startposition;
    private int oldCamralTime = 0;
    private JCVideoPlayer.JCAutoFullscreenListener mSensorEventListener;
    private SensorManager mSensorManager;
    private GoogleApiClient client;
    //导入视频组件引用
    private JCVideoPlayerStandard jcVideo;
    private int play_progress;
    private String video_url, video_id, video_thumb, video_title, show_counts;
    private String progress_Title;
    private Dialog progress;
    private TextView content, counts;
    private TextView videoCounts, currentPosition;
    private LinearLayout share, shoucang;
    private ImageView collect;
    private int learnTime;

    boolean issave;
    private int width = 0;

    private HorizontalListView hListView;
    private RecycleVideoAdapter adapter;

    private List<String> datas = new ArrayList<>();
    private List<String> num = new ArrayList<>();
    private Gson gson = new Gson();
    private VideoInfo videoInfo;
    private int position;
    private ArrayList<VideoCollect> videoCollects;
    private int selectIndex = 0;

    private String time;
    private RecyclerView recyclerView;

    private SurfaceView frontSurfaceView;
    //    private MySurfaceView frontSurfaceView;
    private SurfaceHolder frontHolder;
    private Camera mFrontCamera;
    private Timer timer;
    private TimerTask timerTask, timerTask2;
    private Chronometer chronometer;
    private TextView close_timer, title;
    private ImageView back;


    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    Log.d("VideoActivity", "播放中");
                    total = playposition - startposition;
//                    alltime++;
                    Log.d("VideoActivity", "total==========" + total);
                    Log.d("VideoActivity", "learnTime===========" + learnTime);
                    Log.d("MyUserActionStandard", "allti+++me+++++++++++++++++++-" + alltime);
                    break;
                case 2:
                    System.out.println("停止播放");
                    chronometer.stop();
                    Log.d("VideoActivity", "time===========" + time);
                    break;
                case 3:
                    Log.d("VideoActivity", "拍摄照片");
                    if (mFrontCamera != null) {
                        mFrontCamera.takePicture(null, null, myPicCallback);
                    }
//                    initCamera();
                    break;
                case 5:
                    issave = true;
                    break;
                case 6:
                    issave = false;
                    break;
                case 8://刷新视频进度
//                    Toast.makeText(VideoActivity.this, "开始刷新视频进度", Toast.LENGTH_SHORT).show();
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            OkGo.post(WebInterface.video_process_refress)
                                    .tag(this)
                                    .params("signid", onlyID)
                                    .params("orderid", orderid)
                                    .params("playposition", playposition)
                                    .execute(new StringCallback() {
                                        @Override
                                        public void onSuccess(String s, Call call, Response response) {
                                            int num = JsonUtils.parseNum(s);
                                            if (num == 1) {
                                                Toast.makeText(VideoActivity.this, "刷新视频播放进度成功", Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    });
                        }
                    }).start();
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);
        currentTime = "";
        oldposition = 0;
        result = "";
        isPlaying = false;
        total = 1;
        alltime = 1;
        orderid = "";
        /**
         * 初始化前置相机参数
         */

        // 初始化surface holder

        width = getWidth() / 2;
        Intent intent = getIntent();
        bundle = intent.getExtras();
        video_url = bundle.getString("url");
        Log.d("VideoActivity", "videoid=============" + bundle.getString("videoid"));
        subject = bundle.getString("subject");
        if (subject.equals("3")) {
            subject0 = "3";
        } else if (subject.equals("4")) {
            subject0 = "3";
        } else if (subject.equals("2")) {
            subject0 = "2";
        } else if (subject.equals("1")) {
            subject0 = "1";
        }else if (subject.equals("5")){
            subject0=bundle.getString("subject0");
        }
        issave = false;
        su = new SystemUtil(this);
        if (su.showTiming() == -1) {
            su.saveTiming(0);
        }
        onlyID = su.showOnlyID();
        phone = su.showPhone();
        position = bundle.getInt("position", 0);//第几个视频
        selectIndex = bundle.getInt("selectIndex", 0);//第几节
        collect = (ImageView) findViewById(R.id.collect);
        initTitle();
        initView();
        chronometer.setBase(SystemClock.elapsedRealtime());//计时器清零
        int hour = (int) ((SystemClock.elapsedRealtime() - chronometer.getBase()) / 1000 / 60);
        chronometer.setFormat("0" + String.valueOf(hour) + ":%s");
        initData();
//        initPlay();
        initState();
//        initCamera();
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
        jcVideo.setUp(video_url
                , JCVideoPlayerStandard.SCREEN_LAYOUT_NORMAL, "");
        jcVideo.seekToInAdvance = startposition * 1000;

    }

    private void initState() {
        Log.d("VideoActivity", "chromor============" + chronometer.getText().toString());
//        frontSurfaceView.setVisibility(View.GONE);
        chronometer.setVisibility(View.GONE);
        close_timer.setVisibility(View.GONE);
        if (su.showTiming() == 0) {//不计时模式
            final StartTimingDialog dialog = new StartTimingDialog(this);
            dialog.setMessage("是否开始计时？");
            dialog.show();
            dialog.setNoOnclickListener(new StartTimingDialog.onNoOnclickListener() {
                @Override
                public void onNoClick() {
                    dialog.dismiss();
                    su.saveTiming(0);//不计时
                    initPlay();
                }
            });
            dialog.setYesOnclickListener(new StartTimingDialog.onYesOnclickListener() {
                @Override
                public void onYesClick() {
                    dialog.dismiss();
//                    frontSurfaceView.setVisibility(View.VISIBLE);
//                    人脸识别认证
                    Intent intent = new Intent();
                    intent.putExtra("phone", phone);
                    intent.putExtra("from", 1);
                    intent.setClass(VideoActivity.this, FaceLogin.class);
                    startActivity(intent);
                }
            });
            dialog.setNeverOnclickListener(new StartTimingDialog.onNeverOnclickListener() {
                @Override
                public void onNeverClick() {
                    dialog.dismiss();
                    su.saveTiming(2);
                    initPlay();
                }
            });
        } else if (su.showTiming() == 2) {
//            不再提示是否计时
            initPlay();
        }
    }


    @Override
    protected void onStart() {
        super.onStart();
        client.connect();
        if (su.showTiming() == 1) {//视频能开启计时模式
            Log.d("VideoActivity", "开启计时模式");
//            initCamera();//开启摄像头，拍摄照片
            Runnable runnable = new Runnable() {
                @Override
                public void run() {

//        frontHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
                    // 初始化surface view
                    frontSurfaceView = (SurfaceView) findViewById(R.id.front_surfaceview);
                    frontSurfaceView.setVisibility(View.VISIBLE);
                    frontHolder = frontSurfaceView.getHolder();
                    frontHolder.addCallback(surfaceCallback);
                    // 设置surface不需要自己的维护缓存区
                    frontHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
//                    initCamera();
                }
            };
            handler.postDelayed(runnable, 3000);

            startTime();//开始计时
        } else if (su.showTiming() == -1) {//人脸识别验证失败
//            frontSurfaceView.setVisibility(View.GONE);
            chronometer.setVisibility(View.GONE);
            close_timer.setVisibility(View.GONE);
            initPlay();
        }
        AppIndex.AppIndexApi.start(client, getIndexApiAction());
    }


    /**
     * 自动对焦的回调方法，用来处理对焦成功/不成功后的事件
     */
    private Camera.AutoFocusCallback mAutoFocus = new Camera.AutoFocusCallback() {

        @Override
        public void onAutoFocus(boolean success, Camera camera) {
            //TODO:空实现
        }
    };


    //      开始计时
    private void startTime() {
//        frontSurfaceView.setVisibility(View.VISIBLE);
//        chronometer.setVisibility(View.VISIBLE);
//        close_timer.setVisibility(View.VISIBLE);
        OkGo.post(WebInterface.theoretical_record_create)
                .tag(this)
                .params("signid", onlyID)
                .params("type", "3")
                .params("subject", subject0)
                .params("videoid", video_id)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        int num = JsonUtils.parseNum(s);
                        if (num == 1) {
                            Log.d("VideoActivity", "开始计时，并开始播放视频==========");
                            orderid = JsonUtils.parseStringOrderId(s);
                            Toast.makeText(VideoActivity.this, "创建学时订单成功", Toast.LENGTH_SHORT).show();
                            initPlay();//播放视频
                        }
                    }
                });
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
//            mFrontCamera.stopPreview();
//            mFrontCamera.release();
//            mFrontCamera = null;
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
//            Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
//
//            Matrix matrix = new Matrix();
//            matrix.preRotate(180);
//            bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
//            final Bitmap finalBitmap = bitmap;
            Bitmap byteToBitmap = byteToBitmap(data);
            byteToBitmap = FaceUtil.rotateImage(180, byteToBitmap);
            byteToBitmap = FormatUtil.comp(byteToBitmap, 10);
            result = FormatUtil.getBitmapStrBase64(byteToBitmap);
            new Thread(new Runnable() {
                @Override
                public void run() {
//                    Bitmap byteToBitmap = FormatUtil.comp(finalBitmap, 10);
//                    result = bitmapToBase64(byteToBitmap);
                    learnTime = total - oldposition;
                    oldposition = total;
                    Log.d("VideoActivity", "saveTime1============" + learnTime);
//                    su.saveLearnedTime(Math.abs(learnTime));
                    if (learnTime < 0) {
                        learnTime = -learnTime;
                    }
                    su.saveLearnedTime(learnTime);
                    alltime += learnTime;
                    try {
                        Log.d("VideoActivity", "learnTime1111============" + learnTime);
                        OkGo.post(WebInterface.theoretical_pic_info)//上传图片信息
                                .tag(this)
                                .params("signid", onlyID)
                                .params("orderid", orderid)
                                .params("type", "3")
//                                .params("time", String.valueOf(Math.abs(learnTime)))
                                .params("time", learnTime)
                                .params("picture", result)
                                .execute(new StringCallback() {
                                    @Override
                                    public void onSuccess(String s, Call call, Response response) {
                                        int num = JsonUtils.parseNum(s);
                                        if (num == 1) {
                                            Toast.makeText(VideoActivity.this, "上传照片成功", Toast.LENGTH_SHORT).show();
                                        } else {
                                        }
                                    }
                                });
                    } catch (Exception e) {
                        Log.d("VideoActivity", "上传照片失败");
                    }
                }
            }).start();

//            result = bitmapToBase64(bitmap);
//            time = getChronometerSeconds(chronometer);
            Log.d("VideoActivity", "time===========" + learnTime);
            System.out.println("--------" + result);

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


    private void initTitle() {
        title = (TextView) findViewById(R.id.title);
        subject = bundle.getString("subject");
        if (subject.equals("1")) {
            title.setText("科目一");
        } else if (subject.equals("2")) {
            title.setText("科目二");
        } else if (subject.equals("3")) {
            title.setText("科目三(实操)");
        } else if (subject.equals("4")) {
            title.setText("科目三(理论)");
        } else if (subject.equals("5")) {
            title.setText("我的收藏");
            issave = true;
            collect.setImageResource(R.mipmap.collected);
        }
    }


    private void initData() {
//        recyclerView.setAdapter(null);

        if (subject.equals("5")) {//收藏的视频

            OkGo.post(WebInterface.video_list)
                    .tag(this)
                    .params("signid", onlyID)
                    .execute(new StringCallback() {
                        @Override
                        public void onSuccess(String s, Call call, Response response) {
                            int po = JsonUtils.parseNum(s);
                            if (po == 1) {
                                if (JsonUtils.parseVideoCollect(s).size() != 0) {
                                    videoCollects = JsonUtils.parseVideoCollect(s);
                                    content.setText("" + (position + 1) + ". " + video_title);
                                    counts.setText(show_counts);
                                    for (int i = 0; i < videoCollects.size(); i++) {
                                        if (videoCollects.get(i) != null) {
                                            datas.add("" + (i + 1) + ". " + videoCollects.get(i).getVideo_title());

                                            num.add(videoCollects.get(i).getShow_count());
                                        }
                                    }
                                    adapter = new RecycleVideoAdapter(datas, num, VideoActivity.this);
                                    videoCounts.setText(String.valueOf(datas.size()));

                                    recyclerView.setAdapter(adapter);
                                    adapter.setSelectIndex(position);

                                    adapter.setOnItemClickListener(new RecycleVideoAdapter.OnRecyclerViewItemClickListener() {
                                        @Override
                                        public void onItemClick(View view, int p) {
                                            position = p;
                                            mRecordTime = SystemClock.elapsedRealtime();
                                            int i = p;
                                            currentPosition.setText(String.valueOf(i + 1));
                                            OkGo.post(WebInterface.video_showcount)
                                                    .tag(this)
                                                    .params("videoid", videoCollects.get(p).getVideo_id())
                                                    .execute(new StringCallback() {
                                                        @Override
                                                        public void onSuccess(String s, Call call, Response response) {
                                                        }
                                                    });
                                            content.setText((position + 1) + ". " + videoCollects.get(p).getVideo_title());
                                            counts.setText(videoCollects.get(p).getShow_count());
                                            video_id = videoCollects.get(p).getVideo_id();
                                            video_url = videoCollects.get(p).getVideo_url();
                                            video_thumb = videoCollects.get(p).getVideo_thumb();
                                            video_title = videoCollects.get(p).getVideo_title();

                                            isSave(video_id);

                                            adapter.setSelectIndex(i);
                                            adapter.notifyDataSetChanged();
                                            isConnect(VideoActivity.this);
                                            OkGo.post(WebInterface.theoretical_record_create)//创建订单
                                                    .tag(this)
                                                    .params("signid", onlyID)
                                                    .params("type", "3")
                                                    .params("subject", subject0)
                                                    .params("videoid", video_id)
                                                    .execute(new StringCallback() {
                                                        @Override
                                                        public void onSuccess(String s, Call call, Response response) {
                                                            int num = JsonUtils.parseNum(s);
                                                            if (num == 1) {
                                                                Log.d("VideoActivity", "开始计时，并开始播放视频==========");
                                                                orderid = JsonUtils.parseStringOrderId(s);
                                                                Toast.makeText(VideoActivity.this, "创建学时订单成功", Toast.LENGTH_SHORT).show();
                                                                initPlay();//播放视频
                                                            }
                                                        }
                                                    });
                                            jcVideo.setUp(video_url
                                                    , JCVideoPlayerStandard.SCREEN_LAYOUT_NORMAL, "");
                                            jcVideo.startButton.performClick();
                                        }
                                    });
                                }
                            }
                            recyclerView.scrollToPosition(position);
                        }
                    });
        } else {
            OkGo.post(WebInterface.video)//视频播放列表
                    .tag(this)
                    .params("subject", subject)
                    .params("signid", onlyID)
                    .execute(new StringCallback() {
                        @Override
                        public void onSuccess(String s, Call call, Response response) {
                            videoInfo = gson.fromJson(s, VideoInfo.class);
                            content.setText((position + 1) + ". " + videoInfo.getData().get(selectIndex).getVideoinfo().get(position).getVideo_title());
                            counts.setText(videoInfo.getData().get(selectIndex).getVideoinfo().get(position).getShow_count() + "次");
                            for (int i = 0; i < videoInfo.getData().get(selectIndex).getVideoinfo().size(); i++) {
                                if (videoInfo.getData().get(selectIndex).getVideoinfo().get(i) != null) {
                                    datas.add((i + 1) + ". " + videoInfo.getData().get(selectIndex).getVideoinfo().get(i).getVideo_title());
                                    num.add(videoInfo.getData().get(selectIndex).getVideoinfo().get(i).getShow_count());
                                }
                            }
                            adapter = new RecycleVideoAdapter(datas, num, VideoActivity.this);
                            videoCounts.setText(String.valueOf(datas.size()
                            ));
                            recyclerView.setAdapter(adapter);
                            adapter.setSelectIndex(position);
                            recyclerView.smoothScrollToPosition(position);
                            adapter.setOnItemClickListener(new RecycleVideoAdapter.OnRecyclerViewItemClickListener() {
                                @Override
                                public void onItemClick(View view, int position) {

                                    if (videoInfo.getData().get(selectIndex).getVideoinfo().get(position - 1).getEnd_status() == 1 &&
                                            videoInfo.getData().get(selectIndex).getVideoinfo().get(position - 1).getPlayflag().equals("1")) {
                                        mRecordTime = SystemClock.elapsedRealtime();
                                        int i = position;
                                        currentPosition.setText(String.valueOf(i + 1));
                                        OkGo.post(WebInterface.video_showcount)
                                                .tag(this)
                                                .params("videoid", videoInfo.getData().get(selectIndex).getVideoinfo().get(i).getVideo_id())
                                                .execute(new StringCallback() {
                                                    @Override
                                                    public void onSuccess(String s, Call call, Response response) {
                                                    }
                                                });
                                        content.setText((i + 1) + ". " + videoInfo.getData().get(selectIndex).getVideoinfo().get(i).getVideo_title());
                                        counts.setText(videoInfo.getData().get(selectIndex).getVideoinfo().get(i).getShow_count());
//                                content.setText("第一章" + "(" + selectIndex + "." + (i + 1) + ")" + videoInfo.getData().get(selectIndex).getVideoinfo().get(i).getVideo_title());
                                        video_id = videoInfo.getData().get(selectIndex).getVideoinfo().get(i).getVideo_id();
                                        video_url = videoInfo.getData().get(selectIndex).getVideoinfo().get(i).getVideo_url();
                                        video_thumb = videoInfo.getData().get(selectIndex).getVideoinfo().get(i).getVideo_thumb();
                                        video_title = videoInfo.getData().get(selectIndex).getVideoinfo().get(i).getVideo_title();

                                        isSave(video_id);
                                        isConnect(VideoActivity.this);
                                        OkGo.post(WebInterface.theoretical_record_create)//创建订单
                                                .tag(this)
                                                .params("signid", onlyID)
                                                .params("type", "3")
                                                .params("subject", subject0)
                                                .params("videoid", video_id)
                                                .execute(new StringCallback() {
                                                    @Override
                                                    public void onSuccess(String s, Call call, Response response) {
                                                        int num = JsonUtils.parseNum(s);
                                                        if (num == 1) {
                                                            Log.d("VideoActivity", "开始计时，并开始播放视频==========");
                                                            orderid = JsonUtils.parseStringOrderId(s);
                                                            Toast.makeText(VideoActivity.this, "创建学时订单成功", Toast.LENGTH_SHORT).show();
                                                            initPlay();//播放视频
                                                        }
                                                    }
                                                });
                                        adapter.setSelectIndex(i);
                                        adapter.notifyDataSetChanged();

                                        jcVideo.setUp(video_url
                                                , JCVideoPlayerStandard.SCREEN_LAYOUT_NORMAL, "");
                                        jcVideo.startButton.performClick();
                                    } else {
                                        Toast.makeText(VideoActivity.this, "有未播放完视频", Toast.LENGTH_SHORT).show();
                                    }

                                }
                            });
                            recyclerView.scrollToPosition(position);
                        }
                    });
        }
    }

    private void initPlay() {//播放视频
        isSave(video_id);

        System.out.println("issave============" + issave);
        OkGo.post(WebInterface.video_showcount)//添加播放次数
                .tag(this)
                .params("videoid", video_id)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                    }
                });

        if (this.progress_Title == null)
            this.progress_Title = "Loading";

        play_progress = bundle.getInt("play_progress", 0);
        oldCamralTime = play_progress;
        isConnect(VideoActivity.this);
//        });
        Log.d("VideoActivity", "videoUrl==========" + video_url);
//        jcVideo.setUp(video_url
//                , JCVideoPlayerStandard.SCREEN_LAYOUT_NORMAL, "");
        Log.d("VideoActivity", "startposition=========" + startposition);
//        jcVideo.seekToInAdvance = startposition * 1000;
        jcVideo.startButton.performClick();

    }


    public int getWidth() {

        WindowManager wm = (WindowManager) this
                .getSystemService(Context.WINDOW_SERVICE);

        int width = wm.getDefaultDisplay().getWidth();
        return width;
    }

    private void initView() {

        recyclerView = (RecyclerView) findViewById(R.id.recycleview);
        //设置布局管理器
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView.setLayoutManager(linearLayoutManager);

        content = (TextView) findViewById(R.id.content);
        counts = (TextView) findViewById(R.id.counts);
        videoCounts = (TextView) findViewById(R.id.video_count);
        currentPosition = (TextView) findViewById(R.id.current_position);
        currentPosition.setText(String.valueOf(position + 1));
        jcVideo = (JCVideoPlayerStandard) this.findViewById(R.id.videoactivity_vv);
        jcVideo.setTimeCallBack(this);

        JCVideoPlayer.setJcUserAction(new MyUserActionStandard());
        mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        mSensorEventListener = new JCVideoPlayer.JCAutoFullscreenListener();
        back = (ImageView) findViewById(R.id.back);
        chronometer = (Chronometer) findViewById(R.id.timer);
        close_timer = (TextView) findViewById(R.id.close);
        startposition = bundle.getInt("playposition", 0);
        playposition = startposition;
        progress_Title = bundle.getString("title");
//        video_url = intent.getStringExtra("url");
        video_thumb = bundle.getString("thumb");
        video_title = bundle.getString("title");
        video_id = bundle.getString("videoid");
        show_counts = bundle.getString("showcount");
        content.setText(video_title);

        share = (LinearLayout) findViewById(R.id.share);
        shoucang = (LinearLayout) findViewById(R.id.shoucang);
        share.setOnClickListener(this);
        shoucang.setOnClickListener(this);
        back.setOnClickListener(this);
        close_timer.setOnClickListener(this);

    }

    @Override
    public void onBackPressed() {
        if (JCVideoPlayer.backPress()) {
            return;
        } else {
            //全屏退出处理
//            if (jcVideo.getCurrentPositionWhenPlaying() <= 5000) {
//
//                Toast.makeText(this, "学习时间太短，成绩无效", Toast.LENGTH_SHORT);
//            } else {
            //结束程序
//                 if(!isFinish){

//                Toast.makeText(this, "稍等，正在拍照", Toast.LENGTH_SHORT);
//                Message msg = handler.obtainMessage();
//                msg.what = 3;
//                msg.arg1 = jcVideo.getCurrentPositionWhenPlaying();
//                handler.sendMessage(msg);
//                SystemClock.sleep(50);

//                 }
//            }
            super.onBackPressed();
        }

    }

    @Override
    protected void onRestart() {
        super.onRestart();
        jcVideo.seekToInAdvance = startposition * 1000;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.share:


                UMVideo umVideo = new UMVideo(video_url);
                umVideo.setThumb(new UMImage(VideoActivity.this, video_thumb));
                umVideo.setTitle(video_title);
                umVideo.setDescription("哈哈手机计时，让天下没有难学的驾照");
                new ShareAction(VideoActivity.this).withText("哈哈手机计时")
                        .setDisplayList(SHARE_MEDIA.QQ, SHARE_MEDIA.QZONE, SHARE_MEDIA.WEIXIN, SHARE_MEDIA.WEIXIN_CIRCLE)
                        .withMedia(umVideo)
                        .withTargetUrl(video_url)
                        .setCallback(umShareListener)
                        .open();
                break;
            case R.id.shoucang:
                System.out.println("点击了收藏");
                if (onlyID.equals("")) {
                    Toast.makeText(this, "请先登录", Toast.LENGTH_SHORT).show();
                } else {
                    if (issave == true) {
                        System.out.println("issave===========" + issave);
                        OkGo.post(WebInterface.video_collect_delete)
                                .tag(this)
                                .params("signid", onlyID)
                                .params("videoid", video_id)
                                .execute(new StringCallback() {
                                    @Override
                                    public void onSuccess(String s, Call call, Response response) {
                                        int num = JsonUtils.parseNum(s);
                                        if (num == 1) {
                                            handler.sendEmptyMessage(6);
                                            collect.setImageResource(R.mipmap.shoucang);
                                        }
                                    }
                                });
                    } else {
                        System.out.println("issave===========" + issave);
                        System.out.println("video_id====" + video_id);
                        OkGo.post(WebInterface.video_collect)
                                .tag(this)
                                .params("signid", onlyID)
                                .params("videoid", video_id)
                                .params("subject", subject0)
                                .execute(new StringCallback() {
                                    @Override
                                    public void onSuccess(String s, Call call, Response response) {
                                        int num = JsonUtils.parseNum(s);
                                        if (num == 1) {
                                            issave = true;
                                            collect.setImageResource(R.mipmap.collected);
                                        }
                                    }
                                });
                    }
                }

                break;
            case R.id.back:
                finish();
                break;
            case R.id.close:
                chronometer.setVisibility(View.GONE);
                close_timer.setVisibility(View.GONE);
                break;

        }
    }

    //分享平台回调
    private UMShareListener umShareListener = new UMShareListener() {
        @Override
        public void onResult(SHARE_MEDIA platform) {
            Log.d("plat", "platform" + platform);
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
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
//            Intent intent = new Intent();
//            intent.putExtra("play_progress", jcVideo.getCurrentPositionWhenPlaying());
//            setResult(RESULT_OK, intent);
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Sensor accelerometerSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        mSensorManager.registerListener(mSensorEventListener, accelerometerSensor, SensorManager.SENSOR_DELAY_NORMAL);
        MobclickAgent.onPageStart("VideoActivity");
        MobclickAgent.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (su.showTiming() == 1 && !orderid.equals("")) {
            new Thread() {
                @Override
                public void run() {
                    super.run();
                    if (playposition != 0) {
                        if (!result.equals("")) {
                            Log.d("VideoActivity", "playPosition===========" + playposition);
                            learnTime = total - oldposition;
                            oldposition = total;
                            alltime += learnTime;
                            Log.d("VideoActivity", "saveTime2============" + learnTime);
//                            su.saveLearnedTime(Math.abs(learnTime));
                            if (learnTime < 0) {
                                learnTime = -learnTime;
                            }
                            su.saveLearnedTime(learnTime);
                            Log.d("VideoActivity", "learnTime222==========" + learnTime);
                            OkGo.post(WebInterface.theoretical_pic_info)
                                    .tag(this)
                                    .params("signid", onlyID)
                                    .params("orderid", orderid)
                                    .params("type", "3")
//                                    .params("time", String.valueOf(Math.abs(learnTime)))
                                    .params("time", learnTime)
                                    .params("picture", result)
                                    .execute(new StringCallback() {
                                        @Override
                                        public void onSuccess(String s, Call call, Response response) {
                                            int num = JsonUtils.parseNum(s);
                                            if (num == 1) {
                                                Toast.makeText(VideoActivity.this, "上传照片成功", Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    });
                            OkGo.post(WebInterface.video_process_refress)
                                    .tag(this)
                                    .params("signid", onlyID)
                                    .params("orderid", orderid)
                                    .params("playposition", playposition)
                                    .execute(new StringCallback() {
                                        @Override
                                        public void onSuccess(String s, Call call, Response response) {
                                            Log.d("VideoActivity", "刷新视频播放进度");
                                            int num = JsonUtils.parseNum(s);
                                            if (num == 1) {
                                                Toast.makeText(VideoActivity.this, "刷新视频播放进度成功", Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    });
                            startposition = playposition;

                        } else {
                            OkGo.post(WebInterface.video_process_refress)
                                    .tag(this)
                                    .params("signid", onlyID)
                                    .params("orderid", orderid)
                                    .params("playposition", startposition)
                                    .execute(new StringCallback() {
                                        @Override
                                        public void onSuccess(String s, Call call, Response response) {
                                            Log.d("VideoActivity", "刷新视频播放进度");
                                            int num = JsonUtils.parseNum(s);
                                            if (num == 1) {
                                                Toast.makeText(VideoActivity.this, "刷新视频播放进度成功", Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    });
                        }
                    }
                }


            }.start();
            new Thread(new Runnable() {
                @Override
                public void run() {
                    if (playposition != 0 && !result.equals("")) {
                        Log.d("MyUserActionStandard", "alltime++++++++++++++++++++++-" + total);
                        OkGo.post(WebInterface.video_record_end)
                                .tag(this)
                                .params("signid", onlyID)
                                .params("orderid", orderid)
                                .params("usetime", total)
                                .execute(new StringCallback() {
                                    @Override
                                    public void onSuccess(String s, Call call, Response response) {
                                        int num = JsonUtils.parseNum(s);
                                        if (num == 1) {
                                            Toast.makeText(VideoActivity.this, "该学时结束", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });

                    }

                }
            }).start();
            new Thread(new Runnable() {
                @Override
                public void run() {
                    if (playposition != 0 && !result.equals("")) {
                        OkGo.post(WebInterface.video_process_refress)
                                .tag(this)
                                .params("signid", onlyID)
                                .params("orderid", orderid)
                                .params("playposition", playposition)
                                .execute(new StringCallback() {
                                    @Override
                                    public void onSuccess(String s, Call call, Response response) {
                                        Log.d("VideoActivity", "刷新视频播放进度");
                                        int num = JsonUtils.parseNum(s);
                                        if (num == 1) {
                                            Toast.makeText(VideoActivity.this, "刷新视频播放进度成功", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                    }

                }
            }).start();
            mSensorManager.unregisterListener(mSensorEventListener);

            MobclickAgent.onPageEnd("HomeActivity");
            MobclickAgent.onPause(this);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        startposition = playposition;

        oldposition = 0;
        total = 0;
        learnTime = 0;


        if (timer != null) {
            timer.cancel();
            timer = null;
        }
        if (timerTask != null) {
            timerTask = null;
        }

        AppIndex.AppIndexApi.end(client, getIndexApiAction());
//        JCVideoPlayer.releaseAllVideos();
        if (jcVideo.isPlaying()) {
            jcVideo.startButton.performClick();
        }
        client.disconnect();
        chronometer.stop();
        mRecordTime = SystemClock.elapsedRealtime();


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        OkGo.getInstance().cancelTag(this);
//        UMShareAPI.get(this).release();
//        videoView.stopPlayback();
        JCVideoPlayer.releaseAllVideos();
        stopTime();
        Thread.interrupted();
//        if (timer != null)
//            timer.cancel();
        if (mFrontCamera != null) {
            mFrontCamera.lock();
            mFrontCamera.stopPreview();
            mFrontCamera.release();
            mFrontCamera = null;
        }
    }

    /**
     * 停止自动减时
     */

    private void stopTime() {
        //完成拍照后关闭Activity
        if (timer != null)
            timer.cancel();
    }

    //判断视频是否收藏
    public boolean isSave(final String videoid) {
        Log.d("VideoActivity", "videoid======" + videoid);
        collect.setImageResource(R.mipmap.shoucang);
        issave = false;
        OkGo.post(WebInterface.video_list)
                .tag(this)
                .params("signid", onlyID)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        int num = JsonUtils.parseNum(s);
                        if (num == 0) {
                            issave = false;
                        } else {
                            if (JsonUtils.parseVideoCollect(s).size() != 0) {
                                ArrayList<VideoCollect> videoCollects = JsonUtils.parseVideoCollect(s);
                                for (int i = 0; i < videoCollects.size(); i++) {
                                    Log.d("VideoActivity", "videoid======11111----" + videoid);
                                    Log.d("VideoActivity", "收藏视频的id==========" + videoCollects.get(i).getVideo_id());
                                    if (videoid.equals(videoCollects.get(i).getVideo_id())) {
                                        collect.setImageResource(R.mipmap.collected);
                                        handler.sendEmptyMessage(5);
                                    }
                                }
                            }
                        }

                    }
                });
        Log.d("VideoActivity", "是否收藏=========" + issave);
        return issave;
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

    //      判断网络链接
    public boolean isConnect(Context context) {
        ConnectivityManager connectivity = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity != null) {
            NetworkInfo info = connectivity.getActiveNetworkInfo();
            if (info != null && info.isConnected()) {
                if (info.getState() == NetworkInfo.State.CONNECTED) {
                    return true;
                }
            }
        }
        Toast.makeText(context, "请检查你的网络", Toast.LENGTH_SHORT).show();
        return false;
    }

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    public Action getIndexApiAction() {
        Thing object = new Thing.Builder()
                .setName("Video Page") // TODO: Define a title for the content shown.
                // TODO: Make sure this auto-generated URL is correct.
                .setUrl(Uri.parse("http://[ENTER-YOUR-URL-HERE]"))
                .build();
        return new Action.Builder(Action.TYPE_VIEW)
                .setObject(object)
                .setActionStatus(Action.STATUS_TYPE_COMPLETED)
                .build();
    }


    @Override
    public void timeCallback(int nowTime, int progress) {
        Log.e("time", "现在时间======" + Integer.valueOf(nowTime) + "现在进度:======" + progress);
        handler.sendEmptyMessage(1);//发送消息更新时间
        playposition = nowTime / 1000;
        Log.d("VideoActivity", "playposition-----------" + playposition);
        if (jcVideo.isPlaying() && su.showTiming() == 1) {

            if (total == 3) {
//                initCamera();
                if (mFrontCamera != null) {
                    mFrontCamera.takePicture(null, null, myPicCallback);
                }
            }
            if (total % 60 == 0 && total != 0) {//每隔60秒拍一次照
                handler.sendEmptyMessage(3);
//                if ((nowTime - oldCamralTime) >= 1 * 1000 * 60) {
//                    Message msg = handler.obtainMessage();
//                    msg.arg1 = nowTime;//显示现在播放时间
//                    msg.what = 3;
//                    handler.sendMessage(msg);
//                    oldCamralTime = nowTime;
//                    Log.e("video", "entietimme" + jcVideo.getDuration());
//                }
            }
            if (total % 50 == 0 && total != 0) {
//                每隔30秒刷新一次数据
                handler.sendEmptyMessage(8);
            }


        }

    }

    class MyUserActionStandard implements JCUserActionStandard {

        @Override
        public void onEvent(int type, String url, int screen, Object... objects) {
            switch (type) {
                case JCUserAction.ON_CLICK_START_ICON:
                    //开始播放的监听
                    Log.d("MyUserActionStandard", "ON_CLICK_START_ICON-------------");
                    if (su.showTiming() == 1) {
                        oldCamralTime = 0;//重新从0开始计时
                        if (mRecordTime != 0) {
                            chronometer.setBase(chronometer.getBase() + (SystemClock.elapsedRealtime() - mRecordTime));
                        } else {
                            chronometer.setBase(SystemClock.elapsedRealtime());
                        }
                        chronometer.start();
                    }
                    break;
//                case JCUserAction.ON_CLICK_START_ERROR:
//                    Log.i("USER_EVENT", "ON_CLICK_START_ERROR" + " title is : " + (objects.length == 0 ? "" : objects[0]) + " url is : " + url + " screen is : " + screen);
//                    break;
                case JCUserAction.ON_CLICK_START_AUTO_COMPLETE:
                    Log.d("MyUserActionStandard", "ON_CLICK_START_AUTO_COMPLETE--------------");
//                    Toast.makeText(getBaseContext(), "重新播放不进行计时", Toast.LENGTH_SHORT);
//                    if (su.showTiming() == 1) {
//                        su.saveTiming(0);
//                    }
                    break;
                case JCUserAction.ON_CLICK_PAUSE://暂停
                    Log.d("MyUserActionStandard", "ON_CLICK_PAUSE--------------");
                    if (su.showTiming() == 1) {
                        chronometer.stop();
                        mRecordTime = SystemClock.elapsedRealtime();
                        Log.d("MyUserActionStandard", "currentTime=========" + currentTime);
                    }

                    break;
                case JCUserAction.ON_CLICK_RESUME:
//                    暂停后，点击开始继续播放
                    Log.d("MyUserActionStandard", "ON_CLICK_RESUME---------------");
                    if (su.showTiming() == 1) {
                        if (mRecordTime != 0) {
                            chronometer.setBase(chronometer.getBase() + (SystemClock.elapsedRealtime() - mRecordTime));
                        } else {
                            chronometer.setBase(SystemClock.elapsedRealtime());
                        }
                        chronometer.start();
                        Message message = handler.obtainMessage();
                        message.what = 1;
                        handler.sendMessage(message);
                    }
                    break;
//                case JCUserAction.ON_SEEK_POSITION:
//                    Log.i("USER_EVENT", "ON_SEEK_POSITION" + " title is : " + (objects.length == 0 ? "" : objects[0]) + " url is : " + url + " screen is : " + screen);
//                    break;
                case JCUserAction.ON_AUTO_COMPLETE:
                    Log.d("MyUserActionStandard", "ON_AUTO_COMPLETE---------------");
                    if (su.showTiming() == 1) {//计时状态，播放完成
//                        发送请求，该视频已播放完成

                        chronometer.stop();
                        mRecordTime = SystemClock.elapsedRealtime();
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                OkGo.post(WebInterface.theoretical_order_submit)
                                        .tag(this)
                                        .params("type", "3")
                                        .params("signid", onlyID)
                                        .params("orderid", orderid)
                                        .params("end_status", "1")//0:已结束  1：未结束
                                        .params("playposition", 0)
                                        .params("use_time", total)
                                        .execute(new StringCallback() {
                                            @Override
                                            public void onSuccess(String s, Call call, Response response) {
                                                int num = JsonUtils.parseNum(s);
                                                if (num == 1) {
                                                    alltime = 1;
                                                    Toast.makeText(VideoActivity.this, "该视频播放完成", Toast.LENGTH_SHORT).show();
                                                }
                                            }
                                        });
                                learnTime = total - oldposition;

                                Log.d("VideoActivity", "learnTime33333============" + Math.abs(learnTime));
                                Log.d("VideoActivity", "learnTime33333============" + learnTime);
//                                su.saveLearnedTime(Math.abs(learnTime));
                                if (learnTime < 0) {
                                    learnTime = -learnTime;
                                }
                                su.saveLearnedTime(learnTime);
                                try {
                                    OkGo.post(WebInterface.theoretical_pic_info)//上传图片信息
                                            .tag(this)
                                            .params("signid", onlyID)
                                            .params("orderid", orderid)
                                            .params("type", "3")
//                                            .params("time", String.valueOf(Math.abs(learnTime)))
                                            .params("time", learnTime)
                                            .params("picture", result)
                                            .execute(new StringCallback() {
                                                @Override
                                                public void onSuccess(String s, Call call, Response response) {
                                                    int num = JsonUtils.parseNum(s);
                                                    if (num == 1) {
                                                        Toast.makeText(VideoActivity.this, "上传照片成功1", Toast.LENGTH_SHORT).show();
                                                    } else {
                                                    }
                                                }
                                            });
                                    Log.d("VideoActivity", "time111============" + learnTime);
                                    Log.d("MyUserActionStandard", "alltime---------------------------" + total);
                                    alltime += learnTime;
                                    OkGo.post(WebInterface.video_record_end)
                                            .tag(this)
                                            .params("signid", onlyID)
                                            .params("orderid", orderid)
                                            .params("usetime", total)
                                            .execute(new StringCallback() {
                                                @Override
                                                public void onSuccess(String s, Call call, Response response) {
                                                    int num = JsonUtils.parseNum(s);
                                                    if (num == 1) {
                                                        Toast.makeText(VideoActivity.this, "该学时结束", Toast.LENGTH_SHORT).show();
                                                    }
                                                }
                                            });


                                } catch (Exception e) {
                                    Log.d("VideoActivity", "上传照片失败");
                                }


                                startposition = 0;
                                oldposition = 0;
                                total = 0;
                                learnTime = 0;

                                datas.clear();
                                num.clear();
                                initData();
                            }
                        }).start();
                        playposition = 0;
                    }
//                    }
                    break;
                case JCUserAction.ON_ENTER_FULLSCREEN:
                    Log.d("MyUserActionStandard", "ON_ENTER_FULLSCREEN--------------");

                    break;
                case JCUserAction.ON_QUIT_FULLSCREEN:
                    Log.d("MyUserActionStandard", "ON_QUIT_FULLSCREEN------------------");
                    break;
//                case JCUserAction.ON_ENTER_TINYSCREEN:
//                    Log.i("USER_EVENT", "ON_ENTER_TINYSCREEN" + " title is : " + (objects.length == 0 ? "" : objects[0]) + " url is : " + url + " screen is : " + screen);
//                    break;
//                case JCUserAction.ON_QUIT_TINYSCREEN:
//                    Log.i("USER_EVENT", "ON_QUIT_TINYSCREEN" + " title is : " + (objects.length == 0 ? "" : objects[0]) + " url is : " + url + " screen is : " + screen);
//                    break;
//                case JCUserAction.ON_TOUCH_SCREEN_SEEK_VOLUME:
//                    Log.i("USER_EVENT", "ON_TOUCH_SCREEN_SEEK_VOLUME" + " title is : " + (objects.length == 0 ? "" : objects[0]) + " url is : " + url + " screen is : " + screen);
//                    break;
//                case JCUserAction.ON_TOUCH_SCREEN_SEEK_POSITION:
//                    Log.i("USER_EVENT", "ON_TOUCH_SCREEN_SEEK_POSITION" + " title is : " + (objects.length == 0 ? "" : objects[0]) + " url is : " + url + " screen is : " + screen);
//                    break;
//                case JCUserActionStandard.ON_CLICK_START_THUMB:
//                    Log.i("USER_EVENT", "ON_CLICK_START_THUMB" + " title is : " + (objects.length == 0 ? "" : objects[0]) + " url is : " + url + " screen is : " + screen);
//                    break;
//                case JCUserActionStandard.ON_CLICK_BLANK:
//                    Log.i("USER_EVENT", "ON_CLICK_BLANK" + " title is : " + (objects.length == 0 ? "" : objects[0]) + " url is : " + url + " screen is : " + screen);
//                    break;
                default:
                    break;
            }
        }
    }

}
