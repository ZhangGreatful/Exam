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
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.VideoView;

import com.google.gson.Gson;
import com.haha.exam.R;
import com.haha.exam.activity.CollectQuestionActivity;
import com.haha.exam.activity.ErrorQuestionActivity;
import com.haha.exam.activity.OrderTextActivity;
import com.haha.exam.bean.AddRight;
import com.haha.exam.bean.AllErrorQuestions;
import com.haha.exam.bean.AllQuestions;
import com.haha.exam.utils.SPUtils;
import com.haha.exam.web.WebInterface;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.wanzheng.driver.RelatedActivity.PictureDetail;
import com.wanzheng.driver.util.SystemUtil;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import okhttp3.Call;
import okhttp3.Response;


public class LayoutAdapter extends RecyclerView.Adapter<LayoutAdapter.SimpleViewHolder> {

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

        }
    };

    protected ImageLoader imageLoader;
    private SystemUtil su;
    private String onlyID;
    private String subject, subject0;
    private SPUtils spUtils = new SPUtils();
    private final Context mContext;
    private final RecyclerView mRecyclerView;
    private List<AllQuestions.DataBean> datas;
    private OrderTextActivity orderTextActivity;
    private ErrorQuestionActivity errorQuestionActivity;
    private CollectQuestionActivity collectQuestionActivity;
    private Gson gson = new Gson();
    private String[] questionInfo;
    public static int rightCount;
    public static int errorCount;
    private List<String[]> list = new ArrayList<>();
    private int autoClear;
    public static final int SHOWN_STATE = 1;
    public static final int INVISIBLE_STATE = 0;

    private List<Bitmap> mBitmaps;


    //建立管理子线程线程池
    ExecutorService cachedThreadPool;

    public static class SimpleViewHolder extends RecyclerView.ViewHolder {
        public final TextView title, tv_1, tv_2, tv_3, tv_4;//title,
        public final TextView answer, is_wrong, answer_explain;
        public final ImageView iv_1, iv_2, iv_3, iv_4, iv_pic, choice_icon;
        public Drawable drawable;
        public final LinearLayout ll_explain, choice_1, choice_2, choice_3, choice_4;
        public final LinearLayout explain_pic1, explain_pic2, explain_pic3;
        public final ImageView pic_1, pic_2, pic_3;
        public final TextView pic1_text, pic2_text, pic3_text;
        public final Button btn_sure;
        public final VideoView videoView;
        public final FrameLayout video;
        public boolean isopen;
        public Runnable runnable1 = null;
        public ProgressBar progressBar;


        public SimpleViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.title);
            videoView = (VideoView) view.findViewById(R.id.videoView);
            btn_sure = (Button) view.findViewById(R.id.btn_sure);
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
            choice_icon = (ImageView) view.findViewById(R.id.choice_icon);

            ll_explain = (LinearLayout) view.findViewById(R.id.ll_anwer);
            choice_1 = (LinearLayout) view.findViewById(R.id.choice_1);
            choice_2 = (LinearLayout) view.findViewById(R.id.choice_2);
            choice_3 = (LinearLayout) view.findViewById(R.id.choice_3);
            choice_4 = (LinearLayout) view.findViewById(R.id.choice_4);
            video = (FrameLayout) view.findViewById(R.id.ll_video);

            explain_pic1 = (LinearLayout) view.findViewById(R.id.explain_pic1);
            explain_pic2 = (LinearLayout) view.findViewById(R.id.explain_pic2);
            explain_pic3 = (LinearLayout) view.findViewById(R.id.explain_pic3);

            pic_1 = (ImageView) view.findViewById(R.id.pic_1);
            pic_2 = (ImageView) view.findViewById(R.id.pic_2);
            pic_3 = (ImageView) view.findViewById(R.id.pic_3);

            pic1_text = (TextView) view.findViewById(R.id.pic1_text);
            pic2_text = (TextView) view.findViewById(R.id.pic2_text);
            pic3_text = (TextView) view.findViewById(R.id.pic3_text);
            progressBar = (ProgressBar) view.findViewById(R.id.item_progressBar);

        }

    }


    public LayoutAdapter(Context context, RecyclerView recyclerView) {

        mBitmaps = new ArrayList<>();

        mContext = context;
        orderTextActivity = new OrderTextActivity();
        errorQuestionActivity = new ErrorQuestionActivity();
        collectQuestionActivity = new CollectQuestionActivity();
        su = new SystemUtil(mContext);
        onlyID = su.showOnlyID();
        subject = (String) spUtils.get(mContext, "subject", "one");
        subject0 = (String) spUtils.get(mContext, "subject0", "1");
        System.out.println("uid===========" + onlyID);

        rightCount = 0;
        errorCount = 0;
        cachedThreadPool = Executors.newCachedThreadPool();
        mRecyclerView = recyclerView;


        imageLoader = ImageLoader.getInstance();
    }

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

    public void setDataList(List<AllQuestions.DataBean> datas) {
        this.datas = datas;
        notifyDataSetChanged();
    }

    public void setAutoClear(int autoClear) {
        this.autoClear = autoClear;
        notifyDataSetChanged();
        System.out.println("autoClear=========" + autoClear);
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
                if (position + 1 < datas.size()) {
                    mRecyclerView.smoothScrollToPosition(position + 1);
                }
            }

        };
        holder.answer.setVisibility(View.GONE);
        holder.ll_explain.setVisibility(View.GONE);

        System.out.println("发送过来的消息是====" + orderTextActivity.isClicked);


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
            imageLoader.displayImage(imageUrl, holder.iv_pic, new ImageLoadingListener() {

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
//            Picasso.with(mContext).load(imageUrl).memoryPolicy(MemoryPolicy.NO_CACHE, MemoryPolicy.NO_STORE).into(holder.iv_pic);
        } else if (!videoUrl.equals("")) {
            if (!videoUrl.contains("swf")) {
                holder.iv_pic.setVisibility(View.GONE);
                holder.video.setVisibility(View.VISIBLE);
                holder.videoView.setVideoURI(Uri.parse(videoUrl));
//            MediaController controller = new MediaController(mContext);
//            holder.videoView.setMediaController(controller);
                holder.videoView.start();
                holder.videoView.requestFocus();
                holder.videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                    @Override
                    public void onPrepared(MediaPlayer mp) {
                        if (holder.progressBar != null)
                            holder.progressBar.setVisibility(View.GONE);
                        mp.start();
                        mp.setLooping(true);
                    }
                });

                holder.videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mp) {

                    }
                });

                mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
                    @Override
                    public void onScrollStateChanged(RecyclerView recyclerView, final int newState) {
                        super.onScrollStateChanged(recyclerView, newState);
                        Log.e("layoutadapter", "执行scrolllistener");
                        imageLoader.clearDiskCache();
                        imageLoader.clearMemoryCache();

                    }

                    @Override
                    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                        super.onScrolled(recyclerView, dx, dy);
                        Log.e("layoutadapter", dx + "是x" + dy + "是y");
                    }
                });
            }
        } else {
            holder.iv_pic.setVisibility(View.GONE);
            holder.video.setVisibility(View.GONE);
        }
        String str = problem.getDetail();
        if (str.contains(".jpg")) {
            holder.explain_pic1.setVisibility(View.VISIBLE);
            holder.explain_pic2.setVisibility(View.VISIBLE);
            int index = str.indexOf("</p>");
            String url1 = str.substring(0, index + 4);
            String url2 = str.substring(index + 4, str.length());

            int Findex = url1.indexOf("=");
            int Findex_end = url1.indexOf("jpg");

            String Furl1 = url1.substring(Findex + 1, Findex_end + 3);
            String text1 = url1.substring(Findex_end + 4, url1.length() - 4);
//            Picasso.with(mContext).load(Furl1).into(holder.pic_1);//加载解释中的第一张图片
            imageLoader.displayImage(Furl1, holder.pic_1);
            holder.pic1_text.setText(text1);

            System.out.println("Furl1===============" + Furl1);
            System.out.println("text1===========" + text1);
            int index1 = url2.indexOf("</p>");
            System.out.println("index1======" + (index1 + 4));
            System.out.println("length=======" + url2.length());
            if (index1 + 4 == url2.length()) {
//          解释中只有两张图片
                int Sindex = url2.indexOf("=");
                int Sindex_end = url2.indexOf("jpg");
                String Furl2 = url2.substring(Findex + 1, Findex_end + 3);
                String text2 = url2.substring(Sindex_end + 4, url2.length() - 4);
//                Picasso.with(mContext).load(Furl2).into(holder.pic_2);
                imageLoader.displayImage(Furl2, holder.pic_2);
                holder.pic2_text.setText(text2);
                System.out.println(Furl2);
                System.out.println(text2);
            } else {
//                解释中有三张图片
                holder.explain_pic3.setVisibility(View.VISIBLE);
                String url3 = url2.substring(0, index1 + 4);
                String url4 = url2.substring(index1 + 4, url2.length());
                System.out.println("url3==========" + url3);
                System.out.println("url4==========" + url4);
                int Tindex = url3.indexOf("=");
                int Tindex_end = url3.indexOf("jpg");
                String Turl2 = url3.substring(Tindex + 1, Tindex_end + 3);
                String text3 = url3.substring(Tindex_end + 4, url3.length() - 4);
//                加载第二张图片
//                Picasso.with(mContext).load(Turl2).into(holder.pic_2);
                imageLoader.displayImage(Turl2, holder.pic_2);
                holder.pic2_text.setText(text3);
                System.out.println("Turl3=======" + Turl2);
                System.out.println("text3=======" + text3);
                int Gindex = url4.indexOf("=");
                int Gindex_end = url4.indexOf("jpg");
                String Turl4 = url4.substring(Gindex + 1, Gindex_end + 3);
                String text5 = url4.substring(Gindex_end + 4, url4.length() - 4);
//                加载第三张图片
//                Picasso.with(mContext).load(Turl4).into(holder.pic_3);
                imageLoader.getInstance().displayImage(Turl4, holder.pic_3);
                holder.pic3_text.setText(text5);
                System.out.println("Turl4=======" + Turl4);
                System.out.println("text4=======" + text5);

            }
        } else {
            holder.answer_explain.setText(problem.getDetail());
            holder.explain_pic1.setVisibility(View.GONE);
            holder.explain_pic2.setVisibility(View.GONE);
            holder.explain_pic3.setVisibility(View.GONE);
        }


        System.out.println("explain=======" + problem.getDetail());
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
            if ((orderTextActivity.isClicked == 1 && orderTextActivity.curPosition == position) ||
                    (errorQuestionActivity.isClicked == 1 && errorQuestionActivity.curPosition == position)
                    || (collectQuestionActivity.isClicked == 1 && collectQuestionActivity.curPosition == position)) {
                holder.answer.setVisibility(View.VISIBLE);
                holder.ll_explain.setVisibility(View.VISIBLE);
            } else {
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
//                        dao.updateIsdo(problem.getSid(),"xc",problem.getSubject(),1,1);


                        if (problem.getAnswer().equals("1")) {
                            holder.iv_1.setImageResource(R.mipmap.right);
                            holder.tv_1.setTextColor(mContext.getResources().getColor(R.color.right_choice_color));
                            rightCount++;
//                            dao.addRightQuestions(problem, subject);
                            if (autoClear == 0) {
                                OkGo.post(WebInterface.add_right)
                                        .params("signid", onlyID)
                                        .params("questionid", problem.getSid())
                                        .params("subject", subject0)
                                        .execute(new StringCallback() {
                                            @Override
                                            public void onSuccess(String s, Call call, Response response) {
                                                AddRight addRight = gson.fromJson(s, AddRight.class);
                                            }
                                        });
                            }
                            handler.postDelayed(runnable, 300);

                        } else {
                            errorCount++;
                            holder.answer.setVisibility(View.VISIBLE);
                            holder.ll_explain.setVisibility(View.VISIBLE);
                            holder.iv_1.setImageResource(R.mipmap.wrong);
                            holder.tv_1.setTextColor(mContext.getResources().getColor(R.color.wrong_choice_color));
                            questionInfo = new String[]{problem.getSid(), String.valueOf(problem.getIsdo()), String.valueOf(problem.getChoose())};
                            System.out.println("questionInfo========" + Arrays.toString(questionInfo));
                            list.add(questionInfo);
                            System.out.println("listsize===========" + list.size());
                            //  将错题发送到账号内
                            OkGo.post(WebInterface.add_error)
                                    .tag(this)
                                    .params("subject", subject0)
                                    .params("signid", onlyID).params("questionid", problem.getSid())
                                    .params("option", "1")
                                    .execute(new StringCallback() {
                                        @Override
                                        public void onSuccess(String s, Call call, Response response) {
                                            AllErrorQuestions questions = gson.fromJson(s, AllErrorQuestions.class);
                                        }
                                    });
//                            dao.addErrorQuestions(problem, "one");//添加错题到错题库
                        }
                    }
                });
                holder.choice_2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        problem.setIsdo(1);
                        problem.setChoose(2);
                        //                        更新数据库做题状态
