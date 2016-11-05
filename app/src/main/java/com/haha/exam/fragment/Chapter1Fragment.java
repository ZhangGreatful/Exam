package com.haha.exam.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.haha.exam.R;
import com.haha.exam.adapter.MyListViewAdapter1;
import com.haha.exam.adapter.MyListViewAdapter2;
import com.haha.exam.adapter.VideoAdapter;
import com.haha.exam.view.TimeLineView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/11/5.
 */
public class Chapter1Fragment extends Fragment {

    private int selectIndex=0;

    private ListView mListView1, mListView2;
    private MyListViewAdapter1 adapter1;
    private MyListViewAdapter2 adapter2;
    private List<String> datas;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.chapter_1_fragment, container, false);
        datas = new ArrayList<>();
        datas.add("第一节");
        datas.add("第二节");
        datas.add("第三节");
        datas.add("第四节");
        datas.add("第五节");

        mListView1 = (ListView) view.findViewById(R.id.list_item_1);
        mListView2 = (ListView) view.findViewById(R.id.list_item_2);

        adapter1 = new MyListViewAdapter1(datas, getActivity(), selectIndex);
        adapter2 = new MyListViewAdapter2(datas, getActivity(), selectIndex);
        mListView1.setAdapter(adapter1);
        mListView2.setAdapter(adapter2);

        mListView1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                selectIndex = position;
                //把下标传过去，然后刷新adapter
                adapter1.setIndex(position);
                adapter1.notifyDataSetChanged();
                //当点击某个item的时候让这个item自动滑动到listview的顶部(下面item够多，如果点击的是最后一个就不能到达顶部了)
                mListView1.smoothScrollToPositionFromTop(position, 0);

                adapter2.setIndex(position);
                mListView2.setAdapter(adapter2);
            }
        });

        mListView2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
               Toast.makeText(getActivity(),"你点击了"+position,Toast.LENGTH_SHORT).show();
            }
        });
        return view;
    }
}
