package com.haha.exam.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.haha.exam.R;

/**
 * Created by Administrator on 2016/10/29.
 */
public class GradeAdapter extends BaseAdapter {
    private Context context;
    private LayoutInflater inflater;

    public GradeAdapter(Context context) {
        this.context = context;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        ViewHolder viewHolder=null;
        if (viewHolder==null){
            view=inflater.inflate(R.layout.grade_item,null);
        }
        return null;
    }
    private class ViewHolder {
        ImageView iv_num;
        TextView tv_content;
        TextView tv_count;
    }
}
