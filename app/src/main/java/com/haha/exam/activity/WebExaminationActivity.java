package com.haha.exam.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebSettings.LayoutAlgorithm;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.haha.exam.R;
import com.wanzheng.driver.util.SystemUtil;

public class WebExaminationActivity extends BaseActivity implements OnClickListener {
	private WebView wv;
	private SystemUtil su;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		initeview();
		su = new SystemUtil(WebExaminationActivity.this);
		Intent intent = getIntent();
		Bundle extras = intent.getExtras();
		String weburl = extras.getString("url");
		System.out.println("科三url============="+weburl);
		String title = extras.getString("title");
		lesson = extras.getString("lesson");

		setTitleColor(getResources().getColor(R.color.title_color));
		setTitlebarBackground(R.color.white);
		setLeftBtnDrawable();
		setTitle(title);
//		TextView txt = (TextView) getTitleBar().findViewById(R.id.titlebar_right_text);
//		txt.setText("分享");
//		txt.setVisibility(View.VISIBLE);
//		txt.setOnClickListener(this);

		wv.getSettings().setJavaScriptEnabled(true);
		// 设置可以支持缩放
		wv.getSettings().setSupportZoom(true);
		// 设置出现缩放工具
		wv.getSettings().setBuiltInZoomControls(true);
		// 扩大比例的缩放
		wv.getSettings().setUseWideViewPort(true);
		// 自适应屏幕
		wv.getSettings().setLayoutAlgorithm(LayoutAlgorithm.SINGLE_COLUMN);
		wv.getSettings().setLoadWithOverviewMode(true);
		wv.loadUrl(weburl);
		wv.setWebViewClient(new WebViewClient() {

			@Override
			public boolean shouldOverrideUrlLoading(WebView view, String url) {
				// TODO Auto-generated method stub
				view.loadUrl(url);
				return true;
			}

		});
	}

	@Override
	protected int getContentView() {
		return R.layout.activity_webexamination;
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		wv.reload();
		super.onPause();
	}

	private void initeview() {
		wv = (WebView) findViewById(R.id.wv);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.iv_return:
			finish();
			break;
//		case R.id.titlebar_right_text:
//
//			initedatashare();
//
//			break;
		}
	}

//	private void initedatashare() {
//		// TODO Auto-generated method stub
//		new NetWorkUtils().getInstance().work(new MineNetInterface().getInstance().shareRule(lesson),
//				getOrderInforcallback);
//	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub

		if (keyCode == KeyEvent.KEYCODE_BACK && wv.canGoBack()) {
			wv.goBack();
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}
//		//分享回调
//	private NetWorkCallBack<BaseResult> getOrderInforcallback = new NetWorkCallBack<BaseResult>() {
//
//		@Override
//		public void onComplete(String json) {
//
//			try {
//				JSONObject object = new JSONObject(json);
//				JSONObject object2 = object.getJSONObject("data");
//
//				Share share = new Share();
//				share.setPic(object2.getString("thumb"));
//				share.setTitle(object2.getString("title"));
//				share.setContent(object2.getString("disc"));
//				share.setUrl(object2.getString("url"));
//				SharePopWindow shareBoard = new SharePopWindow(WebExaminationActivity.this, share);
//				shareBoard.showAtLocation(WebExaminationActivity.this.getWindow().getDecorView(), Gravity.BOTTOM, 0, 0);
//
//			} catch (JSONException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//
//		}
//
//	};
	private String lesson;
}
