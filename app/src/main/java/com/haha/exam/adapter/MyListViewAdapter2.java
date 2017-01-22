package com.haha.exam.adapter;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.haha.exam.R;
import com.haha.exam.bean.VideoInfo;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.List;


/**
 * Created by a on 2016/5/13.
 */
public class MyListViewAdapter2 extends BaseAdapter {
    private List<VideoInfo.DataBean> datas;
    private Context context;
    private int selectIndex;

    public MyListViewAdapter2(List<VideoInfo.DataBean> datas, Context context, int selectIndex) {
        this.datas = datas;
        this.context = context;
        this.selectIndex = selectIndex;
    }

    @Override
    public int getCount() {
        return datas.get(selectIndex).getVideoinfo().size();
    }

    @Override
    public Object getItem(int position) {
        return datas.get(selectIndex).getVideoinfo().get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder vh;
        if (convertView == null) {
            convertView = View.inflate(context, R.layout.item_listview_2, null);
            vh = new ViewHolder();
            vh.content = (TextView) convertView.findViewById(R.id.content);
            vh.time = (TextView) convertView.findViewById(R.id.view_time);
            vh.video_list = (LinearLayout) convertView.findViewById(R.id.video_list);
            vh.space = (LinearLayout) convertView.findViewById(R.id.space);
            vh.xuhao = (TextView) convertView.findViewById(R.id.xu_hao);
            vh.pic = (ImageView) convertView.findViewById(R.id.pic);
            vh.looked = (TextView) convertView.findViewById(R.id.looked);
            vh.progressBar = (ProgressBar) convertView.findViewById(R.id.progressBar5);
            convertView.setTag(vh);
        } else {
            vh = (ViewHolder) convertView.getTag();
        }
        vh.video_list.setVisibility(View.VISIBLE);
        vh.xuhao.setText("" + (selectIndex + 1) + "." + (position + 1));
        vh.content.setText(datas.get(selectIndex).getVideoinfo().get(position).getVideo_title());

        Picasso.with(context).load(datas.get(selectIndex).getVideoinfo().get(position).getVideo_thumb()).memoryPolicy(MemoryPolicy.NO_CACHE,MemoryPolicy.NO_STORE).into(vh.pic);

        vh.time.setText(datas.get(selectIndex).getVideoinfo().get(position).getVideo_length().substring(3));
        vh.space.setVisibility(View.VISIBLE);

        if (datas.get(selectIndex).getVideoinfo().get(position).getPlayflag().equals("0")) {//未播放过
            vh.progressBar.setVisibility(View.GONE);
            vh.looked.setText("未观看");
            vh.looked.setTextColor(context.getResources().getColor(R.color.title_color));
        } else {//播放过
            if (datas.get(selectIndex).getVideoinfo().get(position).getEnd_status() == 1) {//播放结束
                vh.progressBar.setVisibility(View.GONE);
                vh.looked.setText("已看完");
                vh.looked.setTextColor(context.getResources().getColor(R.color.text_unselect_color));
            } else {
                vh.progressBar.setVisibility(View.VISIBLE);
                int time = getChronometerSeconds(datas.get(selectIndex).getVideoinfo().get(position).getVideo_length());
                int currentTime = datas.get(selectIndex).getVideoinfo().get(position).getPlayposition();

                double percent = (double) currentTime / (double) time;
                DecimalFormat fnum = new DecimalFormat("##0.00");
                double dd = Float.parseFloat(fnum.format(percent)) * 100;
                String string = String.valueOf(Double.valueOf(dd));
                String str = "";
                Log.d("MyListViewAdapter2", "str==========" + str+"time====="+time+"currenttime===="+currentTime
                        +"dd========"+dd+"string========="+string);
                if (string.length() > 4) {
                    str = string.substring(0,4);
                    Log.d("MyListViewAdapter2", "str==========" + str);

                    vh.looked.setText("已观看至" + str + "%");
                } else {
                    Log.d("MyListViewAdapter2", "string1==========" + string);
                    vh.looked.setText("已观看至" + string + "%");
                }

                vh.looked.setTextColor(context.getResources().getColor(R.color.text_unselect_color));
                vh.progressBar.setMax(time);
                vh.progressBar.setProgress(currentTime);
//                vh.progressBar.setProgress(currentTime);
                //计算位置--------------------------------------------------------------
//                int progress= (int) (dd*100);

            }
        }

        return convertView;
    }

    public void setIndex(int index) {
        selectIndex = index;
        Log.d("MyListViewAdapter2", "selectIndex=========" + selectIndex);
    }

    class ViewHolder {
        LinearLayout video_list, space;
        TextView content, looked;
        TextView time;
        TextView xuhao;
        ImageView pic;
        ProgressBar progressBar;
    }

    /**
     * @return 小时+分钟+秒数  的所有秒数
     */
    public int getChronometerSeconds(String string) {
        int totalss = 0;
        if (string.length() == 8) {

            String[] split = string.split(":");
            String string2 = split[0];
            int hour = Integer.parseInt(string2);
            int Hours = hour * 3600;
            String string3 = split[1];
            int min = Integer.parseInt(string3);
            int Mins = min * 60;
            int SS = Integer.parseInt(split[2]);
            totalss = Hours + Mins + SS;
            return totalss;
        } else if (string.length() == 5) {

            String[] split = string.split(":");
            String string3 = split[0];
            int min = Integer.parseInt(string3);
            int Mins = min * 60;
            int SS = Integer.parseInt(split[1]);

            totalss = Mins + SS;
            return totalss;
        }
        System.out.println("908098080======" + totalss);
        return totalss;
    }
}
