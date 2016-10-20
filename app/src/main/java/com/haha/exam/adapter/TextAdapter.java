package com.haha.exam.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.haha.exam.R;

import java.util.ArrayList;
import java.util.List;

/**
 *选择题的适配器
 * Created by Administrator on 2016/10/20.
 */
public class TextAdapter extends BaseAdapter {

    private int[] image = {};
    private LayoutInflater inflater;
    private List<String> list = new ArrayList<>();

    public TextAdapter(Context context, List<String> mList) {
        this.list = mList;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder = null;
        if (viewHolder == null) {
            view = inflater.inflate(R.layout.text_list_item, null);
            viewHolder = new ViewHolder();
            viewHolder.imageView = (ImageView) view.findViewById(R.id.choice_icon);
            viewHolder.textView = (TextView) view.findViewById(R.id.choic_content);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        if (position == 0 || position == 2) {
            view.setBackgroundResource(R.color.mine_head_background);
        }
        viewHolder.imageView.setImageResource(image[position]);
        viewHolder.textView.setText(list.get(position));
        return view;
    }

    private class ViewHolder {
        ImageView imageView;
        TextView textView;
    }
}
