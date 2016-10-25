package com.haha.exam.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.haha.exam.R;
import com.haha.exam.activity.BaseActivity;
import com.haha.exam.bean.AllQuestions;

import java.util.ArrayList;
import java.util.List;

/**
 * 选项的Adapter
 * Created by Administrator on 2016/10/24.
 */
public class OptionAdapter extends BaseAdapter {
    private Context context;
    private AllQuestions.DataBean datas;
    private LayoutInflater inflater;
    private int[] i = {1, 2};
    boolean isAllItemEnable = false;
    private ListView listView;
    public  boolean isClicked=false;

    public OptionAdapter(Context context, AllQuestions.DataBean data, ListView list) {
        this.context = context;
        this.datas = data;
        this.listView = list;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public boolean isEnabled(int position) {
        return super.isEnabled(position);
    }

    public void disableAllItemChoser() {
        isAllItemEnable = false;
        notifyDataSetChanged();
    }

    public void enableItemChoser() {
        isAllItemEnable = true;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        if (datas.getType().equals("2")) {
            return datas.getOption().size();
        }
        return i.length;
    }


    @Override
    public Object getItem(int positon) {

        if (datas.getType().equals("2")) {
            return datas.getOption().get(positon);
        }
        return i[positon];
    }

    @Override
    public long getItemId(int positon) {
        return positon;
    }

    @Override
    public View getView(final int positon, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder = null;

        if (viewHolder == null) {
            viewHolder = new ViewHolder();
            view = inflater.inflate(R.layout.text_list_item, null);
            viewHolder.iv_choice = (ImageView) view.findViewById(R.id.choice_icon);
            viewHolder.tv_content = (TextView) view.findViewById(R.id.choice_content);
            viewHolder.ll_item = (LinearLayout) view.findViewById(R.id.ll_item);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        if (datas.getType().equals("2")) {
            if (positon == 0) {
                viewHolder.iv_choice.setImageResource(R.mipmap.a);
            } else if (positon == 1) {
                viewHolder.iv_choice.setImageResource(R.mipmap.b);
            } else if (positon == 2) {
                viewHolder.iv_choice.setImageResource(R.mipmap.c);
            } else if (positon == 3) {
                viewHolder.iv_choice.setImageResource(R.mipmap.d);
            }


            viewHolder.tv_content.setText(datas.getOption().get(positon).substring(2));
        } else if (datas.getType().equals("3")) {
            if (positon == 0) {
                viewHolder.iv_choice.setImageResource(R.mipmap.a);
                viewHolder.tv_content.setText("对");
            } else if (positon == 1) {
                viewHolder.iv_choice.setImageResource(R.mipmap.b);
                viewHolder.tv_content.setText("错");
            }
        }
        view.setEnabled(false);

        final ViewHolder finalViewHolder = viewHolder;
//        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
//                System.out.println("你点击了==========" + position);
//                String answer = null;
//                if (datas.getAnswer().equals("1")) {
//                    answer = "1";
//                } else if (datas.getAnswer().equals("2")) {
//                    answer = "2";
//                } else if (datas.getAnswer().equals("4")) {
//                    answer = "3";
//                } else if (datas.getAnswer().equals("8")) {
//                    answer = "4";
//                }
//                if (positon == 0) {
//                    if (answer.equals("" + (position + 1))) {
////                       isEnabled(positon);
//                        isRight = true;
//                        System.out.println("选择============" + isRight);
//                        finalViewHolder.iv_choice.setImageResource(R.mipmap.right);
//                    }else {
//                        isRight = false;
//                        System.out.println("选择============" + isRight);
//                        finalViewHolder.iv_choice.setImageResource(R.mipmap.wrong);
//
//                    }
//
//                }
//                if (positon == 1) {
//                    if (answer.equals("" + (position + 1))) {
////                       isEnabled(positon);
//                        isRight = true;
//                        System.out.println("选择============" + isRight);
//                        finalViewHolder.iv_choice.setImageResource(R.mipmap.right);
//                    }else {
//                        isRight = false;
//                        System.out.println("选择============" + isRight);
//                        finalViewHolder.iv_choice.setImageResource(R.mipmap.wrong);
//
//                    }
//
//                }
//                if (positon == 2) {
//                    if (answer.equals("" + (position + 1))) {
////                       isEnabled(positon);
//                        isRight = true;
//                        System.out.println("选择============" + isRight);
//                        finalViewHolder.iv_choice.setImageResource(R.mipmap.right);
//                    }else {
//                        isRight = false;
//                        System.out.println("选择============" + isRight);
//                        finalViewHolder.iv_choice.setImageResource(R.mipmap.wrong);
//
//                    }
//
//                }
//                if (positon == 3) {
//                    if (answer.equals("" + (position + 1))) {
////                       isEnabled(positon);
//                        isRight = true;
//                        System.out.println("选择============" + isRight);
//                        finalViewHolder.iv_choice.setImageResource(R.mipmap.right);
//                    }else {
//                        isRight = false;
//                        System.out.println("选择============" + isRight);
//                        finalViewHolder.iv_choice.setImageResource(R.mipmap.wrong);
//
//                    }
//
//                }
//                listView.setEnabled(false);
//            }
//        });


//        if (listView.isEnabled() == false) {
//            System.out.println("=================");
//            if (isRight == true) {
//                viewHolder.iv_choice.setImageResource(R.mipmap.right);
//                viewHolder.tv_content.setTextColor(context.getResources().getColor(R.color.right_choice_color));
//            } else {
//                viewHolder.iv_choice.setImageResource(R.mipmap.wrong);
//                viewHolder.tv_content.setTextColor(context.getResources().getColor(R.color.wrong_choice_color));
//            }
//        }

        if (isClicked==false) {
            final View finalView = view;
            viewHolder.ll_item.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String answer = null;
                    isClicked=true;
                    if (datas.getAnswer().equals("1")) {
                        answer = "1";
                    } else if (datas.getAnswer().equals("2")) {
                        answer = "2";
                    } else if (datas.getAnswer().equals("4")) {
                        answer = "3";
                    } else if (datas.getAnswer().equals("8")) {
                        answer = "4";
                    }
                    if (answer.equals("" + (positon + 1))) {
//                       isEnabled(positon);
                        System.out.println("选择============" +isClicked);
                        listView.setEnabled(false);

                        finalView.setEnabled(false);
                        finalViewHolder.iv_choice.setImageResource(R.mipmap.right);
                        finalViewHolder.tv_content.setTextColor(context.getResources().getColor(R.color.right_choice_color));
                    } else {
                        System.out.println("选择============" + isClicked);
                        listView.setEnabled(false);
                        listView.setClickable(false);

                        finalViewHolder.iv_choice.setImageResource(R.mipmap.wrong);
                        finalViewHolder.tv_content.setTextColor(context.getResources().getColor(R.color.wrong_choice_color));
                    }

                    finalView.setEnabled(false);
                    listView.setEnabled(false);
                    listView.setClickable(false);
                }

            });
        }
            return view;
//    }

    }


    class ViewHolder {
        ImageView iv_choice;
        TextView tv_content;
        LinearLayout ll_item;
    }
}