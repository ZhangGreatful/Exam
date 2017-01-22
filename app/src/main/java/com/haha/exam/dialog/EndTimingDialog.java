package com.haha.exam.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.haha.exam.R;

/**
 * Created by Administrator on 2016/12/10.
 */

public class EndTimingDialog extends Dialog {
    private Button yes;//确定按钮
    private Button no;//取消按钮
    private TextView messageTv, time;//消息提示文本
    private String messageStr;//从外界设置的消息文本
    private String timeStr;
    //确定文本和取消文本的显示内容
    private String yesStr, noStr;

    private EndTimingDialog.onNoOnclickListener noOnclickListener;//取消按钮被点击了的监听器
    private EndTimingDialog.onYesOnclickListener yesOnclickListener;//确定按钮被点击了的监听器

    /**
     * 设置取消按钮的显示内容和监听
     *
     * @param
     * @param onNoOnclickListener
     */
    public void setNoOnclickListener(EndTimingDialog.onNoOnclickListener onNoOnclickListener) {
        this.noOnclickListener = onNoOnclickListener;
    }

    /**
     * 设置确定按钮的显示内容和监听
     *
     * @param
     * @param onYesOnclickListener
     */
    public void setYesOnclickListener(EndTimingDialog.onYesOnclickListener onYesOnclickListener) {
        this.yesOnclickListener = onYesOnclickListener;
    }

    public EndTimingDialog(Context context) {
        super(context, R.style.MyDialog);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.end_timing_dialog);
        //按空白处不能取消动画
        setCanceledOnTouchOutside(false);

        //初始化界面控件
        initView();
        initData();
        //初始化界面控件的事件
        initEvent();

    }

    /**
     * 初始化界面的确定和取消监听器
     */
    private void initEvent() {
        //设置确定按钮被点击后，向外界提供监听
        yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (yesOnclickListener != null) {
                    yesOnclickListener.onYesClick();
                }
            }
        });
        //设置取消按钮被点击后，向外界提供监听
        no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (noOnclickListener != null) {
                    noOnclickListener.onNoClick();
                }
            }
        });
    }


    /**
     * 初始化界面控件
     */
    private void initView() {
        yes = (Button) findViewById(R.id.yes);
        no = (Button) findViewById(R.id.no);
        messageTv = (TextView) findViewById(R.id.message);
        time = (TextView) findViewById(R.id.time);
    }

    /**
     * 初始化界面控件的显示数据
     */
    private void initData() {
        //如果用户自定了title和message
        if (messageStr != null) {
            messageTv.setText(messageStr);
        }
        if (timeStr != null) {
            time.setText(timeStr);
        }
        //如果设置按钮的文字
        if (yesStr != null) {
            yes.setText(yesStr);
        }
        if (noStr != null) {
            no.setText(noStr);
        }
    }

    /**
     * 从外界Activity为Dialog设置dialog的message
     *
     * @param message
     */
    public void setMessage(String message) {
        messageStr = message;
    }

    public void setTime(String time) {
        timeStr = time;
    }

    public void setNoMessage(String message) {
        noStr = message;
    }

    public void setYesMessage(String message) {
        yesStr = message;
    }


    /**
     * 设置确定按钮和取消被点击的接口
     */
    public interface onYesOnclickListener {
        public void onYesClick();
    }

    public interface onNoOnclickListener {
        public void onNoClick();
    }
}