package com.haha.exam.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.haha.exam.R;
import com.haha.exam.bean.VideoCollect;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/11/30.
 */

public class VideoCollectAdapter extends BaseAdapter {

    private LayoutInflater inflater;
    private Context context;
    private ArrayList<VideoCollect> datas;

    public VideoCollectAdapter(Context context, ArrayList<VideoCollect> datas) {
        this.context = context;
        this.datas = datas;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return datas.size();
    }

    @Override
    public Object getItem(int i) {
        return datas.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        ViewHolder viewHolder = null;
        if (viewHolder == null) {
            viewHolder = new ViewHolder();
            view = inflater.inflate(R.layout.video_collect_item, null);
            viewHolder.video_thumb = (ImageView) view.findViewById(R.id.video_thumb);
            viewHolder.video_title = (TextView) view.findViewById(R.id.video_title);
            viewHolder.video_length = (TextView) view.findViewById(R.id.video_length);
            viewHolder.show_counts = (TextView) view.findViewById(R.id.show_counts);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        Picasso.with(context).load(datas.get(i).getVideo_thumb()).into(viewHolder.video_thumb);
        viewHolder.video_title.setText((i+1)+". "+datas.get(i).getVideo_title());
        viewHolder.video_length.setText(datas.get(i).getVideo_length());
        viewHolder.show_counts.setText(datas.get(i).getShow_count()+"次");
        return view;
    }

    private class ViewHolder {
        ImageView video_thumb;
        TextView video_length;
        TextView video_title;
        TextView show_counts;
    }
}
