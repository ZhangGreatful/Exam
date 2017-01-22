package com.haha.exam.adapter;

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
import android.widget.VideoView;

import com.haha.exam.R;
import com.haha.exam.activity.OrderTextActivity;
import com.haha.exam.bean.AllQuestions;
import com.haha.exam.dao.ExamDao;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.wanzheng.driver.RelatedActivity.PictureDetail;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/11/10.
 */

public class ReviewErrorAdapter extends RecyclerView.Adapter<ReviewErrorAdapter.SimpleViewHolder> {
    Handler handler = new Handler();
    protected ImageLoader imageLoader;
    private final Context mContext;
    private final RecyclerView mRecyclerView;
    private List<AllQuestions.DataBean> datas;
    private OrderTextActivity orderTextActivity;
    public static int rightCount;
    public static int errorCount;
    private List<String[]> list = new ArrayList<>();
    private String sbj = "one";
    private List<Bitmap> mBitmaps;


    public class SimpleViewHolder extends RecyclerView.ViewHolder {
        public TextView title, tv_1, tv_2, tv_3, tv_4;//title,
        public TextView answer, is_wrong, answer_explain;
        public ImageView iv_1, iv_2, iv_3, iv_4, iv_pic, choice_icon;
        public Drawable drawable;
        public Button btn_sure;
        public LinearLayout ll_explain, choice_1, choice_2, choice_3, choice_4;
        public final LinearLayout explain_pic1, explain_pic2, explain_pic3;
        public final TextView pic1_text, pic2_text, pic3_text;
        public final ImageView pic_1, pic_2, pic_3;
        public VideoView videoView;
        public ProgressBar mProgressBar;
        public FrameLayout ll_video;


        public SimpleViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.title);

//            mixtureTextView= (MixtureTextView) view.findViewById(R.id.text);
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
            iv_pic = (ImageView) view.findViewById(R.id.iv_pic);
            choice_icon = (ImageView) view.findViewById(R.id.choice_icon);

            explain_pic1 = (LinearLayout) view.findViewById(R.id.explain_pic1);
            explain_pic2 = (LinearLayout) view.findViewById(R.id.explain_pic2);
            explain_pic3 = (LinearLayout) view.findViewById(R.id.explain_pic3);

            pic_1 = (ImageView) view.findViewById(R.id.pic_1);
            pic_2 = (ImageView) view.findViewById(R.id.pic_2);
            pic_3 = (ImageView) view.findViewById(R.id.pic_3);

            pic1_text = (TextView) view.findViewById(R.id.pic1_text);
            pic2_text = (TextView) view.findViewById(R.id.pic2_text);
            pic3_text = (TextView) view.findViewById(R.id.pic3_text);

            ll_explain = (LinearLayout) view.findViewById(R.id.ll_anwer);
            choice_1 = (LinearLayout) view.findViewById(R.id.choice_1);
            choice_2 = (LinearLayout) view.findViewById(R.id.choice_2);
            choice_3 = (LinearLayout) view.findViewById(R.id.choice_3);
            choice_4 = (LinearLayout) view.findViewById(R.id.choice_4);
            ll_video = (FrameLayout) view.findViewById(R.id.practice_Frvideo);
            videoView = (VideoView) view.findViewById(R.id.videoView_practice);
            mProgressBar = (ProgressBar) view.findViewById(R.id.practice_item_progressBar);


        }

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

    public ReviewErrorAdapter(Context context, RecyclerView recyclerView) {
        mBitmaps = new ArrayList<>();
        orderTextActivity = new OrderTextActivity();
        handler = new Handler();
        rightCount = 0;
        errorCount = 0;
        mContext = context;
        mRecyclerView = recyclerView;

    }

    public void setDataList(List<AllQuestions.DataBean> datas) {
        this.datas = datas;
        notifyDataSetChanged();
    }


    @Override
    public ReviewErrorAdapter.SimpleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_practice, parent, false);
        return new ReviewErrorAdapter.SimpleViewHolder(view);
