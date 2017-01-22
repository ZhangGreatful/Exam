package com.haha.exam.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.haha.exam.R;
import com.haha.exam.bean.ChapterQuestion;

import java.util.List;

/**
 * Created by Administrator on 2016/10/20.
 */
public class ChapterAdapter extends BaseAdapter {
    private Context context;
    private LayoutInflater inflater;
    private List<ChapterQuestion.MsgBean> datas;
    private int[] image = {R.mipmap.one, R.mipmap.two, R.mipmap.three, R.mipmap.four, R.mipmap.five, R.mipmap.six,
            R.mipmap.seven, R.mipmap.eight, R.mipmap.nine, R.mipmap.ten, R.mipmap.eleven, R.mipmap.twele, R.mipmap.thirteen,
            R.mipmap.fourteen, R.mipmap.fifteen};

    public ChapterAdapter(Context context, List<ChapterQuestion.MsgBean> data) {
        this.datas = data;
        this.context = context;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
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
    public View getView(int position, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder = null;
        if (viewHolder == null) {
            viewHolder = new ViewHolder();
            view = inflater.inflate(R.layout.chapter_list_item, null);
            viewHolder.iv_num = (ImageView) view.findViewById(R.id.chapter_icon);
            viewHolder.tv_content = (TextView) view.findViewById(R.id.chapter_content);
            viewHolder.tv_count = (TextView) view.findViewById(R.id.chater_count);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        viewHolder.iv_num.setImageResource(image[position]);
        viewHolder.tv_count.setText(datas.get(position).getQuestionnum());
        viewHolder.tv_content.setText(datas.get(position).getChaptername());
        return view;
    }

    private class ViewHolder {
        ImageView iv_num;
        TextView tv_content;
        TextView tv_count;
    }
}
