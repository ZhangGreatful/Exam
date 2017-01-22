package com.wanzheng.driver.order;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;

import com.haha.exam.R;
import com.wanzheng.driver.Entity.BaseResult;
import com.wanzheng.driver.Entity.OrdershowEntity;
import com.wanzheng.driver.RelatedActivity.CurrentOrderActivity;
import com.wanzheng.driver.network.JsonUtils;
import com.wanzheng.driver.network.NetInterface;
import com.wanzheng.driver.network.NetWorkCallBack;
import com.wanzheng.driver.network.NetWorkUtils;
import com.wanzheng.driver.order.CustomListView.OnLoadMoreListener;
import com.wanzheng.driver.util.Keys;
import com.wanzheng.driver.util.SystemUtil;

import java.util.ArrayList;

/**
 * 
 */
@SuppressLint("ValidFragment")
public class OrderFragment extends Fragment {

	private CustomListView listView;
	private OrderAdapter orderAdapter;
	private ImageView noOrderImg;
	private ArrayList<OrdershowEntity> orderList;
	private ArrayList<OrdershowEntity> orderList2;
	private ArrayList<OrdershowEntity> orderList3;
	private Context context;

	private int position;
	// private Mine mine;
	int uid;
	private int pageCurrent = 1;// 当前页
	private int pageCount;// 总页数

	private int pageCurrent2 = 1;// 当前页
	private int pageCount2;// 总页数

	private int pageCurrent3 = 1;// 当前页
	private int pageCount3;// 总页数

	private int pageCurrent4 = 1;// 当前页
	private int pageCount4;// 总页数
	private static final int LOAD_DATA_FINISH = 10;

	@SuppressWarnings("unchecked")
	private Handler mHandler = new Handler() {

		public void handleMessage(Message msg) {
			switch (msg.what) {

			case LOAD_DATA_FINISH:
				listView.onLoadMoreCompleteAndRemoveFooter(); // 加载更多完成
				break;
			case 2:
				OrdershowEntity order =null;
				order= (OrdershowEntity) msg.getData().getSerializable(Keys.ORDER);
				Intent intent=new Intent(context,CurrentOrderActivity.class);
				Bundle b=new Bundle();
				b.putSerializable(Keys.ORDER, order);
				intent.putExtras(b);
				context.startActivity(intent);
				if (order.getMoneyState()==1) {
					((Activity) context).finish();
				}
				break;
			default:
				break;
			}
		};
	};

	public OrderFragment() {

	}

	public OrderFragment(int position,Context context) {
		super();
		this.position = position;
		this.context=context;
	}

