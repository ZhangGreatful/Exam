package com.haha.exam.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.haha.exam.R;
import com.haha.exam.bean.ErrorList;

import java.util.List;

/**
 * 我的错题题目类型列表
 *
 * Created by Administrator on 2016/10/29.
 */
public class ErrorAdapter extends BaseAdapter {

    private LayoutInflater inflater;
    private Context context;
    private List<ErrorList.DataBean> datas;
    private int[] image = {R.mipmap.one, R.mipmap.two, R.mipmap.three, R.mipmap.four, R.mipmap.five, R.mipmap.six,
            R.mipmap.seven, R.mipmap.eight, R.mipmap.nine, R.mipmap.ten, R.mipmap.eleven, R.mipmap.twele, R.mipmap.thirteen,
            R.mipmap.fourteen, R.mipmap.fifteen};

    public ErrorAdapter(Context context,List<ErrorList.DataBean> datas) {
        this.context = context;
        this.datas=datas;
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
    public View getView(int position, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder = null;
        if (viewHolder == null) {
            viewHolder = new ViewHolder();
            view = inflater.inflate(R.layout.error_list_item, null);
            viewHolder.iv_num = (ImageView) view.findViewById(R.id.bian_hao);
            viewHolder.tv_content = (TextView) view.findViewById(R.id.nei_rong);
            viewHolder.tv_count = (TextView) view.findViewById(R.id.shu_liang);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        viewHolder.iv_num.setImageResource(image[position]);
        viewHolder.tv_content.setText(datas.get(position).getChaptername());
        viewHolder.tv_count.setText(datas.get(position).getErrorcount()+"题");
        return view;
    }

    private class ViewHolder {
        ImageView iv_num;
        TextView tv_content;
        TextView tv_count;
    }
}
