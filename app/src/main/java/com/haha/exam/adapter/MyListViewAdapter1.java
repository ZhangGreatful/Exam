package com.haha.exam.adapter;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.haha.exam.R;

import java.util.List;


/**
 * 左侧的listview
 * Created by a on 2016/5/12.
 */
public class MyListViewAdapter1 extends BaseAdapter {

    private List<String> datas;
    private List<Integer> countList;
    private List<Integer> playedList;
    private final Context context;
    private int selectIndex;

    public MyListViewAdapter1(List<String> data, List<Integer> countList, List<Integer> playedList, Context context, int selectIndex) {
        this.datas = data;
        this.context = context;
        this.selectIndex = selectIndex;
        this.countList = countList;
        this.playedList = playedList;
    }

    @Override
    public int getCount() {
        return datas.size();
    }

    @Override
    public Object getItem(int position) {
        return datas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder vh = null;
        if (convertView == null) {
            convertView = View.inflate(context, R.layout.item_listview_1, null);
            vh = new ViewHolder();
            vh.tv = (TextView) convertView.findViewById(R.id.textview);
            vh.relativeLayout = (RelativeLayout) convertView.findViewById(R.id.text);
            vh.progressBar = (ProgressBar) convertView.findViewById(R.id.progressBar);
            vh.bottom_line = convertView.findViewById(R.id.bottom_line);
            convertView.setTag(vh);
        } else {
            vh = (ViewHolder) convertView.getTag();
        }

        if (position == selectIndex) {
            vh.relativeLayout.setBackgroundColor(Color.parseColor("#f5f5f5"));
            vh.tv.setTextColor(Color.parseColor("#f19228"));
        } else {
            if (countList.get(position)==playedList.get(position)){
                vh.tv.setTextColor(Color.parseColor("#000000"));
            }else {
                vh.tv.setTextColor(Color.parseColor("#bbbbbb"));
            }

            vh.relativeLayout.setBackgroundColor(Color.parseColor("#ffffff"));
        }
        Log.d("MyListViewAdapter1", "countList=========" + countList.get(position));
        Log.d("MyListViewAdapter1", "playList===========" + playedList.get(position));
        if (playedList.get(position) != 0) {
            vh.progressBar.setVisibility(View.VISIBLE);
            vh.bottom_line.setVisibility(View.GONE);
            vh.progressBar.setMax(countList.get(position));
            vh.progressBar.setProgress(playedList.get(position));

        } else {
            vh.progressBar.setVisibility(View.GONE);
            vh.bottom_line.setVisibility(View.VISIBLE);
        }
        vh.tv.setText(datas.get(position));
        return convertView;
    }

    public void setIndex(int index) {
        selectIndex = index;
    }

    class ViewHolder {
        TextView tv;
        RelativeLayout relativeLayout;
        ProgressBar progressBar;
        View bottom_line;
    }
}
