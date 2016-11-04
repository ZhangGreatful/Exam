package com.haha.exam.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.haha.exam.R;
import com.haha.exam.activity.SubjectOneActivity;
import com.haha.exam.adapter.VideoAdapter;
import com.haha.exam.view.TimeLineView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/11/4.
 */
public class ChapterOneFragment extends Fragment {

    private TimeLineView timeLine;
    /**
     * 显示物流的listview
     */
    private ListView listView;

    private VideoAdapter adapter;
    private List<String> datas;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.chapter_one_fragment, container, false);
        listView = (ListView) view.findViewById(R.id.list_order_logistics);
        datas=new ArrayList<>();
        datas.add("驾驶证的使用");
        datas.add("驾驶考试");
        datas.add("驾驶证的申领");
        datas.add("违法记分制度");
        datas.add("驾驶证的许可、种类和有效期");


        adapter = new VideoAdapter(getActivity(), datas);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent=new Intent(getActivity(), SubjectOneActivity.class);
                startActivity(intent);
            }
        });
        timeLine = (TimeLineView) view.findViewById(R.id.tv_timelines);
        timeLine.setTimelineCount(5);//设置显示多少个时间轴
        timeLine.setTimelineHeadRadius(10);
        timeLine.setTimelineRadius(20);//设置下面那些轴的圆点直径
        timeLine.setTimelineWidth(5);//设置时间轴的宽度
        timeLine.setTimelineRadiusDistance(145);//设置时间轴的高度
        timeLine.setMarginTop(100);
        return view;
    }
}
