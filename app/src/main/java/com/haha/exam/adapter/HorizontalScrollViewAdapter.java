package com.haha.exam.adapter;

import java.util.List;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.haha.exam.R;

public class HorizontalScrollViewAdapter {

    private Context mContext;
    private LayoutInflater mInflater;
    private List<String> mDatas;

    public HorizontalScrollViewAdapter(Context context, List<String> mDatas) {
        this.mContext = context;
        mInflater = LayoutInflater.from(context);
        this.mDatas = mDatas;
    }

    public int getCount() {
        return mDatas.size();
    }

    public Object getItem(int position) {
        return mDatas.get(position);
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = mInflater.inflate(
                    R.layout.text_adapter_item, parent, false);
            WindowManager wm = (WindowManager) mContext
                    .getSystemService(Context.WINDOW_SERVICE);
            DisplayMetrics outMetrics = new DisplayMetrics();
            wm.getDefaultDisplay().getMetrics(outMetrics);

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(outMetrics.widthPixels, ViewGroup.LayoutParams.MATCH_PARENT);
            convertView.setLayoutParams(params);
            viewHolder.mChoice_icon = (ImageView) convertView
                    .findViewById(R.id.choice_icon);

            viewHolder.mChoice_Content = (TextView) convertView.findViewById(R.id.content);
//            viewHolder.choice_A = (TextView) convertView
//                    .findViewById(R.id.choice_A);
//            viewHolder.choice_B = (TextView) convertView
//                    .findViewById(R.id.choice_B);
//            viewHolder.choice_C = (TextView) convertView
//                    .findViewById(R.id.choice_C);
//            viewHolder.choice_D = (TextView) convertView
//                    .findViewById(R.id.choice_D);
            viewHolder.is_wrong = (TextView) convertView.findViewById(R.id.is_right);
            viewHolder.answer = (TextView) convertView.findViewById(R.id.answer);
            viewHolder.answer_content = (TextView) convertView.findViewById(R.id.answer_content);

//            viewHolder.choice_1 = (LinearLayout) convertView.findViewById(R.id.choice_1);
//            viewHolder.choice_2 = (LinearLayout) convertView.findViewById(R.id.choice_2);
//            viewHolder.choice_3 = (LinearLayout) convertView.findViewById(R.id.choice_3);
//            viewHolder.choice_4 = (LinearLayout) convertView.findViewById(R.id.choice_4);
            viewHolder.explain = (LinearLayout) convertView.findViewById(R.id.explain);


            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.mChoice_Content.setText(mDatas.get(position));
        return convertView;
    }

    private class ViewHolder {
        TextView choice_A, choice_B, choice_C, choice_D, is_wrong, answer, answer_content;//选项内容
        ImageView mChoice_icon;//单选，多选图片
        TextView mChoice_Content;//选择题内容
        LinearLayout choice_1, choice_2, choice_3, choice_4, explain;

    }

}
