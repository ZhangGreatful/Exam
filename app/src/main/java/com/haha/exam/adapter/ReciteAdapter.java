package com.haha.exam.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Handler;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.VideoView;

import com.haha.exam.R;
import com.haha.exam.activity.OrderTextActivity;
import com.haha.exam.bean.AllQuestions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.wanzheng.driver.RelatedActivity.PictureDetail;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/10/26.
 */
public class ReciteAdapter extends RecyclerView.Adapter<ReciteAdapter.SimpleViewHolder> {

    Handler handler = new Handler();
    protected ImageLoader imageLoader;
    private List<Integer> isdo = new ArrayList<>();
    private List<Integer> choose = new ArrayList<>();
    private final Context mContext;
    private final RecyclerView mRecyclerView;
    private List<AllQuestions.DataBean> datas;
    private OrderTextActivity orderTextActivity;
    private String choice_answer;
    private List<Bitmap> mBitmaps;

    String choice;


    public class SimpleViewHolder extends RecyclerView.ViewHolder {
        public TextView title, tv_1, tv_2, tv_3, tv_4;
        public TextView answer, is_wrong, answer_explain;
        public ImageView iv_1, iv_2, iv_3, iv_4, choice_icon, iv_pic;
        public VideoView videoView;

        public LinearLayout explain_pic1, explain_pic2, explain_pic3;
        public ImageView pic_1, pic_2, pic_3;
        public TextView pic1_text, pic2_text, pic3_text;
        public ProgressBar mProgressBar;
        public LinearLayout ll_explain, choice_1, choice_2, choice_3, choice_4;
        public FrameLayout ll_video;


        public SimpleViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.recite_title);
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
            ll_video = (FrameLayout) view.findViewById(R.id.recite_Frvideo);
            videoView = (VideoView) view.findViewById(R.id.videoView_recite);
            choice_icon = (ImageView) view.findViewById(R.id.choice_icon);
            ll_explain = (LinearLayout) view.findViewById(R.id.ll_anwer);
            choice_1 = (LinearLayout) view.findViewById(R.id.choice_1);
            choice_2 = (LinearLayout) view.findViewById(R.id.choice_2);
            choice_3 = (LinearLayout) view.findViewById(R.id.choice_3);
            choice_4 = (LinearLayout) view.findViewById(R.id.choice_4);
            mProgressBar = (ProgressBar) view.findViewById(R.id.recite_item_progressBar);
            explain_pic1 = (LinearLayout) view.findViewById(R.id.explain_pic1);
            explain_pic2 = (LinearLayout) view.findViewById(R.id.explain_pic2);
            explain_pic3 = (LinearLayout) view.findViewById(R.id.explain_pic3);

            pic_1 = (ImageView) view.findViewById(R.id.pic_1);
            pic_2 = (ImageView) view.findViewById(R.id.pic_2);
            pic_3 = (ImageView) view.findViewById(R.id.pic_3);

