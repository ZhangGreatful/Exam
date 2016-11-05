package com.haha.exam.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.haha.exam.R;

import java.util.List;


/**
 * Created by a on 2016/5/13.
 */
public class MyListViewAdapter2 extends BaseAdapter {
    private List<String> datas;
    private Context context;
    private int selectIndex;

    public MyListViewAdapter2(List<String> datas, Context context, int selectIndex) {
        this.datas = datas;
        this.context = context;
        this.selectIndex = selectIndex;
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
        ViewHolder vh;
        if (convertView == null) {
            convertView = View.inflate(context, R.layout.item_listview_2, null);
            vh = new ViewHolder();
            vh.content = (TextView) convertView.findViewById(R.id.content);
            vh.time = (TextView) convertView.findViewById(R.id.view_time);
            vh.title = (TextView) convertView.findViewById(R.id.title);
            vh.video_list= (LinearLayout) convertView.findViewById(R.id.video_list);
            vh.space= (LinearLayout) convertView.findViewById(R.id.space);
            convertView.setTag(vh);
        } else {
            vh = (ViewHolder) convertView.getTag();
        }
        if (position==0){
            vh.title.setText(datas.get(position));
            vh.video_list.setVisibility(View.GONE);
            vh.space.setVisibility(View.GONE);
        }else {
            vh.title.setVisibility(View.GONE);
            vh.video_list.setVisibility(View.VISIBLE);
            vh.content.setText(datas.get(position));
            vh.space.setVisibility(View.VISIBLE);
        }


        return convertView;
    }

    public void setIndex(int index) {
        selectIndex = index;
    }

    class ViewHolder {
        LinearLayout video_list,space;
        TextView content;
        TextView time;
        TextView title;
    }
}
