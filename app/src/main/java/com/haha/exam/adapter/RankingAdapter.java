package com.haha.exam.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.haha.exam.R;
import com.haha.exam.bean.Ranking;
import com.haha.exam.view.circleimageview.CircleImageView;
import com.squareup.picasso.Picasso;

import java.util.List;

import static com.haha.exam.R.id.minite;

/**
 * Created by Administrator on 2016/11/8.
 */
public class RankingAdapter extends BaseAdapter {

    private List<Ranking.MsgBean> data;
    private LayoutInflater inflater;
    private Context context;

    public RankingAdapter(Context context, List<Ranking.MsgBean> data) {
        this.context = context;
        this.data = data;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int i) {
        return data.get(i);
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
            view = inflater.inflate(R.layout.ranking_item, null);
            viewHolder.iv_diyi = (ImageView) view.findViewById(R.id.iv_diyi);
            viewHolder.iv_header = (CircleImageView) view.findViewById(R.id.iv_header);
            viewHolder.tv_name = (TextView) view.findViewById(R.id.tv_name);
            viewHolder.tv_grade = (TextView) view.findViewById(R.id.grade);
            viewHolder.tv_minite = (TextView) view.findViewById(minite);
            viewHolder.tv_second = (TextView) view.findViewById(R.id.second);
            viewHolder.tv_xuhao = (TextView) view.findViewById(R.id.xu_hao);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        if (i == 0) {
            viewHolder.iv_diyi.setImageResource(R.mipmap.diyi);
        } else if (i == 1) {
            viewHolder.iv_diyi.setImageResource(R.mipmap.dier);
        } else if (i == 2) {
            viewHolder.iv_diyi.setImageResource(R.mipmap.disan);
        } else {
            viewHolder.iv_diyi.setVisibility(View.GONE);
            viewHolder.tv_xuhao.setVisibility(View.VISIBLE);
            viewHolder.tv_xuhao.setText(String.valueOf(i + 1));
        }
        viewHolder.tv_name.setText(data.get(i).getStudentname());
        viewHolder.tv_grade.setText(data.get(i).getStudentscore());
        String time = data.get(i).getUse_time()!=null?data.get(i).getUse_time():"";//测试时，时间、分数返回的数
        // 据都为null
        if (!time.equals("")){
            int minite = Integer.valueOf(time) / 60;
            int second = Integer.valueOf(time) % 60;
            viewHolder.tv_minite.setText(String.valueOf(minite));
            viewHolder.tv_second.setText(String.valueOf(second));
        }
        String headPic=data.get(i).getHeadpic()!=null?data.get(i).getHeadpic():"";
        if (!headPic.equals("")){
            Picasso.with(context).load(headPic).into(viewHolder.iv_header);
        }

        return view;
    }

    private class ViewHolder {

        TextView tv_xuhao;
        ImageView iv_diyi;
        CircleImageView iv_header;
        TextView tv_name;
        TextView tv_grade;
        TextView tv_minite;
        TextView tv_second;
    }
}
