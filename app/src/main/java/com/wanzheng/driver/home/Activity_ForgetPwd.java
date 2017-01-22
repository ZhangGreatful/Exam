package com.wanzheng.driver.home;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.AudioManager;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.telephony.TelephonyManager;
import android.text.Editable;
import android.text.InputType;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextWatcher;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Timer;

import com.haha.exam.R;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.wanzheng.driver.Entity.BaseResult;
import com.wanzheng.driver.Entity.phoMsgpwd;
import com.wanzheng.driver.network.JsonUtils;
import com.wanzheng.driver.network.NetInterface;
import com.wanzheng.driver.network.NetWorkCallBack;
import com.wanzheng.driver.network.NetWorkUtils;
import com.wanzheng.driver.util.SystemUtil;
import com.wanzheng.driver.Entity.Mine;

import okhttp3.Call;
import okhttp3.Response;

public class Activity_ForgetPwd extends Activity implements OnClickListener {
	private EditText userNameEdt, passwordEdt, phopwdEdt;
	private Button submintBtn, zhuceBtn;

	private boolean hasCheck = true;
	/**
	 * 倒计时用计数
	 */
	private int time = 0;
	private ImageView titlebar_layout_left, eye_key;
	private Timer timer;
	private CharSequence s;
	private String zhucePhone;
	private phoMsgpwd phoMsg = new phoMsgpwd();
	/**
	 * 按钮上的文字
	 */
	private String btnMsg = "";
	private TextView titleTxt, titleTxtRight;

	private int clickCheck = 0;// 验证码版本为1 登陆版本为0

	private int login = 0;
	private int eye = 0;

	protected static final int UI_LOG_TO_VIEW = 0;

	private TextView loginQuestionTxt;
	private Mine m;

	private Handler handler = new Handler() {
		@SuppressLint({ "ResourceAsColor", "NewApi" })
		public void handleMessage(Message msg) {

			switch (msg.what) {

			case 1:
				// 按钮重新可以点击
				// checkBtn.setEnabled(true);
				// 设置为原有的text
				// checkBtn.setText(btnMsg);
				// timer 取消执行
				timer.cancel();
				break;
			case 2:
				toast("修改成功");
				finish();
				break;
			case 4:
				zhuceBtn.setEnabled(true);
				submintBtn.setEnabled(true);
				break;
			case 5:
				if (time != 0 && time > 0) {
					submintBtn.setBackgroundColor(getResources().getColor(R.color.gray));
					submintBtn.setText(time + " " + "S");
					getTime();
				} else {
					submintBtn.setBackground(getResources().getDrawable(
							R.drawable.login_new_pic));
					userNameEdt.setEnabled(true);
					submintBtn.setEnabled(true);
					submintBtn.setText("验证");
				}
				break;
			default:
				break;
			}

		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity__forgetpwd);
		initData();
		initView();
	}

	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
	}

	private void initView() {
		// TODO Auto-generated method stub
		RelativeLayout titlebar = (RelativeLayout) findViewById(R.id.titlebar);
		titleTxt = (TextView) titlebar.findViewById(R.id.titlebar_tv);
		titleTxt.setText("修改密码");
		titlebar_layout_left = (ImageView) titlebar
				.findViewById(R.id.titlebar_back);
		titlebar_layout_left.setOnClickListener(this);
		titleTxtRight = (TextView) titlebar
				.findViewById(R.id.titlebar_right_text);
		titleTxtRight.setText("登录");
		titleTxtRight.setVisibility(View.VISIBLE);
		titleTxtRight.setOnClickListener(this);
		userNameEdt = (EditText) findViewById(R.id.forget_username);
		userNameEdt.setInputType(EditorInfo.TYPE_CLASS_PHONE);
		// userNameEdt.addTextChangedListener(new TextWatcher() {
		//
		// @Override
		// public void onTextChanged(CharSequence arg0, int arg1, int arg2, int
		// arg3) {
		// // TODO Auto-generated method stub
		// s=arg0;
		// }
		//
		// @Override
		// public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
		// int arg3) {
		// // TODO Auto-generated method stub
		//
		// }
		//
		// @Override
		// public void afterTextChanged(Editable arg0) {
		// int editStart = userNameEdt.getSelectionStart();
		// int editEnd = userNameEdt.getSelectionEnd();
		// if (s.length() <11) {
		//
		// }else{
		// new NetWorkUtils().getInstance().work(
		// new NetInterface().getInstance()
		// .isexists(s.toString()),
		// chekPhoneCallBack);
		// }
		//
		// }
		// });
		passwordEdt = (EditText) findViewById(R.id.forget_password);
		submintBtn = (Button) findViewById(R.id.forget_pho_psw);
		submintBtn.setOnClickListener(this);
		zhuceBtn = (Button) findViewById(R.id.forget);
		zhuceBtn.setOnClickListener(this);
		phopwdEdt = (EditText) findViewById(R.id.forget_phonepassword);
		eye_key = (ImageView) findViewById(R.id.forget_eye_key);
		eye_key.setOnClickListener(this);

	}

	private void initData() {
		// TODO Auto-generated method stub
		Intent intent = getIntent();
		// login = intent.getIntExtra(Keys.Login, 0);
	}

	String imei;

	@SuppressLint("NewApi")
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.forget:
			String mobile1 = userNameEdt.getText().toString();
			if ("".equals(mobile1)) {
				Toast.makeText(Activity_ForgetPwd.this, "请输入正确的手机号码",
						Toast.LENGTH_LONG).show();
				return;
			}
			String passwordString2 = passwordEdt.getText().toString();
			if ("".equals(passwordString2)) {
				toast("密码不能为空");
				return;
			}
			String phoPwd = phopwdEdt.getText().toString();
			if ("".equals(passwordString2)) {
				toast("请输入验证码");
				return;
			}
			if (!phopwdEdt.getText().toString().equals(phoMsg.getCode())
					|| !mobile1.equals(zhucePhone)) {
				toast("输入验证码有误");
				return;
			}

