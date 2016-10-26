package com.haha.exam.adapter;

import android.content.Context;
import android.os.Handler;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.haha.exam.R;
import com.haha.exam.activity.OrderTextActivity;
import com.haha.exam.bean.AllQuestions;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/10/26.
 */
public class PracticeAdapter extends RecyclerView.Adapter<PracticeAdapter.SimpleViewHolder> {
    final Handler handler = new Handler();
    private List<Integer> isdo = new ArrayList<>();
    private List<Integer> choose = new ArrayList<>();
    private final Context mContext;
    private final RecyclerView mRecyclerView;
    private List<AllQuestions.DataBean> datas;
    private OrderTextActivity orderTextActivity;
    private String choice_answer;
    String choice;


    public static class SimpleViewHolder extends RecyclerView.ViewHolder {
        public final TextView title, tv_1, tv_2, tv_3, tv_4;
        public final TextView answer, is_wrong, answer_explain;
        public final ImageView iv_1, iv_2, iv_3, iv_4;

        public final LinearLayout ll_explain, choice_1, choice_2, choice_3, choice_4;


        public SimpleViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.title);
            answer = (TextView) view.findViewById(R.id.answer);
            is_wrong = (TextView) view.findViewById(R.id.is_wrong);
            answer_explain = (TextView) view.findViewById(R.id.answer_explain);
            tv_1 = (TextView) view.findViewById(R.id.tv_1);
            tv_2 = (TextView) view.findViewById(R.id.tv_2);
            tv_3 = (TextView) view.findViewById(R.id.tv_3);
            tv_4 = (TextView) view.findViewById(R.id.tv_4);
            iv_1 = (ImageView) view.findViewById(R.id.iv_1);
            iv_2 = (ImageView) view.findViewById(R.id.iv_2);
            iv_3 = (ImageView) view.findViewById(R.id.iv_3);
            iv_4 = (ImageView) view.findViewById(R.id.iv_4);

