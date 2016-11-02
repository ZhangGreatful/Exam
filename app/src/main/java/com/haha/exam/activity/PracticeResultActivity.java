package com.haha.exam.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.haha.exam.R;
import com.haha.exam.adapter.PracticeAdapter;
import com.haha.exam.bean.PersonInfo;
import com.haha.exam.web.WebInterface;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.squareup.picasso.Picasso;

import okhttp3.Call;
import okhttp3.Response;

/**
 * 点击完交卷，进入该页面
 * 模拟考试成绩界面
 */
public class PracticeResultActivity extends BaseActivity implements View.OnClickListener {

    private ImageView back, header, iv_result;
    private TextView cartype, score, minite_time, second_time, username;
    private LinearLayout error_review, re_exam;
    private PraticeActivity practiceActivity;
    private PracticeAdapter practiceAdapter;
    private Gson gson = new Gson();
    private int time;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        practiceActivity = new PraticeActivity();
        time = practiceActivity.time;
        initView();
        initData();
    }

    private void initData() {

//        用户登录情况下
        OkGo.post(WebInterface.get_person)
                .tag(this)
                .params("telphone", "18266142739")
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        PersonInfo personInfo = gson.fromJson(s, PersonInfo.class);
                        username.setText(personInfo.getMsg().get(0).getName());
                        Picasso.with(PracticeResultActivity.this).load(personInfo.getMsg().get(0).getPictrue()).into(header);
                        int second = time % 60;
                        int minite = time / 60;
                        minite_time.setText(String.valueOf(minite));
                        second_time.setText(String.valueOf(second));
                        String subject = "";
                        cartype.setText(personInfo.getMsg().get(0).getCar_type() + "·" + subject);
                    }
                });
//        未登录情况下

    }

    private void initView() {
        back = (ImageView) findViewById(R.id.back);
        header = (ImageView) findViewById(R.id.iv_header);
        iv_result = (ImageView) findViewById(R.id.iv_result);
        username = (TextView) findViewById(R.id.username);
        cartype = (TextView) findViewById(R.id.cartype);
        score = (TextView) findViewById(R.id.grade);
        minite_time = (TextView) findViewById(R.id.minute);
        second_time = (TextView) findViewById(R.id.second);
        error_review = (LinearLayout) findViewById(R.id.error_review);
        re_exam = (LinearLayout) findViewById(R.id.re_exam);
        score.setText(String.valueOf(practiceActivity.right));

        if (practiceActivity.right < 90) {
            iv_result.setImageResource(R.drawable.ma_lu_sha_shou);
        } else {
            iv_result.setImageResource(R.mipmap.gong_xi_tong_guo);
        }

        error_review.setOnClickListener(this);
        re_exam.setOnClickListener(this);
        back.setOnClickListener(this);
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_practice_result;
    }

    @Override
    protected int getTitlebarResId() {
        return R.layout.result_bar;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        OkGo.getInstance().cancelTag(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.re_exam:
                Intent intent = new Intent(PracticeResultActivity.this, PraticeActivity.class);
                startActivity(intent);
                finish();
                break;
        }
    }
}