//        return  new LayoutAdapter(mContext,mRecyclerView).SimpleViewHolder();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public void onBindViewHolder(final ReviewErrorAdapter.SimpleViewHolder holder, final int position) {

        final ExamDao dao = new ExamDao(mContext);
        holder.answer.setVisibility(View.GONE);
        holder.ll_explain.setVisibility(View.GONE);
        holder.btn_sure.setVisibility(View.GONE);

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
//            Picasso.with(mContext).load(imageUrl).memoryPolicy(MemoryPolicy.NO_CACHE,MemoryPolicy.NO_STORE).into(holder.iv_pic);
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
            //添加程序
            holder.iv_pic.setVisibility(View.GONE);
            //添加程序
            holder.ll_video.setVisibility(View.VISIBLE);
            holder.videoView.setVideoURI(Uri.parse(videoUrl));
            holder.videoView.start();
            holder.videoView.requestFocus();

            holder.videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
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
                public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                    super.onScrollStateChanged(recyclerView, newState);
//

                }

                @Override
                public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                    super.onScrolled(recyclerView, dx, dy);
                    holder.videoView.stopPlayback();
                }
            });

        } else {
            holder.iv_pic.setVisibility(View.GONE);
            holder.ll_video.setVisibility(View.GONE);
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
            imageLoader.getInstance().displayImage(Furl1, holder.pic_1);
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
                imageLoader.getInstance().displayImage(Furl2, holder.pic_2);
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
                imageLoader.getInstance().displayImage(Turl2, holder.pic_2);
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
        holder.iv_1.setImageResource(R.mipmap.a);
        holder.iv_2.setImageResource(R.mipmap.b);
        holder.iv_3.setImageResource(R.mipmap.c);
        holder.iv_4.setImageResource(R.mipmap.d);

        holder.title.setText("       " + problem.getQuestion());
        if (problem.getType().equals("3")) {//判断题
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
        } else {

            holder.tv_1.setText(problem.getOption().get(0).substring(2));
            holder.tv_2.setText(problem.getOption().get(1).substring(2));
            holder.tv_3.setText(problem.getOption().get(2).substring(2));
            holder.tv_4.setText(problem.getOption().get(3).substring(2));

            if (problem.getType().equals("2")) {//单选题

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
            } else if (problem.getType().equals("1")) {//多选题
                holder.choice_icon.setImageResource(R.mipmap.many_choice);
                holder.btn_sure.setVisibility(View.GONE);
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
//            if (orderTextActivity.isClicked == 1 && orderTextActivity.curPosition == position) {
            holder.answer.setVisibility(View.VISIBLE);
            holder.ll_explain.setVisibility(View.VISIBLE);
            holder.itemView.getTag();
            System.out.println("size===========" + datas.size());


        } else {
//            该题已经做过
            holder.choice_1.setEnabled(false);
            holder.choice_2.setEnabled(false);
            holder.choice_3.setEnabled(false);
            holder.choice_4.setEnabled(false);

            if (problem.getChoose() == Integer.valueOf(problem.getAnswer())) {
                rightCount++;
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
                holder.answer.setVisibility(View.VISIBLE);
                holder.ll_explain.setVisibility(View.VISIBLE);
            } else {
                errorCount++;
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
    public void onViewDetachedFromWindow(ReviewErrorAdapter.SimpleViewHolder holder) {
        super.onViewDetachedFromWindow(holder);
        holder.mProgressBar.setVisibility(View.VISIBLE);
        holder.videoView.stopPlayback();
        holder.videoView.suspend();//释放资源
    }

    @Override
    public void onViewAttachedToWindow(ReviewErrorAdapter.SimpleViewHolder holder) {
        super.onViewAttachedToWindow(holder);
    }

    @Override
    public int getItemCount() {
        return datas != null ? datas.size() : 0;
    }
}