            ll_explain = (LinearLayout) view.findViewById(R.id.ll_anwer);
            choice_1 = (LinearLayout) view.findViewById(R.id.choice_1);
            choice_2 = (LinearLayout) view.findViewById(R.id.choice_2);
            choice_3 = (LinearLayout) view.findViewById(R.id.choice_3);
            choice_4 = (LinearLayout) view.findViewById(R.id.choice_4);

        }

    }

    public static void setListViewHeightBasedOnChildren(ListView listView) {
        // 获取ListView对应的Adapter
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            return;
        }

        int totalHeight = 0;
        for (int i = 0, len = listAdapter.getCount(); i < len; i++) {
            // listAdapter.getCount()返回数据项的数目
            View listItem = listAdapter.getView(i, null, listView);
            // 计算子项View 的宽高
            listItem.measure(0, 0);
            // 统计所有子项的总高度
            totalHeight += listItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        // listView.getDividerHeight()获取子项间分隔符占用的高度
        // params.height最后得到整个ListView完整显示需要的高度
        listView.setLayoutParams(params);
    }


    public PracticeAdapter(Context context, RecyclerView recyclerView) {
        orderTextActivity = new OrderTextActivity();
        mContext = context;
        mRecyclerView = recyclerView;
    }

    public void setDataList(List<AllQuestions.DataBean> datas) {
        this.datas = datas;

        for (int i = 0; i < datas.size(); i++) {
            isdo.add(datas.get(i).getIsdo());
            choose.add(datas.get(i).getChoose());
        }

        notifyDataSetChanged();
    }


    @Override
    public SimpleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(mContext).inflate(R.layout.item, parent, false);
        return new SimpleViewHolder(view);
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public void onBindViewHolder(final SimpleViewHolder holder, final int position) {
//        AnwerInfo.DataBean.SubDataBean subDataBean = datas.get(position);
        final AllQuestions.DataBean problem = datas.get(position);
        holder.answer.setText(problem.getAnswer());
        holder.answer_explain.setText(problem.getDetail());
        if (problem.getAnswer().equals("1")) {
            choice_answer = "1";
        } else if (problem.getAnswer().equals("2")) {
            choice_answer = "2";
        } else if (problem.getAnswer().equals("4")) {
            choice_answer = "3";
        } else if (problem.getAnswer().equals("8")) {
            choice_answer = "4";
        }
        holder.iv_1.setImageResource(R.mipmap.a);
        holder.iv_2.setImageResource(R.mipmap.b);
        holder.iv_3.setImageResource(R.mipmap.c);
        holder.iv_4.setImageResource(R.mipmap.d);
//        该题没有做
        if (isdo.get(position) == 0) {
            holder.itemView.getTag();
            holder.answer.setVisibility(View.INVISIBLE);
            holder.ll_explain.setVisibility(View.INVISIBLE);
            System.out.println("size===========" + datas.size());
//        holder.title.setText(subDataBean.getQuestionid() + ". " + subDataBean.getQuestion());
            holder.title.setText(problem.getSid() + ". " + problem.getQuestion());
            if (problem.getType().equals("3")) {
                holder.choice_3.setVisibility(View.GONE);
                holder.choice_4.setVisibility(View.GONE);
                holder.tv_1.setText("对");
                holder.tv_2.setText("错");
            } else if (problem.getType().equals("2")) {
//                holder.iv_1.setImageResource(R.mipmap.a);
//                holder.iv_2.setImageResource(R.mipmap.b);
//                holder.iv_3.setImageResource(R.mipmap.c);
//                holder.iv_4.setImageResource(R.mipmap.d);
                holder.tv_1.setText(problem.getOption().get(0).substring(2));
                holder.tv_2.setText(problem.getOption().get(1).substring(2));
                holder.tv_3.setText(problem.getOption().get(2).substring(2));
                holder.tv_4.setText(problem.getOption().get(3).substring(2));
            }


            holder.choice_1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    choice = "1";
                    System.out.println("choice============="+choice);
                    System.out.println("choice_answer============="+choice_answer);
                    if (choice.equals(choice_answer)) {
                        holder.choice_2.setEnabled(false);
                        holder.choice_3.setEnabled(false);
                        holder.choice_4.setEnabled(false);
                        isdo.set(position, 1);
                        choose.set(position, 1);


                        holder.iv_1.setImageResource(R.mipmap.right);
                        holder.tv_1.setTextColor(mContext.getResources().getColor(R.color.right_choice_color));
                    } else {
                        holder.choice_2.setEnabled(false);
                        holder.choice_3.setEnabled(false);
                        holder.choice_4.setEnabled(false);
                        isdo.set(position, 1);
                        choose.set(position, 1);
                        holder.answer.setVisibility(View.VISIBLE);
                        holder.ll_explain.setVisibility(View.VISIBLE);
                        holder.iv_1.setImageResource(R.mipmap.wrong);
                        holder.tv_1.setTextColor(mContext.getResources().getColor(R.color.wrong_choice_color));
                    }
                }
            });
            holder.choice_2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    choice = "2";
                    if (choice.equals(choice_answer)) {
                        isdo.set(position, 1);
                        choose.set(position, 2);
                        holder.choice_1.setEnabled(false);
                        holder.choice_3.setEnabled(false);
                        holder.choice_4.setEnabled(false);
                        holder.iv_2.setImageResource(R.mipmap.right);
                        holder.tv_2.setTextColor(mContext.getResources().getColor(R.color.right_choice_color));
//                        Runnable runnable = new Runnable() {
//                            @Override
//                            public void run() {
//                                handler.postDelayed(this, 200);
////                                mRecyclerView.smoothScrollToPosition(position + 1);
//                            }
//                        };
                    } else {
                        isdo.set(position, 1);
                        choose.set(position, 2);
                        holder.choice_1.setEnabled(false);
                        holder.choice_3.setEnabled(false);
                        holder.choice_4.setEnabled(false);
                        holder.answer.setVisibility(View.VISIBLE);
                        holder.ll_explain.setVisibility(View.VISIBLE);
                        holder.iv_2.setImageResource(R.mipmap.wrong);
                        holder.tv_2.setTextColor(mContext.getResources().getColor(R.color.wrong_choice_color));
                    }
                }
            });
            holder.choice_3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    choice = "3";
                    if (choice.equals(choice_answer)) {
                        isdo.set(position, 1);
                        choose.set(position, 3);
                        holder.choice_2.setEnabled(false);
                        holder.choice_1.setEnabled(false);
                        holder.choice_4.setEnabled(false);
                        holder.iv_3.setImageResource(R.mipmap.right);
                        holder.tv_3.setTextColor(mContext.getResources().getColor(R.color.right_choice_color));
                        Runnable runnable = new Runnable() {
                            @Override
                            public void run() {
                                handler.postDelayed(this, 200);
//                                mRecyclerView.smoothScrollToPosition(position + 1);
                            }
                        };
                    } else {
                        isdo.set(position, 1);
                        choose.set(position, 3);
                        holder.choice_2.setEnabled(false);
                        holder.choice_1.setEnabled(false);
                        holder.choice_4.setEnabled(false);
                        holder.answer.setVisibility(View.VISIBLE);
                        holder.ll_explain.setVisibility(View.VISIBLE);
                        holder.iv_3.setImageResource(R.mipmap.wrong);
                        holder.tv_3.setTextColor(mContext.getResources().getColor(R.color.wrong_choice_color));
                    }
                }
            });
            holder.choice_4.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    choice = "4";
                    if (choice.equals(choice_answer)) {
                        isdo.set(position, 1);
                        choose.set(position, 4);
                        holder.choice_2.setEnabled(false);
                        holder.choice_3.setEnabled(false);
                        holder.choice_1.setEnabled(false);
                        holder.iv_4.setImageResource(R.mipmap.right);
                        holder.tv_4.setTextColor(mContext.getResources().getColor(R.color.right_choice_color));
//                        Runnable runnable = new Runnable() {
//                            @Override
//                            public void run() {
//                                handler.postDelayed(this, 200);
////                                mRecyclerView.smoothScrollToPosition(position + 1);
//                            }
//                        };
                    } else {
                        isdo.set(position, 1);
                        choose.set(position, 4);
                        holder.choice_2.setEnabled(false);
                        holder.choice_3.setEnabled(false);
                        holder.choice_1.setEnabled(false);
                        holder.answer.setVisibility(View.VISIBLE);
                        holder.ll_explain.setVisibility(View.VISIBLE);
                        holder.iv_4.setImageResource(R.mipmap.wrong);
                        holder.tv_4.setTextColor(mContext.getResources().getColor(R.color.wrong_choice_color));
                    }
                }
            });
        } else {
//            该题已经做过
            holder.choice_1.setEnabled(false);
            holder.choice_2.setEnabled(false);
            holder.choice_3.setEnabled(false);
            holder.choice_4.setEnabled(false);
            if (String.valueOf(choose.get(position)).equals(choice_answer)) {
                if (choose.get(position) == 1) {
                    holder.iv_1.setImageResource(R.mipmap.right);
                    holder.tv_1.setTextColor(mContext.getResources().getColor(R.color.right_choice_color));
                } else if (choose.get(position) == 2) {
                    holder.iv_2.setImageResource(R.mipmap.right);
                    holder.tv_2.setTextColor(mContext.getResources().getColor(R.color.right_choice_color));
                } else if (choose.get(position) == 3) {
                    holder.iv_3.setImageResource(R.mipmap.right);
                    holder.tv_3.setTextColor(mContext.getResources().getColor(R.color.right_choice_color));
                } else if (choose.get(position) == 4) {
                    holder.iv_4.setImageResource(R.mipmap.right);
                    holder.tv_4.setTextColor(mContext.getResources().getColor(R.color.right_choice_color));
                }
            } else {
                holder.answer.setVisibility(View.VISIBLE);
                holder.ll_explain.setVisibility(View.VISIBLE);
                if (choose.get(position) == 1) {
                    holder.iv_1.setImageResource(R.mipmap.wrong);
                    holder.tv_1.setTextColor(mContext.getResources().getColor(R.color.wrong_choice_color));
                } else if (choose.get(position) == 2) {
                    holder.iv_2.setImageResource(R.mipmap.wrong);
                    holder.tv_2.setTextColor(mContext.getResources().getColor(R.color.wrong_choice_color));
                } else if (choose.get(position) == 3) {
                    holder.iv_3.setImageResource(R.mipmap.wrong);
                    holder.tv_3.setTextColor(mContext.getResources().getColor(R.color.wrong_choice_color));
                } else if (choose.get(position) == 4) {
                    holder.iv_4.setImageResource(R.mipmap.wrong);
                    holder.tv_4.setTextColor(mContext.getResources().getColor(R.color.wrong_choice_color));
                }
            }
        }
    }


    @Override
    public int getItemCount() {
        return datas != null ? datas.size() : 0;
    }
}
