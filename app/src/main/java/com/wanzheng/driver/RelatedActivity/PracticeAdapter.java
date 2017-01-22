package com.wanzheng.driver.RelatedActivity;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.haha.exam.R;
import com.haha.exam.bean.Practice;

import java.util.List;

/**
 * Created by Administrator on 2017/1/9.
 */

public class PracticeAdapter extends BaseAdapter {
    private Context context;
    private List<Practice.MsgBean.ReturnDataBean> orderList;

    private final int TYPE_COUNT = 3;
    private final int eAccompany_TYPE = 2;

    public PracticeAdapter(Context context, List<Practice.MsgBean.ReturnDataBean> orderList) {
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

    @Override
    public int getItemViewType(int position) {

        return eAccompany_TYPE;
    }

    public void AddFooterItem(List<Practice.MsgBean.ReturnDataBean> dataBeen) {
        orderList.addAll(dataBeen);
        notifyDataSetChanged();
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
        PracticeAdapter.PstudyViewHolder viewHolder = null;
        if (eStudyView == null) {
            eStudyView = LayoutInflater.from(context).inflate(
                    R.layout.practical_period_item, null);
            viewHolder = new PracticeAdapter.PstudyViewHolder();

            viewHolder.practice_order = (TextView) eStudyView.findViewById(R.id.period_num);
            viewHolder.practice_time = (TextView) eStudyView.findViewById(R.id.practice_time);
            viewHolder.practice_picshow = (LinearLayout) eStudyView.findViewById(R.id.practice_picshow);
            viewHolder.practice_result = (TextView) eStudyView.findViewById(R.id.practice_result);
            viewHolder.practice_usetime = (TextView) eStudyView.findViewById(R.id.practice_usetime);
            viewHolder.remark = (TextView) eStudyView.findViewById(R.id.remark);
            eStudyView.setTag(viewHolder);
        } else {
            viewHolder = (PracticeAdapter.PstudyViewHolder) eStudyView.getTag();
        }
        String time = order.getEnd_time() != null ? order.getEnd_time() : "";
        if (time.length() > 11) {
            viewHolder.practice_time.setText(order.getStart_time() + "—" + order.getEnd_time().substring(11));
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
        viewHolder.practice_order.setText(order.getId());
        viewHolder.practice_result.setText(order.getTitle());
        Log.d("PracticeAdapter", "title=============" + order.getTitle());
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
        TextView practice_result;
        TextView practice_usetime;
        TextView remark;
        LinearLayout practice_picshow;
    }
}