//			new NetWorkUtils().getInstance().work(
//					new NetInterface().getInstance().forgetpwd(mobile1,
//							passwordString2), registerCallBack);
			String s = "http://120.26.118.158:8082/user.ashx?do=forgetPassword&mobile="
					+ mobile1 + "&newpass=" + passwordString2;
			OkGo.post(s)
					.tag(this)
					.execute(registerCallBack);
			// Bundle bundle = new Bundle();
			// bundle.putString("phone", "18364553891");
			// Intent intent=new Intent(Activity_Zhuce.this,
			// Activity_PerInfor.class);
			// intent.putExtra("phone", userNameEdt.getText().toString());
			// startActivity(intent);
			break;
		case R.id.forget_pho_psw:
			zhucePhone = userNameEdt.getText().toString();
			String mobile2 = userNameEdt.getText().toString();
			if ("".equals(mobile2)) {
				Toast.makeText(Activity_ForgetPwd.this, "请输入正确的手机号码",
						Toast.LENGTH_LONG).show();
				return;
			} else {
				userNameEdt.setEnabled(false);
				submintBtn.setEnabled(false);
				handler.sendEmptyMessage(5);
				time = 60;

//
//				new NetWorkUtils().getInstance().work(
//						new NetInterface().getInstance().getPhonepwd(mobile2),
//						getPhomsgCallBack);
				String s1 = "http://120.26.118.158:8082/push.ashx?do=pushmessage&mobile="
						+ mobile2;
				OkGo.post(s1)
						.tag(this)
						.execute(getPhoneCallBack);

			}
			break;
		case R.id.titlebar_back:
			finish();
			break;
		case R.id.titlebar_right_text:
			finish();
			break;
		case R.id.forget_eye_key:
			if (eye == 0) {
				passwordEdt
						.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
				eye = 1;
				eye_key.setImageDrawable(getResources().getDrawable(
						R.drawable.eye_yes));
			} else {
				passwordEdt.setInputType(InputType.TYPE_CLASS_TEXT
						| InputType.TYPE_TEXT_VARIATION_PASSWORD);
				eye = 0;
				eye_key.setImageDrawable(getResources().getDrawable(
						R.drawable.eye_no));
			}
			break;
		default:
			break;
		}
	}
	private StringCallback registerCallBack=new StringCallback() {
		@Override
		public void onSuccess(String s, Call call, Response response) {
			int msg = JsonUtils.parseisok(s);
			if (msg == 1) {
				Message message = new Message();
				message.what = 2;
				handler.sendMessage(message);
			} else if (msg == 0) {
				toast("修改失败");
			}
		}
	};

	private StringCallback getPhoneCallBack=new StringCallback() {
		@Override
		public void onSuccess(String s, Call call, Response response) {
			phoMsg = JsonUtils.parsePhoPwd(s);
			if (phoMsg.getIssend() == 1) {
				toast("发送成功");

			}
		}
	};

	private String intToIp(int i) {

		return (i & 0xFF) + "." + ((i >> 8) & 0xFF) + "." + ((i >> 16) & 0xFF)
				+ "." + (i >> 24 & 0xFF);
	}

	private int first = 1;// 验证码版本为0 登陆版本为1
	private NetWorkCallBack<BaseResult> getCheckCallBack = new NetWorkCallBack<BaseResult>() {

		@Override
		public void onComplete(String json) {
			// TODO Auto-generated method stub
			int msg = JsonUtils.parseMsg(json);
			first = msg;
			if (msg == 1) {
				// toast("欢迎回来");
			} else {
				// toast("欢迎你！新用户");
				// openActivity(CheckActivity.class);
				// finish();
			}
		}
	};
	
//	private NetWorkCallBack<BaseResult> getPhomsgCallBack = new NetWorkCallBack<BaseResult>() {
//
//		@Override
//		public void onComplete(String json) {
//			phoMsg = JsonUtils.parsePhoPwd(json);
//			if (phoMsg.getIssend() == 1) {
//				toast("发送成功");
//
//			}
//		}
//	};
//	private NetWorkCallBack<BaseResult> registerCallBack = new NetWorkCallBack<BaseResult>() {
//
//		@Override
//		public void onComplete(String json) {
//			// TODO Auto-generated method stub
//			int msg = JsonUtils.parseisok(json);
//			if (msg == 1) {
//				Message message = new Message();
//				message.what = 2;
//				handler.sendMessage(message);
//			} else if (msg == 0) {
//				toast("修改失败");
//			}
//
//		}
//	};

	private NetWorkCallBack<BaseResult> pushCallBack = new NetWorkCallBack<BaseResult>() {

		@Override
		public void onComplete(String json) {
			// TODO Auto-generated method stub
			String ResponseId = JsonUtils.parseResponseId(json);
			Message message = new Message();
			message.what = 2;
			handler.sendMessage(message);
		}
	};

	private void toast(final String str) {
		handler.post(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				Toast.makeText(Activity_ForgetPwd.this, str, Toast.LENGTH_LONG)
						.show();
			}
		});

	}

	private int getTime() {
		time--;
		handler.sendEmptyMessageDelayed(5, 1000);
		return time;
	}

}