            pic1_text = (TextView) view.findViewById(R.id.pic1_text);
            pic2_text = (TextView) view.findViewById(R.id.pic2_text);
            pic3_text = (TextView) view.findViewById(R.id.pic3_text);

        }

    }


    public ReciteAdapter(Context context, RecyclerView recyclerView) {
        mBitmaps = new ArrayList<>();
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
    public SimpleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(mContext).inflate(R.layout.recite_item, parent, false);
        return new SimpleViewHolder(view);
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public void onBindViewHolder(final SimpleViewHolder holder, final int position) {
        final AllQuestions.DataBean problem = datas.get(position);

        String str = problem.getDetail();
        if (position == 300 || position == 600 || position == 900 || position == 1200) {
            cleanBitmapList();
        }
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

//        holder.answer_explain.setText(problem.getDetail());
        //        显示解释和答案
        holder.answer.setVisibility(View.VISIBLE);
        holder.ll_explain.setVisibility(View.VISIBLE);
        holder.title.setText("       " + problem.getQuestion());
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
            if (!videoUrl.contains("swf")) {
                holder.iv_pic.setVisibility(View.GONE);
                holder.ll_video.setVisibility(View.VISIBLE);

                holder.videoView.setVideoURI(Uri.parse(videoUrl));
                holder.videoView.start();
                holder.videoView.requestFocus();

                holder.videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                    @Override
                    public void onPrepared(MediaPlayer mp) {
                        if (holder.mProgressBar != null)
                            holder.mProgressBar.setVisibility(View.GONE);
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
            }
        } else {
            holder.iv_pic.setVisibility(View.GONE);
            holder.ll_video.setVisibility(View.GONE);
        }
        holder.iv_1.setImageResource(R.mipmap.a);
        holder.iv_2.setImageResource(R.mipmap.b);
        holder.iv_3.setImageResource(R.mipmap.c);
        holder.iv_4.setImageResource(R.mipmap.d);
//        不可点击
        holder.choice_1.setEnabled(false);
        holder.choice_2.setEnabled(false);
        holder.choice_3.setEnabled(false);
        holder.choice_4.setEnabled(false);
        System.out.println("size===========" + datas.size());
//        判断题
        if (problem.getType().equals("3")) {
            holder.choice_icon.setImageResource(R.mipmap.pan_duan);
            holder.choice_3.setVisibility(View.GONE);
            holder.choice_4.setVisibility(View.GONE);
            holder.tv_1.setText("对");
            holder.tv_2.setText("错");
            if (problem.getAnswer().equals("0")) {
                holder.answer.setText("错误");
                choice_answer = "1";
            } else if (problem.getAnswer().equals("1")) {
                holder.answer.setText("正确");
                choice_answer = "0";
            }
            if (choice_answer.equals("0")) {
                holder.iv_1.setImageResource(R.mipmap.right);
                holder.tv_1.setTextColor(mContext.getResources().getColor(R.color.right_choice_color));
            } else if (choice_answer.equals("1")) {
                holder.iv_2.setImageResource(R.mipmap.right);
                holder.tv_2.setTextColor(mContext.getResources().getColor(R.color.right_choice_color));
            }
        }
//        选择题
        else if (problem.getType().equals("2")) {
            holder.choice_icon.setImageResource(R.mipmap.single_choice);
            holder.tv_1.setText(problem.getOption().get(0).substring(2));
            holder.tv_2.setText(problem.getOption().get(1).substring(2));
            holder.tv_3.setText(problem.getOption().get(2).substring(2));
            holder.tv_4.setText(problem.getOption().get(3).substring(2));
            if (problem.getAnswer().equals("1")) {
                holder.answer.setText("A");
            } else if (problem.getAnswer().equals("2")) {
                holder.answer.setText("B");
            } else if (problem.getAnswer().equals("4")) {
                holder.answer.setText("C");
            } else if (problem.getAnswer().equals("8")) {
                holder.answer.setText("D");
            }
            if (problem.getAnswer().equals("1")) {
                choice_answer = "1";
            } else if (problem.getAnswer().equals("2")) {
                choice_answer = "2";
            } else if (problem.getAnswer().equals("4")) {
                choice_answer = "3";
            } else if (problem.getAnswer().equals("8")) {
                choice_answer = "4";
            }
            if (choice_answer.equals("1")) {
                holder.iv_1.setImageResource(R.mipmap.right);
                holder.tv_1.setTextColor(mContext.getResources().getColor(R.color.right_choice_color));
            } else if (choice_answer.equals("2")) {
                holder.iv_2.setImageResource(R.mipmap.right);
                holder.tv_2.setTextColor(mContext.getResources().getColor(R.color.right_choice_color));
            } else if (choice_answer.equals("3")) {
                holder.iv_3.setImageResource(R.mipmap.right);
                holder.tv_3.setTextColor(mContext.getResources().getColor(R.color.right_choice_color));
            } else if (choice_answer.equals("4")) {
                holder.iv_4.setImageResource(R.mipmap.right);
                holder.tv_4.setTextColor(mContext.getResources().getColor(R.color.right_choice_color));
            }
        } else if (problem.getType().equals("1")) {//多选题
            holder.tv_1.setText(problem.getOption().get(0).substring(2));
            holder.tv_2.setText(problem.getOption().get(1).substring(2));
            holder.tv_3.setText(problem.getOption().get(2).substring(2));
            holder.tv_4.setText(problem.getOption().get(3).substring(2));
            holder.choice_icon.setImageResource(R.mipmap.many_choice);
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
            String answer1 = Integer.toBinaryString(Integer.parseInt(problem.getAnswer()));
            if (answer1.length() == 1) {
                answer1 = "000".concat(answer1);
            } else if (answer1.length() == 2) {
                answer1 = "00".concat(answer1);
            } else if (answer1.length() == 3) {
                answer1 = "0".concat(answer1);
            }
            System.out.println("answer1==========" + answer1);
            if (String.valueOf(answer1.charAt(0)).equals("1")) {
                holder.iv_4.setImageResource(R.mipmap.right);
                holder.tv_4.setTextColor(mContext.getResources().getColor(R.color.right_choice_color));
            }
            if (String.valueOf(answer1.charAt(1)).equals("1")) {
                holder.iv_3.setImageResource(R.mipmap.right);
                holder.tv_3.setTextColor(mContext.getResources().getColor(R.color.right_choice_color));
            }
            if (String.valueOf(answer1.charAt(2)).equals("1")) {
                holder.iv_2.setImageResource(R.mipmap.right);
                holder.tv_2.setTextColor(mContext.getResources().getColor(R.color.right_choice_color));
            }
            if (String.valueOf(answer1.charAt(3)).equals("1")) {
                holder.iv_1.setImageResource(R.mipmap.right);
                holder.tv_1.setTextColor(mContext.getResources().getColor(R.color.right_choice_color));
            }
        }

    }

    @Override
    public void onViewDetachedFromWindow(SimpleViewHolder holder) {
        super.onViewDetachedFromWindow(holder);
        holder.mProgressBar.setVisibility(View.VISIBLE);
        holder.videoView.stopPlayback();
        holder.videoView.suspend();
    }

    @Override
    public int getItemCount() {
        return datas != null ? datas.size() : 0;
    }
}
