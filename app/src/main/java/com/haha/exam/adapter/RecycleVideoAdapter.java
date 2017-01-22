package com.haha.exam.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.haha.exam.R;

import java.util.List;

/**
 * Created by Administrator on 2016/12/16.
 */

public class RecycleVideoAdapter extends RecyclerView.Adapter<RecycleVideoAdapter.ViewHolder> implements View.OnClickListener {
    private LayoutInflater mInflater;
    private List<String> text;
    private List<String> counts;
    private Context context;
    private int selectIndex = -1;
    private OnRecyclerViewItemClickListener mOnItemClickListener = null;

    public RecycleVideoAdapter(List<String> text, List<String> counts, Context context) {
        mInflater = LayoutInflater.from(context);
        this.text = text;
        this.counts = counts;
        this.context = context;
    }
    //define interface
    public static interface OnRecyclerViewItemClickListener {
        void onItemClick(View view , int position);
    }
    public void setOnItemClickListener(OnRecyclerViewItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(View arg0) {
            super(arg0);
        }

        TextView title;
        ImageView bo_fang;
        TextView tv_bofang;
    }

    @Override
    public int getItemCount() {
        return text.size();
    }

    /**
     * 创建ViewHolder
     */
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = mInflater.inflate(R.layout.video_title_item,
                viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(view);

        viewHolder.title = (TextView) view.findViewById(R.id.content);
        viewHolder.bo_fang = (ImageView) view.findViewById(R.id.bo_fang);
        viewHolder.tv_bofang = (TextView) view.findViewById(R.id.count);
        view.setOnClickListener(this);
        return viewHolder;
    }

    @Override
    public void onClick(View v) {
        if (mOnItemClickListener != null) {
            //注意这里使用getTag方法获取数据
            mOnItemClickListener.onItemClick(v, (Integer) v.getTag());
        }
    }

    /**
     * 设置值
     */
    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, final int i) {
//        viewHolder.mImg.setImageResource(mDatas.get(i));
        viewHolder.title.setText(text.get(i));
        if (i == selectIndex) {
            viewHolder.itemView.setSelected(true);
            viewHolder.tv_bofang.setText("正在播放");
        } else {
            viewHolder.itemView.setSelected(false);
            viewHolder.tv_bofang.setText(counts.get(i));
        }
        //将数据保存在itemView的Tag中，以便点击时进行获取
        viewHolder.itemView.setTag(i);
    }
    public void setSelectIndex(int i) {
        selectIndex = i;
    }
}
