package com.wanzheng.driver.order;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.haha.exam.R;
import com.wanzheng.driver.Entity.OrdershowEntity;

public class OrderAdapter extends BaseAdapter {
	private Context context;
	private ArrayList<OrdershowEntity> orderList;
	private Order order1;

	private final int TYPE_COUNT = 3;
	private final int eDriver_TYPE = 0;
	private final int eStudy_TYPE = 1;
	private final int eAccompany_TYPE = 2;
	private int currentType;

	public OrderAdapter(Context context, ArrayList<OrdershowEntity> orderList) {
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

	@Override
	public int getItemViewType(int position) {

			return eAccompany_TYPE;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup arg2) {
		// 获取到当前位置所对应的Type
		currentType = getItemViewType(position);
		final OrdershowEntity order = orderList.get(position);

		View eDriverView = null;
		View eStudyView = null;
		View eAccompanyView = null;
		// 获取到当前位置所对应的Type
		currentType = getItemViewType(position);
		
			eStudyView = convertView;
			EstudyViewHolder estudyViewHolder = null;
			if (eStudyView == null) {
				eStudyView = LayoutInflater.from(context).inflate(
						R.layout.study_order_item, null);
				estudyViewHolder = new EstudyViewHolder();

				estudyViewHolder.titleTxt = (TextView) eStudyView
						.findViewById(R.id.time_study_order_title);
				estudyViewHolder.carTxt = (TextView) eStudyView
						.findViewById(R.id.time_history_order_car);
				estudyViewHolder.dateTxt = (TextView) eStudyView
						.findViewById(R.id.time_history_order_date);
				estudyViewHolder.priceTxt = (TextView) eStudyView
						.findViewById(R.id.time_history_order_price);
				estudyViewHolder.nameTxt = (TextView) eStudyView
						.findViewById(R.id.time_history_order_coachname);
				estudyViewHolder.stateTxt = (TextView) eStudyView
						.findViewById(R.id.time_history_order_state);
				eStudyView.setTag(estudyViewHolder);
			} else {

				estudyViewHolder = (EstudyViewHolder) eStudyView.getTag();
			}
				estudyViewHolder.titleTxt.setText("哈哈计时");
				estudyViewHolder.nameTxt.setText(order.getJiaolianxingming());

				estudyViewHolder.carTxt.setText(order.getSubject());
				if (order.getEnd().length()>11)
				estudyViewHolder.dateTxt.setText(order.getStart()+"—"+order.getEnd().substring(11));
		

			// 
//			estudyViewHolder.dateTxt.setText(order.getBookingTime().substring(
//					5, 10)
//					+ "  " + order.getDate());
//			estudyViewHolder.priceTxt.setText(order.getPrice() + "元");
//			estudyViewHolder.nameTxt.setText(order.getCoachName());
//			estudyViewHolder.carTxt.setText(order.getCar());
			switch (order.getMoneyState()) {
			case 0:
				estudyViewHolder.stateTxt.setText("待支付");
				estudyViewHolder.priceTxt.setText(order.getMoney()+"元");
				break;
			case 1:
				estudyViewHolder.stateTxt.setText("已支付");
				if("-1.0".equals(String.valueOf(order.getMoney())))
					estudyViewHolder.priceTxt.setText("一费制");
				else
					estudyViewHolder.priceTxt.setText(order.getMoney()+"元");
				break;
			case 2:
				estudyViewHolder.stateTxt.setText("已完成");
				if("-1.0".equals(String.valueOf(order.getMoney())))
					estudyViewHolder.priceTxt.setText("一费制");
				else
					estudyViewHolder.priceTxt.setText(order.getMoney()+"元");
				break;

			default:
				break;
			}

			convertView = eStudyView;


		return convertView;
	}

	class EdriverViewHolder {

		TextView subjectTxt;
		TextView dateTxt;
		TextView priceTxt;
		TextView nameTxt;
		TextView stateTxt;
		TextView reviewText;
	}

	class EstudyViewHolder {
		TextView titleTxt;
		TextView carTxt;
		TextView dateTxt;
		TextView priceTxt;
		TextView nameTxt;
		TextView stateTxt;
	}

}
