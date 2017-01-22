package com.haha.exam.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.haha.exam.R;
import com.haha.exam.bean.AllQuestions;
import com.haha.exam.bean.AllSchool;

import java.util.List;

/**
 * Created by Administrator on 2016/11/18.
 */
public class SchoolAdapter extends BaseAdapter {
    private List<AllSchool.DataBean> list;
    private LayoutInflater inflater;
    private Context context;

    public SchoolAdapter(Context context, List<AllSchool.DataBean> list) {
        this.context = context;
        this.list = list;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
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
            view = inflater.inflate(R.layout.school_item, null);
            viewHolder.school_name = (TextView) view.findViewById(R.id.school_name);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        viewHolder.school_name.setText(list.get(i).getSchoolname());
        return view;
    }

    private class ViewHolder {
        TextView school_name;
    }
}