//                        dao.updateIsdo(problem.getSid(),"xc",problem.getSubject(),1,2);

                        holder.choice_1.setEnabled(false);
                        if (problem.getAnswer().equals("0")) {
                            rightCount++;
//                            dao.addRightQuestions(problem, sbj);
                            holder.answer.setText("对");
                            holder.iv_2.setImageResource(R.mipmap.right);
                            holder.tv_2.setTextColor(mContext.getResources().getColor(R.color.right_choice_color));
                            if (autoClear == 0) {
                                OkGo.post(WebInterface.add_right)
                                        .params("signid", onlyID)
                                        .params("questionid", problem.getSid())
                                        .params("subject", subject0)
                                        .execute(new StringCallback() {
                                            @Override
                                            public void onSuccess(String s, Call call, Response response) {
                                                AddRight addRight = gson.fromJson(s, AddRight.class);
                                            }
                                        });
                            }
                            handler.postDelayed(runnable, 300);

                        } else {
                            errorCount++;
                            holder.answer.setVisibility(View.VISIBLE);
                            holder.ll_explain.setVisibility(View.VISIBLE);
                            holder.iv_2.setImageResource(R.mipmap.wrong);
                            holder.tv_2.setTextColor(mContext.getResources().getColor(R.color.wrong_choice_color));
                            //                           将错题发送到账号内
                            OkGo.post(WebInterface.add_error)
                                    .tag(this)
                                    .params("subject", subject0)
                                    .params("signid", onlyID).params("questionid", problem.getSid())
                                    .params("option", "1")
                                    .execute(new StringCallback() {
                                        @Override
                                        public void onSuccess(String s, Call call, Response response) {
                                            AllErrorQuestions questions = gson.fromJson(s, AllErrorQuestions.class);
                                        }
                                    });
                            questionInfo = new String[]{problem.getSid(), String.valueOf(problem.getIsdo()), String.valueOf(problem.getChoose())};
                            System.out.println("questionInfo========" + Arrays.toString(questionInfo));
                            list.add(questionInfo);
                            System.out.println("listsize===========" + list.size());
//                            dao.addErrorQuestions(problem, "one");//添加错题到错题库
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
//                        dao.updateIsdo(problem.getSid(),"xc",problem.getSubject(),1,1);

                        if (problem.getAnswer().equals("1")) {
                            rightCount++;
//                            dao.addRightQuestions(problem, sbj);
                            holder.iv_1.setImageResource(R.mipmap.right);
                            holder.tv_1.setTextColor(mContext.getResources().getColor(R.color.right_choice_color));
                            if (autoClear == 0) {
                                OkGo.post(WebInterface.add_right)
                                        .params("signid", onlyID)
                                        .params("questionid", problem.getSid())
                                        .params("subject", subject0)
                                        .execute(new StringCallback() {
                                            @Override
                                            public void onSuccess(String s, Call call, Response response) {
                                                AddRight addRight = gson.fromJson(s, AddRight.class);
                                            }
                                        });
                            }
                            handler.postDelayed(runnable, 300);
                        } else {
                            errorCount++;
                            holder.answer.setVisibility(View.VISIBLE);
                            holder.ll_explain.setVisibility(View.VISIBLE);
                            holder.iv_1.setImageResource(R.mipmap.wrong);
                            holder.tv_1.setTextColor(mContext.getResources().getColor(R.color.wrong_choice_color));
//                            dao.addErrorQuestions(problem, "one");//添加错题到错题库
                            //                           将错题发送到账号内
                            OkGo.post(WebInterface.add_error)
                                    .tag(this)
                                    .params("subject", subject0)
                                    .params("signid", onlyID).params("questionid", problem.getSid())
                                    .params("option", "1")
                                    .execute(new StringCallback() {
                                        @Override
                                        public void onSuccess(String s, Call call, Response response) {
                                            AllErrorQuestions questions = gson.fromJson(s, AllErrorQuestions.class);
                                        }
                                    });
                            questionInfo = new String[]{problem.getSid(), String.valueOf(problem.getIsdo()), String.valueOf(problem.getChoose())};
                            System.out.println("questionInfo========" + Arrays.toString(questionInfo));
                            list.add(questionInfo);
                            System.out.println("listsize===========" + list.size());
                        }
                    }
                });
                holder.choice_2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        problem.setIsdo(1);
                        problem.setChoose(2);

                        //                        更新数据库做题状态
