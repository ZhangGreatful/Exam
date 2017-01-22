package com.haha.exam.activity;

import android.os.Bundle;
import android.view.KeyEvent;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.haha.exam.R;

/**
 * Created by Administrator on 2016/12/29.
 */

public class TakeOrderActivity extends BaseActivity {
    private WebView web;
    private String weburl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initTitle();
        initView();
    }

    private void initView() {
        web = (WebView) findViewById(R.id.webView);
        web.getSettings().setJavaScriptEnabled(true);
//        web 设置可以支持缩放
        web.getSettings().setSupportZoom(true);
//        web 设置出现缩放工具
        web.getSettings().setBuiltInZoomControls(true);
//        web 扩大比例的缩放
        web.getSettings().setUseWideViewPort(true);
//        web 自适应屏幕
//        web.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        web.getSettings().setLoadWithOverviewMode(true);
        web.loadUrl(weburl);
        web.setWebViewClient(new WebViewClient() {

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
        web.reload();
        super.onPause();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // TODO Auto-generated method stub
        if (web.canGoBack()) {
            web.goBack();
            return true;

        } else if (keyCode == KeyEvent.KEYCODE_BACK) {
//			backData();
            finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    private void initTitle() {

        setTitle("考试预约");
        weburl = "http://122.gov.cn/m/map/select";
        setTitlebarBackground(R.color.white);
        setTitleColor(getResources().getColor(R.color.title_color));
        setLeftBtnDrawable();
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_rule;
    }
}
