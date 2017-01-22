package com.wanzheng.driver.view;


import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.haha.exam.R;
import com.wanzheng.driver.util.SystemMethod;

public class TitleBarView extends RelativeLayout {

    private static final String TAG = "TitleBarView";
    private Context mContext;
    private Button btnLeft;
    private Button btnRight;
    private Button btn_titleLeft;
    private Button btn_titleRight;
    private TextView tv_center;
    private LinearLayout common_constact;

    public TitleBarView(Context context) {
        super(context);
        mContext = context;
        initView();
    }

    public TitleBarView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        initView();
    }

    private void initView() {
        LayoutInflater.from(mContext).inflate(R.layout.common_title_bar, this);
        btn_titleLeft = (Button) findViewById(R.id.constact_group);//新订单
        btn_titleRight = (Button) findViewById(R.id.constact_all);//抢单记录
        tv_center = (TextView) findViewById(R.id.title_txt);
        common_constact = (LinearLayout) findViewById(R.id.common_constact);

    }

    public void setCommonTitle(int LeftVisibility, int centerVisibility,
                               int center1Visibilter, int rightVisibility) {
        tv_center.setVisibility(centerVisibility);
        common_constact.setVisibility(center1Visibilter);

    }

    public void setBtnLeft(int icon, int txtRes) {
        Drawable img = mContext.getResources().getDrawable(icon);
        int height = SystemMethod.dip2px(mContext, 20);
        int width = img.getIntrinsicWidth() * height / img.getIntrinsicHeight();
        img.setBounds(0, 0, width, height);
        btnLeft.setText(txtRes);
        btnLeft.setCompoundDrawables(img, null, null, null);
    }

    public void setBtnLeft(int txtRes) {
        btnLeft.setText(txtRes);
    }

    public void setBtnLeftVisible(int visible) {
        btn_titleLeft.setVisibility(visible);
    }

    public void setBtnRightVisible(int visible) {
        btn_titleRight.setVisibility(visible);
    }


    public void setBtnRight(int icon) {
        Drawable img = mContext.getResources().getDrawable(icon);
        int height = SystemMethod.dip2px(mContext, 30);
        int width = img.getIntrinsicWidth() * height / img.getIntrinsicHeight();
        img.setBounds(0, 0, width, height);
        btnRight.setCompoundDrawables(img, null, null, null);
    }

    public void setTitleLeft(int resId) {
        btn_titleLeft.setText(resId);
    }

    public void setTitleRight(int resId) {
        btn_titleRight.setText(resId);
    }

    public void setTitleLeft(String resId) {
        btn_titleLeft.setText(resId);
    }

    public void setTitleRight(String resId) {
        btn_titleRight.setText(resId);
    }

    @SuppressLint("NewApi")
    public void setPopWindow(PopupWindow mPopWindow, TitleBarView titleBaarView) {
        mPopWindow.setBackgroundDrawable(new ColorDrawable(Color
                .parseColor("#E9E9E9")));
        mPopWindow.showAsDropDown(titleBaarView, 0, -15);
        mPopWindow.setAnimationStyle(R.style.popwin_anim_style);
        mPopWindow.setFocusable(true);
        mPopWindow.setOutsideTouchable(true);
        mPopWindow.update();

    }

    public void setTitleText(int txtRes) {
        tv_center.setText(txtRes);
    }

    public void setBtnLeftOnclickListener(OnClickListener listener) {
        btnLeft.setOnClickListener(listener);
    }

    public void setBtnRightOnclickListener(OnClickListener listener) {
        btnRight.setOnClickListener(listener);
    }

    public Button getTitleLeft() {
        return btn_titleLeft;
    }

    public Button getTitleRight() {
        return btn_titleRight;
    }

    public void destoryView() {
        btnLeft.setText(null);
        btnRight.setText(null);
        tv_center.setText(null);
    }

}