//                        dao.updateIsdo(problem.getSid(),"xc",problem.getSubject(),1,2);

                        holder.choice_1.setEnabled(false);
                        holder.choice_3.setEnabled(false);
                        holder.choice_4.setEnabled(false);
                        if (problem.getAnswer().equals("2")) {
                            rightCount++;
//                            dao.addRightQuestions(problem, sbj);
                            holder.iv_2.setImageResource(R.mipmap.right);
                            holder.tv_2.setTextColor(mContext.getResources().getColor(R.color.right_choice_color));
                            if (autoClear == 0) {
                                OkGo.post(WebInterface.add_right)
                                        .params("signid", onlyID)
                                        .params("questionid", problem.getSid())
                                        .params("subject", subject0)
                                        .execute(new StringCallback() {
                                            @Override
                                            public void onSuccess(String s, Call call, Response response) {
                                                AddRight addRight = gson.fromJson(s, AddRight.class);
                                            }
                                        });
                            }
                            handler.postDelayed(runnable, 300);
                        } else {
                            errorCount++;
                            holder.answer.setVisibility(View.VISIBLE);
                            holder.ll_explain.setVisibility(View.VISIBLE);
                            holder.iv_2.setImageResource(R.mipmap.wrong);
                            holder.tv_2.setTextColor(mContext.getResources().getColor(R.color.wrong_choice_color));
//                            dao.addErrorQuestions(problem, "one");//添加错题到错题库
                            //                           将错题发送到账号内
                            OkGo.post(WebInterface.add_error)
                                    .tag(this)
                                    .params("subject", subject0)
                                    .params("signid", onlyID).params("questionid", problem.getSid())
                                    .params("option", "1")
                                    .execute(new StringCallback() {
                                        @Override
                                        public void onSuccess(String s, Call call, Response response) {
                                            AllErrorQuestions questions = gson.fromJson(s, AllErrorQuestions.class);
                                        }
                                    });
                            questionInfo = new String[]{problem.getSid(), String.valueOf(problem.getIsdo()), String.valueOf(problem.getChoose())};
                            System.out.println("questionInfo========" + Arrays.toString(questionInfo));
                            list.add(questionInfo);
                            System.out.println("listsize===========" + list.size());
                        }
                    }
                });
                holder.choice_3.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        problem.setIsdo(1);
                        problem.setChoose(4);


                        //                        更新数据库做题状态
