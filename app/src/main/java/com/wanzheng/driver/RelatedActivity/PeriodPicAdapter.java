package com.wanzheng.driver.RelatedActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.haha.exam.R;
import com.haha.exam.view.MyImgeview;
import com.squareup.picasso.Picasso;
import com.wanzheng.driver.Entity.BaseResult;
import com.wanzheng.driver.home.BasicMapActivity;
import com.wanzheng.driver.network.JsonUtils;
import com.wanzheng.driver.network.NetInterface;
import com.wanzheng.driver.network.NetWorkCallBack;
import com.wanzheng.driver.network.NetWorkUtils;
import com.wanzheng.driver.util.GpsEntity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class PeriodPicAdapter extends BaseAdapter {
	private Context context;
	private List<PassPicEntity> orderList;


	private final int TYPE_COUNT = 3;
	private final int eAccompany_TYPE = 2;
	public PeriodPicAdapter(Context context, List<PassPicEntity> orderList) {
		super();
		this.context = context;
		this.orderList = orderList;
	}

	@Override
	public int getCount() {
		int siz=orderList.size();
		if (orderList.size()!=0&&siz%2==1){
			siz=siz/2+1;
		}else{
			siz=siz/2;
		}
		return siz;

	}

	@Override
	public Object getItem(int position) {
		return position;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public int getViewTypeCount() {
		return TYPE_COUNT;
	}

	@Override
	public int getItemViewType(int position) {

			return eAccompany_TYPE;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup arg2) {
		// 获取到当前位置所对应的Type
		final PassPicEntity order = orderList.get(position);

		View eDriverView = null;
		View eStudyView = null;
		View eAccompanyView = null;
		// 获取到当前位置所对应的Type

			eStudyView = convertView;
			PstudyViewHolder estudyViewHolder = null;
			if (eStudyView == null) {
				eStudyView = LayoutInflater.from(context).inflate(
						R.layout.study_pic_item, null);
				estudyViewHolder = new PstudyViewHolder();

				estudyViewHolder.pass = (MyImgeview) eStudyView
						.findViewById(R.id.pic_pass1);
				estudyViewHolder.pass2 = (MyImgeview) eStudyView
						.findViewById(R.id.pic_pass2);
				eStudyView.setTag(estudyViewHolder);
			} else {

				estudyViewHolder = (PstudyViewHolder) eStudyView.getTag();
			}

		if (orderList.get(position*2).getPicurl().contains("jpg")) {
			Picasso.with(context).load(orderList.get(position*2).getPicurl()).into(estudyViewHolder.pass);
		}
		if (orderList.size()-1>position*2) {
			if (orderList.get(position*2+1).getPicurl().contains("jpg")) {
				Picasso.with(context).load(orderList.get(position*2+1).getPicurl()).into(estudyViewHolder.pass2);
			}
		}



			convertView = eStudyView;


		return convertView;
	}



	class PstudyViewHolder {
		MyImgeview pass;
		MyImgeview pass2;
	}

}
