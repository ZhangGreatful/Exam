package com.haha.exam.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.haha.exam.R;
import com.haha.exam.dialog.PraticeDialog;
import com.haha.exam.utils.SPUtils;
import com.haha.exam.view.circleimageview.CircleImageView;
import com.squareup.picasso.Picasso;
import com.wanzheng.driver.util.SystemUtil;

/**
 * 模拟考试登录界面
 */
public class PracticeTextActivity extends BaseActivity implements View.OnClickListener {

    private SystemUtil su;
    private SPUtils spUtils = new SPUtils();
    private String subject;
    private String headerPath;
    private ImageView moni, zuoti;
    private TextView kemu, counts,time;
    private CircleImageView header;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("模拟考试");
        setTitleColor(getResources().getColor(R.color.title_color));
        setTitlebarBackground(R.color.white);
        setLeftBtnDrawable();
        su = new SystemUtil(this);
        subject = (String) spUtils.get(this, "subject", "one");
        headerPath = su.showUserHeader();
        initView();
    }

    private void initView() {
        time= (TextView) findViewById(R.id.time);
        moni = (ImageView) findViewById(R.id.iv_moni);
        zuoti = (ImageView) findViewById(R.id.iv_zuoti);
        header = (CircleImageView) findViewById(R.id.iv_login_header);
        kemu = (TextView) findViewById(R.id.kemu);
        counts = (TextView) findViewById(R.id.counts);
        if (subject.equals("one")) {
            kemu.setText("科目一理论考试");
            counts.setText("100");
            time.setText("45");

        } else {
            kemu.setText("科目四理论考试");
            counts.setText("50");
            time.setText("30");
        }

        if (!su.showOnlyID().equals("")&&!headerPath.equals("")) {
            Picasso.with(this).load(headerPath).into(header);
        }

        moni.setOnClickListener(this);
        header.setOnClickListener(this);
        zuoti.setOnClickListener(this);
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
                        Intent intent = new Intent(PracticeTextActivity.this, PraticeActivity.class);
                        intent.putExtra("FROM", 0);
                        startActivity(intent);
                        finish();
                        dialog.dismiss();
                    }
                });
                break;
            case R.id.iv_zuoti:
                final PraticeDialog dialog1 = new PraticeDialog(PracticeTextActivity.this);
                dialog1.show();
                dialog1.setYesOnclickListener("确定", new PraticeDialog.onYesOnclickListener() {
                    @Override
                    public void onYesClick() {
                        Intent intent = new Intent(PracticeTextActivity.this, PraticeActivity.class);
                        intent.putExtra("FROM", 0);
                        startActivity(intent);
                        finish();
                        dialog1.dismiss();
                    }
                });
                break;
        }
    }
}
