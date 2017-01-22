package com.wanzheng.driver.home;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.Result;
import com.haha.exam.R;
import com.wanzheng.driver.Entity.BaseResult;
import com.wanzheng.driver.network.NetInterface;
import com.wanzheng.driver.network.NetWorkCallBack;
import com.wanzheng.driver.network.NetWorkUtils;
import com.wanzheng.driver.util.CameraManager;
import com.wanzheng.driver.util.CaptureActivityHandler;
import com.wanzheng.driver.util.DecodeThread;
import com.wanzheng.driver.util.SystemUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.reflect.Field;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public final class Activity_QRcode extends Activity implements
		SurfaceHolder.Callback {

	private static final String TAG = Activity_QRcode.class.getSimpleName();

	private CameraManager cameraManager;
	private CaptureActivityHandler handler;
	long millionSeconds;
	private SurfaceView scanPreview = null;
	private RelativeLayout scanContainer;
	private RelativeLayout scanCropView;
	private ImageView scanLine;
	private ImageView mFlash;
	private String phone;
	private SystemUtil sys;
	private int uid;

	private Rect mCropRect = null;

	public Handler getHandler() {
		return handler;
	}

	public CameraManager getCameraManager() {
		return cameraManager;
	}

	private boolean isHasSurface = false;

	@Override
	public void onCreate(Bundle icicle) {
		super.onCreate(icicle);

		Window window = getWindow();
		window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		setContentView(R.layout.activity_qr_scan);
		
		initView();
		initData();
		
	}

	private void initData() {
		sys=new SystemUtil(Activity_QRcode.this);
		phone=sys.showPhone();
		uid=sys.showUid();
	}

	private void initView() {
		TextView titlebar = (TextView) findViewById(R.id.titlebar_tv);
		titlebar.setText("二维码");
		ImageView back = (ImageView) findViewById(R.id.titlebar_back);

		// 返回按钮
		back.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});

		scanPreview = (SurfaceView) findViewById(R.id.capture_preview);
		scanContainer = (RelativeLayout) findViewById(R.id.capture_container);
		scanCropView = (RelativeLayout) findViewById(R.id.capture_crop_view);
		scanLine = (ImageView) findViewById(R.id.capture_scan_line);
		TranslateAnimation animation = new TranslateAnimation(
				Animation.RELATIVE_TO_PARENT, 0.0f,
				Animation.RELATIVE_TO_PARENT, 0.0f,
				Animation.RELATIVE_TO_PARENT, 0.0f,
				Animation.RELATIVE_TO_PARENT, 0.9f);
		animation.setDuration(4500);
		animation.setRepeatCount(-1);
		animation.setRepeatMode(Animation.RESTART);
		scanLine.startAnimation(animation);
		
	}

	@Override
	protected void onResume() {
		super.onResume();
		cameraManager = new CameraManager(getApplication());
		handler = null;
		if (isHasSurface) {
			initCamera(scanPreview.getHolder());
		} else {
			scanPreview.getHolder().addCallback(this);
		}
	}

	@Override
	protected void onPause() {
		if (handler != null) {
			handler.quitSynchronously();
			handler = null;
		}
		cameraManager.closeDriver();
		if (!isHasSurface) {
			scanPreview.getHolder().removeCallback(this);
		}
		super.onPause();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		if (!isHasSurface) {
			isHasSurface = true;
			initCamera(holder);
		}
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		isHasSurface = false;
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {

	}

	// -------------------------------------------------------------------------------------二维码扫描结果
	public void handleDecode(final Result rawResult, Bundle bundle) {
		// result为结果
		String result = rawResult.getText();
		// 测试
		// String ceshi
		// ="@wanzheng,5422bbe5-286c-4bcd-a78f-3e12ad91a762，2016-10-19
		// 16:09:35";
		final String[] split = result.split(",");
		// Toast.makeText(getApplicationContext(), result, 0).show();
		if (!split[0].equals("@wanzheng")) {
			Toast.makeText(getApplicationContext(), "二维码不符合规范！请重新扫描。", Toast.LENGTH_SHORT)
					.show();
			// 重新开始扫描
			restartPreviewAfterDelay(1500);
			return;
		}
		if (split.length == 5) {
			boolean timeOut = isTimeOut(split[2]);
			// true 为超时 ；false，为没超时
			if (timeOut) {
				// 超时处理
				Toast.makeText(getApplicationContext(), "超时了", Toast.LENGTH_SHORT).show();
				restartPreviewAfterDelay(1500);
				return;
			} else {
				// 没超时处理
				// Toast.makeText(getApplicationContext(), "沒超时", 0).show();
				// -------------------------------------此处为开始计时发起推送
				new NetWorkUtils().getInstance().work(
						new NetInterface().getInstance().tuiSong(
								"scnningLogin2",uid+"", split[3],phone,
								split[4], split[1]), tuisongCallBack);

//                new NetWorkUtils().getInstance().work(new NetInterface().getInstance().tuiSongSms("scnningLogin2",
//                        uid + "", split[3], phone, split[4], split[1]), tuisongCallBack);
			}
		} else if (split.length == 6) {
			boolean timeOut = isTimeOut(split[2]);
			if (timeOut) {
				Toast.makeText(getApplicationContext(), "超时了", Toast.LENGTH_SHORT).show();
				// 超时处理
				// // 重新开始扫描
				restartPreviewAfterDelay(1500);
				return;
			} else {
				boolean isthisPeople = isThisPeople(split[5]);
				if (isthisPeople) {
					// -------------------------------------此处为结束计时时发起推送
					new NetWorkUtils().getInstance().work(
							new NetInterface().getInstance().tuiSong(
									"scnningLogout",uid+"", split[3], split[5],
									split[4], split[1]), tuisongCallBack);
				} else {
					// 不是本人处理
					Toast.makeText(getApplicationContext(), "不是本人处理", Toast.LENGTH_SHORT).show();
					// 重新开始扫描
					restartPreviewAfterDelay(1500);
				}
			}
		}
	}

	private NetWorkCallBack<BaseResult> tuisongCallBack = new NetWorkCallBack<BaseResult>() {
		@Override
		public void onComplete(String json) {
			// Toast.makeText(getApplicationContext(),
			// json, 1).show();

			try {
				JSONObject jsonObject = new JSONObject(json);
				int scnningResult = jsonObject.optInt("scnningResult");
				String xmobile = jsonObject.optString("xmobile", "");

				if (scnningResult == 4) {
					Toast.makeText(getApplicationContext(), "学员和教练不属于同一所驾校",  Toast.LENGTH_SHORT).show();
					finish();
				}else if(scnningResult == 5){
					Toast.makeText(getApplicationContext(), "您有未支付的订单，请先完成支付！",  Toast.LENGTH_SHORT).show();
					Intent intent2 = new Intent(Activity_QRcode.this, Activity_OrderShow.class);
					startActivity(intent2);
					finish();
				}else if (!TextUtils.isEmpty(scnningResult + "") && !TextUtils.isEmpty(xmobile) ) {
					// 计时开始前的逻辑
					if (scnningResult == 1) {
						Toast.makeText(getApplicationContext(), "扫描成功，请开始训练！", Toast.LENGTH_LONG).show();
						finish();
						// ---------------------------------可在此处判断是否进入
						// 支付界面（本次默认进入）------------------------------------------------------------------------------
						// Intent intent = new Intent(CaptureActivity.this,
						// Activity_Pay.class);
						// startActivity(intent);
						// finish();
						// ---------------------------------可在此处判断是否进入
						// 支付界面（本次默认进入）------------------------------------------------------------------------------
					} else if (scnningResult == 0) {
						Toast.makeText(getApplicationContext(), "信息核对失败，请重新扫描！", Toast.LENGTH_SHORT).show();
						restartPreviewAfterDelay(1500);
					} else if (scnningResult == 3) {
						Toast.makeText(getApplicationContext(), "你正在其他教练学车计时",  Toast.LENGTH_SHORT).show();
						finish();
					}

				} else if (!TextUtils.isEmpty(scnningResult + "") && TextUtils.isEmpty(xmobile)) {
					// // 计时结束前的逻辑
					if (scnningResult == 1) {
						Toast.makeText(getApplicationContext(), "信息核对成功，计时结束！",  Toast.LENGTH_SHORT).show();
						finish();
					} else if (scnningResult == 0) {
						Toast.makeText(getApplicationContext(), "信息核对失败，请重新扫描！",  Toast.LENGTH_SHORT).show();
						restartPreviewAfterDelay(1500);
					} else if (scnningResult == 3) {
						Toast.makeText(getApplicationContext(), "你正在其他教练学车计时",  Toast.LENGTH_SHORT).show();
						finish();
					}

				}

			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
	};
	private boolean isThisPeople(String string) {
		if (phone.equals(string)) {
			Toast.makeText(getApplicationContext(), "是本人", Toast.LENGTH_SHORT).show();
			return true;
		} else {
			Toast.makeText(getApplicationContext(), "不是本人", Toast.LENGTH_SHORT).show();
			return false;
		}
	}

	public boolean isTimeOut(String time) {
		long currentTimeMillis = System.currentTimeMillis();
		// String str = "2016-10-19 16:09:35";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		try {
			millionSeconds = sdf.parse(time).getTime();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} // 毫秒
		if ((currentTimeMillis-WelcomeActivity.shiJian-60000 - millionSeconds) > 0) {

			return true;
		} else {

			return false;
		}
	}

	private void handleText(String url) {

		Toast.makeText(getApplicationContext(), url, Toast.LENGTH_SHORT).show();
	}

	private void initCamera(SurfaceHolder surfaceHolder) {
		if (surfaceHolder == null) {
			throw new IllegalStateException("No SurfaceHolder provided");
		}
		if (cameraManager.isOpen()) {
			Log.w(TAG,
					"initCamera() while already open -- late SurfaceView callback?");
			return;
		}
		try {
			cameraManager.openDriver(surfaceHolder);
			// Creating the handler starts the preview, which can also throw a
			// RuntimeException.
			if (handler == null) {
				handler = new CaptureActivityHandler(this, cameraManager,
						DecodeThread.ALL_MODE);
			}

			initCrop();
		} catch (IOException ioe) {
			Log.w(TAG, ioe);
			displayFrameworkBugMessageAndExit();
		} catch (RuntimeException e) {
			// Barcode Scanner has seen crashes in the wild of this variety:
			// java.?lang.?RuntimeException: Fail to connect to camera service
			Log.w(TAG, "Unexpected error initializing camera", e);
			displayFrameworkBugMessageAndExit();
		}
	}

	private void displayFrameworkBugMessageAndExit() {
		// camera error
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle(getString(R.string.app_name));
		builder.setMessage("相机打开出错，请稍后重试");
		builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				finish();
			}

		});
		builder.setOnCancelListener(new DialogInterface.OnCancelListener() {

			@Override
			public void onCancel(DialogInterface dialog) {
				finish();
			}
		});
		builder.show();
	}

	public void restartPreviewAfterDelay(long delayMS) {
		if (handler != null) {
			handler.sendEmptyMessageDelayed(R.id.restart_preview, delayMS);
		}
	}

	public Rect getCropRect() {
		return mCropRect;
	}

	/**
	 * 初始化截取的矩形区域
	 */
	private void initCrop() {
		int cameraWidth = cameraManager.getCameraResolution().y;
		int cameraHeight = cameraManager.getCameraResolution().x;

		int[] location = new int[2];
		scanCropView.getLocationInWindow(location);

		int cropLeft = location[0];
		int cropTop = location[1] - getStatusBarHeight();

		int cropWidth = scanCropView.getWidth();
		int cropHeight = scanCropView.getHeight();

		int containerWidth = scanContainer.getWidth();
		int containerHeight = scanContainer.getHeight();

		int x = cropLeft * cameraWidth / containerWidth;
		int y = cropTop * cameraHeight / containerHeight;

		int width = cropWidth * cameraWidth / containerWidth;
		int height = cropHeight * cameraHeight / containerHeight;

		mCropRect = new Rect(x, y, width + x, height + y);
	}

	private int getStatusBarHeight() {
		try {
			Class<?> c = Class.forName("com.android.internal.R$dimen");
			Object obj = c.newInstance();
			Field field = c.getField("status_bar_height");
			int x = Integer.parseInt(field.get(obj).toString());
			return getResources().getDimensionPixelSize(x);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}

	private Handler myhandler = new Handler() {
		public void handleMessage(android.os.Message msg) {

		};
	};
}
