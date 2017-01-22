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

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Handler;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.google.gson.Gson;
import com.haha.exam.R;
import com.haha.exam.activity.PracticeResultActivity;
import com.haha.exam.activity.PraticeActivity;
import com.haha.exam.bean.AddRight;
import com.haha.exam.bean.AllErrorQuestions;
import com.haha.exam.bean.AllQuestions;
import com.haha.exam.bean.ErrorQuestion;
import com.haha.exam.dao.ExamDao;
import com.haha.exam.dialog.MyDialog;
import com.haha.exam.utils.SPUtils;
import com.haha.exam.web.WebInterface;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.wanzheng.driver.RelatedActivity.PictureDetail;
import com.wanzheng.driver.network.JsonUtils;
import com.wanzheng.driver.util.SystemUtil;

import org.json.JSONArray;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Response;


public class PracticeAdapter extends RecyclerView.Adapter<PracticeAdapter.SimpleViewHolder> {

    protected ImageLoader imageLoader;
    private SystemUtil su;
    private String onlyID;
    private SPUtils spUtils = new SPUtils();
    private String subject, subject0;
    private String cartype;
    private Handler handler = new Handler();
    private final Context mContext;
    private final RecyclerView mRecyclerView;
    private List<AllQuestions.DataBean> datas;
    private PraticeActivity praticeActivity;
    public static int error_count;
    private ExamDao dao;
    public static int isRight;
    public static int rightCount;
    private Gson gson = new Gson();
    private Map map;
    private List<Map> list1;
    private List<Bitmap> mBitmaps;

    public static List<ErrorQuestion> list = new ArrayList<>();


    public static class SimpleViewHolder extends RecyclerView.ViewHolder {

        public TextView title, tv_1, tv_2, tv_3, tv_4;
        public TextView answer, is_wrong, answer_explain;
        public ImageView iv_1, iv_2, iv_3, iv_4, choice_icon, iv_pic;
        public Drawable drawable;
        public Button btn_sure;
        public LinearLayout ll_explain, choice_1, choice_2, choice_3, choice_4;
        public VideoView mVideoview;
        public ProgressBar mProgressBar;
        public FrameLayout ll_video;

        public SimpleViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.title);
            answer = (TextView) view.findViewById(R.id.answer);
            is_wrong = (TextView) view.findViewById(R.id.is_wrong);
            answer_explain = (TextView) view.findViewById(R.id.answer_explain);
            btn_sure = (Button) view.findViewById(R.id.btn_sure);
            tv_1 = (TextView) view.findViewById(R.id.tv_1);
            tv_2 = (TextView) view.findViewById(R.id.tv_2);
            tv_3 = (TextView) view.findViewById(R.id.tv_3);
            tv_4 = (TextView) view.findViewById(R.id.tv_4);
            iv_1 = (ImageView) view.findViewById(R.id.iv_1);
            iv_2 = (ImageView) view.findViewById(R.id.iv_2);
            iv_3 = (ImageView) view.findViewById(R.id.iv_3);
            iv_4 = (ImageView) view.findViewById(R.id.iv_4);
            choice_icon = (ImageView) view.findViewById(R.id.choice_icon);
            iv_pic = (ImageView) view.findViewById(R.id.iv_pic);
            ll_video = (FrameLayout) view.findViewById(R.id.practice_Frvideo);
            mVideoview = (VideoView) view.findViewById(R.id.videoView_practice);
            mProgressBar = (ProgressBar) view.findViewById(R.id.practice_item_progressBar);

            ll_explain = (LinearLayout) view.findViewById(R.id.ll_anwer);
            choice_1 = (LinearLayout) view.findViewById(R.id.choice_1);
            choice_2 = (LinearLayout) view.findViewById(R.id.choice_2);
            choice_3 = (LinearLayout) view.findViewById(R.id.choice_3);
            choice_4 = (LinearLayout) view.findViewById(R.id.choice_4);

        }

    }

    public PracticeAdapter(Context context, RecyclerView recyclerView) {
        mBitmaps = new ArrayList<>();
        praticeActivity = new PraticeActivity();
        list = new ArrayList<>();
        list1 = new ArrayList<>();
        subject = (String) spUtils.get(context, "subject", "one");
        subject0 = (String) spUtils.get(context, "subject0", "1");
        cartype = (String) spUtils.get(context, "cartype", "xc");
        mContext = context;
        su = new SystemUtil(mContext);
        onlyID = su.showOnlyID();
        mRecyclerView = recyclerView;
        dao = new ExamDao(context);
        rightCount = 0;
        error_count = 0;
    }

    public void setDataList(List<AllQuestions.DataBean> datas) {
        this.datas = datas;
        notifyDataSetChanged();
    }


    @Override
    public SimpleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(mContext).inflate(R.layout.item_practice, parent, false);
        return new SimpleViewHolder(view);
    }

    @Override
    public int getItemViewType(int position) {

        return position;
    }

    //    清除缓存的图片
    public void cleanBitmapList() {
        if (mBitmaps.size() > 0) {
            for (int i = 0; i < mBitmaps.size(); i++) {
                Bitmap b = mBitmaps.get(i);
                if (b != null && !b.isRecycled()) {
                    b.recycle();
                }
            }
        }
    }

    @Override
    public void onBindViewHolder(final SimpleViewHolder holder, final int position) {
        final Runnable runnable = new Runnable() {
            @Override
            public void run() {
                if (position + 1 < datas.size()) {
                    mRecyclerView.smoothScrollToPosition(position + 1);
                }
            }

        };
        final ExamDao dao = new ExamDao(mContext);
        holder.answer.setVisibility(View.GONE);
        holder.ll_explain.setVisibility(View.GONE);

        final AllQuestions.DataBean problem = datas.get(position);
        final String imageUrl = problem.getImage();
        final String videoUrl = problem.getVideo();
        System.out.println("url===========" + imageUrl + videoUrl);
        if (!imageUrl.equals("")) {
            holder.iv_pic.setVisibility(View.VISIBLE);
            holder.iv_pic.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mContext, PictureDetail.class);
                    intent.putExtra("bitmap", imageUrl);
                    mContext.startActivity(intent);
                }
            });
