package com.haha.exam.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.haha.exam.R;

import java.util.List;

/**
 * Created by Administrator on 2016/11/7.
 */
public class HorizonListAdapter extends BaseAdapter {
    private LayoutInflater mInflater;
    private List<String> text;
    private List<String> counts;
    private Context context;
    private int selectIndex = -1;

    public HorizonListAdapter(List<String> text, List<String> counts, Context context) {
        this.text = text;
        this.counts = counts;
        this.context = context;
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return text.size();
    }

    @Override
    public Object getItem(int i) {
        return text.get(i);
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
            view = mInflater.inflate(R.layout.video_title_item, null);
            viewHolder.title = (TextView) view.findViewById(R.id.content);
            viewHolder.bo_fang = (ImageView) view.findViewById(R.id.bo_fang);
            viewHolder.tv_bofang = (TextView) view.findViewById(R.id.count);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        viewHolder.title.setText(text.get(i));
        if (i == selectIndex) {
            view.setSelected(true);
            viewHolder.tv_bofang.setText("正在播放");
        } else {
            view.setSelected(false);
            viewHolder.tv_bofang.setText(counts.get(i));
        }

        return view;
    }

    private class ViewHolder {
        TextView title;
        ImageView bo_fang;
        TextView tv_bofang;
    }

    public void setSelectIndex(int i) {
        selectIndex = i;
    }
}
