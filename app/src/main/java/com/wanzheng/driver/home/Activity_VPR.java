package com.wanzheng.driver.home;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.haha.exam.R;
import com.iflytek.cloud.ErrorCode;
import com.iflytek.cloud.InitListener;
import com.iflytek.cloud.SpeakerVerifier;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechError;
import com.iflytek.cloud.SpeechEvent;
import com.iflytek.cloud.SpeechListener;
import com.iflytek.cloud.VerifierListener;
import com.iflytek.cloud.VerifierResult;
import com.wanzheng.driver.util.SystemUtil;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * 声纹密码示例
 * 
 * @author iFlytek &nbsp;&nbsp;&nbsp;<a
 *         href="http://http://www.xfyun.cn/">讯飞语音云</a>
 */
public class Activity_VPR extends Activity implements OnClickListener {
	private static final String TAG = Activity_VPR.class.getSimpleName();

	// 自由说由于效果问题，暂不开放
	// private static final int PWD_TYPE_FREE = 2;
	private static final int PWD_TYPE_NUM = 3;
	// 当前声纹密码类型，1、2、3分别为文本、自由说和数字密码
	// 声纹识别对象
	private SpeakerVerifier mVerifier;
	// 声纹AuthId，用户在云平台的身份标识，也是声纹模型的标识
	// 请使用英文字母或者字母和数字的组合，勿使用中文字符
	private String mAuthId = "";
	// 数字声纹密码
	private String mNumPwd = "";
	// 数字声纹密码段，默认有5段
	private String[] mNumPwdSegs;

