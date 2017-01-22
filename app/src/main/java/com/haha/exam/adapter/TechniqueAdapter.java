package com.haha.exam.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.haha.exam.R;
import com.haha.exam.bean.Technique;

import java.util.List;

/**
 * Created by Administrator on 2016/12/9.
 */

public class TechniqueAdapter extends BaseAdapter {

    private LayoutInflater inflater;
    private Context context;
    private List<Technique.DataBean> datas;
    private int[] image = {R.mipmap.one, R.mipmap.two, R.mipmap.three, R.mipmap.four, R.mipmap.five, R.mipmap.six,
            R.mipmap.seven, R.mipmap.eight, R.mipmap.nine, R.mipmap.ten, R.mipmap.eleven, R.mipmap.twele, R.mipmap.thirteen,
            R.mipmap.fourteen, R.mipmap.fifteen};

    public TechniqueAdapter(Context context, List<Technique.DataBean> datas) {
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
            view = inflater.inflate(R.layout.technique_item, null);
            viewHolder.imageView = (ImageView) view.findViewById(R.id.image);
            viewHolder.tv_content = (TextView) view.findViewById(R.id.content);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        viewHolder.imageView.setImageResource(image[i]);
        viewHolder.tv_content.setText(datas.get(i).getTitle());
        return view;
    }

    private class ViewHolder {
        TextView tv_content;
        ImageView imageView;
    }
}
