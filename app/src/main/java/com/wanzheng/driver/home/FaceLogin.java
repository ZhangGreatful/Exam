package com.wanzheng.driver.home;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ImageFormat;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.graphics.drawable.AnimationDrawable;
import android.hardware.Camera;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.facepp.error.FaceppParseException;
import com.haha.exam.R;
import com.haha.exam.activity.HomeActivity;
import com.iflytek.cloud.FaceDetector;
import com.iflytek.cloud.util.Accelerometer;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.wanzheng.driver.Entity.Mine;
import com.wanzheng.driver.network.JsonUtils;
import com.wanzheng.driver.order.Order;
import com.wanzheng.driver.util.FaceRect;
import com.wanzheng.driver.util.FaceUtil;
import com.wanzheng.driver.util.FormatUtil;
import com.wanzheng.driver.util.ParseResult;
import com.wanzheng.driver.util.SystemUtil;
import com.wanzheng.driver.util.Youtu;
import com.wanzheng.driver.util.dlduibi;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Response;

import static android.os.Process.myPid;
import static android.os.Process.myUid;

/*
 * @创建者     Administrator
 * @创建时间   2016/12/3 11:38
 * @描述	      ${TODO}
 *
 * @更新者     $Author$
 * @更新时间   $Date$
 * @更新描述   ${TODO}
 */
public class FaceLogin extends Activity {

    SystemUtil su;
    public static boolean isSuccess;
    SurfaceHolder holder1 = null;
    private int from;
    private static final int VERIFY_LOG = 99;
    private static final int VERIFY_OUT = 100;
    private static final int LOG = 44;
    private static final int OUT = 88;
    int count = 0;
    private int logStatus = 0;
    private int xueshi = 0;
    private int modle = -1;
    private Order order;
    private Mine mineCamera;
    private Youtu faceYoutu;
    public static final String APP_ID = "10008585";
    public static final String SECRET_ID = "AKIDFZrEXkormaHE93LTvFVfebUQCrLerriP";
    public static final String SECRET_KEY = "iHfv1Nk4Oex51zBQaebVMRbknkfTAmIi";
    public final static String API_YOUTU_END_POINT = "http://api.youtu.qq.com/youtu/";
    float previewRate = -1f;
    // FaceRequest对象，集成了人脸识别的各种功能
    private boolean callback = false;
    private Bitmap byteToBitmap;
    private boolean isok = false;
    private SurfaceView mPreviewSurface;
    private SurfaceView mFaceSurface;
    private TextView tv_prompt;
    private Camera mCamera;
    private int mCameraId = Camera.CameraInfo.CAMERA_FACING_FRONT;
    // Camera nv21格式预览帧的尺寸，默认设置640*480
    private int PREVIEW_WIDTH = 640;
    private int PREVIEW_HEIGHT = 480;
    // 预览帧数据存储数组和缓存数组
    private byte[] nv21;

    private byte[] buffer;
    // 缩放矩阵
    private Matrix mScaleMatrix = new Matrix();
    // 加速度感应器，用于获取手机的朝向
    private Accelerometer mAcc;
    // FaceDetector对象，集成了离线人脸识别：人脸检测、视频流检测功能
    private FaceDetector mFaceDetector;
    private boolean mStopTrack;
    private Toast mToast;
    private int isAlign = 1;
    private int number = 0;
    private LinearLayout ll_back;
    private TextView tv_title;
    private boolean isHaveXueshi = false;
    public static boolean isSend = false;
    private LoginActivity loginActivity;

