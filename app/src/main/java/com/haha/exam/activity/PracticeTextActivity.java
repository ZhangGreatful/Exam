package com.haha.exam.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.haha.exam.R;
import com.haha.exam.dialog.MyDialog;
import com.haha.exam.dialog.PraticeDialog;

/**
 * ]模拟考试登录界面
 */
public class PracticeTextActivity extends BaseActivity implements View.OnClickListener {

    private ImageView moni, zuoti,header;
    private TextView login;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("模拟考试");
        initView();
    }

    private void initView() {
        moni = (ImageView) findViewById(R.id.iv_moni);
        zuoti = (ImageView) findViewById(R.id.iv_zuoti);
        login= (TextView) findViewById(R.id.tv_login);
        header= (ImageView) findViewById(R.id.iv_login_header);

        moni.setOnClickListener(this);
        login.setOnClickListener(this);
        header.setOnClickListener(this);
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_practice_text;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_moni:
                final PraticeDialog dialog = new PraticeDialog(PracticeTextActivity.this);
                dialog.show();
                dialog.setYesOnclickListener("确定", new PraticeDialog.onYesOnclickListener() {
                    @Override
                    public void onYesClick() {
                        Toast.makeText(PracticeTextActivity.this, "点击了--确定--按钮", Toast.LENGTH_LONG).show();
                        dialog.dismiss();
                    }
                });
                break;
            case R.id.tv_login:
                Intent intent=new Intent(PracticeTextActivity.this,LoginActivity.class);
                startActivity(intent);
                finish();
                break;
            case R.id.iv_login_header:
                Intent intent1=new Intent(PracticeTextActivity.this,LoginActivity.class);
                startActivity(intent1);
                finish();
                break;
        }
    }
}
