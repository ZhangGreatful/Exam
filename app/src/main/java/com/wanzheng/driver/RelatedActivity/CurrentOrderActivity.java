package com.wanzheng.driver.RelatedActivity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.haha.exam.R;
import com.wanzheng.driver.Entity.BaseResult;
import com.wanzheng.driver.Entity.OrdershowEntity;
import com.wanzheng.driver.home.Activity_OrderShow;
import com.wanzheng.driver.home.Activity_Pay;
import com.wanzheng.driver.home.Activity_PerInfor;
import com.wanzheng.driver.network.JsonUtils;
import com.wanzheng.driver.network.NetInterface;
import com.wanzheng.driver.network.NetWorkCallBack;
import com.wanzheng.driver.network.NetWorkUtils;
import com.wanzheng.driver.util.Keys;
import com.wanzheng.driver.util.SystemUtil;
import com.wanzheng.driver.util.ToastUtil;




public class CurrentOrderActivity extends Activity implements
		OnClickListener {
	private OrdershowEntity order, order2;
	private TextView coachNameTxt,subjectTxt, startTimeTxt, endTimeTxt, totalTimeTxt, stateTxt
					,slongTxt,paymoneyTxt,viewshowTxt;
	private Button pay_order_monney,pay_order_view_submit;
	// 状态图片
	// 状态图片
	private ImageView orderImg,receiveImg,serviceImg,liuchengImg;
	private ImageView line1Img, line2Img;
	private TextView txt1, txt2, txt3;
	private LinearLayout reviewLayout,layout_pay;
	private EditText reviewEdit;



	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_current_order);
		init();
		initView();
		initData();
	}

	private void init() {
		Bundle b = getIntent().getExtras();
		order = (OrdershowEntity) b.getSerializable(Keys.ORDER);
	}

	private void initView() {
		TextView tv_title =(TextView) findViewById(R.id.titlebar_tv);
		tv_title.setVisibility(View.VISIBLE);
		ImageView titlebar_back = (ImageView)findViewById(R.id.titlebar_back);
		titlebar_back.setVisibility(View.VISIBLE);
		titlebar_back.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent=new Intent(CurrentOrderActivity.this,Activity_OrderShow.class);
				startActivity(intent);
				finish();
			}
		});
		tv_title.setText("订单详情");
		orderImg = (ImageView) findViewById(R.id.current_order_order);
		receiveImg = (ImageView) findViewById(R.id.current_order_receive);
		serviceImg = (ImageView) findViewById(R.id.current_order_service);
		line1Img = (ImageView) findViewById(R.id.current_order_line_1);
		line2Img = (ImageView) findViewById(R.id.current_order_line_2);
		
		liuchengImg=(ImageView) findViewById(R.id.current_order_liucheng);
		
		coachNameTxt = (TextView) findViewById(R.id.current_order_coach_name);
		subjectTxt = (TextView) findViewById(R.id.current_order_coach_subject);
		startTimeTxt = (TextView) findViewById(R.id.current_order_time_start);
		endTimeTxt = (TextView) findViewById(R.id.current_order_time_end);
		totalTimeTxt = (TextView) findViewById(R.id.current_order_total_time);
		stateTxt = (TextView) findViewById(R.id.current_order_state);
		slongTxt = (TextView) findViewById(R.id.current_order_long);
		viewshowTxt= (TextView) findViewById(R.id.current_order_review_txt);
		paymoneyTxt = (TextView) findViewById(R.id.current_order_pay_moneny);
		reviewEdit=(EditText) findViewById(R.id.current_order_review);
		
		pay_order_monney = (Button) findViewById(R.id.pay_order_monney);
		pay_order_monney.setOnClickListener(this);
		pay_order_view_submit= (Button) findViewById(R.id.pay_order_view_submit);
		pay_order_view_submit.setOnClickListener(this);
		
		txt1 = (TextView) findViewById(R.id.txt1);
		txt2 = (TextView) findViewById(R.id.txt2);
		txt3 = (TextView) findViewById(R.id.txt3);
		reviewLayout=(LinearLayout) findViewById(R.id.layout_review);
		layout_pay=(LinearLayout) findViewById(R.id.layout_pay);
		if (order.getMoneyState() == 0) {
			stateTxt.setText("等待支付");
			reviewLayout.setVisibility(View.GONE);
		} else if (order.getMoneyState() == 1) {
			stateTxt.setText("未评价");
			liuchengImg.setImageResource(R.drawable.order_liucheng_2);
			receiveImg.setImageResource(R.drawable.point_yes);
			line1Img.setImageResource(R.drawable.line_press);
			layout_pay.setVisibility(View.GONE);
			txt2.setTextColor(getResources().getColor(R.color.order_txt));
			pay_order_view_submit.setVisibility(View.VISIBLE);

		} else if (order.getMoneyState() == 2) {
			stateTxt.setText("已完成");
			liuchengImg.setImageResource(R.drawable.order_liucheng_3);
			viewshowTxt.setVisibility(View.VISIBLE);
			viewshowTxt.setText(order.getPingjiaContent());
			reviewEdit.setVisibility(View.GONE);
			receiveImg.setImageResource(R.drawable.point_yes);
			line1Img.setImageResource(R.drawable.line_press);
			serviceImg.setImageResource(R.drawable.point_yes);
			line2Img.setImageResource(R.drawable.line_press);
			txt2.setTextColor(getResources().getColor(R.color.order_txt));
			txt3.setTextColor(getResources().getColor(R.color.order_txt));
			layout_pay.setVisibility(View.GONE);

		}
		stateTxt.setTextColor(getResources().getColor(R.color.red));
	}

	private NetWorkCallBack<BaseResult> coachDetailCallBack = new NetWorkCallBack<BaseResult>() {

		@Override
		public void onComplete(String json) {
		}
	};

	private void initData() {
		coachNameTxt.setText(order.getJiaolianxingming());
		subjectTxt.setText(order.getSubject());
		startTimeTxt.setText(order.getStart());
		endTimeTxt.setText(order.getEnd());
		totalTimeTxt.setText(order.getShiChang()+"分钟(有效时长)");
		totalTimeTxt.setTextColor(getResources().getColor(R.color.red));
		slongTxt.setText(order.getDistance()+"km");
		//viewshowTxt= (TextView) findViewById(R.id.current_order_review_txt);
		paymoneyTxt.setText("应支付金额:"+order.getMoney()+ "元");
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.pay_order_monney:
			Bundle bundle1 = new Bundle();
			Intent intent = new Intent(CurrentOrderActivity.this,
					Activity_Pay.class);
			bundle1.putString("money", order.getMoney()+"");
			bundle1.putInt("modle", 1);
			bundle1.putInt("xueshiid", order.getID());
			intent.putExtras(bundle1);
			startActivity(intent);
			finish();
			break;
		case R.id.pay_order_view_submit:
			String content=reviewEdit.getText().toString();
			if(content==null||content.equals("")){
				return;
			}
			pay_order_view_submit.setEnabled(false);
			new NetWorkUtils().getInstance().work(
					new NetInterface().getInstance().submitReview(
							order.getXid(),order.getID(), order.getJid(),content),
							orderReciewCallBack);
			break;
		default:
			break;
		}
	}

	
	

	private void setState(int state) {
		if (state == 1) {
			stateTxt.setText("等待教练接单");
		} else if (state == 2) {
			stateTxt.setText("已销单");
		} else if (state == 3) {
			stateTxt.setText("预约成功");
		} 
	}

	
	private NetWorkCallBack<BaseResult> orderReciewCallBack = new NetWorkCallBack<BaseResult>() {

		@Override
		public void onComplete(String json) {
			int msg = JsonUtils.parseCodeJson(json);
			if(msg!=1){
				ToastUtil.show(CurrentOrderActivity.this, "提交失败");
				pay_order_view_submit.setEnabled(true);
			}else{
				ToastUtil.show(CurrentOrderActivity.this, "提交成功");
				Intent intent=new Intent(CurrentOrderActivity.this,Activity_OrderShow.class);
				startActivity(intent);
				finish();
			}
			
		}
	};
	public void call(String tel) {
		// 用intent启动拨打电话
		Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + tel));
		startActivity(intent);
	}
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if(keyCode==KeyEvent.KEYCODE_BACK){
			Intent intent=new Intent(CurrentOrderActivity.this,Activity_OrderShow.class);
			startActivity(intent);
			finish();
		}
		return false;
	}

}