	private TextView mShowPwdTextView;
	private TextView mShowMsgTextView,showvoice_change;
	private TextView mShowRegFbkTextView;
	private Toast mToast;
	private ImageView vrp_img;
	private Handler handler=new Handler(){

		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			register();
		}
		
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_vpr);

		initUi();
		// 将上个页面输入的用户名作为AuthId
		mAuthId = "wz" + new SystemUtil(this).showPhone();

		// 初始化SpeakerVerifier，InitListener为初始化完成后的回调接口
		mVerifier = SpeakerVerifier.createVerifier(Activity_VPR.this,
				new InitListener() {

					@Override
					public void onInit(int errorCode) {
						if (ErrorCode.SUCCESS == errorCode) {
							showTip("引擎初始化成功");
						} else {
							showTip("引擎初始化失败，错误码：" + errorCode);
						}
					}
				});
	}

	@SuppressLint("ShowToast")
	private void initUi() {
		findViewById(R.id.titlebar_tv).setVisibility(View.GONE);
		findViewById(R.id.titlebar_back).setVisibility(View.GONE);
		findViewById(R.id.titlebar_layout_left111).setVisibility(View.VISIBLE);
		ImageView titlebar_back = (ImageView) findViewById(R.id.titlebar_back111);
		titlebar_back.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});
		TextView tv_title = (TextView) findViewById(R.id.tv_title111);
		tv_title.setText("声纹注册");
		vrp_img=(ImageView) findViewById(R.id.vrp_img);
		vrp_img.setOnClickListener(this);
		mShowPwdTextView = (TextView) findViewById(R.id.showPwd);
		mShowMsgTextView = (TextView) findViewById(R.id.showMsg);
		mShowRegFbkTextView = (TextView) findViewById(R.id.showRegFbk);
		showvoice_change=(TextView) findViewById(R.id.showvoice_change);

		mToast = Toast.makeText(Activity_VPR.this, "", Toast.LENGTH_SHORT);
		mToast.setGravity(Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
		// 46509873-56829407-30629547-39546278-46329850
		mNumPwd = "46509873-56829407-30629547-39546278-46329850";
		mNumPwdSegs = mNumPwd.split("-");
	}

	/**
	 * 执行模型操作
	 * 
	 * @param operation
	 *            操作命令
	 * @param listener
	 *            操作结果回调对象
	 */
	private void performModelOperation(String operation, SpeechListener listener) {
		// 清空参数
		mVerifier.setParameter(SpeechConstant.PARAMS, null);
		mVerifier.setParameter(SpeechConstant.ISV_PWDT, "" + PWD_TYPE_NUM);
		
		// 设置auth_id，不能设置为空
		mVerifier.sendRequest(operation, mAuthId, listener);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
	
			//performModelOperation("que", mModelOperationListener);
		case R.id.vrp_img:
			StartV();
			performModelOperation("del", mModelOperationListener);
			
			break;
			//mVerifier.cancel();
		default:
			break;
		}
	}

	private SpeechListener mModelOperationListener = new SpeechListener() {

		@Override
		public void onEvent(int eventType, Bundle params) {
		}

		@Override
		public void onBufferReceived(byte[] buffer) {

			String result = new String(buffer);
			try {
				JSONObject object = new JSONObject(result);
				String cmd = object.getString("cmd");
				int ret = object.getInt("ret");

				if ("del".equals(cmd)) {
					if (ret == ErrorCode.SUCCESS) {
						showTip("删除成功");
					} else if (ret == ErrorCode.MSP_ERROR_FAIL) {
						showTip("删除失败，模型不存在");
					}
				} else if ("que".equals(cmd)) {
					if (ret == ErrorCode.SUCCESS) {
						showTip("模型存在");
					} else if (ret == ErrorCode.MSP_ERROR_FAIL) {
						showTip("模型不存在");
					}
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				
			}
		}

		@Override
		public void onCompleted(SpeechError error) {

			if (null != error && ErrorCode.SUCCESS != error.getErrorCode()) {
				showTip("操作失败：" + error.getPlainDescription(true));
			}
			handler.sendEmptyMessage(1);
		}
	};

	private VerifierListener mRegisterListener = new VerifierListener() {

		@Override
		public void onVolumeChanged(int volume, byte[] data) {
			//showTip("当前正在说话，音量大小：" + volume);
			if(volume==0)
				vrp_img.setImageResource(R.drawable.vpr_pro_1);
			if(volume>0&&volume<3)
				vrp_img.setImageResource(R.drawable.vpr_pro_1);
			if(volume>=3&&volume<5)
				vrp_img.setImageResource(R.drawable.vpr_pro_2);
			if(volume>=5&&volume<7)
				vrp_img.setImageResource(R.drawable.vpr_pro_3);
			if(volume>=7&&volume<9)
				vrp_img.setImageResource(R.drawable.vpr_pro_4);
			if(volume>=9&&volume<13)
				vrp_img.setImageResource(R.drawable.vpr_pro_5);
			if(volume>=13&&volume<17)
				vrp_img.setImageResource(R.drawable.vpr_pro_6);
			if(volume>=17)
				vrp_img.setImageResource(R.drawable.vpr_pro_7);
			Log.d(TAG, "返回音频数据：" + data.length);
		}

		@Override
		public void onResult(VerifierResult result) {
			((TextView) findViewById(R.id.showMsg)).setText(result.source);

			if (result.ret == ErrorCode.SUCCESS) {
				
				switch (result.err) {
				case VerifierResult.MSS_ERROR_IVP_GENERAL:
					mShowMsgTextView.setText("内核异常");
					break;
				case VerifierResult.MSS_ERROR_IVP_EXTRA_RGN_SOPPORT:
					mShowRegFbkTextView.setText("达到最大次数");
					break;
				case VerifierResult.MSS_ERROR_IVP_TRUNCATED:
					mShowRegFbkTextView.setText("出现截幅");
					break;
				case VerifierResult.MSS_ERROR_IVP_MUCH_NOISE:
					mShowRegFbkTextView.setText("太多噪音");
					break;
				case VerifierResult.MSS_ERROR_IVP_UTTER_TOO_SHORT:
					mShowRegFbkTextView.setText("验证太短");
					break;
				case VerifierResult.MSS_ERROR_IVP_TEXT_NOT_MATCH:
					mShowRegFbkTextView.setText("失败，您所读的文本不一致");
					break;
				case VerifierResult.MSS_ERROR_IVP_TOO_LOW:
					mShowRegFbkTextView.setText("音量太低");
					break;
				case VerifierResult.MSS_ERROR_IVP_NO_ENOUGH_AUDIO:
					mShowMsgTextView.setText("音频长达不到自由说的要求");
				default:
					mShowRegFbkTextView.setText("");
					break;
				}

				if (result.suc == result.rgn) {
					mShowMsgTextView.setText("验证成功");
					EndV();

				} else {
					int nowTimes = result.suc + 1;
					int leftTimes = result.rgn - nowTimes;

					mShowPwdTextView
							.setText(mNumPwdSegs[nowTimes - 1]);

					mShowMsgTextView.setText(" 第" + nowTimes + "遍，剩余"
							+ leftTimes + "遍");
				}

			} else {

				mShowMsgTextView.setText("验证失败，请重新开始。");
				EndV();
			}
		}

		// 保留方法，暂不用
		@Override
		public void onEvent(int eventType, int arg1, int arg2, Bundle arg3) {
			// 以下代码用于获取与云端的会话id，当业务出错时将会话id提供给技术支持人员，可用于查询会话日志，定位出错原因
			if (SpeechEvent.EVENT_SESSION_ID == eventType) {
			}
		}

		@Override
		public void onError(SpeechError error) {

			if (error.getErrorCode() == ErrorCode.MSP_ERROR_ALREADY_EXIST) {
				showTip("模型已存在，如需重新注册，请先删除");
			} else {
				showTip("onError Code：" + error.getPlainDescription(true));
				if(error.getErrorDescription()!=null){
					if(error.getErrorDescription().contains("网络")){
						mShowRegFbkTextView.setText("验证失败:网络异常");
					}else if(error.getErrorDescription().contains("次数")) {
						mShowRegFbkTextView.setText("验证失败:超时");
					}
				}else
					mShowRegFbkTextView.setText("验证失败,请重新注册");
			}
			EndV();
		}

		@Override
		public void onEndOfSpeech() {
			showTip("结束说话");
		}

		@Override
		public void onBeginOfSpeech() {
			showTip("开始说话");
		}
	};

	@Override
	protected void onDestroy() {
		if (null != mVerifier) {
			mVerifier.stopListening();
			mVerifier.destroy();
		}
		super.onDestroy();
	}

	private void showTip(final String str) {
		mToast.setText(str);
		//mToast.show();
	}

	private void register() {
		// 清空参数
		mVerifier.setParameter(SpeechConstant.PARAMS, null);
		mVerifier.setParameter(SpeechConstant.ISV_RGN, 3 + "");
		mVerifier.setParameter(SpeechConstant.ISV_AUDIO_PATH, Environment
				.getExternalStorageDirectory().getAbsolutePath()
				+ "/msc/test.pcm");
		// 对于某些麦克风非常灵敏的机器，如nexus、samsung i9300等，建议加上以下设置对注册进行消噪处理
		if (TextUtils.isEmpty(mNumPwd)) {
			showTip("请获取密码后进行操作");
			return;
		}
		mVerifier.setParameter(SpeechConstant.ISV_PWD, mNumPwd);
		((TextView) findViewById(R.id.showPwd)).setText(mNumPwd.substring(0, 8));
		mShowMsgTextView.setText("第" + 1 + "遍，剩余2遍");

		// 设置auth_id，不能设置为空
		mVerifier.setParameter(SpeechConstant.AUTH_ID, mAuthId);
		// 设置业务类型为注册
		mVerifier.setParameter(SpeechConstant.ISV_SST, "train");
		// 设置声纹密码类型
		mVerifier.setParameter(SpeechConstant.ISV_PWDT, "" + PWD_TYPE_NUM);
		// 开始注册
		mVerifier.startListening(mRegisterListener);
	}
	private void StartV(){
		vrp_img.setClickable(false);
		showvoice_change.setText("正在声波注册...");
		mShowRegFbkTextView.setText("");
	}
	private void EndV(){
		vrp_img.setClickable(true);
		vrp_img.setImageResource(R.drawable.vpr_pro_0);
		showvoice_change.setText("请点击话筒注册");
	}



}
