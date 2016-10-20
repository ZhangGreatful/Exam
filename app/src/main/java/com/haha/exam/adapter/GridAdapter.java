package com.haha.exam.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.haha.exam.R;
import com.haha.exam.bean.SpecialType;

import java.util.List;

/**
 * Created by Administrator on 2016/10/20.
 */
public class GridAdapter extends BaseAdapter {

    private LayoutInflater inflater;
    private SpecialType specialTypes;
    private Context mContext;
    private int[] image={};

    public GridAdapter(Context context, SpecialType specialType) {
        this.mContext = context;
        this.specialTypes = specialType;
        System.out.println("获得的数据是"+specialTypes.getMsg().size());
        inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return specialTypes.getMsg().size();
    }

    @Override
    public Object getItem(int position) {
        return specialTypes.getMsg().get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {

        ViewHolder viewHolder = null;
        if (viewHolder==null){
            viewHolder=new ViewHolder();
            view=inflater.inflate(R.layout.special_gridview_item,null);
            viewHolder.imageView= (ImageView) view.findViewById(R.id.iv_num);
            viewHolder.type= (TextView) view.findViewById(R.id.tv_type);
            viewHolder.count= (TextView) view.findViewById(R.id.tv_num);
            view.setTag(viewHolder);
        }else {
            viewHolder= (ViewHolder) view.getTag();
        }
//            viewHolder.imageView.setImageResource(image[position]);
            viewHolder.type.setText(specialTypes.getMsg().get(position).getName());
            viewHolder.count.setText(specialTypes.getMsg().get(position).getNum());
        return view;
    }

    private class ViewHolder {
        ImageView imageView;
        TextView type;
        TextView count;
    }
}