	private void initData() {
		// TODO Auto-generated method stub
		//startProgressDialog();
		pageCurrent = 1;
		pageCurrent2 = 1;
		pageCurrent3 = 1;
		pageCurrent4 = 1;
		orderList = new ArrayList<OrdershowEntity>();
		orderList2 = new ArrayList<OrdershowEntity>();
		orderList3 = new ArrayList<OrdershowEntity>();
		SystemUtil su = new SystemUtil(getActivity());
		uid = su.showUid();
		if (uid != -1) {

			switch (position) {
			case 0:
				new NetWorkUtils().getInstance().work(
						new NetInterface().getInstance().queryStudentOrderCurr(
								uid,0, pageCurrent),
						orderCurrenCallBack);
				break;
			case 1:

				new NetWorkUtils().getInstance().work(
				new NetInterface().getInstance().queryStudentOrderCurr(
						uid,1, pageCurrent2),
				orderCurrenCallBack2);
				break;
			case 2:
				new NetWorkUtils().getInstance().work(
						new NetInterface().getInstance().queryStudentOrderCurr(
								uid,2, pageCurrent3),
						orderCurrenCallBack3);
				break;
				
			default:
				break;
			}

		} else {
			//stopProgressDialog();
			//Toast.makeText(getActivity(), "您还没有登录，请先登录", 1).show();
			// noLogin();
		}

	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.activity_order_list, container,
				false);
		return view;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onViewCreated(view, savedInstanceState);
		initView();
		initData();
	}

	@Override
	public void onStart() {
		super.onStart();
	}

	private void initView() {
		noOrderImg = (ImageView) getView().findViewById(R.id.no_order);
		listView = (CustomListView) getView().findViewById(R.id.list);
		listView.onRefreshComplete();
		listView.setCanRefresh(false);
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int index,
					long arg3) {
				OrdershowEntity order =null;
				Intent intent=new Intent(context,CurrentOrderActivity.class);
				Bundle b=new Bundle();
				//getActivity().finish();
				switch (position) {
					case 0:
						order = orderList.get(index-1);
						break;
					case 1:
						order = orderList2.get(index-1);
						break;
					case 2:
						order = orderList3.get(index-1);
						break;
					default:
						break;
				}

				b.putSerializable(Keys.ORDER, order);
				intent.putExtras(b);
				context.startActivity(intent);
				if (order.getMoneyState()==1) {
					((Activity) context).finish();
				}


				
			}
		});
		listView.setOnLoadListener(new OnLoadMoreListener() {

			@Override
			public void onLoadMore() {
				loadData(1);
			}
		});

	}
	private NetWorkCallBack<BaseResult> orderDeilCallBack = new NetWorkCallBack<BaseResult>() {

		@Override
		public void onComplete(String json) {
			ArrayList<OrdershowEntity> order1 = JsonUtils.parsePayorder(json);
			Message msg=new Message();
			msg.what=2;
			Bundle bb=new Bundle();
			bb.putSerializable(Keys.ORDER,order1.get(0));
			msg.setData(bb);
			mHandler.sendMessage(msg);
		}
	};

	public void loadData(final int type) {
		new Thread() {
			@Override
			public void run() {
				switch (type) {
				case 0:// 下拉刷新 暂不适用
						// refresh = true;
						// int count = pageCurrent * NumUtil.NUM20;
					pageCurrent = 1;// 如果下拉刷新就从新加载
					initData();
					break;
				case 1:
					switch (position) {
					case 0:
						if (pageCurrent <= pageCount)
							new NetWorkUtils().getInstance().work(
									new NetInterface().getInstance().queryStudentOrderCurr(
											uid,0, pageCurrent),
									orderCurrenCallBack);
						else {// 如果是首页且数据不足一页 或者已经是最后一页
							Message _Msg = mHandler
									.obtainMessage(LOAD_DATA_FINISH);
							mHandler.sendMessage(_Msg);
						}
						break;
					case 1:
						if (pageCurrent2 <= pageCount2)
						new NetWorkUtils().getInstance().work(
								new NetInterface().getInstance().queryStudentOrderCurr(
										uid,1, pageCurrent2),
								orderCurrenCallBack2);
						else {// 如果是首页且数据不足一页 或者已经是最后一页
							Message _Msg = mHandler
									.obtainMessage(LOAD_DATA_FINISH);
							mHandler.sendMessage(_Msg);
						}
						break;
					case 2:
						if (pageCurrent3 <= pageCount3)
							new NetWorkUtils().getInstance().work(
									new NetInterface().getInstance().queryStudentOrderCurr(
											uid,1, pageCurrent3),
									orderCurrenCallBack3);
						else {// 如果是首页且数据不足一页 或者已经是最后一页
							Message _Msg = mHandler
									.obtainMessage(LOAD_DATA_FINISH);
							mHandler.sendMessage(_Msg);
						}
						break;
					default:
						break;
					}

				}
			}
		}.start();

	}

	private void initListView() {
		if (orderList != null && orderList.size() != 0) {
			orderAdapter = new OrderAdapter(getActivity(), orderList);
			listView.setAdapter(orderAdapter);
			noOrderImg.setVisibility(View.GONE);
		} else {
			noOrderImg.setVisibility(View.VISIBLE);
		}
	}

	private void initListView2() {
		if (orderList2 != null && orderList2.size() != 0) {
			orderAdapter = new OrderAdapter(getActivity(), orderList2);
			listView.setAdapter(orderAdapter);
			noOrderImg.setVisibility(View.GONE);
		} else {
			noOrderImg.setVisibility(View.VISIBLE);
		}
	}

	private void initListView3() {
		if (orderList3 != null && orderList3.size() != 0) {
			orderAdapter = new OrderAdapter(getActivity(), orderList3);
			listView.setAdapter(orderAdapter);
			noOrderImg.setVisibility(View.GONE);
		} else {
			noOrderImg.setVisibility(View.VISIBLE);
		}
	}


	private NetWorkCallBack<BaseResult> orderCurrenCallBack = new NetWorkCallBack<BaseResult>() {

		@Override
		public void onComplete(String json) {
			paging(json);
			// orderList = JsonUtils.parseOrderHistory(json);

		}
	};
	private NetWorkCallBack<BaseResult> orderCurrenCallBack2 = new NetWorkCallBack<BaseResult>() {

		@Override
		public void onComplete(String json) {
			paging2(json);
			// orderList = JsonUtils.parseOrderHistory(json);

		}
	};

	private NetWorkCallBack<BaseResult> orderCurrenCallBack3 = new NetWorkCallBack<BaseResult>() {

		@Override
		public void onComplete(String json) {
			paging3(json);
			// orderList = JsonUtils.parseOrderHistory(json);

		}
	};


	// 分页
	private void paging(String json) {
//		if (getActivity() != null && progressDialog != null
//				&& progressDialog.isShowing()) {
//			progressDialog.dismiss();
//		}
		if (pageCurrent == 1) {// 首次加载
			pageCount = JsonUtils.parsePageCount(json);

			if (pageCount >=pageCurrent) {
				pageCurrent++;
			} else if(pageCount==0){
				return;
			}
			ArrayList<OrdershowEntity> firstList = JsonUtils.parsePayorder(json);
			orderList.addAll(firstList);
			initListView();
		} else {
			ArrayList<OrdershowEntity> otherList = JsonUtils.parsePayorder(json);
			orderList.addAll(otherList);
			orderAdapter.notifyDataSetChanged();
			if (pageCurrent >= pageCount) {// 如果没有更多页数
				listView.setCanLoadMore(false);
			}
			listView.onLoadMoreComplete();

		}
	}

	// 分页
	private void paging2(String json) {
//		if (getActivity() != null && progressDialog != null
//				&& progressDialog.isShowing()) {
//			progressDialog.dismiss();
//		}
		if (pageCurrent2 == 1) {// 首次加载
			pageCount2 = JsonUtils.parsePageCount(json);

			if (pageCount2 >=pageCurrent2) {
				pageCurrent2++;
			} else if(pageCount2==0){
				return;
			}
			ArrayList<OrdershowEntity> firstList = JsonUtils.parsePayorder(json);
			orderList2.addAll(firstList);
			initListView2();
		} else {

			ArrayList<OrdershowEntity> otherList = JsonUtils.parsePayorder(json);
			orderList2.addAll(otherList);
			orderAdapter.notifyDataSetChanged();
			if (pageCurrent2 >= pageCount2) {// 如果没有更多页数
				listView.setCanLoadMore(false);
			}
			listView.onLoadMoreComplete();

		}
	}

	// 分页
	private void paging3(String json) {
//		if (getActivity() != null && progressDialog != null
//				&& progressDialog.isShowing()) {
//			progressDialog.dismiss();
//		}
		if (pageCurrent3 == 1) {// 首次加载
			pageCount3 = JsonUtils.parsePageCount(json);

			if (pageCount3 >=pageCurrent3) {
				pageCurrent3++;
			} else if(pageCount3==0){
				return;
			}
			ArrayList<OrdershowEntity> firstList = JsonUtils.parsePayorder(json);
			orderList3.addAll(firstList);
			initListView3();
		} else {

			ArrayList<OrdershowEntity> otherList = JsonUtils.parsePayorder(json);
			orderList3.addAll(otherList);
			orderAdapter.notifyDataSetChanged();
			if (pageCurrent3 >= pageCount3) {// 如果没有更多页数
				listView.setCanLoadMore(false);
			}
			listView.onLoadMoreComplete();

		}
	}

	
//	private CustomProgressDialog progressDialog = null;
//
//	private void startProgressDialog() {
//		if (progressDialog == null && getActivity() != null) {
//			progressDialog = CustomProgressDialog.createDialog(getActivity());
//		}
//
//		if (!progressDialog.isShowing() && getActivity() != null) {
//			progressDialog.show();
//		}
//	}
//
//	private void stopProgressDialog() {
//		if (progressDialog != null) {
//			progressDialog.dismiss();
//			progressDialog.cancel();
//			progressDialog = null;
//		}
//	}
}
