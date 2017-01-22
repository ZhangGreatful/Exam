package com.haha.exam.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.haha.exam.R;
import com.haha.exam.dao.DatabaseHelper;
import com.haha.exam.dialog.MyDialog;
import com.haha.exam.utils.SPUtils;
import com.wanzheng.driver.home.LoginActivity;
import com.wanzheng.driver.util.SystemUtil;

public class SettingActivity extends BaseActivity implements View.OnClickListener {

    private HomeActivity homeActivity;
    private SystemUtil su;
    private SPUtils spUtils = new SPUtils();
    private DatabaseHelper dbHelper;
    private ImageView back;
    private RelativeLayout update_version, service_center, about_us, change_password;
    private Button exit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("设置");
        homeActivity = new HomeActivity();
        su = new SystemUtil(this);
        dbHelper = new DatabaseHelper(this);
        initView();
    }

    private void initView() {
        back = (ImageView) findViewById(R.id.back);
        change_password = (RelativeLayout) findViewById(R.id.change_password);
        update_version = (RelativeLayout) findViewById(R.id.update_version);
        service_center = (RelativeLayout) findViewById(R.id.service_center);
        about_us = (RelativeLayout) findViewById(R.id.about_us);
        exit = (Button) findViewById(R.id.exit);

        change_password.setOnClickListener(this);
        back.setOnClickListener(this);
        update_version.setOnClickListener(this);
        service_center.setOnClickListener(this);
        about_us.setOnClickListener(this);
        exit.setOnClickListener(this);
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_setting2;
    }

    @Override
    protected int getTitlebarResId() {
        return R.layout.setting_bar;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.change_password:
                Intent intent2 = new Intent(this, ChangePasswordActivity.class);
                startActivity(intent2);
                break;
            case R.id.update_version:
                Toast.makeText(this, "当前版本是最新版本", Toast.LENGTH_SHORT).show();
                break;
            case R.id.service_center:
                Intent phoneIntent = new Intent("android.intent.action.CALL", Uri.parse("tel:" + "4008004650"));
                startActivity(phoneIntent);
                break;
            case R.id.about_us:
                Intent intent = new Intent(SettingActivity.this, AboutUsActivity.class);
                startActivity(intent);
                break;
            case R.id.exit:
//                判断是否是登录状态，若是登录状态，则点击退出，删除数据库

                final MyDialog dialog = new MyDialog(this);
                dialog.setMessage("请确认是否退出");
                dialog.show();
                dialog.setNoOnclickListener("否", new MyDialog.onNoOnclickListener() {
                    @Override
                    public void onNoClick() {
                        dialog.dismiss();
                    }
                });
                dialog.setYesOnclickListener("是", new MyDialog.onYesOnclickListener() {
                    @Override
                    public void onYesClick() {
//                        清空所有数据（数据库数据，保存数据）
                        su.clear();
//                        spUtils.clear(SettingActivity.this);
                        spUtils.remove(SettingActivity.this, "subject4_learned");
                        spUtils.remove(SettingActivity.this, "subject4_alltime");

                        Intent intent = new Intent(SettingActivity.this, LoginActivity.class);
                        startActivity(intent);
//                        su.deletePwd();
                        homeActivity.instance.finish();
                        finish();
                        dialog.dismiss();
                    }
                });

                break;
        }
    }
}
