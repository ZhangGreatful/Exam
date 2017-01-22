package com.wanzheng.driver.util;


import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.haha.exam.R;

public class ActionTitlebar extends RelativeLayout implements OnClickListener {

	private TextView tv_title;
	// for left
	private ImageView btn_backbtn;
	private TextView btn_location;
	private FrameLayout layout_left;
	// for right
	private FrameLayout layout_right, layout_right_second;
	private TextView btn_right_text;
	private ImageView btn_right_image, btn_right_second_image;

	public ActionTitlebar(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context);
	}

	public ActionTitlebar(Context context) {
		super(context);
		init(context);
	}

	void init(Context context) {

		LayoutInflater.from(context).inflate(R.layout.titlebar, this);

		tv_title = (TextView) findViewById(R.id.titlebar_tv);

		btn_backbtn = (ImageView) findViewById(R.id.titlebar_back);
		btn_location = (TextView) findViewById(R.id.titlebar_location);
		layout_left = (FrameLayout) findViewById(R.id.titlebar_layout_left);

		layout_right = (FrameLayout) findViewById(R.id.titlebar_layout_right);
		btn_right_text = (TextView) findViewById(R.id.titlebar_right_text);
		btn_right_image = (ImageView) findViewById(R.id.titlebar_right_image);

		layout_right_second = (FrameLayout) findViewById(R.id.titlebar_layout_right_second);
		btn_right_second_image = (ImageView) findViewById(R.id.titlebar_right_second_image);

		layout_left.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.titlebar_layout_left:
			((Activity) getContext()).finish();
			break;
		default:
			break;
		}
	}

	public void setTitle(String title) {
		tv_title.setText(title);
	}

	public void setTitle(int titleRes) {
		setTitle(getContext().getString(titleRes));
	}

	public void hideBackBtn() {
		btn_backbtn.setVisibility(View.GONE);
		layout_left.setVisibility(View.GONE);
	}

	public void showBackBtn() {
		btn_backbtn.setVisibility(View.VISIBLE);
		layout_left.setVisibility(View.VISIBLE);
	}

	public void hideRightLayout() {
		layout_right.setVisibility(View.GONE);
	}

	// for home location
	public void setLeft(String text, OnClickListener clickListener) {
		btn_backbtn.setVisibility(View.GONE);
		btn_location.setVisibility(View.VISIBLE);
		btn_location.setText(text);
		layout_left.setOnClickListener(clickListener);
	}

	public void setLeft(OnClickListener clickListener) {
		layout_left.setOnClickListener(clickListener);
	}

	public View getLeftView() {
		if (btn_backbtn.getVisibility() == View.VISIBLE) {
			return btn_backbtn;
		}
		if (btn_location.getVisibility() == View.VISIBLE) {
			return btn_location;
		}
		return null;
	}

	public View getLeftLayout() {
		return layout_left;
	}

	public View getRightLayout() {
		return layout_right;
	}

	public void setLeftText(String text) {
		btn_location.setText(text);
	}

	public void setLeftTextDrawable(int imageResource) {
		Drawable drawable = getResources().getDrawable(imageResource);
		btn_location.setCompoundDrawablesWithIntrinsicBounds(null, null,
				drawable, null);
	}

	// right
	public void setRight(String text, OnClickListener clickListener) {
		layout_right.setVisibility(View.VISIBLE);
		btn_right_text.setVisibility(View.VISIBLE);
		btn_right_text.setText(text);
		layout_right.setOnClickListener(clickListener);
	}

	public void setRight(int imageResource, OnClickListener clickListener) {
		layout_right.setVisibility(View.VISIBLE);
		btn_right_image.setVisibility(View.VISIBLE);
		btn_right_image.setImageResource(imageResource);
		layout_right.setOnClickListener(clickListener);
	}

	public View getRightView() {
		if (btn_right_text.getVisibility() == View.VISIBLE) {
			return btn_right_text;
		}
		if (btn_right_image.getVisibility() == View.VISIBLE) {
			return btn_right_image;
		}
		return null;
	}

	public void setRightSecond(int imageResource, OnClickListener clickListener) {
		layout_right.setVisibility(View.VISIBLE);
		layout_right_second.setVisibility(View.VISIBLE);
		layout_right_second.setOnClickListener(clickListener);
		btn_right_second_image.setImageResource(imageResource);
	}

	public View getRightSecondLayout() {
		return layout_right_second;
	}
}
