/*
 * Copyright (C) 2014 Lucas Rocha
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.haha.exam.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
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
import android.widget.Toast;


import com.google.gson.Gson;
import com.haha.exam.R;
import com.haha.exam.activity.MyErrorActivity;
import com.haha.exam.activity.OrderTextActivity;
import com.haha.exam.bean.AddRight;
import com.haha.exam.bean.AllErrorQuestions;
import com.haha.exam.bean.AllQuestions;
import com.haha.exam.bean.DeleteAll;
import com.haha.exam.dao.ExamDao;
import com.haha.exam.view.MixtureTextView;
import com.haha.exam.web.WebInterface;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.squareup.picasso.Picasso;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import okhttp3.Call;
import okhttp3.Response;


public class LayoutAdapter extends RecyclerView.Adapter<LayoutAdapter.SimpleViewHolder> {

    Handler handler = new Handler();
    private final Context mContext;
    private final RecyclerView mRecyclerView;
    private List<AllQuestions.DataBean> datas;
    private OrderTextActivity orderTextActivity;
    private MyErrorActivity errorActivity;
    private Gson gson=new Gson();
    private String[] questionInfo;
    public static int rightCount;
    public static int errorCount;
    private List<String[]> list=new ArrayList<>();
    private String sbj="one";


    public static class SimpleViewHolder extends RecyclerView.ViewHolder {
        public final TextView  title,tv_1, tv_2, tv_3, tv_4;//title,
        public final TextView answer, is_wrong, answer_explain;
        public final ImageView iv_1, iv_2, iv_3, iv_4, iv_pic,choice_icon;
        public Drawable drawable;
        public final LinearLayout ll_explain, choice_1, choice_2, choice_3, choice_4;
//        private MixtureTextView mixtureTextView;


        public SimpleViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.title);

//            mixtureTextView= (MixtureTextView) view.findViewById(R.id.text);
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
            iv_pic = (ImageView) view.findViewById(R.id.iv_pic);
            choice_icon= (ImageView) view.findViewById(R.id.choice_icon);

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


    public LayoutAdapter(Context context, RecyclerView recyclerView) {
        orderTextActivity = new OrderTextActivity();
        errorActivity=new MyErrorActivity();

        mContext = context;
        mRecyclerView = recyclerView;
    }

    public void setDataList(List<AllQuestions.DataBean> datas) {
        this.datas = datas;
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

        final Runnable runnable = new Runnable() {
            @Override
            public void run() {
                mRecyclerView.scrollToPosition(position + 1);
            }

        };
        final ExamDao dao=new ExamDao(mContext);
        holder.answer.setVisibility(View.GONE);
        holder.ll_explain.setVisibility(View.GONE);

        System.out.println("发送过来的消息是===="+orderTextActivity.isClicked);

        final AllQuestions.DataBean problem = datas.get(position);
        String imageUrl=problem.getImage();
        String videoUrl=problem.getVideo();
        System.out.println("url==========="+imageUrl+videoUrl);
        if (!imageUrl.equals("")) {
            holder.iv_pic.setVisibility(View.VISIBLE);
            Picasso.with(mContext).load(imageUrl).into(holder.iv_pic);
        }else if (!videoUrl.equals("")){
            holder.iv_pic.setVisibility(View.VISIBLE);
            Picasso.with(mContext).load(videoUrl).into(holder.iv_pic);
        }else {
            holder.iv_pic.setVisibility(View.GONE);
        }
        holder.answer_explain.setText(problem.getDetail());
        holder.iv_1.setImageResource(R.mipmap.a);
        holder.iv_2.setImageResource(R.mipmap.b);
        holder.iv_3.setImageResource(R.mipmap.c);
        holder.iv_4.setImageResource(R.mipmap.d);

        holder.title.setText("       "+problem.getQuestion());
        if (problem.getType().equals("3")) {//选择题
            holder.choice_3.setVisibility(View.GONE);
            holder.choice_4.setVisibility(View.GONE);
            holder.choice_icon.setImageResource(R.mipmap.pan_duan);
            if (problem.getAnswer().equals("0")) {
                holder.answer.setText("错");
            } else {
                holder.answer.setText("对");
            }
            holder.tv_1.setText("对");
            holder.tv_2.setText("错");
        }else if (problem.getType().equals("2")) {//单选题
            holder.tv_1.setText(problem.getOption().get(0).substring(2));
            holder.tv_2.setText(problem.getOption().get(1).substring(2));
            holder.tv_3.setText(problem.getOption().get(2).substring(2));
            holder.tv_4.setText(problem.getOption().get(3).substring(2));
            holder.choice_icon.setImageResource(R.mipmap.single_choice);
            if (problem.getAnswer().equals("1")) {
                holder.answer.setText("A");
            } else if (problem.getAnswer().equals("2")) {
                holder.answer.setText("B");
            } else if (problem.getAnswer().equals("4")) {
                holder.answer.setText("C");
            } else if (problem.getAnswer().equals("8")) {
                holder.answer.setText("D");
            }
        }

//        该题没有做
        if (problem.getIsdo() == 0) {
            if (orderTextActivity.isClicked==1&&orderTextActivity.curPosition==position){
                holder.answer.setVisibility(View.VISIBLE);
                holder.ll_explain.setVisibility(View.VISIBLE);
            }else {
                holder.answer.setVisibility(View.GONE);
                holder.ll_explain.setVisibility(View.GONE);
            }
            holder.itemView.getTag();
            System.out.println("size===========" + datas.size());

            if (problem.getType().equals("3")) {//判断题
                holder.choice_1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        holder.choice_2.setEnabled(false);
                        problem.setIsdo(1);
                        problem.setChoose(1);

//                        更新数据库做题状态
                        dao.updateIsdo(problem.getSid(),"xc",problem.getSubject(),1,1);


                        if (problem.getAnswer().equals("1")) {
                            holder.iv_1.setImageResource(R.mipmap.right);
                            holder.tv_1.setTextColor(mContext.getResources().getColor(R.color.right_choice_color));
                            rightCount++;
                            dao.addRightQuestions(problem,sbj);
                            OkGo.post(WebInterface.add_right)
                                    .params("telphone","18266142739")
                                    .params("questionid",problem.getSid())
                                    .execute(new StringCallback() {
                                        @Override
                                        public void onSuccess(String s, Call call, Response response) {
                                            AddRight addRight=gson.fromJson(s,AddRight.class);
                                            Toast.makeText(mContext,addRight.getMsg(),Toast.LENGTH_SHORT).show();
                                        }
                                    });
//                            如果做对了，将错题从错题库中自动删除
                            if (errorActivity.auto_clear==1){
                                OkGo.post(WebInterface.delete_error)
                                        .tag(this)
                                        .params("telphone", "18266142739")
                                        .params("questionid", datas.get(position).getSid())
                                        .execute(new StringCallback() {
                                            @Override
                                            public void onSuccess(String s, Call call, Response response) {
                                                DeleteAll string = gson.fromJson(s, DeleteAll.class);
                                                Toast.makeText(mContext, string.getMsg(), Toast.LENGTH_SHORT).show();
                                            }
                                        });

                            }
                          handler.postDelayed(runnable,300);

                        } else {
                            holder.answer.setVisibility(View.VISIBLE);
                            holder.ll_explain.setVisibility(View.VISIBLE);
                            holder.iv_1.setImageResource(R.mipmap.wrong);
                            holder.tv_1.setTextColor(mContext.getResources().getColor(R.color.wrong_choice_color));
                            questionInfo=new String[]{problem.getSid(),String.valueOf(problem.getIsdo()),String.valueOf(problem.getChoose())};
                            System.out.println("questionInfo========"+ Arrays.toString(questionInfo));
                            list.add(questionInfo);
                            System.out.println("listsize==========="+list.size());
                            //  将错题发送到账号内
                            OkGo.post(WebInterface.add_error)
                                    .tag(this)
                                    .params("tel", "18266142739")
                                    .params("questionid", problem.getSid())
                                    .params("option", "1")
                                    .execute(new StringCallback() {
                                        @Override
                                        public void onSuccess(String s, Call call, Response response) {
                                            AllErrorQuestions questions = gson.fromJson(s, AllErrorQuestions.class);
                                            Toast.makeText(mContext, questions.getMsg(), Toast.LENGTH_SHORT).show();
                                        }
                                    });
                            dao.addErrorQuestions(problem, "1");//添加错题到错题库
                        }
                    }
                });
                holder.choice_2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        problem.setIsdo(1);
                        problem.setChoose(2);
                        //                        更新数据库做题状态
                        dao.updateIsdo(problem.getSid(),"xc",problem.getSubject(),1,2);

                        holder.choice_1.setEnabled(false);
                        if (problem.getAnswer().equals("0")) {
                            dao.addRightQuestions(problem,sbj);
                            holder.answer.setText("对");
                            holder.iv_2.setImageResource(R.mipmap.right);
                            holder.tv_2.setTextColor(mContext.getResources().getColor(R.color.right_choice_color));
                            OkGo.post(WebInterface.add_right)
                                    .params("telphone","18266142739")
                                    .params("questionid",problem.getSid())
                                    .execute(new StringCallback() {
                                        @Override
                                        public void onSuccess(String s, Call call, Response response) {
                                            AddRight addRight=gson.fromJson(s,AddRight.class);
                                            Toast.makeText(mContext,addRight.getMsg(),Toast.LENGTH_SHORT).show();
                                        }
                                    });
                            //                            如果做对了，将错题从错题库中自动删除
                            if (errorActivity.auto_clear==1){
                                OkGo.post(WebInterface.delete_error)
                                        .tag(this)
                                        .params("telphone", "18266142739")
                                        .params("questionid", datas.get(position).getSid())
                                        .execute(new StringCallback() {
                                            @Override
                                            public void onSuccess(String s, Call call, Response response) {
                                                DeleteAll string = gson.fromJson(s, DeleteAll.class);
                                                Toast.makeText(mContext, string.getMsg(), Toast.LENGTH_SHORT).show();
                                            }
                                        });

                            }
                            handler.postDelayed(runnable,300);

                        } else {
                            holder.answer.setVisibility(View.VISIBLE);
                            holder.ll_explain.setVisibility(View.VISIBLE);
                            holder.iv_2.setImageResource(R.mipmap.wrong);
                            holder.tv_2.setTextColor(mContext.getResources().getColor(R.color.wrong_choice_color));
                            //                           将错题发送到账号内
                            OkGo.post(WebInterface.add_error)
                                    .tag(this)
                                    .params("tel", "18266142739")
                                    .params("questionid", problem.getSid())
                                    .params("option", "1")
                                    .execute(new StringCallback() {
                                        @Override
                                        public void onSuccess(String s, Call call, Response response) {
                                            AllErrorQuestions questions = gson.fromJson(s, AllErrorQuestions.class);
                                            Toast.makeText(mContext, questions.getMsg(), Toast.LENGTH_SHORT).show();
                                        }
                                    });
                            questionInfo=new String[]{problem.getSid(),String.valueOf(problem.getIsdo()),String.valueOf(problem.getChoose())};
                            System.out.println("questionInfo========"+ Arrays.toString(questionInfo));
                            list.add(questionInfo);
                            System.out.println("listsize==========="+list.size());
                            dao.addErrorQuestions(problem, "1");//添加错题到错题库
                        }
                    }
                });
            } else if (problem.getType().equals("2")) {//单选题
                holder.choice_1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        holder.choice_2.setEnabled(false);
                        holder.choice_3.setEnabled(false);
                        holder.choice_4.setEnabled(false);
                        problem.setIsdo(1);
                        problem.setChoose(1);

                        //                        更新数据库做题状态
                        dao.updateIsdo(problem.getSid(),"xc",problem.getSubject(),1,1);

                        if (problem.getAnswer().equals("1")) {
                            dao.addRightQuestions(problem,sbj);
                            holder.iv_1.setImageResource(R.mipmap.right);
                            holder.tv_1.setTextColor(mContext.getResources().getColor(R.color.right_choice_color));
                            OkGo.post(WebInterface.add_right)
                                    .params("telphone","18266142739")
                                    .params("questionid",problem.getSid())
                                    .execute(new StringCallback() {
                                        @Override
                                        public void onSuccess(String s, Call call, Response response) {
                                            AddRight addRight=gson.fromJson(s,AddRight.class);
                                            Toast.makeText(mContext,addRight.getMsg(),Toast.LENGTH_SHORT).show();
                                        }
                                    });
                            //                            如果做对了，将错题从错题库中自动删除
                            if (errorActivity.auto_clear==1){
                                OkGo.post(WebInterface.delete_error)
                                        .tag(this)
                                        .params("telphone", "18266142739")
                                        .params("questionid", datas.get(position).getSid())
                                        .execute(new StringCallback() {
                                            @Override
                                            public void onSuccess(String s, Call call, Response response) {
                                                DeleteAll string = gson.fromJson(s, DeleteAll.class);
                                                Toast.makeText(mContext, string.getMsg(), Toast.LENGTH_SHORT).show();
                                            }
                                        });

                            }
                            handler.postDelayed(runnable,300);
//                            if (mRecyclerView.getScrollState() == 0) {
//                                handler.postDelayed(runnable, 500);
//                            }
                        } else {
                            holder.answer.setVisibility(View.VISIBLE);
                            holder.ll_explain.setVisibility(View.VISIBLE);
                            holder.iv_1.setImageResource(R.mipmap.wrong);
                            holder.tv_1.setTextColor(mContext.getResources().getColor(R.color.wrong_choice_color));
                            dao.addErrorQuestions(problem, "1");//添加错题到错题库
                            //                           将错题发送到账号内
                            OkGo.post(WebInterface.add_error)
                                    .tag(this)
                                    .params("tel", "18266142739")
                                    .params("questionid", problem.getSid())
                                    .params("option", "1")
                                    .execute(new StringCallback() {
                                        @Override
                                        public void onSuccess(String s, Call call, Response response) {
                                            AllErrorQuestions questions = gson.fromJson(s, AllErrorQuestions.class);
                                            Toast.makeText(mContext, questions.getMsg(), Toast.LENGTH_SHORT).show();
                                        }
                                    });
                            questionInfo=new String[]{problem.getSid(),String.valueOf(problem.getIsdo()),String.valueOf(problem.getChoose())};
                            System.out.println("questionInfo========"+ Arrays.toString(questionInfo));
                            list.add(questionInfo);
                            System.out.println("listsize==========="+list.size());
                        }
                    }
                });
                holder.choice_2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        problem.setIsdo(1);
                        problem.setChoose(2);

                        //                        更新数据库做题状态
                        dao.updateIsdo(problem.getSid(),"xc",problem.getSubject(),1,2);

                        holder.choice_1.setEnabled(false);
                        holder.choice_3.setEnabled(false);
                        holder.choice_4.setEnabled(false);
                        if (problem.getAnswer().equals("2")) {
                            dao.addRightQuestions(problem,sbj);
                            holder.iv_2.setImageResource(R.mipmap.right);
                            holder.tv_2.setTextColor(mContext.getResources().getColor(R.color.right_choice_color));
                            OkGo.post(WebInterface.add_right)
                                    .params("telphone","18266142739")
                                    .params("questionid",problem.getSid())
                                    .execute(new StringCallback() {
                                        @Override
                                        public void onSuccess(String s, Call call, Response response) {
                                            AddRight addRight=gson.fromJson(s,AddRight.class);
                                            Toast.makeText(mContext,addRight.getMsg(),Toast.LENGTH_SHORT).show();
                                        }
                                    });
                            //                            如果做对了，将错题从错题库中自动删除
                            if (errorActivity.auto_clear==1){
                                OkGo.post(WebInterface.delete_error)
                                        .tag(this)
                                        .params("telphone", "18266142739")
                                        .params("questionid", datas.get(position).getSid())
                                        .execute(new StringCallback() {
                                            @Override
                                            public void onSuccess(String s, Call call, Response response) {
                                                DeleteAll string = gson.fromJson(s, DeleteAll.class);
                                                Toast.makeText(mContext, string.getMsg(), Toast.LENGTH_SHORT).show();
                                            }
                                        });

                            }
                            handler.postDelayed(runnable,300);
//                            if (mRecyclerView.getScrollState() == 0) {
//                                handler.postDelayed(runnable, 500);
//                            }
                        } else {
                            holder.answer.setVisibility(View.VISIBLE);
                            holder.ll_explain.setVisibility(View.VISIBLE);
                            holder.iv_2.setImageResource(R.mipmap.wrong);
                            holder.tv_2.setTextColor(mContext.getResources().getColor(R.color.wrong_choice_color));
                            dao.addErrorQuestions(problem, "1");//添加错题到错题库
                            //                           将错题发送到账号内
                            OkGo.post(WebInterface.add_error)
                                    .tag(this)
                                    .params("tel", "18266142739")
                                    .params("questionid", problem.getSid())
                                    .params("option", "1")
                                    .execute(new StringCallback() {
                                        @Override
                                        public void onSuccess(String s, Call call, Response response) {
                                            AllErrorQuestions questions = gson.fromJson(s, AllErrorQuestions.class);
                                            Toast.makeText(mContext, questions.getMsg(), Toast.LENGTH_SHORT).show();
                                        }
                                    });
                            questionInfo=new String[]{problem.getSid(),String.valueOf(problem.getIsdo()),String.valueOf(problem.getChoose())};
                            System.out.println("questionInfo========"+ Arrays.toString(questionInfo));
                            list.add(questionInfo);
                            System.out.println("listsize==========="+list.size());
                        }
                    }
                });
                holder.choice_3.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        problem.setIsdo(1);
                        problem.setChoose(4);


                        //                        更新数据库做题状态
                        dao.updateIsdo(problem.getSid(),"xc",problem.getSubject(),1,4);

                        holder.choice_2.setEnabled(false);
                        holder.choice_1.setEnabled(false);
                        holder.choice_4.setEnabled(false);
                        if (problem.getAnswer().equals("4")) {
                            dao.addRightQuestions(problem,sbj);
                            holder.iv_3.setImageResource(R.mipmap.right);
                            holder.tv_3.setTextColor(mContext.getResources().getColor(R.color.right_choice_color));
                            OkGo.post(WebInterface.add_right)
                                    .params("telphone","18266142739")
                                    .params("questionid",problem.getSid())
                                    .execute(new StringCallback() {
                                        @Override
                                        public void onSuccess(String s, Call call, Response response) {
                                            AddRight addRight=gson.fromJson(s,AddRight.class);
                                            Toast.makeText(mContext,addRight.getMsg(),Toast.LENGTH_SHORT).show();
                                        }
                                    });
                            //                            如果做对了，将错题从错题库中自动删除
                            if (errorActivity.auto_clear==1){
                                OkGo.post(WebInterface.delete_error)
                                        .tag(this)
                                        .params("telphone", "18266142739")
                                        .params("questionid", datas.get(position).getSid())
                                        .execute(new StringCallback() {
                                            @Override
                                            public void onSuccess(String s, Call call, Response response) {
                                                DeleteAll string = gson.fromJson(s, DeleteAll.class);
                                                Toast.makeText(mContext, string.getMsg(), Toast.LENGTH_SHORT).show();
                                            }
                                        });

                            }
                            handler.postDelayed(runnable,300);
//                            if (mRecyclerView.getScrollState() == 0) {
//                                handler.postDelayed(runnable, 500);
//                            }
                        } else {
                            holder.answer.setVisibility(View.VISIBLE);
                            holder.ll_explain.setVisibility(View.VISIBLE);
                            holder.iv_3.setImageResource(R.mipmap.wrong);
                            holder.tv_3.setTextColor(mContext.getResources().getColor(R.color.wrong_choice_color));
                            dao.addErrorQuestions(problem, "1");//添加错题到错题库
                            //                           将错题发送到账号内
                            OkGo.post(WebInterface.add_error)
                                    .tag(this)
                                    .params("tel", "18266142739")
                                    .params("questionid", problem.getSid())
                                    .params("option", "1")
                                    .execute(new StringCallback() {
                                        @Override
                                        public void onSuccess(String s, Call call, Response response) {
                                            AllErrorQuestions questions = gson.fromJson(s, AllErrorQuestions.class);
                                            Toast.makeText(mContext, questions.getMsg(), Toast.LENGTH_SHORT).show();
                                        }
                                    });
                            questionInfo=new String[]{problem.getSid(),String.valueOf(problem.getIsdo()),String.valueOf(problem.getChoose())};
                            System.out.println("questionInfo========"+ Arrays.toString(questionInfo));
                            list.add(questionInfo);
                            System.out.println("listsize==========="+list.size());
                        }
                    }
                });
                holder.choice_4.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        problem.setIsdo(1);
                        problem.setChoose(8);

                        //                        更新数据库做题状态
                        dao.updateIsdo(problem.getSid(),"xc",problem.getSubject(),1,8);

                        holder.choice_2.setEnabled(false);
                        holder.choice_3.setEnabled(false);
                        holder.choice_1.setEnabled(false);
                        if (problem.getAnswer().equals("8")) {
                            dao.addRightQuestions(problem,sbj);
                            holder.iv_4.setImageResource(R.mipmap.right);
                            holder.tv_4.setTextColor(mContext.getResources().getColor(R.color.right_choice_color));
                            OkGo.post(WebInterface.add_right)
                                    .params("telphone","18266142739")
                                    .params("questionid",problem.getSid())
                                    .execute(new StringCallback() {
                                        @Override
                                        public void onSuccess(String s, Call call, Response response) {
                                            AddRight addRight=gson.fromJson(s,AddRight.class);
                                            Toast.makeText(mContext,addRight.getMsg(),Toast.LENGTH_SHORT).show();
                                        }
                                    });
                            //                            如果做对了，将错题从错题库中自动删除
                            if (errorActivity.auto_clear==1){
                                OkGo.post(WebInterface.delete_error)
                                        .tag(this)
                                        .params("telphone", "18266142739")
                                        .params("questionid", datas.get(position).getSid())
                                        .execute(new StringCallback() {
                                            @Override
                                            public void onSuccess(String s, Call call, Response response) {
                                                DeleteAll string = gson.fromJson(s, DeleteAll.class);
                                                Toast.makeText(mContext, string.getMsg(), Toast.LENGTH_SHORT).show();
                                            }
                                        });

                            }
                            handler.postDelayed(runnable,300);
//                            if (mRecyclerView.getScrollState() == 0) {
//                                handler.postDelayed(runnable, 500);
//                            }
                        } else {
                            holder.answer.setVisibility(View.VISIBLE);
                            holder.ll_explain.setVisibility(View.VISIBLE);
                            holder.iv_4.setImageResource(R.mipmap.wrong);
                            holder.tv_4.setTextColor(mContext.getResources().getColor(R.color.wrong_choice_color));
                            dao.addErrorQuestions(problem, "1");//添加错题到错题库
                            //                           将错题发送到账号内
                            OkGo.post(WebInterface.add_error)
                                    .tag(this)
                                    .params("tel", "18266142739")
                                    .params("questionid", problem.getSid())
                                    .params("option", "1")
                                    .execute(new StringCallback() {
                                        @Override
                                        public void onSuccess(String s, Call call, Response response) {
                                            AllErrorQuestions questions = gson.fromJson(s, AllErrorQuestions.class);
                                            Toast.makeText(mContext, questions.getMsg(), Toast.LENGTH_SHORT).show();
                                        }
                                    });
                            questionInfo=new String[]{problem.getSid(),String.valueOf(problem.getIsdo()),String.valueOf(problem.getChoose())};
                            System.out.println("questionInfo========"+ Arrays.toString(questionInfo));
                            list.add(questionInfo);
                            System.out.println("listsize==========="+list.size());
                        }
                    }
                });
            }


        } else {
//            该题已经做过
            holder.choice_1.setEnabled(false);
            holder.choice_2.setEnabled(false);
            holder.choice_3.setEnabled(false);
            holder.choice_4.setEnabled(false);

            if (problem.getChoose() == Integer.valueOf(problem.getAnswer())) {
                if (problem.getChoose() == 1) {
                    holder.iv_1.setImageResource(R.mipmap.right);
                    holder.tv_1.setTextColor(mContext.getResources().getColor(R.color.right_choice_color));
                } else if (problem.getChoose() == 2) {
                    holder.iv_2.setImageResource(R.mipmap.right);
                    holder.tv_2.setTextColor(mContext.getResources().getColor(R.color.right_choice_color));
                } else if (problem.getChoose() == 4) {
                    holder.iv_3.setImageResource(R.mipmap.right);
                    holder.tv_3.setTextColor(mContext.getResources().getColor(R.color.right_choice_color));
                } else if (problem.getChoose() == 8) {
                    holder.iv_4.setImageResource(R.mipmap.right);
                    holder.tv_4.setTextColor(mContext.getResources().getColor(R.color.right_choice_color));
                }
            } else {
                holder.answer.setVisibility(View.VISIBLE);
                holder.ll_explain.setVisibility(View.VISIBLE);
                if (problem.getChoose() == 1) {
                    holder.iv_1.setImageResource(R.mipmap.wrong);
                    holder.tv_1.setTextColor(mContext.getResources().getColor(R.color.wrong_choice_color));
                } else if (problem.getChoose() == 2) {
                    holder.iv_2.setImageResource(R.mipmap.wrong);
                    holder.tv_2.setTextColor(mContext.getResources().getColor(R.color.wrong_choice_color));
                } else if (problem.getChoose() == 4) {
                    holder.iv_3.setImageResource(R.mipmap.wrong);
                    holder.tv_3.setTextColor(mContext.getResources().getColor(R.color.wrong_choice_color));
                } else if (problem.getChoose() == 8) {
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