    private Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 2:
                    tv_prompt.setText("张张嘴");
                    break;
                case 3:
                    tv_prompt.setText("请保持好姿势进行拍照");
                    takePicture();
                    break;
                case 666:
                    finish();
                    break;
                case VERIFY_LOG:
                    closeCamera();
                    isSend = false;
                    finish();
                    break;
                default:
                    break;
            }
        }
    };
    int id;

    private Handler handler1 = new Handler() {
        public void handleMessage(Message msg) {
            SystemUtil su = new SystemUtil(FaceLogin.this);
            switch (msg.what) {

                case 2:
                    su.saveRemember(1);

                    id = mine.getUid();
                    su.saveUid(mine.getUid());
                    su.savePhone(mine.getTelphone());
                    su.saveName(mine.getName());

                    //    su.savePwd(mine.getText().toString());
                    su.saveUserHeader(mine.getIconPath());

                    su.saveOnlyID(mine.getOnlyId());

                    su.saveCity(mine.getCityName());
                    su.saveSchool(mine.getJiaxiao());

                    if (from == 0) {
                        Intent intent = new Intent();
                        intent.setClass(FaceLogin.this, HomeActivity.class);
                        startActivity(intent);
                        loginActivity.instance.finish();
                        finish();
                    } else {
                        if (su.showTiming() == 1) {
                            su.removeLearnedTime();
                            su.saveTiming(0);
                        } else {
                            su.saveTiming(1);
                        }
                        finish();
                    }

                    break;
                case 3:
                    su.saveRemember(1);
                    id = mine.getUid();
                    su.saveUid(mine.getUid());
                    su.savePhone(mine.getTelphone());
                    su.saveName(mine.getName());

                    //    su.savePwd(mine.getText().toString());
                    su.saveUserHeader(mine.getIconPath());

                    su.saveOnlyID(mine.getOnlyId());

                    su.saveCity(mine.getCityName());
                    su.saveSchool(mine.getJiaxiao());

                    //  toast("登陆成功");

//                    intent = new Intent(FaceLogin.this,
//                            Activity_PerInfor.class);
//                    intent.putExtra("phone", m.getTelphone());
//                    startActivity(intent);
//                    finish();
                    if (from == 0) {
                        Intent intent2 = new Intent();
                        intent2.setClass(FaceLogin.this, HomeActivity.class);
                        intent2.putExtra("phone", mine.getTelphone());
                        startActivity(intent2);
                        loginActivity.instance.finish();
                        finish();
                    } else {
                        Log.d("FaceLogin", "showTiming1==========" + su.showTiming());
                        if (su.showTiming() == 1) {
                            su.saveTiming(0);
                            finish();
                        } else {
                            su.saveTiming(1);
                            finish();
                        }

                    }

                    break;
                case 4:
                    su.saveRemember(1);
                    id = mine.getUid();
                    su.saveUid(mine.getUid());
                    su.savePhone(mine.getTelphone());
                    su.saveName(mine.getName());

                    //    su.savePwd(mine.getText().toString());
                    su.saveUserHeader(mine.getIconPath());

                    su.saveOnlyID(mine.getOnlyId());

                    su.saveCity(mine.getCityName());
                    su.saveSchool(mine.getJiaxiao());
                    su.saveJiaxiaobianhao(mine.getJiaxiaobianhao());

//                    Bundle bundle1 = new Bundle();
//                    toast("登陆成功");
//                    intent = new Intent(LoginActivity.this, Activity_Pay.class);
//                    bundle1.putString("phone", m.getTelphone());
//                    bundle1.putInt("modle", 0);
//                    intent.putExtras(bundle1);
//                    startActivity(intent);
//                    finish();

                    if (from == 0) {
                        Bundle bundle1 = new Bundle();
                        Intent intent3 = new Intent();
                        intent3.setClass(FaceLogin.this, HomeActivity.class);
                        bundle1.putString("phone", mine.getTelphone());
                        bundle1.putInt("modle", 0);
                        intent3.putExtras(bundle1);
                        startActivity(intent3);
                        loginActivity.instance.finish();
                        finish();
                    } else {
                        Log.d("FaceLogin", "showTiming1==========" + su.showTiming());
                        if (su.showTiming() == 1) {
                            su.saveTiming(0);
                        } else {
                            su.saveTiming(1);
                        }
                        finish();
                    }
                    break;
                default:
                    background.stop();
                    jianyan.setVisibility(View.GONE);

                    break;


            }

        }
    };
    String phone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_facelogin);
        isSuccess = false;
        loginActivity = new LoginActivity();
        su = new SystemUtil(this);
        from = 0;
        this.getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        Intent intent = getIntent();
        from = intent.getIntExtra("from", 0);
        phone = (String) intent.getExtras().get("phone");

        faceYoutu = new Youtu(APP_ID, SECRET_ID, SECRET_KEY, API_YOUTU_END_POINT);
        init();

        isSend = false;
        nv21 = new byte[PREVIEW_WIDTH * PREVIEW_HEIGHT * 2];
        buffer = new byte[PREVIEW_WIDTH * PREVIEW_HEIGHT * 2];
        mAcc = new Accelerometer(FaceLogin.this);

    }


    private final class MyPictureCallback implements Camera.PictureCallback {
        @Override
        public void onPictureTaken(byte[] data, Camera camera) {
            if (!callback) {
                try {
                    SavePictureTask(data);
                } catch (FaceppParseException e) {
                    e.printStackTrace();
                }
                isok = true;
            }

        }
    }

    RelativeLayout jianyan;
    ImageView animation;
    AnimationDrawable background;

    private void SavePictureTask(final byte[] data) throws FaceppParseException {
        jianyan.setVisibility(View.VISIBLE);
        background.start();
        tv_prompt.setText("");
//        jianyan.setVisibility(View.GONE);
//        background.stop();

        if (!isConnect(this)) {
            Toast.makeText(getApplicationContext(), "请检查网络", Toast.LENGTH_SHORT).show();
            return;
        }
        byteToBitmap = byteToBitmap(data);
        byteToBitmap = FaceUtil.rotateImage(-90, byteToBitmap);
        byteToBitmap = FormatUtil.comp(byteToBitmap, 10);
        String bitmapStr = FormatUtil.getBitmapStrBase64(byteToBitmap);

        //        String s = "http://120.26.118.158:8082/user.ashx?do=loginbyphoto"
        //                + "&mobile=" + phone + "&facebase64=" + bitmapStr;
        String s = "http://120.26.118.158:8082/user.ashx";
        OkGo.post(s)
                .tag(this)
                .params("do", "loginbyphoto")
                .params("mobile", phone)
                .params("facebase64", bitmapStr)
                .execute(loginCallBack);


        //        if (isConnect(FaceLogin.this)) {
        //            SystemUtil su = new SystemUtil(FaceLogin.this);
        //            final String userHeader = su.getUserHeader();
        //            if (!TextUtils.isEmpty(userHeader)) {
        //                new Thread(new Runnable() {
        //                    @Override
        //                    public void run() {
        //                        try {
        //                            JSONObject respose = faceYoutu.FaceCompareUrlAndBitmap(userHeader, byteToBitmap);
        //                            final dlduibi similar = parseJsonObject(respose);
        //                            if (similar.getSimilarity() > 75) {
        //                                runOnUiThread(new Runnable() {
        //                                    @Override
        //                                    public void run() {
        //                                        Toast.makeText(getApplicationContext(), "身份核对成功!", Toast
        // .LENGTH_SHORT).show();
        //                                        Intent intent = new Intent();
        //                                        intent.setClass(FaceLogin.this, HomeActivity.class);
        //                                        startActivity(intent);
        //                                        finish();
        //                                    }
        //                                });
        //
        //                            } else if (similar.getSimilarity() == 0 && similar.getFail_flag() == 2) {
        //                                runOnUiThread(new Runnable() {
        //                                    @Override
        //                                    public void run() {
        //                                        Toast.makeText(getApplicationContext(), "未识别到人脸,请重新识别!", Toast
        // .LENGTH_SHORT)
        //                                                .show();
        //                                        if (mCamera != null) {
        //                                            mCamera.startPreview();
        //                                            isSend = false;
        //                                            isok=true;
        //                                            mStopTrack = false;
        //                                        }
        //                                        new Thread(myFaceRunnable).start();
        //                                    }
        //                                });
        //
        //                            } else {
        //                                runOnUiThread(new Runnable() {
        //                                    @Override
        //                                    public void run() {
        //                                        Toast.makeText(getApplicationContext(), "身份不符，请重新识别!" + similar
        // .getSimilarity
        //                                                (), Toast.LENGTH_SHORT).show();
        //
        //                                        if (mCamera != null) {
        //                                            mCamera.startPreview();
        //                                            isSend = false;
        //                                            isok=true;
        //                                            mStopTrack = false;
        //                                        }
        //                                        new Thread(myFaceRunnable).start();
        //                                    }
        //                                });
        //
        //                            }
        //                        } catch (Exception e) {
        //                            e.printStackTrace();
        //                        }
        //                    }
        //                }).start();
        //
        //
        //            } else {
        //                Toast.makeText(this, "请录入自己的人脸信息！", Toast.LENGTH_LONG).show();
        //                finish();
        //            }
        //        } else {
        //            Toast.makeText(this, "请检查您的网络！", Toast.LENGTH_LONG).show();
        //            finish();
        //        }


    }

    public Mine mine;
    private StringCallback loginCallBack = new StringCallback() {
        @Override
        public void onSuccess(final String s, Call call, Response response) {

            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    try {
                        JSONObject jsonObject = new JSONObject(s);
                        String result = (String) jsonObject.get("result");
                        String msg = (String) jsonObject.get("msg");
                        //   Toast.makeText(getApplicationContext(), s, Toast.LENGTH_SHORT).show();
                        if (result.contains("true")) {
                            Toast.makeText(getApplicationContext(), "身份核对成功!" + msg, Toast.LENGTH_SHORT).show();

                            mine = JsonUtils.parseMine(s);

                            Message message = new Message();
//                            JPushInterface.setAlias(FaceLogin.this, mine.getTelphone(),
//                                    new TagAliasCallback() {
//
//                                        @Override
//                                        public void gotResult(int arg0, String arg1,
//                                                              Set<String> arg2) {
//                                            // TODO Auto-generated method stub
//
//                                        }
//                                    });
                            if (mine.getIconPath() == null || "".equals(mine.getIconPath())
                                    || mine.getName() == null || "".equals(mine.getName())) {
                                message.what = 3;

                                handler1.sendMessage(message);

                            } else {
                                new SystemUtil(FaceLogin.this).saveRegesterState(1);
                                String url = "http://120.26.118.158:8082/pay.ashx?do=getPayState&mobile="
                                        + mine.getTelphone();
                                OkGo.post(url)
                                        .tag(this)
                                        .execute(payStateCallBack);
                            }

//                            Intent intent = new Intent();
//                            intent.setClass(FaceLogin.this, HomeActivity.class);
//                            startActivity(intent);
//                             finish();
                        } else {

                            if (msg.contains("相似率较低")) {
                                Toast.makeText(getApplicationContext(), "相似率较低，请重试!", Toast.LENGTH_SHORT).show();
                                background.stop();
                                tv_prompt.setText("请调整脸部正对框内");
                                jianyan.setVisibility(View.GONE);
                                if (mCamera != null) {
                                    mCamera.startPreview();
                                    isSend = false;
                                    isok = true;
                                    mStopTrack = false;
                                }
                                new Thread(myFaceRunnable).start();

                            } else {
                                if (from == 1) {
                                    Toast.makeText(getApplicationContext(), "未注册脸部信息,", Toast.LENGTH_LONG).show();
                                    background.stop();
                                    tv_prompt.setText("请调整脸部正对框内");
                                    jianyan.setVisibility(View.GONE);
                                    if (mCamera != null) {
                                        mCamera.startPreview();
                                        isSend = false;
                                        isok = true;
                                        mStopTrack = false;
                                    }
                                    new Thread(myFaceRunnable).start();
                                } else {
                                    Toast.makeText(getApplicationContext(), "未注册脸部信息", Toast.LENGTH_SHORT).show();
                                    background.stop();
                                    jianyan.setVisibility(View.GONE);
                                    finish();
                                }

                                //  Toast.makeText(getApplicationContext(), msg+"!", Toast.LENGTH_SHORT).show();

//                                background.stop();
//                                jianyan.setVisibility(View.GONE);
//                                finish();
                            }
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });


        }

    };

    public StringCallback payStateCallBack = new StringCallback() {
        @Override
        public void onSuccess(String s, Call call, Response response) {
            int msg = JsonUtils.parsePaystate(s);
            if (msg == 1) {

//                su = new SystemUtil(FaceLogin.this);
                su.saveZhifuR(1);
                Message message = new Message();
                message.what = 2;
                handler1.sendMessage(message);
            } else if (msg == 0) {
                Message message = new Message();
                message.what = 4;
                handler1.sendMessage(message);
            } else if (msg == 2) {

            } else {

            }
        }
    };

    private void init() {
        mPreviewSurface = (SurfaceView) findViewById(R.id.sfv_preview);
        tv_prompt = (TextView) findViewById(R.id.tv_prompt);
        tv_title = (TextView) findViewById(R.id.tv_title);
        ll_back = (LinearLayout) findViewById(R.id.ll_back);
        jianyan = (RelativeLayout) findViewById(R.id.jianyan);
        animation = (ImageView) findViewById(R.id.im_dongqilai);
        background = (AnimationDrawable) animation.getBackground();
        tv_title.setText("人脸识别");
        tv_prompt.setText("请调整脸部正对框内");
        mPreviewSurface.getHolder().addCallback(mPreviewCallback);
        mPreviewSurface.getHolder().setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        mFaceSurface = (SurfaceView) findViewById(R.id.sfv_face);
        mFaceSurface.setZOrderOnTop(true);
        mFaceSurface.getHolder().setFormat(PixelFormat.TRANSLUCENT);

        ll_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                closeCamera();
                // FaceUtil.count = 0;
                FaceLogin.this.finish();
            }
        });
        setSurfaceSize();
        mToast = Toast.makeText(FaceLogin.this, "", Toast.LENGTH_SHORT);
    }


    private SurfaceHolder.Callback mPreviewCallback = new SurfaceHolder.Callback() {

        @Override
        public void surfaceDestroyed(SurfaceHolder holder) {
            closeCamera();
        }

        @Override
        public void surfaceCreated(SurfaceHolder holder) {
            openCamera();
        }

        @Override
        public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
            mScaleMatrix.setScale(width / (float) PREVIEW_HEIGHT, height / (float) PREVIEW_WIDTH);
        }
    };

    private void setSurfaceSize() {
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);

        int width = metrics.widthPixels;
        int height = metrics.heightPixels;
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(width, height);
        params.addRule(RelativeLayout.ALIGN_PARENT_TOP);

        mPreviewSurface.setLayoutParams(params);
        mFaceSurface.setLayoutParams(params);
    }

    private void openCamera() {
        if (null != mCamera) {
            return;
        }

        if (!checkCameraPermission()) {
            showTip("摄像头权限未打开，请打开后再试");
            mStopTrack = true;
            return;
        }

        // 只有一个摄相头，打开后置
        if (Camera.getNumberOfCameras() == 1) {
            mCameraId = Camera.CameraInfo.CAMERA_FACING_BACK;
        }
        mCamera = Camera.open(Camera.CameraInfo.CAMERA_FACING_FRONT);
        Camera.Parameters params = mCamera.getParameters();
        params.setPreviewFormat(ImageFormat.NV21);
        params.setPreviewSize(PREVIEW_WIDTH, PREVIEW_HEIGHT);
        mCamera.setParameters(params);

        // 设置显示的偏转角度，大部分机器是顺时针90度，某些机器需要按情况设置
        mCamera.setDisplayOrientation(90);
        mCamera.setPreviewCallback(new Camera.PreviewCallback() {

            @Override
            public void onPreviewFrame(byte[] data, Camera camera) {
                System.arraycopy(data, 0, nv21, 0, data.length);
            }
        });

        try {
            mCamera.setPreviewDisplay(mPreviewSurface.getHolder());
            mCamera.startPreview();
            isok = true;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void closeCamera() {
        if (null != mCamera) {
            mCamera.setPreviewCallback(null);
            mCamera.stopPreview();
            mCamera.release();
            mCamera = null;
        }
    }


    private void takePicture() {
        if (mCamera != null) {
            if (isok) {
                try {
                    tv_prompt.setText("准备拍照，请保持好姿势");
//                    handler.postDelayed(new Runnable() {
//                        @Override
//                        public void run() {
//                            mStopTrack = true;
//                            number += 1;
//                            // tv_prompt.setText("");
//                            mCamera.takePicture(null, null, new MyPictureCallback());
//                            isok = false;
//                        }
//                    }, 2000);
                    handler.postDelayed(takePicture, 2000);

                } catch (Exception e) {

                }
            }
        }
    }

    Runnable takePicture = new Runnable() {
        @Override
        public void run() {
            mStopTrack = true;
            number += 1;
            // tv_prompt.setText("");
            mCamera.takePicture(null, null, new MyPictureCallback());
            isok = false;
        }
    };

    private boolean checkCameraPermission() {
        int status = checkPermission(Manifest.permission.CAMERA, myPid(), myUid());
        if (PackageManager.PERMISSION_GRANTED == status) {
            return true;
        }

        return false;
    }

    class myRunnable implements Runnable {
        @Override
        public void run() {
            mFaceDetector = FaceDetector.createDetector(FaceLogin.this, null);
            if (null != mAcc) {
                mAcc.start();
            }
            mStopTrack = false;
            while (!mStopTrack) {
                if (mFaceDetector == null) {
                    /**
                     * 离线视频流检测功能需要单独下载支持离线人脸的SDK 请开发者前往语音云官网下载对应SDK
                     */
                    showTip("本SDK不支持离线视频流检测");
                    break;
                }
                if (null == nv21) {
                    continue;
                }

                synchronized (nv21) {
                    System.arraycopy(nv21, 0, buffer, 0, nv21.length);
                }

                // 获取手机朝向，返回值0,1,2,3分别表示0,90,180和270度
                int direction = Accelerometer.getDirection();
                boolean frontCamera = (Camera.CameraInfo.CAMERA_FACING_FRONT == mCameraId);
                // 前置摄像头预览显示的是镜像，需要将手机朝向换算成摄相头视角下的朝向。
                // 转换公式：a' = (360 - a)%360，a为人眼视角下的朝向（单位：角度）
                if (frontCamera) {
                    // SDK中使用0,1,2,3,4分别表示0,90,180,270和360度
                    direction = (4 - direction) % 4;
                }


                String result = mFaceDetector.trackNV21(buffer, PREVIEW_WIDTH, PREVIEW_HEIGHT, isAlign, direction);
                FaceRect[] faces = ParseResult.parseResult(result);
                Canvas canvas = mFaceSurface.getHolder().lockCanvas();
                if (null == canvas) {
                    continue;
                }
                canvas.drawColor(0, PorterDuff.Mode.CLEAR);
                canvas.setMatrix(mScaleMatrix);

                if (faces.length <= 0) {
                    mFaceSurface.getHolder().unlockCanvasAndPost(canvas);
                    continue;
                }

                if (null != faces && frontCamera == (Camera.CameraInfo.CAMERA_FACING_FRONT == mCameraId)) {
                    for (FaceRect face : faces) {
                        face.bound = FaceUtil.RotateDeg90(face.bound, PREVIEW_WIDTH, PREVIEW_HEIGHT);
                        if (face.point != null) {
                            for (int i = 0; i < face.point.length; i++) {
                                face.point[i] = FaceUtil.RotateDeg90(face.point[i], PREVIEW_WIDTH, PREVIEW_HEIGHT);
                            }
                        }
                        drawFaceRect(FaceLogin.this, canvas, face, PREVIEW_WIDTH, PREVIEW_HEIGHT,
                                frontCamera, false);
                    }
                } else {
                    tv_prompt.setText("请将脸部对准框内");
                }

                mFaceSurface.getHolder().unlockCanvasAndPost(canvas);
            }

        }
    }

    myRunnable myFaceRunnable;

    @Override
    protected void onResume() {
        super.onResume();
        if (myFaceRunnable == null) {
            myFaceRunnable = new myRunnable();
        }

        new Thread(myFaceRunnable).start();

    }

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

    protected dlduibi parseJsonObject(JSONObject respose) {
        dlduibi similar = new dlduibi();
        if (null != respose.optString("session_id")) {
            similar.setSession_id(respose.optString("session_id"));
        }
        if (null != respose.optString("similarity")) {
            similar.setSimilarity(respose.optInt("similarity"));
        }
        if (null != respose.optString("fail_flag")) {
            similar.setFail_flag(respose.optInt("fail_flag"));
        }
        if (null != respose.optString("errorcode")) {
            similar.setErrorcode(respose.optInt("errorcode"));
        }
        if (null != respose.optString("errormsg")) {
            similar.setErrormsg(respose.optString("errormsg"));
        }
        return similar;

    }

    @Override
    protected void onPause() {
        super.onPause();
        // closeCamera();
        if (null != mAcc) {
            mAcc.stop();
        }
        mStopTrack = true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // 销毁对象
        closeCamera();
        callback = true;
        mFaceDetector.destroy();
        handler.removeCallbacks(takePicture);
        OkGo.getInstance().cancelTag(this);
    }

    private void showTip(final String str) {
        mToast.setText(str);
        mToast.show();
    }


//    public boolean onKeyDown(int keyCode, KeyEvent event) {// 捕捉返回键
//        if (keyCode == KeyEvent.KEYCODE_BACK) {
//            su.saveTiming(0);
//            return false;
//        }
//        return super.onKeyDown(keyCode, event);
//    }


    /**
     * 在指定画布上将人脸框出来
     *
     * @param canvas      给定的画布
     * @param face        需要绘制的人脸信息
     * @param width       原图宽
     * @param height      原图高
     * @param frontCamera 是否为前置摄像头，如为前置摄像头需左右对称
     * @param DrawOriRect 可绘制原始框，也可以只画四个角
     */
    //	public static int count = 0;
    public void drawFaceRect(Context context, Canvas canvas, FaceRect face, int width, int height, boolean
            frontCamera, boolean DrawOriRect) {
        if (canvas == null) {
            return;
        }

        Paint paint = new Paint();
        paint.setColor(Color.rgb(14, 171, 235));
        int len = (face.bound.bottom - face.bound.top) / 8;
        if (len / 8 >= 2)
            paint.setStrokeWidth(len / 8);
        else
            paint.setStrokeWidth(2);

        Rect rect = face.bound;

        if (frontCamera) {
            int top = rect.top;
            rect.top = width - rect.bottom;
            rect.bottom = width - top;
        }
        if (DrawOriRect) {
            paint.setStyle(Paint.Style.STROKE);
            canvas.drawRect(rect, paint);
        } else {
            int drawl = rect.left - len;
            int drawr = rect.right + len;
            int drawu = rect.top - len;
            int drawd = rect.bottom + len;
            canvas.drawLine(drawl, drawd, drawl, drawd - len, paint);
            canvas.drawLine(drawl, drawd, drawl + len, drawd, paint);
            canvas.drawLine(drawr, drawd, drawr, drawd - len, paint);
            canvas.drawLine(drawr, drawd, drawr - len, drawd, paint);
            canvas.drawLine(drawl, drawu, drawl, drawu + len, paint);
            canvas.drawLine(drawl, drawu, drawl + len, drawu, paint);
            canvas.drawLine(drawr, drawu, drawr, drawu + len, paint);
            canvas.drawLine(drawr, drawu, drawr - len, drawu, paint);
            String text = tv_prompt.getText().toString().trim();
            if (drawl < 50 || drawl > 220 || drawu < 100 || drawu > 320) {
                System.out.println("cuole  cuole  cuole  cuole  cuole");
                if (!text.equals("请调整脸部正对框内")) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            tv_prompt.setText("请调整脸部正对框内");
                            //    TimerApplication.read("请调整脸部正对框内");
                        }
                    });
                }
            } else {
                if (!isSend) {
                    mStopTrack = true;
                    mFaceDetector.destroy();
                    handler.sendEmptyMessage(3);
                    isSend = true;
                }
            }
        }
    }

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
        return false;
    }

}