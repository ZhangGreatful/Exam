package com.haha.exam.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.webkit.WebSettings.LayoutAlgorithm;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;

import com.haha.exam.R;
import com.haha.exam.bean.Four;
import com.umeng.analytics.MobclickAgent;
import com.wanzheng.driver.util.SystemUtil;

public class ExaminationDetailActivity extends Activity implements
        OnClickListener {

    private WebView wv;
    private Four driveHead;
    private SystemUtil su;
    private String wid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activty_examinationdetail);
        initeview();
        su = new SystemUtil(ExaminationDetailActivity.this);
        Intent intent = getIntent();
        driveHead = (Four) intent.getSerializableExtra("DriveHead");
        wid = driveHead.getWid();
        iscollection = driveHead.getIscollection();

        String title = driveHead.getBiaoti();
        String weburl = driveHead.getUrl();
        tv_title.setText("考试咨询");

        iv_return.setOnClickListener(this);

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
    protected void onPause() {
        // TODO Auto-generated method stub
        wv.reload();
        super.onPause();
        MobclickAgent.onPageEnd("examinationDetailActivity");
        MobclickAgent.onPause(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onPageStart("examinationDetailActivity");
        MobclickAgent.onResume(this);
    }

    private void initeview() {
        wv = (WebView) findViewById(R.id.wv);
        tv_title = (TextView) findViewById(R.id.tv_title);
        iv_return = (ImageView) findViewById(R.id.iv_return);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // TODO Auto-generated method stub
        if (wv.canGoBack()) {
            wv.goBack();
            return true;

        } else if (keyCode == KeyEvent.KEYCODE_BACK) {
//			backData();
            finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        switch (v.getId()) {
            case R.id.iv_return:
//			backData();
                finish();
                break;
//		case R.id.tv_share:
//			initedatashare();
//
//			break;
//
        }
    }

    //	private void initedatashare() {
//		// TODO Auto-generated method stub
//		new NetWorkUtils().getInstance().work(
//				new MineNetInterface().getInstance().shareMiji(su.showOpenID(),
//						wid), getOrderInforcallback);
//	}
//
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
//				SharePopWindow shareBoard = new SharePopWindow(
//						ExaminationDetailActivity.this, share);
//				shareBoard.showAtLocation(ExaminationDetailActivity.this
//						.getWindow().getDecorView(), Gravity.BOTTOM, 0, 0);
//
//			} catch (JSONException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//
//		}
//
//	};
    private int iscollection;
    private TextView tv_title;
    private ImageView iv_return;

}
