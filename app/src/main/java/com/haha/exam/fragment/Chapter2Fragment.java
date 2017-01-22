//package com.haha.exam.fragment;
//
//import android.content.Intent;
//import android.os.Bundle;
//import android.support.annotation.Nullable;
//import android.support.v4.app.Fragment;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.AdapterView;
//import android.widget.ListView;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import com.google.gson.Gson;
//import com.haha.exam.R;
//import com.haha.exam.activity.SubjectOneActivity;
//import com.haha.exam.activity.VideoActivity;
//import com.haha.exam.adapter.MyListViewAdapter1;
//import com.haha.exam.adapter.MyListViewAdapter2;
//import com.haha.exam.bean.VideoInfo;
//import com.haha.exam.web.WebInterface;
//import com.lzy.okgo.OkGo;
//import com.lzy.okgo.callback.StringCallback;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import okhttp3.Call;
//import okhttp3.Response;
//
///**
// * Created by Administrator on 2016/11/5.
// */
//public class Chapter2Fragment extends Fragment {
//    private int selectIndex = 0;
//
//    private TextView title;
//    private ListView mListView1, mListView2;
//    private MyListViewAdapter1 adapter1;
//    private MyListViewAdapter2 adapter2;
//    private List<String> datas;
//    private VideoInfo videoInfo;
//    private Gson gson = new Gson();
//    private List<VideoInfo.DataBean> data = new ArrayList<>();
//    private int pos = 0;
//    private SubjectOneActivity subjectOneActivity;
//
//    @Nullable
//    @Override
//    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        View view = inflater.inflate(R.layout.chapter_1_fragment, container, false);
//        datas = new ArrayList<>();
////        datas.add("第一节");
////        datas.add("第二节");
////        datas.add("第三节");
////        datas.add("第四节");
//
//        mListView1 = (ListView) view.findViewById(R.id.list_item_1);
//        mListView2 = (ListView) view.findViewById(R.id.list_item_2);
//        title = (TextView) view.findViewById(R.id.title);
//
//
//        return view;
//    }
//
//    @Override
//    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
//        super.onActivityCreated(savedInstanceState);
//        subjectOneActivity = new SubjectOneActivity();
//        pos = subjectOneActivity.position;
//
//
////        OkGo.post(WebInterface.video)
////                .tag(this)
////                .execute(new StringCallback() {
////                    @Override
////                    public void onSuccess(String s, Call call, Response response) {
////                        videoInfo = gson.fromJson(s, VideoInfo.class);
////                        int num = 0;
////                        for (int i = 0; i < videoInfo.getData().size(); i++) {
////                            if (videoInfo.getData().get(i).getKnowledge_chapter_id().equals("2") && videoInfo.getData().get(i).getVideoinfo() != null) {
////                                data.add(videoInfo.getData().get(i));
////                                num++;
////                                datas.add("第" + num + "节");
////                            }
////                        }
////
////                        System.out.println("3241234========" + datas.size());
////                        adapter1 = new MyListViewAdapter1(datas, getActivity(), pos);
////                        adapter2 = new MyListViewAdapter2(data, getActivity(), pos);
////                        title.setText(data.get(pos).getKnowledge_name());
////                        mListView1.setAdapter(adapter1);
////                        mListView2.setAdapter(adapter2);
////                        adapter1.setIndex(pos);
////                        mListView1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
////                            @Override
////                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
////
////                                selectIndex = position;
////                                //把下标传过去，然后刷新adapter
////                                adapter1.setIndex(selectIndex);
////                                adapter1.notifyDataSetChanged();
////                                //当点击某个item的时候让这个item自动滑动到listview的顶部(下面item够多，如果点击的是最后一个就不能到达顶部了)
////                                mListView1.smoothScrollToPositionFromTop(position, 0);
////                                title.setText(data.get(position).getKnowledge_name());
////                                adapter2.setIndex(selectIndex);
////                                adapter2.notifyDataSetChanged();
//////                                mListView2.setAdapter(adapter2);
////                                System.out.println("selectIndex======" + selectIndex + "");
////                            }
////                        });
////                        mListView2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
////                            @Override
////                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
////                                Toast.makeText(getActivity(), "你点击了" + position, Toast.LENGTH_SHORT).show();
////                                Intent intent = new Intent(getActivity(), VideoActivity.class);
////                                intent.putExtra("selectIndex", selectIndex);
////                                intent.putExtra("position", position);
////                                intent.putExtra("url", data.get(selectIndex).getVideoinfo().get(position).getVideo_url());
////                                startActivity(intent);
////                            }
////                        });
////
////                    }
////                });
//
//
//    }
//}
