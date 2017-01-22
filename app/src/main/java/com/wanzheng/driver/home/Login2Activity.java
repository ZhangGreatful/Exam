package com.wanzheng.driver.home;

import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;

import com.haha.exam.R;
import com.haha.exam.activity.BaseActivity;

/**
 * 新的登录界面
 */
public class Login2Activity extends BaseActivity implements View.OnClickListener {

    private EditText userNameEdt, phopwdEdt;
    private Button submintBtn, applyBtn, loginBtn;

    @Override
    protected int getContentView() {
        return R.layout.activity_login2;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitlebarBackground(R.color.mine_head_background);
        setTitle("登录");
        setTitleColor(getResources().getColor(R.color.title_color));
        initView();
    }

    private void initView() {
        userNameEdt = (EditText) findViewById(R.id.user_name);
        phopwdEdt = (EditText) findViewById(R.id.password);
        userNameEdt.setInputType(EditorInfo.TYPE_CLASS_PHONE);
        phopwdEdt.setInputType(EditorInfo.TYPE_CLASS_NUMBER);
        submintBtn = (Button) findViewById(R.id.btn_get);
        applyBtn = (Button) findViewById(R.id.apply_submit);
        loginBtn = (Button) findViewById(R.id.login_submit);

        submintBtn.setOnClickListener(this);
        applyBtn.setOnClickListener(this);
        loginBtn.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_get://发送验证码

                break;
            case R.id.apply_submit://游客模式登录

                break;
            case R.id.login_submit://登录

                break;
        }
    }
}
