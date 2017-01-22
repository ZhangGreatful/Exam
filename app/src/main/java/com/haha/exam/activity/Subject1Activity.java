package com.haha.exam.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.gson.Gson;
import com.haha.exam.R;
import com.haha.exam.adapter.VideoAdapter;
import com.haha.exam.bean.VideoInfo;
import com.haha.exam.web.WebInterface;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.wanzheng.driver.util.SystemUtil;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Response;

/**
 * 科一视频界面
 */
public class Subject1Activity extends BaseActivity {

    private ListView listView;
    private VideoInfo videoInfo;
    private Gson gson = new Gson();
    private VideoAdapter adapter;
    private String subject;
    private SystemUtil su;
    private String signID;
    private int played;
    private List<Integer> playedList = new ArrayList<>();
    private List<Integer> countList = new ArrayList<>();

    private List<VideoInfo.DataBean> datas = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        su = new SystemUtil(this);
        signID = su.showOnlyID();
        isConnect(this);
        initTitle();
        initView();
        initData();
    }

    private void initTitle() {
        Intent intent = getIntent();
        subject = intent.getStringExtra("subject");
        if (subject.equals("1")) {
            setTitle("科目一");
        } else if (subject.equals("2")) {
            setTitle("科目二");
        } else if (subject.equals("3")) {
            setTitle("科目三(实操)");
        } else if (subject.equals("4")) {
            setTitle("科目三(理论)");
        }
        setTitlebarBackground(R.color.white);
        setTitleColor(getResources().getColor(R.color.title_color));
        setLeftBtnDrawable();
    }


    private void initData() {

        OkGo.post(WebInterface.video)
                .tag(this)
                .params("subject", subject)
                .params("signid", signID)
                .execute(VideoCallBack);
    }

    StringCallback VideoCallBack = new StringCallback() {
        @Override
        public void onSuccess(String s, Call call, Response response) {
            videoInfo = gson.fromJson(s, VideoInfo.class);
            for (int i = 0; i < videoInfo.getData().size(); i++) {
                if (videoInfo.getData().get(i).getVideoinfo() != null&&videoInfo.getData().get(i).getVideoinfo().size()!=0) {
                    played = 0;
                    datas.add(videoInfo.getData().get(i));
                    countList.add(videoInfo.getData().get(i).getVideoinfo().size());
                }
                for (int j = 0; j < videoInfo.getData().get(i).getVideoinfo().size(); j++) {
                    if (videoInfo.getData().get(i).getVideoinfo().get(j).getPlayflag().equals("1")
                            && videoInfo.getData().get(i).getVideoinfo().get(j).getEnd_status() == 1) {
                        played++;
                    }
                }
                Log.d("SubjectOneActivity", "played===========" + played);
                playedList.add(played);
            }
            adapter = new VideoAdapter(Subject1Activity.this, datas,countList,playedList);
            listView.setAdapter(adapter);
        }
    };

    private void initView() {
        listView = (ListView) findViewById(R.id.list_order_logistics);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(Subject1Activity.this, SubjectOneActivity.class);
                intent.putExtra("subject", subject);
                intent.putExtra("position", i);
                startActivity(intent);
            }
        });
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_subject1;
    }

}
