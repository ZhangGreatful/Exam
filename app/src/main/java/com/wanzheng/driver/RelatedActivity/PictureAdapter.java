package com.wanzheng.driver.RelatedActivity;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.haha.exam.R;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.wanzheng.driver.Entity.Picture;
import com.wanzheng.driver.view.SmoothImageView;

import java.util.List;

/**
 * Created by Administrator on 2017/1/11.
 */
public class PictureAdapter extends RecyclerView.Adapter<PictureAdapter.PictureHolder> {

    private Picture picture;
    private Context context;
    private LayoutInflater layoutInflater;
    private List<Picture.MsgBean.ReturnDataBean> datas;
    private ImageLoader imageLoader;
    DisplayImageOptions options;
    private String imageUrl;

    //    添加recycleview的点击事件的监听
    public interface OnItemClickLitener {
        void onItemClick(View view, int position);
    }

    private OnItemClickLitener mOnItemClickLitener;

    public void setOnItemClickLitener(OnItemClickLitener mOnItemClickLitener) {
        this.mOnItemClickLitener = mOnItemClickLitener;
    }

    public PictureAdapter(Context context, Picture picture) {
        this.context = context;
        this.picture = picture;
        datas = picture.getMsg().getReturnData();
        layoutInflater = LayoutInflater.from(context);
        options = new DisplayImageOptions.Builder()
                .imageScaleType(ImageScaleType.EXACTLY_STRETCHED)//设置图片以如何的编码方式显示
                .bitmapConfig(Bitmap.Config.RGB_565)//设置图片的解码类型//
                .resetViewBeforeLoading(true)//设置图片在下载前是否重置，复位
                .build();//构建完成
    }

    @Override
    public PictureAdapter.PictureHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new PictureHolder(layoutInflater.inflate(R.layout.item_image, parent, false));
    }

    @Override
    public void onBindViewHolder(final PictureAdapter.PictureHolder holder, int position) {
        imageUrl = datas.get(position).getPhoto_url();
        imageLoader.getInstance().displayImage(imageUrl, holder.imageView, options);

        // 如果设置了回调，则设置点击事件
        if (mOnItemClickLitener != null) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = holder.getLayoutPosition();
                    mOnItemClickLitener.onItemClick(holder.itemView, pos);
                }
            });
        }
    }

    public void setDataList(List<Picture.MsgBean.ReturnDataBean> datas) {
        this.datas = datas;
        notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    public static class PictureHolder extends RecyclerView.ViewHolder {
        SmoothImageView imageView;

        public PictureHolder(View itemView) {
            super(itemView);
            imageView = (SmoothImageView) itemView.findViewById(R.id.image);
        }
    }

    public void AddFooterItem(Picture picture) {
        List<Picture.MsgBean.ReturnDataBean> dataBeen = picture.getMsg().getReturnData();
        datas.addAll(dataBeen);
        notifyDataSetChanged();
    }
}
