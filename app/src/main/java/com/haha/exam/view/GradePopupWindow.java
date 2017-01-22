package com.haha.exam.view;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.haha.exam.R;
import com.haha.exam.bean.MyGrade;

/**
 * Created by Administrator on 2016/10/29.
 */
public class GradePopupWindow extends PopupWindow {

    private TextView cancel, check;
    private View view;
    private TextView fenshu, date, time, texttime, rightCount, errorCount, rightPercent, count;
    private MyGrade myGrade;
    private int position;


    public GradePopupWindow(Activity context, View.OnClickListener itemsOnClick, MyGrade myGrade, int position) {
        super(context);
        this.myGrade = myGrade;
        this.position = position;
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(R.layout.grage_popup, null);

        cancel = (TextView) view.findViewById(R.id.cancel);
        check = (TextView) view.findViewById(R.id.check);

        count = (TextView) view.findViewById(R.id.text_count);
        fenshu = (TextView) view.findViewById(R.id.fen_shu);
        date = (TextView) view.findViewById(R.id.date);
        time = (TextView) view.findViewById(R.id.time);
        texttime = (TextView) view.findViewById(R.id.text_time);
        rightCount = (TextView) view.findViewById(R.id.right_count);
        errorCount = (TextView) view.findViewById(R.id.wrong_count);
        rightPercent = (TextView) view.findViewById(R.id.right_percent);

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();//取消按钮
            }
        });
        check.setOnClickListener(itemsOnClick);
        initView();
        initClick();

    }

    private void initView() {
        String i = myGrade.getData().get(position).getScore() != null ? myGrade.getData().get(position).getScore() : "0";
        if (myGrade.getData().get(position).getSubject().equals("1")){//科目一  100道题
            count.setText("100");

            int right = Integer.valueOf(i);
            int error = 100 - right;

            rightCount.setText(myGrade.getData().get(position).getScore() + "题");
            errorCount.setText(String.valueOf(error) + "题");
            rightPercent.setText(myGrade.getData().get(position).getScore() + "%");
        }else {//科目四   50道题
            count.setText("50");
            int right = Integer.valueOf(i)/2;
            int error = 50 - right;

            rightCount.setText(right + "题");
            errorCount.setText(String.valueOf(error) + "题");
            rightPercent.setText(myGrade.getData().get(position).getScore() + "%");
        }
        fenshu.setText(myGrade.getData().get(position).getScore());
        date.setText(myGrade.getData().get(position).getBegin_time());
        String string = myGrade.getData().get(position).getTime() != null ? myGrade.getData().get(position).getTime() : "";
//        int time1 = Integer.parseInt(myGrade.getData().get(position).getTime());
        int time1 = 0;
        if (!string.equals("")) {
            time1 = Integer.parseInt(string);
        }
        int minute = time1 / 60;
        int second = time1 % 60;
        String str;

        if (minute < 10) {

            if (second < 10) {
                str = "0" + minute + "分" + "0" + second + "秒";
            } else {
                str = "0" + minute + "分" + second + "秒";
            }
        } else {
            if (second < 10) {
                str = minute + "分" + "0" + second + "秒";
            } else {
                str = minute + "分" + second + "秒";
            }
        }
        texttime.setText(str);
    }

    private void initClick() {
        //设置SelectPicPopupWindow的View
        this.setContentView(view);
        //设置SelectPicPopupWindow弹出窗体的宽
        this.setWidth(ViewGroup.LayoutParams.FILL_PARENT);
        //设置SelectPicPopupWindow弹出窗体的高
        this.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        //设置SelectPicPopupWindow弹出窗体可点击
        this.setFocusable(true);
//        设置SelectPicPopupWindow弹出窗体动画效果
        this.setAnimationStyle(R.anim.enter_anim);
        //实例化一个ColorDrawable颜色为半透明
        ColorDrawable dw = new ColorDrawable(0xb0000000);
        //设置SelectPicPopupWindow弹出窗体的背景
        this.setBackgroundDrawable(dw);
        //mMenuView添加OnTouchListener监听判断获取触屏位置如果在选择框外面则销毁弹出框
        view.setOnTouchListener(new View.OnTouchListener() {

            public boolean onTouch(View v, MotionEvent event) {

                int height = view.findViewById(R.id.pop_layout).getTop();
                int y = (int) event.getY();
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (y < height) {
                        dismiss();
                    }
                }
                return true;
            }
        });


    }

}
