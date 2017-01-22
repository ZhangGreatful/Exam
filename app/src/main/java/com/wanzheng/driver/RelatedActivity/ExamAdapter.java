package com.wanzheng.driver.RelatedActivity;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.haha.exam.R;
import com.haha.exam.bean.Practice;

import java.util.List;

/**
 * Created by Administrator on 2017/1/10.
 */

public class ExamAdapter extends BaseAdapter {
    private Context context;
    private List<Practice.MsgBean.ReturnDataBean> orderList;

    private final int TYPE_COUNT = 3;
    private final int eAccompany_TYPE = 2;

    public ExamAdapter(Context context, List<Practice.MsgBean.ReturnDataBean> orderList) {
        super();
        this.context = context;
        this.orderList = orderList;
    }

    @Override
    public int getCount() {
        return orderList.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getViewTypeCount() {
        return TYPE_COUNT;
    }

    public void AddFooterItem(List<Practice.MsgBean.ReturnDataBean> dataBeen) {
        orderList.addAll(dataBeen);
        notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {

        return eAccompany_TYPE;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup arg2) {
        // 获取到当前位置所对应的Type
        final Practice.MsgBean.ReturnDataBean order = orderList.get(position);

        View eDriverView = null;
        View eStudyView = null;
        View eAccompanyView = null;
        // 获取到当前位置所对应的Type

        eStudyView = convertView;
        ExamAdapter.PstudyViewHolder viewHolder = null;
        if (eStudyView == null) {
            eStudyView = LayoutInflater.from(context).inflate(
                    R.layout.practical_period_item, null);
            viewHolder = new ExamAdapter.PstudyViewHolder();

            viewHolder.practice_order = (TextView) eStudyView.findViewById(R.id.period_num);
            viewHolder.practice_time = (TextView) eStudyView.findViewById(R.id.practice_time);
            viewHolder.practice_picshow = (LinearLayout) eStudyView.findViewById(R.id.practice_picshow);
            viewHolder.exam_result = (TextView) eStudyView.findViewById(R.id.exam_result);
            viewHolder.practice_usetime = (TextView) eStudyView.findViewById(R.id.practice_usetime);
            viewHolder.assess = (TextView) eStudyView.findViewById(R.id.assess);
            viewHolder.remark = (TextView) eStudyView.findViewById(R.id.remark);

            viewHolder.exam = (RelativeLayout) eStudyView.findViewById(R.id.ll_exam);
            viewHolder.practice = (LinearLayout) eStudyView.findViewById(R.id.ll_practice);
            eStudyView.setTag(viewHolder);
        } else {
            viewHolder = (ExamAdapter.PstudyViewHolder) eStudyView.getTag();
        }
        viewHolder.exam.setVisibility(View.VISIBLE);
        viewHolder.practice.setVisibility(View.GONE);
        String time = order.getEnd_time() != null ? order.getEnd_time() : "";
        if (time.length() > 11) {
            viewHolder.practice_time.setText(order.getStart_time() + "—" + order.getEnd_time().substring(11));
        }
        viewHolder.practice_order.setText(order.getId());
        viewHolder.exam_result.setText(order.getScore() + "分");
        int score = Integer.valueOf(order.getScore());
        if (score < 90) {
            viewHolder.assess.setText("马路杀手");
        } else {
            viewHolder.assess.setText("考试大神");
        }
        int time1 = Integer.valueOf(order.getUse_time());
        int minute = time1 / 60;
        int second = time1 % 60;
        String str;

        if (minute < 10) {

            if (second < 10) {
                str = "0" + minute + "分" + "0" + second + "秒";
            } else {
                str = "0" + minute + "分" + second + "秒";
            }
        } else {
            if (second < 10) {
                str = minute + "分" + "0" + second + "秒";
            } else {
                str = minute + "分" + second + "秒";
            }
        }
        viewHolder.practice_usetime.setText(str);
        viewHolder.remark.setText(order.getRemark());
        viewHolder.practice_picshow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putExtra("xueshiid", order.getId() + "");
                intent.setClass(context, ShowPicActivity.class);
                context.startActivity(intent);
            }
        });
        convertView = eStudyView;
        return convertView;
    }

    class PstudyViewHolder {
        TextView practice_time;
        TextView practice_order;
        TextView exam_result;
        TextView practice_usetime;
        TextView assess;
        TextView remark;
        LinearLayout practice_picshow;
        LinearLayout practice;
        RelativeLayout exam;
    }
}