//                        dao.updateIsdo(problem.getSid(),"xc",problem.getSubject(),1,4);

                        holder.choice_2.setEnabled(false);
                        holder.choice_1.setEnabled(false);
                        holder.choice_4.setEnabled(false);
                        if (problem.getAnswer().equals("4")) {
                            rightCount++;
//                            dao.addRightQuestions(problem, sbj);
                            holder.iv_3.setImageResource(R.mipmap.right);
                            holder.tv_3.setTextColor(mContext.getResources().getColor(R.color.right_choice_color));
                            if (autoClear == 0) {
                                OkGo.post(WebInterface.add_right)
                                        .params("signid", onlyID)
                                        .params("questionid", problem.getSid())
                                        .params("subject", subject0)
                                        .execute(new StringCallback() {
                                            @Override
                                            public void onSuccess(String s, Call call, Response response) {
                                                AddRight addRight = gson.fromJson(s, AddRight.class);
                                            }
                                        });
                            }
                            handler.postDelayed(runnable, 300);
                        } else {
                            errorCount++;
                            holder.answer.setVisibility(View.VISIBLE);
                            holder.ll_explain.setVisibility(View.VISIBLE);
                            holder.iv_3.setImageResource(R.mipmap.wrong);
                            holder.tv_3.setTextColor(mContext.getResources().getColor(R.color.wrong_choice_color));
//                            dao.addErrorQuestions(problem, "one");//添加错题到错题库
                            //                           将错题发送到账号内
                            OkGo.post(WebInterface.add_error)
                                    .tag(this)
                                    .params("signid", onlyID)
                                    .params("questionid", problem.getSid())
                                    .params("option", "1")
                                    .params("subject", subject0)
                                    .execute(new StringCallback() {
                                        @Override
                                        public void onSuccess(String s, Call call, Response response) {
                                            AllErrorQuestions questions = gson.fromJson(s, AllErrorQuestions.class);
                                        }
                                    });
                            questionInfo = new String[]{problem.getSid(), String.valueOf(problem.getIsdo()), String.valueOf(problem.getChoose())};
                            System.out.println("questionInfo========" + Arrays.toString(questionInfo));
                            list.add(questionInfo);
                            System.out.println("listsize===========" + list.size());
                        }
                    }
                });
                holder.choice_4.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        problem.setIsdo(1);
                        problem.setChoose(8);

                        //                        更新数据库做题状态