//            Picasso.with(mContext).load(imageUrl).memoryPolicy(NO_CACHE, NO_STORE).into(holder.iv_pic);
            imageLoader.getInstance().displayImage(imageUrl, holder.iv_pic, new ImageLoadingListener() {
                @Override
                public void onLoadingStarted(String s, View view) {

                }

                @Override
                public void onLoadingFailed(String s, View view, FailReason failReason) {
                    switch (failReason.getType()) {
                        case OUT_OF_MEMORY:
                            cleanBitmapList();
                            break;
                    }
                }

                @Override
                public void onLoadingComplete(String s, View view, Bitmap bitmap) {
                    mBitmaps.add(bitmap);
                }

                @Override
                public void onLoadingCancelled(String s, View view) {

                }
            });
        } else if (!videoUrl.equals("")) {
            holder.iv_pic.setVisibility(View.GONE);
            //添加程序
            holder.ll_video.setVisibility(View.VISIBLE);
            holder.mVideoview.setVideoURI(Uri.parse(videoUrl));
            holder.mVideoview.start();
            holder.mVideoview.requestFocus();

//               holder.videoView.setLayoutParams(new FrameLayout.LayoutParams(
//                       ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

            holder.mVideoview.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
                    if (holder.mProgressBar != null)
                        holder.mProgressBar.setVisibility(View.GONE);
                    mp.start();
                    mp.setLooping(true);
                }
            });

            holder.mVideoview.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {

                }
            });


        } else {
            holder.iv_pic.setVisibility(View.GONE);
            holder.ll_video.setVisibility(View.GONE);
        }
        holder.answer_explain.setText(problem.getDetail());
        holder.iv_1.setImageResource(R.mipmap.a);
        holder.iv_2.setImageResource(R.mipmap.b);
        holder.iv_3.setImageResource(R.mipmap.c);
        holder.iv_4.setImageResource(R.mipmap.d);

        holder.title.setText("       " + problem.getQuestion());
        if (problem.getType().equals("3")) {//判断题
            holder.choice_3.setVisibility(View.GONE);
            holder.choice_4.setVisibility(View.GONE);
            holder.btn_sure.setVisibility(View.GONE);
            holder.choice_icon.setImageResource(R.mipmap.pan_duan);
            if (problem.getAnswer().equals("0")) {
                holder.answer.setText("错");
            } else {
                holder.answer.setText("对");
            }
            holder.tv_1.setText("对");
            holder.tv_2.setText("错");
        } else {


            holder.tv_1.setText(problem.getOption().get(0).substring(2));
            holder.tv_2.setText(problem.getOption().get(1).substring(2));
            holder.tv_3.setText(problem.getOption().get(2).substring(2));
            holder.tv_4.setText(problem.getOption().get(3).substring(2));


            if (problem.getType().equals("2")) {//单选题
                holder.choice_icon.setImageResource(R.mipmap.single_choice);
                holder.btn_sure.setVisibility(View.GONE);
                if (problem.getAnswer().equals("1")) {
                    holder.answer.setText("A");
                } else if (problem.getAnswer().equals("2")) {
                    holder.answer.setText("B");
                } else if (problem.getAnswer().equals("4")) {
                    holder.answer.setText("C");
                } else if (problem.getAnswer().equals("8")) {
                    holder.answer.setText("D");
                }
            } else if (problem.getType().equals("1")) {//多选题
                holder.choice_icon.setImageResource(R.mipmap.many_choice);
                holder.btn_sure.setVisibility(View.VISIBLE);
                String string = Integer.toBinaryString(Integer.parseInt(problem.getAnswer()));
                String answer = "";
                if (string.length() == 2) {
                    if (String.valueOf(string.charAt(1)).equals("1")) {
                        answer = answer + "A";
                        System.out.println("answer1=========" + answer);
                    }
                    if (String.valueOf(string.charAt(0)).equals("1")) {
                        answer = answer + "B";
                        System.out.println("answer2=========" + answer);
                    }
                } else if (string.length() == 3) {
                    if (String.valueOf(string.charAt(2)).equals("1")) {
                        answer = answer + "A";
                        System.out.println("answer3=========" + answer);
                    }
                    if (String.valueOf(string.charAt(1)).equals("1")) {
                        answer = answer + "B";
                        System.out.println("answer4=========" + answer);
                    }
                    if (String.valueOf(string.charAt(0)).equals("1")) {
                        answer = answer + "C";
                        System.out.println("answer5=========" + answer);
                    }
                } else if (string.length() == 4) {
                    if (String.valueOf(string.charAt(3)).equals("1")) {
                        answer = answer + "A";
                        System.out.println("answer6=========" + answer);
                    }
                    if (String.valueOf(string.charAt(2)).equals("1")) {
                        answer = answer + "B";
                        System.out.println("answer7=========" + answer);
                    }
                    if (String.valueOf(string.charAt(1)).equals("1")) {
                        answer = answer + "C";
                        System.out.println("answer8=========" + answer);
                    }
                    if (String.valueOf(string.charAt(0)).equals("1")) {
                        answer = answer + "D";
                        System.out.println("answer9=========" + answer);
                    }
                }
                System.out.println("answer=========" + answer);
                holder.answer.setText(answer);
            }

        }
