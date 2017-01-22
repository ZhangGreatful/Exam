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

/**
 * Created by shaowei on 2016/12/14.
 *
 */

public class AppointFragList1Adapter extends BaseAdapter {
    private ArrayList<String[]> bookshelfList = null;
    private Context context       = null;
    private LayoutInflater inflater;
    public AppointFragList1Adapter(ArrayList<String[]> bookshelfList,Context context){
    this.bookshelfList=bookshelfList;
        this.context=context;
        inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return bookshelfList.size();
    }

    @Override
    public Object getItem(int position) {
        return bookshelfList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder=null;
        if(holder==null){
            convertView=inflater.inflate(R.layout.appointment_list1_item,null);
            holder=new ViewHolder();
            holder.mImageView= (ImageView) convertView.findViewById(R.id.appoint_listview1_image);
            holder.name= (TextView) convertView.findViewById(R.id.appoint_listview1_item_name);
            holder.location= (TextView) convertView.findViewById(R.id.appoint_listview1_item_location);
            holder.price= (TextView) convertView.findViewById(R.id.appoint_listview1_item_price);
            convertView.setTag(holder);
        }else{
            holder= (ViewHolder) convertView.getTag();
        }
        holder.name.setText(bookshelfList.get(position)[0]);
        holder.price.setText(bookshelfList.get(position)[2]);
        holder.location.setText(bookshelfList.get(position)[1]);
        return convertView;
    }
    class ViewHolder{
        ImageView mImageView;
        TextView name;
        TextView price;
        TextView location;

    }
}
