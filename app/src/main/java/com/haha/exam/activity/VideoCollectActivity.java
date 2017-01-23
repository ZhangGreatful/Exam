package com.haha.exam.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.haha.exam.R;
import com.haha.exam.adapter.VideoCollectAdapter;
import com.haha.exam.bean.VideoCollect;
import com.haha.exam.web.WebInterface;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.wanzheng.driver.network.JsonUtils;
import com.wanzheng.driver.util.SystemUtil;

import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Response;

/**
 * 视频收藏界面
 */
public class VideoCollectActivity extends BaseActivity {

    private ListView listView;
    private SystemUtil su;
    private String onlyID;
    private ArrayList<VideoCollect> videoCollects;
    private VideoCollectAdapter adapter;
    private TextView textView;

    @Override
    protected int getContentView() {
        return R.layout.activity_video_collect;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("视频收藏");
        setTitlebarBackground(R.color.white);
        setTitleColor(getResources().getColor(R.color.title_color));
        setLeftBtnDrawable();
        su = new SystemUtil(this);
        onlyID = su.showOnlyID();
        videoCollects = new ArrayList<>();

        initView();
        initData();
    }

    private void initData() {
        OkGo.post(WebInterface.video_list)
                .tag(this)
                .params("signid", onlyID)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        int num = JsonUtils.parseNum(s);
                        if (num == 1) {
                            if (JsonUtils.parseMessage(s) == null) {
                                System.out.println("没有视频收藏");
                            }
                            if (JsonUtils.parseVideoCollect(s).size() != 0) {
                                listView.setVisibility(View.VISIBLE);
                                textView.setVisibility(View.GONE);
                                videoCollects = JsonUtils.parseVideoCollect(s);
                                adapter = new VideoCollectAdapter(VideoCollectActivity.this, videoCollects);
                                listView.setAdapter(adapter);
                                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                    @Override
                                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                                        Intent intent = new Intent(VideoCollectActivity.this, VideoActivity.class);
                                        intent.putExtra("subject", "5");//代表收藏的视频
                                        intent.putExtra("title", videoCollects.get(i).getVideo_title());
                                        intent.putExtra("url", videoCollects.get(i).getVideo_url());
                                        intent.putExtra("videoid", videoCollects.get(i).getVideo_id());
                                        intent.putExtra("showcount", videoCollects.get(i).getShow_count());
                                        intent.putExtra("thumb", videoCollects.get(i).getVideo_thumb());
                                        intent.putExtra("position", i);
                                        startActivity(intent);
                                    }
                                });
                                System.out.println("共有视频收藏数据" + videoCollects.size());
                            }
                        } else {
                            listView.setVisibility(View.GONE);
                            textView.setVisibility(View.VISIBLE);
                        }
                    }
                });
    }

    private void initView() {
        listView = (ListView) findViewById(R.id.video_collect_list);
        textView = (TextView) findViewById(R.id.no_video);

    }


}
