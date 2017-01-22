package com.haha.exam.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.haha.exam.R;
import com.haha.exam.bean.VideoInfo;

import java.util.List;

/**
 * Created by Administrator on 2016/11/4.
 */
public class VideoAdapter extends BaseAdapter {
    private LayoutInflater inflater;
    private Context context;
    private List<VideoInfo.DataBean> datas;
    private List<Integer> countList;
    private List<Integer> playedList;

    public VideoAdapter(Context context, List<VideoInfo.DataBean> datas, List<Integer> count, List<Integer> played) {
        this.context = context;
        this.countList = count;
        this.playedList = played;
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
            view = inflater.inflate(R.layout.video_item, null);
            viewHolder.tv_content = (TextView) view.findViewById(R.id.content);
            viewHolder.bottom = view.findViewById(R.id.bottom);
            viewHolder.top = view.findViewById(R.id.top);
            viewHolder.point = (ImageView) view.findViewById(R.id.point);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        if (i == 0) {
            viewHolder.top.setBackgroundColor(context.getResources().getColor(R.color.title_color));
            if (playedList.get(i) == countList.get(i)) {
                viewHolder.point.setImageResource(R.mipmap.yellow_point);
                viewHolder.bottom.setBackgroundColor(context.getResources().getColor(R.color.title_color));
            } else {
                viewHolder.point.setImageResource(R.mipmap.gray_point);
                viewHolder.bottom.setBackgroundColor(context.getResources().getColor(R.color.circle_color));
            }
        } else {
            if (playedList.get(i) == countList.get(i)) {
                viewHolder.top.setBackgroundColor(context.getResources().getColor(R.color.title_color));
                viewHolder.point.setImageResource(R.mipmap.yellow_point);
                viewHolder.bottom.setBackgroundColor(context.getResources().getColor(R.color.title_color));
            } else {
                if (playedList.get(i - 1) == countList.get(i - 1)) {
                    viewHolder.top.setBackgroundColor(context.getResources().getColor(R.color.title_color));
                } else {
                    viewHolder.top.setBackgroundColor(context.getResources().getColor(R.color.circle_color));
                }
                viewHolder.point.setImageResource(R.mipmap.gray_point);
                viewHolder.bottom.setBackgroundColor(context.getResources().getColor(R.color.circle_color));
            }
        }
        viewHolder.tv_content.setText(datas.get(i).getChapter_name());
        if (i == (datas.size() - 1)) {
            viewHolder.bottom.setVisibility(View.INVISIBLE);
        }
        return view;
    }

    private class ViewHolder {
        TextView tv_content;
        View bottom;
        View top;
        ImageView point;
    }
}
