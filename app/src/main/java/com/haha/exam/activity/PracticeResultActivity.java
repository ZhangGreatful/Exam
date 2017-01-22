package com.haha.exam.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.haha.exam.R;
import com.haha.exam.bean.AllQuestions;
import com.haha.exam.bean.MyGrade;
import com.haha.exam.dao.ExamDao;
import com.haha.exam.utils.SPUtils;
import com.haha.exam.view.circleimageview.CircleImageView;
import com.lzy.okgo.OkGo;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.Picasso;
import com.wanzheng.driver.util.SystemUtil;

import java.util.List;

/**
 * 点击完交卷，进入该页面
 * 模拟考试成绩界面
 */
public class PracticeResultActivity extends BaseActivity implements View.OnClickListener {

    private SystemUtil su;
    private String headerPath;
    private SPUtils spUtils = new SPUtils();
    private String subject, type;
    private ImageView back, iv_result;
    private CircleImageView header;
    private TextView cartype, score, minite_time, second_time, username;
    private RelativeLayout error_review, re_exam;
    private PraticeActivity practiceActivity;
    private MyGradeActivity myGradeActivity;
    public static MyGrade myGrade;
    public static List<AllQuestions.DataBean> datas;
    public static int position;
    private ExamDao dao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        practiceActivity = new PraticeActivity();
        myGradeActivity = new MyGradeActivity();
        su = new SystemUtil(PracticeResultActivity.this);
        headerPath = su.showUserHeader();
        dao = new ExamDao(this);
        subject = (String) spUtils.get(this, "subject", "one");
        type = (String) spUtils.get(this, "cartype", "xc");
        initView();
        initData();
    }

    private void initData() {

        Intent intent = getIntent();
        if (intent.getIntExtra("msg", 0) == -1) {//点击交卷
            datas = practiceActivity.datas;
            System.out.println("datassize=========" + datas.size());
            if (subject.equals("one")) {
                if (type.equals("xc")) {
                    cartype.setText("小车•科目一");
                } else if (type.equals("kc")) {
                    cartype.setText("客车•科目一");
                } else if (type.equals("hc")) {
                    cartype.setText("货车•科目一");
                } else if (type.equals("mtc")) {
                    cartype.setText("摩托车•科目一");
                }
                score.setText(String.valueOf(practiceActivity.right));
            } else {
                if (type.equals("xc")) {
                    cartype.setText("小车•科目四");
                } else if (type.equals("kc")) {
                    cartype.setText("客车•科目四");
                } else if (type.equals("hc")) {
                    cartype.setText("货车•科目四");
                } else if (type.equals("mtc")) {
                    cartype.setText("摩托车•科目四");
                }
                score.setText(String.valueOf((2 * practiceActivity.right)));
            }
            if (!su.showOnlyID().equals("") && !headerPath.equals("")) {
                Picasso.with(this).load(headerPath)
                        .memoryPolicy(MemoryPolicy.NO_CACHE, MemoryPolicy.NO_STORE).into(header);
            }

            int time = practiceActivity.time;
            int second = time % 60;
            int minite = time / 60;
            minite_time.setText(String.valueOf(minite));
            second_time.setText(String.valueOf(second));
            if (practiceActivity.right < 90) {
                iv_result.setImageResource(R.drawable.ma_lu_sha_shou);
            } else {
                iv_result.setImageResource(R.mipmap.gong_xi_tong_guo);
            }
        } else {//从我的成绩界面跳转过来

            position = intent.getIntExtra("position", 0);
            System.out.println("position==========" + position);
            myGrade = myGradeActivity.myGrade;
            datas = dao.getExamQuestions("xc", subject, myGrade, position);
            System.out.println("09009090909=========" + datas.size());
            int textScore = Integer.valueOf(myGrade.getData().get(position).getScore());
            score.setText(myGrade.getData().get(position).getScore());
            Picasso.with(this).load(headerPath).into(header);
            if (myGrade.getData().get(position).getSubject().equals("1")) {
                cartype.setText("科目一");
            } else {
                cartype.setText("科目四");
            }
            String string = myGrade.getData().get(position).getTime() != null ? myGrade.getData().get(position).getTime() : "";
            int i = 0;
            if (!string.equals("")) {
                i = Integer.parseInt(string);
            }
            int second = i % 60;
            int minite = i / 60;
            minite_time.setText(String.valueOf(minite));
            second_time.setText(String.valueOf(second));

            if (textScore < 90) {
                iv_result.setImageResource(R.drawable.ma_lu_sha_shou);
            } else {
                iv_result.setImageResource(R.mipmap.gong_xi_tong_guo);
            }

        }

//        未登录情况下

    }

    private void initView() {
        back = (ImageView) findViewById(R.id.back);
        header = (CircleImageView) findViewById(R.id.iv_header);
        iv_result = (ImageView) findViewById(R.id.iv_result);
        username = (TextView) findViewById(R.id.username);
        cartype = (TextView) findViewById(R.id.cartype);
        score = (TextView) findViewById(R.id.grade);
        minite_time = (TextView) findViewById(R.id.minute);
        second_time = (TextView) findViewById(R.id.second);
        error_review = (RelativeLayout) findViewById(R.id.error_review);
        re_exam = (RelativeLayout) findViewById(R.id.re_exam);
        score.setText(String.valueOf(practiceActivity.right));

        if (practiceActivity.right < 90) {
            iv_result.setImageResource(R.drawable.ma_lu_sha_shou);
        } else {
            iv_result.setImageResource(R.mipmap.gong_xi_tong_guo);
        }

        username.setText(su.showName());
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
                intent.putExtra("exam", "-1");
                startActivity(intent);
                finish();
                break;
            case R.id.error_review:
                Intent intent1 = new Intent(PracticeResultActivity.this, ReviewErrorActivity.class);
                intent1.putExtra("allerror", "-1");
                startActivity(intent1);
                finish();
                break;
        }
    }
}
