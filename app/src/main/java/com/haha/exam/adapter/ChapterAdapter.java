package com.haha.exam.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.haha.exam.R;

/**
 * Created by Administrator on 2016/10/20.
 */
public class ChapterAdapter extends BaseAdapter{
    private Context context;
    private LayoutInflater inflater;

    public ChapterAdapter(Context context){
        this.context=context;
        inflater= (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder=null;
        if(viewHolder==null){
            viewHolder=new ViewHolder();
            view=inflater.inflate(R.layout.chapter_list_item,null);
            viewHolder.iv_num= (ImageView) view.findViewById(R.id.chapter_num);
            viewHolder.tv_content= (TextView) view.findViewById(R.id.chapter_num);
            viewHolder.tv_count= (TextView) view.findViewById(R.id.chater_count);
            view.setTag(viewHolder);
        }else {
            viewHolder= (ViewHolder) view.getTag();
        }

        return view;
    }

    private class ViewHolder {
        ImageView iv_num;
        TextView tv_content;
        TextView tv_count;
    }
}
