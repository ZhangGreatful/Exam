package com.haha.exam.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.haha.exam.R;
import com.haha.exam.adapter.MyListViewAdapter1;
import com.haha.exam.adapter.MyListViewAdapter2;
import com.haha.exam.bean.VideoInfo;
import com.haha.exam.web.WebInterface;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.wanzheng.driver.network.JsonUtils;
import com.wanzheng.driver.util.SystemUtil;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Response;

/**
 * 科目一视频列表
 */
public class SubjectOneActivity extends BaseActivity {

    private int selectIndex;
    private SystemUtil su;
    private String signID;
    private int played;//已播放完视频的数量
    private List<Integer> playedList = new ArrayList<>();
    private List<Integer> countList = new ArrayList<>();

    private String subject;
    private TextView title, content;
    private ImageView back;
    private ListView mListView1, mListView2;
    private MyListViewAdapter1 adapter1;
    private MyListViewAdapter2 adapter2;
    private List<String> datas;
    private VideoInfo videoInfo;
    private Gson gson = new Gson();
    private List<VideoInfo.DataBean> data;
    private int pos = 0;
    private Handler handler = new Handler();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        su = new SystemUtil(this);
        played = 0;
        signID = su.showOnlyID();

        initView();
        initTitle();
        initData();
    }

    private void initTitle() {
        Intent intent = getIntent();
        subject = intent.getStringExtra("subject");
        if (subject.equals("1")) {
            content.setText("科目一");
        } else if (subject.equals("2")) {
            content.setText("科目二");
        } else if (subject.equals("3")) {
            content.setText("科目三(实操)");
        } else if (subject.equals("4")) {
            content.setText("科目三(理论)");
        }
//        setTitlebarBackground(R.color.white);
//        setTitleColor(getResources().getColor(R.color.title_color));
//        setLeftBtnDrawable();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        data.clear();
        datas.clear();
        countList.clear();
        playedList.clear();
        mListView2.setAdapter(null);
        mListView1.setAdapter(null);
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                initData();
            }
        }, 500);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (data.size() != 0) {
                finish();
            } else {
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    private void initData() {
//              获取视频播放列表
//        playflag      1.播放过  0.未播放过
//        end_status    1.结束    0.未结束
        data = new ArrayList<>();
        videoInfo = new VideoInfo();
        Log.d("SubjectOneActivity", "subject===================" + subject);
        Log.d("SubjectOneActivity", "signID===================" + signID);
        OkGo.post(WebInterface.video)
                .tag(this)
                .params("subject", subject)
                .params("signid", signID)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(final String s, Call call, Response response) {
                        Log.d("SubjectOneActivity", "s===================" + s);
                        int o = JsonUtils.parseNum(s);
                        if (o == 1) {
                            videoInfo = gson.fromJson(s, VideoInfo.class);
                            Log.d("SubjectOneActivity", "videoInfo===============" + videoInfo.getData().size());
                            int num = 0;
                            for (int i = 0; i < videoInfo.getData().size(); i++) {
                                if (videoInfo.getData().get(i).getVideoinfo() != null && videoInfo.getData().get(i).getVideoinfo().size() != 0) {
                                    played = 0;
                                    num++;
                                    datas.add("第" + num + "节");
                                    data.add(videoInfo.getData().get(i));
                                    countList.add(videoInfo.getData().get(i).getVideoinfo().size());
                                }
                                for (int j = 0; j < videoInfo.getData().get(i).getVideoinfo().size(); j++) {
                                    System.out.println("----------" + data.get(i).getVideoinfo().get(j).getPlayposition());
                                    System.out.println("----------" + data.get(i).getVideoinfo().get(j).getVideo_length());
                                    if (videoInfo.getData().get(i).getVideoinfo().get(j).getPlayflag().equals("1")
                                            && videoInfo.getData().get(i).getVideoinfo().get(j).getEnd_status() == 1) {
                                        played++;
                                    }
                                }
                                Log.d("SubjectOneActivity", "played===========" + played);
                                playedList.add(played);
                            }
                            System.out.println("3241234========" + datas.size());

                            adapter1 = new MyListViewAdapter1(datas, countList, playedList, SubjectOneActivity.this, pos);
                            adapter2 = new MyListViewAdapter2(data, SubjectOneActivity.this, pos);
                            title.setText(videoInfo.getData().get(pos).getChapter_name());
                            mListView1.setAdapter(adapter1);
                            mListView2.setAdapter(adapter2);
                            adapter1.setIndex(pos);
                            adapter2.setIndex(selectIndex);
                            adapter2.notifyDataSetChanged();
                            mListView1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                                    selectIndex = position;
                                    //把下标传过去，然后刷新adapter
                                    adapter1.setIndex(selectIndex);
                                    adapter1.notifyDataSetChanged();
                                    //当点击某个item的时候让这个item自动滑动到listview的顶部(下面item够多，如果点击的是最后一个就不能到达顶部了)
                                    mListView1.smoothScrollToPositionFromTop(position, 0);
                                    title.setText(videoInfo.getData().get(position).getChapter_name());
                                    adapter2.setIndex(selectIndex);
                                    adapter2.notifyDataSetChanged();
//                                mListView2.setAdapter(adapter2);
//                                nowProgressPosition = -1;//----------------------------------初始化位置
                                    System.out.println("selectIndex======" + selectIndex + "");
                                }
                            });
                            Log.d("SubjectOneActivity", "---------------" + selectIndex);
                            Log.d("SubjectOneActivity", "p=============" + videoInfo.getData().get(selectIndex).getVideoinfo().get(0).getPlayposition());
                            Log.d("SubjectOneActivity", "p=============" + videoInfo.getData().get(selectIndex).getVideoinfo().get(0).getVideo_length());
                            mListView2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                                    if (selectIndex > 0) {//从第二节开始播
//                                   判断上一节的最后一个视频是否是播放完状态
                                        int size = videoInfo.getData().get(selectIndex - 1).getVideoinfo().size();
                                        if (position == 0) {//某一节的第一个视频
                                            if (data.get(selectIndex - 1).getVideoinfo().get(size - 1).getPlayflag().equals("0")) {
                                                Toast.makeText(SubjectOneActivity.this, "有未播放视频", Toast.LENGTH_SHORT).show();
                                            } else {
                                                if (data.get(selectIndex - 1).getVideoinfo().get(size - 1).getEnd_status() == 0) {
                                                    Toast.makeText(SubjectOneActivity.this, "有未播放视频", Toast.LENGTH_SHORT).show();
                                                } else {
                                                    Intent intent = new Intent(SubjectOneActivity.this, VideoActivity.class);
                                                    Bundle bundle = new Bundle();
                                                    bundle.putString("playflag", data.get(selectIndex).getVideoinfo().get(position).getPlayflag());
                                                    bundle.putString("endflag", data.get(selectIndex).getVideoinfo().get(position).getPlayflag());
                                                    bundle.putInt("playposition", data.get(selectIndex).getVideoinfo().get(position).getPlayposition());
                                                    bundle.putInt("selectIndex", selectIndex);
                                                    bundle.putString("subject", subject);
                                                    bundle.putInt("position", position);
                                                    bundle.putString("videoid", data.get(selectIndex).getVideoinfo().get(position).getVideo_id());
                                                    bundle.putString("url", data.get(selectIndex).getVideoinfo().get(position).getVideo_url());
                                                    bundle.putString("thumb", data.get(selectIndex).getVideoinfo().get(position).getVideo_thumb());
                                                    bundle.putString("title", data.get(selectIndex).getVideoinfo().get(position).getVideo_title());
                                                    System.out.println("url==========" + data.get(selectIndex).getVideoinfo().get(position).getVideo_url());
                                                    intent.putExtras(bundle);
                                                    startActivity(intent);
                                                }
                                            }
                                        } else {//不是某一节的第一个视频
                                            if (data.get(selectIndex).getVideoinfo().get(position - 1).getPlayflag().equals("0")) {
                                                Toast.makeText(SubjectOneActivity.this, "有未播放视频", Toast.LENGTH_SHORT).show();
                                            } else {
                                                if (data.get(selectIndex).getVideoinfo().get(position - 1).getEnd_status() == 0) {
                                                    Toast.makeText(SubjectOneActivity.this, "有未播放视频", Toast.LENGTH_SHORT).show();
                                                } else {
                                                    Intent intent = new Intent(SubjectOneActivity.this, VideoActivity.class);
                                                    Bundle bundle = new Bundle();
                                                    bundle.putString("playflag", data.get(selectIndex).getVideoinfo().get(position).getPlayflag());
                                                    bundle.putString("endflag", data.get(selectIndex).getVideoinfo().get(position).getPlayflag());
                                                    bundle.putInt("playposition", data.get(selectIndex).getVideoinfo().get(position).getPlayposition());
                                                    bundle.putInt("selectIndex", selectIndex);
                                                    bundle.putString("subject", subject);
                                                    bundle.putInt("position", position);
                                                    bundle.putString("videoid", data.get(selectIndex).getVideoinfo().get(position).getVideo_id());
                                                    bundle.putString("url", data.get(selectIndex).getVideoinfo().get(position).getVideo_url());
                                                    bundle.putString("thumb", data.get(selectIndex).getVideoinfo().get(position).getVideo_thumb());
                                                    bundle.putString("title", data.get(selectIndex).getVideoinfo().get(position).getVideo_title());
                                                    System.out.println("url==========" + data.get(selectIndex).getVideoinfo().get(position).getVideo_url());
                                                    intent.putExtras(bundle);
                                                    startActivity(intent);
//                                                startActivityForResult(intent, 0);
//                                                nowProgressPosition = position;//改变位置------------------------------
                                                }
                                            }
                                        }
                                    } else {
                                        if (position == 0) {//某一节的第一个视频
                                            Intent intent = new Intent(SubjectOneActivity.this, VideoActivity.class);
                                            Bundle bundle = new Bundle();
                                            bundle.putString("playflag", data.get(selectIndex).getVideoinfo().get(position).getPlayflag());
                                            bundle.putString("endflag", data.get(selectIndex).getVideoinfo().get(position).getPlayflag());
                                            bundle.putInt("playposition", data.get(selectIndex).getVideoinfo().get(position).getPlayposition());
                                            bundle.putInt("selectIndex", selectIndex);
                                            bundle.putString("subject", subject);
                                            bundle.putInt("position", position);
                                            bundle.putString("videoid", data.get(selectIndex).getVideoinfo().get(position).getVideo_id());
                                            bundle.putString("url", data.get(selectIndex).getVideoinfo().get(position).getVideo_url());
                                            bundle.putString("thumb", data.get(selectIndex).getVideoinfo().get(position).getVideo_thumb());
                                            bundle.putString("title", data.get(selectIndex).getVideoinfo().get(position).getVideo_title());
                                            System.out.println("url==========" + data.get(selectIndex).getVideoinfo().get(position).getVideo_url());
                                            intent.putExtras(bundle);
                                            startActivity(intent);
//                                        startActivityForResult(intent, 0);
//                                        nowProgressPosition = position;//改变位置------------------------------


                                        } else {//不是某一节的第一个视频
                                            if (data.get(0).getVideoinfo().get(position - 1).getPlayflag().equals("0")) {
                                                Toast.makeText(SubjectOneActivity.this, "有未播放视频", Toast.LENGTH_SHORT).show();
                                            } else {
                                                if (data.get(0).getVideoinfo().get(position - 1).getEnd_status() == 0) {
                                                    Toast.makeText(SubjectOneActivity.this, "有未播放视频", Toast.LENGTH_SHORT).show();
                                                } else {
                                                    Intent intent = new Intent(SubjectOneActivity.this, VideoActivity.class);
                                                    Bundle bundle = new Bundle();
                                                    bundle.putString("playflag", data.get(selectIndex).getVideoinfo().get(position).getPlayflag());
                                                    bundle.putString("endflag", data.get(selectIndex).getVideoinfo().get(position).getPlayflag());
                                                    bundle.putInt("playposition", data.get(selectIndex).getVideoinfo().get(position).getPlayposition());
                                                    bundle.putInt("selectIndex", selectIndex);
                                                    bundle.putString("subject", subject);
                                                    bundle.putInt("position", position);
                                                    bundle.putString("videoid", data.get(selectIndex).getVideoinfo().get(position).getVideo_id());
                                                    bundle.putString("url", data.get(selectIndex).getVideoinfo().get(position).getVideo_url());
                                                    bundle.putString("thumb", data.get(selectIndex).getVideoinfo().get(position).getVideo_thumb());
                                                    bundle.putString("title", data.get(selectIndex).getVideoinfo().get(position).getVideo_title());
                                                    System.out.println("url==========" + data.get(selectIndex).getVideoinfo().get(position).getVideo_url());
                                                    intent.putExtras(bundle);
                                                    startActivity(intent);
//                                                startActivityForResult(intent, 0);
//                                                nowProgressPosition = position;//改变位置------------------------------
                                                }
                                            }
                                        }
                                    }

                                }
                            });
                        }


                    }
                });
    }


    private void initView() {
        datas = new ArrayList<>();
        content = (TextView) findViewById(R.id.content);
        mListView1 = (ListView) findViewById(R.id.list_item_1);
        mListView2 = (ListView) findViewById(R.id.list_item_2);
        title = (TextView) findViewById(R.id.title);
        back = (ImageView) findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (data.size() != 0) {
                    finish();
                }
            }
        });

        Intent intent = getIntent();
        pos = intent.getIntExtra("position", 0);
        selectIndex = pos;
    }

//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        //跳转视频界面返回之后刷新progressbar------------------------------------------------
//        if (data != null) {
//            if (requestCode == 0) {
//                int currenttime = data.getIntExtra("play_progress", 0);
//                if (data.getBooleanExtra("play_over", false)) {
//                    adapter2.setProgress(nowProgressPosition, currenttime, true);
//                } else {
//                    adapter2.setProgress(nowProgressPosition, currenttime, false);
////                    adapter2.notifyDataSetChanged();
//                }
//
//            }
//        }
//
//
//    }

    @Override
    protected int getContentView() {
        return R.layout.activity_subject_one;
    }

    @Override
    protected int getTitlebarResId() {
        return R.layout.subject_one_bar;
    }
}
