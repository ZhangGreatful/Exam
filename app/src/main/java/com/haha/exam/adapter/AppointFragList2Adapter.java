package com.haha.exam.adapter;

import android.content.Context;
import android.graphics.Paint;
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

public class AppointFragList2Adapter extends BaseAdapter {
    private ArrayList<String[]> bookshelfList = null;
    private Context context       = null;
    private LayoutInflater inflater;
    public AppointFragList2Adapter(ArrayList<String[]> bookshelfList, Context context){
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
            holder=new ViewHolder();
            convertView= inflater.inflate(R.layout.appointment_list2_item,null);
            holder.mImageView= (ImageView) convertView.findViewById(R.id.appoint_listview2_image);
            holder.username= (TextView) convertView.findViewById(R.id.appoint_haoping_username);
            holder.serviceTime= (TextView) convertView.findViewById(R.id.appointment_list2_servicecount);
            holder.schoolname= (TextView) convertView.findViewById(R.id.appointment_list2_schollname);
            holder.tecAge= (TextView) convertView.findViewById(R.id.appointment_list2_techage);
            holder.nowprice= (TextView) convertView.findViewById(R.id.appointment_list2_pricenow);
            holder.oldprice= (TextView) convertView.findViewById(R.id.appointment_list2_pricebef);
            holder.distance= (TextView) convertView.findViewById(R.id.appointment_list2_distance);
            convertView.setTag(holder);
        }else{
            holder= (ViewHolder) convertView.getTag();
        }
//        holder.name.setText(bookshelfList.get(position)[0]);
//        holder.price.setText(bookshelfList.get(position)[2]);
//        holder.location.setText(bookshelfList.get(position)[1]);
        holder.username.setText(bookshelfList.get(position)[0]);
        holder.schoolname.setText(bookshelfList.get(position)[1]);
        holder.serviceTime.setText(bookshelfList.get(position)[2]);
        holder.nowprice.setText(bookshelfList.get(position)[4]);
        holder.oldprice.setText(bookshelfList.get(position)[5]);
        holder.oldprice.setPaintFlags(holder.oldprice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        holder.distance.setText(bookshelfList.get(position)[6]);
        holder.tecAge.setText(bookshelfList.get(position)[3]);
        return convertView;
    }
    class ViewHolder{
        ImageView mImageView;
        TextView username;
        TextView schoolname;
        TextView serviceTime;
        TextView tecAge;
        TextView nowprice;
        TextView oldprice;
        TextView distance;

    }
}
