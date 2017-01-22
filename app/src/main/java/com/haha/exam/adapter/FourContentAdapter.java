package com.haha.exam.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.haha.exam.R;
import com.haha.exam.activity.ExaminationDetailActivity;
import com.haha.exam.bean.Four;
import com.haha.exam.view.MyImgeview;
import com.haha.exam.view.MyListViewItem;
import com.squareup.picasso.Picasso;
import com.umeng.socialize.utils.Log;

import java.util.ArrayList;

public class FourContentAdapter extends BaseAdapter {
    private ArrayList<Four> mList;
    private LayoutInflater inflater;
    private Context mContext;

    public FourContentAdapter(Context context, ArrayList<Four> list) {
        inflater = LayoutInflater.from(context);
        this.mList = list;
        mContext = context;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return mList != null ? mList.size() : 0;
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return mList != null ? mList.get(position) : null;
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            // convertView = inflater.inflate(R.layout.four_item, null);
            convertView = new MyListViewItem(mContext);
            holder.title = (TextView) ((MyListViewItem) convertView)
                    .findView(R.id.title);
            holder.counts = (TextView) ((MyListViewItem) convertView)
                    .findView(R.id.counts);
            holder.name = (TextView) ((MyListViewItem) convertView)
                    .findView(R.id.name);
            holder.iv = (MyImgeview) ((MyListViewItem) convertView)
                    .findView(R.id.iv_logo);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        ((MyListViewItem) convertView).setPosition(position);
        ((MyListViewItem) convertView).setFour(mList.get(position));
        ((MyListViewItem) convertView).setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Intent intent2 = new Intent(mContext,
                        ExaminationDetailActivity.class);
                //Four four = parseEsoterica.get(position - 1);
                intent2.putExtra("DriveHead", mList.get(position));
                intent2.putExtra("position", position);
                // startActivity(intent2);
                ((Activity) mContext).startActivityForResult(intent2, 100);
            }

        });
        Four four = mList.get(position);
        holder.title.setText(four.getBiaoti());
        holder.name.setText(four.getZuozhe());
        holder.counts.setText(four.getLiulanliang());

        Log.e("tag", "秘笈秘笈秘笈秘笈秘笈秘笈秘笈秘笈秘笈秘笈秘笈秘笈秘笈秘笈秘笈秘笈秘笈" + four.getLogo());
        if (four.getLogo().contains("jpg")) {
//			holder.iv.setImageUrl(mList.get(position).getLogo(), InitVolley
//					.getInstance().getImageLoader());

            Picasso.with(mContext).load(mList.get(position).getLogo()).into(holder.iv);
        }
        return convertView;
    }

    class ViewHolder {
        TextView title, counts, name;
        MyImgeview iv;
    }

}
