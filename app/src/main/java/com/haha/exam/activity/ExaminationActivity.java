package com.haha.exam.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.haha.exam.R;
import com.haha.exam.adapter.FourAdapter;
import com.haha.exam.fragment.FourFragment;
import com.haha.exam.view.TabPageIndicator;
import com.haha.exam.web.WebInterface;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.umeng.analytics.MobclickAgent;
import com.wanzheng.driver.network.NetWorkUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Response;

/**
 * 考试秘籍
 *
 * @author Administrator
 */
@SuppressLint("NewApi")
public class ExaminationActivity extends FragmentActivity implements OnClickListener {

    private TabPageIndicator tab;
    private List<Fragment> mList;
    private ViewPager mVp;
    private ImageView iv_return;
    private List<String> titleList = new ArrayList<String>();
    private String tid;
    private String rule2;
    private String rule3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_examination);
        if (!new NetWorkUtils().checkNet(this)) {
            Toast.makeText(this, "没有网络，加载失败",Toast.LENGTH_SHORT).show();
        }
        initedata();
        initeview();
        ll_coursetwo.setOnClickListener(this);
        ll_coursethree.setOnClickListener(this);
        iv_return.setOnClickListener(this);
    }

    @Override
    protected void onResume() {
        // TODO Auto-generated method stub
        super.onResume();

        MobclickAgent.onPageStart("examinationActivity");
        MobclickAgent.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd("examinationActivity");
        MobclickAgent.onPause(this);
    }

    private void initedata() {
        // TODO Auto-generated method stub
        OkGo.post(WebInterface.exam_message)
                .tag(this)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        llLoading.setVisibility(View.INVISIBLE);

                        try {
                            JSONObject object = new JSONObject(s);
                            JSONObject object2 = object.getJSONObject("skillinfo");
                            rule2 = object2.getString("rule2");
                            rule3 = object2.getString("rule3");
                            JSONArray jsonArray = object2.getJSONArray("types");
                            for (int i = 0; i < jsonArray.length(); i++) {

                                JSONObject object3 = jsonArray.getJSONObject(i);
                                tid = object3.getString("tid");
                            }
                        } catch (JSONException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                    }
                });
    }

    private void initeview() {
        llLoading = (LinearLayout) findViewById(R.id.ll_loading);
        tab = (TabPageIndicator) findViewById(R.id.tpi);
        mVp = (ViewPager) findViewById(R.id.vp);
        iv_return = (ImageView) findViewById(R.id.iv_return);
        ll_coursetwo = (LinearLayout) findViewById(R.id.ll_coursetwo);
        ll_coursethree = (LinearLayout) findViewById(R.id.ll_coursethree);

        titleList.add("新捷达");
        titleList.add("老捷达");
        titleList.add("桑塔纳");
        titleList.add("其他");

        mList = new ArrayList<Fragment>();
        for (int i = 0; i < 4; i++) {
            FourFragment fragment = new FourFragment();
            Bundle args = new Bundle();
            args.putInt("tid", i + 1);
            fragment.setArguments(args);
            mList.add(fragment);

        }
        FourAdapter adapter = new FourAdapter(titleList, tab, mList, mVp, getSupportFragmentManager());
        mVp.setAdapter(adapter);
        adapter.setTabPager();

    }

    private LinearLayout ll_coursetwo;
    private LinearLayout ll_coursethree;
    public LinearLayout llLoading;

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        switch (v.getId()) {
            case R.id.ll_coursetwo:

                Intent intent = new Intent(ExaminationActivity.this, WebExaminationActivity.class);
                intent.putExtra("url", rule2);
                intent.putExtra("title", "科二考规");
                intent.putExtra("lesson", "2");
                startActivity(intent);
                break;
            case R.id.ll_coursethree:
                Intent intent2 = new Intent(ExaminationActivity.this, WebExaminationActivity.class);
                intent2.putExtra("url", rule3);
                intent2.putExtra("title", "科三考规");
                intent2.putExtra("lesson", "3");
                startActivity(intent2);

                break;
            case R.id.iv_return:
                finish();
                break;
            default:
                break;
        }
    }
}
