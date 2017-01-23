package com.wanzheng.driver.RelatedActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.haha.exam.R;
import com.wanzheng.driver.Entity.BaseResult;
import com.wanzheng.driver.home.BasicMapActivity;
import com.wanzheng.driver.network.JsonUtils;
import com.wanzheng.driver.network.NetInterface;
import com.wanzheng.driver.network.NetWorkCallBack;
import com.wanzheng.driver.network.NetWorkUtils;
import com.wanzheng.driver.util.GpsEntity;

import java.util.ArrayList;
import java.util.List;

public class PeriodAdapter extends BaseAdapter {
	private Context context;
	private ArrayList<Period> orderList;
	public static List<GpsEntity> listGps;
	private Handler handler=new Handler(){
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what){
				case 1:
					Intent intent=new Intent();
//					intent.putExtra("GPS", (Serializable) listGps);
					intent.setClass(context,BasicMapActivity.class);
					context.startActivity(intent);
					break;
				case 2:
					break;
			}

		}
	};

	private final int TYPE_COUNT = 3;
	private final int eAccompany_TYPE = 2;

	public PeriodAdapter(){};
	public PeriodAdapter(Context context, ArrayList<Period> orderList) {
		super();
		this.context = context;
		this.orderList = orderList;
	}

	@Override
	public int getCount() {
		return orderList.size();
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

	public void AddFooterItem(ArrayList<Period> dataBeen) {
		orderList.addAll(dataBeen);
		notifyDataSetChanged();
	}

	@Override
	public int getItemViewType(int position) {

			return eAccompany_TYPE;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup arg2) {
		// 获取到当前位置所对应的Type
		final Period order = orderList.get(position);

		View eDriverView = null;
		View eStudyView = null;
		View eAccompanyView = null;
		// 获取到当前位置所对应的Type

			eStudyView = convertView;
			PstudyViewHolder estudyViewHolder = null;
			if (eStudyView == null) {
				eStudyView = LayoutInflater.from(context).inflate(
						R.layout.study_period_item, null);
				estudyViewHolder = new PstudyViewHolder();

				estudyViewHolder.period_time = (TextView) eStudyView
						.findViewById(R.id.period_time);
				estudyViewHolder.period_num = (TextView) eStudyView
						.findViewById(R.id.period_num);
				estudyViewHolder.period_coachname = (TextView) eStudyView
						.findViewById(R.id.period_coachname);
				estudyViewHolder.period_subject = (TextView) eStudyView
						.findViewById(R.id.period_subject);
				estudyViewHolder.period_xunlianchangdi = (TextView) eStudyView
						.findViewById(R.id.period_xunlianchangdi);

				estudyViewHolder.period_carnum = (TextView) eStudyView
						.findViewById(R.id.period_carnum);
				estudyViewHolder.period_picshow = (LinearLayout) eStudyView
						.findViewById(R.id.period_picshow);
				estudyViewHolder.period_guijishow = (LinearLayout) eStudyView
						.findViewById(R.id.period_guijishow);
				eStudyView.setTag(estudyViewHolder);
			} else {

				estudyViewHolder = (PstudyViewHolder) eStudyView.getTag();
			}


		estudyViewHolder.period_num.setText(order.getXueShiBianHao());
		estudyViewHolder.period_coachname.setText(order.getJiaolianxingming());
		estudyViewHolder.period_subject.setText(order.getSubject());
		estudyViewHolder.period_xunlianchangdi.setText(order.getJiaxiaomingcheng());
		if (order.getChepai().equals("")) {
			estudyViewHolder.period_carnum.setVisibility(View.GONE);
		}else
			estudyViewHolder.period_carnum.setText(order.getChepai());
		if (order.getEnd().length()>11)
			estudyViewHolder.period_time.setText(order.getStart()+"—"+order.getEnd().substring(11));
		estudyViewHolder.period_picshow.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent=new Intent();
				intent.putExtra("xueshiid", order.getId()+"");
				intent.setClass(context,Activity_PeriodPicShow.class);
				context.startActivity(intent);
			}
		});
		final PstudyViewHolder finalEstudyViewHolder = estudyViewHolder;
		estudyViewHolder.period_guijishow.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				new NetWorkUtils().getInstance().work(
						new NetInterface().getInstance().StudyGPS(order.getId()+""),
						orderCurrenCallBack2);

			}
		});


			convertView = eStudyView;


		return convertView;
	}



	class PstudyViewHolder {
		TextView period_time;
		TextView period_num;
		TextView period_coachname;
		TextView period_subject;
		TextView period_xunlianchangdi;
		TextView period_carnum;
		LinearLayout period_picshow;
		LinearLayout period_guijishow;
	}
	private NetWorkCallBack<BaseResult> orderCurrenCallBack2 = new NetWorkCallBack<BaseResult>() {

		@Override
		public void onComplete(String json) {
			listGps= JsonUtils.periodGpsGuiji(json);
			if (listGps == null&&listGps.size()==0) {
				return;
			}
			handler.sendEmptyMessage(1);

		}
	};

}
