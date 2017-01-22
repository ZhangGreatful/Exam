package com.haha.exam.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.haha.exam.R;
import com.haha.exam.bean.MyGrade;

/**
 * Created by Administrator on 2016/10/29.
 */
public class GradeAdapter extends BaseAdapter {
    private Context context;
    private LayoutInflater inflater;
    private MyGrade grade;

    public GradeAdapter(Context context, MyGrade grade) {
        this.grade = grade;
        this.context = context;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return grade.getData().size();
    }

    @Override
    public Object getItem(int i) {
        return grade.getData().get(i);
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
            view = inflater.inflate(R.layout.grade_item, null);

            viewHolder.xu_hao = (TextView) view.findViewById(R.id.xu_hao);
            viewHolder.date = (TextView) view.findViewById(R.id.ri_qi);
            viewHolder.time = (TextView) view.findViewById(R.id.shi_jian);
            viewHolder.grade = (TextView) view.findViewById(R.id.fen_shu);
            viewHolder.jie_guo = (TextView) view.findViewById(R.id.jie_guo);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        viewHolder.xu_hao.setText(String.valueOf(i + 1) + ".");

        String date = grade.getData().get(i).getBegin_time().substring(5, 16);
        String string = grade.getData().get(i).getTime() != null ? grade.getData().get(i).getTime() : "";
        int time = 0;
        if (!string.equals("")) {
            time = Integer.parseInt(string);
        }

        int minute = time / 60;
        int second = time % 60;
        String str;

        if (minute < 10) {

            if (second < 10) {
                str = "0" + minute + ":" + "0" + second;
            } else {
                str = "0" + minute + ":" + second;
            }
        } else {
            if (second < 10) {
                str = minute + ":" + "0" + second;
            } else {
                str = minute + ":" + second;
            }
        }


        viewHolder.date.setText(date);
        viewHolder.time.setText(str);
        String string1 = grade.getData().get(i).getScore() != null ? grade.getData().get(i).getScore() : "";
        if (!string1.equals("")) {
            viewHolder.grade.setText(grade.getData().get(i).getScore()+"分");
            if (Integer.valueOf(grade.getData().get(i).getScore()) > 90) {
                viewHolder.jie_guo.setText("恭喜通过");
            } else {
                viewHolder.jie_guo.setText("马路杀手");
            }
        }


        return view;
    }

    private class ViewHolder {
        TextView xu_hao;
        TextView date;
        TextView time;
        TextView grade;
        TextView jie_guo;
    }
}