//        该题没有做
        if (problem.getIsdo() == 0) {
            final ErrorQuestion errorQuestion = new ErrorQuestion();
            isRight = 0;
            holder.itemView.getTag();
            holder.answer.setVisibility(View.GONE);
            holder.ll_explain.setVisibility(View.GONE);
            System.out.println("size===========" + datas.size());
            holder.title.setText("       " + problem.getQuestion());
            if (problem.getType().equals("3")) {//判断题
                holder.choice_1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        holder.choice_2.setEnabled(false);
                        problem.setIsdo(1);
                        problem.setChoose(1);
                        if (problem.getAnswer().equals("1")) {
                            holder.iv_1.setImageResource(R.mipmap.right);
                            holder.tv_1.setTextColor(mContext.getResources().getColor(R.color.right_choice_color));
                            if (mRecyclerView.getScrollState() == 0) {
                                handler.postDelayed(runnable, 500);
                            }
                            OkGo.post(WebInterface.add_right)
                                    .tag(this)
                                    .params("subject", subject0)
                                    .params("signid", onlyID)
                                    .params("questionid", problem.getSid())
                                    .execute(new StringCallback() {
                                        @Override
                                        public void onSuccess(String s, Call call, Response response) {
                                            AllErrorQuestions questions = gson.fromJson(s, AllErrorQuestions.class);
                                        }
                                    });
                            isRight = 1;
                            rightCount++;
                        } else {
                            holder.iv_1.setImageResource(R.mipmap.wrong);
                            holder.tv_1.setTextColor(mContext.getResources().getColor(R.color.wrong_choice_color));


                            errorQuestion.setSid(problem.getSid());
                            errorQuestion.setIsdo(problem.getIsdo());
                            errorQuestion.setChoose(problem.getChoose());
                            errorQuestion.setChapterid(problem.getChapterid());
                            list.add(errorQuestion);


                            if (mRecyclerView.getScrollState() == 0) {
                                if (error_count == 11) {
                                    final MyDialog dialog = new MyDialog(mContext);
                                    dialog.setMessage("错题超过10分，自动提交试卷");
                                    dialog.show();
                                    dialog.setNoOnclickListener("否", new MyDialog.onNoOnclickListener() {
                                        @Override
                                        public void onNoClick() {
                                            handler.postDelayed(runnable, 500);
                                            dialog.dismiss();
                                        }
                                    });
                                    dialog.setYesOnclickListener("是", new MyDialog.onYesOnclickListener() {
                                        @Override
                                        public void onYesClick() {

                                            if (su.showTiming() == 1) {

                                                int score = 0;
                                                if (subject.equals("one")) {
                                                    score = rightCount;
                                                } else {
                                                    score = 2 * rightCount;
                                                }
//                上传成绩到服务器
                                                for (int i = 0; i < datas.size(); i++) {
                                                    map = new HashMap();
                                                    map.put("sid", datas.get(i).getSid());
                                                    map.put("isdo", String.valueOf(datas.get(i).getIsdo()));
                                                    map.put("choose", String.valueOf(datas.get(i).getChoose()));
                                                    list1.add(map);
                                                }
                                                JSONArray jsonObject = new JSONArray(list1);
                                                System.out.println("json=============" + jsonObject.toString());
                                                OkGo.post(WebInterface.theoretical_order_submit)
                                                        .tag(this)
                                                        .params("signid", onlyID)
                                                        .params("type", "2")
                                                        .params("orderid", praticeActivity.orderId)
                                                        .params("use_time", String.valueOf(praticeActivity.time))
                                                        .params("score", String.valueOf(score))
                                                        .params("all_question_info", jsonObject.toString())
                                                        .params("totalnumber", datas.size())
                                                        .params("correctnumber", rightCount)
                                                        .params("wrongnumber", error_count)
                                                        .params("pass_status", "90")
                                                        .execute(new StringCallback() {
                                                            @Override
                                                            public void onSuccess(String s, Call call, Response response) {
                                                                int num = JsonUtils.parseNum(s);
                                                                if (num == 1) {
                                                                    Toast.makeText(mContext, "成绩上传成功", Toast.LENGTH_SHORT).show();
                                                                }
                                                            }
                                                        });
//                                                OkGo.post(WebInterface.lilun_order_info)
//                                                        .tag(this)
//                                                        .params("score", String.valueOf(score))
//                                                        .params("time", String.valueOf(praticeActivity.time))
//                                                        .params("orderid", String.valueOf(praticeActivity.orderId))
//                                                        .params("questioninfo", jsonObject.toString())
//                                                        .execute(new StringCallback() {
//                                                            @Override
//                                                            public void onSuccess(String s, Call call, Response response) {
//                                                                int num= JsonUtils.parseNum(s);
//                                                                if (num==1){
//                                                                    Toast.makeText(mContext, "上传成绩成功", Toast.LENGTH_SHORT).show();
//                                                                }
//                                                            }
//                                                        });
                                            }
//                    提交试卷
                                            //                上传成绩到服务器
//                                            for (int i = 0; i < datas.size(); i++) {
//                                                map = new HashMap();
//                                                map.put("sid", datas.get(i).getSid());
//                                                map.put("isdo", String.valueOf(datas.get(i).getIsdo()));
//                                                map.put("choose", String.valueOf(datas.get(i).getChoose()));
//                                                list1.add(map);
//                                            }
////                                            net.sf.json.JSONArray jsonObject = net.sf.json.JSONArray.fromObject(list1);
//                                            JSONArray jsonObject = new JSONArray(list1);
//                                            OkGo.post(WebInterface.personal_score)
//                                                    .tag(this)
//                                                    .params("score", String.valueOf(rightCount))
//                                                    .params("subject", subject0)
//                                                    .params("cartype", cartype)
//                                                    .params("time", String.valueOf(praticeActivity.time))
//                                                    .params("signid", onlyID)
//                                                    .params("questioninfo", jsonObject.toString())
//                                                    .execute(new StringCallback() {
//                                                        @Override
//                                                        public void onSuccess(String s, Call call, Response response) {
//                                                            System.out.println("返回信息是====" + s);
//                                                        }
//                                                    });
                                            Intent intent = new Intent(mContext, PracticeResultActivity.class);
                                            intent.putExtra("msg", -1);
                                            mContext.startActivity(intent);
                                            ((Activity) mContext).finish();
                                            dialog.dismiss();
                                        }
                                    });
                                } else {
                                    handler.postDelayed(runnable, 500);
                                }

                            }
//                           将错题发送到账号内
                            OkGo.post(WebInterface.add_error)
                                    .tag(this)
                                    .params("subject", subject0)
                                    .params("signid", onlyID)
                                    .params("questionid", problem.getSid())
//                                    .params("option", "1")
                                    .execute(new StringCallback() {
                                        @Override
                                        public void onSuccess(String s, Call call, Response response) {
                                            AllErrorQuestions questions = gson.fromJson(s, AllErrorQuestions.class);
                                        }
                                    });
//                            dao.addErrorQuestions(problem, "1");//添加错题到错题库
                            isRight = 2;
                            error_count++;
                        }
                    }
                });
                holder.choice_2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        problem.setIsdo(1);
                        problem.setChoose(2);
                        holder.choice_1.setEnabled(false);
                        if (problem.getAnswer().equals("0")) {
                            holder.answer.setText("对");
                            holder.iv_2.setImageResource(R.mipmap.right);
                            holder.tv_2.setTextColor(mContext.getResources().getColor(R.color.right_choice_color));
                            if (mRecyclerView.getScrollState() == 0) {
                                handler.postDelayed(runnable, 500);
                            }
                            OkGo.post(WebInterface.add_right)
                                    .tag(this)
                                    .params("subject", subject0)
                                    .params("signid", onlyID)
                                    .params("questionid", problem.getSid())
                                    .execute(new StringCallback() {
                                        @Override
                                        public void onSuccess(String s, Call call, Response response) {
                                            AllErrorQuestions questions = gson.fromJson(s, AllErrorQuestions.class);
                                        }
                                    });
                            isRight = 1;
                            rightCount++;
                        } else {
//                            holder.answer.setVisibility(View.VISIBLE);
//                            holder.ll_explain.setVisibility(View.VISIBLE);
                            holder.iv_2.setImageResource(R.mipmap.wrong);
                            holder.tv_2.setTextColor(mContext.getResources().getColor(R.color.wrong_choice_color));

                            errorQuestion.setSid(problem.getSid());
                            errorQuestion.setIsdo(problem.getIsdo());
                            errorQuestion.setChoose(problem.getChoose());
                            errorQuestion.setChapterid(problem.getChapterid());
                            list.add(errorQuestion);

                            if (mRecyclerView.getScrollState() == 0) {
                                if (error_count == 11) {
                                    final MyDialog dialog = new MyDialog(mContext);
                                    dialog.setMessage("错题超过10分，自动提交试卷");
                                    dialog.show();
                                    dialog.setNoOnclickListener("否", new MyDialog.onNoOnclickListener() {
                                        @Override
                                        public void onNoClick() {
                                            handler.postDelayed(runnable, 500);
                                            dialog.dismiss();
                                        }
                                    });
                                    dialog.setYesOnclickListener("是", new MyDialog.onYesOnclickListener() {
                                        @Override
                                        public void onYesClick() {
//                    提交试卷
                                            if (su.showTiming() == 1) {

                                                int score = 0;
                                                if (subject.equals("one")) {
                                                    score = rightCount;
                                                } else {
                                                    score = 2 * rightCount;
                                                }
//                上传成绩到服务器
                                                for (int i = 0; i < datas.size(); i++) {
                                                    map = new HashMap();
                                                    map.put("sid", datas.get(i).getSid());
                                                    map.put("isdo", String.valueOf(datas.get(i).getIsdo()));
                                                    map.put("choose", String.valueOf(datas.get(i).getChoose()));
                                                    list1.add(map);
                                                }
                                                JSONArray jsonObject = new JSONArray(list1);
                                                System.out.println("json=============" + jsonObject.toString());
                                                OkGo.post(WebInterface.theoretical_order_submit)
                                                        .tag(this)
                                                        .params("signid", onlyID)
                                                        .params("type", "2")
                                                        .params("orderid", praticeActivity.orderId)
                                                        .params("use_time", String.valueOf(praticeActivity.time))
                                                        .params("score", String.valueOf(score))
                                                        .params("all_question_info", jsonObject.toString())
                                                        .params("totalnumber", datas.size())
                                                        .params("correctnumber", rightCount)
                                                        .params("wrongnumber", error_count)
                                                        .params("pass_status", "90")
                                                        .execute(new StringCallback() {
                                                            @Override
                                                            public void onSuccess(String s, Call call, Response response) {
                                                                int num = JsonUtils.parseNum(s);
                                                                if (num == 1) {
                                                                    Toast.makeText(mContext, "成绩上传成功", Toast.LENGTH_SHORT).show();
                                                                }
                                                            }
                                                        });
//                                                OkGo.post(WebInterface.lilun_order_info)
//                                                        .tag(this)
//                                                        .params("score", String.valueOf(score))
//                                                        .params("time", String.valueOf(praticeActivity.time))
//                                                        .params("orderid", String.valueOf(praticeActivity.orderId))
//                                                        .params("questioninfo", jsonObject.toString())
//                                                        .execute(new StringCallback() {
//                                                            @Override
//                                                            public void onSuccess(String s, Call call, Response response) {
//                                                                int num= JsonUtils.parseNum(s);
//                                                                if (num==1){
//                                                                    Toast.makeText(mContext, "上传成绩成功", Toast.LENGTH_SHORT).show();
//                                                                }
//                                                            }
//                                                        });
                                            }
//                    提交试卷
                                            //                上传成绩到服务器
//                                            for (int i = 0; i < datas.size(); i++) {
//                                                map = new HashMap();
//                                                map.put("sid", datas.get(i).getSid());
//                                                map.put("isdo", String.valueOf(datas.get(i).getIsdo()));
//                                                map.put("choose", String.valueOf(datas.get(i).getChoose()));
//                                                list1.add(map);
//                                            }
////                                            net.sf.json.JSONArray jsonObject = net.sf.json.JSONArray.fromObject(list1);
//                                            JSONArray jsonObject = new JSONArray(list1);
//                                            OkGo.post(WebInterface.personal_score)
//                                                    .tag(this)
//                                                    .params("score", String.valueOf(rightCount))
//                                                    .params("subject", subject0)
//                                                    .params("cartype", cartype)
//                                                    .params("time", String.valueOf(praticeActivity.time))
//                                                    .params("signid", onlyID)
//                                                    .params("questioninfo", jsonObject.toString())
//                                                    .execute(new StringCallback() {
//                                                        @Override
//                                                        public void onSuccess(String s, Call call, Response response) {
//                                                            System.out.println("返回信息是====" + s);
//                                                        }
//                                                    });
                                            Intent intent = new Intent(mContext, PracticeResultActivity.class);
                                            intent.putExtra("msg", -1);
                                            mContext.startActivity(intent);
                                            ((Activity) mContext).finish();
                                            dialog.dismiss();
                                        }
                                    });
                                } else {
                                    handler.postDelayed(runnable, 500);
                                }

                            }
                            //                           将错题发送到账号内
                            OkGo.post(WebInterface.add_error)
                                    .tag(this)
                                    .params("subject", subject0)
                                    .params("signid", onlyID)
                                    .params("questionid", problem.getSid())
//                                    .params("option", "2")
                                    .execute(new StringCallback() {
                                        @Override
                                        public void onSuccess(String s, Call call, Response response) {
                                            AllErrorQuestions questions = gson.fromJson(s, AllErrorQuestions.class);
                                        }
                                    });
//                            dao.addErrorQuestions(problem, "1");//添加错题到错题库
                            isRight = 2;
                            error_count++;
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
                        if (problem.getAnswer().equals("1")) {
                            holder.iv_1.setImageResource(R.mipmap.right);
                            isRight = 1;
                            rightCount++;
                            holder.tv_1.setTextColor(mContext.getResources().getColor(R.color.right_choice_color));
                            if (error_count < 11 && mRecyclerView.getScrollState() == 0) {
                                handler.postDelayed(runnable, 500);
                            }
                            OkGo.post(WebInterface.add_right)
                                    .tag(this)
                                    .params("subject", subject0)
                                    .params("signid", onlyID)
                                    .params("questionid", problem.getSid())
                                    .execute(new StringCallback() {
                                        @Override
                                        public void onSuccess(String s, Call call, Response response) {
                                            AllErrorQuestions questions = gson.fromJson(s, AllErrorQuestions.class);
                                        }
                                    });

                        } else {
//                            holder.answer.setVisibility(View.VISIBLE);
//                            holder.ll_explain.setVisibility(View.VISIBLE);
                            holder.iv_1.setImageResource(R.mipmap.wrong);
                            holder.tv_1.setTextColor(mContext.getResources().getColor(R.color.wrong_choice_color));
                            error_count++;

                            errorQuestion.setSid(problem.getSid());
                            errorQuestion.setIsdo(problem.getIsdo());
                            errorQuestion.setChoose(problem.getChoose());
                            errorQuestion.setChapterid(problem.getChapterid());
                            list.add(errorQuestion);

                            if (mRecyclerView.getScrollState() == 0) {
                                if (error_count == 11) {

                                    final MyDialog dialog = new MyDialog(mContext);
                                    dialog.setMessage("错题超过10分，自动提交试卷");
                                    dialog.show();
                                    dialog.setNoOnclickListener("否", new MyDialog.onNoOnclickListener() {
                                        @Override
                                        public void onNoClick() {
                                            handler.postDelayed(runnable, 500);
                                            dialog.dismiss();
                                        }
                                    });
                                    dialog.setYesOnclickListener("是", new MyDialog.onYesOnclickListener() {
                                        @Override
                                        public void onYesClick() {
//                    提交试卷
                                            if (su.showTiming() == 1) {

                                                int score = 0;
                                                if (subject.equals("one")) {
                                                    score = rightCount;
                                                } else {
                                                    score = 2 * rightCount;
                                                }
//                上传成绩到服务器
                                                for (int i = 0; i < datas.size(); i++) {
                                                    map = new HashMap();
                                                    map.put("sid", datas.get(i).getSid());
                                                    map.put("isdo", String.valueOf(datas.get(i).getIsdo()));
                                                    map.put("choose", String.valueOf(datas.get(i).getChoose()));
                                                    list1.add(map);
                                                }
                                                JSONArray jsonObject = new JSONArray(list1);
                                                System.out.println("json=============" + jsonObject.toString());
                                                OkGo.post(WebInterface.theoretical_order_submit)
                                                        .tag(this)
                                                        .params("signid", onlyID)
                                                        .params("type", "2")
                                                        .params("orderid", praticeActivity.orderId)
                                                        .params("use_time", String.valueOf(praticeActivity.time))
                                                        .params("score", String.valueOf(score))
                                                        .params("all_question_info", jsonObject.toString())
                                                        .params("totalnumber", datas.size())
                                                        .params("correctnumber", rightCount)
                                                        .params("wrongnumber", error_count)
                                                        .params("pass_status", "90")
                                                        .execute(new StringCallback() {
                                                            @Override
                                                            public void onSuccess(String s, Call call, Response response) {
                                                                int num = JsonUtils.parseNum(s);
                                                                if (num == 1) {
                                                                    Toast.makeText(mContext, "成绩上传成功", Toast.LENGTH_SHORT).show();
                                                                }
                                                            }
                                                        });
//                                                OkGo.post(WebInterface.lilun_order_info)
//                                                        .tag(this)
//                                                        .params("score", String.valueOf(score))
//                                                        .params("time", String.valueOf(praticeActivity.time))
//                                                        .params("orderid", String.valueOf(praticeActivity.orderId))
//                                                        .params("questioninfo", jsonObject.toString())
//                                                        .execute(new StringCallback() {
//                                                            @Override
//                                                            public void onSuccess(String s, Call call, Response response) {
//                                                                int num= JsonUtils.parseNum(s);
//                                                                if (num==1){
//                                                                    Toast.makeText(mContext, "上传成绩成功", Toast.LENGTH_SHORT).show();
//                                                                }
//                                                            }
//                                                        });
                                            }
//                    提交试卷
                                            //                上传成绩到服务器
//                                            for (int i = 0; i < datas.size(); i++) {
//                                                map = new HashMap();
//                                                map.put("sid", datas.get(i).getSid());
//                                                map.put("isdo", String.valueOf(datas.get(i).getIsdo()));
//                                                map.put("choose", String.valueOf(datas.get(i).getChoose()));
//                                                list1.add(map);
//                                            }
////                                            net.sf.json.JSONArray jsonObject = net.sf.json.JSONArray.fromObject(list1);
//                                            JSONArray jsonObject = new JSONArray(list1);
//                                            OkGo.post(WebInterface.personal_score)
//                                                    .tag(this)
//                                                    .params("score", String.valueOf(rightCount))
//                                                    .params("subject", subject0)
//                                                    .params("cartype", cartype)
//                                                    .params("time", String.valueOf(praticeActivity.time))
//                                                    .params("signid", onlyID)
//                                                    .params("questioninfo", jsonObject.toString())
//                                                    .execute(new StringCallback() {
//                                                        @Override
//                                                        public void onSuccess(String s, Call call, Response response) {
//                                                            System.out.println("返回信息是====" + s);
//                                                        }
//                                                    });
                                            Intent intent = new Intent(mContext, PracticeResultActivity.class);
                                            intent.putExtra("msg", -1);
                                            mContext.startActivity(intent);
                                            ((Activity) mContext).finish();
                                            dialog.dismiss();
                                        }
                                    });
                                } else {
                                    handler.postDelayed(runnable, 500);
                                }

                            }
                            //                           将错题发送到账号内
                            OkGo.post(WebInterface.add_error)
                                    .tag(this)
                                    .params("subject", subject0)
                                    .params("signid", onlyID)
                                    .params("questionid", problem.getSid())
//                                    .params("option", "1")
                                    .execute(new StringCallback() {
                                        @Override
                                        public void onSuccess(String s, Call call, Response response) {
                                            AllErrorQuestions questions = gson.fromJson(s, AllErrorQuestions.class);
                                        }
                                    });
//                            dao.addErrorQuestions(problem, "1");//添加错题到错题库

                        }
                    }
                });
                holder.choice_2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        problem.setIsdo(1);
                        problem.setChoose(2);
                        holder.choice_1.setEnabled(false);
                        holder.choice_3.setEnabled(false);
                        holder.choice_4.setEnabled(false);
                        if (problem.getAnswer().equals("2")) {
                            holder.iv_2.setImageResource(R.mipmap.right);
                            holder.tv_2.setTextColor(mContext.getResources().getColor(R.color.right_choice_color));
                            isRight = 1;
                            rightCount++;
                            if (mRecyclerView.getScrollState() == 0) {
                                handler.postDelayed(runnable, 500);
                            }
                            OkGo.post(WebInterface.add_right)
                                    .tag(this)
                                    .params("subject", subject0)
                                    .params("signid", onlyID)
                                    .params("questionid", problem.getSid())
                                    .execute(new StringCallback() {
                                        @Override
                                        public void onSuccess(String s, Call call, Response response) {
                                            AllErrorQuestions questions = gson.fromJson(s, AllErrorQuestions.class);
                                        }
                                    });

                        } else {
//                            holder.answer.setVisibility(View.VISIBLE);
//                            holder.ll_explain.setVisibility(View.VISIBLE);
                            holder.iv_2.setImageResource(R.mipmap.wrong);
                            holder.tv_2.setTextColor(mContext.getResources().getColor(R.color.wrong_choice_color));
                            error_count++;

                            errorQuestion.setSid(problem.getSid());
                            errorQuestion.setIsdo(problem.getIsdo());
                            errorQuestion.setChoose(problem.getChoose());
                            errorQuestion.setChapterid(problem.getChapterid());
                            list.add(errorQuestion);

                            if (mRecyclerView.getScrollState() == 0) {
                                if (error_count == 11) {
                                    final MyDialog dialog = new MyDialog(mContext);
                                    dialog.setMessage("错题超过10分，自动提交试卷");
                                    dialog.show();
                                    dialog.setNoOnclickListener("否", new MyDialog.onNoOnclickListener() {
                                        @Override
                                        public void onNoClick() {
                                            handler.postDelayed(runnable, 500);
                                            dialog.dismiss();
                                        }
                                    });
                                    dialog.setYesOnclickListener("是", new MyDialog.onYesOnclickListener() {
                                        @Override
                                        public void onYesClick() {
//                    提交试卷
                                            if (su.showTiming() == 1) {

                                                int score = 0;
                                                if (subject.equals("one")) {
                                                    score = rightCount;
                                                } else {
                                                    score = 2 * rightCount;
                                                }
//                上传成绩到服务器
                                                for (int i = 0; i < datas.size(); i++) {
                                                    map = new HashMap();
                                                    map.put("sid", datas.get(i).getSid());
                                                    map.put("isdo", String.valueOf(datas.get(i).getIsdo()));
                                                    map.put("choose", String.valueOf(datas.get(i).getChoose()));
                                                    list1.add(map);
                                                }
                                                JSONArray jsonObject = new JSONArray(list1);
                                                System.out.println("json=============" + jsonObject.toString());
                                                OkGo.post(WebInterface.theoretical_order_submit)
                                                        .tag(this)
                                                        .params("signid", onlyID)
                                                        .params("type", "2")
                                                        .params("orderid", praticeActivity.orderId)
                                                        .params("use_time", String.valueOf(praticeActivity.time))
                                                        .params("score", String.valueOf(score))
                                                        .params("all_question_info", jsonObject.toString())
                                                        .params("totalnumber", datas.size())
                                                        .params("correctnumber", rightCount)
                                                        .params("wrongnumber", error_count)
                                                        .params("pass_status", "90")
                                                        .execute(new StringCallback() {
                                                            @Override
                                                            public void onSuccess(String s, Call call, Response response) {
                                                                int num = JsonUtils.parseNum(s);
                                                                if (num == 1) {
                                                                    Toast.makeText(mContext, "成绩上传成功", Toast.LENGTH_SHORT).show();
                                                                }
                                                            }
                                                        });
//                                                OkGo.post(WebInterface.lilun_order_info)
//                                                        .tag(this)
//                                                        .params("score", String.valueOf(score))
//                                                        .params("time", String.valueOf(praticeActivity.time))
//                                                        .params("orderid", String.valueOf(praticeActivity.orderId))
//                                                        .params("questioninfo", jsonObject.toString())
//                                                        .execute(new StringCallback() {
//                                                            @Override
//                                                            public void onSuccess(String s, Call call, Response response) {
//                                                                int num= JsonUtils.parseNum(s);
//                                                                if (num==1){
//                                                                    Toast.makeText(mContext, "上传成绩成功", Toast.LENGTH_SHORT).show();
//                                                                }
//                                                            }
//                                                        });
                                            }
//                    提交试卷
                                            //                上传成绩到服务器
//                                            for (int i = 0; i < datas.size(); i++) {
//                                                map = new HashMap();
//                                                map.put("sid", datas.get(i).getSid());
//                                                map.put("isdo", String.valueOf(datas.get(i).getIsdo()));
//                                                map.put("choose", String.valueOf(datas.get(i).getChoose()));
//                                                list1.add(map);
//                                            }
////                                            net.sf.json.JSONArray jsonObject = net.sf.json.JSONArray.fromObject(list1);
//                                            JSONArray jsonObject = new JSONArray(list1);
//                                            OkGo.post(WebInterface.personal_score)
//                                                    .tag(this)
//                                                    .params("score", String.valueOf(rightCount))
//                                                    .params("subject", subject0)
//                                                    .params("cartype", cartype)
//                                                    .params("time", String.valueOf(praticeActivity.time))
//                                                    .params("signid", onlyID)
//                                                    .params("questioninfo", jsonObject.toString())
//                                                    .execute(new StringCallback() {
//                                                        @Override
//                                                        public void onSuccess(String s, Call call, Response response) {
//                                                            System.out.println("返回信息是====" + s);
//                                                        }
//                                                    });
                                            Intent intent = new Intent(mContext, PracticeResultActivity.class);
                                            intent.putExtra("msg", -1);
                                            mContext.startActivity(intent);
                                            ((Activity) mContext).finish();
                                            dialog.dismiss();
                                        }
                                    });
                                } else {
                                    handler.postDelayed(runnable, 500);
                                }

                            }
                            //                           将错题发送到账号内
                            OkGo.post(WebInterface.add_error)
                                    .tag(this).params("subject", subject0)
                                    .params("signid", onlyID)
                                    .params("questionid", problem.getSid())
//                                    .params("option", "2")
                                    .execute(new StringCallback() {
                                        @Override
                                        public void onSuccess(String s, Call call, Response response) {
                                            AllErrorQuestions questions = gson.fromJson(s, AllErrorQuestions.class);
                                        }
                                    });
//                            dao.addErrorQuestions(problem, "1");//添加错题到错题库

                        }
                    }
                });
                holder.choice_3.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        problem.setIsdo(1);
                        problem.setChoose(4);
                        holder.choice_2.setEnabled(false);
                        holder.choice_1.setEnabled(false);
                        holder.choice_4.setEnabled(false);
                        if (problem.getAnswer().equals("4")) {
                            holder.iv_3.setImageResource(R.mipmap.right);
                            holder.tv_3.setTextColor(mContext.getResources().getColor(R.color.right_choice_color));
                            rightCount++;
                            if (mRecyclerView.getScrollState() == 0) {
                                handler.postDelayed(runnable, 500);
                            }
                            OkGo.post(WebInterface.add_right)
                                    .tag(this)
                                    .params("subject", subject0)
                                    .params("signid", onlyID)
                                    .params("questionid", problem.getSid())
                                    .execute(new StringCallback() {
                                        @Override
                                        public void onSuccess(String s, Call call, Response response) {
                                            AllErrorQuestions questions = gson.fromJson(s, AllErrorQuestions.class);
                                        }
                                    });

                        } else {
//                            holder.answer.setVisibility(View.VISIBLE);
//                            holder.ll_explain.setVisibility(View.VISIBLE);
                            holder.iv_3.setImageResource(R.mipmap.wrong);
                            holder.tv_3.setTextColor(mContext.getResources().getColor(R.color.wrong_choice_color));
                            error_count++;

                            errorQuestion.setSid(problem.getSid());
                            errorQuestion.setIsdo(problem.getIsdo());
                            errorQuestion.setChoose(problem.getChoose());
                            errorQuestion.setChapterid(problem.getChapterid());
                            list.add(errorQuestion);


                            if (mRecyclerView.getScrollState() == 0) {
                                if (error_count == 11) {
                                    final MyDialog dialog = new MyDialog(mContext);
                                    dialog.setMessage("错题超过10分，自动提交试卷");
                                    dialog.show();
                                    dialog.setNoOnclickListener("否", new MyDialog.onNoOnclickListener() {
                                        @Override
                                        public void onNoClick() {
                                            handler.postDelayed(runnable, 500);
                                            dialog.dismiss();
                                        }
                                    });
                                    dialog.setYesOnclickListener("是", new MyDialog.onYesOnclickListener() {
                                        @Override
                                        public void onYesClick() {
//                    提交试卷
                                            if (su.showTiming() == 1) {

                                                int score = 0;
                                                if (subject.equals("one")) {
                                                    score = rightCount;
                                                } else {
                                                    score = 2 * rightCount;
                                                }
//                上传成绩到服务器
                                                for (int i = 0; i < datas.size(); i++) {
                                                    map = new HashMap();
                                                    map.put("sid", datas.get(i).getSid());
                                                    map.put("isdo", String.valueOf(datas.get(i).getIsdo()));
                                                    map.put("choose", String.valueOf(datas.get(i).getChoose()));
                                                    list1.add(map);
                                                }
                                                JSONArray jsonObject = new JSONArray(list1);
                                                System.out.println("json=============" + jsonObject.toString());
                                                OkGo.post(WebInterface.theoretical_order_submit)
                                                        .tag(this)
                                                        .params("signid", onlyID)
                                                        .params("type", "2")
                                                        .params("orderid", praticeActivity.orderId)
                                                        .params("use_time", String.valueOf(praticeActivity.time))
                                                        .params("score", String.valueOf(score))
                                                        .params("all_question_info", jsonObject.toString())
                                                        .params("totalnumber", datas.size())
                                                        .params("correctnumber", rightCount)
                                                        .params("wrongnumber", error_count)
                                                        .params("pass_status", "90")
                                                        .execute(new StringCallback() {
                                                            @Override
                                                            public void onSuccess(String s, Call call, Response response) {
                                                                int num = JsonUtils.parseNum(s);
                                                                if (num == 1) {
                                                                    Toast.makeText(mContext, "成绩上传成功", Toast.LENGTH_SHORT).show();
                                                                }
                                                            }
                                                        });
//                                                OkGo.post(WebInterface.lilun_order_info)
//                                                        .tag(this)
//                                                        .params("score", String.valueOf(score))
//                                                        .params("time", String.valueOf(praticeActivity.time))
//                                                        .params("orderid", String.valueOf(praticeActivity.orderId))
//                                                        .params("questioninfo", jsonObject.toString())
//                                                        .execute(new StringCallback() {
//                                                            @Override
//                                                            public void onSuccess(String s, Call call, Response response) {
//                                                                int num= JsonUtils.parseNum(s);
//                                                                if (num==1){
//                                                                    Toast.makeText(mContext, "上传成绩成功", Toast.LENGTH_SHORT).show();
//                                                                }
//                                                            }
//                                                        });
                                            }
//                    提交试卷
                                            //                上传成绩到服务器
//                                            for (int i = 0; i < datas.size(); i++) {
//                                                map = new HashMap();
//                                                map.put("sid", datas.get(i).getSid());
//                                                map.put("isdo", String.valueOf(datas.get(i).getIsdo()));
//                                                map.put("choose", String.valueOf(datas.get(i).getChoose()));
//                                                list1.add(map);
//                                            }
////                                            net.sf.json.JSONArray jsonObject = net.sf.json.JSONArray.fromObject(list1);
//                                            JSONArray jsonObject = new JSONArray(list1);
//                                            OkGo.post(WebInterface.personal_score)
//                                                    .tag(this)
//                                                    .params("score", String.valueOf(rightCount))
//                                                    .params("subject", subject0)
//                                                    .params("cartype", cartype)
//                                                    .params("time", String.valueOf(praticeActivity.time))
//                                                    .params("signid", onlyID)
//                                                    .params("questioninfo", jsonObject.toString())
//                                                    .execute(new StringCallback() {
//                                                        @Override
//                                                        public void onSuccess(String s, Call call, Response response) {
//                                                            System.out.println("返回信息是====" + s);
//                                                        }
//                                                    });
                                            Intent intent = new Intent(mContext, PracticeResultActivity.class);
                                            intent.putExtra("msg", -1);
                                            mContext.startActivity(intent);
                                            ((Activity) mContext).finish();
                                            dialog.dismiss();
                                        }
                                    });
                                } else {
                                    handler.postDelayed(runnable, 500);
                                }

                            }
                            //                           将错题发送到账号内
                            OkGo.post(WebInterface.add_error)
                                    .tag(this)
                                    .params("subject", subject0)
                                    .params("signid", onlyID)
                                    .params("questionid", problem.getSid())
//                                    .params("option", "4")
                                    .execute(new StringCallback() {
                                        @Override
                                        public void onSuccess(String s, Call call, Response response) {
                                            AllErrorQuestions questions = gson.fromJson(s, AllErrorQuestions.class);
                                        }
                                    });
//                            dao.addErrorQuestions(problem, "1");//添加错题到错题库

                        }
                    }
                });
                holder.choice_4.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        problem.setIsdo(1);
                        problem.setChoose(8);
                        holder.choice_2.setEnabled(false);
                        holder.choice_3.setEnabled(false);
                        holder.choice_1.setEnabled(false);
                        if (problem.getAnswer().equals("8")) {
                            holder.iv_4.setImageResource(R.mipmap.right);
                            holder.tv_4.setTextColor(mContext.getResources().getColor(R.color.right_choice_color));
                            rightCount++;
                            if (mRecyclerView.getScrollState() == 0) {
                                handler.postDelayed(runnable, 500);
                            }
                            OkGo.post(WebInterface.add_right)
                                    .tag(this)
                                    .params("subject", subject0)
                                    .params("signid", onlyID)
                                    .params("questionid", problem.getSid())
                                    .execute(new StringCallback() {
                                        @Override
                                        public void onSuccess(String s, Call call, Response response) {
                                            AllErrorQuestions questions = gson.fromJson(s, AllErrorQuestions.class);
                                        }
                                    });

                        } else {
//                            holder.answer.setVisibility(View.VISIBLE);
//                            holder.ll_explain.setVisibility(View.VISIBLE);
                            holder.iv_4.setImageResource(R.mipmap.wrong);
                            holder.tv_4.setTextColor(mContext.getResources().getColor(R.color.wrong_choice_color));
                            error_count++;

                            errorQuestion.setSid(problem.getSid());
                            errorQuestion.setIsdo(problem.getIsdo());
                            errorQuestion.setChoose(problem.getChoose());
                            errorQuestion.setChapterid(problem.getChapterid());
                            list.add(errorQuestion);

                            if (mRecyclerView.getScrollState() == 0) {
                                if (error_count == 11) {
                                    final MyDialog dialog = new MyDialog(mContext);
                                    dialog.setMessage("错题超过10分，自动提交试卷");
                                    dialog.show();
                                    dialog.setNoOnclickListener("否", new MyDialog.onNoOnclickListener() {
                                        @Override
                                        public void onNoClick() {
                                            handler.postDelayed(runnable, 500);
                                            dialog.dismiss();
                                        }
                                    });
                                    dialog.setYesOnclickListener("是", new MyDialog.onYesOnclickListener() {
                                        @Override
                                        public void onYesClick() {
//                    提交试卷

                                            if (su.showTiming() == 1) {

                                                int score = 0;
                                                if (subject.equals("one")) {
                                                    score = rightCount;
                                                } else {
                                                    score = 2 * rightCount;
                                                }
//                上传成绩到服务器
                                                for (int i = 0; i < datas.size(); i++) {
                                                    map = new HashMap();
                                                    map.put("sid", datas.get(i).getSid());
                                                    map.put("isdo", String.valueOf(datas.get(i).getIsdo()));
                                                    map.put("choose", String.valueOf(datas.get(i).getChoose()));
                                                    list1.add(map);
                                                }
                                                JSONArray jsonObject = new JSONArray(list1);
                                                System.out.println("json=============" + jsonObject.toString());
                                                OkGo.post(WebInterface.theoretical_order_submit)
                                                        .tag(this)
                                                        .params("signid", onlyID)
                                                        .params("type", "2")
                                                        .params("orderid", praticeActivity.orderId)
                                                        .params("use_time", String.valueOf(praticeActivity.time))
                                                        .params("score", String.valueOf(score))
                                                        .params("all_question_info", jsonObject.toString())
                                                        .params("totalnumber", datas.size())
                                                        .params("correctnumber", rightCount)
                                                        .params("wrongnumber", error_count)
                                                        .params("pass_status", "90")
                                                        .execute(new StringCallback() {
                                                            @Override
                                                            public void onSuccess(String s, Call call, Response response) {
                                                                int num = JsonUtils.parseNum(s);
                                                                if (num == 1) {
                                                                    Toast.makeText(mContext, "成绩上传成功", Toast.LENGTH_SHORT).show();
                                                                }
                                                            }
                                                        });
//                                                OkGo.post(WebInterface.lilun_order_info)
//                                                        .tag(this)
//                                                        .params("score", String.valueOf(score))
//                                                        .params("time", String.valueOf(praticeActivity.time))
//                                                        .params("orderid", String.valueOf(praticeActivity.orderId))
//                                                        .params("questioninfo", jsonObject.toString())
//                                                        .execute(new StringCallback() {
//                                                            @Override
//                                                            public void onSuccess(String s, Call call, Response response) {
//                                                                int num= JsonUtils.parseNum(s);
//                                                                if (num==1){
//                                                                    Toast.makeText(mContext, "上传成绩成功", Toast.LENGTH_SHORT).show();
//                                                                }
//                                                            }
//                                                        });
                                            }
//                    提交试卷
                                            //                上传成绩到服务器
//                                            for (int i = 0; i < datas.size(); i++) {
//                                                map = new HashMap();
//                                                map.put("sid", datas.get(i).getSid());
//                                                map.put("isdo", String.valueOf(datas.get(i).getIsdo()));
//                                                map.put("choose", String.valueOf(datas.get(i).getChoose()));
//                                                list1.add(map);
//                                            }
////                                            net.sf.json.JSONArray jsonObject = net.sf.json.JSONArray.fromObject(list1);
//                                            JSONArray jsonObject = new JSONArray(list1);
//                                            OkGo.post(WebInterface.personal_score)
//                                                    .tag(this)
//                                                    .params("score", String.valueOf(rightCount))
//                                                    .params("subject", subject0)
//                                                    .params("cartype", cartype)
//                                                    .params("time", String.valueOf(praticeActivity.time))
//                                                    .params("signid", onlyID)
//                                                    .params("questioninfo", jsonObject.toString())
//                                                    .execute(new StringCallback() {
//                                                        @Override
//                                                        public void onSuccess(String s, Call call, Response response) {
//                                                            System.out.println("返回信息是====" + s);
//                                                        }
//                                                    });
                                            Intent intent = new Intent(mContext, PracticeResultActivity.class);
                                            intent.putExtra("msg", -1);
                                            mContext.startActivity(intent);
                                            ((Activity) mContext).finish();
                                            dialog.dismiss();
                                        }
                                    });
                                } else {
                                    handler.postDelayed(runnable, 500);
                                }

                            }
                            //                           将错题发送到账号内
                            OkGo.post(WebInterface.add_error)
                                    .tag(this)
                                    .params("subject", subject0)
                                    .params("signid", onlyID)
                                    .params("questionid", problem.getSid())
//                                    .params("option", "8")
                                    .execute(new StringCallback() {
                                        @Override
                                        public void onSuccess(String s, Call call, Response response) {
                                            AllErrorQuestions questions = gson.fromJson(s, AllErrorQuestions.class);
                                        }
                                    });
//                            dao.addErrorQuestions(problem, subject);//添加错题到错题库

                        }
                    }
                });
            } else if (problem.getType().equals("1")) {//多选题
                final List<Integer> l = new ArrayList<>();

                holder.choice_1.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View view) {
                        if (holder.choice_1.isSelected()) {
                            holder.choice_1.setSelected(false);
                            l.remove(l.size() - 1);
                        } else {
                            holder.choice_1.setSelected(true);
                            l.add(1);
                        }
                        if (l.size() > 1) {
                            holder.btn_sure.setEnabled(true);
                        } else {
                            holder.btn_sure.setEnabled(false);
                        }
                    }
                });
                holder.choice_2.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View view) {
                        if (holder.choice_2.isSelected()) {
                            holder.choice_2.setSelected(false);
                            l.remove(l.size() - 1);
                        } else {
                            holder.choice_2.setSelected(true);
                            l.add(1);
                        }
                        if (l.size() > 1) {
                            holder.btn_sure.setEnabled(true);
                        } else {
                            holder.btn_sure.setEnabled(false);
                        }

                    }
                });
                holder.choice_3.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View view) {
                        if (holder.choice_3.isSelected()) {
                            holder.choice_3.setSelected(false);
                            l.remove(l.size() - 1);
                        } else {
                            holder.choice_3.setSelected(true);
                            l.add(1);
                        }
                        if (l.size() > 1) {
                            holder.btn_sure.setEnabled(true);
                        } else {
                            holder.btn_sure.setEnabled(false);
                        }

                    }
                });
                holder.choice_4.setOnClickListener(new View.OnClickListener() {


                    @Override
                    public void onClick(View view) {
                        if (holder.choice_4.isSelected()) {
                            holder.choice_4.setSelected(false);
                            l.remove(l.size() - 1);
                        } else {
                            holder.choice_4.setSelected(true);
                            l.add(1);
                        }
                        if (l.size() > 1) {
                            holder.btn_sure.setEnabled(true);
                        } else {
                            holder.btn_sure.setEnabled(false);
                        }

//
                    }

                });


                holder.btn_sure.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View view) {
                        String myAnswer = "";
                        for (int i = 0; i < 4; i++) {
                            if (holder.choice_4.isSelected() && i == 0) {
                                myAnswer = myAnswer.concat("1");
                            } else if (!holder.choice_4.isSelected() && i == 0) {
                                myAnswer = myAnswer.concat("0");
                            } else if (holder.choice_3.isSelected() && i == 1) {
                                myAnswer = myAnswer.concat("1");
                            } else if (!holder.choice_3.isSelected() && i == 1) {
                                myAnswer = myAnswer.concat("0");
                            } else if (holder.choice_2.isSelected() && i == 2) {
                                myAnswer = myAnswer.concat("1");
                            } else if (!holder.choice_2.isSelected() && i == 2) {
                                myAnswer = myAnswer.concat("0");
                            } else if (holder.choice_1.isSelected() && i == 3) {
                                myAnswer = myAnswer.concat("1");
                            } else if (!holder.choice_1.isSelected() && i == 3) {
                                myAnswer = myAnswer.concat("0");
                            }
                        }

                        System.out.println("myAnswer==========" + myAnswer);
                        String answer = Integer.toBinaryString(Integer.parseInt(problem.getAnswer()));
                        if (answer.length() == 1) {
                            answer = "000".concat(answer);
                        } else if (answer.length() == 2) {
                            answer = "00".concat(answer);
                        } else if (answer.length() == 3) {
                            answer = "0".concat(answer);
                        }
                        System.out.println("Answer==========" + answer);
                        if (String.valueOf(myAnswer.charAt(0)).equals("1") && String.valueOf(answer.charAt(0)).equals("0")) {//错误
                            holder.iv_4.setImageResource(R.mipmap.wrong);
                            holder.tv_4.setTextColor(mContext.getResources().getColor(R.color.wrong_choice_color));
                        } else if (String.valueOf(myAnswer.charAt(0)).equals("1") && String.valueOf(answer.charAt(0)).equals("1")) {
                            holder.iv_4.setImageResource(R.mipmap.right);
                            holder.tv_4.setTextColor(mContext.getResources().getColor(R.color.right_choice_color));
                        } else if (String.valueOf(myAnswer.charAt(0)).equals("0") && String.valueOf(answer.charAt(0)).equals("1")) {
                            holder.iv_4.setImageResource(R.mipmap.right);
                            holder.tv_4.setTextColor(mContext.getResources().getColor(R.color.right_choice_color));
                        }
                        if (String.valueOf(myAnswer.charAt(1)).equals("1") && String.valueOf(answer.charAt(1)).equals("0")) {//错误
                            holder.iv_3.setImageResource(R.mipmap.wrong);
                            holder.tv_3.setTextColor(mContext.getResources().getColor(R.color.wrong_choice_color));
                        } else if (String.valueOf(myAnswer.charAt(1)).equals("1") && String.valueOf(answer.charAt(1)).equals("1")) {
                            holder.iv_3.setImageResource(R.mipmap.right);
                            holder.tv_3.setTextColor(mContext.getResources().getColor(R.color.right_choice_color));
                        } else if (String.valueOf(myAnswer.charAt(1)).equals("0") && String.valueOf(answer.charAt(1)).equals("1")) {
                            holder.iv_3.setImageResource(R.mipmap.right);
                            holder.tv_3.setTextColor(mContext.getResources().getColor(R.color.right_choice_color));
                        }
                        if (String.valueOf(myAnswer.charAt(2)).equals("1") && String.valueOf(answer.charAt(2)).equals("0")) {//错误
                            holder.iv_2.setImageResource(R.mipmap.wrong);
                            holder.tv_2.setTextColor(mContext.getResources().getColor(R.color.wrong_choice_color));
                        } else if (String.valueOf(myAnswer.charAt(2)).equals("1") && String.valueOf(answer.charAt(2)).equals("1")) {
                            holder.iv_2.setImageResource(R.mipmap.right);
                            holder.tv_2.setTextColor(mContext.getResources().getColor(R.color.right_choice_color));
                        } else if (String.valueOf(myAnswer.charAt(2)).equals("0") && String.valueOf(answer.charAt(2)).equals("1")) {
                            holder.iv_2.setImageResource(R.mipmap.right);
                            holder.tv_2.setTextColor(mContext.getResources().getColor(R.color.right_choice_color));
                        }
                        if (String.valueOf(myAnswer.charAt(3)).equals("1") && String.valueOf(answer.charAt(3)).equals("0")) {//错误
                            holder.iv_1.setImageResource(R.mipmap.wrong);
                            holder.tv_1.setTextColor(mContext.getResources().getColor(R.color.wrong_choice_color));
                        } else if (String.valueOf(myAnswer.charAt(3)).equals("1") && String.valueOf(answer.charAt(3)).equals("1")) {
                            holder.iv_1.setImageResource(R.mipmap.right);
                            holder.tv_1.setTextColor(mContext.getResources().getColor(R.color.right_choice_color));
                        } else if (String.valueOf(myAnswer.charAt(3)).equals("0") && String.valueOf(answer.charAt(3)).equals("1")) {
                            holder.iv_1.setImageResource(R.mipmap.right);
                            holder.tv_1.setTextColor(mContext.getResources().getColor(R.color.right_choice_color));
                        }


                        problem.setIsdo(1);
                        BigInteger src = new BigInteger(myAnswer, 2);//转化为10进制
                        int i = Integer.valueOf(src.toString());
                        problem.setChoose(i);
                        if (myAnswer.equals(answer)) {//做对了
                            rightCount++;
//                            dao.addRightQuestions(problem, subject);
                            OkGo.post(WebInterface.add_right)
                                    .params("subject", subject0)
                                    .params("signid", onlyID)
                                    .params("questionid", problem.getSid())
                                    .execute(new StringCallback() {
                                        @Override
                                        public void onSuccess(String s, Call call, Response response) {
                                            AddRight addRight = gson.fromJson(s, AddRight.class);
                                        }
                                    });
                            handler.postDelayed(runnable, 300);
                        } else {
                            error_count++;
//                            holder.answer.setVisibility(View.VISIBLE);
//                            holder.ll_explain.setVisibility(View.VISIBLE);
//                            dao.addErrorQuestions(problem, subject);//添加错题到错题库
                            //                           将错题发送到账号内
                            OkGo.post(WebInterface.add_error)
                                    .tag(this)
                                    .params("subject", subject0)
                                    .params("signid", onlyID)
                                    .params("questionid", problem.getSid())
//                                    .params("option", myAnswer)
                                    .execute(new StringCallback() {
                                        @Override
                                        public void onSuccess(String s, Call call, Response response) {
                                            AllErrorQuestions questions = gson.fromJson(s, AllErrorQuestions.class);
                                        }
                                    });
                            if (mRecyclerView.getScrollState() == 0) {
                                if (error_count == 11) {
                                    final MyDialog dialog = new MyDialog(mContext);
                                    dialog.setMessage("错题超过10分，自动提交试卷");
                                    dialog.show();
                                    dialog.setNoOnclickListener("否", new MyDialog.onNoOnclickListener() {
                                        @Override
                                        public void onNoClick() {
                                            handler.postDelayed(runnable, 500);
                                            dialog.dismiss();
                                        }
                                    });
                                    dialog.setYesOnclickListener("是", new MyDialog.onYesOnclickListener() {
                                        @Override
                                        public void onYesClick() {
//                    提交试卷
                                            problem.setIsdo(0);
                                            problem.setChoose(0);
                                            if (su.showTiming() == 1) {

                                                int score = 0;
                                                if (subject.equals("one")) {
                                                    score = rightCount;
                                                } else {
                                                    score = 2 * rightCount;
                                                }
//                上传成绩到服务器
                                                for (int i = 0; i < datas.size(); i++) {
                                                    map = new HashMap();
                                                    map.put("sid", datas.get(i).getSid());
                                                    map.put("isdo", String.valueOf(datas.get(i).getIsdo()));
                                                    map.put("choose", String.valueOf(datas.get(i).getChoose()));
                                                    list1.add(map);
                                                }
                                                JSONArray jsonObject = new JSONArray(list1);
                                                System.out.println("json=============" + jsonObject.toString());
                                                OkGo.post(WebInterface.theoretical_order_submit)
                                                        .tag(this)
                                                        .params("signid", onlyID)
                                                        .params("type", "2")
                                                        .params("orderid", praticeActivity.orderId)
                                                        .params("use_time", String.valueOf(praticeActivity.time))
                                                        .params("score", String.valueOf(score))
                                                        .params("all_question_info", jsonObject.toString())
                                                        .params("totalnumber", datas.size())
                                                        .params("correctnumber", rightCount)
                                                        .params("wrongnumber", error_count)
                                                        .params("pass_status", "90")
                                                        .execute(new StringCallback() {
                                                            @Override
                                                            public void onSuccess(String s, Call call, Response response) {
                                                                int num = JsonUtils.parseNum(s);
                                                                if (num == 1) {
                                                                    Toast.makeText(mContext, "成绩上传成功", Toast.LENGTH_SHORT).show();
                                                                }
                                                            }
                                                        });
                                            }
//                                            //                上传成绩到服务器
//                                            for (int i = 0; i < datas.size(); i++) {
//                                                map = new HashMap();
//                                                map.put("sid", datas.get(i).getSid());
//                                                map.put("isdo", String.valueOf(datas.get(i).getIsdo()));
//                                                map.put("choose", String.valueOf(datas.get(i).getChoose()));
//                                                list1.add(map);
//                                            }
////                                            net.sf.json.JSONArray jsonObject = net.sf.json.JSONArray.fromObject(list1);
//                                            OkGo.post(WebInterface.theoretical_order_submit)
//                                                    .tag(this)
//                                                    .params("signid", onlyID)
//                                                    .params("type", "2")
//                                                    .params("orderid", praticeActivity.orderId)
//                                                    .params("use_time", String.valueOf(praticeActivity.time))
//                                                    .params("score", String.valueOf(score))
//                                                    .params("all_question_info", jsonObject.toString())
//                                                    .params("totalnumber", datas.size())
//                                                    .params("correctnumber", rightCount)
//                                                    .params("wrongnumber", error_count)
//                                                    .params("pass_status", "90")
//                                                    .execute(new StringCallback() {
//                                                        @Override
//                                                        public void onSuccess(String s, Call call, Response response) {
//                                                            int num = JsonUtils.parseNum(s);
//                                                            if (num == 1) {
//                                                                Toast.makeText(mContext, "成绩上传成功", Toast.LENGTH_SHORT).show();
//                                                            }
//                                                        }
//                                                    });
//                                            OkGo.post(WebInterface.personal_score)
//                                                    .tag(this)
//                                                    .params("score", String.valueOf(rightCount))
//                                                    .params("subject", subject0)
//                                                    .params("cartype", cartype)
//                                                    .params("time", String.valueOf(praticeActivity.time))
//                                                    .params("signid", onlyID)
////                                                    .params("questioninfo", jsonObject.toString())
//                                                    .execute(new StringCallback() {
//                                                        @Override
//                                                        public void onSuccess(String s, Call call, Response response) {
//                                                            System.out.println("返回信息是====" + s);
//                                                        }
//                                                    });

                                            Intent intent = new Intent(mContext, PracticeResultActivity.class);
                                            mContext.startActivity(intent);
                                            ((Activity) mContext).finish();
                                            dialog.dismiss();
                                        }
                                    });
                                } else {
                                    handler.postDelayed(runnable, 500);
                                }

                            }
//                            questionInfo = new String[]{problem.getSid(), String.valueOf(problem.getIsdo()), String.valueOf(problem.getChoose())};
//                            System.out.println("questionInfo========" + Arrays.toString(questionInfo));
//                            list.add(questionInfo);
//                            System.out.println("listsize===========" + list.size());
                        }

                    }
                });

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
//                holder.answer.setVisibility(View.VISIBLE);
//                holder.ll_explain.setVisibility(View.VISIBLE);
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

    }

    @Override
    public void onViewDetachedFromWindow(SimpleViewHolder holder) {
        super.onViewDetachedFromWindow(holder);
        holder.mProgressBar.setVisibility(View.VISIBLE);
        holder.mVideoview.stopPlayback();
        holder.mVideoview.suspend();

    }

    @Override
    public void onViewAttachedToWindow(SimpleViewHolder holder) {
        super.onViewAttachedToWindow(holder);
    }

    @Override
    public int getItemCount() {
        return datas != null ? datas.size() : 0;
    }
}