//                        dao.updateIsdo(problem.getSid(),"xc",problem.getSubject(),1,8);

                        holder.choice_2.setEnabled(false);
                        holder.choice_3.setEnabled(false);
                        holder.choice_1.setEnabled(false);
                        if (problem.getAnswer().equals("8")) {
                            rightCount++;
//                            dao.addRightQuestions(problem, sbj);
                            holder.iv_4.setImageResource(R.mipmap.right);
                            holder.tv_4.setTextColor(mContext.getResources().getColor(R.color.right_choice_color));
                            if (autoClear == 0) {
                                OkGo.post(WebInterface.add_right)
                                        .params("signid", onlyID)
                                        .params("questionid", problem.getSid())
                                        .params("subject", subject0)
                                        .execute(new StringCallback() {
                                            @Override
                                            public void onSuccess(String s, Call call, Response response) {
                                                AddRight addRight = gson.fromJson(s, AddRight.class);
                                            }
                                        });
                            }
                            handler.postDelayed(runnable, 300);
                        } else {
                            errorCount++;
                            holder.answer.setVisibility(View.VISIBLE);
                            holder.ll_explain.setVisibility(View.VISIBLE);
                            holder.iv_4.setImageResource(R.mipmap.wrong);
                            holder.tv_4.setTextColor(mContext.getResources().getColor(R.color.wrong_choice_color));
//                            dao.addErrorQuestions(problem, "one");//添加错题到错题库
                            //                           将错题发送到账号内
                            OkGo.post(WebInterface.add_error)
                                    .tag(this)
                                    .params("subject", subject0)
                                    .params("signid", onlyID)
                                    .params("questionid", problem.getSid())
                                    .params("option", "1")
                                    .execute(new StringCallback() {
                                        @Override
                                        public void onSuccess(String s, Call call, Response response) {
                                            AllErrorQuestions questions = gson.fromJson(s, AllErrorQuestions.class);
                                        }
                                    });
                            questionInfo = new String[]{problem.getSid(), String.valueOf(problem.getIsdo()), String.valueOf(problem.getChoose())};
                            System.out.println("questionInfo========" + Arrays.toString(questionInfo));
                            list.add(questionInfo);
                            System.out.println("listsize===========" + list.size());
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
                        if (holder.choice_4.isSelected()) {
                            myAnswer = myAnswer.concat("1");
                        } else {
                            myAnswer = myAnswer.concat("0");
                        }
                        if (holder.choice_3.isSelected()) {
                            myAnswer = myAnswer.concat("1");
                        } else {
                            myAnswer = myAnswer.concat("0");
                        }
                        if (holder.choice_2.isSelected()) {
                            myAnswer = myAnswer.concat("1");
                        } else {
                            myAnswer = myAnswer.concat("0");
                        }
                        if (holder.choice_1.isSelected()) {
                            myAnswer = myAnswer.concat("1");
                        } else {
                            myAnswer = myAnswer.concat("0");
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
//                            dao.addRightQuestions(problem, sbj);
                            if (autoClear == 0) {
                                OkGo.post(WebInterface.add_right)
                                        .params("signid", onlyID)
                                        .params("questionid", problem.getSid())
                                        .params("subject", subject0)
                                        .execute(new StringCallback() {
                                            @Override
                                            public void onSuccess(String s, Call call, Response response) {
                                                AddRight addRight = gson.fromJson(s, AddRight.class);
                                            }
                                        });
                            }
                            handler.postDelayed(runnable, 300);
                        } else {
                            errorCount++;
                            holder.answer.setVisibility(View.VISIBLE);
                            holder.ll_explain.setVisibility(View.VISIBLE);
//                            dao.addErrorQuestions(problem, "one");//添加错题到错题库
                            //                           将错题发送到账号内
                            OkGo.post(WebInterface.add_error)
                                    .tag(this)
                                    .params("subject", subject0)
                                    .params("signid", onlyID)
                                    .params("questionid", problem.getSid())
                                    .params("option", myAnswer)
                                    .execute(new StringCallback() {
                                        @Override
                                        public void onSuccess(String s, Call call, Response response) {
                                            AllErrorQuestions questions = gson.fromJson(s, AllErrorQuestions.class);
                                        }
                                    });
                            questionInfo = new String[]{problem.getSid(), String.valueOf(problem.getIsdo()), String.valueOf(problem.getChoose())};
                            System.out.println("questionInfo========" + Arrays.toString(questionInfo));
                            list.add(questionInfo);
                            System.out.println("listsize===========" + list.size());
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

            if (problem.getType().equals("1")) {//多选题
                String myAnswer = Integer.toBinaryString(Integer.parseInt(String.valueOf(problem.getChoose())));
                String answer = Integer.toBinaryString(Integer.parseInt(problem.getAnswer()));
                if (answer.length() == 1) {
                    answer = "000".concat(answer);
                } else if (answer.length() == 2) {
                    answer = "00".concat(answer);
                } else if (answer.length() == 3) {
                    answer = "0".concat(answer);
                }
                if (myAnswer.length() == 1) {
                    myAnswer = "000".concat(myAnswer);
                } else if (myAnswer.length() == 2) {
                    myAnswer = "00".concat(myAnswer);
                } else if (myAnswer.length() == 3) {
                    myAnswer = "0".concat(myAnswer);
                }
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

            } else {
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
    }

    @Override
    public void onViewDetachedFromWindow(SimpleViewHolder holder) {
        super.onViewDetachedFromWindow(holder);
        holder.progressBar.setVisibility(View.VISIBLE);
        holder.videoView.stopPlayback();
        holder.videoView.suspend();
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
