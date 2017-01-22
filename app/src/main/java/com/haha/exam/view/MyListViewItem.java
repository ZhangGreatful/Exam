package com.haha.exam.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;

import com.haha.exam.R;
import com.haha.exam.bean.Four;

public class MyListViewItem extends LinearLayout {
	private View view;
	private LayoutInflater inflater;
	private Context mContext;
	int position;
	private Four four;

	public MyListViewItem(Context context) {
		super(context);
		mContext = context;
		init();
	}

	public MyListViewItem(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		mContext = context;
		init();
	}

	public void setPosition(int p) {
		this.position = p;
	}

	public void setFour(Four f) {
		this.four = f;
	}

	public void init() {
		this.setOrientation(LinearLayout.VERTICAL);
		inflater = LayoutInflater.from(mContext);
		view = inflater.inflate(R.layout.four_item, null);
		this.addView(view);
	}

	public View findView(int id) {
		return view.findViewById(id);
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// TODO Auto-generated method stub
		// Toast.makeText(mContext, "事件点击", Toast.LENGTH_SHORT).show();
		// Intent intent2 = new Intent(mContext,
		// ExaminationDetailActivity.class);
		// //Four four = parseEsoterica.get(position - 1);
		// intent2.putExtra("DriveHead", four);
		// intent2.putExtra("position", position );
		// // startActivity(intent2);
		// ((Activity)mContext).startActivityForResult(intent2, 100);
		// return true;
		return super.onTouchEvent(event);
	}
 
}
